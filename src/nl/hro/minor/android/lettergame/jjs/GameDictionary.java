package nl.hro.minor.android.lettergame.jjs;

public class GameDictionary {

	public GameDictionary() {
		super();
	}
	
	public static Boolean checkWord(String word){
		
		// Get main context
		ContextHolder ch = ContextHolder.getInstance();
		
		// Get the arraylist/dictionary
		String[] dictionary = ch.getContext().getResources().getStringArray(R.array.dict_nl_array);
		
		for (String s : dictionary) {
			
		    // Check if dictionary item matches the given word
			if (s.equals(word)) {
		       return true;
		    }
			
		}
		
		return false;

	}

	
}
