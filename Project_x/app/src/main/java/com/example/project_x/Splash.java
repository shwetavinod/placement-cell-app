package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Splash extends AppCompatActivity {
    Button b1;
String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen


        setContentView(R.layout.activity_splash);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor ed = sh.edit();
        ip="192.168.167.26";
//        Toast.makeText(getApplicationContext(), "ip"+ip, Toast.LENGTH_LONG).show();

        ed.putString("ip",  ip);

        ed.putString("url", "http://"+ip+":8080/SHplacement/placement?wsdl");

        ed.putString("namespace", "http://dbcon/");
        ed.commit();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//      startActivity(new Intent(getApplicationContext(),Login.class));
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        }, 3000);

    }
}