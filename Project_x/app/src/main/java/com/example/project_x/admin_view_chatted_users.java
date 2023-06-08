package com.example.project_x;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class admin_view_chatted_users extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView l1;
    SharedPreferences sh;
    String[] fname, lname, phone, email,imgs, logid, val;
    String method = "";
    String namespace = "http://dbcon/";
    String soapAction = "";
    public static String log_id,img,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_admin_view_chatted_users);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = (ListView) findViewById(R.id.lv10);
        l1.setOnItemClickListener(this);
        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method = "view_chatted_user";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
//                sop.addProperty("join_req_id",Company_view_joinrequest.join_req_id);
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.setOutputSoapObject(sop);

            HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
            hp.call(soapAction, env);

            String result = env.getResponse().toString();
//            Toast.makeText(getApplicationContext(), "ddddd"+result, Toast.LENGTH_LONG).show();
            if (result.equals("na")) {
                Toast.makeText(getApplicationContext(), "No posts to show you.!", Toast.LENGTH_LONG).show();
            } else {
                String[] temp1 = result.split("\\$");
                if (temp1.length > 0) {
                    inItArraySize(temp1.length);
                    for (int z = 0; z < temp1.length; z++) {
                        String[] temp2 = temp1[z].split("\\#");

                        logid[z] = temp2[0];
                        fname[z] = temp2[1];
                        imgs[z]=temp2[2];





//                        val[z] = "first name : " + fname[z] ;
                    }
                }
//                ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
//                l1.setAdapter(aa);



                CustomUser a = new CustomUser(this, fname);
                l1.setAdapter(a);
            }
        } catch (Exception e) {
        }


    }

    private void inItArraySize(int length) {

        logid = new String[length];
        fname = new String[length];
        lname = new String[length];
        imgs=new String[length];

        val = new String[length];
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        log_id = logid[i];
        img=imgs[i];
        name=fname[i];
        SharedPreferences.Editor ed = sh.edit();
        ed.putString("receiver_id", log_id);
        ed.commit();

        final CharSequence[] items1 = {"Chat", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(admin_view_chatted_users.this);
        builder.setItems(items1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items1[item].equals("Chat")) {

                    startActivity(new Intent(getApplicationContext(), Chathere.class));
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
