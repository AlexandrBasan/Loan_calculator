package com.cbsb.credcalc;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CreditActivity extends Activity {

	public static final String RESULT_EMPTY = " ...";
	public static final int ERROR_EMPTY_FIELD_SUM = 1;
	public static final int ERROR_EMPTY_FIELD_PERCENTS = 2;
	public static final int ERROR_EMPTY_FIELD_TERM = 3;
	public static final int ERROR_NUMBER_FORMAT_EXCEPTION = 4;
	public static final int ERROR_EMPTY_FIELD_OT_COMISSION = 5;
	public static final int ERROR_EMPTY_FIELD_MONTHLY_COMISSION = 6;

	private EditText creditSumET, creditPercentsET, creditTermET,
			creditOneTimeComissionET, creditMonthlyComissionET;
	private TextView resultMonthlyPayoutTV, resultOverpayTV, resultEffectiveTV;
	private Button calculateButton;
	private RadioGroup creditTypeRadioGroup;
	private ImageView cbsbIV, dropDownIV;
	private LinearLayout hidingLL, detailsLL;
	private boolean detailsExpanded = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.credit);

		getResources();

		detailsLL = (LinearLayout) findViewById(R.id.details_ll);
		detailsLL.setOnClickListener(onDetailsClick);
		hidingLL = (LinearLayout) findViewById(R.id.detais_hiding_ll);
		hidingLL.setVisibility(View.GONE);
		dropDownIV = (ImageView) findViewById(R.id.dropdown_image);
		creditOneTimeComissionET = (EditText) findViewById(R.id.et_credit_one_time_comission);
		creditMonthlyComissionET = (EditText) findViewById(R.id.et_credit_monthly_comission);
		creditSumET = (EditText) findViewById(R.id.et_credit_sum);
		creditPercentsET = (EditText) findViewById(R.id.et_percents);
		creditTermET = (EditText) findViewById(R.id.et_credit_term);
		calculateButton = (Button) findViewById(R.id.button_calculate);
		resultMonthlyPayoutTV = (TextView) findViewById(R.id.tv_result_monthly_payout);
		resultOverpayTV = (TextView) findViewById(R.id.tv_result_overpay);
		resultEffectiveTV = (TextView) findViewById(R.id.tv_result_effective);
		creditTypeRadioGroup = (RadioGroup) findViewById(R.id.radio_group_credit_type);

		calculateButton.setOnClickListener(onCalculateClick);
		Typeface cFont = Typeface.createFromAsset(getAssets(), "604.ttf");
		calculateButton.setTypeface(cFont);

		cbsbIV = (ImageView) findViewById(R.id.image_cbsb);
		cbsbIV.setOnClickListener(onImageClick);

		resultMonthlyPayoutTV.setText(RESULT_EMPTY);
		resultOverpayTV.setText(RESULT_EMPTY);
		resultEffectiveTV.setText(RESULT_EMPTY);

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

	private void displayError(int errorCode) {
		Toast errorToast = null;
		switch (errorCode) {
		case ERROR_EMPTY_FIELD_SUM:
			errorToast = Toast.makeText(this, R.string.error_empty_sum,
					Toast.LENGTH_SHORT);
			break;
		case ERROR_EMPTY_FIELD_PERCENTS:
			errorToast = Toast.makeText(this, R.string.error_empty_percents,
					Toast.LENGTH_SHORT);
			break;
		case ERROR_EMPTY_FIELD_TERM:
			errorToast = Toast.makeText(this, R.string.error_empty_term,
					Toast.LENGTH_SHORT);
			break;
		case ERROR_NUMBER_FORMAT_EXCEPTION:
			errorToast = Toast.makeText(this, R.string.error_invalid_format,
					Toast.LENGTH_SHORT);
			break;
		case ERROR_EMPTY_FIELD_OT_COMISSION:
			errorToast = Toast.makeText(this,
					R.string.error_one_time_comission, Toast.LENGTH_SHORT);
			break;
		case ERROR_EMPTY_FIELD_MONTHLY_COMISSION:
			errorToast = Toast.makeText(this, R.string.error_monthly_comission,
					Toast.LENGTH_SHORT);
			break;
		}
		if (errorToast != null)
			errorToast.show();
	}

	View.OnClickListener onCalculateClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			int creditSum, creditTerm;
			double creditPercents, monthlyPercents, oneTimeComission = 0, monthlyComission = 0;
			double overpay, monthlyPayout, classicBasePayment, effectiveResult = 0;
			ArrayList<StandartPaymentMonth> monthList;
			if (creditPercentsET.getText().length() == 0) {
				displayError(ERROR_EMPTY_FIELD_PERCENTS);
				return;
			}
			if (creditSumET.getText().length() == 0) {
				displayError(ERROR_EMPTY_FIELD_SUM);
				return;
			}
			if (creditTermET.getText().length() == 0) {
				displayError(ERROR_EMPTY_FIELD_TERM);
				return;
			}
			if (detailsExpanded
					&& creditOneTimeComissionET.getText().length() == 0) {
				displayError(ERROR_EMPTY_FIELD_OT_COMISSION);
				return;
			}
			if (detailsExpanded
					&& creditMonthlyComissionET.getText().length() == 0) {
				displayError(ERROR_EMPTY_FIELD_MONTHLY_COMISSION);
				return;
			}

			try {
				creditPercents = Double.parseDouble(creditPercentsET.getText()
						.toString().replace(',', '.'));
				creditSum = Integer.parseInt(creditSumET.getText().toString());
				creditTerm = Integer
						.parseInt(creditTermET.getText().toString());
				if (detailsExpanded) {
					monthlyComission = Double
							.parseDouble(creditMonthlyComissionET.getText()
									.toString().replace(',', '.'));
					oneTimeComission = Double
							.parseDouble(creditOneTimeComissionET.getText()
									.toString().replace(',', '.'));
				}

				if (creditTypeRadioGroup.getCheckedRadioButtonId() == R.id.radio_button_annuity) {
					monthlyPercents = (creditPercents * 0.01) / 12;
					if (!detailsExpanded) {
						monthlyPayout = (monthlyPercents * Math.pow(
								(1 + monthlyPercents), creditTerm))
								/ (Math.pow((1 + monthlyPercents), creditTerm) - 1)
								* creditSum;
						overpay = (monthlyPayout * creditTerm) - creditSum;
						resultOverpayTV.setText(" "
								+ Integer.toString((int) overpay));
						resultMonthlyPayoutTV.setText(" "
								+ Integer.toString((int) monthlyPayout));
						resultEffectiveTV.setText(RESULT_EMPTY);
					} else {
						monthlyPayout = (monthlyPercents * Math.pow(
								(1 + monthlyPercents), creditTerm))
								/ (Math.pow((1 + monthlyPercents), creditTerm) - 1)
								* creditSum;
						overpay = (monthlyPayout * creditTerm) - creditSum;
						double tCreditExpenses = overpay
								+ (creditSum * creditTerm * (monthlyComission * 0.01))
								+ (creditSum * (oneTimeComission * 0.01));
						Log.d("CreditExpenses",
								Double.toString(tCreditExpenses));
						double tW1_1 = Math
								.pow(1 + monthlyPercents, creditTerm) - 1;
						Log.d("t1_1", Double.toString(tW1_1));
						double tW1_2 = (creditPercents * 0.01)
								* (creditTerm / 12);
						Log.d("t1_2", Double.toString(tW1_2));
						double tW1 = tW1_1 / tW1_2;
						Log.d("t1", Double.toString(tW1));
						double tW2_1_1 = Math.pow(1 + monthlyPercents,
								creditTerm) - 1;
						Log.d("t2_1_1", Double.toString(tW2_1_1));
						double tW2_1 = tW2_1_1 / monthlyPercents - creditTerm;
						Log.d("t2_1", Double.toString(tW2_1));
						double tW2_2 = creditTerm
								* (1 - Math.pow(1 + monthlyPercents,
										(0 - creditTerm)));
						Log.d("t2_2", Double.toString(tW2_2));
						double tW2 = tW2_1 / tW2_2;
						Log.d("t2", Double.toString(tW2));
						double tWeightedSum = creditSum * (tW1 - tW2);
						Log.d("tWeightedSum", Double.toString(tWeightedSum));
						effectiveResult = tCreditExpenses / (creditTerm / 12)
								/ tWeightedSum;
						resultEffectiveTV.setText(String.format("%.1f",
								effectiveResult * 100) + "%");
						resultOverpayTV.setText(Integer
								.toString((int) tCreditExpenses));
						resultMonthlyPayoutTV.setText(Integer
								.toString((int) (monthlyPayout + creditSum
										* (monthlyComission * 0.01))));
					}

				}
				if (creditTypeRadioGroup.getCheckedRadioButtonId() == R.id.radio_button_classic) {
					classicBasePayment = creditSum / creditTerm;
					monthList = new ArrayList<StandartPaymentMonth>();
					double tCreditBody, tFromPercents, tPayment;
					overpay = 0;
					if (!detailsExpanded) {
						for (int i = 0; i < creditTerm; i++) {
							if (i == 0) {
								tCreditBody = creditSum;
							} else {
								tCreditBody = (monthList.get(i - 1)
										.getCreditBody() - classicBasePayment);
							}
							tFromPercents = (tCreditBody * ((creditPercents * 0.01) / 12));
							overpay += tFromPercents;
							tPayment = (int) (classicBasePayment + tFromPercents);
							monthList.add(i, new StandartPaymentMonth((i + 1),
									(int)tCreditBody, (int)tFromPercents, (int)tPayment));
						}
					} else {
						double tcWeightedSum = creditSum * (creditTerm + 1)
								/ (2 * creditTerm);
						Log.d("Classic payment weighted sum", Double.toString(tcWeightedSum));
						for (int i = 0; i < creditTerm; i++) {
							if (i == 0) {
								tCreditBody = creditSum;
							} else {
								tCreditBody = (monthList.get(i - 1)
										.getCreditBody() - classicBasePayment);
							}
							tFromPercents = (tCreditBody * ((creditPercents * 0.01) / 12)  + (creditSum
									* monthlyComission * 0.01));
							overpay += tFromPercents;
							tPayment = (classicBasePayment
									+ tFromPercents);
							monthList.add(i, new StandartPaymentMonth((i + 1),
									(int)tCreditBody, (int)tFromPercents, (int)tPayment));
						}
						double tcCreditExpenses = overpay + (creditSum * oneTimeComission * 0.01);
						Log.d("Classic credit expenses", Double.toString(tcCreditExpenses));
						effectiveResult = tcCreditExpenses / (creditTerm / 12) / tcWeightedSum * 100;
						overpay = tcCreditExpenses;
					}

					ResultsDialog dialog = new ResultsDialog(
							CreditActivity.this, monthList, (int)overpay,
							(int) (creditSum + overpay), effectiveResult);
					dialog.show();
				}
			} catch (NumberFormatException e) {
				displayError(ERROR_NUMBER_FORMAT_EXCEPTION);
			}

		}

	};
}
