package route;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public String getLastVisitedAddress() {
		List<String> visitedAddresses = getVisitedAddresses();
		if(visitedAddresses != null) {
			return visitedAddresses.get(visitedAddresses.size()-1);
		}
		return "no-address";
	}
	
	private List<String> parseJSON(JSONArray json) throws JSONException {
		List<String> list = new ArrayList<String>();
		for (int i=0; i < json.length(); i++) {
			list.add(json.getString(i));
		}
		return list;
	}
	
    public void save(String json) throws IOException {
    	File dir = context.getCacheDir();
    	File file = new File(dir, "itinerary");
    	PrintStream printStream = new PrintStream(file);
    	printStream.print(json);
    }
    
    public String getItineraryJSONFromFile() {
    	File dir = context.getCacheDir();
    	File file = new File(dir, "itinerary");
    	Scanner scanner;
    	String content = "";
		try {
			scanner = new Scanner(new FileInputStream(file));
			while (scanner.hasNextLine()) {
				content += scanner.nextLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return content;
    }

	public ArrayList<Address> buildItineraryArray(String itineraryJSON) {
		JSONArray array;
		ArrayList<Address> itinerary = new ArrayList<Address>();
		try {
			array = new JSONArray(itineraryJSON);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				Address address = new Address(jsonObject.getString("name"), jsonObject.getInt("number"));
				itinerary.add(address);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itinerary;
	}
}
