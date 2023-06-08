package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Timer;
import java.util.TimerTask;

public class Chathere extends AppCompatActivity {
    EditText ed1;
    ImageView b1;
    String chat;
    ListView l1;
    ImageView iv10;
    String method = "";
    String namespace = "http://dbcon/";
    String soapAction = "";
    String url = "";
    String[] aid, aname, r_id1, msg1;
    String[] msgid, s_id, r_id, message, date, re;

    String aid1, aname1, msg;
    SharedPreferences sh;
//    String soapaction = "";
    String contentcheck, chattedby;

    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_chathere);
        {

            sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            url = sh.getString("url", "");
            ed1 = (EditText) findViewById(R.id.editText1);
            l1 = (ListView) findViewById(R.id.listView1);
            b1 = (ImageView) findViewById(R.id.imageView1);
            iv10 = (ImageView) findViewById(R.id.imageView3);

            TextView t1 = findViewById(R.id.textView1);

            t1.setText(admin_view_chatted_users.name);
            //Toast.makeText(getApplicationContext(), "hii1", Toast.LENGTH_SHORT).show();

            startTimer();
            getChats();

            b1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    chat = ed1.getText().toString();
                    if (chat.equalsIgnoreCase("")) {
                        ed1.setError("Empty Message ");
                        ed1.setFocusable(true);
                    } else {

                        try {
                            method = "chat";
                            soapAction = namespace + method;
//						er_id="+sh.getString("log_id", "")+"&receiver_id="+sh.getString("receiver_id", "")+"&details="+chat
                            SoapObject sop = new SoapObject(namespace, method);
                            sop.addProperty("sender_id", sh.getString("sender_id", ""));
                            sop.addProperty("receiver_id", sh.getString("receiver_id", ""));
                            sop.addProperty("details", chat);

//                            Toast.makeText(getApplicationContext(), " failed.!join_req_id::"+join_req_id, Toast.LENGTH_LONG).show();

                            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                            env.setOutputSoapObject(sop);
                            HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
                            hp.call(soapAction, env);
                            String result = env.getResponse().toString();
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                            if (result.equals("failed")) {
                                Toast.makeText(getApplicationContext(), " failed.!", Toast.LENGTH_LONG).show();
                            } else {
//                        Toast.makeText(getApplicationContext(), " attendance marked  successfully.!", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(getApplicationContext(), Chathere.class));
                            }


                        } catch (Exception e) {

                            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();

                        }


//					JsonReq JR=new JsonReq();
//			        JR.json_response=(JsonResponse) ChatHere.this;
//			        String q = "/chat?sender_id="+sh.getString("log_id", "")+"&receiver_id="+sh.getString("receiver_id", "")+"&details="+chat;
//			        q=q.replace(" ","%20");
//			        JR.execute(q);

                    }
                }
            });
        }
    }


    void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 30000);
    }


    void initializeTimerTask() {
        timerTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        getChats();
                    }
                });
            }
        };
        String pth ="http://"+sh.getString("ip", "")+":8080/SHplacement/images/"+admin_view_chatted_users.img;
        pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

        Log.d("-------------", pth);
        Picasso.with(getApplicationContext())
                .load(pth)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(iv10);
    }



    void getChats() {

//
//			JsonReq JR=new JsonReq();
//	        JR.json_response=(JsonResponse) ChatHere.this;
//	        String q = "/chatdetail?sender_id="+sh.getString("log_id", "")+"&receiver_id="+sh.getString("receiver_id", "");
//	        q=q.replace(" ","%20");
////	        Toast.makeText(getApplicationContext(),q, Toast.LENGTH_SHORT).show();
//	        JR.execute(q);

        try {
            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method = "chatdetail";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
            sop.addProperty("sender_id", sh.getString("sender_id", ""));
            sop.addProperty("receiver_id", sh.getString("receiver_id", ""));
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.setOutputSoapObject(sop);
            HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
            hp.call(soapAction, env);
            String result = env.getResponse().toString();
            if (result.equals("failed")) {
//                Toast.makeText(getApplicationContext(), "No Chat.!", Toast.LENGTH_LONG).show();
            } else {
                String[] temp1 = result.split("\\$");
                if (temp1.length > 0) {

                    s_id = new String[temp1.length];
                    r_id = new String[temp1.length];
                    message = new String[temp1.length];
                    date = new String[temp1.length];

//					 val=new String[ja1.length()];

                    for (int z = 0; z < temp1.length; z++) {
                        String[] temp2 = temp1[z].split("\\#");
                        s_id[z] = temp2[0];
                        r_id[z] = temp2[1];
                        message[z] = temp2[2];
                        date[z] = temp2[3];

//						val[z] = "Student Name : " + fn[z] + "\nPlace : " + pl[z] + "\nPhone : " + ph[z] +"\nEmail:"+ em[z]  +"\n";
                    }
                }
                l1.setAdapter(new Customchat(this, message, s_id, date));
            }
        } catch (Exception e) {
        }

    }


//	@Override
//	public void response(JSONObject jo) {
//		// TODO Auto-generated method stub
//
//		try {
//
//			String method=jo.getString("method");
//			if(method.equalsIgnoreCase("chatdetail")){
//				String status=jo.getString("status");
//				Log.d("pearl",status);
////				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
//				if(status.equalsIgnoreCase("success")){
//
//					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
//
//					 s_id= new String[ja1.length()];
//						r_id=new String[ja1.length()];
//						message=new String[ja1.length()];
//						date=new String[ja1.length()];
//
////					 val=new String[ja1.length()];
//
//
//
//					for(int i = 0;i<ja1.length();i++)
//					{
//
//						s_id[i]=ja1.getJSONObject(i).getString("sender_id");
//						r_id[i]=ja1.getJSONObject(i).getString("receiver_id");
//						message[i]=ja1.getJSONObject(i).getString("message");
//						date[i]=ja1.getJSONObject(i).getString("date_time");
//
//
//
//
////						Toast.makeText(getApplicationContext(),"adsdf"+s_id[i]+"   "+r_id[i], Toast.LENGTH_SHORT).show();
////						val[i]="\n\nTeacher : "+teacher[i]+"\nPlace : "+place[i]+"\nDesignation : "+desig[i]+"\nDate of Joining : "+doj[i]+"\nWork Status : "+yoc[i]+"\nSubject : "+subject[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i];
//	//					Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
//
//					}
////					ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
////					lv1.setAdapter(ar);
//					l1.setAdapter(new Customchat(this, message, s_id,date));
//
//
//
//				}
//
//				else {
//					Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
//
//					}
//				}
//			if(method.equalsIgnoreCase("chat")){
//
//				String status=jo.getString("status");
//				Log.d("pearl",status);
//
//
//				if(status.equalsIgnoreCase("success")){
//
////					 Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_LONG).show();
//					 startActivity(new Intent(getApplicationContext(),ChatHere.class));
//
//				}
//
//			}
//
//
//			}catch(Exception e)
//			{
//			// TODO: handle exception
//
//			  Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
//			}
//
//
//
//	}


    public void onBackPressed() {

        super.onBackPressed();
        if (sh.getString("usertype", "").equalsIgnoreCase("admin")) {
            Intent b = new Intent(getApplicationContext(), Adminshome.class);
            startActivity(b);
        } else if (sh.getString("usertype", "").equalsIgnoreCase("user")) {
            Intent b = new Intent(getApplicationContext(), Adminshome.class);
            startActivity(b);
        }


    }
}