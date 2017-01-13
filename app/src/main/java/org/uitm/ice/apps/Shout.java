package org.uitm.ice.apps;

import org.uitm.ice.ITCConstants;
import org.uitm.ice.controllers.ShoutController;
import org.uitm.ice.R;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Shout extends AppCompatActivity implements OnClickListener, OnDismissListener {
	private SharedPreferences _sp;
	SharedPreferences.Editor _ed;
	
	int[] screen;
	TextView configuredFriends,panicMessage,countdownReadout;
	Button sendShout,cancelCountdown;
	EditText configuredFriendsText,panicMessageText;
	
	String recipients,panicMsg;
	
	Dialog countdown;
	CountDownTimer cd = null;
	int t;
	
	boolean keepPanicing = true;
	boolean isEditable = false;
	volatile boolean isCountingDown = false;
	
	ShoutController sc;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shout);

		//Point size = new Point();
		screen = new int[] {
				//getWindowManager().getDefaultDisplay().getSize()
        		getWindowManager().getDefaultDisplay().getWidth(),
        		getWindowManager().getDefaultDisplay().getHeight()
        };
        
        _sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
        configuredFriends = (TextView) findViewById(R.id.configuredFriends);
        
        configuredFriendsText = (EditText)findViewById(R.id.configuredFriendsText);
        
        panicMessage = (TextView) findViewById(R.id.panicMessage);
        
        panicMessageText = (EditText) findViewById(R.id.panicMessageText);
        panicMessageText.setHeight((int) (screen[1] * 0.25));
        
        sendShout = (Button) findViewById(R.id.shoutBtn);
        sendShout.setOnClickListener(this);
        
    }
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		updatePreferences();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		alignPreferences();
	}
	
	private void alignPreferences() {
		panicMsg = _sp.getString(ITCConstants.Preference.DEFAULT_PANIC_MSG, "");
        panicMessageText.setText(panicMsg);

		recipients = _sp.getString(ITCConstants.Preference.CONFIGURED_FRIENDS,"");
		configuredFriendsText.setText(recipients);
	}
	
	private void updatePreferences() {
		_ed = _sp.edit();
		_ed.putString(ITCConstants.Preference.DEFAULT_PANIC_MSG, panicMsg).commit();
		_ed.putString(ITCConstants.Preference.CONFIGURED_FRIENDS, recipients).commit();
	}
	
	public void doCountdown() {
		countdown = new Dialog(this);
		countdown.setContentView(R.layout.countdown);
		countdown.setCancelable(false);
		countdown.setOnDismissListener(this);
		
		countdownReadout = (TextView) countdown.findViewById(R.id.countdownReadout);
		cancelCountdown = (Button) countdown.findViewById(R.id.cancelCountdown);
		cancelCountdown.setText(getResources().getString(R.string.KEY_SHOUT_COUNTDOWNCANCEL));
		cancelCountdown.setOnClickListener(this);
		countdown.show();
		
		t = 0;
		cd = new CountDownTimer(ITCConstants.Duriation.COUNTDOWN, ITCConstants.Duriation.COUNTDOWNINTERVAL) {
			@Override
			public void onFinish() {
				sc.sendSMSShout(recipients, panicMsg, sc.buildShoutData());
				countdown.dismiss();
			}

			@Override
			public void onTick(long countDown) {
				String secondString = 
					getString(R.string.KEY_SHOUT_COUNTDOWNMSG) + " " + (5 - t) +
					" " + getString(R.string.KEY_SECONDS);
				countdownReadout.setText(secondString);
				Log.d(ITCConstants.Log.ITC,secondString);
				t++;
			}
		};
		cd.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu m) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.shout_menu, m);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v == sendShout) {
			sc = new ShoutController(this);
			doCountdown();
		} else if(v == cancelCountdown) {
			if(cd != null)
				cd.cancel();
		}
	}

	@Override
	public void onDismiss(DialogInterface d) {
		cd.cancel();
		
	}
}
