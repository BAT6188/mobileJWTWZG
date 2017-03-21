package com.zondy.jwt.jwtmobile.entity;


//import io.realm.RealmObject;
//import io.realm.annotations.PrimaryKey;
//import io.realm.annotations.Required;

/**
 * Created by sheep on 2017/2/10.
 */

public class EntitySearchHistory {
    public EntitySearchHistory() {

    }

    public EntitySearchHistory(String id, String keyword, String time) {
        this.id = id;
        this.keyword = keyword;
        this.time = time;
    }


    private String id;//主键

    private String keyword;
    private String time;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
