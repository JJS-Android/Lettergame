package nl.hro.minor.android.lettergame.jjs;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;


public class Game extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(new CanvasView(this));
    }

}
