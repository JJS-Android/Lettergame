package nl.hro.minor.android.games;

import java.util.ArrayList;
import java.util.HashMap;

import nl.hro.minor.android.games.lettergame.ContextHolder;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

public class Main extends Activity {

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
        makeList();
    }
    
    public void makeList()
    {
    	

    	ArrayList<HashMap<String, String>> glist = new ArrayList<HashMap<String, String>>();
    	
    	 //add game 1
    	 HashMap<String, String> map = new HashMap<String, String>();
    	 map.put("name", "Zoek de verschillen");
     	 map.put("description", "Vindt in elk level 3 verschillen binnen de tijd om door te gaan naar het volgende level.");
     	 map.put("genres", "puzzle");
     	 map.put("img", ""+R.drawable.magnify);
     	 glist.add(map);
     	 
    	 //add game 2
    	 HashMap<String, String> map2 = new HashMap<String, String>();
    	 map2.put("name", "Letterspel");
     	 map2.put("description", "Gebruik de letters om zoveel mogelijk woorden te maken en haal de 10.000 punten. Koop voor 200 een nieuwe letter als je er echt niet meer uitkomt.");
     	 map2.put("genres", "taal");
     	 map2.put("img", ""+R.drawable.letter);
     	 glist.add(map2);
     	 
    	 //add game 3
    	 HashMap<String, String> map3 = new HashMap<String, String>();
    	 map3.put("name", "Pogo");
     	 map3.put("description", "Bestuur jou speler (#1) door je apparaat te bewegen (overhellen naar voren, achter, links en rechts). Maak zoveel mogelijk blokjes in jou kleur en spring vervolgens op een wit blokje om je punten te innen. Om de zoveel tijd komt er op een willekeurige plek een wit blokje. Je hebt 1:30 minuut de tijd om zoveel mogelijk punten te behalen.");
     	 map3.put("genres", "fun, strategie");
     	 map3.put("img", ""+R.drawable.pongo);
     	 glist.add(map3);
     	 
     	 list=(ListView)findViewById(R.id.list);
     	 
         adapter=new GameAdapter(this, glist);
         //start gameAdapter showing possible games
         list.setAdapter(adapter);
         //set clicklistener for adapterview
         list.setOnItemClickListener(adapter);
    	 
    }
    
}
