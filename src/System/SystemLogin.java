package System;
import java.sql.*;
import javax.swing.*;

import util.Constant;

import java.awt.*;
import java.awt.event.*;
/**
 * 实现个人管理系统的登录
 * 分为用户和管理员两个角色
 * @author 
 *
 */
public class SystemLogin {
	public static void main(String[] args) {
		new loginFrame();
	}
}

class loginFrame extends JFrame implements ActionListener {
	/**
	 * 登录界面
	 */
	private static final long serialVersionUID = 1L;
	private JLabel userName, userPwd, role;
	private static JTextField nameField;
	private static JPasswordField pwdField;
	private JButton btnLogin, btncz, btnCancel;
	private JPanel panel1, panel2, panel3;
	private JLabel imageLabel;
	private JComboBox<String> faceCombo;
    
	loginFrame() {
		this.setLayout(new FlowLayout());	
		this.setBounds(100, 100, 350, 550);	
		
		//更换Java咖啡图标
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/title.png"));	
		this.setIconImage(imageIcon.getImage());	
		
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		
		//增加登录界面图片
		ImageIcon image = new ImageIcon(getClass().getResource("/images/login.png"));
		imageLabel = new JLabel(image);
		panel1.add(imageLabel);
		
		//设置选择登录角色
		role = new JLabel("选择你的角色:");		
		faceCombo = new JComboBox<String>();
		faceCombo.addItem("用户");
		faceCombo.addItem("管理员");
		
		userName = new JLabel("请你输入账号:");
		userName.setBackground(Color.lightGray);
		userPwd = new JLabel("请你输入密码:");
		userPwd.setBackground(Color.lightGray);
		nameField = new JTextField(10);
		pwdField = new JPasswordField(10);

		btnLogin = new JButton("登录");
		btnLogin.setBackground(Color.lightGray);
		btncz = new JButton("注册");
		btncz.setBackground(Color.lightGray);
		btnCancel = new JButton("exit");
		btnCancel.setBackground(Color.lightGray);
		

		panel2.setLayout(new GridLayout(4, 2));
		panel2.add(userName);
		panel2.add(nameField);
		panel2.add(userPwd);
		panel2.add(pwdField);
		panel2.add(role);
		panel2.add(faceCombo);
		
		panel3.setLayout(new FlowLayout());
		panel3.add(btnLogin);
		panel3.add(btncz);
		panel3.add(btnCancel);
		
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.setVisible(true);
		//设置不可扩展窗口
		this.setResizable(false);
		setTitle("个人系统登陆");
		//设置关闭窗口
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		// 居中窗体
		setLocationRelativeTo(null);
		//设置监听器
		btnLogin.addActionListener(this);
		btncz.addActionListener(this);
		btnCancel.addActionListener(this);
	}

	public static String getUserName() {
		
		//获得用户名
		return nameField.getText();
	}

	@SuppressWarnings("deprecation")
	public static String getUserPwd() {
		
		//获得用户密码
		return pwdField.getText();
	}
	
	public static void setConstant() {
		Constant.account = nameField.getText();
		Constant.password = nameField.getText();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == btnLogin) {
			String name, pwd;
			setConstant();
			//System.out.println(Constant.account + "" + Constant.password);
			name = getUserName();
			pwd = getUserPwd();
			try {
				//使得驱动器被加载
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException ex) {
				System.out.println(ex);
			}
			try {
				Connection con;
				Statement sql;
				ResultSet rs;
				String url, userName, userPwd;
				url = "jdbc:sqlserver://localhost:1433;DatabaseName=WTF";
				userName = "sa";
				userPwd = "12345678";
				//驱动管理器遍历所有注册过的驱动程序，找到一个能够使用数据库URL中指定的子协议的驱动程序
				//连接数据库，需要知道数据库的名字和密码
				con = DriverManager.getConnection(url, userName, userPwd);
				//执行SQL命令之前，需要创建一个Statement对象
				sql = con.createStatement();
				if(name.trim().equals("") || pwd.trim().equals("")) {
					JOptionPane.showMessageDialog(this, "账号或密码不可以为空", "消息提示框", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (faceCombo.getSelectedItem() == "用户")
					//executeUpdate方法将返回SQL命令影响的行数
					rs = sql.executeQuery("select * from login where name = '" + name + "' and pwd='" + pwd + "'");
				else
					rs = sql.executeQuery("select * from sulogin where name = '" + name + "' and pwd='" + pwd + "'");
				int q = 0;
				//分析结果
				while (rs.next()) {
					q++;
				}
				if (q > 0) {
					//证明有该账号的记录
					JOptionPane.showMessageDialog(this, "登录成功", "消息提示框", JOptionPane.WARNING_MESSAGE);
					this.dispose();
					if (faceCombo.getSelectedItem() == "用户")
						new UserFrame();
					else
						new SuperUserFrame();
				} else
					JOptionPane.showMessageDialog(this, "账号或者密码错误！", "消息提示框", JOptionPane.WARNING_MESSAGE);
			} catch (SQLException exp) {
				System.out.println(exp);
			}
		} else if (source == btncz) {
			
			new ResgisterFrame();
		} else if (source == btnCancel) {
			System.exit(0);
		}
	}
}