package com.kb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.kb.exceptions.CustomException;
import com.kb.pojo.Account;
import com.kb.pojo.User;
import com.kb.utility.DBConnection;

public class AccountDaoImpl implements AccountDao {

	Connection conn = DBConnection.getConnect();
	String sql;
	PreparedStatement ps;
	ResultSet rs;
	int result;
	User user;
	HashMap<Integer, Integer> map;
	Account account;
	CustomException customException = new CustomException();

	@Override
	public boolean addAccount(int accno, int userid) throws SQLException {

		return false;
	}

	@Override
	public boolean deposit(int accno, double amt) throws SQLException {

		sql = "update accountinfo set balance=balance+? where accno=?";
		ps = conn.prepareStatement(sql);
		ps.setDouble(1, amt);
		ps.setInt(2, accno);
		result = ps.executeUpdate();

		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean withdraw(int accno, double amt) throws SQLException {

		double balance = showBalance(accno);
		if (balance < amt) {

			try {
				customException.insufficientBalanceException();// throwing InsufficientBalanceException
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;

		} else {
			sql = "update accountinfo set balance=balance-? where accno=?";
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, amt);
			ps.setInt(2, accno);
			result = ps.executeUpdate();

			if (result > 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public double showBalance(int accno) throws SQLException {

		sql = "select balance from accountinfo where accno=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, accno);
		rs = ps.executeQuery();
		double balance = 0;
		if (rs.next()) {
			balance = rs.getDouble(1);
		}

		return balance;
	}

	@Override // add ttype and tdate parameters
	public boolean transfer(int accnofrom, int accnoto, double amt, String ttype, String tdate) throws SQLException {

		boolean success = true;
		double balance = showBalance(accnofrom);
		if (balance < amt) {
			try {
				customException.insufficientBalanceException();// throwing InsufficientBalanceException
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} else {
			sql = "update accountinfo set balance=balance+? where accno=?";
			ps = conn.prepareStatement(sql);

			conn.setAutoCommit(false);
			ps.setDouble(1, -amt);
			ps.setInt(2, accnofrom);
			ps.addBatch();

			ps.setDouble(1, amt);
			ps.setInt(2, accnoto);
			ps.addBatch();

			int a[] = ps.executeBatch();
			for (int n : a) {
				if (n == 0) {
					success = false;
				}
			}
			conn.commit();
			conn.setAutoCommit(true);// here call TransactionEntry method, pass accnofrom,accnoto,amt,ttype,tdate as
										// parameters

			if (success) {
				transactionEntry(accnoto, accnofrom, amt, ttype, tdate);
			}
			return success;
		}
	}

	@Override
	public User userLogin(String useremail, String userpass) throws SQLException {

		sql = "select u.userid,username,useremail,usercontact,userdob,userpan,useraadhar,useraddress,userpass,userrole,accno,acctype,balance,status"
				+ " from userinfo u join accountinfo a on u.userid=a.userid where u.useremail=? and u.userpass=? ";

		ps = conn.prepareStatement(sql);
		ps.setString(1, useremail);
		ps.setString(2, userpass);

		rs = ps.executeQuery();

		if (rs.next()) {
			if (rs.getString(3).equals(useremail) && rs.getString(9).equals(userpass)) {
				user = new User();
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setUseremail(rs.getString(3));
				user.setUsercontact(rs.getString(4));
				user.setUserdob(rs.getString(5));
				user.setUserpan(rs.getString(6));
				user.setUseraadhar(rs.getString(7));
				user.setUseraddress(rs.getString(8));
				user.setUserpass(rs.getString(9));
				user.setUserrole(rs.getString(10));
				account = new Account(rs.getInt(11), rs.getInt(1), rs.getString(12), rs.getDouble(13),
						rs.getString(14));

				user.setAccount(account);

				return user;
			} else {

				try {
					customException.wrongPasswordException();// throwing WrongPasswordException
				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}
		} else {
			return null;
		}

	}

	@Override
	public boolean transactionEntry(int accnoto, int accnofrom, double amt, String ttype, String tdate)
			throws SQLException {

		sql = "insert into transactioninfo(accnofrom,accnoto,ttype,tamount,tdate) values(?,?,?,?,?)";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, accnofrom);
		ps.setInt(2, accnoto);
		ps.setString(3, ttype);
		ps.setDouble(4, amt);
		ps.setString(5, tdate);

		result = ps.executeUpdate();

		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addPayee(int useraccno, int payeeaccno) throws SQLException {

		sql = "insert into payee values(?,?)";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, useraccno);
		ps.setInt(2, payeeaccno);
		result = ps.executeUpdate();

		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removePayee(int useraccno, int payeeaccno) throws SQLException {
		sql = "delete from payee where useraccno=? and payeeaccno=? ";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, useraccno);
		ps.setInt(2, payeeaccno);
		result = ps.executeUpdate();

		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public HashMap<Integer, Integer> showPayee(int accno) throws SQLException {

		map = new HashMap<Integer, Integer>();
		sql = "select payeeaccno from payee where useraccno=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, accno);
		rs = ps.executeQuery();
		int i = 1;
		while (rs.next()) {
			map.put(i, rs.getInt(1));
			i++;
		}
		return map;
	}
}
