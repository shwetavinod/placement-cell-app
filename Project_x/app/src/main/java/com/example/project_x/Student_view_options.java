package com.example.project_x;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Student_view_options extends Activity {

    RadioButton r1,r2,r3,r4,r5;
    TextView t1;
    String[] answer_id,option,exam_id,qstnid,qstn,statuss;
    public static String[] qstnid1,qstn1;
    Button b1;

    SharedPreferences sh;

    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";
    String url = "";

    public static String correct,val,mark,answer_ids,question,qids,questions,questionids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_options);

        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        r1=(RadioButton)findViewById(R.id.radio0);
        r2=(RadioButton)findViewById(R.id.radio1);
        r3=(RadioButton)findViewById(R.id.radio2);
        r4=(RadioButton)findViewById(R.id.radio3);
        r5=(RadioButton)findViewById(R.id.radio4);
        b1=(Button)findViewById(R.id.button1);

        r1.setVisibility(View.GONE);
        r2.setVisibility(View.GONE);
        r3.setVisibility(View.GONE);
        r4.setVisibility(View.GONE);
        r5.setVisibility(View.GONE);


        t1=(TextView)findViewById(R.id.textView2);


//        JsonReq JR = new JsonReq();
//        JR.json_response = (JsonResponse) User_view_options.this;
//        String q = "/user_view_qstn?sub_id="+UserViewQuizSub.subject_id+"&log_id="+sh.getString("log_id","");
//        q = q.replace(" ", "%20");
//        JR.execute(q);

        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method = "Student_view_questions";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
            sop.addProperty("logid",sh.getString("logid",""));
            sop.addProperty("exam_id",Student_view_exam.e_id);
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.setOutputSoapObject(sop);

            HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
            hp.call(soapAction, env);
//            Toast.makeText(getApplicationContext(), "ddddd", Toast.LENGTH_LONG).show();
            String result = env.getResponse().toString();
            if (result.equals("na")) {
                Toast.makeText(getApplicationContext(), "Already attend the Exam.!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Student_view_exam.class));

            } else {
                String[] temp1 = result.split("\\$");
                if (temp1.length > 0) {
                    qstnid1 = new String[temp1.length];
                    for (int z = 0; z < temp1.length; z++) {
                        String[] temp2 = temp1[z].split("\\#");
                            qstnid1[z]=temp2[0];


                    }
                    try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
                        method = "Student_view_options";
                        soapAction = namespace + method;
//                        String q = "/user_view_options?sub_id="+UserViewQuizSub.subject_id+"&log_id="+sh.getString("log_id","")+"&q_id="+qstnid1[0];

                        SoapObject sop1 = new SoapObject(namespace, method);
                        sop1.addProperty("logid",sh.getString("logid",""));
                        sop1.addProperty("q_id",qstnid1[0]);
                        sop1.addProperty("eid",Student_view_exam.e_id);
                        SoapSerializationEnvelope env1 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        env1.setOutputSoapObject(sop1);

                        HttpTransportSE hp1 = new HttpTransportSE(sh.getString("url", ""));
                        hp1.call(soapAction, env1);
//            Toast.makeText(getApplicationContext(), "ddddd", Toast.LENGTH_LONG).show();
                        String result1 = env1.getResponse().toString();
                        Toast.makeText(getApplicationContext(), "ddddd"+result1, Toast.LENGTH_LONG).show();
                        if (result1.equals("na")) {
                            Toast.makeText(getApplicationContext(), "No .!", Toast.LENGTH_LONG).show();
                        } else {
                            String[] mainsss = result1.split("\\^");

                            String[] temp13 = mainsss[0].split("\\#");
                            if (temp13.length > 0) {
                                questionids = temp13[0];
                                questions = temp13[1];

                                t1.setText(questions);
                            }

                            String[] temp11 = mainsss[1].split("\\$");
                            if (temp11.length > 0) {
//                                inItArraySize(temp11.length);
                                answer_id = new String[temp1.length];
                                option = new String[temp1.length];
                                statuss = new String[temp1.length];
                                int i=0;
                                for (int z = 0; z < temp11.length; z++) {
                                    String[] temp22 = temp11[z].split("\\#");

                                    Toast.makeText(getApplicationContext(), "inside"+temp22[2], Toast.LENGTH_SHORT).show();





//                                    do{
//								qstnid[i]=ja1.getJSONObject(i).getString("qstn_id");
//								qstn[i]=ja1.getJSONObject(i).getString("question");
                                        answer_id[i]=temp22[0];
//                                        exam_id[i]=temp22[1];
                                        option [i]=temp22[1];
                                        statuss[i]=temp22[2];



                                        if(statuss[i].equals("Yes"))
                                        {

                                            correct=option[i];
                                        }
                                        if(i==0)
                                        {
                                            r1.setText(temp22[1]);
                                            r1.setVisibility(View.VISIBLE);
                                        }
                                        if(i==1)
                                        {
                                            r2.setText(temp22[1]);
                                            r2.setVisibility(View.VISIBLE);
                                        }
                                        if(i==2)
                                        {
                                            r3.setText(temp22[1]);
                                            r3.setVisibility(View.VISIBLE);
                                        }
                                        if(i==3)
                                        {
                                            r4.setText(temp22[1]);
                                            r4.setVisibility(View.VISIBLE);
                                        }
                                        if(i==4)
                                        {
                                            r5.setText(temp22[1]);
                                            r5.setVisibility(View.VISIBLE);
                                        }


                                        i++;

//                                    } while(i<=temp22.length);





                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
        }




        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if(r1.isChecked())
                {
                    val=r1.getText().toString();
                    answer_ids=answer_id[0];
                }
                else if(r2.isChecked())
                {
                    val=r2.getText().toString();
                    answer_ids=answer_id[1];
                }
                else if(r3.isChecked())
                {
                    val=r3.getText().toString();
                    answer_ids=answer_id[2];
                }
                else if(r4.isChecked())
                {
                    val=r4.getText().toString();
                    answer_ids=answer_id[3];
                }
                else if(r5.isChecked())
                {
                    val=r5.getText().toString();
                    answer_ids=answer_id[5];
                }

                if(correct.equals(val))
                {
                    mark="1";
                }
                else
                {
                    mark="0";
                }

//                JsonReq JR=new JsonReq();
//                JR.json_response=(JsonResponse)User_view_options.this;
//                String q = "/user_answer?val="+val+"&ans_id="+answer_ids+"&mark="+mark+"&log_id="+sh.getString("log_id", "")+"&q_id="+qids;
//                JR.execute(q);
//                Log.d("pearl",q);

                try {
                    method = "User_answer";
                    soapAction = namespace + method;
                    SoapObject sop = new SoapObject(namespace, method);
                    sop.addProperty("logid", sh.getString("log_id",""));
                    sop.addProperty("qanswer_id",answer_ids);
                    sop.addProperty("mark", mark);
                    sop.addProperty("question_id",questionids);


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
//                        SharedPreferences.Editor ed = sh.edit();
//                        ed.putString("logid", result);
//                        ed.commit();
                        startActivity(new Intent(getApplicationContext(), Student_view_options.class));
                    }
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();

                }
            }

    });



}


    private void inItArraySize(int length) {
//        answer_id=new String[length];
//        option = new String[length];
//        statuss = new String[length];


    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Adminshome.class);
        startActivity(b);
    }

