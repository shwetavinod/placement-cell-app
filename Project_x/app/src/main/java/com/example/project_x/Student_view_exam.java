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

public class Student_view_exam extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView l1;
    String[] pid, post, exm, edate, dte, eid, val;

    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";
    String url = "";
    SharedPreferences sh;
    public static String p_id, e_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_exam);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=findViewById(R.id.lv3);
        l1.setOnItemClickListener(this);
        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method = "Student_view_exams";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
//                sop.addProperty("join_req_id",Company_view_joinrequest.join_req_id);
            sop.addProperty("Post_id",Student_view_post.p_id);
            sop.addProperty("exam_id",e_id);
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
                        eid[z] = temp2[0];

                        post[z] = temp2[1];
                        exm[z] = temp2[2];
                        edate[z] = temp2[3];
                        dte[z] = temp2[4];
                        pid[z]= temp2[5];


                        val[z] = "Post: " + post[z] + "\nExam: " + exm[z] + "\nExam Date : " + edate[z] + "\nDate: " + dte[z];
                    }
                }
                ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                l1.setAdapter(aa);
            }
        } catch (Exception e) {
        }


    }


    private void inItArraySize(int length) {
        eid = new String[length];

        post = new String[length];
        exm = new String[length];
        edate = new String[length];
        dte = new String[length];
        pid= new String[length];


        val = new String[length];
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), Adminshome.class);
        startActivity(b);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        e_id=eid[i];
//        Toast.makeText(getApplicationContext(), "No posts to show you.!"+e_id, Toast.LENGTH_LONG).show();

        final CharSequence[] items1 = {"Attend Exam","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Student_view_exam.this);
        builder.setItems(items1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items1[item].equals("Attend Exam")) {

                    startActivity(new Intent(getApplicationContext(), Student_view_options.class));
                }
                else if (items1[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

}
