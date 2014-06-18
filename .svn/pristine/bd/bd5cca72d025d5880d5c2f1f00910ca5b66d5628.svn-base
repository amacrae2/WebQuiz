package quizweb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import java.sql.*;

public class AccountManager {
	/* constant */
	private static final String ALGORITHM = "SHA";
	
	/* instance variable */
	private Statement stmt;
	
	public AccountManager(Statement stmt) {
		this.stmt = stmt;
	}
	
	/* when logging in, check if the account exists */
	public boolean checkAccountExists(String accountName) throws SQLException {
		// search for the account name in database
		ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username='"+accountName+"';");
		 
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}
	
	/* when logging in, check if the password is correct */
	public boolean checkPassword(String accountName, String password) throws SQLException {
		if (checkAccountExists(accountName)) {
			// get the stored salt and password digest
			ResultSet rs = stmt.executeQuery("SELECT salt,digest FROM users WHERE username='"+accountName+"';");
			rs.next();
			String salt = rs.getString("salt");
			String passwordDigest = rs.getString("digest");
			
			// hash the typed password
			String saltedTypedPassword = password + salt;
			String typedPasswordDigest = computeHash(saltedTypedPassword);
			
			// check password
			if (typedPasswordDigest.equals(passwordDigest)) {
				return true;
			}			
		}
		return false;	
	}
	
	/* create a new account if the account doesn't exist already 
	 * return whether it has been successfully done
	 */
	public boolean createNewAccount(String accountName, String password) throws SQLException {	
		if (!checkAccountExists(accountName) && accountName != "" && password != "") {
			// generate the salt
			Random random = new Random();
			int salt = random.nextInt(100);
			String strSalt = Integer.toString(salt);
			
			// hash the password
			String saltedPassword = password + salt;
			String passwordDigest = computeHash(saltedPassword);
			
			// create new account
			stmt.executeUpdate("INSERT INTO users (username, salt, digest) VALUES ('"+accountName+"', '"+strSalt+"', '"+passwordDigest+"');");
			return true;
		}
		return false;
	}
	
	/* compute the hash value given a string */
	public static String computeHash(String password) {
		String result = null;
		
		// convert the string of given password into byte
		byte[] passwordByte = password.getBytes();
		
		byte[] digestByte;		
		try {
			// compute the digest
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			digestByte = md.digest(passwordByte);
			
			// convert the digest into hex string
			result = hexToString(digestByte);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
}
