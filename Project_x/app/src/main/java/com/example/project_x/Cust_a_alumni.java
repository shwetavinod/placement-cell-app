package com.example.project_x;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Cust_a_alumni extends ArrayAdapter<String>  {

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] type;
    private String[] griv;
    private String[] deta;
    private String[] reply;
    private String[] date;





    public Cust_a_alumni(Activity context, String[] type, String[] griv, String[] deta, String[] reply, String[] date) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_cust_aalumni,type);
        this.context = context;

        this.type = type;
        this.griv = griv;
        this.deta = deta;
        this.reply = reply;
        this.date = date;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_cust_aalumni, null, true);
        //cust_list_view is xml file of layout created in step no.2

//	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
        TextView t1=(TextView)listViewItem.findViewById(R.id.names);

//        TextView t2=(TextView)listViewItem.findViewById(R.id.griv);
//			TextView t3=(TextView)listViewItem.findViewById(R.id.det);
//			TextView t4=(TextView)listViewItem.findViewById(R.id.reply);
//			TextView t5=(TextView)listViewItem.findViewById(R.id.date);
        t1.setText("Type: "+type[position]+"\nGrevence: "+griv[position]+"\nReply: "+reply[position]+"\nDate: "+date[position]+"\nDetails: "+deta[position]);
//        t2.setText(grivenc[position]);
//			t3.setText(detail[position]);
//			t4.setText(reply[position]);
//			t5.setText(date[position]);

//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

        return  listViewItem;
    }

    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}