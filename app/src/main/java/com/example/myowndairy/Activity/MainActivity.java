package com.example.myowndairy.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;

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

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SetLanguageInterface, SetThemeInterface {
    ActivityMainBinding binding;

    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        createNotificationChannel();


        loadLocale();


        binding = ActivityMainBinding.inflate(getLayoutInflater());



        setContentView(binding.getRoot());




        MenuItem startItem = binding.bottomNavigationView.getMenu().findItem(R.id.homeMainBottom);
        startItem.setChecked(true);

        replaceFragment(new HomeFragment());



        setTheme();

//        Button button = findViewById(R.id.notifButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Calendar calendar = Calendar.getInstance();
////                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.set(Calendar.HOUR_OF_DAY,12);
//                calendar.set(Calendar.MINUTE,10);
//                calendar.set(Calendar.SECOND,0);
//                calendar.set(Calendar.MILLISECOND,0);
////                calendar.set(Calendar.MINUTE,0);
////                calendar.set(Calendar.MILLISECOND,0);
//
//
//
//                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
//
//
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//
//                long curTime = System.currentTimeMillis();
//                long interval = 1000*10;
//
//                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
////                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
//
//
//
//                Toast.makeText(MainActivity.this,"URA",Toast.LENGTH_SHORT).show();
//
//            }
//        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(R.id.homeMainBottom == item.getItemId()){
                replaceFragment(new HomeFragment());
            }
            if(R.id.taskMainBottom == item.getItemId()){
                replaceFragment(new TaskFragment());
            }
            if(R.id.settingsMainBottom == item.getItemId()){
                replaceFragment(new SettingsFragment());
            }
            return true;
        });




    }

//    private void createNotificationChannel() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            CharSequence name = "DairyChannel";
//            String description = "Channel for alarm manager";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("Dairy",name,importance);
//            channel.setDescription(description);
//
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//
//            notificationManager.createNotificationChannel(channel);
//        }
//    }



    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
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
