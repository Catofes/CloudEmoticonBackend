import java.util.*;

/**
 * Created by herbertqiao on 12/23/14.
 *
 * Instructions
 * 1. sync = new Sync(apiEndpoint, httpService);
 * 2. sync.onLoad() //Get Info from local.
 * 3. sync.onCheckLogin(); //Check Login Info.
 * 4. sync.Login(username, password): //Save Login info.
 * 5. while(true) pull();
 *
 * What is a Record?
 * user: A string(<64Kb) store EVERY USEFUL INFO. such {"Data":":D","Description":"Laugh","Category":"Smile"}
 * type: A int store TYPE INFO of the record.  such 1 means normal record. 2 means deleted record. 3 means group Info Record. 4 means deleted group Info Record.
 * group: A int store addId info. such group.
 * addOn:  A string(<16Mb) store addOn Info. such as collection info.
 *
 * Add a Record
 * sync.add(Record)
 *
 * Modify a Record
 * sync.modify(Record)
 */


public class Sync
{
    // Info
    public static final String VERSION = "0.0.1";
    public static final int APP_ID = 1;

    // Variables
    public String apiEndPoint;
    private Account user;

    // Callbacks
    public interface BooleanFinishCallback {
        void finish(boolean b, String statusCode, String message);
    }

    // External services
    private HttpService httpService;
    private JsonService jsonService;

    public Sync(
            String apiEndPoint,
            HttpService httpService,
            JsonService jsonService
    ) {
        this.apiEndPoint = apiEndPoint;
        this.httpService = httpService;
        this.jsonService = jsonService;
    }

    private String getMessageFromStatusCode(String errorCode)
    {
        if (errorCode.equals("101")) return "OK.";
        if (errorCode.equals("201")) return "Please login.";
        if (errorCode.equals("202")) return "Already logged in. Please logout first.";
        if (errorCode.equals("203")) return "Username or password incorrect.";
        if (errorCode.equals("204")) return "SQL Error. We are sorry about that. Please contact administrator to solve the issue.";
        if (errorCode.equals("205")) return "Access denied. Your account was banned.";
        if (errorCode.equals("206")) return "Wrong password.";
        if (errorCode.equals("207")) return "Please input password.";
        if (errorCode.equals("208")) return "Username illegal.";
        if (errorCode.equals("209")) return "Username exists.";
        if (errorCode.equals("210")) return "Email address illegal.";
        if (errorCode.equals("211")) return "Email address exists.";
        if (errorCode.equals("212")) return "Data too long.";
        if (errorCode.equals("213")) return "Please input user.";
        if (errorCode.equals("214")) return "Please input id.";
        if (errorCode.equals("215")) return "Wrong input.";
        if (errorCode.equals("216")) return "Permission denied.";
        if (errorCode.equals("217")) return "No record.";
        if (errorCode.equals("218")) return "Device id is needed.";
        if (errorCode.equals("219")) return "Please input MainKey";
        if (errorCode.equals("220")) return "Wrong device id";
        if (errorCode.equals("404")) return "Page not found";
        return "No error.";
    }


    // This function is save all info to local.
    // TODO
    public void onSave()
    {
    }

    // This function is load all info
    // TODO
    public void onLoad()
    {
    }

    private void onLogin(String username, String password, boolean keepLogin, BooleanFinishCallback callback)
    {
        if (user.getUsername().equals("")) {
            String statusCode = "201";
            callback.finish(false, statusCode, getMessageFromStatusCode(statusCode));
        }
        else
        {
            // TODO: post json object
            httpService.post(
                    this.apiEndPoint + "/api/account.php?f=login",
                    "u, p, k"
                    , new HttpService.PostFinishCallback() {
                        @Override
                        public void success(String data) {
                            RequestStatus status = getRequestStatusFromJsonString(data);
                            if (status.statusCode.equals("101")) {
                                user.setAccessKey(jsonService.getFieldAsString(data, "AccessKey"));
                                user.setUserId(jsonService.getFieldAsInteger(data, "UserId"));
                                user.setLogin(true);
                                user.setLoginInfo(status.message);
                                callback.finish(true, status.statusCode, status.message);
                            } else {
                                user.setLoginInfo(status.message);
                                callback.finish(false, status.statusCode, status.message);
                            }
                            onSave();
                        }

                        @Override
                        public void fail() {
                            callback.finish(false, "", "");
                        }
                    });
        }
    }

