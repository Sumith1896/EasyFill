package com.udiboy.easyfill;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class WebActivity extends Activity {
    private static final String TAG = "WebView";
    WebView form;

    SharedPreferences prefs;
    String name, phone, email, hostel, rollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        name=prefs.getString(UserData.Name,"");
        email=prefs.getString(UserData.Email,"");
        hostel=prefs.getString(UserData.Hostel,"");
        rollno=prefs.getString(UserData.RollNo,"");
        phone=prefs.getString(UserData.Phone,"");

        form = (WebView) findViewById(R.id.web_view);

        form.getSettings().setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("url");

        form.loadUrl(url);
        form.setWebViewClient(new MyWebViewClient());
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
            Log.v("", "Webview Function URL: " + url);
            if(url.substring(0,4).equals("http")) autoFill(view);

            url=view.getUrl();
            if(url.substring(url.length()-12,url.length()).equals("formResponse")) {
                Toast.makeText(WebActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public  void autoFill(WebView view){

        String javascript = "javascript:(function(){" +
                "labels=document.getElementsByClassName(\"ss-q-item-label\");" +
                "fields={" +
                "NAME:\"" + name + "\"," +
                "HOSTEL:\"" + hostel + "\"," +
                "ROLLNO:\"" + rollno + "\"," +
                "PHONE:\"" + phone + "\"," +
                "EMAIL:\"" + email + "\"" +
                "}; function getval(str){ str=str.replace(/\\s+/g,\"\").toUpperCase(); return fields[str];};" +
                "for(i=0; i<labels.length; i++){ field_name=labels[i].getElementsByClassName(\"ss-q-title\")[0].innerHTML; document.getElementById(labels[i].getAttribute(\"for\")).value=getval(field_name)};})()";
                Log.d(TAG, javascript);

                Log.d(TAG, name+" "+hostel+" "+phone+" "+rollno+" "+email);

        view.loadUrl(javascript);
    }
}
