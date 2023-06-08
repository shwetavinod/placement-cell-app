package com.example.project_x;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Student_view_questions extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    Button b1;
    String[] exam_id,question_id,question,val;
    SharedPreferences sh;
    public static String qid,e_id;
    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_questions);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView) findViewById(R.id.lv76);
        l1.setOnItemClickListener(this);
        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method = "Student_view_question";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
            sop.addProperty("logid",sh.getString("logid",""));
            sop.addProperty("exam_id",exam_id);
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
                        question_id[z]=temp2[0];
                        exam_id[z]=temp2[1];
                        question[z]=temp2[2];




                        val[z] = "Question: " + question[z] ;
                    }
                }
                ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                l1.setAdapter(aa);
            }
        } catch (Exception e) {
        }


    }


    private void inItArraySize(int length) {
        question_id = new String[length];
        question = new String[length];

        val = new String[length];
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        qid=question_id[i];
e_id=exam_id[i];



        final CharSequence[] items = {"View Options","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Student_view_questions.this);
        builder.setTitle("Verify!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Options"))
                {
                    startActivity(new Intent(getApplicationContext(),Student_view_options.class));

                }

                else if (items[item].equals("Cancel")) {
                }
                dialog.dismiss();
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
