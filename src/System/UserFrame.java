package System;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import util.Constant;

public class UserFrame extends JFrame {
	
	/**
	 * 用户菜单页
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel;
	static JTable table;
	JScrollPane jScrollPane;
	String curTableName;
	String[] curColumnName;
	DefaultTableModel defaultTableModel = new DefaultTableModel();
	JButton pebtn, libtn, stbtn, wobtn, frbtn, mcbtn, rjbtn, kcbtn, jsqButton;
	JButton addbtn, deletebtn, savebtn, updatebtn;
	 //空菜单条menu
    MenuBar menu = null;
    //三个菜单m1,m2,m3
    Menu m1,m2,m3, m4;
    //菜单选项
    MenuItem m1a,m1b,m1c,m1d,m2a,m2b,m3a,m3b,pma,pmb,pmc;
    //弹出式菜单pMenu
    PopupMenu pMenu = null;
    
	public UserFrame() {
		pebtn = new JButton("个人信息");
		libtn = new JButton("生活计划");
		stbtn = new JButton("学习计划");
		wobtn = new JButton("工作计划");
		frbtn = new JButton("通讯录");
		mcbtn = new JButton("记账管理");
		rjbtn = new JButton("日记");
		kcbtn = new JButton("课程表");
		jsqButton = new JButton("计算器");
		
		addbtn = new JButton("增加");
		deletebtn = new JButton("删除");
		savebtn = new JButton("保存");
		updatebtn = new JButton("修改");
		
		table = new JTable();
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 19));
		table.setRowSelectionAllowed(true);
		defaultTableModel = new DefaultTableModel();
		table.setModel(defaultTableModel);
		jScrollPane = new JScrollPane(table);
		jScrollPane.setBounds(250, 150, 500, 600);
		this.add(jScrollPane);
		
		pebtn.addActionListener(new peListener());
		libtn.addActionListener(new liListener());
		stbtn.addActionListener(new stListener());
		wobtn.addActionListener(new woListener());
		frbtn.addActionListener(new frListener());
		mcbtn.addActionListener(new mcListener());
		rjbtn.addActionListener(new rjListener());
		kcbtn.addActionListener(new kcListener());
		jsqButton.addActionListener(new jsqListner());
		
		addbtn.addActionListener(new addListener());
		deletebtn.addActionListener(new deleteListener());
		savebtn.addActionListener(new saveListener());
		updatebtn.addActionListener(new updateListener());
		
		pebtn.setBackground(Color.DARK_GRAY);
		pebtn.setForeground(Color.WHITE);
		libtn.setBackground(Color.DARK_GRAY);
		libtn.setForeground(Color.WHITE);
		stbtn.setBackground(Color.DARK_GRAY);
		stbtn.setForeground(Color.WHITE);
		wobtn.setBackground(Color.DARK_GRAY);
		wobtn.setForeground(Color.WHITE);
		frbtn.setBackground(Color.DARK_GRAY);
		frbtn.setForeground(Color.WHITE);
		mcbtn.setBackground(Color.DARK_GRAY);
		mcbtn.setForeground(Color.WHITE);
		rjbtn.setBackground(Color.DARK_GRAY);
		rjbtn.setForeground(Color.WHITE);
		kcbtn.setBackground(Color.DARK_GRAY);
		kcbtn.setForeground(Color.WHITE);
		jsqButton.setBackground(Color.DARK_GRAY);
		jsqButton.setForeground(Color.WHITE);
		
		addbtn.setBackground(Color.DARK_GRAY);
		addbtn.setForeground(Color.WHITE);
		deletebtn.setBackground(Color.DARK_GRAY);
		deletebtn.setForeground(Color.WHITE);
		savebtn.setBackground(Color.DARK_GRAY);
		savebtn.setForeground(Color.WHITE);
		updatebtn.setBackground(Color.DARK_GRAY);
		updatebtn.setForeground(Color.WHITE);
		
		ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/images/background.png"));
		JLabel label = new JLabel(backgroundIcon);
		label.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
		jPanel = (JPanel)getContentPane();
		//设置控件透明
		jPanel.setOpaque(false);  
		jPanel.setLayout(null);
		getLayeredPane().setLayout(null);
		//把背景图片添加到分层窗格的最底层作为背景
		getLayeredPane().add(label, new Integer(Integer.MIN_VALUE)); 
		setSize(backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight()+20);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/title.png"));
		this.setIconImage(imageIcon.getImage());
		setResizable(false);
		jPanel = new JPanel();
		this.add(jPanel);
		//界面左上角标题
		setTitle(Constant.account+"的主页");	 
		//设置布局管理器	
		this.setLayout(null); 
		this.setBackground(Color.white);
		pack();  //自动调整
		this.setSize(800, 800);
		setLocationRelativeTo(null); //居中窗体
		
		//初始化菜单条，菜单和菜单选项
        menu = new MenuBar();
        pMenu = new PopupMenu();
        m1 = new Menu("文件");
        m2 = new Menu("编辑");
        m3 = new Menu("帮助");
        m4 = new Menu("退出");
        
        m1a = new MenuItem("new diary");
        //设置快捷键
        m1a.setShortcut(new MenuShortcut(KeyEvent.VK_N));
        m1b = new MenuItem("save diary");
        m1b.setShortcut(new MenuShortcut(KeyEvent.VK_S));
        m1c = new Menu("del diary");
        m1c.setShortcut(new MenuShortcut(KeyEvent.VK_D));
        m1d = new MenuItem("exit System");
        m1d.setShortcut(new MenuShortcut(KeyEvent.VK_Q));
        m1.add(m1a);
        m1.addSeparator();
        m1.add(m1b);
        m1.add(m1c);
        m1.add(m1c);
        m1.addSeparator();
        m1.add(m1d);
        menu.add(m1);
        
        
        m2a = new MenuItem("copy");
        m2a.setShortcut(new MenuShortcut(KeyEvent.VK_C));
        m2b = new MenuItem("paste");
        m2b.setShortcut(new MenuShortcut(KeyEvent.VK_V));
        m2.add(m2a);
        m2.add(m2b);
        menu.add(m2);

        m3a = new MenuItem("关于主题");
        m3b = new MenuItem("使用手册");
        m3.add(m3a);
        m3.add(m3b);
        menu.add(m3);
        menu.add(m4);
        //添加菜单条
        this.setMenuBar(menu);
        
		
		pebtn.setBounds(70, 30, 100, 30);
		libtn.setBounds(70, 90, 100, 30);
		stbtn.setBounds(70, 150, 100, 30);
		wobtn.setBounds(70, 210, 100, 30);
		frbtn.setBounds(70, 270, 100, 30);
		mcbtn.setBounds(70, 330, 100, 30);
		rjbtn.setBounds(70, 390, 100, 30);
		kcbtn.setBounds(70, 450, 100, 30);
		jsqButton.setBounds(70, 510, 100, 30);
		
		addbtn.setBounds(300, 70, 80, 30);
		deletebtn.setBounds(400, 70, 80, 30);
		savebtn.setBounds(500, 70, 80, 30);
		updatebtn.setBounds(600, 70, 80, 30);
		add(pebtn);
		add(libtn);
		add(stbtn);
		add(wobtn);
		add(frbtn);
		add(mcbtn);
		add(rjbtn);
		add(kcbtn);
		add(addbtn);
		add(deletebtn);
		add(savebtn);
		add(updatebtn);
		add(jsqButton);
		this.setVisible(true);
	}
	
	class peListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			String[] columnname = {"姓名", "性别", "电话", "地址", "微信", "出生年日"};
			String tablename = "personinformation";
			scan(tablename, columnname);		
		}
	}
	class liListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			String[] columnname = {"时间", "生活TODO", "小目标"};
			String tablename = "life";
			scan(tablename, columnname);
		}
	}

	class stListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			String[] columnname = {"时间", "学习TODO", "学习小目标"};
			String tablename = "study";
			scan(tablename, columnname);
		}

	}

	class woListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			String[] columnname = {"时间", "工作TODO", "工作小目标"};
			String tablename = "work";
			scan(tablename, columnname);
		}
	}

	class frListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
			
			String[] columnname = {"朋友", "性别", "电话", "地址", "微信", "出生日期"};
			String tablename = "friend";
			scan(tablename, columnname);
		}
		
	}

	class mcListener implements ActionListener {
		
		public void actionPerformed(ActionEvent arg0) {
		
			String[] columnname = {"时间", "本月支出", "本月剩余", "本月零花钱"};
			String tablename = "moneymananger";
			scan(tablename, columnname);
		}
	}
	
	class rjListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			new DiaryFrame().launchFrame();
		}
		
	}
	
	class kcListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] columnname = {"星期", "课程1", "课程2","课程3","课程4","课程5","课程6","课程7","课程8","课程9","课程10","课程11" };
			String tablename = "course";
			scan(tablename, columnname);
		}
		
	}
	
	class jsqListner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new MyCalculator();
		}

	}

	class addListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] strings = new String[table.getColumnCount()];
			((DefaultTableModel)table.getModel()).addRow(strings);
		}
		
	}

	class deleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Delete().Execute_Delete(table, curTableName, curColumnName);
			((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
		}
		
	}

	class saveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] strings = new String[table.getColumnCount()];
			for(int i = 0; i < strings.length; i++) {
				strings[i] = (String) defaultTableModel.getValueAt(defaultTableModel.getRowCount()-1, i);
				//System.out.println(strings[i]);
			}
			new Insert().Execute_Insert(curTableName, curColumnName, strings);
			scan(curTableName, curColumnName);
		}
	}

	class updateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Update().Execute_Update(table, curTableName, curColumnName);
		}
		
	}

	public void scan(String tablename, String[] columnname) {
		/**
		 * 浏览表格
		 */
		Query query = new Query();
		query.setTableName(tablename);
		
		curColumnName = new String[columnname.length];
		for(int i = 0; i < columnname.length; i++) {
			curColumnName[i] = columnname[i];
		}
		curTableName = tablename;
		Object content[][] = query.getRecord();
		defaultTableModel.setDataVector(content, columnname);
	}
	
	public void dispose() {
		this.dispose();
	}
}
