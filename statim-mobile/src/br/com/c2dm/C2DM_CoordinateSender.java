package br.com.c2dm;

import java.io.IOException;

import manager.ItineraryFileManager;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;

import route.CoordinateWrapper;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import constants.Constants;

public class C2DM_CoordinateSender extends Service {
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Context context = getApplicationContext();
		
		ItineraryFileManager manager = new ItineraryFileManager(context);
        String lastVisitedAddress = manager.getLastVisitedAddress();
        
        CoordinateWrapper coordinate = new CoordinateWrapper(intent.getDoubleExtra("latitude", 0), intent.getDoubleExtra("longitude", 0), lastVisitedAddress, Constants.device_email);
		
		send(coordinate);
		
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long [] padrao = {0, 200, 100, 200};
		vibrator.vibrate(padrao, -1);
		
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private void send(CoordinateWrapper coordinate) {
		String url = Constants.server_url + "/map/addcoordinate";
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext context = new BasicHttpContext();
		HttpPost post = new HttpPost(url);
		try {
			JSONArray jsonArray = new JSONArray(coordinate.getCoordinateArray());
			post.setEntity(new StringEntity(jsonArray.toString()));
			httpClient.execute(post, context);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
