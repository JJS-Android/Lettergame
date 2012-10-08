package nl.hro.minor.android.lettergame.jjs;

import java.util.Random;

import android.util.Log;

//eager alphabet singleton
public class AlphabetSingleton {

	    private static final AlphabetSingleton _instance = new AlphabetSingleton();
	    //private static final int _frequence[] = new int[]{74, 16, 12, 59, 189, 8, 34, 24, 65, 15, 22, 35, 22, 100, 60, 16, 1, 64, 37, 67, 20, 28, 15 , 2, 1, 14}; 
	    private static int _matrix[];
	    	
	    private AlphabetSingleton() {
	    	//frequence of letter use    a   b   c   d   e   f   g   h   i   j   k   l   m    n   o   p   x  r   s   t   u   v   w   x  y  z
	    	int _frequence[] = new int[]{74, 16, 12, 59, 189, 8, 34, 24, 65, 15, 22, 35, 22, 100, 60, 16, 1, 64, 37, 67, 20, 28, 15, 2, 1, 14};
	    	
	    	_matrix = new int[1000];
	    	int count =0;
	    	
	    	for(int f = 0; f<26; f++)
	    	{
	    		for(int m=0; m<_frequence[f]; m++)
	    		{
	    			_matrix[count] = (f+1);
	    			count++;
	    		}
	    	}
	    	
	    }
	    
	    public static int randomLetter()
	    {
		    Random r = new Random();
		    int n = _matrix[r.nextInt(1000)];
		    Log.d("", ""+n);
		    return n;
		    
	    }
	
}
