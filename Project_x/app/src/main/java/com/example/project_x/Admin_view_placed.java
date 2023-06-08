package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintJob;
import android.print.PrintManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Admin_view_placed extends AppCompatActivity {
    ListView l1;
    String[] pid, fname, lname, work, val;

    String namespace = "http://dbcon/";
    String method = "";
    String soapAction = "";
    String url = "";
    SharedPreferences sh;
    public static String logid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_placed);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        l1 = (ListView) findViewById(R.id.lv167);
        try {

//            Toast.makeText(getApplicationContext(), "Haiiii", Toast.LENGTH_LONG).show();
            method = "view_placed";
            soapAction = namespace + method;
            SoapObject sop = new SoapObject(namespace, method);
//                sop.addProperty("join_req_id",Company_view_joinrequest.join_req_id);
//            sop.addProperty("Post_id",Manage_post.p_id);
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


                        fname[z] = temp2[0];
                        lname[z] = temp2[1];
                        work[z] = temp2[2];

                        val[z] = "First Name: " + fname[z] + "\nLast Name: " + lname[z] + "\nWork:" + work[z];
                    }
                }
                ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                l1.setAdapter(aa);
            }
        } catch (Exception e) {
        }


    }


    private void inItArraySize(int length) {


        fname = new String[length];
        lname = new String[length];
        work = new String[length];

        val = new String[length];
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), Adminshome.class);
        startActivity(b);
    }
}
//    // Get a reference to the system's print service
//    PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
//
//    // Set the job name and description
//    String jobName = "My Document";
//    PrintDocumentAdapter printAdapter = new PrintDocumentAdapter() {
//        @Override
//        public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
//            // Write the document content to the output stream
//            try {
//                OutputStream outputStream = new FileOutputStream(destination.getFileDescriptor());
//                // Write the content of the document to the output stream
//                outputStream.write("Hello, world!".getBytes());
//                callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
//            } catch (IOException e) {
//                // Handle the error
//            }
//        }
//
//        @Override
//        public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
//            // Respond to layout requests by providing the content size
//            if (cancellationSignal.isCanceled()) {
//                callback.onLayoutCancelled();
//                return;
//            }
//            PrintDocumentInfo info = new PrintDocumentInfo.Builder(jobName).setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
//            callback.onLayoutFinished(info, true);
//        }
//    };
//
//    // Create a print job with the job name and print adapter
//    PrintJob printJob = printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
//
//    // Get a reference to the system's print service
//    PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
//
//    // Set the job name and description
//    String jobName = "My Page";
//    PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
//
//    // Create a print job with the job name and print adapter
//    PrintJob printJob = printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
//}