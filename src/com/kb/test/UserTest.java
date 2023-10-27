package com.kb.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.kb.dao.AccountDaoImpl;
import com.kb.pojo.Account;
import com.kb.pojo.User;
import com.kb.utility.DBConnection;

public class UserTest {

	public static void main(User user) {

		Connection conn = DBConnection.getConnect();
		String sql;
		ResultSet rs;
		int result;
		HashMap<Integer, Integer> map;
		AccountDaoImpl accountdao = new AccountDaoImpl();

		int userid = user.getUserid();
		Account account = user.getAccount();
		int accno = account.getAccount_no();
		int payeeaccno;

		Scanner sc = new Scanner(System.in);
		char ch;
		double amt;

		System.out.println("------------Logged in Succesfully------------");

		do {

			System.out.println("1.Check Account Details ");
			System.out.println("2.Check Balance ");
			System.out.println("3.Withdraw ");
			System.out.println("4.Deposit ");
			System.out.println("5.Transfer ");
			System.out.println("6.Add Payee ");
			System.out.println("7.Remove Payee ");
			System.out.println("Please Select Option :");

			int choice = sc.nextInt();

			switch (choice) {
			case 1:

				System.out.println(user);

				break;

			case 2:
				try {
					System.out.println("Balance :" + accountdao.showBalance(accno) + " rs");
				} catch (SQLException e) {

					e.printStackTrace();
				}

				break;

			case 3:
				System.out.println("Enter Amount To be Withdrawn :");
				amt = sc.nextDouble();
				try {
					boolean success = accountdao.withdraw(accno, amt);

					if (success) {
						System.out.println("amount " + amt + " is withdrawn from account");
					} else {
						System.out.println("amount is not withdrawn from account");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 4:
				System.out.println("Enter Amount To be Deposited :");
				amt = sc.nextDouble();
				try {
					boolean success = accountdao.deposit(accno, amt);

					if (success) {
						System.out.println("amount " + amt + " is deposited to account");
					} else {
						System.out.println("amount is not deposited to account");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 5:
				System.out.println("1.Transfer to Payee");
				System.out.println("2.Transfer to Another Account");
				System.out.println("Select Option :");
				int opt = sc.nextInt();

				if (opt == 1) {
					try {
						map = accountdao.showPayee(accno);

						Set<Map.Entry<Integer, Integer>> set = map.entrySet();

						System.out.println("option :" + " payee account number");
						for (Map.Entry<Integer, Integer> e : set) {
							System.out.println(e.getKey() + "      :" + e.getValue());
						}
						System.out.println("Select Option :");
						int opt1 = sc.nextInt();
						if (map.containsKey(opt1)) {

							payeeaccno = map.get(opt1);

							System.out.println("Enter Amount To be Transferred :");
							amt = sc.nextDouble();
							System.out.println("Enter Type of Transaction :");
							String ttype = sc.next();

							LocalDateTime dt = LocalDateTime.now();

							DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
							String curDate = dt.format(df);

							try {
								boolean success = accountdao.transfer(accno, payeeaccno, amt, ttype, curDate);
								if (success) {
									System.out.println("Amount " + amt + " Succesfully Transferred To Account Number "
											+ payeeaccno);
								} else {
									System.out.println("Amount Transfer Unsuccessfull");
								}
							} catch (SQLException e) {

								e.printStackTrace();
							}

						} else {
							System.out.println("Please Select Right Option");
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}

				} else if (opt == 2) {
					System.out.println("Enter Account Number of Recipient :");
					payeeaccno = sc.nextInt();
					System.out.println("Enter Amount To be Transferred :");
					amt = sc.nextDouble();
					System.out.println("Enter Type of Transaction :");
					String ttype = sc.next();

					LocalDateTime dt = LocalDateTime.now();

					DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					String curDate = dt.format(df);

					try {
						boolean success = accountdao.transfer(accno, payeeaccno, amt, ttype, curDate);
						if (success) {
							System.out.println(
									"Amount " + amt + " Succesfully Transferred To Account Number " + payeeaccno);
						} else {
							System.out.println("Amount Transfer Unsuccessfull");
						}
					} catch (SQLException e) {

						e.printStackTrace();
					}
				}

				break;

			case 6:
				System.out.println("Enter Payee Account Number :");
				payeeaccno = sc.nextInt();

				try {
					boolean isAdded = accountdao.addPayee(accno, payeeaccno);
					if (isAdded) {
						System.out.println("Payee is Added");
					} else {
						System.out.println("Payee is Not Added");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 7:
				System.out.println("Enter Payee Account Number :");
				payeeaccno = sc.nextInt();

				try {
					boolean isRemoved = accountdao.removePayee(accno, payeeaccno);
					if (isRemoved) {
						System.out.println("Payee is Removed");
					} else {
						System.out.println("Payee is Not Removed");
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
