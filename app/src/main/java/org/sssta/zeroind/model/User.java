package org.sssta.zeroind.model;

/**
 * Created by 林韬 on 2015/10/15.
 */
public class User {
    private String username;
    private String password;
    private String sex;
    private int level;
    private int wind_direction;
    private int exp;
    private String img_id;
    private String signature;

    public User(String userName, String password, String sex, int level, int direction, int exp, String img_id) {
        this.username = userName;
        this.password = password;
        this.sex = sex;
        this.level = level;
        this.wind_direction = direction;
        this.exp = exp;
        this.img_id = img_id;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getUserName() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDirection() {
        return wind_direction;
    }

    public void setWind_direction(int direction) {
        this.wind_direction = direction;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
