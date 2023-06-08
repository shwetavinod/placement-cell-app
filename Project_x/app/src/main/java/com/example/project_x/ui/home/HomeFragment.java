package com.example.project_x.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_x.A_alumni;
import com.example.project_x.Adminshome;
import com.example.project_x.Chathere;
import com.example.project_x.Login;
import com.example.project_x.Manage_post;
import com.example.project_x.R;
import com.example.project_x.Student_view_applied;
import com.example.project_x.Student_view_post;
import com.example.project_x.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    ImageView i1,i2,i3,i4,i5,i6,i7,i8;

    private FragmentHomeBinding binding;
SharedPreferences sh;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




//        sh = PreferenceManager.getDefaultSharedPreferences(Context);
//        if(Login.ut.equals("admin")) {

//             i1= root.findViewById(R.id.im1);
//            i1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getContext(), A_alumni.class));
//                }
//            });
//             i2 = root.findViewById(R.id.img9);
//
//            i2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getContext(), A_alumni.class));
//                }
//            });
//             i3 = root.findViewById(R.id.chat);
//
//            i3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getContext(), Chathere.class));
//                }
//            });
//             i4 = root.findViewById(R.id.logout);
//
//            i4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getContext(), A_alumni.class));
//                }
//            });
//
////        if(Login.ut.equals("student")) {
//
////            i1.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    startActivity(new Intent(getContext(), Student_view_post.class));
////                }
////            });
//             i5 = root.findViewById(R.id.a_img1);
//
//            i5.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getContext(), Student_view_applied.class));
//                }
//            });
//             i6 = root.findViewById(R.id.a_img1);
//
//            i6.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getContext(), A_alumni.class));
//                }
//            });
//             i7 = root.findViewById(R.id.a_img1);
//
//            i7.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getContext(), A_alumni.class));
//                }
//            });
//
//            i8 = root.findViewById(R.id.a_img1);
//
//            i8.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(getContext(), A_alumni.class));
//                }
//            });

//            if (sh.getString("usertype", "").equalsIgnoreCase("admin")) {
//
//
//                i1.setVisibility(View.VISIBLE);
//                i2.setVisibility(View.VISIBLE);
//                i3.setVisibility(View.VISIBLE);
//                i4.setVisibility(View.VISIBLE);
//                i5.setVisibility(View.GONE);
//                i6.setVisibility(View.GONE);
//                i7.setVisibility(View.GONE);
//                i8.setVisibility(View.GONE);
//
//
//
//            } else if (sh.getString("usertype", "").equalsIgnoreCase("student")) {
//
//                i1.setVisibility(View.GONE);
//                i2.setVisibility(View.GONE);
//                i3.setVisibility(View.GONE);
//                i4.setVisibility(View.GONE);
//                i5.setVisibility(View.VISIBLE);
//                i6.setVisibility(View.VISIBLE);
//                i7.setVisibility(View.VISIBLE);
//                i8.setVisibility(View.VISIBLE);
//
//
//            }
//



//
//        else if Login.ut.equals("student"){
//                    }
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }

}