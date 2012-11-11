package nl.hro.minor.android.lettergame.jjs;

import java.util.Random;

//eager alphabet singleton
public class AlphabetSingleton {

	    private static final AlphabetSingleton _instance = new AlphabetSingleton();
	    private  final int _frequence[];
	    private static int _matrix[];
	    	
	    private AlphabetSingleton() {
	    	//frequence of letter use  a  b  c  d  e   f  g  h  i  j  k  l  m  n   o  p  q  r  s  t  u  v  w  x  y  z
	    	_frequence = new int[]{    7, 1, 1, 4, 18, 1, 3, 2, 6, 1, 2, 3, 2, 10, 6, 1, 1, 6, 3, 6, 2, 2, 1, 1, 1, 1};
	    	
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
		    int n = _matrix[r.nextInt(77)];
		    return n;
		    
	    }
	
}
