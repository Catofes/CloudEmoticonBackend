import java.util.*;
/**
 * Created by herbertqiao on 12/23/14.
 */

/* How to Use

    1. sync = new Sync;
    2. sync.onLoad() //Get Info from local.
    3. sync.onCheckLogin(); //Check Login Info.
    4. sync.Login(username,password): //Save Login info.
    5. while(true) pull();

    What is a Record?
    Value: A string(<64Kb) store EVERY USEFUL INFO. such {"Data":":D","Description":"Laugh","Category":"Smile"}
    Type: A int store TYPE INFO of the record.  such 1 means normal record. 2 means deleted record. 3 means Group Info Record. 4 means deleted Group Info Record.
    Group: A int store addId info. such group.
    AddOn:  A string(<16Mb) store AddOn Info. such as collection info.


    Add a Record
    sync.add(Record)

    Modify a Record
    sync.modify(Record)


 */


class Record
{
    public int Id;
    public int UserId;
    public String Value;
    public String AddOn;
    public int Type = 1;
    public String Group;
    public String LastModified;
    public String CheckCode;
}

class Account
{
    public String username;
    public String password;
    public String accesskey;
    public String deviceid;
    public String pullrequesttime="Never Synced";
    public boolean login=false;
    public String logininfo;
    public int userid=0;
    public int MaxId=0;
    public HashMap<Integer,Record> data = new HashMap<Integer,Record>();
}

public class Sync
{
    private String url = "https://web.emoticon.moe";
    public String Version = "0.0.1";
    public int appid = 1;
    private Account value = new Account();
    public int ErrorCode;
    public String ErrorMessage;

    private int setError(String info)
    {
        ErrorCode = 401;
        ErrorMessage = "Remote Error At:"+info;
        return 0;
    }

    private int setError(int errorCode)
    {
        ErrorCode = errorCode;
        switch (errorCode) {
            case 0:
                ErrorMessage = "No Error.";
                break;
            case 101:
                ErrorMessage = "Login Successful.";
                break;
            case 201:
                ErrorMessage = "No UserName.";
                break;
            case 202:
                ErrorMessage = "Login Failed.";
                break;
            case 301:
                ErrorMessage = "Wait for Login.";
                break;

        }
        return 0;
    }

    //This function to reset the class.
    public int init()
    {
        value.username = "";
        value.password = "";
        value.accesskey = "";
        value.deviceid = "";
        value.pullrequesttime = "Never Synced";
        value.login = false;
        value.logininfo = "No Login";
        value.userid = 0;
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
        $.post(this.url + "/api/account.php?f=login", {"u":value.username,"p":value.passowrd, "k":1})
        {
            //Here is callback. returned data format is json.
            //DECODEJSON(data)
            if (data.code == 101) {
                this.value.accesskey = data.AccessKey;
                this.value.userid = data.UserId;
                this.value.login = true;
                setError(101);
                this.value.logininfo = "Login Success.";
                this.onSave();
            } else {
                setError(202);
                this.value.logininfo = "Login Failed.";
                this.onSave();
            }
        }
        return 0;
    }

    public int onCheckLogin()
    {
        $.post(this.url+"/api/account.php?f=checklogin",{ "ak" : this.value.accesskey })
        //CallBack
        {
            if(data.code==302) {
                this.value.login=false;
                this.value.logininfo="Login Expired.";
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
        $.post(this.url+"/api/device.php?f=now",{ "ak" : value.accesskey }
        //CALLBACK
        {
            if(data.code==101){
                value.pullrequesttime = data.Now;
            }else{
                setError(String(data.code));
                return -1;
            }
            onSave();
            $.post(url+"/api/device.php?f=pull",{ "ak" : value.accesskey ,"d": value.deviceid},
            //CALLBACK
            {
                if(data==101) {
                    for (int i = 0; i < data.Result.length; i++) {
                        int id = data.Result[i].Id;
                        Integer ID = new Integer(id);
                        if (id > value.MaxId)
                            value.MaxId = id;
                        if (value.data.get(ID) != null && value.data.get(ID).CheckCode.length() < 4) {
                            Record record = value.data.get(ID);
                            record.CheckCode = "+";
                            value.MaxId++;
                            Integer newID = new Integer(value.MaxId);
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
                        this.value.pullrequesttime = data.Result[0].PullTime;
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
                $.post(this.url+"/api/favor.php?f=add",{"ak": value.accesskey, "v": record.Value,"a":record.AddOn "l":record.Type, "g": record.Group,"t": value.pullrequesttime},
                //CALLBACK
                {
                    if(data.code == 101) {
                        Integer returnID = new Integer(data.Result.Id);
                        if (this.value.data.get(returnID) != null) {
                            value.MaxId++;
                            this.value.data.put(new Integer(value.MaxId), this.value.data.get(returnID));
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
                $.post(this.url+"/api/favor.php?f=modify",{"ak": value.accesskey, "i": record.Id, "v": record.Value,"a":record.AddOn "l":record.Type,"g":record.Group, "t": value.pullrequesttime},
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
        $.post(this.url+"/api/device.php?f=pullok",{"ak":value.accesskey, 'd' : value.deviceid , 't' : value.pullrequesttime});
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
        value.MaxId++;
        record.Id=value.MaxId;
        Integer ID = new Integer(value.MaxId);
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

