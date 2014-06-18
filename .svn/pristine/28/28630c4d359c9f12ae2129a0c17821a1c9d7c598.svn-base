package quizweb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySQLUtil {
	
	private static final String AND_OR_KEY = "and/or";
	
	/**
	 * generates a query from MySQL in the format of SELECT ___ FROM ___
	 * @param toReturn string that should follow immediately after SELECT in the query (what information the query should return)
	 * @param table string that should follow immediately after FROM in the query (the name of the table to be used)
	 * @return list of resulting objects generated from the query
	 */
	public static List<Object> getQuery(Connection conn, String toReturn, String table) {
		List<Object> result = genericGetQuery(conn, toReturn, table, null, "");
		return result;
	}
	
	/**
	 * generates a query from MySQL in the format of SELECT ___ FROM ___ ORDER BY ___
	 * @param toReturn string that should follow immediately after SELECT in the query (what information the query should return)
	 * @param table string that should follow immediately after FROM in the query (the name of the table to be used)
	 * @param order string that should follow immediately after ORDER BY in the query
	 * @return list of resulting objects generated from the query
	 */
	public static List<Object> getQuery(Connection conn, String toReturn, String table, String order) {
		List<Object> result = genericGetQuery(conn, toReturn, table, null, order);
		return result;
	}
	
	/**
	 * generates a query from MySQL in the format of SELECT ___ FROM ___ WHERE ___ = ___ (AND ___ = ___)
	 * @param toReturn string that should follow immediately after SELECT in the query (what information the query should return)
	 * @param table string that should follow immediately after FROM in the query (the name of the table to be used)
	 * @param params a map from strings to objects such that the query will set parameters given "Key = Value" (eg. WHERE key1 = value1 AND key2 = value2)
	 * @param and true if AND should be used in query, and false if OR should be used.
	 * @return list of resulting objects generated from the query
	 */
	public static List<Object> getQuery(Connection conn, String toReturn, String table, Map<String,Object> params, boolean and) {
		params.put(AND_OR_KEY, and);
		List<Object> result = genericGetQuery(conn, toReturn, table, params, "");
		return result;
	}
	
	/**
	 * generates a query from MySQL in the format of SELECT ___ FROM ___ WHERE ___ = ___ (AND ___ = ___) ORDER BY ___
	 * @param toReturn string that should follow immediately after SELECT in the query (what information the query should return)
	 * @param table string that should follow immediately after FROM in the query (the name of the table to be used)
	 * @param params a map from strings to objects such that the query will set parameters given "Key = Value" (eg. WHERE key1 = value1 AND key2 = value2)
	 * @param and true if AND should be used in query, and false if OR should be used.
	 * @param order string that should follow immediately after ORDER BY in the query
	 * @return list of resulting objects generated from the query
	 */
	public static List<Object> getQuery(Connection conn, String toReturn, String table, Map<String,Object> params, boolean and, String order) {
		params.put(AND_OR_KEY, and);
		List<Object> result = genericGetQuery(conn, toReturn, table, params, order);
		return result;
	}
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
		    conn = DriverManager.getConnection("jdbc:mysql://"+MyDBInfo.MYSQL_DATABASE_SERVER+"/"+MyDBInfo.MYSQL_DATABASE_NAME+"?"+
		                                   "user="+MyDBInfo.MYSQL_USERNAME+"&password="+MyDBInfo.MYSQL_PASSWORD);

		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}
	

	private static List<Object> genericGetQuery(Connection conn, String toReturn, String table,
			Map<String, Object> params, String order) {
		List<Object> result = new ArrayList<Object>();
		Statement stmt = null;
		ResultSet rs = null;
		if(conn!=null){
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(generateQueryString(toReturn,table,params,order));
				while(rs.next()){
					ResultSetMetaData rsmd = rs.getMetaData();
					int numCol = rsmd.getColumnCount();
					if (numCol > 1) {
						List<Object> row = new ArrayList<Object>();
						for (int i = 1; i <= numCol; i++) {
							row.add(rs.getObject(i));
						}
						result.add(row);
					} else {
						result.add(rs.getObject(1));
					}

				}
				
			} catch (SQLException e) {
			    System.out.println("SQLException: " + e.getMessage());
			}
		}
		return result;
	}
	
	private static String generateQueryString(String toReturn, String table, Map<String,Object> params, String order) {
		String query = "SELECT "+toReturn+" FROM "+table;
		if (params != null) {
			query = addParams(query,params);
		}
		if (!order.equals("")) {
			query += " ORDER BY "+order;
		}
		query += ";";
		return query;
	}

	private static String addParams(String query, Map<String, Object> params) {
		int iter = 0;
		String addOn = "";
		String andOr = "OR";
		if ((Boolean) params.get(AND_OR_KEY)) {
			andOr = "AND";
		}
		for (String key: params.keySet()) {
			if (!key.equals(AND_OR_KEY)) {
				String paramString = "";
				if (params.get(key) instanceof String) {
					paramString = (String) params.get(key);
					addOn = "\"";
				} else {
					paramString = Integer.toString((Integer) params.get(key));
					addOn = "";
				}
				String[] paramArray = paramString.split(",");
				for (int i = 0; i < paramArray.length; i++) {
					if (iter == 0) {
						query += " WHERE "+key+" = "+addOn+paramArray[i]+addOn;
					} else {
						query += " "+andOr+" "+key+" = "+addOn+paramArray[i]+addOn;
					}
					iter ++;
				}

			}
		}
		return query;
	}

	
}
