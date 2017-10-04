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
            float tempx=Float.parseFloat(df.format(sensorEvent.values[0]));
            float tempy= Float.parseFloat(df.format(sensorEvent.values[1]));
            float tempz=Float.parseFloat(df.format(sensorEvent.values[2]));
            String X=""+tempx;
            String Y=""+tempy;
            String Z=""+tempz;
            double direction= (float) 0.0;
            if(sensorEvent.values[0]>0){
                direction=270+Math.atan(sensorEvent.values[1]/sensorEvent.values[0])*108/3.1415926;
            }
            else if(sensorEvent.values[0]<0){
                direction=90+Math.atan(sensorEvent.values[1]/sensorEvent.values[0])*108/3.1415926;
            }
            else if(sensorEvent.values[0]==0 && sensorEvent.values[1]>0){
                direction=0;
            }
            else if(sensorEvent.values[0]==0 && sensorEvent.values[1]<0){
                direction=180;
            }
            String temp="Exact Heading: "+direction;
            resultTextView.setText(temp);
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
