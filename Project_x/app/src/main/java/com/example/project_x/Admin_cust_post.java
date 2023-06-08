package com.example.project_x;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Admin_cust_post extends ArrayAdapter<String> {

    private Activity context;       //for to get current activity context
    SharedPreferences sh;

    private String[] pst;
    private String[] quali;
    private String[] pstfor;
    private String[] details;
    private String[] dte;

    private String[] imgs;


    public Admin_cust_post(Activity context, String[] post, String[] qualification, String[] postfor, String[] details, String[] date, String[] imgs) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_admin_cust_post, imgs);


        this.context = context;
        this.pst = post;
        this.quali = qualification;
        this.pstfor = postfor;
        this.details = details;
        this.dte = date;

        this.imgs = imgs;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //override getView() method
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_admin_cust_post, null, true);


        //cust_list_view is xml file of layout created in step no.2
        ImageView im = (ImageView) listViewItem.findViewById(R.id.cardImage);
        TextView t1 = (TextView) listViewItem.findViewById(R.id.cardTitle);


        t1.setText("Post:  " + pst[position] + "\nQualification : " + quali[position] + "\npostfor:" + pstfor[position] + "\nDetails:" + details[position] + "\nDate:" + dte[position]);
        sh = PreferenceManager.getDefaultSharedPreferences(getContext());


        String pth = "http://" + sh.getString("ip", "") + ":8080/SHplacement/images/" + imgs[position];
        pth = pth.replace("~", "");


        Log.d("-------------", pth);
        Picasso.with(context)
                .load(pth)
                .placeholder(R.drawable.add)
                .error(R.drawable.arrow).into(im);
        return listViewItem;

    }

    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}
