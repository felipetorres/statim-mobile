package infra;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;

public class DeviceInfo {

	private final Context context;

	public DeviceInfo(Context context) {
		this.context = context;
	}
	
	public String getEmail() {
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
    	return deviceUserEmail;
	}
}
