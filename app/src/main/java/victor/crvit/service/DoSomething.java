package victor.crvit.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import victor.crvit.repositories.LocationRepo;

public class DoSomething extends Service {
    private StringBuilder _stringBuilder;
    private GPSTracker gpsTracker;
    private LocationRepo locationRepo;

    public DoSomething() {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LOCATION", "onStartCommand - " + startId);
        final Context context = this;
        Handler handler = new Handler(this.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getApplicationContext(), _stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                _stringBuilder.delete(0, _stringBuilder.length());
                gpsTracker.canGetLocation();
                Location location = gpsTracker.getLocation();
                if (location != null) {
                    victor.crvit.repositories.Location location1 = new victor.crvit.repositories.Location();
                    location1.Latitude = location.getLatitude();
                    location1.Longitude = location.getLongitude();
                    location1.time = new Date(location.getTime());
                    location1.createdDate = new Date();
                    locationRepo.add(location1);
                    Toast.makeText(getApplicationContext(),"added", Toast.LENGTH_SHORT).show();
                }
                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarm.set(alarm.RTC_WAKEUP,
                        System.currentTimeMillis() + (10 * 500),
                        PendingIntent.getService(context, 0, new Intent(context, DoSomething.class), 0)
                );
            }
        });

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        _stringBuilder = new StringBuilder();
        showIcon();
        showSettings();
        locationRepo = LocationRepo.Create(getApplicationContext());
        gpsTracker = new GPSTracker(getApplicationContext());
//        {
//            @Override
//            public void onLocationChanged(Location location) {
//                Log.d("LOCATION", "onDestroy");
//                _stringBuilder.append(location.getLongitude() + " " + new Date(location.getTime()));
//            }
//        };

        Timer myTimer = new Timer(); // Создаем таймер
        myTimer.schedule(new TimerTask() { // Определяем задачу
            @Override
            public void run() {
                _stringBuilder.append(new Date());
                _stringBuilder.append("\n");
            }
        }, 0L, 1000);

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i("LOCATION", "onDestroy");
        gpsTracker.stopUsingGPS();
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.cancel(PendingIntent.getService(this, 0, new Intent(this, DoSomething.class), 0));
        hideIcon();
        super.onDestroy();
    }

    private void showIcon() {
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.support.v7.appcompat.R.drawable.notification_icon_background)
                .setOngoing(false).build();
        notification.flags |= Notification.FLAG_NO_CLEAR
                | Notification.FLAG_ONGOING_EVENT;
        NotificationManager notifier = (NotificationManager)
                this.getSystemService(this.NOTIFICATION_SERVICE);
        notifier.notify(1, notification);
    }

    private void showSettings() {
        Intent nIntent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.support.v7.appcompat.R.drawable.abc_btn_radio_material)
                .setOngoing(true)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent).build();

        NotificationManager notifier = (NotificationManager)
                this.getSystemService(this.NOTIFICATION_SERVICE);
        notifier.notify(2, notification);
    }

    private void hideIcon() {
        NotificationManager notifier = (NotificationManager)
                this.getSystemService(this.NOTIFICATION_SERVICE);
        notifier.cancel(1);
    }
}
