package com.kb.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	static String driver = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/bank_db";
	static String username = "root";
	static String userpass = "root";
	static Connection conn = null;

	public static Connection getConnect() {
		try {
			Class.forName(driver);
			System.out.println("driver loaded");
			conn = DriverManager.getConnection(url, username, userpass);
			System.out.println("connected to db");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}
}
/*
 * CREATE DEFINER = CURRENT_USER TRIGGER `kuberbank_db`.`userinfo_AFTER_INSERT`
 * AFTER INSERT ON `userinfo` FOR EACH ROW BEGIN declare ur varchar(10); select
 * userrole into ur from userinfo where userid=new.userid; if ur='user' then
 * insert into accountinfo(userid)values(new.userid); end if; END
 */