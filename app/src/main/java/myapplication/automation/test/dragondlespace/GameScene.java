package myapplication.automation.test.dragondlespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import static myapplication.automation.test.dragondlespace.GameActivity.mp;

public class GameScene extends SurfaceView implements Runnable {

    private volatile boolean running;
    private Thread gameThread = null;

    private Life life;
    public JeanMarc jeanMarc;
    private EnemyTroll enemyTroll;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private ArrayList<SpaceDust> spaceDusts;

    public static ArrayList<EnemySlow> enemiesSlow;
    public static ArrayList<EnemyFast> enemiesFast;
    private int indexSlow = 0, indexFast = 0;

    private long curTime, timeOfDeath = 0;



    public GameScene(Context context) {
        super(context);

        surfaceHolder = getHolder();
        paint = new Paint();

        createSpaceDusts();
        life = new Life(context);
        jeanMarc = new JeanMarc(context);
        createEnemies();

    }

    @Override
    public void run() {

        while (running) {
            draw();
            getCollisions();

            control();
        }
    }


    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            paint.setColor(Color.argb(255, 255, 255, 255));
            for (SpaceDust sd : spaceDusts) {
                canvas.drawPoint(sd.getX(), sd.getY(), paint);
            }

            jeanMarc.getCurrentFrame();
            canvas.drawBitmap(jeanMarc.getBitmap(), jeanMarc.getFrameJeanMarc(), jeanMarc.getPositionJeanMarc(), paint);

            spawnEnemies();

            drawLives();

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawLives() {
        for (int i = 0; i < GameActivity.lives; i++) {
            canvas.drawBitmap(life.getBitmap(), life.getX() + i * 40, life.getY(), paint);
        }
    }

    private void drawHUD() {

        paint.setTextSize(40);
        canvas.drawText("Score: " + GameActivity.score, 10, GameActivity.deviceHeight / 16, paint);
        canvas.drawText("VIE: " + GameActivity.lives, 10, GameActivity.deviceHeight *3/ 16, paint);
        canvas.drawText("TEST: " + JeanMarc.test1 + "           " + JeanMarc.test2 + " " + JeanMarc.test3 + " " +JeanMarc.test4, 10, GameActivity.deviceHeight *5/ 16, paint);


        GameActivity.score++;


    }

    private void createSpaceDusts() {
        spaceDusts = new ArrayList<>();
        for (int i = 0; i < 1024; i++) {
            SpaceDust spaceDust = new SpaceDust(GameActivity.deviceWidth, GameActivity.deviceHeight);
            spaceDusts.add(spaceDust);
        }
    }

    private void createEnemies() {
        enemyTroll = new EnemyTroll(getContext());
        enemiesSlow = new ArrayList<>();
        enemiesFast = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            enemiesSlow.add(new EnemySlow(getContext()));
            enemiesFast.add(new EnemyFast(getContext()));
        }
    }

    private void spawnEnemies() {
        drawHUD();
        spawnSlow();
        spawnFast();
        spawnTroll();

    }

    private void spawnSlow() {
        for (int i = 0; i < indexSlow; i++) {
            enemiesSlow.get(i).getCurrentFrame();
            canvas.drawBitmap(enemiesSlow.get(i).getBitmap(), enemiesSlow.get(i).getFrameSlow(), enemiesSlow.get(i).getPositionSlow(), paint);
        }

        int genSlow = new Random().nextInt(300);
        if (genSlow == 3) {
            if (indexSlow < 10) {
                indexSlow++;
            }
        }
    }

    private void spawnFast() {
        for (int i = 0; i < indexFast; i++) {
            enemiesFast.get(i).getCurrentFrame();
            canvas.drawBitmap(enemiesFast.get(i).getBitmap(), enemiesFast.get(i).getFrameFast(), enemiesFast.get(i).getPositionFast(), paint);
        }

        int genFast = new Random().nextInt(400);
        if (genFast == 4) {
            if (indexFast < 5) {
                indexFast++;
            }
        }
    }

    private void spawnTroll() {
        enemyTroll.getCurrentFrame();
        canvas.drawBitmap(enemyTroll.getBitmap(), enemyTroll.getFrameTroll(), enemyTroll.getPositionTroll(), paint);
    }

    private void getCollisions() {
        boolean hitDetected = false;
        for (EnemySlow es : enemiesSlow) {
            if (Rect.intersects(jeanMarc.getHitbox(), es.getHitbox())) {
                hitDetected = true;
            }
        }
        for (EnemyFast ef : enemiesFast) {
            if (Rect.intersects(jeanMarc.getHitbox(), ef.getHitbox())) {
                hitDetected = true;
            }
        }

        if (Rect.intersects(jeanMarc.getHitbox(), enemyTroll.getHitbox())) {
            hitDetected = true;
        }


        if (hitDetected) {

            // TODO invincible
            --GameActivity.lives;


            if ((System.currentTimeMillis() - timeOfDeath) < 2000) {//on est invincibl
                //long diffTime = (curTime - lastUpdate);
                //lastUpdate = curTime;
                ++GameActivity.lives;
                //timeOfDeath = System.currentTimeMillis();

            }
            else{
                timeOfDeath = System.currentTimeMillis();
                mp.start();
                // TODO SOUND HIT
            }
            //long curTime = System.currentTimeMillis();
            //--GameActivity.lives;
            if (GameActivity.lives <= 0) {
                // TODO SOUND LOST
                // TODO gameEnded = true;

                running = false;

            }
        }

    }



    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public JeanMarc getJeanMarc() {
        return jeanMarc;
    }
}
