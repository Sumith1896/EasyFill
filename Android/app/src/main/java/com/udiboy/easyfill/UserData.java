package com.udiboy.easyfill;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserData extends Activity {
    TextView name;
    TextView phone;
    TextView email;
    TextView hostel;
    TextView rollno;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String Hostel = "hostelKey";
    public static final String RollNo = "rollKey";

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
    name = (TextView) findViewById(R.id.editTextName);
    phone = (TextView) findViewById(R.id.editTextPhone);
    email = (TextView) findViewById(R.id.editTextEmail);
    hostel = (TextView) findViewById(R.id.editTextHostel);
    rollno = (TextView) findViewById(R.id.editTextRollno);

    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    if (sharedpreferences.contains(Name)) {
        name.setText(sharedpreferences.getString(Name, ""));

    }
    if (sharedpreferences.contains(Phone)) {
        phone.setText(sharedpreferences.getString(Phone, ""));

    }
    if (sharedpreferences.contains(Email)) {
        email.setText(sharedpreferences.getString(Email, ""));

    }
    if (sharedpreferences.contains(Hostel)) {
        hostel.setText(sharedpreferences.getString(Hostel, ""));

    }
    if (sharedpreferences.contains(RollNo)) {
        rollno.setText(sharedpreferences.getString(RollNo, ""));

    }
}
    public void run(View view){
        String n  = name.getText().toString();
        String ph  = phone.getText().toString();
        String e  = email.getText().toString();
        String s  = hostel.getText().toString();
        String p  = rollno.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, n);
        editor.putString(Phone, ph);
        editor.putString(Email, e);
        editor.putString(Hostel, s);
        editor.putString(RollNo, p);
        String check[]={n,ph,e,s,p};
        int f;
            f=0;
            for(int j=0;j<5;j++)
            {

                Log.d("userdata", ""+check[j].length());
                if(check[j].length()==0) {
                    f = 1;
                    break;
                }
            }
            if(f==1) {
                Toast.makeText(this, "Please fill all the parameters", Toast.LENGTH_SHORT).show();
            }
        else {
                editor.commit();
                finish();
            }

    }
}
