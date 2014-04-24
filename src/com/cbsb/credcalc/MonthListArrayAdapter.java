package com.cbsb.credcalc;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MonthListArrayAdapter extends ArrayAdapter<StandartPaymentMonth> {

	private List<StandartPaymentMonth> months;
	private TextView monthNumberTV, creditBodyTV, fromPercentsTV, paymentTV;
	
	public MonthListArrayAdapter(Context context, int textViewResourceId,
			List<StandartPaymentMonth> months) {
		super(context, textViewResourceId, months);
		this.months = months;
	}
	
	@Override
	public int getCount() {
		return months.size();
	}
	
	@Override
	public StandartPaymentMonth getItem(int position) {
		return months.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.month_listitem, parent, false);
		}
		StandartPaymentMonth monthObject = getItem(position);
		
		monthNumberTV = (TextView)row.findViewById(R.id.month_number);
		monthNumberTV.setText(Integer.toString(monthObject.getMonthNumber()));
		
		creditBodyTV = (TextView)row.findViewById(R.id.credit_body);
		creditBodyTV.setText(Integer.toString(monthObject.getCreditBody()));
		
		fromPercentsTV = (TextView)row.findViewById(R.id.credit_from_percents);
		fromPercentsTV.setText(Integer.toString(monthObject.getFromPercents()));
		
		paymentTV = (TextView)row.findViewById(R.id.credit_payment);
		paymentTV.setText(Integer.toString(monthObject.getPayment()));
		return row;
	}

}
