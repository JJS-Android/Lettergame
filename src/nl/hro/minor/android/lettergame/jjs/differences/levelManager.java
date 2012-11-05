package nl.hro.minor.android.lettergame.jjs.differences;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.util.Log;

public final class levelManager {

	private ArrayList<Integer> playedLevelIds = new ArrayList<Integer>();
	private String levelImageUrl = "http://student.cmi.hro.nl/0821349/Minor/Android/Levels/";
	
	public int getNewLevel() {
		
		// get already played levels
		String levelsPlayed = "";
		for(int levelId : playedLevelIds){
			levelsPlayed += levelId + ",";
		}
		
		// get level (which is not played yet)
		try {
			
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("http://student.cmi.hro.nl/0821349/Minor/Android/zdv.php?ids=" + levelsPlayed);
			HttpResponse response = client.execute(request);
			
			//Log.w("Response", "Request, check");
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 50);
			    
			String line = "";
			String lineRead;
			while ((lineRead = rd.readLine()) != null) {
			  line += lineRead;
			} 
			
			//Log.w("Level Number", line);
			
			// Set the new level as played
			setPlayedLevel(Integer.valueOf(line));
			
			return Integer.valueOf(line);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	public void load(int levelId){
		
		contextHolder ch = contextHolder.getInstance();
		String imgUrl = levelImageUrl + levelId + "_1.bmp";
		Log.w("Img url", imgUrl);
		String fileUrl = imgUrl;
		Bitmap bmp = null;
		
		URL myFileUrl =null;
		
		
		
		/*
        try {
             myFileUrl= new URL(fileUrl);
        } catch (MalformedURLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
        }
        try {
             HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
             conn.setDoInput(true);
             conn.connect();
             InputStream is = conn.getInputStream();
             
             bmp = BitmapFactory.decodeStream(is);
             
             ImageView iv = (ImageView) ch.getContext().findViewById(R.id.imageView1);
     		iv.setImageBitmap(bmp);
     		
        } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
        }
        */
		
		//ImageView iv = (ImageView) ch.getContext().findViewById(R.id.imageView1);

		
	}
	
	private void setPlayedLevel(int playedLevelId){
		Log.w("Added", ""+playedLevelId);
		playedLevelIds.add(playedLevelId);
	}
	
	public void resetLevelHistory(){
        playedLevelIds.clear();
    }
}