package System;  
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import util.Constant;

import java.sql.*;  
import java.awt.*;  
import java.awt.event.*;  
public class Delete {  
	
    public Delete(){  
          
    }  
    
    public void  Execute_Delete(JTable table, String tableName, String[] columnName){  
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
             String astring1 = (String) ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 0);
             String astring2 = (String) ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 1);
             String astring3 = (String) ((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 2);
         	 SQL="delete from " + tableName + " where name=" + "'" + Constant.account + "'" + " and " +  columnName[0]  + "=" + "'" + astring1 + "'" +
         			" and " +  columnName[1]  + "=" + "'" + astring2 + "'" + " and " +  columnName[2]  + "=" + "'" + astring3 + "'";
         	 
//         	 System.out.println(SQL);
         	 con=DriverManager.getConnection(url,userName,userPwd);  
         	 sql=con.createStatement();  
         	 sql.execute(SQL);
             con.close();  
         }  
         catch(SQLException ex) {  
             System.out.println(ex);
         }  
    }  
}  