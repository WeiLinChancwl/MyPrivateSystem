package System;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import util.Constant;
public class Query {
	Object a[][] = null;
	String b[] = null;
	String tableName = "";
	int numFields;	//字段个数
	public Query() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}
		catch(ClassNotFoundException e) {
			System.out.println(e);
		}
	}
	
	public Object[][] getRecord() { //求表格的内容
		a = null;
		b = null;
		Connection con;
		Statement sql;
		ResultSet rs;
		try {
			String url, userName, userPwd;
			url = "jdbc:sqlserver://localhost:1433;DatabaseName=WTF";
			userName = "sa";
			userPwd = "12345678";
			con = DriverManager.getConnection(url,userName, userPwd);
			numFields = getZiDuan();
			int n = getAmount();
			a = new Object[n][numFields];
			sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sql.executeQuery("select * from " + tableName + " where name = " + Constant.account);
			int m = 0;
			while(rs.next()) {
				for(int k = 1; k <= numFields; k++) {
					if(k+1 > numFields)
						break;
					a[m][k-1] = rs.getString(k+1);
				}
				m++;
			}
			con.close();
		}
		catch(SQLException e) {
			System.out.println("请输入正确的表名" + e);
		}
		return a;
	}
	
	public int getAmount() {//求表内容有多少行
		Connection con;
		Statement sql;
		ResultSet rs;
		try {
			String url, userName, userPwd;
			url = "jdbc:sqlserver://localhost:1433;DatabaseName=WTF";
			userName = "sa";
			userPwd = "12345678";
			con = DriverManager.getConnection(url, userName, userPwd);
			sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sql.executeQuery("select * from " + tableName + " where name=" + Constant.account);
			rs.last();
			int rows = rs.getRow();
			return rows;
		}
		catch(SQLException exp) {
			System.out.println(""+exp);
			return 0;
		}
	}
	
	public String[] getField() { //求字段名称
		Connection con;
		try {
			String url, userName, userPwd;
			url = "jdbc:sqlserver://localhost:1433;DatabaseName=WTF";
			userName = "sa";
			userPwd = "12345678";
			con = DriverManager.getConnection(url, userName, userPwd);
			DatabaseMetaData metaData = con.getMetaData();
			ResultSet rs1 = metaData.getColumns(null, null, tableName, null);
			numFields = getZiDuan();
			b = new String[numFields];
			int k = 0;
			while(rs1.next()) {
				b[k] = rs1.getString(4);
				k++;
			}
			con.close();
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return b;
	}
	public void setTableName(String s) { //初试表名
		tableName = s.trim();
	}
	public int getZiDuan() { //求字段个数
		Connection con;
		PreparedStatement sql;
		ResultSet rs;
		try {
			String url, userName, userPwd;
			url = "jdbc:sqlserver://localhost:1433;DatabaseName=WTF";
			userName = "sa";
			userPwd = "12345678";
			con = DriverManager.getConnection(url, userName, userPwd);
			DatabaseMetaData metaData = con.getMetaData();
			ResultSet rs1 = metaData.getColumns(null, null, tableName, null);
			numFields = 0;
			while(rs1.next())
				numFields++;
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return numFields;
	}
}