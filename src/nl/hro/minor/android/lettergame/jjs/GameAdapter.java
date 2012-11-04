package nl.hro.minor.android.lettergame.jjs;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GameAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public GameAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.activity_main, null);

		TextView name = (TextView) vi.findViewById(R.id.item_name); // name
		TextView description = (TextView) vi.findViewById(R.id.item_desc); // description

		TextView genre = (TextView) vi.findViewById(R.id.item_genre); // genre
		ImageView img = (ImageView) vi.findViewById(R.id.item_img); // img

		HashMap<String, String> game = new HashMap<String, String>();
		game = data.get(position);

		// Setting the values in the listview
		name.setText(game.get("name"));
		description.setText(game.get("description"));
		genre.setText(game.get("genre"));
		
		
		
		img.setImageResource(Integer.parseInt(game.get("img")));
		return vi;
	}
	



}
