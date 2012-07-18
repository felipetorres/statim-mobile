package route;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import constants.Constants;

public class ItineraryFileManager {

	private final Context context;

	public ItineraryFileManager(Context context) {
		this.context = context;
	}
	
	public void saveVisitedAddresses(List<String> visitedAddresses) {
		JSONArray json = new JSONArray(visitedAddresses);
		SharedPreferences preferences = context.getSharedPreferences(Constants.statim_filename, 0);
		Editor editor = preferences.edit();
		editor.putString(Constants.statim_itinerary, json.toString());
		editor.commit();
	}
	
	public List<String> getVisitedAddresses() {
		SharedPreferences preferences = context.getSharedPreferences(Constants.statim_filename, 0);
		String jsonAddresses = preferences.getString(Constants.statim_itinerary, "");
		List<String> addresses = null;
		if(jsonAddresses != "") {
			try {
				addresses = parseJSON(new JSONArray(jsonAddresses));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return addresses;
	}
	
	private List<String> parseJSON(JSONArray json) throws JSONException {
		List<String> list = new ArrayList<String>();
		for (int i=0; i < json.length(); i++) {
			list.add(json.getString(i));
		}
		return list;
	}
}
