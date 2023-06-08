  package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

  public class Viewapplied extends AppCompatActivity {
      ListView l1;
      SharedPreferences sh;
      String [] aid,post,details,qualification,val;
      String method="";
      String namespace="http://dbcon/";
      String soapAction="";
      public static String a_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewapplied);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView) findViewById(R.id.lv1);
        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method="viewapplied";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
                sop.addProperty("applied_id",aid);
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
                        aid[z]=temp2[0];
                        post[z] = temp2[1];
                        details[z] = temp2[2];
                        qualification[z]=temp2[3];




                        val[z] = "Post : "+post[z]+"\nDetails : "+details[z]+"\nQualification : "+qualification[z];
                    }
                }
                ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                l1.setAdapter(aa);
            }
        } catch (Exception e) { }


    }

      private void inItArraySize(int length) {
            aid=new String[length];
          post = new String[length];
          details=new String[length];
          qualification = new String[length];
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
