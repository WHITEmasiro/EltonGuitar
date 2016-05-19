package com.example.whiteer.helloguitar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by whiteer on 16/05/18.
 */
public class PdfViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();

        String pdfPath = bundle.getString("pdfPath");

        WebView pdfWebView = new WebView(this);
        pdfWebView.getSettings().setJavaScriptEnabled(true);
        pdfWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        pdfWebView.loadUrl("https://docs.google.com/viewer?url=" + pdfPath);
        setContentView(pdfWebView);

    }

}
