package yanshengliu.gyroscopefunctions;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.math.BigDecimal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private Button gyro1Btn,gyro2Btn,magBtn;
    private TextView gyro1TextView,gyro2TextView;

    private static final float NS2S=1.0f/1000000000.0f;
    private float timestamp=0;
    private float[] angle= {0,0,0};
    private int switchBtn=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gyro1TextView=(TextView) findViewById(R.id.gyro1TextView);
        gyro2TextView=(TextView) findViewById(R.id.gyro2TextView);
        gyro1Btn=(Button) findViewById(R.id.gyro1Btn);
        gyro2Btn=(Button) findViewById(R.id.gyro2Btn);
        magBtn=(Button) findViewById(R.id.magBtn);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        magBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MagnetometerFunction.class);
                startActivityForResult(intent,1);
            }
        });
        gyro1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSensorManager.registerListener(mGyroscopeListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        gyro2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchBtn=1;
            }
        });

    }



    final SensorEventListener mGyroscopeListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float dT=(sensorEvent.timestamp-timestamp)*NS2S;
            if (timestamp != 0) {
                //gyro1TextView.setText(X);
                //java.text.DecimalFormat df= new java.text.DecimalFormat("#.00");
//                float temp0=Float.parseFloat(df.format(sensorEvent.values[0]));
//                float temp1= Float.parseFloat(df.format(sensorEvent.values[1]));
//                float temp2=Float.parseFloat(df.format(sensorEvent.values[2]));
////                angle[0]+=sensorEvent.values[0]*dT;
////                angle[1]+=sensorEvent.values[1]*dT;
////                angle[2]+=sensorEvent.values[2]*dT;
//                angle[0]+=temp0*dT;
//                angle[1]+=temp1*dT;
//                angle[2]+=temp2*dT;
                gyro1TextView.setText("X: "+sensorEvent.values[0]+"\nY: "+sensorEvent.values[1]+"\nZ: "+sensorEvent.values[2]);
            }
            timestamp= sensorEvent.timestamp;

            if(switchBtn==1){
                if(sensorEvent.values[2]>0.1 | sensorEvent.values[2]<-0.1){
                    angle[2]+=sensorEvent.values[2]*dT;
                    String temp=""+angle[2];
                    gyro2TextView.setText("Z: "+temp);
                }

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        //nothing except loneliness;
        }
    };
}
