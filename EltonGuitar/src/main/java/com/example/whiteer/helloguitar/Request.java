package com.example.whiteer.helloguitar;

/**
 * Created by whiteer on 16/05/27.
 */
public class Request {

    private int id;
    private String userID;
    private String sheetName;
    private String singerName;
    private String songURL;
    private String lyricURL;
    private String requestDate;
    private String mark;

    public Request(int id, String userID, String sheetName, String singerName, String songURL, String lyricURL, String requestDate, String mark) {
        this.id = id;
        this.userID = userID;
        this.sheetName = sheetName;
        this.singerName = singerName;
        this.songURL = songURL;
        this.lyricURL = lyricURL;
        this.requestDate = requestDate;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSongURL() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }

    public String getLyricURL() {
        return lyricURL;
    }

    public void setLyricURL(String lyricURL) {
        this.lyricURL = lyricURL;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
