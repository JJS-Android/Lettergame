package nl.hro.minor.android.lettergame.jjs;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Main extends Activity implements android.view.View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set the current context (this) in the 'singleton' contextHolder.
        ContextHolder ch = ContextHolder.getInstance();
        ch.setContext(this);
        
        View aboutButton = findViewById(R.id.btn_start);
        aboutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_start:
				Intent i = new Intent(this, Game.class);
				startActivity(i);
			break;
		}
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
