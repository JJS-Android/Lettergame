package nl.hro.minor.android.lettergame.jjs.differences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

public class bitmapCompare {
    
	private contextHolder _ch = contextHolder.getInstance();
	
	@SuppressWarnings("unused")
	// SuppressWarnings("unused") - For '(pixels1 == pixels2)'. Set to null but the array is filled later on in the code.
	public bitmapCompare() {
	    
	}
	
	public Bitmap getDiffMap(int bm1, int bm2){
        
	    // Get bitmaps
        Bitmap _bm1 = BitmapFactory.decodeResource(_ch.getContext().getResources(), bm1);
        Bitmap _bm2 = BitmapFactory.decodeResource(_ch.getContext().getResources(), bm2);
        
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
        
        // Bitmap to draw differences
		Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		Bitmap _diffBitmap = Bitmap.createBitmap(imgWidth, imgHeight, conf);
		
        while (currentRow < maxRow) {
        	while (currentColumn < maxColumn) {
        		// Get pixels in the row and column
        		int[] pixels1 = new int[partialBoxWidth * partialBoxHeight];
        		int[] pixels2 = new int[partialBoxWidth * partialBoxHeight];
        		
        		if ((currentColumn * partialBoxWidth) < imgWidth && (currentRow * partialBoxHeight) < imgHeight) {
            		try {
            			_bm1.getPixels(pixels1, 0, partialBoxWidth, currentColumn * partialBoxWidth,  currentRow * partialBoxHeight, partialBoxWidth, partialBoxHeight);
        				_bm2.getPixels(pixels2, 0, partialBoxWidth, currentColumn * partialBoxWidth,  currentRow * partialBoxHeight, partialBoxWidth, partialBoxHeight);
            		} catch (Exception e) {
            			Log.e("bitmapCompare", "getDiffMap, try getPixels: "+e.getMessage());
            		}
        		}
        		
        		// Loop over all selected pixels
				for (int q=1; q < pixels1.length; q++) {
				    
					int r1 = Color.red(pixels1[q]);
			        int g1 = Color.green(pixels1[q]);
			        int b1 = Color.blue(pixels1[q]);

			        int r2 = Color.red(pixels2[q]);
			        int g2 = Color.green(pixels2[q]);
			        int b2 = Color.blue(pixels2[q]);
			        
					if (r1 != r2 && g1 != g2 && b1 != b2) {
						for (int x=0; x < partialBoxWidth; x++) {
							for (int y=0; y < partialBoxHeight; y++) {
								_diffBitmap.setPixel(currentColumn * partialBoxWidth + x, currentRow * partialBoxHeight + y, Color.RED);
							}
						}
					}
				}
        		currentColumn++;
        	}
        	currentColumn = 0; // Start at the most left column again
        	currentRow++;
        }
        return _diffBitmap;
	}
}
