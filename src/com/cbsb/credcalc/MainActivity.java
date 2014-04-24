package com.cbsb.credcalc;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private Button creditButton, depositButton;
	private ImageView cbsbImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main);
		
		creditButton = (Button)findViewById(R.id.main_button_credit);
		creditButton.setOnClickListener(onButtonsClick);
		depositButton = (Button)findViewById(R.id.main_button_deposit);
		depositButton.setOnClickListener(onButtonsClick);
		cbsbImage = (ImageView)findViewById(R.id.image_cbsb_main);
		cbsbImage.setOnClickListener(onButtonsClick);
	}
	
	View.OnClickListener onButtonsClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.main_button_credit:
				Intent creditIntent = new Intent(MainActivity.this, CreditActivity.class);
				startActivity(creditIntent);
				Log.d("Credit", "credit");
				break;
			case R.id.main_button_deposit:
				Intent depositIntent = new Intent(MainActivity.this, DepositActivity.class);
				startActivity(depositIntent);
				Log.d("Deposit", "deposit");
				break;
			case R.id.image_cbsb_main:
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://proalab.com/?page_id=517"));
				startActivity(browserIntent);
				break;
			}
			
		}
	};
}