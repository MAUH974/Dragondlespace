package myapplication.automation.test.dragondlespace;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity implements SensorEventListener{
    //private JeanMarc jeanMarc;


    private GameScene gameScene;
    public static int deviceWidth, deviceHeight;
    public static int score;
    public static int lives;


    public SensorManager senSensorManager;
    public Sensor senAccelerometer;
    private long lastUpdate = 0;
    private static final int SHAKE_THRESHOLD = 600;
    public static float zPosition, last_zPosition;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        deviceWidth = displayMetrics.widthPixels;
        deviceHeight = displayMetrics.heightPixels;
        score = 0;
        lives = 3;
        zPosition = 0;

        gameScene = new GameScene(this);
        setContentView(gameScene);


        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_GAME);


        Log.d("x y z contienne:", "");



    }
    public void pause() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameScene.pause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameScene.resume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_GAME);

    }

  /*  @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            zPosition = event.values[2];// d'hab


            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                lastUpdate = curTime;

                last_zPosition = zPosition;
                gameScene.getJeanMarc().update();

            }



        }

    }*/
  @Override
  public void onSensorChanged(SensorEvent event) {

      Sensor mySensor = event.sensor;

      if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {

          zPosition = event.values[2];// d'hab
          zPosition -= 9;

          gameScene.getJeanMarc().update();





      }
  }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
