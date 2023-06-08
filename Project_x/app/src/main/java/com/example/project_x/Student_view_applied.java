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

public class Student_view_applied extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String [] exam_id,post,details,qualification,postid,val;
    String method="";
    String namespace="http://dbcon/";
    String soapAction="";
    public static String e_id,p_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_applied);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView) findViewById(R.id.lv18);
        l1.setOnItemClickListener(this);
        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method="student_view_applied";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
            sop.addProperty("logid",Login.logid);
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.setOutputSoapObject(sop);

            HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
            hp.call(soapAction, env);
            String result = env.getResponse().toString();
            Toast.makeText(getApplicationContext(), "ddddd"+result, Toast.LENGTH_LONG).show();

            if (result.equals("na")) {
                Toast.makeText(getApplicationContext(), "No posts to show you.!", Toast.LENGTH_LONG).show();
            } else {
                String[] temp1 = result.split("\\$");
                if (temp1.length > 0) {
                    inItArraySize(temp1.length);
                    for (int z = 0; z < temp1.length; z++) {
                        String[] temp2 = temp1[z].split("\\#");
//                        Toast.makeText(getApplicationContext(), "mmmmmm", Toast.LENGTH_LONG).show();
                        exam_id[z]=temp2[0];
                        post[z] = temp2[1];
                        details[z] = temp2[2];
                        qualification[z]=temp2[3];
                        postid[z]=temp2[4];
                        val[z] = "Post : "+post[z]+"\nDetails : "+details[z]+"\nQualification : "+qualification[z];
                    }
                }
                ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                l1.setAdapter(aa);
            }
        } catch (Exception e) { }


    }

    private void inItArraySize(int length) {
        exam_id=new String[length];
        post = new String[length];
        details=new String[length];
        qualification = new String[length];
        postid= new String[length];
        val = new String[length];
    }

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Adminshome.class);
        startActivity(b);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        e_id = exam_id[i];
        p_id=postid[i];

        final CharSequence[] items1 = {"View Exam", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Student_view_applied.this);
        builder.setItems(items1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items1[item].equals("View Exam")) {

                    startActivity(new Intent(getApplicationContext(), Student_view_exams.class));
                } else if (items1[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

}
