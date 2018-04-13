/* ========================================================================================================
 * Course:       CSC 460 DataBase Design
 * Assignment:   Final Project - Database-driven Web Application
 * Instructor:   Dr. Lester I. McCann
 * TAs:          Anand Vastrad, Theodore Sackos
 * Members:      XUEHENG WAN
 *               RUYI ZUO
 *               WENKANG ZHOU
 *               YANG HONG
 * Due date:     December 5th, 2017
 * --------------------------------------------------------------------------------------------------------
 * Description:  This program is a database-driven web information management system implementation
 *               which interacts with oracle.aloe on Lectura.
 *
 *               This project includes 3 parts:
 *                  1) Database Back-End
 *                  2) The business logic and data processing layer
 *                  3) Web Front-End
 *               This java file is the 1st part of the project, it accepts instructions, commands and
 *               user input from Web Front-End and conducts information processes, then it passes
 *               proper instructions/queries into the actual database stored on Lectura_oracle.aloe
 *               and gets proper query results back for further processing and displaying.
 *
 *               It takes <username> <password> in order to grant access to oracle.
 *               It is embedded with multiple queries as default for user to choose from.
 *               It is embedded with multiple basic operations to interact with the database.
 *               For further information on how to use the program, please see README.txt.
 * --------------------------------------------------------------------------------------------------------
 * Requirements: 1) JavaSE-1.8 (backward compatible)
 *                  or
 *                  JavaSE-1.7 (minimum requirement)
 *               2) User needs to provide username and password for oracle.aloe (built-in)
 *               3) To correctly compile this program on Lectura, please configure:
 *                  3.1) JavaSE-1.7 (optional, if 1.8 is not available)
 *                       export JAVA_HOME=/usr/local/jdk
 *                  3.2) Tomcat home directory
 *                       export CATALINA_HOME=/home/hong1/tomcat
 *                  3.3) Testing
 *                       echo $JAVA_HOME
 *                       echo $CATALINA_HOME
 *                  3.4) SSH forwarding
 *                       ssh -L <port number>:localhost:<port number> <username>@lectura.cs.arizona.edu
 *               4) Compilation: javac DatabaseController.java
 *               5) Execution: will be excuted by Web Front-End
 * --------------------------------------------------------------------------------------------------------
 * Variables:    serialVersionUID             : long
 *               username                     : String
 *               password                     : String
 *               tables                       : HashMap<String, String>
 *               pks                          : HashMap<String, String>
 *               dbconn                       : Connection
 *               stmt                         : Statement
 *               customer_col                 : List<String>
 *               customer_typ                 : boolean[]
 *               model_col                    : List<String>
 *               model_typ                    : boolean[]
 *               contract_col                 : List<String>
 *               contract_typ                 : boolean[]
 *               department_col               : List<String>
 *               department_typ               : boolean[]
 *               part_col                     : List<String>
 *               part_typ                     : boolean[]
 *               cont_part_col                : List<String>
 *               cont_part_typ                : boolean[]
 *               model_part_col               : List<String>
 *               model_part_typ               : boolean[]
 * --------------------------------------------------------------------------------------------------------
 * Methods:      DatabaseController           ()                                 : Constructor
 *               DatabaseController           (String)                           : Constructor
 *               EstablishConnection          ()                                 : void
 *               termination                  ()                                 : void
 *               query                        (String)         	         	 : ResultSet
 *               insert                       (String, String, String)         	 : ResultSet
 *               update                       (String, String, String)           : ResultSet
 *               delete                       (String, String)                   : ResultSet
 *               insertNewMember              (String[])                         : boolean
 *               verifyingMember              (String, String)                   : int
 *               getModels                    ()                                 : List<String[]>
 *               getTable                     (String)                           : List<String[]>
 *               getModelParts                (String)                           : List<String[]>
 *               getModelFeatures             (String)                           : List<String[]>
 *               getCustIDbyUsername          (String)                           : String
 *               modelCostQuery               (String)                           : String
 *               getBuildingShips             ()                                 : List<String[]>
 *               getMoneySpentPerCustomer     ()                                 : List<String[]>
 *               getMoneySpentHighestCustomer ()                                 : String[]
 *               getDepartmentRevenue	      ()                                 : List<String[]>
 *               getPartByStatus              (int)                              : List<String[]>
 *               getContractsByUsername       (String)                           : List<String[]>
 *               getPriceByConID              (String)                           : String
 *               getPartStatusByContID        (String)                           : List<String[]>
 *               insertion                    (String, String[], String[])       : boolean
 *               insertModel                  (String, String, String, String[]) : boolean
 *               insertDepartment             (String, String)                   : boolean
 *               insertPart                   (String, String)                   : boolean
 *               makeNewContract              (String, String)                   : boolean
 *               updatePartPrice              (String, String)                   : boolean
 *               updateModelLabor             (String, String)                   : boolean
 *               updateShipMarkup             (String, String)                   : boolean
 *               deleteContract               (String)                           : boolean
 *               getCurrentCostByContID       (String)                           : String
 *               conditionFormator1           (String, List<String>, String[]) 	 : String
 *               conditionFormator2           (String, List<String>, String[]) 	 : String
 *               conditionFormator3           (String, List<String>, String[]) 	 : String
 *               conditionFormator4           (String, List<String>, String[]) 	 : String
 *               conditionFormator            (String[])                         : String
 *               getColumns                   (String)                           : List<String[]>
 *               getTypes                     (String)                           : boolean[]
 *               TablesIndexing               ()                                 : void
 *               toLowerCaseArr               (String[])                         : String[]
 *               IDGenerator                  (String)                           : int
 *               departmentSelector           (String)                           : String
 *               checkExisitingPK             (String, String)                   : boolean
 *               checkExisitingColumn         (String, String)                   : boolean
 *               findRowExisitng              (String, String, String)           : boolean
 * --------------------------------------------------------------------------------------------------------
 * Missing
 * Features:     None
 *
 * Known Bug(s): None
 * ========================================================================================================
 */

