package com.example.screentimetracker;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView screenTimeText;
    Button openSettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenTimeText = findViewById(R.id.screenTimeText);
        openSettingsButton = findViewById(R.id.openSettingsButton);

        openSettingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        });

        long millis = getTotalScreenTimeToday();
        long minutes = millis / (1000 * 60);
        screenTimeText.setText("Screen Time Today: " + minutes + " minutes");
    }

    private long getTotalScreenTimeToday() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        long startTime = calendar.getTimeInMillis();

        long totalTime = 0;
        List<UsageStats> stats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

        if (stats != null) {
            for (UsageStats usage : stats) {
                totalTime += usage.getTotalTimeInForeground();
            }
        }

        return totalTime;
    }
                     }
