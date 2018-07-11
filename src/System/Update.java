package System;  
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import util.Constant;

import java.sql.*;  
import java.awt.*;  
import java.awt.event.*;  
public class Update {  
   
    public Update(){  
          
    }  
    public void  Execute_Update(JTable table, String tableName, String[] columnName){  
    	 String SQL;  
         Connection con = null;  
         Statement sql;
         
         try{  
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
         }  
         catch(ClassNotFoundException exp) {  
             System.out.println(exp);  
         }  
         try{  
             String url,userName,userPwd;  
             url="jdbc:sqlserver://localhost:1433;DatabaseName=WTF";  
             userName="sa";  
             userPwd="12345678";
             String[] aStrings = new String[columnName.length];
             con=DriverManager.getConnection(url,userName,userPwd);  
         	 sql=con.createStatement(); 
             for(int i = 0; i < columnName.length; i++) {
            	 aStrings[i] = (String) ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), i);
            	 SQL = "update " + tableName + " set " +  columnName[i] + "=" + "'" + aStrings[i] + "'" + " where name = " + "'" + Constant.account + "'"
            			 + " and " + columnName[0] + "=" + "'" + aStrings[0] + "'";
             	 System.out.println(SQL);
            	 sql.execute(SQL);
             } 
             con.close();  
         }  
         catch(SQLException ex) {  
             System.out.println(ex);
         }  
    }  
}  