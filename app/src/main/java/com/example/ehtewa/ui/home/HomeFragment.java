package com.example.ehtewa.ui.home;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ehtewa.AboutUs;
import com.example.ehtewa.R;
import com.example.ehtewa.Ratting;
import com.example.ehtewa.recommendation;
import com.example.ehtewa.ui.Consultants.ConsultantsFragment;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView recom ,conv , aboutUs , ratting;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
       recom = root.findViewById(R.id.textView3);
       conv = root.findViewById(R.id.textView2);
       aboutUs = root.findViewById(R.id.aboutUs);
        ratting = root.findViewById(R.id.ratting);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        conv = root.findViewById(R.id.textView2);
        conv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsultantsFragment consultantsFragment = new ConsultantsFragment();
                FragmentManager f1 = getFragmentManager();
                f1.beginTransaction()
                        .replace(R.id.nav_host_fragment, consultantsFragment, consultantsFragment.getTag()).addToBackStack(null).commit();

            }
        });


        recom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), recommendation.class);
                startActivity(i);
            }

        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AboutUs.class);
                startActivity(i);
            }
        });

        ratting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Ratting.class);
                startActivity(i);
            }
        });
        return root;
    }


}