package com.example.or.imageserviceapp;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class ImageServiceApp extends Service {

    final IntentFilter filter;
    BroadcastReceiver broadcastReceiver;
    public static boolean serviceRunning = false;

    /**
     * Constructor.
     */
    public ImageServiceApp() {
        filter = new IntentFilter();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * When start the server
     * @param intent intent
     * @param flag flag
     * @param startId start id
     * @return the flag
     */
    public int onStartCommand(Intent intent, int flag, final int startId) {
        //Make Toast message
        Toast.makeText(this, "Starting ImageServiceApp", Toast.LENGTH_SHORT).show();

        //Start the reciver (wifi)
        this.broadcastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onReceive(Context context, Intent intent) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (networkInfo != null) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                            //Tranfser Data
                            Log.i("Location", "almost in transfer");

                            //Create the thread that run the transfer
                            new Thread(new Runnable() {
//                                @Override
                                public void run() {
                                    startTransfer();
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {

                                    }
                                }
                            }).start();
                            Log.i("Location", "transfer finished");
                        }
                    }
                }
            }
        };
        //Register reciever
        this.registerReceiver(this.broadcastReceiver, this.filter);
        return START_STICKY;
    }

    /**
     * On destroy func
     */
    @Override
    public void onDestroy() {
        Log.i("Location", "On Destroy");
        serviceRunning = false;
        unregisterReceiver(broadcastReceiver);
        Toast.makeText(this, "Service Ending...",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Transfer data to service
     */
    public void startTransfer() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
        builder.setContentTitle("Transfer pics").setContentText("Process running...")
                .setPriority(NotificationCompat.PRIORITY_LOW).setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText("50%").setProgress(100, 50, false);

        //Build list and update progress bar
        List<File> pics = updatePicsList();
        int size = pics.size();
        builder.setProgress(size, 0, false);
        notificationManager.notify(1, builder.build());
        // When Finished
        builder.setContentText("Transfer Finished!").setProgress(0, 0, false);
        notificationManager.notify(1, builder.build());

        if (pics == null)
            return;

        TCPClient client = new TCPClient();
        int bar = 0;
        client.connect();
            for (int i = 0; i < size; i++) {
                try {
                    client.send(pics.get(i));
                    //update the progress bar
                    bar = bar + 100 / pics.size();
                    builder.setProgress(100, bar, false);
                    notificationManager.notify(1, builder.build());
                } catch (Exception e) {
                    client.closeSocket();
                }
            }
            //in finish
            builder.setProgress(0, 0, false);
            builder.setContentTitle("Finished transfer!");
            builder.setContentText("Finished transfer!");
            notificationManager.notify(1, builder.build());
             //   client.closeSocket();


    }

    /**
     * On create func
     */
    @Override
    public void onCreate() {
        Log.i("OnFunction", "oncreate");
        super.onCreate();
        serviceRunning = true;

        this.filter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        this.filter.addAction("android.net.wifi.STATE_CHANGE");
    }


    /**
     * Add pics from dir to list
     * @param direcotry direcotry
     * @param pics list
     */
    public void traverseDir(File direcotry, List<File> pics) {
        File[] content = direcotry.listFiles();
        List<File> files = Arrays.asList(content);
        for (File file : files) {
            if (file.isDirectory()) {
                traverseDir(file, pics);
            } else if (file.toString().contains(".jpg") ||
                    file.toString().contains(".bmp") ||
                    file.toString().contains(".png") ||
                    file.toString().contains(".gif")) {
                pics.add(file);
            }
        }
    }

    /**
     * Update the pics list by dcim dir
     * @return list
     */
    public List<File> updatePicsList() {
        List<File> pics = new ArrayList<>();
        File dcim = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        if (dcim == null) {
            return pics;
        }
        traverseDir(dcim, pics);
        Log.i("Location", "update list finished");
        return pics;
    }



}
