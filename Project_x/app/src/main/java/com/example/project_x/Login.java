package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class Login extends AppCompatActivity {
    EditText lreg, lpass;
    Button login;
    TextView tv1, tv2;
    String sreg, spass;
    public static String logid,ut;

    String namespace = "http://dbcon/";
    String method = "login";
    String soapAction = "";
    String url = "";
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_login);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lreg=(EditText) findViewById(R.id.l_regno);
        lpass=(EditText) findViewById(R.id.l_pass);
        login=(Button) findViewById(R.id.l_btn);
        tv1=(TextView) findViewById(R.id.l_tv);
        tv2=(TextView) findViewById(R.id.l_tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Registration.class));
            }
        });



        try {
            if(android.os.Build.VERSION.SDK_INT>9)
            {
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

        } catch (Exception e) {
// TODO: handle exception
        }
        url=sh.getString("url","");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sreg=lreg.getText().toString();
                spass=lpass.getText().toString();
                if (sreg.equalsIgnoreCase("")){
                    lreg.setError("");
                    lreg.setFocusable(true);
                }
                else if (spass.equalsIgnoreCase("")){
                    lpass.setError("");
                    lpass.setFocusable(true);
                }
//                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                else{
//                    Toast.makeText(getApplicationContext(),"username:"+username+"password:"+password,Toast.LENGTH_LONG).show();
                    try {
//                        method="login";
                        soapAction = namespace + method;
                        SoapObject sop = new SoapObject(namespace, method);
                        sop.addProperty("username", sreg);
                        sop.addProperty("password", spass);
                        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        env.setOutputSoapObject(sop);
                        HttpTransportSE hp = new HttpTransportSE(url);
                        hp.call(soapAction, env);

                        Toast.makeText(getApplicationContext(), env.getResponse() + "", Toast.LENGTH_SHORT).show();

                        String result = env.getResponse().toString();
                        Toast.makeText(getApplicationContext(), env.getResponse().toString(), Toast.LENGTH_SHORT).show();
                        if (result.equals("na")) {

                            Toast.makeText(getApplicationContext(), "Login failed.!", Toast.LENGTH_SHORT).show();
                        } else {
                            String[] temp1 = result.split("\\#");
                            logid = temp1[0];
                            ut = temp1[0];


                            Toast.makeText(getApplicationContext(), "Login success.!", Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor ed = sh.edit();
                            ed.putString("log_id", temp1[0]);
                            ed.putString("sender_id", temp1[0]);
                            ed.putString("usertype", temp1[1]);



                            ed.commit();
                            if (sh.getString("usertype", "").equalsIgnoreCase("admin")) {
                                startActivity(new Intent(getApplicationContext(), Adminshome.class));

                            } else if (sh.getString("usertype", "").equalsIgnoreCase("student")) {
                                startActivity(new Intent(getApplicationContext(), Adminshome.class));

                            }
                            }
                         } catch(Exception e){

                            Toast.makeText(getApplicationContext(), "kkkkkkkkkkkkkkkkkkkkkkkkk", Toast.LENGTH_SHORT).show();
                            Log.d("pearl", "failed" + e);

                        }
                    }


            }
        });




    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }
}