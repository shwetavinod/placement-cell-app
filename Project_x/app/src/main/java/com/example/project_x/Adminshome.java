package com.example.project_x;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_x.databinding.ActivityAdminshomeBinding;

public class Adminshome extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAdminshomeBinding binding;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminshomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarAdminshome.toolbar);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        binding.appBarAdminshome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_adminshome);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if(sh.getString("usertype","").equals("admin"))
        {
            navigationView.getMenu().findItem(R.id.admin_home).setVisible(true);
            navigationView.getMenu().findItem(R.id.adminmanage_post).setVisible(true);
            navigationView.getMenu().findItem(R.id.adminviewapplied).setVisible(true);
            navigationView.getMenu().findItem(R.id.placed).setVisible(true);
//            navigationView.getMenu().findItem(R.id.email).setVisible(true);
            navigationView.getMenu().findItem(R.id.admin_view_exam_attended).setVisible(true);
            navigationView.getMenu().findItem(R.id.A_alumni).setVisible(true);
            navigationView.getMenu().findItem(R.id.adminviewexams).setVisible(true);
            navigationView.getMenu().findItem(R.id.marklist).setVisible(true);
            navigationView.getMenu().findItem(R.id.chatted_user).setVisible(true);
            navigationView.getMenu().findItem(R.id.adminenquiry).setVisible(true);
            navigationView.getMenu().findItem(R.id.staddwrking).setVisible(true);

//            navigationView.getMenu().findItem(R.id.admin_view_exam_attended).setVisible(true);
            navigationView.getMenu().findItem(R.id.Logout).setVisible(true);
        }else if(sh.getString("usertype","").equals("student")){
            navigationView.getMenu().findItem(R.id.student_view_post).setVisible(true);
            navigationView.getMenu().findItem(R.id.Student_send_enquiry).setVisible(true);
            navigationView.getMenu().findItem(R.id.student_view_applied).setVisible(true);
            navigationView.getMenu().findItem(R.id.chat_admin).setVisible(true);
            navigationView.getMenu().findItem(R.id.Logout).setVisible(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.admin_home)
                {
                    startActivity(new Intent(getApplicationContext(),Ahome.class));
                }
                else if(item.getItemId()==R.id.adminmanage_post)
                {
                    startActivity(new Intent(getApplicationContext(),Manage_post.class));
                }
                else if(item.getItemId()==R.id.marklist)
                {
                    startActivity(new Intent(getApplicationContext(),Admin_view_marklist.class));
                }
                else if(item.getItemId()==R.id.placed)
                {
                    startActivity(new Intent(getApplicationContext(),Admin_view_placed .class));
                }
//                else if(item.getItemId()==R.id.email)
//                {
//                    startActivity(new Intent(getApplicationContext(),Admin_send_email .class));
//                }
                else if(item.getItemId()==R.id.chatted_user)
                {
                    startActivity(new Intent(getApplicationContext(),admin_view_chatted_users.class));
                }
                else if(item.getItemId()==R.id.adminviewapplied)
                {
                    startActivity(new Intent(getApplicationContext(),Student_view_applied.class));
                }
                else if(item.getItemId()==R.id.A_alumni)
                {
                    startActivity(new Intent(getApplicationContext(),A_alumni.class));
                }
                else if(item.getItemId()==R.id.adminenquiry)
                {
                    startActivity(new Intent(getApplicationContext(),View_enquiry.class));
                }
                else if(item.getItemId()==R.id.admin_view_exam_attended)
                {
                    startActivity(new Intent(getApplicationContext(),Admin_view_exam_attended.class));
                }
                else if(item.getItemId()==R.id.adminviewexams)
                {
                    startActivity(new Intent(getApplicationContext(),View_exam.class));
                }
                else if(item.getItemId()==R.id.student_view_post)
                {
                    startActivity(new Intent(getApplicationContext(),Student_view_post.class));
                }
                else if(item.getItemId()==R.id.Student_send_enquiry)
                {
                    startActivity(new Intent(getApplicationContext(),Student_send_enquiry.class));
                }
                else if(item.getItemId()==R.id.student_view_applied)
                {
                    startActivity(new Intent(getApplicationContext(),Student_view_applied.class));
                }
                else if(item.getItemId()==R.id.chat_admin)
                {
                    startActivity(new Intent(getApplicationContext(),Chathere.class));
                }

                else if(item.getItemId()==R.id.staddwrking)
                {
                    startActivity(new Intent(getApplicationContext(),Student_add_working.class));
                }
                else if(item.getItemId()==R.id.Logout)
                {
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }




                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.adminshome, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_adminshome);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}