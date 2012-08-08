package br.com.c2dm;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.util.Patterns;
import constants.Constants;

public class RegistrationManager {

	private Context context;
	
	public RegistrationManager(Context context) {
		this.context = context;
	}

	public void manage(String newRegistrationId) {
		String oldRegistrationId = getRegistrationId();
		
		if(oldRegistrationId.isEmpty()) send(newRegistrationId);
		else if(!newRegistrationId.equals(oldRegistrationId)) update(oldRegistrationId, newRegistrationId);
		
		saveLocally(newRegistrationId);
	}

	private String getRegistrationId() {
		SharedPreferences settings = context.getSharedPreferences(Constants.statim_filename, 0);
		return settings.getString(Constants.statim_registration_id, "");
	}

	private void saveLocally(String registrationId) {
		SharedPreferences settings = context.getSharedPreferences(Constants.statim_filename, 0);
		Editor editor = settings.edit();
		editor.putString(Constants.statim_registration_id, registrationId);
		editor.commit();
	}
    
    private void send(String newRegistrationId) {
    	String deviceUserEmail = "";
    	Pattern emailAddress = Patterns.EMAIL_ADDRESS;
    	Account[] accounts = AccountManager.get(context).getAccounts();
    	for (Account account : accounts) {
			if(emailAddress.matcher(account.name).matches()) {
				deviceUserEmail = account.name;
				Log.i("EMAIL", deviceUserEmail);
				break;
			}
		}
    	sendTo(Constants.server_url + "/new/" + Constants.sender_email + "/" + deviceUserEmail + "/" + newRegistrationId);
    }
    
    private void update(String oldRegistrationId, String newRegistrationId) {
    	sendTo(Constants.server_url + "/update/" + oldRegistrationId + "/" + newRegistrationId);
    }
    
	private void sendTo(String url) {
		HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	try {
			client.execute(post);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
