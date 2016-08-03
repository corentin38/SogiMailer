package com.sogilis.sogimailer.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;

import com.sogilis.sogimailer.R;

public class TestMailDialog extends DialogFragment {

	private static final String TAG = "SOGIMAILER_TESTEMAILDLG";

	public static final String TESTMAIL_DIALOG_KEY = "com.sogilis.sogimailer.ui.TestMailDialog";

	public static TestMailDialog newInstance() {
		return new TestMailDialog();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		builder.setView(inflater.inflate(R.layout.dialog_testmail, null))
			.setTitle("Test mail sender")
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Log.d(TAG, "Cancel !");
				}
			})
			.setPositiveButton("Send", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.d(TAG, "We should send test mail !");
				}
			});

		AlertDialog dlg = builder.create();
		dlg.setCanceledOnTouchOutside(true);
		return dlg;
	}

}
