package model;

public class User {
    private String id;
    private String pwd;
    private String memName;
    private String rank;

    // ������, getter, setter ���� �ʿ信 ���� �߰��� �� �ֽ��ϴ�.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
