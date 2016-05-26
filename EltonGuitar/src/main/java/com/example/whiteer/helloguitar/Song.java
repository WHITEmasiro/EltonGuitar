package com.example.whiteer.helloguitar;

/**
 * Created by whiteer on 16/05/17.
 */
public class Song {

    int id;
    private String name;
    private String singer;
    private String date;
    private String songClass;
    private String detail;

    public Song(int id, String name, String singer, String date, String songClass, String detail) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.date = date;
        this.songClass = songClass;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public String getDate() {
        return date;
    }

    public String getsongClass() {
        return songClass;
    }

    public String getdetail() {
        return detail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setAddDate(String addDate) {
        this.date = addDate;
    }

    public void setsongClass(String songClass) {
        this.songClass = songClass;
    }

    public void setdetail(String detail) {
        this.detail = detail;
    }
}
