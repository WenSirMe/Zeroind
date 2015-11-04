package org.sssta.zeroind.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Heaven on 2015/10/25.
 */
public class Message {
    private String content;
    private String sex;
    private int hp;
    @SerializedName("wind_direction")
    private int windDirection;
    @SerializedName("from_uid")
    private int fromUid;
    private int id;
    @SerializedName("to_uid")
    private int toUid;
    private int status;
    private int count;
    @SerializedName("from_level")
    private int fromLevel;
    private String imgID;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImgID() {
        return imgID;
    }

    public void setImgID(String imgID) {
        this.imgID = imgID;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public int getFromUid() {
        return fromUid;
    }

    public void setFromUid(int fromUid) {
        this.fromUid = fromUid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToUid() {
        return toUid;
    }

    public void setToUid(int toUid) {
        this.toUid = toUid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Message(String content, String sex, int count, int hp, int windDirection, int fromUid, int id, int toUid, int status, String imgID) {
        this.content = content;
        this.sex = sex;
        this.count = count;
        this.hp = hp;
        this.windDirection = windDirection;
        this.fromUid = fromUid;
        this.id = id;
        this.toUid = toUid;
        this.status = status;
        this.imgID = imgID;
    }
}
