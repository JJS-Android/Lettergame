package nl.hro.minor.android.games;

import java.util.ArrayList;
import java.util.HashMap;

import nl.hro.minor.android.games.differences.Game1;
import nl.hro.minor.android.games.lettergame.Game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GameAdapter extends BaseAdapter implements
AdapterView.OnItemClickListener {
	
	private Activity _activity;
	private ArrayList<HashMap<String, String>> _data;
	private static LayoutInflater _inflater = null;

	public GameAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		_activity = a;
		_data = d;
		_inflater = (LayoutInflater) _activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// items in listview
		return 2;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}
	
	public void onItemClick(AdapterView<?> parent, View v, int position,
			long id) {
		
		Intent i;
		switch(position)
		{
			case 0:
				i = new Intent(_activity, Game1.class);
				_activity.startActivity(i);
				break;
			case 1:
				i = new Intent(_activity, Game.class);
				_activity.startActivity(i);
				break;
		}
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = _inflater.inflate(R.layout.activity_main, null);

		TextView name = (TextView) vi.findViewById(R.id.item_name); // name
		TextView description = (TextView) vi.findViewById(R.id.item_desc); // description

		TextView genre = (TextView) vi.findViewById(R.id.item_genre); // genre
		ImageView img = (ImageView) vi.findViewById(R.id.item_img); // img

		HashMap<String, String> game = new HashMap<String, String>();
		game = _data.get(position);

		// Setting the values in the listview
		name.setText(game.get("name"));
		description.setText(game.get("description"));
		genre.setText(game.get("genre"));
		img.setImageResource(Integer.parseInt(game.get("img")));
		
		return vi;
	}
	



}
