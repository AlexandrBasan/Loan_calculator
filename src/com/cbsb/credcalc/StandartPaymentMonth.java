package com.cbsb.credcalc;

public class StandartPaymentMonth {

	private int monthNumber, creditBody, fromPercents, payment;
	
	public StandartPaymentMonth() {
		setMonthNumber(setCreditBody(setFromPercents(setPayment(0))));
	}
	
	public StandartPaymentMonth(int monthNumber, int creditBody, int fromPercentsOverpay, int payment) {
		this.setCreditBody(creditBody);
		this.setMonthNumber(monthNumber);
		this.setFromPercents(fromPercentsOverpay);
		this.setPayment(payment);
	}

	public int getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(int monthNumber) {
		this.monthNumber = monthNumber;
	}

	public int getCreditBody() {
		return creditBody;
	}

	public int setCreditBody(int creditBody) {
		this.creditBody = creditBody;
		return creditBody;
	}

	public int getFromPercents() {
		return fromPercents;
	}

	public int setFromPercents(int fromPercents) {
		this.fromPercents = fromPercents;
		return fromPercents;
	}

	public int getPayment() {
		return payment;
	}

	public int setPayment(int payment) {
		this.payment = payment;
		return payment;
	}
}
