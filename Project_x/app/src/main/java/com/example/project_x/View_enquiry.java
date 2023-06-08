package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class View_enquiry extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String [] eid,user,enquiry,reply,date,val;
    String method="";
    String namespace="http://dbcon/";
    String soapAction="";
    public static String e_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_enquiry);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView) findViewById(R.id.lv1);
        l1.setOnItemClickListener(this);
        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method="view_enquiry";
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
                        user[z] = temp2[1];
                        enquiry[z] = temp2[2];
                        reply[z]=temp2[3];
                        date[z]=temp2[4];



                        val[z] = "User name : "+user[z]+"\nEnquiry : "+enquiry[z]+"\nReply : "+reply[z]+"\nDate: "+date[z]  ;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        e_id = eid[i];

        final CharSequence[] items1 = {"Send Reply", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(View_enquiry.this);
        builder.setItems(items1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items1[item].equals("Send Reply")) {

                    startActivity(new Intent(getApplicationContext(), Send_reply.class));
                } else if (items1[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Adminshome.class);
        startActivity(b);
    }
    }

