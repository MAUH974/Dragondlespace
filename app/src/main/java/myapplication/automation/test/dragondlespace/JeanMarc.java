package myapplication.automation.test.dragondlespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;


public class JeanMarc {
    private int x, y;
    private Bitmap bitmap;
    private Rect frameJeanMarc;
    private RectF positionJeanMarc;
    private long lastFrameChangeTime = 0;
    private int frameLengthInMilliseconds = 200;
    private int currentFrame = 0, frameCount = 4;

    private Rect hitbox;

    public static float test1, test2, test3, test4;

    public static float offset;




    public JeanMarc(Context context) {
        x = GameActivity.deviceWidth/8;
        y = GameActivity.deviceHeight/3;
        frameJeanMarc = new Rect(0, 0, 90, 65);
        positionJeanMarc = new RectF(x, y, x+100, y+75);
        //test1 = positionJeanMarc.top;
        //test2 = positionJeanMarc.bottom;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.jeanmarc);
        bitmap = Bitmap.createScaledBitmap(bitmap, 360, 65, false);

        hitbox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update() {

        frameJeanMarc.left = currentFrame * 90;
        frameJeanMarc.right = frameJeanMarc.left + 90;
        if (positionJeanMarc.top - GameActivity.zPosition *3 < 0 || positionJeanMarc.bottom - GameActivity.zPosition *3 > GameActivity.deviceHeight-50){

        }

        else {
            float newposJMtop = positionJeanMarc.top - GameActivity.zPosition * 3;
            float newposJMbot = positionJeanMarc.bottom - GameActivity.zPosition * 3;

            if ( newposJMtop - positionJeanMarc.top > 10 || newposJMtop - positionJeanMarc.top < -10) {

                if (newposJMtop - positionJeanMarc.top < -10) {
                    newposJMtop = positionJeanMarc.top - 10;
                    newposJMbot = positionJeanMarc.bottom - 10;
                }
                else{
                    newposJMtop = positionJeanMarc.top + 10;
                    newposJMbot = positionJeanMarc.bottom + 10;
                }


            }
            if ( newposJMtop - positionJeanMarc.top > -5 && newposJMtop - positionJeanMarc.top < 0) {
                newposJMtop = positionJeanMarc.top - 3;
                newposJMbot = positionJeanMarc.bottom - 3;

            }
            if ( newposJMtop - positionJeanMarc.top > 0 && newposJMtop - positionJeanMarc.top < 5) {
                newposJMtop = positionJeanMarc.top + 3;
                newposJMbot = positionJeanMarc.bottom + 3;
            }
            if ( newposJMtop - positionJeanMarc.top > -3 && newposJMtop - positionJeanMarc.top < 4) {
                newposJMtop = positionJeanMarc.top ;
                newposJMbot = positionJeanMarc.bottom ;

            }


                positionJeanMarc.top = (int) newposJMtop;
                positionJeanMarc.bottom = (int) newposJMbot;


            hitbox = new Rect(x+5, (int) positionJeanMarc.top+10, (int) positionJeanMarc.right-2, (int) positionJeanMarc.bottom-5);
            // TODO hitbox a remplir
            //hitbox.set
            test1 = x;
            test2 = positionJeanMarc.top;
            test3 = bitmap.getWidth();
            test4 = bitmap.getHeight();
        }
/*
        offset = GameActivity.zPosition - GameActivity.last_zPosition;
        if (positionJeanMarc.top - offset < 0 || positionJeanMarc.bottom - offset > GameActivity.deviceHeight-50){

        }
        else{


            //if (GameActivity.zPosition > 0) {
              //  offset = -offset;
            //}

            test1 -= offset*3;
            test2 -= offset*3;

            positionJeanMarc.top -= offset*5;
            positionJeanMarc.bottom -= offset*5;
        }*/



    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Rect getFrameJeanMarc() {
        return frameJeanMarc;
    }

    public RectF getPositionJeanMarc() {
        return positionJeanMarc;
    }

    public void getCurrentFrame() {
        long time = System.currentTimeMillis();
        if (time > lastFrameChangeTime + frameLengthInMilliseconds) {
            lastFrameChangeTime = time;
            currentFrame++;
            if (currentFrame >= frameCount) {

                currentFrame = 0;
            }
        }
        //update();
    }


    public Rect getHitbox() {
        return hitbox;
    }
}
