package nl.hro.minor.android.lettergame.jjs;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Main extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set the current context (this) in the 'singleton' contextHolder.
        ContextHolder ch = ContextHolder.getInstance();
        ch.setContext(this);
        
        // Get the startbutton and attack click listener
        View aboutButton = findViewById(R.id.btn_start);
        aboutButton.setOnClickListener(this);
        
        //*
        Log.w("Test", ""+GameDictionary.checkWord("test"));
        Log.w("Test", ""+GameDictionary.checkWord("banaan"));
        Log.w("Test", ""+GameDictionary.checkWord("computer"));
        Log.w("Test", ""+GameDictionary.checkWord("blauw"));
        Log.w("Test", ""+GameDictionary.checkWord("telefoon"));
        Log.w("Test", ""+GameDictionary.checkWord("F46r"));
        Log.w("Test", ""+GameDictionary.checkWord("Floeps"));
        //*/
        
    }

    @Override
    public void onClick(View v) {
		// Switch over possible incoming views
    	switch(v.getId()){
			case R.id.btn_start:
				// Start game if clicked on the Startbutton
				Intent i = new Intent(this, Game.class);
				startActivity(i);
			break;
		}
		
	}
    
}