package dbController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DatabaseController {
	final long serialVersionUID = 1L;

	String username = "hong1";
	String password = "hong1pass";
	public HashMap<String, String> tables;
	public HashMap<String, String> pks;
	Connection dbconn;
	Statement stmt;
	List<String> customer_col = Arrays
			.asList(new String[] { "cusid", "firstname", "lastname", "address", "username", "password", "user_type" });
	boolean[] customer_typ = { true, false, false, false, false, false, false };
	List<String> model_col = Arrays.asList(new String[] { "mid", "name", "labor", "markup", "did"});
	boolean[] model_typ = { true, false, false, false, true};
	List<String> contract_col = Arrays.asList(new String[] { "contid", "cusid", "mid", "invoice", "timestamp" });
	boolean[] contract_typ = { true, true, true, false, false };
	List<String> department_col = Arrays.asList(new String[] { "did", "mid", "name" });
	boolean[] department_typ = { true, true, false };
	List<String> part_col = Arrays.asList(new String[] { "pid", "name", "price" });
	boolean[] part_typ = { true, false, true };
	List<String> cont_part_col = Arrays.asList(new String[] { "contid", "pid", "status" });
	boolean[] cont_part_typ = { true, true, true };
	List<String> model_part_col = Arrays.asList(new String[] { "mid", "pid" });
	boolean[] model_part_typ = { true, true };

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public DatabaseController() - Constructor
	 *
	 * @param
	 * @return
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public DatabaseController() {
		TablesIndexing();
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public DatabaseController(String a) - Constructor_Test
	 *
	 * @param
	 * @return
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public DatabaseController(String a) {
		TablesIndexing();
		String query = "SELECT user_type FROM " + tables.get("customer") + " WHERE username='" + username
				+ "' AND Password='" + password + "'";
		System.out.println(query);
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public void EstablishConnection()
	 *
	 * @param
	 * @return
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public void EstablishConnection() {
		final String oracleURL = // Magic lectura -> aloe access spell
				"jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("*** ClassNotFoundException:  " + "Error loading Oracle JDBC driver.  \n"
					+ "\tPerhaps the driver is not on the Classpath?");
			System.exit(-1);
		}
		// Oracle database
		dbconn = null;
		try {
			dbconn = DriverManager.getConnection(oracleURL, username, password);
		} catch (SQLException e) {
			System.err.println("*** SQLException:  Could not open JDBC connection.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);
		}
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public void termination()
	 *
	 * @param
	 * @return
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public void termination() {
		// Connection Termination
		try {
			dbconn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Database terminating failed!");
		}
	}

	/******************************
	 * Basic Database Operation Functions
	 ******************************/

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public ResultSet query(String) - DB Query
	 *
	 * @param  query
	 * @return ResultSet
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public ResultSet query(String query) {
		System.out.println(query);
		// Statement stmt = null;
		ResultSet answer = null;
		try {
			stmt = dbconn.createStatement();
			answer = stmt.executeQuery(query);
			/*
			 * ResultSetMetaData answermetadata = answer.getMetaData(); for (int
			 * i = 1; i <= answermetadata.getColumnCount(); i++)
			 * System.out.print(answermetadata.getColumnName(i) + "\t");
			 * System.out.println();
			 */
		} catch (SQLException e) {
			System.err.println("*** SQLException:  " + "Could not fetch query results.");
			System.err.println("\tMessage:   " + e.getMessage());
			System.err.println("\tSQLState:  " + e.getSQLState());
			System.err.println("\tErrorCode: " + e.getErrorCode());
			System.exit(-1);
		}
		return answer;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private ResultSet insert(String, String, String) - DB Insertion
	 *
	 * @param  table
	 * @param  columns
	 * @param  values
	 * @return ResultSet
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private ResultSet insert(String table, String columns, String values) {
		String query = "INSERT INTO " + table + "(" + columns + ") VALUES (" + values + ")";
		return query(query);
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private ResultSet update(String, String, String) - DB Update
	 *
	 * @param  table
	 * @param  values
	 * @param  conditions
	 * @return ResultSet
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private ResultSet update(String table, String values, String conditions) {
		String query = "UPDATE " + tables.get(table) + " SET " + values + " WHERE " + conditions;
		return query(query);
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private ResultSet delete(String, String) - DB Deletion
	 *
	 * @param  table
	 * @param  conditions
	 * @return ResultSet
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private ResultSet delete(String table, String conditions) {
		String query = "DELETE FROM " + tables.get(table) + " WHERE " + conditions;
		return query(query);
	}

	/******************************
	 * Database Operation Functions
	 ******************************/

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean insertNewMember(String[]) - For LogIn Button
	 *                                          - Return false - failedToBeInserted
	 *                                          - Return true  - successToBeInserted
	 *
	 * @param  parameters
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean insertNewMember(String[] parameters) throws SQLException {
		if (checkExisitingColumn("customer", parameters[3]))
			return false;
		String[] info = { String.valueOf(IDGenerator("customer")), parameters[0], parameters[1], parameters[2],
				parameters[3], parameters[4], "" };
		insert(tables.get("customer"), conditionFormator((String[]) customer_col.toArray()),
				conditionFormator1("customer", getColumns("customer"), info));
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public int verifyingMember(String, String) - For SignUp Button
	 *                                            - Return -1 no such user exists or wrong password enetered;
	 *                                                      0 - member;
	 *                                                      1 - administrator.
	 * 
	 * @param  u
	 * @param  p
	 * @return int
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public int verifyingMember(String u, String p) throws SQLException {
		String query = "SELECT user_type FROM " + tables.get("customer") + " WHERE username='" + u + "' AND Password='"
				+ p + "'";
		ResultSet rs = query(query);
		if (!rs.next())
			return -1;
		String group = rs.getString("user_type");
		stmt.close();
		if (group != null)
			return 1;
		else
			return 0;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String[]> getModels() - For model table
	 *                                   - Return all models we have
	 * 
	 * @param
	 * @return List<String>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public List<String[]> getModels() throws SQLException {
		String query = "SELECT mid, name FROM " + tables.get("model");
		ResultSet rs = query(query);
		List<String[]> list = new ArrayList<String[]>();
		while (rs.next())
			list.add(new String[] { String.valueOf(rs.getInt("MID")), rs.getString("NAME") });
		return list;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String[]> getTable(String) - For given table
	 *                                        - Return rows for given table
	 * 
	 * @param  table
	 * @return List<String>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public List<String[]> getTable(String table) throws SQLException {
		String query = "SELECT * FROM " + tables.get(table);
		ResultSet rs = query(query);
		List<String[]> list = new ArrayList<String[]>();
		List<String> cols = getColumns(table);
		while (rs.next()) {
			String[] strs = new String[cols.size()];
			for (int i = 0; i < strs.length; i++) {
				strs[i] = rs.getString(cols.get(i));
			}
			list.add(strs);
		}
		return list;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String> getModelParts(String) - For one model
	 *                                           - Return ALL Parts of a model
	 * 
	 * @param  mid
	 * @return List<String>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public List<String> getModelParts(String mid) throws SQLException {
		String query = "SELECT " + tables.get("part") + ".name FROM " + tables.get("part") + ", "
				+ tables.get("model_part") + " WHERE " + tables.get("model_part") + ".mid=" + mid + " AND "
				+ tables.get("model_part") + ".pid=" + tables.get("part") + ".pid";
		ResultSet rs = query(query);
		List<String> list = new ArrayList<String>();
		while (rs.next())
			list.add(rs.getString("NAME"));
		return list;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String> getModelFeatures(String) - For one model
	 *                                              - Return ALL Features of a model
	 * 
	 * @param  mid
	 * @return List<String>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/

	public List<String> getModelFeatures(String mid) throws SQLException {
		String query = "SELECT " + tables.get("part") + ".name FROM " + tables.get("part") + ", "
				+ tables.get("model_part") + " WHERE " + tables.get("model_part") + ".mid=" + mid + " AND "
				+ tables.get("model_part") + ".pid=" + tables.get("part") + ".pid" + " AND " + tables.get("part")
				+ ".pid!=10001 AND " + tables.get("part") + ".pid!=20001 AND " + tables.get("part") + ".pid!=80001";
		ResultSet rs = query(query);
		List<String> list = new ArrayList<String>();
		while (rs.next())
			list.add(rs.getString("NAME"));
		return list;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public String getCustIDbyUsername(String) - get CustID by username
	 * 
	 * @param  username
	 * @return String
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public String getCustIDbyUsername(String username) throws SQLException {
		String query = "SELECT cusid FROM " + tables.get("customer") + " WHERE username='" + username + "'";
		ResultSet rs = query(query);
		rs.next();
		return rs.getString("CUSID");
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public String modelCostQuery(String) - For Query 1
	 *                                      - Choosing one from a list of ship types, report the cost of all the parts.
	 * 
	 * @param  mid
	 * @return String
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/	public String modelCostQuery(String mid) throws SQLException {
		String query = "SELECT SUM(PRICE) AS TotalCost FROM " + tables.get("model_part") + ", " + tables.get("part")
				+ " WHERE mid" + "=" + mid + " AND " + tables.get("model_part") + ".pid=" + tables.get("part") + ".pid";
		ResultSet rs = query(query);
		rs.next();
		return rs.getString("TotalCost");
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String[]> getBuildingShips() - For Query 2
	 *                                          - List all ships that are partially built but incomplete.
	 * 
	 * @param
	 * @return List<String[]>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public List<String[]> getBuildingShips() throws SQLException {
		String query = "SELECT hong1.contract.contID, hong1.model.mid, hong1.model.name, Developed_Part, In_Progress_Part "
				+ "FROM hong1.model, hong1.contract, "
				+ "(SELECT ContID AS CD, COUNT(status) AS Developed_Part FROM hong1.cont_part WHERE status=2 GROUP BY ContID),"
				+ "(SELECT ContID AS CI, COUNT(status) AS In_Progress_Part FROM hong1.cont_part WHERE status<>2 GROUP BY ContID) "
				+ "WHERE "
				+ "hong1.model.mid=hong1.contract.mid AND hong1.contract.contid=CD AND hong1.contract.contid=CI AND CD>0 AND CI>0";
		ResultSet rs = query(query);
		List<String[]> list = new ArrayList<>();
		while (rs.next()) {
			String[] strs = { String.valueOf(rs.getInt("contID")), String.valueOf(rs.getInt("mid")),
					rs.getString("name"), rs.getString("Developed_Part"), rs.getString("In_Progress_Part") };
			list.add(strs);
		}
		return list;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String[]> getMoneySpentPerCustomer() - For Query 3.1
	 *                                                  - List money spent of each customer
	 * 
	 * @param
	 * @return List<String[]>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public List<String[]> getMoneySpentPerCustomer() throws SQLException {
		String query = "select cusid, firstname, lastname, money_spent from "
				+ "(select cusid, sum(total_price) as money_spent from "
				+ "(select contid, mid, cusid, ((cont_price+labor)*markup/10) as total_price from "
				+ "(select contid, mid, cusid, sum(price) as cont_price " + "from hong1.contract  "
				+ "join hong1.cont_part using (contid) " + "join hong1.part using (pid) "
				+ "group by contid, mid, cusid) " + "join hong1.model using (mid)) "
				+ "join hong1.customer using (cusid) " + "group by cusid) " + "join hong1.customer using (cusid)";
		ResultSet rs = query(query);
		List<String[]> list = new ArrayList<>();
		while (rs.next()) {
			String[] strs = { String.valueOf(rs.getInt("CUSID")), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
					rs.getString("MONEY_SPENT") };
			list.add(strs);
		}
		return list;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public String[] getMoneySpentHighestCustomer() - For Query 3.2
	 *                                                - List the customer who spent the most amount of money
	 * 
	 * @param
	 * @return String[]
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public String[] getMoneySpentHighestCustomer() throws SQLException {
		String query = "select cusid, firstname, lastname, address, money_spent as most_spent from " + "(select * from "
				+ "(select cusid, sum(total_price) as money_spent from "
				+ "(select contid, mid, cusid, ((cont_price+labor)*markup/10) as total_price from "
				+ "(select contid, mid, cusid, sum(price) as cont_price " + "from hong1.contract  "
				+ "join hong1.cont_part using (contid) " + "join hong1.part using (pid) "
				+ "group by contid, mid, cusid) " + "join hong1.model using (mid)) "
				+ "join hong1.customer using (cusid) " + "group by cusid " + "order by money_spent desc) "
				+ "where rownum <= 1) " + "join hong1.customer using (cusid)";
		ResultSet rs = query(query);
		rs.next();
		String[] strs = { String.valueOf(rs.getInt("CUSID")), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
				rs.getString("ADDRESS"), rs.getString("MOST_SPENT") };
		return strs;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String[]> getDepartmentRevenue() - For Query 4.1
	 *                                              - List all total revenue of each department
	 *                                              - !!!! RETURN VALUE PROBLEMS !!!
	 * 
	 * @param
	 * @return List<String[]>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public List<String[]> getDepartmentRevenue() throws SQLException {
		String query = "SELECT hong1.department.name, hong1.department.did, sum(invoice) AS REVENUE FROM "
				+ "hong1.contract, hong1.model, hong1.department  "
				+ "WHERE hong1.model.did=hong1.department.did AND hong1.model.mid=hong1.contract.mid "
				+ "GROUP BY hong1.department.name, hong1.department.did ";
		ResultSet rs = query(query);
		List<String[]> list = new ArrayList<>();
		while (rs.next()) {
			String[] strs = { rs.getString("NAME"), rs.getString("DID"),
					rs.getString("REVENUE") };
			list.add(strs);
		}
		return list;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String[]> getPartByStatus(int) - For Query 4.2
	 *                                            - List part with given status
	 * 
	 * @param  status
	 * @return List<String[]>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public List<String[]> getPartByStatus(int status) throws SQLException {
		String query = "select pid, name, status, quantity, price as price_each "
				+ "from ( select pid, status, count(pid) as quantity " + "from ( select pid, status "
				+ "from hong1.cont_part where status=" + status + ")" + "group by pid, status) "
				+ "join hong1.part using (pid) " + "order by quantity desc";
		ResultSet rs = query(query);
		List<String[]> list = new ArrayList<>();
		while (rs.next()) {
			String[] strs = { String.valueOf(rs.getInt("PID")), rs.getString("NAME"),
					String.valueOf(rs.getInt("STATUS")), rs.getString("QUANTITY"), rs.getString("PRICE_EACH") };
			list.add(strs);
		}
		return list;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String[]> getContractsByUsername(String) - For Purchase History Button
	 *                                                      - List all contracts of given customer
	 * 
	 * @param  un
	 * @return List<String[]>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public List<String[]> getContractsByUsername(String un) throws SQLException {
		String query = "SELECT CONTID, hong1.model.name, timestamp, invoice "
				+ "FROM hong1.model, hong1.contract, hong1.customer "
				+ "WHERE hong1.contract.cusid=hong1.customer.cusid AND hong1.contract.mid=hong1.model.mid "
				+ "AND hong1.customer.username='" + un + "'";
		ResultSet rs = query(query);
		List<String[]> list = new ArrayList<>();
		while (rs.next()) {
			String[] strs = { rs.getString("CONTID"), rs.getString("NAME"), rs.getString("TIMESTAMP"),
					rs.getString("invoice") };
			list.add(strs);
		}
		return list;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public String getPriceByMID(String) - For contract creation, only be called while new contract created
	 *                                     - Compute Price for given MID
	 * 
	 * @param  MID
	 * @return String
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public String getPriceByMID(String MID) throws SQLException {
		String query = "SELECT (((Retail+Labor) * MARKUP)/10) AS PRICE FROM "
				+ "(SELECT SUM(price) AS Retail FROM hong1.model_part, hong1.part " + "WHERE hong1.model_part.mid="
				+ MID + " AND hong1.model_part.pid=hong1.part.pid), "
				+ "(SELECT labor, markup FROM hong1.model WHERE hong1.model.mid=10000)";
		ResultSet rs = query(query);
		rs.next();
		return rs.getString("PRICE");
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public String getPriceByConID(String) - Compute Price for given contractID
	 * @param  contid
	 * @return String
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public String getPriceByConID(String contid) throws SQLException {
		String query = "SELECT (((Retail+Labor) * MARKUP)/10) AS PRICE FROM " + "(SELECT SUM(price) AS Retail "
				+ "FROM hong1.contract, hong1.cont_part, hong1.part "
				+ "WHERE hong1.contract.contID=hong1.cont_part.contid AND hong1.cont_part.pid=hong1.part.pid AND hong1.contract.contID="
				+ contid + " " + "GROUP BY hong1.contract.contid), "
				+ "(SELECT labor, markup FROM hong1.contract, hong1.model WHERE hong1.contract.mid=hong1.model.mid AND hong1.contract.contID="
				+ contid + ") ";
		ResultSet rs = query(query);
		rs.next();
		return rs.getString("PRICE");
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String[]> getPartStatusByContID(String) - For each contract
	 *                                                     - List part status of given contract
	 * 
	 * @param  contID
	 * @return List<String[]>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public List<String[]> getPartStatusByContID(String contID) throws SQLException {
		String query = "SELECT hong1.part.PID, NAME, STATUS FROM hong1.contract, hong1.cont_part, hong1.part "
				+ "WHERE hong1.contract.contid=" + contID + " AND hong1.contract.contid=hong1.cont_part.contid "
				+ "AND hong1.cont_part.pid=hong1.part.pid ";
		ResultSet rs = query(query);
		List<String[]> list = new ArrayList<>();
		while (rs.next()) {
			String[] strs = { String.valueOf(rs.getInt("PID")), rs.getString("NAME"),
					String.valueOf(rs.getInt("STATUS")) };
			list.add(strs);
		}
		return list;
	}

	/******************************
	 * DML Functions
	 ******************************/

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean insertion(String, String[], String[]) - For DML Insertion
	 *                                                      - Insert row into certain table
	 * @param  table
	 * @param  columns
	 * @param  values
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean insertion(String table, String[] columns, String[] values) throws SQLException {
		if (pks.containsKey(table) && checkExisitingPK(table, values[0]))
			return false;
		insert(tables.get(table), conditionFormator(columns),
				conditionFormator1(table, Arrays.asList(columns), values));
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean insertModel(String, String, String, String[]) - For DML 1 - ships
	 * @param  name
	 * @param  labor
	 * @param  markup
	 * @param  parts
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean insertModel(String name, String labor, String markup, String[] parts) throws SQLException {
		if (parts.length < 3 || parts.length > 10)
			return false;
		for (String pid : parts)
			if (!findRowExisitng("part", getColumns("part").get(0), pid))
				return false;
		int mid = IDGenerator("model");
		// Assign a department
		String did = departmentSelector(String.valueOf(mid));
		if (!insertion("model", (String[]) getColumns("model").toArray(),
				new String[] { String.valueOf(mid), name, labor, markup, did}))
			return false;
		// Update MID in department
		String query = " UPDATE " + tables.get("department") + " SET mid=" + mid + " WHERE did=" + did;
		query(query);
		// Adding basic parts
		insertion("model_part", (String[]) getColumns("model_part").toArray(),
				new String[] { String.valueOf(mid), "10001" });
		insertion("model_part", (String[]) getColumns("model_part").toArray(),
				new String[] { String.valueOf(mid), "20001" });
		insertion("model_part", (String[]) getColumns("model_part").toArray(),
				new String[] { String.valueOf(mid), "80001" });
		// Adding featrues
		for (String pid : parts) {
			insertion("model_part", (String[]) getColumns("model_part").toArray(),
					new String[] { String.valueOf(mid), pid });
		}
		return true;
	}
	
	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean insertModel(String, String, String, String[]) - For DML 1 - ships
	 * @param  name
	 * @param  labor
	 * @param  markup
	 * @param  parts
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean insertModel(String name, String labor, String markup, String did, String[] parts) throws SQLException {
		if (parts.length < 3 || parts.length > 10)
			return false;
		for (String pid : parts)
			if (!findRowExisitng("part", getColumns("part").get(0), pid))
				return false;
		int mid = IDGenerator("model");
		if (!insertion("model", (String[]) getColumns("model").toArray(),
				new String[] { String.valueOf(mid), name, labor, markup, did}))
			return false;
		// Update MID in department
		String query = " UPDATE " + tables.get("department") + " SET mid=" + mid + " WHERE did=" + did;
		query(query);
		// Adding basic parts
		insertion("model_part", (String[]) getColumns("model_part").toArray(),
				new String[] { String.valueOf(mid), "10001" });
		insertion("model_part", (String[]) getColumns("model_part").toArray(),
				new String[] { String.valueOf(mid), "20001" });
		insertion("model_part", (String[]) getColumns("model_part").toArray(),
				new String[] { String.valueOf(mid), "80001" });
		// Adding featrues
		for (String pid : parts) {
			insertion("model_part", (String[]) getColumns("model_part").toArray(),
					new String[] { String.valueOf(mid), pid });
		}
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean insertDepartment(String, String name) - For DML 1 - departments
	 * @param  mid
	 * @param  name
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean insertDepartment(String name) throws SQLException {
		int did = IDGenerator("department");
		String[] values = {String.valueOf(did),"-1",name};
		insertion("department",(String[]) getColumns("department").toArray(),values);
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private String departmentSelector(String) - Helper method for
	 * makeNewContract, it finds a available department for producing newly
	 * designed model ship
	 * 
	 * @param mid
	 * @return String
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public String departmentSelector(String mid) throws SQLException {
		String query = "SELECT DID FROM " + tables.get("department") + " WHERE MID=-1";
		ResultSet rs = query(query);
		rs.next();
		return rs.getString("did");
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private boolean checkAvailableDepartment() - Return true if there is an
	 * available apartment that can be assigned to a model ship production
	 * 
	 * @param
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean checkAvailableDepartment() throws SQLException {
		String query = "SELECT * FROM " + tables.get("department") + " WHERE MID=-1";
		ResultSet rs = query(query);
		return rs.next();
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean insertPart(String, String) - For DML 1 - Part
	 * 
	 * @param  name
	 * @param  price
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean insertPart(String name, String price) throws SQLException {
		int pid = IDGenerator("part");
		insertion("part", (String[]) getColumns("part").toArray(), new String[] { String.valueOf(pid), name, price });
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean makeNewContract(String, String) - For DML 2 - Customer
	 *                                                - orders a ship
	 *                                                - Make a new contract
	 * 
	 * @param  username
	 * @param  mid
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean makeNewContract(String username, String mid) throws SQLException {
		String customerID = getCustIDbyUsername(username);
		if (!findRowExisitng("customer", getColumns("customer").get(0), customerID))
			return false;
		if (!findRowExisitng("model", getColumns("model").get(0), mid))
			return false;
		String timestamp = (new SimpleDateFormat("yyyyMMddHHmm").format(new Date())).toString();
		int contID = IDGenerator("contract");
		String invoice = getPriceByMID(mid);
		// insert contract to contract table
		insertion("contract", (String[]) getColumns("contract").toArray(),
				new String[] { String.valueOf(contID), customerID, mid, invoice, timestamp});
		// insert model-part to cont_part table
		String query = "SELECT " + tables.get("part") + ".pid FROM " + tables.get("part") + ", "
				+ tables.get("model_part") + " WHERE " + tables.get("model_part") + ".mid=" + mid + " AND "
				+ tables.get("model_part") + ".pid=" + tables.get("part") + ".pid";
		ResultSet rs = query(query);
		List<String> parts = new ArrayList<String>();
		while (rs.next())
			parts.add(rs.getString("pid"));
		for (String pid : parts) {
			insertion("cont_part", (String[]) getColumns("cont_part").toArray(),
					new String[] { String.valueOf(contID), pid, "0" });
		}
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean updatePartPrice(String, String) - For DML 3 - Updates
	 * 
	 * @param  price
	 * @param  mid
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean updatePartPrice(String price, String pid) throws SQLException {
		if (!findRowExisitng("part", getColumns("part").get(0), pid))
			return false;
		String query = "UPDATE " + tables.get("part") + " SET price=" + price + " WHERE pid=" + pid;
		ResultSet rs = query(query);
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean updateModelLabor(String, String)
	 * 
	 * @param  labor
	 * @param  mid
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean updateModelLabor(String labor, String mid) throws SQLException {
		if (!findRowExisitng("model", getColumns("model").get(2), labor))
			return false;
		if (!findRowExisitng("model", getColumns("model").get(0), mid))
			return false;
		String query = "UPDATE " + tables.get("model") + " SET labor=" + labor + " WHERE mid=" + mid;
		ResultSet rs = query(query);
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean updateShipMarkup(String, String)
	 * 
	 * @param  markup
	 * @param  mid
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean updateShipMarkup(String markup, String mid) throws SQLException {
		if (!findRowExisitng("model", getColumns("model").get(0), mid))
			return false;
		if (!findRowExisitng("model", getColumns("model").get(3), markup))
			return false;
		String query = "UPDATE " + tables.get("model") + " SET markup=" + markup + " WHERE mid=" + mid;
		ResultSet rs = query(query);
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String[]> updatePartStatusByContID(String, String, String) - For DML 5 - update
	 *                                                                        - Update part status of a contract
	 * 
	 * @param
	 * @return List<String[]>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean updatePartStatusByContID(String contid, String pid, String status) throws SQLException {
		if (!findRowExisitng("contract", getColumns("contract").get(0), contid))
			return false;
		if (!findRowExisitng("part", getColumns("part").get(0), pid))
			return false;
		if (!findRowExisitng2("cont_part", getColumns("cont_part").get(0), contid, getColumns("cont_part").get(1), pid))
			return false;
		String query = "UPDATE " + tables.get("cont_part") + " SET status=" + status + " WHERE contid=" + contid + " AND pid=" + pid;
		query(query);
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public List<String[]> getContractUnderConstruction() - For DML 4 - Deletion
	 *                                                      - Delete a ship under construction
	 * 
	 * @param
	 * @return List<String[]>
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public List<String[]> getContractUnderConstruction() throws SQLException {
		String query = "SELECT ContractID, hong1.customer.FIRSTNAME, hong1.customer.LASTNAME, hong1.model.name FROM "
				+ "(SELECT DISTINCT ContID AS ContractID FROM hong1.cont_part WHERE STATUS=1), "
				+ "hong1.customer, hong1.model, hong1.contract "
				+ "WHERE hong1.contract.contid=ContractID AND hong1.contract.mid=hong1.model.mid "
				+ "AND hong1.contract.cusid=hong1.customer.cusid";
		ResultSet rs = query(query);
		List<String[]> list = new ArrayList<>();
		while (rs.next()) {
			String[] strs = { String.valueOf(rs.getInt("CONTRACTID")), rs.getString("FIRSTNAME"),
					rs.getString("LASTNAME"), rs.getString("NAME") };
			list.add(strs);
		}
		return list;

	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public boolean deleteContract(String)
	 * 
	 * @param  contID
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public boolean deleteContract(String contID) throws SQLException {
		if (!findRowExisitng("contract", getColumns("contract").get(0), contID))
			return false;
		String query = "DELETE FROM " + tables.get("contract") + " WHERE contid=" + contID;
		ResultSet rs = query(query);
		query = "DELETE FROM " + tables.get("cont_part") + " WHERE contid=" + contID;
		query(query);
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * public String getCurrentCostByContID(String) - For DML 5
	 *                                              - Current cost of construction of given contract
	 * 
	 * @param  contID
	 * @return String
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	public String getCurrentCostByContID(String contID) throws SQLException {
		if (!findRowExisitng("contract", getColumns("contract").get(0), contID))
			return "INPUT ERROR";
		String query = "select contid, sum(price) as curr_cost " + "from (select contid, pid, status "
				+ "from hong1.cont_part " + "where contid=" + contID + " " + "and status > 0) "
				+ "join hong1.part using (pid) " + "group by contid";
		ResultSet rs = query(query);
		if(!rs.next()) return "0";
		return rs.getString("CURR_COST");
	}

	// Addition Functions for Better User Experience
	public List<String[]> getRefinedContractData() throws SQLException {
		String query = "SELECT hong1.contract.contid, hong1.customer.FIRSTNAME, hong1.customer.LASTNAME, hong1.model.name AS Model_Name, "
				+ "hong1.department.name AS Department_Name, hong1.contract.timestamp, hong1.contract.invoice FROM "
				+ "hong1.contract, hong1.model, hong1.department, hong1.customer "
				+ "WHERE hong1.contract.cusid=hong1.customer.cusid AND hong1.contract.mid=hong1.model.mid AND hong1.model.did=hong1.department.did";
		ResultSet rs = query(query);
		List<String[]> list = new ArrayList<String[]>();
		while (rs.next())
			list.add(new String[] { rs.getString("contid"), rs.getString("firstname"), rs.getString("lastname"),
					rs.getString("Model_Name"), rs.getString("Department_Name"), rs.getString("invoice"),
					rs.getString("timestamp") });
		return list;
	}
	
	
	/******************************
	 * Query Preparation Functions
	 ******************************/

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private String conditionFormator2(String, List<String>, String[]) - return "a='AB', b=2, c'fas'"
	 * 
	 * @param  table
	 * @param  columns
	 * @param  values
	 * @return String
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private String conditionFormator2(String table, List<String> columns, String[] values) {
		String str = "";
		List<String> cols = getColumns(table);
		boolean[] typs = getTypes(table);
		for (int i = 0; i < columns.size(); i++) {
			if (typs[cols.indexOf(columns.get(i))]) {
				str += (columns.get(i) + "=" + values[i]);
			} else {
				str += (columns.get(i) + "='" + values[i] + "'");
			}
			str += ", ";
		}
		return str.substring(0, str.length() - 2);
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private String conditionFormator3(String, List<String>, String[]) - return "'AB' AND 2 AND 'fas'"
	 * 
	 * @param  table
	 * @param  columns
	 * @param  values
	 * @return String
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private String conditionFormator3(String table, List<String> columns, String[] values) {
		String str = "";
		List<String> cols = getColumns(table);
		boolean[] typs = getTypes(table);
		for (int i = 0; i < columns.size(); i++) {
			if (typs[cols.indexOf(columns.get(i))]) {
				str += (values[i]);
			} else {
				str += ("'" + values[i] + "'");
			}
			str += " AND ";
		}
		return str.substring(0, str.length() - 4);
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private String conditionFormator4(String, List<String>, String[]) - return "a='AB' AND b=2 AND c'fas'"
	 * 
	 * @param  table
	 * @param  columns
	 * @param  values
	 * @return String
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private String conditionFormator4(String table, List<String> columns, String[] values) {
		String str = "";
		List<String> cols = getColumns(table);
		boolean[] typs = getTypes(table);
		for (int i = 0; i < columns.size(); i++) {
			if (typs[cols.indexOf(columns.get(i))]) {
				str += (columns.get(i) + "=" + values[i]);
			} else {
				str += (columns.get(i) + "='" + values[i] + "'");
			}
			str += " AND ";
		}
		return str.substring(0, str.length() - 4);
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private String conditionFormator1(String, List<String>, String[]) - return "'AB',2,'fas'"
	 * 
	 * @param  table
	 * @param  columns
	 * @param  values
	 * @return String
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private String conditionFormator1(String table, List<String> columns, String[] values) {
		String str = "";
		List<String> cols = getColumns(table);
		boolean[] typs = getTypes(table);
		for (int i = 0; i < columns.size(); i++) {
			if (typs[cols.indexOf(columns.get(i))]) {
				str += (values[i]);
			} else {
				str += ("'" + values[i] + "'");
			}
			str += ", ";
		}
		return str.substring(0, str.length() - 2);
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private String conditionFormator(String[])
	 * 
	 * @param  values
	 * @return String
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private String conditionFormator(String[] values) {
		String str = "";
		for (int i = 0; i < values.length; i++) {
			str += values[i] + ", ";
		}
		return str.substring(0, str.length() - 2);
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private List<String> getColumns(String)
	 * 
	 * @param  table
	 * @return List<String>
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private List<String> getColumns(String table) {
		switch (table) {
		case "customer":
			return customer_col;
		case "model":
			return model_col;
		case "contract":
			return contract_col;
		case "department":
			return department_col;
		case "part":
			return part_col;
		case "cont_part":
			return cont_part_col;
		case "model_part":
			return model_part_col;
		default:
			return null;
		}
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private boolean[] getTypes(String)
	 * 
	 * @param  table
	 * @return boolean[]
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private boolean[] getTypes(String table) {
		switch (table) {
		case "customer":
			return customer_typ;
		case "model":
			return model_typ;
		case "contract":
			return contract_typ;
		case "department":
			return department_typ;
		case "part":
			return part_typ;
		case "cont_part":
			return cont_part_typ;
		case "model_part":
			return model_part_typ;
		default:
			return null;
		}
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private void TablesIndexing()
	 * 
	 * @param
	 * @return
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private void TablesIndexing() {
		tables = new HashMap<String, String>();
		tables.put("model", username + ".model");
		tables.put("model_part", username + ".model_part");
		tables.put("part", username + ".part");
		tables.put("department", username + ".department");
		tables.put("contract", username + ".contract");
		tables.put("cont_part", username + ".cont_part");
		tables.put("customer", username + ".customer");
		pks = new HashMap<String, String>();
		pks.put("model", "MID");
		pks.put("part", "PID");
		pks.put("department", "DID");
		pks.put("contract", "ContID");
		pks.put("customer", "CusID");
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private String[] toLowerCaseArr(String[])
	 * 
	 * @param  arr
	 * @return String[]
	 * @throws 
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private String[] toLowerCaseArr(String[] arr) {
		String[] strs = new String[arr.length];
		for (int i = 0; i < arr.length; i++)
			strs[i] = arr[i].toLowerCase();
		return strs;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private int IDGenerator(String) - Assign new ID for given table
	 * 
	 * @param  table
	 * @return int
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private int IDGenerator(String table) throws SQLException {
		String query = "SELECT " + getColumns(table).get(0) + " FROM " + tables.get(table);
		ResultSet rs = query(query);
		int last = 0;
		while (rs.next())
			last = rs.getInt(getColumns(table).get(0));
		return (last + 1);
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private boolean checkExisitingPK(String, String)
	 * 
	 * @param  table
	 * @param  PK
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private boolean checkExisitingPK(String table, String PK) throws SQLException {
		String query = "SELECT COUNT(*) FROM " + tables.get(table) + " WHERE " + pks.get(table) + "=" + PK;
		ResultSet rs = query(query);
		rs.next();
		int num = rs.getInt("COUNT(*)");
		if (num == 0)
			return false;
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private boolean checkExisitingColumn(String, String)
	 * 
	 * @param  table
	 * @param  col
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private boolean checkExisitingColumn(String table, String col) throws SQLException {
		String query = "SELECT COUNT(*) FROM " + tables.get(table) + " WHERE USERNAME='" + col + "'";
		ResultSet rs = query(query);
		rs.next();
		int num = rs.getInt("COUNT(*)");
		if (num == 0)
			return false;
		return true;
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private boolean findRowExisitng(String, String, String)
	 * 
	 * @param  table
	 * @param  condCol
	 * @param  value
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private boolean findRowExisitng(String table, String condCol, String value) throws SQLException {
		String query = "";
		if (getTypes(table)[getColumns(table).indexOf(condCol)])
			query = "SELECT * FROM " + tables.get(table) + " WHERE " + condCol + "=" + value;
		else
			query = "SELECT * FROM " + tables.get(table) + " WHERE " + condCol + "='" + value + "'";
		ResultSet rs = query(query);
		return rs.next();
	}

	/**
	 * --------------------------------------------------------------------------------------------------------
	 * private boolean findRowExisitng(String, String, String)
	 * 
	 * @param  table
	 * @param  condCol
	 * @param  value
	 * @return boolean
	 * @throws SQLException
	 * --------------------------------------------------------------------------------------------------------
	 **/
	private boolean findRowExisitng2(String table, String condCol, String value, String condCol2, String value2) throws SQLException {
		String query = "";
		String condition1 = "";
		String condition2 = "";
		if (getTypes(table)[getColumns(table).indexOf(condCol)])
			condition1 = condCol + "=" + value;
		else
			condition1 = condCol + "='" + value + "'";
		if (getTypes(table)[getColumns(table).indexOf(condCol2)])
			condition2 = condCol2 + "=" + value2;
		else
			condition2 = condCol2 + "='" + value2 + "'";
		query = "SELECT * FROM " +tables.get(table) + " WHERE " + condition1 + " AND " + condition2;
		ResultSet rs = query(query);
		return rs.next();
	}

}
