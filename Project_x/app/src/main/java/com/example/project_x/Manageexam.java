package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Calendar;

public class Manageexam extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText e1, e2, e3, e4;
    ListView l1;
    String[] pid,post, exm, edate, dte,eid,val;
    Button b1;
    String exam,date;
    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";
    String url = "";
    SharedPreferences sh;
    public static String p_id,e_id;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageexam);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1 = (EditText)findViewById(R.id.exam);
        e2 =(EditText)findViewById(R.id.examdate);
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Manageexam.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                e2.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        l1=findViewById(R.id.lv3);
l1.setOnItemClickListener(this);
        b1=findViewById(R.id.button2);

        try {
            if(android.os.Build.VERSION.SDK_INT>9)
            {
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

        } catch (Exception e) {
// TODO: handle exception
        }
        url=sh.getString("url","");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exam = e1.getText().toString();
                date = e2.getText().toString();



                if (exam.equalsIgnoreCase("")) {
                    e1.setError("");
                    e1.setFocusable(true);
                } else if (date.equalsIgnoreCase("")) {
                    e2.setError("");
                    e2.setFocusable(true);

                }

//                Toast.makeText(Registration.this, "", Toast.LENGTH_SHORT).show();
                else {

                    try {
                        method="Manage_exam";
                        soapAction = namespace + method;
                        SoapObject sop = new SoapObject(namespace, method);
                        sop.addProperty("Post_id",Manage_post.p_id);
                        sop.addProperty("Exam", exam);
                        sop.addProperty("Date", date);


                        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        env.setOutputSoapObject(sop);
                        // Toast.makeText(getApplicationContext(), "uuuuuuu", Toast.LENGTH_SHORT).show();
                        HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
                        hp.call(soapAction, env);
                        // Toast.makeText(getApplicationContext(), "kkkkkkkkk", Toast.LENGTH_SHORT).show();

                        String result = env.getResponse().toString();
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        if (result.equals("failed")) {
                            Toast.makeText(getApplicationContext(), "Post Not Added.!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "POst Added Successfully.!", Toast.LENGTH_SHORT).show();
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
            method = "view_exam";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
//                sop.addProperty("join_req_id",Company_view_joinrequest.join_req_id);
            sop.addProperty("Post_id",Manage_post.p_id);
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
                        post[z] = temp2[1];
                        exm[z] = temp2[2];
                        edate[z] = temp2[3];
                        dte[z] = temp2[4];
                        eid[z] = temp2[5];


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
        pid = new String[length];
        post = new String[length];
        exm = new String[length];
        edate = new String[length];
        dte = new String[length];
        eid = new String[length];

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
        p_id = pid[i];
        e_id=eid[i];
        final CharSequence[] items1 = {"Manage Questions","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Manageexam.this);
        builder.setItems(items1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items1[item].equals("Manage Questions")) {

                    startActivity(new Intent(getApplicationContext(), Manage_questions.class));
                }
                else if (items1[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

}


