package br.com.c2dm;

import route.ItineraryActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class ItineraryNotificatorService extends Service{
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		send();
		return START_STICKY;
	}

	private void send() {
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		Intent notificationIntent = new Intent(this, ItineraryActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext());
		notification.setTicker("Novo itinerário");
		notification.setContentTitle("Statim");
		notification.setContentText("Itinerário alterado");
		notification.setWhen(System.currentTimeMillis());
		notification.setSmallIcon(R.drawable.location_map);
		notification.setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0));
		notification.setAutoCancel(true);

		manager.notify(1, notification.getNotification());
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	
}
