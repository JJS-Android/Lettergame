package nl.hro.minor.android.lettergame.jjs.differences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;

public class bitmapCompare {
	
	private Bitmap _bm1;
	private Bitmap _bm2;
	private Bitmap _diffBitmap;
	
	@SuppressWarnings("unused")
	// SuppressWarnings("unused") - For '(pixels1 == pixels2)'. Set to null but the array is filled later on in the code.
	public bitmapCompare() {
	
        
        
	}
	
	/*
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	*/

	
	public Bitmap getDiffMap(int bm1, int bm2){
		
		contextHolder ch = contextHolder.getInstance();
        
        Bitmap _bm1 = BitmapFactory.decodeResource(ch.getContext().getResources(), bm1);
        Bitmap _bm2 = BitmapFactory.decodeResource(ch.getContext().getResources(), bm2);
        
        // Get width and height of the first image (second image should be equal)
        int imgWidth 	= _bm1.getWidth();
        int imgHeight 	= _bm1.getHeight();
        
        // Define a grid to check for differences in the image
        int partialBoxWidth 	= imgWidth / 20;
        int partialBoxHeight	= imgHeight / 20;
        
        int currentRow		= 0;
        int currentColumn	= 0;
        
        int maxRow		= imgWidth / partialBoxWidth -1;
        int maxColumn	= imgWidth / partialBoxHeight -1;
        
        /* Debug vars */
        /*
        Log.w("partialBoxWidth", ""+partialBoxWidth);
        Log.w("partialBoxHeight", ""+partialBoxHeight);
        
        Log.w("currentRow", ""+currentRow);
        Log.w("currentColumn", ""+currentColumn);
        
        Log.w("maxRow", ""+maxRow);
        Log.w("maxColumn", ""+maxColumn);
        /* */
        
        // Bitmap to draw differences
		Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		Bitmap _diffBitmap = Bitmap.createBitmap(imgWidth, imgHeight, conf);
		
		// Array to keep track of differences in the images
		//ArrayList<ArrayList<Integer>> diffs = new ArrayList<ArrayList<Integer>>();
		
        while(currentRow < maxRow){
        	
        	while(currentColumn < maxColumn){
        		
        		
        		// Get pixels in the row and column
        		int[] pixels1 = new int[partialBoxWidth * partialBoxHeight];
        		int[] pixels2 = new int[partialBoxWidth * partialBoxHeight];
        		
        		try{
        			
        			_bm1.getPixels(pixels1, 0, partialBoxWidth, currentColumn * partialBoxWidth,  currentRow * partialBoxHeight, partialBoxWidth, partialBoxHeight);
    				_bm2.getPixels(pixels2, 0, partialBoxWidth, currentColumn * partialBoxWidth,  currentRow * partialBoxHeight, partialBoxWidth, partialBoxHeight);
        		
        		}catch(Exception e){
        			Log.w("Exception", e.getMessage());
        		}
        		
        		// Loop over all selected pixels
				for(int q=1; q < pixels1.length; q++){
					//Log.w("Pixeldata", q + ": " + pixels1[q]);
					int r1 = Color.red(pixels1[q]);
			        int g1 = Color.green(pixels1[q]);
			        int b1 = Color.blue(pixels1[q]);

			        int r2 = Color.red(pixels2[q]);
			        int g2 = Color.green(pixels2[q]);
			        int b2 = Color.blue(pixels2[q]);
			        
					if(r1==r2 && g1==g2 && b1==b2){
						/*
						ArrayList<Integer> al = new ArrayList<Integer>();
						al.add(currentRow);
						al.add(currentColumn);
						al.add(q);
						diffs.add(al);
						//*/
						
						// setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height)
						//int pix[] = {0xffffff};
						//pix[0] = 0xffffff;
						//_diffBitmap.setPixels(pix, 0, 10, currentColumn * partialBoxWidth, currentRow * partialBoxHeight, 5, 5);
						
						/*_diffBitmap.setPixel(currentColumn * partialBoxWidth, currentRow * partialBoxHeight, Color.GREEN);
						
						for(int x=0; x < partialBoxWidth; x++){
							
							for(int y=0; y < partialBoxHeight; y++){
								_diffBitmap.setPixel(currentColumn * partialBoxWidth + x, currentRow * partialBoxHeight + y, Color.GREEN);
							}
						
						}
						*/
					} else {
						//diffBitmap.setPixel(currentColumn * partialBoxWidth, currentRow * partialBoxHeight, Color.RED);
						
						for(int x=0; x < partialBoxWidth; x++){
							
							for(int y=0; y < partialBoxHeight; y++){
								_diffBitmap.setPixel(currentColumn * partialBoxWidth + x, currentRow * partialBoxHeight + y, Color.RED);
							}
						
						}
					}
				}
				
				/*
				int redValue = Color.red(pixels2[1]);
		        int blueValue = Color.blue(pixels2[1]);
		        int greenValue = Color.green(pixels2[1]);
		        
		        Log.w("Color", redValue + ", " + blueValue + ", " + greenValue);
				//*/
				
        		currentColumn++;
        	}
        	
        	currentColumn = 0; // Start at the most left column again
        	currentRow++;
        	
        }
        // */
        
       // Canvas canvas = new Canvas(diffBitmap);
		//SurfaceView sfv = (SurfaceView) ch.getContext().findViewById(R.id.surfaceView1);
		//sfv.draw(canvas);
		
		//Canvas canvas = new Canvas(diffBitmap);
		//ImageView iv = (ImageView) ch.getContext().findViewById(R.id.imageView1);
		//iv.setImageBitmap(_diffBitmap);
        
        return _diffBitmap;
        
	}
}
