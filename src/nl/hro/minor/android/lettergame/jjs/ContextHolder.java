package nl.hro.minor.android.lettergame.jjs;

import android.app.Activity;

public class ContextHolder{

	private static ContextHolder _ch = null;
	private Activity _c;
	
	private ContextHolder(){
		
	}
	
	public static ContextHolder getInstance(){
		if(_ch == null){
			_ch = new ContextHolder();
		}
		
		return _ch;
	}
	
	public void setContext(Activity c){
		if(_c == null){
			_c = c;
		}
	}
	
	public Activity getContext(){
		return _c;
	}
}