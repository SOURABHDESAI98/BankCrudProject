package com.kb.dao;

import java.sql.SQLException;
import java.util.HashMap;

import com.kb.pojo.User;

public interface AccountDao {

	boolean addAccount(int accno, int userid) throws SQLException;

	boolean deposit(int accno, double amt) throws SQLException;

	boolean withdraw(int accno, double amt) throws SQLException;

	double showBalance(int accno) throws SQLException;

	boolean transfer(int accnofrom, int accnoto, double amt, String ttype, String tdate) throws SQLException;

	User userLogin(String useremail, String userpass) throws SQLException;

	boolean transactionEntry(int accnoto, int accnofrom, double amt, String ttype, String tdate) throws SQLException;

	boolean addPayee(int useraccno, int payeeaccno) throws SQLException;

	boolean removePayee(int useraccno, int payeeaccno) throws SQLException;

	HashMap<Integer, Integer> showPayee(int accno) throws SQLException;

}
