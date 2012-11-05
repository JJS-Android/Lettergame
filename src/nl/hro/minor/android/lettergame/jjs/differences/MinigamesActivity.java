package nl.hro.minor.android.lettergame.jjs.differences;

import nl.hro.minor.android.lettergame.jjs.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MinigamesActivity extends Activity {
	
	private Activity self = this;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
       

        final Button button = (Button) findViewById(R.id.start_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(self, Game1.class);
            	startActivity(myIntent);

            }
        });
        
    }
}