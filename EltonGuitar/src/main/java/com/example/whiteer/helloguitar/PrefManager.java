package com.example.whiteer.helloguitar;

/**
 * Created by whiteer on 16/05/24.
 */

enum PageID{
    MainPageID,
    OrderNewPageID,
    OrderRatePageID,
    LoginPageID,
    RequestPageID,
    MemberPageID,
    SavedSongPageID,
    SavedRequestPageID
}

public class PrefManager {

    static final String PREF_NAME_USER_DATA = "user_data";
    static final String USER_ID_KEY = "userID";


    static final String MainPageTitle = "最新";
    static final String OrderNewPageTitle = "最新";
    static final String OrderRatePageTitle = "熱門";
    static final String LoginPageTitle = "登入";
    static final String RequestPageTitle = "求譜";
    static final String MemberPageTitle = "會員";
    static final String SavedSongPageTitle = "儲存歌曲";
    static final String SavedRequestPageTitle = "求譜紀錄";

}
