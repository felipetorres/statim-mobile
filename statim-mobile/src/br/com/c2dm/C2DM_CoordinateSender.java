package br.com.c2dm;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import route.ItineraryFileManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import constants.Constants;

public class C2DM_CoordinateSender extends Service {
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		ItineraryFileManager manager = new ItineraryFileManager(getApplicationContext());
        String lastVisitedAddress = manager.getLastVisitedAddress();
		
		send(intent.getStringExtra("latitude"), intent.getStringExtra("longitude"), lastVisitedAddress.replace(" ", "-"));
		
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long [] padrao = {0, 200, 100, 200};
		vibrator.vibrate(padrao, -1);
		
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private void send(String lat, String lon, String lastVisitedAddress) {
		String url = Constants.server_url + "/map/add/" + lat + "/" + lon + "/" + lastVisitedAddress;
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext context = new BasicHttpContext();
		HttpGet get = new HttpGet(url);
		try {
			httpClient.execute(get, context);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
