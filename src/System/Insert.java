package System;  
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import util.Constant;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.awt.*;  
import java.awt.event.*; 

public class Insert { 
	
    
    public Insert() {
    	
    }
    
    public void Execute_Insert(String tableName, String[] columnName, String a[]) {  
        String SQL;  
        Connection con = null;  
        Statement sql;
        
        String kString="name, ";
        String vString="'"+Constant.account+"'" + ",";
        for(int i = 0; i < columnName.length-1; i++)
        	kString += columnName[i] + ",";
        kString += columnName[columnName.length-1];
        
        for(int i = 0; i < a.length-1; i++)
        	vString += "'" +a[i] + "'" + ",";
        vString += "'"+a[a.length-1]+"'";
        
        try{  
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
        }  
        catch(ClassNotFoundException exp){  
            System.out.println(exp);  
        }  
        try{  
            String url,userName,userPwd;  
            url="jdbc:sqlserver://localhost:1433;DatabaseName=WTF";  
            userName="sa";  
            userPwd="12345678";
        	SQL="insert into " +tableName+ "("+kString+")" + " values ("+vString+");";
        	//System.out.println(SQL);
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