package com.example.backsound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.net.URI;

public class ServiceSound extends Service{

    private MediaPlayer player;

    public ServiceSound() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String uri = intent.getStringExtra("URI");
        Log.i("EDWARD", uri);
        Uri u = Uri.fromFile(new File(uri));
        player = MediaPlayer.create(this, u);
        Log.i("EDWARD", Settings.System.DEFAULT_RINGTONE_URI.getPath());
        // start the player
        player.start();
        Toast.makeText(this, "Service Started :) ", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stopping the player when service is destroyed
        player.stop();
        Toast.makeText(this, " :( Service Stopped!!!", Toast.LENGTH_LONG).show();
    }
}
