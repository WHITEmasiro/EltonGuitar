package com.example.whiteer.helloguitar;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by whiteer on 16/05/16.
 */


public class DBConnector {
    private Context context;

    public DBConnector(Context context){
        this.context = context;
    }

    public  static JSONArray execute(String urlString,String paramsString){

        String jsonString = "";
        JSONArray jsonArray = null;

        try{

            URL url = new URL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(5000);//5 second
            urlConnection.setConnectTimeout(10000);//10 second
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.connect();

            //transport params of post
            OutputStream out = urlConnection.getOutputStream();
            out.write(paramsString.getBytes());
            out.flush();
            out.close();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isr = new InputStreamReader(in);

                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null){
                    sb.append(line + "\n");
                }
                br.close();

                char i = sb.charAt(0);
                i = sb.charAt(1);
                char k = i;
                if(sb.charAt(1) == '{'){
                    sb.insert(1,'[');
                    sb.insert(sb.length()-3,']');
                }

                jsonString = sb.toString();

                //convert jsonString to array of json object
                jsonArray = new JSONArray(jsonString);
            }

        }catch (MalformedInputException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonArray;

    }

    public List<Song> getSongList(String urlString,String paramsString){
        List<Song> songList = new ArrayList<>();

        try {

            JSONArray jsonArray = execute(urlString, paramsString);

            for(int i=0; i<jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String idString = jsonObject.getString("ID");
                int id = Integer.parseInt(idString);
                String name = jsonObject.getString("Sheet_Name");
                String singer = jsonObject.getString("Singer_Name");
                String date = jsonObject.getString("Created_Date");
                String classA = jsonObject.getString("ClassA");
                String classB = jsonObject.getString("ClassB");

                //add song here
                songList.add(new Song(id,name,singer,date,classA,classB));
            }

        }catch (Exception e){

        }

        return songList;
    }

    public void login(String urlString,String paramsString){

        SharedPreferences sharedPreferences = context.getSharedPreferences(PrefManager.PREF_NAME_USER_DATA, Context.MODE_PRIVATE);

        try {

            JSONArray jsonArray = execute(urlString, paramsString);

            if(jsonArray == null){
                sharedPreferences.edit()
                        .putString(PrefManager.USER_ID_KEY,"");
                return;
            }

            for(int i=0; i<jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("ID");
                sharedPreferences.edit()
                        .putString(PrefManager.USER_ID_KEY, id)
                        .apply();

            }

        }catch (Exception e){

        }

    }

    public void register(String urlString,String paramsString){

        execute(urlString,paramsString);

    }


}
