package com.cbsb.credcalc;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

public class ResultsDialog extends Dialog {

	private TextView overpayDialogTV, sumDialogTV, effectiveTV;
	private ListView monthResultLV;
	public ResultsDialog(Context context,
			ArrayList<StandartPaymentMonth> months, int overpay, int sumTotal,
			double effective) {
		super(context, R.style.mapdialog_style);
		super.setCancelable(true);
		super.setContentView(R.layout.month_list_dialog);
		context.getResources();

		this.overpayDialogTV = (TextView) findViewById(R.id.tv_dialog_overpay);
		this.overpayDialogTV.setText(Integer.toString(overpay));
		this.sumDialogTV = (TextView) findViewById(R.id.tv_dialog_total);
		this.sumDialogTV.setText(Integer.toString(sumTotal));
		this.effectiveTV = (TextView) findViewById(R.id.tv_dialog_effective);
		if (effective != 0) {
			this.effectiveTV.setText(String.format("%.1f", effective));
		} else {
			this.effectiveTV.setText(CreditActivity.RESULT_EMPTY);
		}

		this.monthResultLV = (ListView) findViewById(R.id.monthListView);
		MonthListArrayAdapter adapter = new MonthListArrayAdapter(context,
				R.layout.month_listitem, months);
		this.monthResultLV.setAdapter(adapter);
	}

}
