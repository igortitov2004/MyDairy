package com.example.myowndairy.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myowndairy.HomePage.HomeFragment;
import com.example.myowndairy.Interfaces.SetLanguageInterface;
import com.example.myowndairy.Interfaces.SetThemeInterface;
import com.example.myowndairy.R;
import com.example.myowndairy.SettingsPage.SettingsFragment;
import com.example.myowndairy.TasksPage.TaskFragment;
import com.example.myowndairy.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SetLanguageInterface, SetThemeInterface {
    private ActivityMainBinding binding;

    private SharedPreferences sharedPreferences;


    private HomeFragment homeFragment;
    private TaskFragment taskFragment;
    private SettingsFragment settingsFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        homeFragment = new HomeFragment();
        taskFragment = new TaskFragment();
        settingsFragment = new SettingsFragment();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



//        createNotificationChannel();

        loadLocale();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MenuItem startItem = binding.bottomNavigationView.getMenu().findItem(R.id.homeMainBottom);
        startItem.setChecked(true);


        setTheme();


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(R.id.homeMainBottom == item.getItemId()){
                replaceFragment(homeFragment);
            }
            if(R.id.taskMainBottom == item.getItemId()){
                replaceFragment(taskFragment);
            }
            if(R.id.settingsMainBottom == item.getItemId()){
                replaceFragment(settingsFragment);
            }
            return true;
        });
//        new CountDownTimer(500, 10) { //Set Timer for 1 seconds
//            public void onTick(long millisUntilFinished) {
//            }
//
//            @Override
//            public void onFinish() {
//
//                replaceFragment(homeFragment);
//            }
//        }.start();
        replaceFragment(homeFragment);
    }







    public void replaceFragment(Fragment fragment){
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frame_layout,fragment).commit();
    }





    @Override
    public void setLocal(String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }

    private void loadLocale(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = sharedPreferences.getString("My_Lang","");
        setLocal(language);
    }

    @Override
    public void setTheme() {
        boolean themeMode;
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        themeMode = sharedPreferences.getBoolean("night",false);
        if(themeMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }


    }
}
