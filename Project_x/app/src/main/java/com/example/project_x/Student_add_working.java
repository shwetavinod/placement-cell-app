package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Student_add_working extends AppCompatActivity {
    EditText e1;
    Button b1;
    String wrk, postfor, date;


    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";
    String url = "";
    ImageButton e5, e6;
    SharedPreferences sh;
    DatePickerDialog datePickerDialog;
    public static String logid, p_id;
    public static byte[] byteArray;
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_working);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid = sh.getString("logid", "");

        e1 = (EditText) findViewById(R.id.wrk);
//        e5=findViewById(R.id.img_re);
//        e5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectImageOption();
//            }
//        });
        e6=findViewById(R.id.resume);
        e6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageOption();
            }
        });
        b1 = findViewById(R.id.button2);

        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

        } catch (Exception e) {
// TODO: handle exception
        }
        url = sh.getString("url", "");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrk = e1.getText().toString();


                if (wrk.equalsIgnoreCase("")) {
                    e1.setError("");
                    e1.setFocusable(true);
                } else {

                    try {
                        method = "Add_work_details";
                        soapAction = namespace + method;
                        SoapObject sop = new SoapObject(namespace, method);
                        sop.addProperty("logid", logid);
                        sop.addProperty("Work", wrk);
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
    }


        private void selectImageOption() {

		/*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

            final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Student_add_working.this);
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



