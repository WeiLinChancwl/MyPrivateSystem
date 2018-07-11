package System;

import java.sql.*;
import javax.swing.*;

import org.omg.CORBA.TRANSACTION_MODE;

import util.Constant;

import java.awt.*;
import java.awt.event.*;

public class ResgisterFrame extends JFrame implements ActionListener {
	private JLabel lbAccount=new JLabel("���������˺ţ�");
	private JTextField tfAccount=new JTextField(10);
    private JLabel lbPassword=new JLabel("�����������룺");
    private JPasswordField pfPassword=new JPasswordField(10);
    private JLabel lbPassword2=new JLabel("����ȷ�����룺");
    private JPasswordField pfPassword2=new JPasswordField(10);
    private JLabel lbName=new JLabel("��������������");
    private JTextField tfName=new JTextField(10);
    private JButton btRegister=new JButton("ע��");
    private JButton btLogin=new JButton("��¼");
    private JButton btExit=new JButton("����");
    public ResgisterFrame() {
    	super("ע��");
    	this.setLayout(new FlowLayout());
    	this.add(lbAccount);
    	this.add(tfAccount);
    	this.add(lbPassword);
        this.add(pfPassword);
        this.add(lbPassword2);
        this.add(pfPassword2);
        this.add(lbName);
        this.add(tfName);
        this.add(btRegister);
        this.add(btLogin);
        this.add(btExit);
        this.setSize(240, 220);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        // ���д���
     	this.setLocationRelativeTo(null);
        btLogin.addActionListener(this);
        btRegister.addActionListener(this);
        btExit.addActionListener(this);
      
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btRegister) {
            String password1=new String(pfPassword.getPassword());
            String password2=new String(pfPassword2.getPassword());
            if(tfName.getText().trim().equals("") || tfAccount.getText().trim().equals(""))
            	JOptionPane.showMessageDialog(this, "�˺Ż����벻��Ϊ��");
            else {
	            if(!password1.equals(password2)) {
	                JOptionPane.showMessageDialog(this,"�������벻��ͬ");
	                return;
	            }
	            String account=tfAccount.getText();
	            if(existAccount(tfAccount.getText())) {
	                JOptionPane.showMessageDialog(this,"�û��Ѿ�ע��");
	                return;
	            }
	            String name=tfName.getText();
	            updateUser(account,password1,name);
	            JOptionPane.showMessageDialog(this,"ע��ɹ�");
            }
        }
        else if(e.getSource()==btLogin) {
            this.dispose();
        }
        else {
            this.dispose();
        }
    }
	private void updateUser(String account, String password1, String name) {
		/**
		 * �����û�
		 */
		try {
			Connection con;
			Statement sql;
			String url, userName, userPwd;
			url = "jdbc:sqlserver://localhost:1433;DatabaseName=WTF";
			userName = "sa";
			userPwd = "12345678";
			con = DriverManager.getConnection(url, userName, userPwd);
			sql = con.createStatement();
			sql.executeUpdate("insert into login(name, pwd) values('"+account+"', '"+password1+"')");
		} catch (SQLException exp) {
			System.out.println(exp);
		}
	}
	private boolean existAccount(String account) {
		/**
		 * ����Ƿ��Ѵ����û�
		 */
		String name;
		name = account;
		System.out.println(name);
		try {
			Connection con;
			Statement sql;
			ResultSet rs;
			String url, userName, userPwd;
			url = "jdbc:sqlserver://localhost:1433;DatabaseName=WTF";
			userName = "sa";
			userPwd = "12345678";
			con = DriverManager.getConnection(url, userName, userPwd);
			sql = con.createStatement();
			rs = sql.executeQuery("select * from login where name = '" + name + "'");
			int q = 0;
			while (rs.next()) {
				q++;
			}
			if (q > 0) {
				return true;
			} else
				return false;
		} catch (SQLException exp) {
			System.out.println(exp);
		}
		return false;
	}
}
