package com.pinderwagen.r8dash;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.Pane;
import androidx.car.app.model.PaneTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;

import java.util.Random;

class HomeScreen extends Screen {

    private final Handler handler = new Handler();
    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate(); // Redraw the screen with updated data
            handler.postDelayed(this, 50); // Poll every 50 milli
        }
    };
    public HomeScreen(@NonNull CarContext carContext) {
        super(carContext);
        handler.post(updateRunnable);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        Pane.Builder paneBuilder = new Pane.Builder();

        // Add information about the Wi-Fi status
        String bluetoothStatus = getBluetoothStatus();
        paneBuilder.addRow(new Row.Builder()
                .setTitle("Bluetooth Status")
                .addText(bluetoothStatus)
                .build());

        int ringerMode = getOilTemp();
        paneBuilder.addRow(new Row.Builder()
                .setTitle("Oil Temp")
                .addText(String.valueOf(ringerMode))
                .build());

        int apiLevel = getCarContext().getCarAppApiLevel();
        paneBuilder.addRow(new Row.Builder()
                .setTitle("Car App API Level")
                .addText(String.valueOf(apiLevel))
                .build());

        return new PaneTemplate.Builder(paneBuilder.build())
                .setTitle("Phone Status")
                .build();
    }

    private int getOilTemp() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(31) +80;
    }

    private String getBluetoothStatus() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return "Bluetooth not supported";
        } else {
            return bluetoothAdapter.isEnabled() ? "Enabled" : "Disabled";
        }
    }
}
