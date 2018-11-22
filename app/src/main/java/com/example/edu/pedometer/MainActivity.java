package com.example.edu.pedometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements SensorEventListener,View.OnClickListener,SeekBar.OnSeekBarChangeListener{
    int steps,threshold;
    float previousY,currentY;
    float acceleration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((SeekBar)findViewById(R.id.seekBar)).setProgress(10);
        threshold = ((SeekBar)findViewById(R.id.seekBar)).getProgress();
        previousY = currentY = steps = 0;
        acceleration = 0.0f;
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        ((Button)findViewById(R.id.buttonReset)).setOnClickListener(this);
      }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        currentY = y;
        if(Math.abs(currentY - previousY) > threshold) {
            steps++;
            ((TextView) findViewById(R.id.textViewStep)).setText(String.valueOf(steps));
        }

        ((TextView)findViewById(R.id.textViewGx)).setText(String.valueOf(x));
        ((TextView)findViewById(R.id.textViewGy)).setText(String.valueOf(y));
        ((TextView)findViewById(R.id.textViewGz)).setText(String.valueOf(z));
        previousY = currentY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonReset){
            steps = 0;
            ((TextView) findViewById(R.id.textViewStep)).setText(String.valueOf(steps));
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        threshold = ((SeekBar)findViewById(R.id.seekBar)).getProgress();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
