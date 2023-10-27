package com.kb.pojo;

public class Transaction {

	private int accnofrom;
	private int accnoto;
	private double amt;
	private String ttype;
	private String tdate;

	public Transaction(int accnofrom, int accnoto, double amt, String ttype, String tdate) {

		this.accnofrom = accnofrom;
		this.accnoto = accnoto;
		this.amt = amt;
		this.ttype = ttype;
		this.tdate = tdate;
	}

	public int getAccnofrom() {
		return accnofrom;
	}

	public void setAccnofrom(int accnofrom) {
		this.accnofrom = accnofrom;
	}

	public int getAccnoto() {
		return accnoto;
	}

	public void setAccnoto(int accnoto) {
		this.accnoto = accnoto;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public String getTtype() {
		return ttype;
	}

	public void setTtype(String ttype) {
		this.ttype = ttype;
	}

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}

	@Override
	public String toString() {
		return "Transaction [accnofrom=" + accnofrom + ", accnoto=" + accnoto + ", amt=" + amt + ", ttype=" + ttype
				+ ", tdate=" + tdate + "]";
	}
}
