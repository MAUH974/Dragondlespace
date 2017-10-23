package myapplication.automation.test.dragondlespace;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Life {
    private Bitmap bitmap;

    private int x, y;

    public Life(Context context) {
        x = 10;
        y = GameActivity.deviceHeight*14/16;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        bitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, false);

    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
