package com.kb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kb.exceptions.CustomException;
import com.kb.pojo.Account;
import com.kb.pojo.User;
import com.kb.utility.DBConnection;

public class UserDaoImpl implements UserDao {

	Connection conn = DBConnection.getConnect();
	PreparedStatement ps;
	ResultSet rs;
	int result;
	String sql;
	User user;
	Account account;
	List<User> ulist;
	CustomException customException = new CustomException();

	@Override
	public boolean addUser(User user) throws SQLException {

		sql = "insert into userinfo(username,useremail,usercontact,userdob,userpan,useraadhar,useraddress,userpass)values(?,?,?,?,?,?,?,?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getUseremail());
		ps.setString(3, user.getUsercontact());
		ps.setString(4, user.getUserdob());
		ps.setString(5, user.getUserpan());
		ps.setString(6, user.getUseraadhar());
		ps.setString(7, user.getUseraddress());
		ps.setString(8, user.getUserpass());

		System.out.println("Query:" + ps);
		result = ps.executeUpdate();
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public boolean updateUser(User user) throws SQLException {
		sql = "update userinfo set username=?,useremail=?,usercontact=?,userdob=?,userpan=?,useraadhar=?,useraddress=?,userpass=? where userid=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getUseremail());
		ps.setString(3, user.getUsercontact());
		ps.setString(4, user.getUserdob());
		ps.setString(5, user.getUserpan());
		ps.setString(6, user.getUseraadhar());
		ps.setString(7, user.getUseraddress());
		ps.setString(8, user.getUserpass());
		ps.setInt(9, user.getUserid());
		System.out.println("Query:" + ps);
		result = ps.executeUpdate();
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public boolean deleteUser(int userid) throws SQLException {

		sql = "delete from userinfo where userid=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, userid);
		result = ps.executeUpdate();
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<User> showUserList() throws SQLException {

		ulist = new ArrayList<User>();

		sql = "select *from userinfo where userrole='user'";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next()) {
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

			ulist.add(user);
		}

		return ulist;
	}

	@Override
	public List<User> showUserWithAccount() throws SQLException {

		ulist = new ArrayList<User>();
		sql = "select u.userid,username,useremail,usercontact,userdob,userpan,useraadhar,useraddress,userpass,userrole,accno,acctype,balance,status"
				+ " from userinfo u join accountinfo a on u.userid=a.userid";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next()) {
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
			account = new Account(rs.getInt(11), rs.getInt(1), rs.getString(12), rs.getDouble(13), rs.getString(14));
			// account=new Account();
			// account.setAccount_no(rs.getInt(11));
			user.setAccount(account);
			ulist.add(user);
		}
		return ulist;
	}

	@Override
	public User searchUserByUserid(int userid) throws SQLException {

		sql = "select u.userid,username,useremail,usercontact,userdob,userpan,useraadhar,useraddress,userpass,userrole,accno,acctype,balance,status"
				+ " from userinfo u join accountinfo a on u.userid=a.userid where u.userid=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, userid);
		System.out.println("query:" + ps);
		rs = ps.executeQuery();

		while (rs.next()) {
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
			account = new Account(rs.getInt(11), rs.getInt(1), rs.getString(12), rs.getDouble(13), rs.getString(14));
			// account=new Account();
			// account.setAccount_no(rs.getInt(11));
			user.setAccount(account);

		}
		return user;
	}

	@Override
	public User searchUserByEmail(String email) throws SQLException {

		sql = "select * from userinfo where useremail=? and userrole='user' ";

		ps = conn.prepareStatement(sql);
		ps.setString(1, email);

		rs = ps.executeQuery();

		if (rs.next()) {
			user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));

		}
		return user;
	}

	@Override
	public User searchUserByAadhar(String aadhar) throws SQLException {
		sql = "select * from userinfo where useraadhar=? ";

		ps = conn.prepareStatement(sql);
		ps.setString(1, aadhar);

		rs = ps.executeQuery();

		if (rs.next()) {
			user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));

		}
		return user;
	}

	@Override
	public User searchUserByAccountno(int accno) throws SQLException {

		sql = "select u.* from userinfo u join accountinfo a where u.userid=a.userid and u.userrole='user' and a.accno=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, accno);
		rs = ps.executeQuery();
		if (rs.next()) {
			user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
		}

		return user;
	}

	@Override // uncompleted method
	public boolean deactivateUser(int accno) throws SQLException {
		sql = "update accountinfo set status='inactive' where accno=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, accno);

		result = ps.executeUpdate();
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override // uncompleted method
	public boolean activateUser(int accno) throws SQLException {

		sql = "update accountinfo set status='active' where accno=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, accno);

		result = ps.executeUpdate();
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
}
