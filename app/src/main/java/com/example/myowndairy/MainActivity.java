package com.example.myowndairy;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myowndairy.databinding.ActivityMainBinding;
import com.example.myowndairy.databinding.CustomDialogSettingsBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    Button show;
    Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        MenuItem startItem = binding.bottomNavigationView.getMenu().findItem(R.id.homeMainBottom);
        startItem.setChecked(true);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(R.id.homeMainBottom == item.getItemId()){
                replaceFragment(new HomeFragment());
            }
            if(R.id.calendarMainBottom == item.getItemId()){
                replaceFragment(new CalendarFragment());
            }
            if(R.id.settingsMainBottom == item.getItemId()){

                replaceFragment(new SettingsFragment());




            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}
