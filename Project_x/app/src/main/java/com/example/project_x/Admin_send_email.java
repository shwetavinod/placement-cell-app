package com.example.project_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin_send_email extends AppCompatActivity {
EditText e1,e2,e3;
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_send_email);
        b1=findViewById(R.id.btnSend);
        e1=findViewById(R.id.subject);
        e2=findViewById(R.id.content);
        e3=findViewById(R.id.to_email);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject,content,to_email;
                subject=e1.getText().toString();
                content=e2.getText().toString();
                to_email=e3.getText().toString();
                if(subject.equals("")&&content.equals("")&&to_email.equals("")){
                    Toast.makeText(Admin_send_email.this, "All Fields Are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    sendEmail(subject,content,to_email);
                }
            }
        });

    }
    public void sendEmail(String subject,String content,String to_email)
    {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{to_email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose email client:"));
    }
}