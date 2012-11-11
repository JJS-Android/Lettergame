package nl.hro.minor.android.games.pogo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GameInput implements SensorEventListener {

	private Player _player;
	private SensorManager _sm;
	private long _lastMoveTime = System.currentTimeMillis();
	private long _freeToMoveTime;
	private long _moveDelay = 300;
	
	// Sensor data
	private float _a; 
	private float _b;
	
	// Chaning movement data
	private int _movementX;
	private int _movementY;
	private int _direction;
	
	public GameInput(Player playerToControll) {
		
		// Set player to controll
		_player = playerToControll;
		
		// Get main context
		ContextHolder ch = ContextHolder.getInstance();
		Activity mainContext = ch.getContext();
		
		_sm = (SensorManager)mainContext.getSystemService(Context.SENSOR_SERVICE);
		_sm.registerListener(this, _sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void onSensorChanged(SensorEvent sensorEvent) {
		
		// Prevent this event from running any further if the last move was within 500ms
		_freeToMoveTime = _lastMoveTime + _moveDelay;
		if(System.currentTimeMillis() < _freeToMoveTime){
			return;
		}
		
		_a = 0;
		_b = 0;
		//float c = 0;
		
		if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			 _a = sensorEvent.values[0];
			 _b = sensorEvent.values[1];
			 //c = sensorEvent.values[2];
		}
        
        _movementX = (int) _a;
        _movementY = (int) _b;
        
        // Determine direction
        if(_movementX > 1){
        	_direction = 1; // Left
        } else if (_movementX < -1){
        	_direction = 2; // Right 
        } else if (_movementY > 1){
        	_direction = 3; // Up
        } else if (_movementY < -1){
        	_direction = 4; // Down
        } else {
        	_direction = -1; // None
        	return;
        }
        
        //Log.w("Movement", "X: " + _movementX);
        //Log.w("Movement", "Y: " + _movementY);
        //Log.w("Movement", "Direction: " + _direction);
        
        // Move the player in the correct direction (move will check for collisions)
        _player.move(_direction);
        
        // Keep track of when this move happened
        _lastMoveTime = System.currentTimeMillis();
        
	}
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}
	
	public void pause(){
		_sm.unregisterListener(this, _sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
	}
	
	public void resume(){
		_sm.registerListener(this, _sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
	}

}
