import java.util.HashMap;

/**
 * Created by KTachibanaM on 2015/5/31.
 * POJO for a user account
 */
public class Account {
    private String username;
    private String password;
    private String accessKey;
    private String deviceId;
    private String pullRequestTime = "Never Synced";
    private boolean login = false;
    private String loginInfo;
    private int userId = 0;
    private int maxId = 0;
    private HashMap<Integer, Record> data = new HashMap<>();

    private Account(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.accessKey = builder.accessKey;
        this.deviceId = builder.deviceId;
        this.pullRequestTime = builder.pullRequestTime;
        this.login = builder.login;
        this.loginInfo = builder.loginInfo;
        this.userId = builder.userId;
        this.maxId = builder.maxId;
        this.data = builder.data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPullRequestTime() {
        return pullRequestTime;
    }

    public void setPullRequestTime(String pullRequestTime) {
        this.pullRequestTime = pullRequestTime;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(String loginInfo) {
        this.loginInfo = loginInfo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public HashMap<Integer, Record> getData() {
        return data;
    }

    public void setData(HashMap<Integer, Record> data) {
        this.data = data;
    }

    public static class Builder {
        private String username;
        private String password;
        private String accessKey;
        private String deviceId;
        private String pullRequestTime;
        private boolean login = false;
        private String loginInfo;
        private int userId = 0;
        private int maxId = 0;
        private HashMap<Integer, Record> data;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder accessKey(String accessKey) {
            this.accessKey = accessKey;
            return this;
        }

        public Builder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder pullRequestTime(String pullRequestTime) {
            this.pullRequestTime = pullRequestTime;
            return this;
        }

        public Builder login(boolean login) {
            this.login = login;
            return this;
        }

        public Builder loginInfo(String loginInfo) {
            this.loginInfo = loginInfo;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder maxId(int maxId) {
            this.maxId = maxId;
            return this;
        }

        public Builder data(HashMap<Integer, Record> data) {
            this.data = data;
            return this;
        }
    }
}