    public void onCheckLogin(BooleanFinishCallback callback)
    {
        // TODO: post json object
        httpService.post(
                this.apiEndPoint + "/api/account.php?f=checklogin",
                "ak",
                new HttpService.PostFinishCallback() {
                    @Override
                    public void success(String data) {
                        RequestStatus status = getRequestStatusFromJsonString(data);
                        if (status.statusCode.equals("302")) {
                            user.setLogin(false);
                            // TODO: 301?
                            user.setLoginInfo("Login Expired.");
                            // TODO: on login again?
                            callback.finish(true, status.statusCode, status.message);
                        }
                        else
                        {
                            callback.finish(false, status.statusCode, status.message);
                        }
                    }

                    @Override
                    public void fail() {
                        callback.finish(false, "", "");
                    }
                }

        );
    }

    public void pull(BooleanFinishCallback callback)
    {
        onLoad();
        if (!this.user.isLogin()) {
            callback.finish(false, "", "");
        }
        else
        {
            httpService.post(
                    this.apiEndPoint + "/api/device.php?f=now",
                    "ak",
                    new HttpService.PostFinishCallback() {
                        @Override
                        public void success(String data) {
                            RequestStatus status = getRequestStatusFromJsonString(data);
                            if (status.statusCode.equals("101")) {
                                user.setPullRequestTime(jsonService.getFieldAsString(data, "Now"));
                                onSave();
                                httpService.post(
                                        apiEndPoint + "/api/device.php?f=pull",
                                        "ak, d",
                                        new HttpService.PostFinishCallback() {
                                            @Override
                                            public void success(String data) {
                                                RequestStatus status1 = getRequestStatusFromJsonString(data);
                                                    if(status1.statusCode.equals("101")) {
                                                        for (int i = 0; i < data.Result.length; i++) {
                                                            int id = data.Result[i].Id;
                                                            Integer ID = new Integer(id);
                                                            if (id > user.maxId)
                                                                user.maxId = id;
                                                            if (user.data.get(ID) != null && user.data.get(ID).CheckCode.length() < 4) {
                                                                Record record = user.data.get(ID);
                                                                record.CheckCode = "+";
                                                                user.maxId++;
                                                                Integer newID = new Integer(user.maxId);
                                                                user.data.put(newID, record);
                                                                record.Id = data.Result[i].Id;
                                                                record.UserId = data.Result[i].UserId;
                                                                record.user = data.Result[i].user;
                                                                record.AddOn = data.Result[i].AddOn;
                                                                record.Type = data.Result[i].Type;
                                                                record.Group = data.Result[i].Group;
                                                                record.LastModified = data.Result[i].LastModified;
                                                                record.CheckCode = data.Result[i].CheckCode;
                                                                user.data.put(ID, record);
                                                            } else if (user.data.get(ID) != null && user.data.get(ID).CheckCode == data.Result[i].CheckCode) {
                                                                continue;
                                                            }else{
                                                                Record record = new Record();
                                                                record.Id = data.Result[i].Id;
                                                                record.UserId = data.Result[i].UserId;
                                                                record.user = data.Result[i].user;
                                                                record.AddOn = data.Result[i].AddOn;
                                                                record.Type = data.Result[i].Type;
                                                                record.Group = data.Result[i].Group;
                                                                record.LastModified = data.Result[i].LastModified;
                                                                record.CheckCode = data.Result[i].CheckCode;
                                                                user.data.put(ID, record);
                                                            }
                                                            this.user.pullRequestTime = data.Result[0].PullTime;
                                                        }
                                                        onSave();
                                                        getMessageFromStatusCode(101);
                                                    }else {
                                                        setError(data.code);
                                                        return -1;
                                                    }
                                                    push();
                                                    pushok();

                                            }

                                            @Override
                                            public void fail() {
                                                callback.finish(false, "", "");
                                            }
                                        }
                                );
                            }
                            else
                            {
                                callback.finish(false, status.statusCode, status.message);
                            }
                        }

                        @Override
                        public void fail() {
                            callback.finish(false, "", "");
                        }
                    });
        }
    }

