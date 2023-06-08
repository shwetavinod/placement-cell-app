package com.example.project_x;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.*;
import java.util.Properties;


public class Manage_post extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText e1, e2, e3, e4,e9;
    Button b1;
    String post, qualification, postfor,detail, date;
    ListView l1;
    String[] pst, quali, pstfor, dte, pid,details,imgs, val;
    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";

    String url = "";
    ImageButton e5;
    SharedPreferences sh;
    DatePickerDialog datePickerDialog;
    public static String logid,phone, p_id,phno;

    public static byte[] byteArray;
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_post);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        logid = sh.getString("logid", "");
        l1 = findViewById(R.id.lv2);
        l1.setOnItemClickListener(this);
        e1 = (EditText) findViewById(R.id.post);
        e2 = (EditText) findViewById(R.id.qualification);
        e3 = (EditText) findViewById(R.id.postfor);
        e4 = (EditText) findViewById(R.id.date);
        e9 = (EditText) findViewById(R.id.details);

        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Manage_post.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                e4.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        b1 = findViewById(R.id.button2);


//    @RequiresApi(api = Build.VERSION_CODES.O)


        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

        e5=findViewById(R.id.imageButton);
            e5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectImageOption();
                }
            });
        } catch (Exception e) {
// TODO: handle exception
        }
        url = sh.getString("url", "");

        b1.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
//
                post = e1.getText().toString();
                qualification = e2.getText().toString();
                postfor = e3.getText().toString();
                date = e4.getText().toString();
                detail = e9.getText().toString();


//                sentNoti("7356972545");


                if (post.equalsIgnoreCase("")) {
                    e1.setError("");
                    e1.setFocusable(true);
                } else if (qualification.equalsIgnoreCase("")) {
                    e2.setError("");
                    e2.setFocusable(true);
                } else if (postfor.equalsIgnoreCase("")) {
                    e3.setError("");
                    e3.setFocusable(true);
                } else if (date.equalsIgnoreCase("")) {
                    e4.setError("");
                    e4.setFocusable(true);
                }

//                Toast.makeText(Registration.this, "", Toast.LENGTH_SHORT).show();

                else {

                    try {

                        method = "Manage_post";
                        soapAction = namespace + method;
                        SoapObject sop = new SoapObject(namespace, method);
                        sop.addProperty("logid", logid);
                        sop.addProperty("Post", post);
                        sop.addProperty("Qualification", qualification);
                        sop.addProperty("Postfor", postfor);
                        sop.addProperty("Date", date);
                        sop.addProperty("Details", detail);
                        sop.addProperty("file", encodedImage);

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
                            String[] temp1 = result.split("\\$");
                            for (int z = 0; z < temp1.length; z++) {

                                    String num = temp1[z];
                                    sentNoti(num);
//                                Toast.makeText(getApplicationContext(), num, Toast.LENGTH_SHORT).show();

                                SmsManager sms=SmsManager.getDefault();

                                sms.sendTextMessage(num, null, "Placement cell! A new post Added Check it out",null,null);
                                     }

//
                        }
                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();

                    }
//                    Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
//                    intent.setType("text/plain");
//                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"aswathyalen@gmail.com", "anju@gmail.com", "aswathyalen@gmail.com", "shweta@gmail.com", "aparnabhaasi@gmail.com", "aswathyalenjoby@gmail.com"});
//                    intent.putExtra(Intent.EXTRA_SUBJECT, "New Post Alert");
//                    intent.putExtra(Intent.EXTRA_TEXT, "A new post has been published. Check it out!");
//
//                    startActivity(Intent.createChooser(intent, "Send email"));

//// Fetch the email addresses of the users from the database
//
//                    List<String> emailList = new ArrayList<>();
//                    Cursor cursor = db.rawQuery("SELECT email FROM users", null);
//                    while (cursor.moveToNext()) {
//                        String email = cursor.getString(cursor.getColumnIndex("email"));
//                        emailList.add(email);
//                    }
//
//// Create the Intent object with the email addresses
//                    Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
//                    intent.setType("text/plain");
//                    intent.putExtra(Intent.EXTRA_EMAIL, emailList.toArray(new String[0]));
//                    intent.putExtra(Intent.EXTRA_SUBJECT, "New Post Alert");
//                    intent.putExtra(Intent.EXTRA_TEXT, "A new post has been published. Check it out!");
//
//// Start the email client
//                    startActivity(Intent.createChooser(intent, "Send email"));
//                    In the code above, we fetch t
//                }
                    {

                }



                }
            }
        });


        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method = "view_post";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
//                sop.addProperty("join_req_id",Company_view_joinrequest.join_req_id);
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.setOutputSoapObject(sop);

            HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
            hp.call(soapAction, env);
//            Toast.makeText(getApplicationContext(), "ddddd", Toast.LENGTH_LONG).show();
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
//
                Admin_cust_post ci=new Admin_cust_post(this,pst,quali,pstfor,details,dte,imgs);
                l1.setAdapter(ci);
            }
        } catch (Exception e) {
        }


    }


    public void sentNoti(String phoneNumber){

        String channelId = "my_channel_id";
        String channelName = "My Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(" Notification from placement cell")
                .setContentText("Added a new post")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent = new Intent(this, Manage_post.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        int notificationId = 1;
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, builder.build());

        // Send SMS message to phone number
        SmsManager smsManager = SmsManager.getDefault();
        String smsText = "Notification from placement cell: Added a new post";
        smsManager.sendTextMessage(phoneNumber, null, smsText, null, null);
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

        final CharSequence[] items1 = {"Manage Exam","Delete","Update","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Manage_post.this);
        builder.setItems(items1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items1[item].equals("Manage Exam")) {

                    startActivity(new Intent(getApplicationContext(), Manageexam.class));
                }
                else if (items1[item].equals("Delete")) {

                    try {
                        method = "Delete";
                        soapAction = namespace + method;
                        SoapObject sop = new SoapObject(namespace, method);
                        sop.addProperty("post_id",p_id);

//                            Toast.makeText(getApplicationContext(), " failed.!join_req_id::"+join_req_id, Toast.LENGTH_LONG).show();

                        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        env.setOutputSoapObject(sop);
                        HttpTransportSE hp = new HttpTransportSE(sh.getString("url", ""));
                        hp.call(soapAction, env);
                        String result = env.getResponse().toString();
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                        if (result.equals("failed")) {
                            Toast.makeText(getApplicationContext(), "Sending  failed.!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), " send  successfully.!", Toast.LENGTH_LONG).show();


                        }


                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();

                    }
                }
                if (items1[item].equals("Update")) {

                    startActivity(new Intent(getApplicationContext(), Admin_update_post.class));
                }
                else if (items1[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void selectImageOption() {

		/*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Manage_post.this);
        builder.setTitle("Take Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    //    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
                path = FileUtils.getPath(this, uri);
                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                File fl = new File(path);
                int ln = (int) fl.length();

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                e5.setImageBitmap(bit);

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                e5.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }




    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), Adminshome.class);
        startActivity(b);
    }

}




