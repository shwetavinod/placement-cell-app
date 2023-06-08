package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Company_reg extends Activity {
    EditText e1,e2,e3,e4,e5,e6,e7;
    Button b1;
    ImageButton e8;

    String cname,place,phone,email,lno,username,password;
    SharedPreferences sh;
    String method="";
    String namespace="http://dbcon/";
    String soapAction="";


    public static byte[] byteArray;
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_reg);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.cname);
        e2=(EditText)findViewById(R.id.place);
        e3=(EditText)findViewById(R.id.phone);
        e4=(EditText)findViewById(R.id.email);
        e5=(EditText)findViewById(R.id.lno);
        e6=(EditText)findViewById(R.id.username);
        e7=(EditText)findViewById(R.id.password);
        e8= (ImageButton) findViewById(R.id.img_re);
        e8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageOption();
            }
        });
        b1=(Button)findViewById(R.id.btn_reg);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cname=e1.getText().toString();
                place=e2.getText().toString();
                phone=e3.getText().toString();
                email=e4.getText().toString();
                lno=e5.getText().toString();
                username=e6.getText().toString();
                password=e7.getText().toString();
                if (cname.equalsIgnoreCase(""))
                {
                    e1.setError("");
                    e1.setFocusable(true);
                }
                else if(place.equalsIgnoreCase(""))
                {
                    e2.setError("");
                    e2.setFocusable(true);
                }
                else if (phone.equalsIgnoreCase(""))
                {
                    e3.setError("");
                    e3.setFocusable(true);
                }
                else if (email.equalsIgnoreCase(""))
                {
                    e4.setError("");
                    e4.setFocusable(true);
                }
                else if (lno.equalsIgnoreCase(""))
                {
                    e5.setError("");
                    e5.setFocusable(true);
                }
                else if (username.equalsIgnoreCase(""))
                {
                    e6.setError("");
                    e6.setFocusable(true);
                }
                else if (password.equalsIgnoreCase(""))
                {
                    e7.setError("");
                    e7.setFocusable(true);
                }
//                Toast.makeText(getApplicationContext(),"company name:"+cname+"place:"+place+"phone:"+phone+"email:"+email+"license:"+lno+"username:"+username+"password:"+password,Toast.LENGTH_LONG).show();

                else{

                    try {
                        method="company_register";
                        soapAction = namespace + method;
                        SoapObject sop = new SoapObject(namespace, method);
                        sop.addProperty("companyname",cname);
                        sop.addProperty("place",place);
                        sop.addProperty("phone",phone);
                        sop.addProperty("email",email);
                        sop.addProperty("licenseno",lno);
                        sop.addProperty("username", username);
                        sop.addProperty("password", password);
                        sop.addProperty("file",encodedImage);

                        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        env.setOutputSoapObject(sop);
                        HttpTransportSE hp = new HttpTransportSE(sh.getString("url",""));
                        hp.call(soapAction, env);
                        String result = env.getResponse().toString();
                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                        if (result.equals("failed")) {
                            Toast.makeText(getApplicationContext(), "Registration failed.!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration success.!", Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor ed = sh.edit();
                            ed.putString("logid", result);
                            ed.commit();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

    }




    private void selectImageOption() {

		/*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Company_reg.this);
        builder.setTitle("Take Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                e8.setImageBitmap(bit);

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
                e8.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






}