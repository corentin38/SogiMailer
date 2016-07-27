package com.sogilis.sogimailer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "SOGI---MAILER";

	IHelloService service;
	AddServiceConnection connection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initService();
	}

	private void initService() {
		Log.i(TAG, "initService()" );
		connection = new AddServiceConnection();
		Intent i = new Intent();
		Log.d(TAG, "Class name : " + com.sogilis.sogimailer.SogiMailerService.class.getName());
		i.setClassName("com.sogilis.sogimailer", com.sogilis.sogimailer.SogiMailerService.class.getName());
		boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
		Log.i(TAG, "initService() bound value: " + ret);
	}

	/**
	 * This is the class which represents the actual service-connection.
	 * It type casts the bound-stub implementation of the service class to our AIDL interface.
	 */
	class AddServiceConnection implements ServiceConnection {

		public void onServiceConnected(ComponentName name, IBinder boundService) {
			service = IHelloService.Stub.asInterface((IBinder) boundService);
			Log.i(TAG, "onServiceConnected(): Connected");
			Toast.makeText(MainActivity.this, "AIDLExample Service connected", Toast.LENGTH_LONG).show();
		}

		public void onServiceDisconnected(ComponentName name) {
			service = null;
			Log.i(TAG, "onServiceDisconnected(): Disconnected");
			Toast.makeText(MainActivity.this, "AIDLExample Service Connected", Toast.LENGTH_LONG).show();
		}
	}

	public void click(View v)
	{
		EditText value2  = (EditText) findViewById(R.id.edit);
		try {
			service.hello(value2.getText().toString());
		} catch (RemoteException e) {
			Log.i(TAG, "Data fetch failed with: " + e);
			e.printStackTrace();
		}
	}

	private void releaseService() {
		unbindService(connection);
		connection = null;
		Log.d(TAG, "releaseService(): unbound.");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseService();
	}
}
