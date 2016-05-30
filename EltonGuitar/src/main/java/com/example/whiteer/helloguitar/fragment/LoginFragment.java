package com.example.whiteer.helloguitar.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.whiteer.helloguitar.DBConnector;
import com.example.whiteer.helloguitar.MainActivity;
import com.example.whiteer.helloguitar.Page;
import com.example.whiteer.helloguitar.PageID;
import com.example.whiteer.helloguitar.PrefManager;
import com.example.whiteer.helloguitar.R;
import com.example.whiteer.helloguitar.fragment.basic.MyFragment;
import com.example.whiteer.helloguitar.fragment.member.MemberFragment;
import com.example.whiteer.helloguitar.fragment.member.SaveRequestFragment;
import com.example.whiteer.helloguitar.fragment.member.SaveSongFragment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by whiteer on 16/05/22.
 */

public class LoginFragment extends MyFragment {

    enum TaskType{
        TaskNone,TaskLogin,TaskRegister
    }

    private TaskType taskType = TaskType.TaskNone;
    private static final String loginUrlString = "http://petradise.website/EltonGuitar/User_Info/login.php";
    private static final String registerUrlString = "http://petradise.website/EltonGuitar/User_Info/register.php";
    private String paramsString = "";
    private EditText etLoginMail;
    private EditText etLoginPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findView(view);

        return view;
    }

    private void findView(View view){

        etLoginMail = (EditText)view.findViewById(R.id.etLoginMail);
        etLoginPassword = (EditText)view.findViewById(R.id.etLoginPassword);

        btnLogin = (Button)view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Login!!");

                //login
                String account = etLoginMail.getText().toString();
                String password = etLoginPassword.getText().toString();

                paramsString = "Account=" + account + "&Password=" + password;
                taskType = TaskType.TaskLogin;
                new DownloadTask().execute();

            }
        });

        btnRegister = (Button)view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Register!!");

                //register
                String account = etLoginMail.getText().toString();
                String password = etLoginPassword.getText().toString();

                String patternStr = "[a-zA-Z_0-9]+?@[a-zA-Z_0-9]+?.[a-zA-Z0-9]{2,3}";
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(account);
                String message = "";
                Boolean success = true;
                if (!matcher.matches()) {
                    message = "信箱格式不正確\n";
                    success = false;
                }
                if (password.length() < 4 || password.length() > 16) {
                    message += "密碼4~16字元";
                    success = false;
                }
//                    System.out.println("wrong mail format!!");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(success ? "註冊成功" : "註冊失敗");
                builder.setMessage(message);
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                if (!success) return;


                paramsString = "Account=" + account + "&Password=" + password;
                taskType = TaskType.TaskRegister;
                new DownloadTask().execute();

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        System.out.println("menu created!!");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_login, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    //    @Override
//    public void setMenuVisibility(boolean menuVisible) {
////        System.out.println("set menu visibility!!");
//        super.setMenuVisibility(menuVisible);
//        setHasOptionsMenu(true);
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onStop();
//        setHasOptionsMenu(false);
//    }


    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
        if(hasMenu) getActivity().setTitle(R.string.login_title);
    }

    private class DownloadTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls){

            long totalSize = 0;
            try{
                //get jsonString of data by url string
                if(taskType == TaskType.TaskLogin) {
                    new DBConnector(getActivity().getApplicationContext()).login(loginUrlString, paramsString);
                }else if(taskType == TaskType.TaskRegister.TaskRegister){
                    new DBConnector(getActivity().getApplicationContext()).register(registerUrlString, paramsString);
                }
                taskType = TaskType.TaskNone;

            }catch (Exception e){

            }

            return totalSize;
        }

        @Override
        protected void onProgressUpdate(Integer... progress){
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(Long result){
            super.onPostExecute(result);

            Context context = getActivity().getApplicationContext();
            SharedPreferences sharedPreferences =
                    context.getSharedPreferences(PrefManager.PREF_NAME_USER_DATA, Context.MODE_PRIVATE);
            String userID = sharedPreferences.getString(PrefManager.USER_ID_KEY, "");

            if(userID != ""){

                MainActivity mainActivity = (MainActivity) getActivity();

                //request page
                mainActivity.setPage(1, new Page(PageID.RequestPageID, new RequestFragment(), PrefManager.RequestPageTitle));

                //page list for member page
                List<Page> tabPageList = new ArrayList<>();
                tabPageList.add(new Page(PageID.SavedSongPageID, new SaveSongFragment(), PrefManager.SavedSongPageTitle));
                tabPageList.add(new Page(PageID.SavedRequestPageID, new SaveRequestFragment(), PrefManager.SavedRequestPageTitle));

                //member page
                mainActivity.setPage(2, new Page(PageID.MemberPageID, new MemberFragment(tabPageList), PrefManager.MemberPageTitle));

            }else{

                //login fail

            }

        }

    }
}
