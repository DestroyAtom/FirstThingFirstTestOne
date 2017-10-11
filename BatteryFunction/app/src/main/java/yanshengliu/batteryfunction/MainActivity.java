package yanshengliu.batteryfunction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private TextView statusTextView,taskTextView;
    private Button taskBtn;
    private BatteryManager mBatteryManager;
    private Intent batteryStatus;
    private IntentFilter ifilter;
    private float batteryPct,initial;
    private String taskInfo;
    private int bNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskBtn=(Button) findViewById(R.id.taskBtn);
        taskTextView =(TextView) findViewById(R.id.taskTextView);
        statusTextView=(TextView) findViewById(R.id.statusTextView);
        mBatteryManager=(BatteryManager) getSystemService(Context.BATTERY_SERVICE);

        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatus = this.registerReceiver(null,ifilter);
        registerReceiver(new PowerConnectionReceiver(), ifilter);
        bNum=0;
    }

    @Override
    protected void onResume(){
        super.onResume();
        taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial=batteryPct;
                if(bNum==0){
                    taskInfo="Normal usage of mobile phone for 10 minutes:\n";
                }else if(bNum==1){
                    taskInfo+="\nUsing GPS for 10 minutes:\n";
                }else if(bNum==2){
                    taskInfo+="\nUsing WiFi for 10 minutes:\n";
                }
                if(bNum<=2){
                    taskInfo=taskInfo+"Initial level: "+initial+"%\n";
                    taskTextView.setText(taskInfo);
                    new TimeCount(5000,1000).start();
                }else{
                    taskInfo=taskInfo+"Mission Complete!\n";
                    taskTextView.setText(taskInfo);
                }

            }
        });
    }

    public class PowerConnectionReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("zhazah","zhazhazhazha");
            statusTextView.setText("Yes!!!!");
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            int level = batteryStatus.getIntExtra(mBatteryManager.EXTRA_LEVEL,-1);
            int scale = batteryStatus.getIntExtra(mBatteryManager.EXTRA_SCALE,-1);
            batteryPct = level / (float)scale*100;
            String result="Current level of battery is: "+batteryPct;
            if(isCharging){
                if(usbCharge){
                    result+="%\nMobile is charging via USB";
                }
                else{
                    result+="\nMobile is charging via AC";
                }
            }
            else{
                result+="\nMobile is not charging";
            }
            Log.e("Battery",result);
            statusTextView.setText(result);
        }
    }


    private class TimeCount extends CountDownTimer{

        private TimeCount(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            taskInfo+="*";
            taskTextView.setText(taskInfo);
        }

        @Override
        public void onFinish() {
            taskInfo=taskInfo.replace("*","");
            float finalLevel=batteryPct;
            taskInfo=taskInfo+"Final level: "+finalLevel+"%\n";
            float consumedLevel=initial-finalLevel;
            taskInfo=taskInfo+"Consumed battery: "+consumedLevel+"%\n";
            taskTextView.setText(taskInfo);
            PopupDialog();
        }
    }

    private void PopupDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        if(bNum==0){
            builder.setMessage("Open GPS Function And Press Task");
            bNum++;
        }else if(bNum==1){
            builder.setMessage("Open WiFi Function And Press Task");
            bNum++;
        }else{
            builder.setMessage("Mission Complete!");
            bNum++;
        }
        builder.setPositiveButton("sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

}
