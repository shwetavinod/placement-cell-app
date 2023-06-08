package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Student_send_enquiry extends AppCompatActivity  {
    EditText e1, e2, e3, e4;
    Button b1;
    String enq;
    ListView l1;
    String [] eid,user,enquiry,reply,date,val;
    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";
    String url = "";
    SharedPreferences sh;

    public static String logid, p_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_send_enquiry);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid = sh.getString("logid", "");
        l1 = findViewById(R.id.lv20);

        e1 = (EditText) findViewById(R.id.enq);
        b1 = findViewById(R.id.button20);

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
                enq = e1.getText().toString();



                if (enq.equalsIgnoreCase("")) {
                    e1.setError("");
                    e1.setFocusable(true);
                }
                else {

                    try {
                        method = "Send_enquiry";
                        soapAction = namespace + method;
                        SoapObject sop = new SoapObject(namespace, method);
                        sop.addProperty("logid", logid);
                        sop.addProperty("Enquiry", enq);


                        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        env.setOutputSoapObject(sop);
                        // Toast.makeText(getApplicationContext(), "uuuuuuu", Toast.LENGTH_SHORT).show();
                        HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
                        hp.call(soapAction, env);
                        // Toast.makeText(getApplicationContext(), "kkkkkkkkk", Toast.LENGTH_SHORT).show();

                        String result = env.getResponse().toString();
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        if (result.equals("failed")) {
                            Toast.makeText(getApplicationContext(), "enquiry not send.!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Enqury send  Successfully.!", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor ed = sh.edit();
                            ed.putString("logid", result);
                            ed.commit();
                            startActivity(new Intent(getApplicationContext(), Adminshome.class));
                        }
                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method="Student_view_enquiry";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
//                sop.addProperty("join_req_id",Company_view_joinrequest.join_req_id);
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.setOutputSoapObject(sop);

            HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
            hp.call(soapAction, env);
//            Toast.makeText(getApplicationContext(), "ddddd", Toast.LENGTH_LONG).show();
            String result = env.getResponse().toString();
            if (result.equals("na")) {
                Toast.makeText(getApplicationContext(), "No posts to show you.!", Toast.LENGTH_LONG).show();
            } else {
                String[] temp1 = result.split("\\$");
                if (temp1.length > 0) {
                    inItArraySize(temp1.length);
                    for (int z = 0; z < temp1.length; z++) {
                        String[] temp2 = temp1[z].split("\\#");
                        eid[z]=temp2[0];

                        enquiry[z] = temp2[1];
                        reply[z]=temp2[2];
                        date[z]=temp2[3];



                        val[z] = "Enquiry : "+enquiry[z]+"\nReply : "+reply[z]+"\nDate: "+date[z]  ;
                    }
                }
                ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                l1.setAdapter(aa);
            }
        } catch (Exception e) { }


    }

    private void inItArraySize(int length) {
        eid=new String[length];
        user = new String[length];
        enquiry=new String[length];
        reply = new String[length];
        date=new String[length];

        val = new String[length];
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Adminshome.class);
        startActivity(b);
    }

}
