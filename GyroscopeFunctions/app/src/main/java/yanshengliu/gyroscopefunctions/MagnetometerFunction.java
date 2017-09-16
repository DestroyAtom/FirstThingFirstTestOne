package yanshengliu.gyroscopefunctions;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MagnetometerFunction extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView resultTextView,resultTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetometer_function);

        resultTextView= (TextView) findViewById(R.id.resultTextView);
        resultTextView2= (TextView) findViewById(R.id.resultTextView2);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(mGyroscopeListener, mSensor,SensorManager.SENSOR_DELAY_NORMAL);


    }

    final SensorEventListener mGyroscopeListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            java.text.DecimalFormat df= new java.text.DecimalFormat("#.00");
            float temp0=Float.parseFloat(df.format(sensorEvent.values[0]));
            float temp1= Float.parseFloat(df.format(sensorEvent.values[1]));
            float temp2=Float.parseFloat(df.format(sensorEvent.values[2]));
            String X=""+temp0;
            String Y=""+temp1;
            String Z=""+temp2;
//            String X=""+sensorEvent.values[0];
//            String Y=""+sensorEvent.values[1];
//            String Z=""+sensorEvent.values[2];
            resultTextView2.setText("X: "+X+"\nY: "+Y+"\nZ: "+Z);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
