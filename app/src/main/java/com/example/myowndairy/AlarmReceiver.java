package com.example.myowndairy;

import static android.nfc.NfcAdapter.EXTRA_ID;
import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static android.provider.Telephony.Mms.Part.TEXT;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myowndairy.Activity.MainActivity;
import com.example.myowndairy.Model.Tasks;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
    private NotificationCompat.Builder builder;
    public NotificationManagerCompat notificationManagerCompat;


    private boolean isEdit;
    @Override

    public void onReceive(Context context, Intent intent) {
        Bundle arg;
        arg = intent.getExtras();




        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_IMMUTABLE);

         builder = new NotificationCompat.Builder(context, "Dairy")
        .setContentIntent(pendingIntent)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(intent.getStringExtra("TITLE"))
        .setContentText(arg.get("TEXT").toString()+" "+arg.get("TASK").toString())
        .setAutoCancel(true)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManagerCompat = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        isEdit = arg.getBoolean("ISEDIT");


            notificationManagerCompat.notify(m, builder.build());






    }


}
