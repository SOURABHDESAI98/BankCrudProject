package com.kb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kb.exceptions.CustomException;
import com.kb.pojo.Account;
import com.kb.pojo.User;
import com.kb.utility.DBConnection;

public class LoginDaoImplementation implements LoginDao {

	Connection conn = DBConnection.getConnect();
	String sql;
	PreparedStatement ps;
	ResultSet rs;
	int result;
	User user;
	Account account;
	CustomException customException = new CustomException();

	@Override
	public boolean isAdmin(String useremail, String userpass) throws SQLException {

		sql = "select userrole from userinfo where useremail=? and userpass=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, useremail);
		ps.setString(2, userpass);

		rs = ps.executeQuery();
		if (rs.next()) {

			System.out.println(rs.getString(1).toLowerCase());

			if (rs.getString(1).toLowerCase().equals("admin")) {
				return true;
			} else {

				if (rs.getString(1).toLowerCase() == null) {
					customException.UserNotFoundException(); // throwing UserNotFoundException
				}

				return false;
			}

		} else {
			return false;
		}
	}

	public boolean searchAdmin(String useremail, String userpass) throws SQLException {

		sql = "select * from userinfo where useremail=? and userpass=? ";

		ps = conn.prepareStatement(sql);
		ps.setString(1, useremail);
		ps.setString(2, userpass);

		rs = ps.executeQuery();

		if (rs.next()) {
			if (rs.getString(3).equals(useremail) && rs.getString(9).equals(userpass)) {
				return true;
			} else {
				try {
					customException.wrongPasswordException();// throwing WrongPasswordException
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
		} else {
			return false;
		}
	}
}