    private int push()
    {
        if(this.user.login==false)
            return -1;
        Iterator iter =user.data.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<Integer, Record> entry =(Map.Entry<Integer, Record>) iter.next();
            Integer ID = entry.getKey();
            Record record = entry.getuser();
            if(record.CheckCode=="+"){
                user.data.remove(ID);
                $.post(this.apiEndPoint +"/api/favor.php?f=add",{"ak": user.accessKey, "v": record.user,"a":record.AddOn "l":record.Type, "g": record.Group,"t": user.pullRequestTime},
                //CALLBACK
                {
                    if(data.code == 101) {
                        Integer returnID = new Integer(data.Result.Id);
                        if (this.user.data.get(returnID) != null) {
                            user.maxId++;
                            this.user.data.put(new Integer(user.maxId), this.user.data.get(returnID));
                        }
                        record.Id = data.Result.Id;
                        record.UserId = data.Result.UserId;
                        record.user = data.Result.user;
                        record.AddOn = data.Result.AddOn;
                        record.Type = data.Result.Type;
                        record.Group = data.Result.Group;
                        record.LastModified = data.Result.LastModified;
                        record.CheckCode = data.Result.CheckCode;
                        user.data.put(ID, record);
                        this.onSave();
                    }
                }
            }else if(record.CheckCode=="*") {
                $.post(this.apiEndPoint +"/api/favor.php?f=modify",{"ak": user.accessKey, "i": record.Id, "v": record.user,"a":record.AddOn "l":record.Type,"g":record.Group, "t": user.pullRequestTime},
                {
                    if(data.code == 101) {
                        record.Id = data.Result.Id;2
                        record.UserId = data.Result.UserId;
                        record.user = data.Result.user;
                        record.AddOn = data.Result.AddOn;
                        record.Type = data.Result.Type;
                        record.Group = data.Result.Group;
                        record.LastModified = data.Result.LastModified;
                        record.CheckCode = data.Result.CheckCode;
                        user.data.put(ID, record);
                        this.onSave();
                    }

                }
            }
        }
        return 0;
    }

    private int pushok()
    {
        if(!user.login)
            return -1;
        $.post(this.apiEndPoint +"/api/device.php?f=pullok",{"ak":user.accessKey, 'd' : user.deviceId, 't' : user.pullRequestTime});
        {
            //No CallBack
        }
    }

    public int login(String username,String password)
    {
        user.username=username;
        user.password=password;
        SetError(301);
        onLogin();
        return 0;
    }

    public ArrayList<Record> getAllRecord()
    {
        ArrayList<Record> result = new ArrayList<Record>();
        Iterator iter =user.data.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<Integer, Record> entry = (Map.Entry<Integer, Record>) iter.next();
            result.add(entry.getuser());
        }
        return result;
    }

    public int add(Record record)
    {
        record.CheckCode="+";
        user.maxId++;
        record.Id=user.maxId;
        Integer ID = new Integer(user.maxId);
        user.data.put(ID, record);
        return record.Id;
    }

    public int modify(Record record)
    {
        record.CheckCode="*";
        Integer ID = new Integer(record.Id);
        user.data.put(ID, record);
        return record.Id;
    }

    private class RequestStatus {
        public String statusCode;
        public String message;
    }

    private RequestStatus getRequestStatusFromJsonString(String data) {
        RequestStatus status = new RequestStatus();
        status.statusCode = jsonService.getFieldAsString(data, "code");
        status.message = getMessageFromStatusCode(status.statusCode);
        return status;
    }
}

