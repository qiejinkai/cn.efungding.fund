package cn.efunding.fund.common;

import java.util.UUID;

import android.content.Context;
import android.content.ContextWrapper;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Uuid extends ContextWrapper{
	
	public Uuid(Context base) {
		super(base);
		// TODO Auto-generated constructor stub
	}

	final public String getMyUUID(){
	 
		final TelephonyManager tm = (TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);   
	  final String tmDevice, tmSerial, tmPhone, androidId;   
	  tmDevice = "" + tm.getDeviceId();  
	  tmSerial = "" + tm.getSimSerialNumber();   
	  androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);   
	  UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());   
	  String uniqueId = deviceUuid.toString();
	  Log.d("debug","uuid="+uniqueId);
	  return uniqueId;
	  
	 }

}
