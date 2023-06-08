package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;





public class Admin_update_post extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7;
    Button b1;
    String fn,ln,place,phone,email;
    SharedPreferences sh;
    String method="";
    String namespace="http://dbcon/";
    String soapAction="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_post);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1 = (EditText) findViewById(R.id.post);
        e2 = (EditText) findViewById(R.id.qualification);
        e3 = (EditText) findViewById(R.id.postfor);
        e5 = (EditText) findViewById(R.id.date);
//        e4 = (EditText) findViewById(R.id.email);
//        b1 = (Button) findViewById(R.id.email);
//        e6=(EditText)findViewById(R.id.uname);
//        e7=(EditText)findViewById(R.id.pwd);
//        b1=(Button)findViewById(R.id.u_reg);


        try {
            method = "Admin_update_post";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
            sop.addProperty("post_id",Manage_post.p_id);
//                        sop.addProperty("firstname",fn);
//                        sop.addProperty("lastname",fn);
//                        sop.addProperty("place",place);
//                        sop.addProperty("phone",phone);
//                        sop.addProperty("email",email);
//                        sop.addProperty("username", username);
//                        sop.addProperty("password", password);

            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.setOutputSoapObject(sop);
            HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
            hp.call(soapAction, env);
            String result = env.getResponse().toString();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            if (result.equals("failed")) {
                Toast.makeText(getApplicationContext(), " failed.!", Toast.LENGTH_LONG).show();
            } else {
                String[] temp1 = result.split("\\#");
//                            lid=temp1[0];
//                            Toast.makeText(getApplicationContext(), "Login success.!", Toast.LENGTH_LONG).show();
                e1.setText(temp1[0]);
                e2.setText(temp1[1]);
                e3.setText(temp1[2]);
                e4.setText(temp1[3]);
                e5.setText(temp1[4]);

            }
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();

        }
    }



}