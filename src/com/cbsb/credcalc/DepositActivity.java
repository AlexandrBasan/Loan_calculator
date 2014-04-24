package com.cbsb.credcalc;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DepositActivity extends Activity {

	public static final String RESULT_EMPTY = " ...";
	public static final int ERROR_EMPTY_FIELD_SUM = 1;
	public static final int ERROR_EMPTY_FIELD_PERCENTS = 2;
	public static final int ERROR_EMPTY_FIELD_TERM = 3;
	public static final int ERROR_NUMBER_FORMAT_EXCEPTION = 4;
	
	EditText depositSumET, depositPercentsET, depositTermET;
	TextView depositPercentsTV, depositSumTV;
	RadioGroup depositTypeRG;
	Button calculateButton;
	ImageView iconCBSBIV, dropDownIV;
	LinearLayout hidingLL, detailsLL;
	private boolean detailsExpanded = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.deposit);
		depositSumET = (EditText) findViewById(R.id.et_deposit_sum);
		depositPercentsET = (EditText) findViewById(R.id.et_deposit_percents);
		depositTermET = (EditText) findViewById(R.id.et_deposit_term);
		depositPercentsTV = (TextView) findViewById(R.id.tv_result_deposit_percents);
		depositPercentsTV.setText(RESULT_EMPTY);
		depositSumTV = (TextView) findViewById(R.id.tv_result_deposit_sum);
		depositSumTV.setText(RESULT_EMPTY);
		depositTypeRG = (RadioGroup) findViewById(R.id.radio_group_deposit_type);
		calculateButton = (Button)findViewById(R.id.button_calculate_deposit);
		iconCBSBIV = (ImageView)findViewById(R.id.image_cbsb_deposit);
		iconCBSBIV.setOnClickListener(onImageClick);
		Typeface cFont = Typeface.createFromAsset(getAssets(), "604.ttf");
		hidingLL = (LinearLayout) findViewById(R.id.detais_deposit_hiding_ll);
		hidingLL.setVisibility(View.GONE);
		detailsLL = (LinearLayout) findViewById(R.id.details_deposit_ll);
		detailsLL.setOnClickListener(onDetailsClick);
		dropDownIV = (ImageView) findViewById(R.id.dropdown_deposit_image);
		calculateButton.setTypeface(cFont);
		calculateButton.setOnClickListener(onCalculateClick);
	}
	
	View.OnClickListener onDetailsClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (detailsExpanded) {
				hidingLL.setVisibility(View.GONE);
				detailsExpanded = false;
				dropDownIV.setImageResource(R.drawable.dropdown_icon);
			} else {
				hidingLL.setVisibility(View.VISIBLE);
				detailsExpanded = true;
				dropDownIV.setImageResource(R.drawable.dropdown_icon_up);
			}

		}
	};
	
	View.OnClickListener onImageClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://proalab.com/?page_id=517"));
			startActivity(browserIntent);

		}
	};
	
	View.OnClickListener onCalculateClick  = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			double depositSum, depositTerm, resultPercents = 0, resultSum = 0;
			double depositPercents;
			if (depositPercentsET.getText().length() == 0) {
				displayError(ERROR_EMPTY_FIELD_PERCENTS);
				return;
			}
			if (depositSumET.getText().length() == 0) {
				displayError(ERROR_EMPTY_FIELD_SUM);
				return;
			}
			if (depositTermET.getText().length() == 0) {
				displayError(ERROR_EMPTY_FIELD_TERM);
				return;
			}
			try {
				depositPercents = Double.parseDouble(depositPercentsET.getText()
						.toString().replace(',', '.'));
				depositSum = Integer.parseInt(depositSumET.getText().toString());
				depositTerm = Integer
						.parseInt(depositTermET.getText().toString());
				
				switch(depositTypeRG.getCheckedRadioButtonId()) {
				case R.id.radio_button_nocap:
					resultPercents = depositSum * (depositPercents * 0.01)
						* (depositTerm / 12);
					resultSum = resultPercents + depositSum;
					break;
				case R.id.radio_button_monthlycap:
					resultSum = depositSum * (Math.pow(1 + (depositPercents * 0.01 / 12), depositTerm));
					resultPercents = resultSum - depositSum;
					break;
				case R.id.radio_button_yearlycap:
					resultSum = depositSum * Math.pow((1 + (depositPercents * 0.01)), (depositTerm / 12));
					resultPercents = resultSum - depositSum;
					break;
				}
				depositPercentsTV.setText(Integer.toString((int)resultPercents));
				depositSumTV.setText(Integer.toString((int)resultSum));
			} catch (NumberFormatException e) {
				displayError(ERROR_NUMBER_FORMAT_EXCEPTION);
			}
		}
	};
	
	private void displayError(int errorCode) {
		Toast errorToast = null;
		switch (errorCode) {
		case ERROR_EMPTY_FIELD_SUM:
			errorToast = Toast.makeText(this, R.string.error_deposit_empty_sum,
					Toast.LENGTH_SHORT);
			break;
		case ERROR_EMPTY_FIELD_PERCENTS:
			errorToast = Toast.makeText(this, R.string.error_deposit_empty_percents,
					Toast.LENGTH_SHORT);
			break;
		case ERROR_EMPTY_FIELD_TERM:
			errorToast = Toast.makeText(this, R.string.error_deposit_empty_term,
					Toast.LENGTH_SHORT);
			break;
		case ERROR_NUMBER_FORMAT_EXCEPTION:
			errorToast = Toast.makeText(this, R.string.error_invalid_format,
					Toast.LENGTH_SHORT);
			break;
		}
		if (errorToast != null)
			errorToast.show();
	}
}
