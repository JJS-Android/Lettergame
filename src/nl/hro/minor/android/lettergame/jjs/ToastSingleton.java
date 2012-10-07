package nl.hro.minor.android.lettergame.jjs;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public enum ToastSingleton {
    INSTANCE;    // only element in this enum
 
    public static void makeToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.show();
    }
}