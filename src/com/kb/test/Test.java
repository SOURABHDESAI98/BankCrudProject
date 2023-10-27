package com.kb.test;

import java.sql.SQLException;
import java.util.Scanner;

import com.kb.dao.AccountDaoImpl;
import com.kb.dao.LoginDaoImplementation;
import com.kb.dao.UserDaoImpl;
import com.kb.exceptions.CustomException;
import com.kb.pojo.User;

public class Test {

	static LoginDaoImplementation logindao = new LoginDaoImplementation();

	UserDaoImpl userdao = new UserDaoImpl();

	public void adminLogin(String useremail, String userpass) throws SQLException {

		boolean result = logindao.searchAdmin(useremail, userpass);
		if (result) {
			AdminTest.main(null);
		} else {
			System.out.println("Admin Not Found");
		}
	}

	public static void main(String[] args) {

		User user;
		AccountDaoImpl accountdao = new AccountDaoImpl();
		String useremail;
		String userpass;

		CustomException customException = new CustomException();

		Test test = new Test();

		Scanner sc = new Scanner(System.in);
		System.out.println("--------------Welcome to HDFC bank--------------------");

		System.out.println("1. Enter Email Id :");
		useremail = sc.next();

		System.out.println("2. User Password :");
		userpass = sc.next();

		try {
			boolean isAdmin = logindao.isAdmin(useremail, userpass);
			if (isAdmin) {
				test.adminLogin(useremail, userpass);
			} else {
				user = accountdao.userLogin(useremail, userpass);
				if (user != null) {
					UserTest.main(user);
				} else {
					try {
						customException.UserNotFoundException(); // throwing UserNotFoundException

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		sc.close();
	}
}
