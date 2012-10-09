package nl.hro.minor.android.lettergame.jjs;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

public class Main extends Activity implements android.view.View.OnClickListener{

	ListView list;
    GameAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.listview);
        
        // Set the current context (this) in the 'singleton' contextHolder.
        ContextHolder ch = ContextHolder.getInstance();
        ch.setContext(this);
        
        // Get the startbutton and attack click listener
        View aboutButton = findViewById(R.id.btn_start);
        aboutButton.setOnClickListener(this);
        makeList();
    }
    
    public void makeList()
    {
    	

    	ArrayList<HashMap<String, String>> glist = new ArrayList<HashMap<String, String>>();
    	
    	 //add game 1
    	 HashMap<String, String> map = new HashMap<String, String>();
    	 map.put("name", "Zoek de verschillen");
     	 map.put("description", "Vindt in elk level 3 verschill binnen de tijd om door te gaan naar het volgende level.");
     	 map.put("genres", "puzzle");
     	 map.put("img", ""+R.drawable.magnify);
     	 glist.add(map);
     	 
    	 //add game 2
    	 HashMap<String, String> map2 = new HashMap<String, String>();
    	 map2.put("name", "Letterspel");
     	 map2.put("description", "Gebruik de letters om zoveel mogelijk woorden te maken.");
     	 map2.put("genres", "taal");
     	 map2.put("img", ""+R.drawable.letter);
     	 glist.add(map2);
     	 
     	 list=(ListView)findViewById(R.id.list);
     	 
         adapter=new GameAdapter(this, glist);
         list.setAdapter(adapter);
    	 
    }
    

    @Override
    public void onClick(View v) {
		//Switch over possible incoming views
    	switch(v.getId()){
			case R.id.btn_start:
				//Start game if clicked on the Startbutton
				Intent i = new Intent(this, Game.class);
				startActivity(i);
			break;
		}
		
	}
    
}
