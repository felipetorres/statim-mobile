package br.com.c2dm;

import route.ItineraryActivity;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import constants.Constants;

public class C2DM_Activity extends Activity {
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        register();
        
        startActivity(new Intent(this, ItineraryActivity.class));
        
        finish();
    }

	private void register() {
		Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
        
        intent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
        intent.putExtra("sender", Constants.sender_id);
        
        startService(intent);
	}
}
