package com.udiboy.easyfill;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    SharedPreferences prefs;
    String name, phone, email, hostel, rollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onResume(){
        super.onResume();
        prefs = getSharedPreferences("MyPrefs", 0);
        if (!prefs.contains(UserData.Name)) {
            Intent i=new Intent(this,UserData.class);
            startActivity(i);
        }

        name=prefs.getString(UserData.Name,"");
        email=prefs.getString(UserData.Email,"");
        hostel=prefs.getString(UserData.Hostel,"");
        rollno=prefs.getString(UserData.RollNo,"");
        phone=prefs.getString(UserData.Phone,"");
    }

    public void get_qr(View v){
        Intent i = new Intent(this, QRActivity.class);
        startActivityForResult(i,1);
    }

    public void change_credentials(View v){
        Intent i=new Intent(this,UserData.class);
        startActivity(i);
    }

    public void go_web(View v){
        String url=((TextView)findViewById(R.id.URL)).getText().toString();

        if(url.length()==0){
            Toast.makeText(this, "Enter a url or event id", Toast.LENGTH_LONG).show();
        }else {
            if (url.substring(0, 4).equals("http")) {
                Intent i = new Intent(this, WebActivity.class);
                i.putExtra("url", url);
                startActivity(i);
            } else
                new ServerTask().execute("http://10.0.2.2:5000/reg/" + url);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            String url = data.getStringExtra("decoded");
            Intent i = new Intent(this, WebActivity.class);
            i.putExtra("url", url);
            startActivity(i);
        }
    }

    class ServerTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            String url = params[0], ret_value=null;

            AndroidHttpClient httpclient = AndroidHttpClient.newInstance(TAG);
            HttpPost httppost = new HttpPost(url);
            int statusCode = 0;

            try {
                List<NameValuePair> nameValuePairs = getPostData(params, 1);

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);

                statusCode = response.getStatusLine().getStatusCode();

                Log.d(TAG, statusCode + " : " + response.getStatusLine().getReasonPhrase());

                if(statusCode==200){
                    ret_value=buildStringFromIS(response.getEntity().getContent());
                } else {
                    onFail(buildStringFromIS(response.getEntity().getContent()));
                    ret_value=null;
                }
            } catch (IOException e) {
                onFail(e.getMessage());
            }

            httpclient.close();

            return ret_value;
            }

        public List<NameValuePair> getPostData(String[] params, int i) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("name", name));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("phone", phone));
            nameValuePairs.add(new BasicNameValuePair("hostel", hostel));
            nameValuePairs.add(new BasicNameValuePair("rollno", rollno));

            return nameValuePairs;
        }

        public void onFail(String message){
            Log.e(TAG, "Task failed : "+message);
        }

        public String buildStringFromIS(InputStream is) {
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));

                int c;
                while((c=bf.read()) != -1){
                    sb.append((char)c);
                }

                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;

            }
        }

        @Override
        public void onPostExecute(String result) {
            if(result!=null)
                Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
        }

    }

}
