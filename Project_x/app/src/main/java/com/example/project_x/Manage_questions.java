package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class   Manage_questions extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText e1, o1, o2, o3,o4,o5;
    Button b1;
    String qns, opt, date;
    String so;

    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";
    String url = "";
    ImageButton ib1;
    SharedPreferences sh;
    Spinner s1;

    RadioButton r1,r2,r3,r4,r5;
    String as[]={"2","3","4","5"};

    public static String logid, p_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_questions);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1 = (EditText)findViewById(R.id.qn);
        o1 =(EditText)findViewById(R.id.o1);
        o2 =(EditText)findViewById(R.id.o2);
        o3 =(EditText)findViewById(R.id.o3);
        o4 =(EditText)findViewById(R.id.o4);
        o5 =(EditText)findViewById(R.id.o5);
        r1 =(RadioButton) findViewById(R.id.radioButton);
        r2 =(RadioButton) findViewById(R.id.radioButton2);
        r3 =(RadioButton) findViewById(R.id.radioButton3);
        r4 =(RadioButton) findViewById(R.id.radioButton4);
        r5 =(RadioButton) findViewById(R.id.radioButton5);


        s1=(Spinner)findViewById(R.id.spinner);
        s1.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, as);
        s1.setAdapter(aa);
        b1=findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qns = e1.getText().toString();
//                opt = o1.getText().toString();



                if (qns.equalsIgnoreCase("")) {
                    e1.setError("");
                    e1.setFocusable(true);
                }


                else {

                    try {
                        method="Manage_questions";
                        soapAction = namespace + method;
                        SoapObject sop = new SoapObject(namespace, method);
                        sop.addProperty("exam_id",Manageexam.e_id);
                        sop.addProperty("Question", qns);
                        sop.addProperty("ss", so);


                        if(so.equalsIgnoreCase("2")) {
//                            Toast.makeText(getApplicationContext(), "hh"+so, Toast.LENGTH_SHORT).show();
                            sop.addProperty("o1", o1.getText().toString());
                            sop.addProperty("o2", o2.getText().toString());
                            sop.addProperty("o3", 0);
                            sop.addProperty("o4", 0);
                            sop.addProperty("o5", 0);
                            if(r1.isChecked())
                            {
                                sop.addProperty("outs", "1");
                            }
                            else if(r2.isChecked())
                            {
                                sop.addProperty("outs", "2");
                            }

                        }
                        else if(so.equalsIgnoreCase("3")) {
                            sop.addProperty("o1", o1.getText().toString());
                            sop.addProperty("o2", o2.getText().toString());
                            sop.addProperty("o3", o3.getText().toString());
                            sop.addProperty("o4", 0);
                            sop.addProperty("o5", 0);

                            if(r1.isChecked())
                            {
                                sop.addProperty("outs", "1");
                            }
                            else if(r2.isChecked())
                            {
                                sop.addProperty("outs", "2");
                            }
                            else if(r3.isChecked())
                            {
                                sop.addProperty("outs", "3");
                            }
                        }
                        else if(so.equalsIgnoreCase("4")) {
                            sop.addProperty("o1", o1.getText().toString());
                            sop.addProperty("o2", o2.getText().toString());
                            sop.addProperty("o3", o3.getText().toString());
                            sop.addProperty("o4", o4.getText().toString());
                            sop.addProperty("o5", 0);
                            if(r1.isChecked())
                            {
                                sop.addProperty("outs", "1");
                            }
                            else if(r2.isChecked())
                            {
                                sop.addProperty("outs", "2");
                            }
                            else if(r3.isChecked())
                            {
                                sop.addProperty("outs", "3");
                            }
                            else if(r4.isChecked())
                            {
                                sop.addProperty("outs", "4");
                            }

                        }
                        else if(so.equalsIgnoreCase("5")) {
                            sop.addProperty("o1", o1.getText().toString());
                            sop.addProperty("o2", o2.getText().toString());
                            sop.addProperty("o3", o3.getText().toString());
                            sop.addProperty("o4", o4.getText().toString());
                            sop.addProperty("o5", o5.getText().toString());
                            if(r1.isChecked())
                            {
                                sop.addProperty("outs", "1");
                            }
                            else if(r2.isChecked())
                            {
                                sop.addProperty("outs", "2");
                            }
                            else if(r3.isChecked())
                            {
                                sop.addProperty("outs", "3");
                            }
                            else if(r4.isChecked())
                            {
                                sop.addProperty("outs", "4");
                            }
                            else if(r5.isChecked())
                            {
                                sop.addProperty("outs", "5");
                            }
                        }




                        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        env.setOutputSoapObject(sop);
                         Toast.makeText(getApplicationContext(), "uuuuuuu", Toast.LENGTH_SHORT).show();
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
                            startActivity(new Intent(getApplicationContext(), Manage_questions.class));
                        }
                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        so=as[i];
        if(as[i]=="2")
        {
            o1.setVisibility(View.VISIBLE);
            o2.setVisibility(View.VISIBLE);
            o3.setVisibility(View.GONE);
            o4.setVisibility(View.GONE);
            o5.setVisibility(View.GONE);
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);
            r3.setVisibility(View.GONE);
            r4.setVisibility(View.GONE);
            r5.setVisibility(View.GONE);

        }
        else if(as[i]=="3")
        {
            o1.setVisibility(View.VISIBLE);
            o2.setVisibility(View.VISIBLE);
            o3.setVisibility(View.VISIBLE);
            o4.setVisibility(View.GONE);
            o5.setVisibility(View.GONE);
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);
            r3.setVisibility(View.VISIBLE);
            r4.setVisibility(View.GONE);
            r5.setVisibility(View.GONE);

        }
        else if(as[i]=="4")
        {
            o1.setVisibility(View.VISIBLE);
            o2.setVisibility(View.VISIBLE);
            o3.setVisibility(View.VISIBLE);
            o4.setVisibility(View.VISIBLE);
            o5.setVisibility(View.GONE);
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);
            r3.setVisibility(View.VISIBLE);
            r4.setVisibility(View.VISIBLE);
            r5.setVisibility(View.GONE);

        }
        else if(as[i]=="5")
        {
            o1.setVisibility(View.VISIBLE);
            o2.setVisibility(View.VISIBLE);
            o3.setVisibility(View.VISIBLE);
            o4.setVisibility(View.VISIBLE);
            o5.setVisibility(View.VISIBLE);
            r1.setVisibility(View.VISIBLE);
            r2.setVisibility(View.VISIBLE);
            r3.setVisibility(View.VISIBLE);
            r4.setVisibility(View.VISIBLE);
            r5.setVisibility(View.VISIBLE);

        }

        Toast.makeText(getApplicationContext(),"Haiiii",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}