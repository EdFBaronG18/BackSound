package com.example.backsound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.EnvironmentCompat;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<URI> list;
    private int current;
    private Button back;
    private Button next;
    private Intent intent;
    private Button play;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        File folder = new File("/storage/sdcard/music/");
        String path = "/system/media/audio/ringtones";
        folder = new File(path);
        File[] l = folder.listFiles();

        current = 0;
        Log.i("EDWARD", "HERE");
        Log.i("EDWARD", folder.getAbsolutePath());
        Log.i("EDWARD", l.length + "");

        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        LinearLayout linearLayout = findViewById(R.id.list);
        for(final File e: l){
            list.add(e.toURI());
            Button button = new Button(this);
            button.setText(e.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(intent != null)
                        stopService(intent);
                    intent = new Intent(v.getContext(), ServiceSound.class);
                    intent.putExtra("URI", e.getPath());
                    startService(intent);
                    play.setEnabled(false);
                    stop.setEnabled(true);
                }
            });
            linearLayout.addView(button);
        }


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               playSound();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ServiceSound.class);
                stopService(intent);
                play.setEnabled(true);
                stop.setEnabled(false);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent != null)
                    stopService(intent);
                current = (current + 1) % list.size();
                playSound();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent != null)
                    stopService(intent);
                current = (current + list.size() - 1) % list.size();
                playSound();
            }
        });
    }

    public void playSound(){
        intent = new Intent(this, ServiceSound.class);
        intent.putExtra("URI", list.get(current).getPath());
        startService(intent);
        play.setEnabled(false);
        stop.setEnabled(true);
    }
}
