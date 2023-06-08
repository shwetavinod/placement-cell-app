package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Ip_setting extends AppCompatActivity {
    EditText ipet;
    Button ipbtn;
    String sip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_setting);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ipet=(EditText) findViewById(R.id.ip_et);
        ipbtn=(Button) findViewById(R.id.ip_btn);

        ipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sip=ipet.getText().toString();
                if(sip.equalsIgnoreCase(""))
                {
                    ipet.setError("");
                    ipet.setFocusable(true);
                }
//                else{
//
//                    SharedPreferences.Editor ed = sh.edit();
//                    ed.putString("ip", sip);
//                    ed.putString("url", "http://"+sip+":8080/internship_management_web/internship?wsdl");
//                    ed.putString("namespace", "http://dbcon/");
//                    ed.commit();
//                    startActivity(new Intent(getApplicationContext(), Login.class));
//                }




            }
        });
    }
}