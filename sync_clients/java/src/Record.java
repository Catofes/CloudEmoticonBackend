/**
 * Created by KTachibanaM on 2015/5/31.
 * POJO for a record
 */
public class Record {
    private int id;
    private int userId;
    private String value;
    private String addOn;
    private int type = 1;
    private String group;
    private String lastModified;
    private String checkCode;

    private Record(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.value = builder.value;
        this.addOn = builder.addOn;
        this.type = builder.type;
        this.group = builder.group;
        this.lastModified = builder.lastModified;
        this.checkCode = builder.checkCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAddOn() {
        return addOn;
    }

    public void setAddOn(String addOn) {
        this.addOn = addOn;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public static class Builder {
        private int id;
        private int userId;
        private String value;
        private String addOn;
        private int type;
        private String group;
        private String lastModified;
        private String checkCode;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder addOn(String addOn) {
            this.addOn = addOn;
            return this;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder group(String group) {
            this.group = group;
            return this;
        }

        public Builder lastModified(String lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder checkCode(String checkCode) {
            this.checkCode = checkCode;
            return this;
        }

        public Record create() {
            return new Record(this);
        }
    }
}
