package com.kb.test;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.kb.dao.UserDaoImpl;
import com.kb.exceptions.CustomException;
import com.kb.pojo.User;

public class AdminTest {

	public static void main(String[] args) {

		int userid;
		String username;
		String useremail;
		String usercontact;
		String userdob;
		String userpan;
		String useraadhar;
		String useraddress;
		String userpass;
		String userrole;
		User user;
		List<User> ulist;
		UserDaoImpl userdao = new UserDaoImpl();
		CustomException customException = new CustomException();
		boolean result;
		int choice;
		char ch;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("-----welcome to kuberbank-----");
			System.out.println("1.add user");
			System.out.println("2.update user");
			System.out.println("3.delete user");
			System.out.println("4.show user list");
			System.out.println("5.show user list with Account details");
			System.out.println("6.search user by id");
			System.out.println("7.search user by email");
			System.out.println("8.search user by account no");
			System.out.println("9.search user by aadhar");
			System.out.println("10.activate user account");
			System.out.println("11.deactivate user account");
			System.out.println("---------------------------------");

			System.out.println("Enter your choice:");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter user name:");
				username = sc.next();
				System.out.println("Enter user email:");
				useremail = sc.next();
				System.out.println("Enter user contact:");
				usercontact = sc.next();
				System.out.println("Enter user dob:");
				userdob = sc.next();
				System.out.println("Enter user pan:");
				userpan = sc.next();
				System.out.println("Enter user aadhar:");
				useraadhar = sc.next();
				System.out.println("Enter user address:");
				useraddress = sc.next();
				System.out.println("Enter user password:");
				userpass = sc.next();
				// user object is created without creating account object
				user = new User(username, useremail, usercontact, userdob, userpan, useraadhar, useraddress, userpass);
				try {
					result = userdao.addUser(user);
					if (result)
						System.out.println("user added successfully");
					else
						System.out.println("user not added");
				} catch (SQLException e) {

					e.printStackTrace();
				}
				break;
			case 2:
				System.out.println("Enter user id:");
				userid = sc.nextInt();
				System.out.println("Enter user name:");
				username = sc.next();
				System.out.println("Enter user email:");
				useremail = sc.next();
				System.out.println("Enter user contact:");
				usercontact = sc.next();
				System.out.println("Enter user dob:");
				userdob = sc.next();
				System.out.println("Enter user pan:");
				userpan = sc.next();
				System.out.println("Enter user aadhar:");
				useraadhar = sc.next();
				System.out.println("Enter user address:");
				useraddress = sc.next();
				System.out.println("Enter user password:");
				userpass = sc.next();
				user = new User(userid, username, useremail, usercontact, userdob, userpan, useraadhar, useraddress,
						userpass);
				try {
					result = userdao.updateUser(user);
					if (result)
						System.out.println("user details updated successfully");
					else
						System.out.println("user not updated");
				} catch (SQLException e) {

					e.printStackTrace();
				}
				break;

			case 3:
				System.out.println("Enter user id :");
				userid = sc.nextInt();
				try {
					result = userdao.deleteUser(userid);
					if (result) {
						System.out.println("user deleted ");
					} else {
						System.out.println("user not deleted ");
					}
				} catch (SQLException e) {

					e.printStackTrace();
				}

				break;

			case 4:
				System.out.println("---------user list-----------");
				try {
					ulist = userdao.showUserList();
					if (!ulist.isEmpty()) {
						for (User u : ulist) {
							System.out.println(u);
						}
					} else {
						System.out.println("userlist is empty");
					}
				} catch (SQLException e) {

					e.printStackTrace();
				}

				break;
			case 5:
				System.out.println("---------user list with account details-----------");
				try {
					ulist = userdao.showUserWithAccount();
					if (!ulist.isEmpty()) {
						for (User u : ulist) {
							System.out.println(u);
						}

					} else {
						System.out.println("userlist is empty");
					}
				} catch (SQLException e) {

					e.printStackTrace();
				}

				break;
			case 6:
				System.out.println("search user and account by user id:");
				userid = sc.nextInt();
				try {
					user = userdao.searchUserByUserid(userid);
					if (user != null) {
						System.out.println(user);
					} else {
						try {
							customException.UserNotFoundException(); // throwing UserNotFoundException
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 7:
				System.out.println("Enter user mail id :");
				useremail = sc.next();
				try {
					user = userdao.searchUserByEmail(useremail);
					if (user != null) {
						System.out.println(user);
					} else {
						try {
							customException.UserNotFoundException(); // throwing UserNotFoundException
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 8:

				System.out.println("Enter account no :");
				int accno = sc.nextInt();
				try {
					user = userdao.searchUserByAccountno(accno);
					if (user != null) {
						System.out.println(user);
					} else {
						try {
							customException.UserNotFoundException(); // throwing UserNotFoundException
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 9:
				System.out.println("Enter user aadhar no :");
				useraadhar = sc.next();
				try {
					user = userdao.searchUserByAadhar(useraadhar);
					if (user != null) {
						System.out.println(user);
					} else {
						try {
							customException.UserNotFoundException(); // throwing UserNotFoundException
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 10:
				System.out.println("Enter Account Number of User :");
				accno = sc.nextInt();
				try {
					result = userdao.activateUser(accno);
					if (result) {
						System.out.println("Account Successfully Activated");
					} else {
						System.out.println("Account is Not Activated");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 11:
				System.out.println("Enter Account Number of User :");
				accno = sc.nextInt();
				try {
					result = userdao.deactivateUser(accno);
					if (result) {
						System.out.println("Account Successfully Deactivated");
					} else {
						System.out.println("Account is Not Deactivated");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			}
			System.out.println("do you want to continue if yes enter y:");
			ch = sc.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');

		System.out.println("Redirect To Login Page If Yes Enter y: ");
		char ch1 = sc.next().charAt(0);

		if (ch1 == 'y' || ch1 == 'Y') {
			Test.main(null);
		}
		sc.close();
	}
}