//    @Override
//    public void response(JSONObject jo) {
//        // TODO Auto-generated method stub
//        try {
//            String action=jo.getString("method");
//            if(action.equalsIgnoreCase("user_view_options"))
//            {
//                try{
//                     String status=jo.getString("status");
//                    Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
//                    if(status.equalsIgnoreCase("success")){
//
//                        JSONArray ja1=(JSONArray)jo.getJSONArray("data1");
//                        JSONArray ja2=(JSONArray)jo.getJSONArray("data2");
//
//
//
//                        if(ja1.length()!=0) {
//                            Toast.makeText(getApplicationContext(), "inside", Toast.LENGTH_SHORT).show();
//
//                            qstnid=new String[ja1.length()];
//                            qstn=new String[ja1.length()];
//
//                            questionids=ja1.getJSONObject(0).getString("exercise_id");
//                            questions=ja1.getJSONObject(0).getString("exercise_question");
//
//                            t1.setText(questions);
//
//
////
//                        }
//                        if(ja2.length()!=0) {
//                            Toast.makeText(getApplicationContext(), "inside", Toast.LENGTH_SHORT).show();
//
////							qstnid=new String[ja1.length()];
////							qstn=new String[ja1.length()];
//                            answer_id=new String[ja2.length()];
//                            option=new String[ja2.length()];
//                            statuss=new String[ja2.length()];
//
////							cr.moveToFirst();
//                            int i=0;
//
//
//
//                            do{
////								qstnid[i]=ja1.getJSONObject(i).getString("qstn_id");
////								qstn[i]=ja1.getJSONObject(i).getString("question");
//                                answer_id[i]=ja2.getJSONObject(i).getString("option_id");
//                                option[i]=ja2.getJSONObject(i).getString("option_description");
//                                statuss[i]=ja2.getJSONObject(i).getString("status");
//
//
//
//                                if(statuss[i].equals("Yes"))
//                                {
//
//                                    correct=option[i];
//                                }
//                                if(i==0)
//                                {
//                                    r1.setText(ja2.getJSONObject(i).getString("option_description"));
//                                    r1.setVisibility(View.VISIBLE);
//                                }
//                                if(i==1)
//                                {
//                                    r2.setText(ja2.getJSONObject(i).getString("option_description"));
//                                    r2.setVisibility(View.VISIBLE);
//                                }
//                                if(i==2)
//                                {
//                                    r3.setText(ja2.getJSONObject(i).getString("option_description"));
//                                    r3.setVisibility(View.VISIBLE);
//                                }
//                                if(i==3)
//                                {
//                                    r4.setText(ja2.getJSONObject(i).getString("option_description"));
//                                    r4.setVisibility(View.VISIBLE);
//                                }
//                                if(i==4)
//                                {
//                                    r5.setText(ja2.getJSONObject(i).getString("option_description"));
//                                    r5.setVisibility(View.VISIBLE);
//                                }
//
//
//                                i++;
//
//                            } while(i<ja2.length());
//
//
//
////							Toast.makeText(getApplicationContext(), t1.toString(), Toast.LENGTH_LONG).show();
//
////							question=qstn[0];
////							t1.setText(question);
////
////							qids=qstnid[0];;
//
//
//
////							l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,question));
////
//                        }
//                    }
//                    else if(status.equalsIgnoreCase("Already Attend")){
//
////                        startActivity(new Intent(getApplicationContext(),UserViewQuizSub.class));
//
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
//                    }
//
//                }catch(Exception e)
//                {
//                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//                }
//            }else if(action.equalsIgnoreCase("user_answer"))
//            {
//                try {
//                    String status=jo.getString("status");
//                    if(status.equalsIgnoreCase("success"))
//                    {
//
//                        Toast.makeText(getApplicationContext()," successsssssss", Toast.LENGTH_LONG).show();
//
////						JsonReq JR=new JsonReq();
////						JR.json_response=(JsonResponse)User_view_options.this;
////						String q= "api.php?action=user_view_options&ex_id="+User_view_registered_exam.ex_id+"&q_id="+qstnid1[1]+"&u_id="+sh.getString("login_id", "");
////						q=q.replace(" ", "%20");
////						Toast.makeText(getApplicationContext(),"helloooooooooooo", Toast.LENGTH_SHORT).show();
//
//
//                        startActivity(new Intent(getApplicationContext(),Student_view_options.class));
//                    }
//                    else if(status.equalsIgnoreCase("Already Attend")){
//
//                        startActivity(new Intent(getApplicationContext(),Student_view_options.class));
//
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(), "Submission failed",Toast.LENGTH_LONG ).show();
//                    }
//                } catch (Exception e){
//                    // TODO: handle exception
//                }
//            }
//            else if(action.equalsIgnoreCase("user_view_qstn"))
//            {
//                try{
//                    String status=jo.getString("status");
//                    Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
//                    if(status.equalsIgnoreCase("success")){
//
//                        JSONArray ja1=(JSONArray)jo.getJSONArray("data");
//
//                        qstnid1=new String[ja1.length()];
////						qstn1=new String[ja1.length()];
//
////					     v1=new String[ja1.length()];
//
//
//
//                        for(int i = 0;i<ja1.length();i++)
//                        {
//                            qstnid1[i]=ja1.getJSONObject(i).getString("exercise_id");
////							qstn1[i]=ja1.getJSONObject(i).getString("question");
//
////					        v1[i]="Question  :  "+qstn[i];
//
//
//                        }
//
////						JsonReq JR=new JsonReq();
////						JR.json_response=(JsonResponse)User_view_options.this;
////						String q= "api.php?action=user_view_options&ex_id="+User_view_registered_exam.ex_id+"&q_id="+qstnid1[0]+"&u_id="+sh.getString("login_id", "");
////						q=q.replace(" ", "%20");
////						Toast.makeText(getApplicationContext(),q, Toast.LENGTH_SHORT).show();
//
//
//
////						JR.execute(q);
//
////                        JsonReq JR = new JsonReq();
////                        JR.json_response = (JsonResponse) User_view_options.this;
////                        String q = "/user_view_options?sub_id="+UserViewQuizSub.subject_id+"&log_id="+sh.getString("log_id","")+"&q_id="+qstnid1[0];
////                        q = q.replace(" ", "%20");
////                        JR.execute(q);
//
//
////						question=qstn1[i];
////						t1.setText(question);
////						question=qstn1[0];
////						t1.setText(question);
//
//                        qids=qstnid1[0];
////						String[] separated = qids.split(",");
////
////
//                    }
//                    else if(status.equalsIgnoreCase("Already Attend")){
//                        Toast.makeText(getApplicationContext(), "SSSSSSSSS", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(getApplicationContext(),UserViewQuizSub.class));
//
//                    }
//                    else
//                    {
//
//                        Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
//                    }
//
//                }catch(Exception e)
//                {
//                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//
//        catch(Exception e)
//        {
//            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//        }
//
//    }


}
