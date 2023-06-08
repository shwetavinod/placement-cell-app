package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Student_apply extends AppCompatActivity {
    EditText e1, e2, e3, e4;
    Button b1;
    String post, qualification, postfor, date;
    ListView l1;
    String[] pst, quali, pstfor, dte, pid, val;
    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";
    String url = "";
    SharedPreferences sh;
    DatePickerDialog datePickerDialog;
    public static String logid, p_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_apply);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid = sh.getString("logid", "");

        e1 = (EditText) findViewById(R.id.post);
        e2 = (EditText) findViewById(R.id.qualification);
        b1 = findViewById(R.id.button2);

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

        } catch (Exception e) {
// TODO: handle exception
        }
        url = sh.getString("url", "");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post = e1.getText().toString();
                qualification = e2.getText().toString();



                if (post.equalsIgnoreCase("")) {
                    e1.setError("");
                    e1.setFocusable(true);
                } else if (qualification.equalsIgnoreCase("")) {
                    e2.setError("");
                    e2.setFocusable(true);
                }

//                Toast.makeText(Registration.this, "", Toast.LENGTH_SHORT).show();
                else {

                    try {
                        method = "Apply";
                        soapAction = namespace + method;
                        SoapObject sop = new SoapObject(namespace, method);
                        sop.addProperty("pid",Student_view_post.p_id);

                        sop.addProperty("logid", Login.logid);
                        sop.addProperty("Post", post);
                        sop.addProperty("Qualification", qualification);


                        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        env.setOutputSoapObject(sop);
                        // Toast.makeText(getApplicationContext(), "uuuuuuu", Toast.LENGTH_SHORT).show();
                        HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
                        hp.call(soapAction, env);
                        // Toast.makeText(getApplicationContext(), "kkkkkkkkk", Toast.LENGTH_SHORT).show();

                        String result = env.getResponse().toString();
                        Toast.makeText(getApplicationContext(), Login.logid, Toast.LENGTH_SHORT).show();
                        if (result.equals("failed")) {
                            Toast.makeText(getApplicationContext(), "Applied Failed.!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), " Applied Successfully.!", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), Adminshome.class));
                        }
                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Adminshome.class);
        startActivity(b);
    }
}