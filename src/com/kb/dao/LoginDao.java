package com.kb.dao;

import java.sql.SQLException;

public interface LoginDao {

	boolean isAdmin(String useremail, String userpass) throws SQLException;

	boolean searchAdmin(String useremail, String userpass) throws SQLException;
}
