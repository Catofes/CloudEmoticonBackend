import java.util.*;

/**
 * Created by herbertqiao on 12/23/14.
 *
 * Instructions
 * 1. sync = new Sync(apiEndpoint, httpClient);
 * 2. sync.onLoad() //Get Info from local.
 * 3. sync.onCheckLogin(); //Check Login Info.
 * 4. sync.Login(username, password): //Save Login info.
 * 5. while(true) pull();
 *
 * What is a Record?
 * value: A string(<64Kb) store EVERY USEFUL INFO. such {"Data":":D","Description":"Laugh","Category":"Smile"}
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
    public static final String version = "0.0.1";
    public static final int appId = 1;

    // Variables
    public String apiEndPoint;
    private Account value = new Account();
    public int errorCode;
    public String errorMessage;

    // External services
    private HttpClient httpClient;

    public Sync(String apiEndPoint, HttpClient httpClient) {
        this.apiEndPoint = apiEndPoint;
        this.httpClient = httpClient;
    }

    private int setError(String info)
    {
        errorCode = 401;
        errorMessage = "Remote Error At:"+info;
        return 0;
    }

    private int setError(int errorCode)
    {
        this.errorCode = errorCode;
        switch (errorCode) {
            case 0:
                errorMessage = "No Error.";
                break;
            case 101:
                errorMessage = "Login Successful.";
                break;
            case 201:
                errorMessage = "No UserName.";
                break;
            case 202:
                errorMessage = "Login Failed.";
                break;
            case 301:
                errorMessage = "Wait for Login.";
                break;

        }
        return 0;
    }

    //This function to reset the class.
    public int init()
    {
        value.username = "";
        value.password = "";
        value.accessKey = "";
        value.deviceId = "";
        value.pullRequestTime = "Never Synced";
        value.login = false;
        value.loginInfo = "No Login";
        value.userId = 0;
        value.data.clear();
        return 0;
    }


    //This function is save all info to local.
    // NEED FINISH
    public int onSave()
    {
        return 0;
    }

    //This function is load all info
    //NEED FINISH
    public int onLoad()
    {
        return 0;
    }

    private int onLogin()
    {
        if (value.username.equals("")) {
            setError(201);
            return -1;
        }
        //NEED FINISH
        $.post(this.apiEndPoint + "/api/account.php?f=login", {"u":value.username,"p":value.passowrd, "k":1})
        {
            //Here is callback. returned data format is json.
            //DECODEJSON(data)
            if (data.code == 101) {
                this.value.accessKey = data.AccessKey;
                this.value.userId = data.UserId;
                this.value.login = true;
                setError(101);
                this.value.loginInfo = "Login Success.";
                this.onSave();
            } else {
                setError(202);
                this.value.loginInfo = "Login Failed.";
                this.onSave();
            }
        }
        return 0;
    }

    public int onCheckLogin()
    {
        $.post(this.apiEndPoint +"/api/account.php?f=checklogin",{ "ak" : this.value.accessKey})
        //CallBack
        {
            if(data.code==302) {
                this.value.login=false;
                this.value.loginInfo ="Login Expired.";
                setError(301);
                this.onLogin();
            }
        }
    }

    public int pull()
    {
        onLoad();
        if(!this.value.login){
            return -1;
        }
        $.post(this.apiEndPoint +"/api/device.php?f=now",{ "ak" : value.accessKey}
        //CALLBACK
        {
            if(data.code==101){
                value.pullRequestTime = data.Now;
            }else{
                setError(String(data.code));
                return -1;
            }
            onSave();
            $.post(apiEndPoint +"/api/device.php?f=pull",{ "ak" : value.accessKey,"d": value.deviceId},
            //CALLBACK
            {
                if(data==101) {
                    for (int i = 0; i < data.Result.length; i++) {
                        int id = data.Result[i].Id;
                        Integer ID = new Integer(id);
                        if (id > value.maxId)
                            value.maxId = id;
                        if (value.data.get(ID) != null && value.data.get(ID).CheckCode.length() < 4) {
                            Record record = value.data.get(ID);
                            record.CheckCode = "+";
                            value.maxId++;
                            Integer newID = new Integer(value.maxId);
                            value.data.put(newID, record);
                            record.Id = data.Result[i].Id;
                            record.UserId = data.Result[i].UserId;
                            record.Value = data.Result[i].Value;
                            record.AddOn = data.Result[i].AddOn;
                            record.Type = data.Result[i].Type;
                            record.Group = data.Result[i].Group;
                            record.LastModified = data.Result[i].LastModified;
                            record.CheckCode = data.Result[i].CheckCode;
                            value.data.put(ID, record);
                        } else if (value.data.get(ID) != null && value.data.get(ID).CheckCode == data.Result[i].CheckCode) {
                            continue;
                        }else{
                            Record record = new Record();
                            record.Id = data.Result[i].Id;
                            record.UserId = data.Result[i].UserId;
                            record.Value = data.Result[i].Value;
                            record.AddOn = data.Result[i].AddOn;
                            record.Type = data.Result[i].Type;
                            record.Group = data.Result[i].Group;
                            record.LastModified = data.Result[i].LastModified;
                            record.CheckCode = data.Result[i].CheckCode;
                            value.data.put(ID, record);
                        }
                        this.value.pullRequestTime = data.Result[0].PullTime;
                    }
                    onSave();
                    setError(101);
                }else {
                    setError(data.code);
                    return -1;
                }
                push();
                pushok();
            }
        }
    }

    private int push()
    {
        if(this.value.login==false)
            return -1;
        Iterator iter =value.data.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<Integer, Record> entry =(Map.Entry<Integer, Record>) iter.next();
            Integer ID = entry.getKey();
            Record record = entry.getValue();
            if(record.CheckCode=="+"){
                value.data.remove(ID);
                $.post(this.apiEndPoint +"/api/favor.php?f=add",{"ak": value.accessKey, "v": record.Value,"a":record.AddOn "l":record.Type, "g": record.Group,"t": value.pullRequestTime},
                //CALLBACK
                {
                    if(data.code == 101) {
                        Integer returnID = new Integer(data.Result.Id);
                        if (this.value.data.get(returnID) != null) {
                            value.maxId++;
                            this.value.data.put(new Integer(value.maxId), this.value.data.get(returnID));
                        }
                        record.Id = data.Result.Id;
                        record.UserId = data.Result.UserId;
                        record.Value = data.Result.Value;
                        record.AddOn = data.Result.AddOn;
                        record.Type = data.Result.Type;
                        record.Group = data.Result.Group;
                        record.LastModified = data.Result.LastModified;
                        record.CheckCode = data.Result.CheckCode;
                        value.data.put(ID, record);
                        this.onSave();
                    }
                }
            }else if(record.CheckCode=="*") {
                $.post(this.apiEndPoint +"/api/favor.php?f=modify",{"ak": value.accessKey, "i": record.Id, "v": record.Value,"a":record.AddOn "l":record.Type,"g":record.Group, "t": value.pullRequestTime},
                {
                    if(data.code == 101) {
                        record.Id = data.Result.Id;
                        record.UserId = data.Result.UserId;
                        record.Value = data.Result.Value;
                        record.AddOn = data.Result.AddOn;
                        record.Type = data.Result.Type;
                        record.Group = data.Result.Group;
                        record.LastModified = data.Result.LastModified;
                        record.CheckCode = data.Result.CheckCode;
                        value.data.put(ID, record);
                        this.onSave();
                    }

                }
            }
        }
        return 0;
    }

    private int pushok()
    {
        if(!value.login)
            return -1;
        $.post(this.apiEndPoint +"/api/device.php?f=pullok",{"ak":value.accessKey, 'd' : value.deviceId, 't' : value.pullRequestTime});
        {
            //No CallBack
        }
    }

    public int login(String username,String password)
    {
        value.username=username;
        value.password=password;
        SetError(301);
        onLogin();
        return 0;
    }

    public ArrayList<Record> getAllRecord()
    {
        ArrayList<Record> result = new ArrayList<Record>();
        Iterator iter =value.data.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<Integer, Record> entry = (Map.Entry<Integer, Record>) iter.next();
            result.add(entry.getValue());
        }
        return result;
    }

    public int add(Record record)
    {
        record.CheckCode="+";
        value.maxId++;
        record.Id=value.maxId;
        Integer ID = new Integer(value.maxId);
        value.data.put(ID, record);
        return record.Id;
    }

    public int modify(Record record)
    {
        record.CheckCode="*";
        Integer ID = new Integer(record.Id);
        value.data.put(ID, record);
        return record.Id;
    }
}

