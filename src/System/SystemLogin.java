package System;
import java.sql.*;
import javax.swing.*;

import util.Constant;

import java.awt.*;
import java.awt.event.*;
/**
 * ʵ�ָ��˹���ϵͳ�ĵ�¼
 * ��Ϊ�û��͹���Ա������ɫ
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
	 * ��¼����
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
		
		//����Java����ͼ��
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/title.png"));	
		this.setIconImage(imageIcon.getImage());	
		
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		
		//���ӵ�¼����ͼƬ
		ImageIcon image = new ImageIcon(getClass().getResource("/images/login.png"));
		imageLabel = new JLabel(image);
		panel1.add(imageLabel);
		
		//����ѡ���¼��ɫ
		role = new JLabel("ѡ����Ľ�ɫ:");		
		faceCombo = new JComboBox<String>();
		faceCombo.addItem("�û�");
		faceCombo.addItem("����Ա");
		
		userName = new JLabel("���������˺�:");
		userName.setBackground(Color.lightGray);
		userPwd = new JLabel("������������:");
		userPwd.setBackground(Color.lightGray);
		nameField = new JTextField(10);
		pwdField = new JPasswordField(10);

		btnLogin = new JButton("��¼");
		btnLogin.setBackground(Color.lightGray);
		btncz = new JButton("ע��");
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
		//���ò�����չ����
		this.setResizable(false);
		setTitle("����ϵͳ��½");
		//���ùرմ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		// ���д���
		setLocationRelativeTo(null);
		//���ü�����
		btnLogin.addActionListener(this);
		btncz.addActionListener(this);
		btnCancel.addActionListener(this);
	}

	public static String getUserName() {
		
		//����û���
		return nameField.getText();
	}

	@SuppressWarnings("deprecation")
	public static String getUserPwd() {
		
		//����û�����
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
				//ʹ��������������
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
				//������������������ע��������������ҵ�һ���ܹ�ʹ�����ݿ�URL��ָ������Э�����������
				//�������ݿ⣬��Ҫ֪�����ݿ�����ֺ�����
				con = DriverManager.getConnection(url, userName, userPwd);
				//ִ��SQL����֮ǰ����Ҫ����һ��Statement����
				sql = con.createStatement();
				if(name.trim().equals("") || pwd.trim().equals("")) {
					JOptionPane.showMessageDialog(this, "�˺Ż����벻����Ϊ��", "��Ϣ��ʾ��", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (faceCombo.getSelectedItem() == "�û�")
					//executeUpdate����������SQL����Ӱ�������
					rs = sql.executeQuery("select * from login where name = '" + name + "' and pwd='" + pwd + "'");
				else
					rs = sql.executeQuery("select * from sulogin where name = '" + name + "' and pwd='" + pwd + "'");
				int q = 0;
				//�������
				while (rs.next()) {
					q++;
				}
				if (q > 0) {
					//֤���и��˺ŵļ�¼
					JOptionPane.showMessageDialog(this, "��¼�ɹ�", "��Ϣ��ʾ��", JOptionPane.WARNING_MESSAGE);
					this.dispose();
					if (faceCombo.getSelectedItem() == "�û�")
						new UserFrame();
					else
						new SuperUserFrame();
				} else
					JOptionPane.showMessageDialog(this, "�˺Ż����������", "��Ϣ��ʾ��", JOptionPane.WARNING_MESSAGE);
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