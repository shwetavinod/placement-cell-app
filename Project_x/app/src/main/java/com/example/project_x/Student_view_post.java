package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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

public class Student_view_post extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String post, qualification, postfor, date;
    ListView l1;
    String[] pst, quali, pstfor, dte,details,imgs, pid, val;
    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";
    String url = "";
    SharedPreferences sh;
    public static String logid, p_id,p_st,qn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_post2);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = (ListView) findViewById(R.id.lv1);
        l1.setOnItemClickListener(this);
        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method = "student_view_post";
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
                        pid[z] = temp2[0];
                        pst[z] = temp2[1];
                        quali[z] = temp2[2];
                        pstfor[z] = temp2[3];
                        details[z] = temp2[4];
                        dte[z] = temp2[5];
                        imgs[z] = temp2[6];


                        val[z] = "Post: " + pst[z] + "\nQualification: " + quali[z] + "\nPost For : " + pstfor[z] + "\nDate: " + dte[z];
                    }
                }
//                ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
//                l1.setAdapter(aa);
                Admin_cust_post ci=new Admin_cust_post(this,pst,quali,pstfor,details,dte,imgs);
                l1.setAdapter(ci);
            }
        } catch (Exception e) {
        }


    }


    private void inItArraySize(int length) {
        pid = new String[length];
        pst = new String[length];
        quali = new String[length];
        pstfor = new String[length];
        details = new String[length];
        dte = new String[length];
        imgs = new String[length];


        val = new String[length];
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        p_id = pid[i];

        p_st = pst[i];
        qn = quali[i];

        final CharSequence[] items1 = {"View Exam", "Apply", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Student_view_post.this);
        builder.setItems(items1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items1[item].equals("View Exam")) {

                    startActivity(new Intent(getApplicationContext(), Student_view_exam.class));

                }
                if (items1[item].equals("Apply")) {

                    startActivity(new Intent(getApplicationContext(),Student_apply.class));

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

