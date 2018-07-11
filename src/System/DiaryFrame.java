package System;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class DiaryFrame extends JFrame
{
    //JTree 声明为静态 ， 一个即可
    static JTree tree = null;
    //根节点root ，只有一个即可
    static DefaultMutableTreeNode root = new DefaultMutableTreeNode("MY Diary");
    //集合nodes保存根节点root上的子节点，每个子节点简称为一个日记本
    //用泛型指明集合中的元素DefaultMultableTreeNode类型数据
    static ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
    JTextArea jta = new JTextArea(20,30);
    //空菜单条menu
    MenuBar menu = null;
    //三个菜单m1,m2,m3
    Menu m1,m2,m3, m4;
    //菜单选项
    MenuItem m1a,m1b,m1c,m1d,m2a,m2b,m3a,m3b,pma,pmb,pmc;
    //弹出式菜单pMenu
    PopupMenu pMenu = null;
    //保存和删除按钮
    JButton bSave,bDel;
    //日记类Diary的引用
    Diary diary = null;
    //DefaulttableTreeNode
    static DefaultMutableTreeNode n = new Diary("生活日记");
    
    //界面初始化
    public void launchFrame()
    {
        int width = 640,height = 480;
        setTitle("我的日记");
        setSize(width,height);
        //更换Java咖啡图标
      	ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/title.png"));	
      	this.setIconImage(imageIcon.getImage());	
        //设置软件呈现的位置在屏幕中间
        setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-width)/2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-height)/2);
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
        
        pma = new MenuItem("new diary");
        pmb = new MenuItem("del diary");
        pmc = new MenuItem("rename");
        pMenu.add(pma);
        pMenu.addSeparator();
        pMenu.add(pmb);
        pMenu.addSeparator();
        pMenu.add(pmc);
        //添加菜单条
        this.setMenuBar(menu);
        //用根节点root初始化JTree
        tree = new JTree (root);
        JScrollPane jsp1 = new JScrollPane(tree);
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        JScrollPane jsp2  = new JScrollPane(jta);
        jp.add(jsp2,BorderLayout.CENTER);
        JPanel bottom= new JPanel();
        bSave = new JButton("save");
        bDel = new JButton("Del");
        bottom.add(bSave);
        bottom.add(bDel);
        jp.add(bottom,BorderLayout.SOUTH);
        //JSpliPane将容器分割向左右两部分
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp1,jp);
        //添加一个默认日记本“生活日记 ”
        nodes.add(n);
        //监听类，用于监听JTree和JButton
        MyListener listener = new MyListener();
        tree.addTreeSelectionListener(listener);
        //调用初始化JTree的方法
        initTree();
        bSave.addActionListener(listener);
        bDel.addActionListener(listener);
        //菜单的监听类
        MenuListener menuListener = new MenuListener();
        m1.addActionListener(menuListener);
        m2.addActionListener(menuListener);
        m3.addActionListener(menuListener);
        
        this.getContentPane().add(splitPane);
        this.setVisible(true);    
    }
    //JTree节点的初始化方法，用于完成树上节点的添加工作
    private static void initTree()
    {
        //将节点集合中的节点，全部添加到树上
        Iterator<DefaultMutableTreeNode> it = nodes.iterator();
        while(it.hasNext())
        {
            DefaultMutableTreeNode n = it.next();
            root.add(n);
        }
        tree.updateUI();
        tree.repaint();
    }
    //内部类，菜单监听
    class MenuListener implements ActionListener
    {
        DiaryFrame  df = new DiaryFrame();
        public void actionPerformed(ActionEvent e)
        {
            //获取用户操作的菜单名称
            String label = ((MenuItem)e.getSource()).getLabel();
            if(label.equals("文件"))
            {
                //根据用户选择的菜单项，实现不同功能
                if(e.getActionCommand().equals("new diary"))
                {
                    //输入对话框，对新建的日记本命名
                    String value = new JOptionPane().showInputDialog(df,"请输入新建的日记本名称");
                    //以用户输入的日记本名，新建具有日记本功能的节点】
                    DefaultMutableTreeNode node =  new Diary(value);
                    //将节点添加到集合
                    nodes.add(node);
                    //重新初始化一次节点
                    DiaryFrame.initTree();
                }
            }
            else if(label.equals("edit"))
            {
                //对“编辑”菜单的处理
                
            }
            else
            {
                //对帮助菜单的处理
            }
        }
    }
    //内部类，监听JTree节点选择和JButton
    class MyListener implements ActionListener,TreeSelectionListener
    {
        DiaryFrame df = new DiaryFrame();
        public void actionPerformed(ActionEvent e)
        {
            //根据不同的按钮，处理响应时间
            //“保存按钮处理”
            if(e.getSource() == bSave)
            {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                //节点node的名字
                String  str = node.toString();
                //如果节点是叶节点，以“日记本名+月+日”为文件名，将日记文本写入文件
                if(node.isLeaf())
                {
                    BufferedWriter out = null;
                    try
                    {
                        //构造文件名
                        String fileName = node.getParent().getParent().toString()+node.getParent().toString()+str+".txt";
                        //构造文件
                        File file = new File(fileName);
                        //文本文件写入流
                        out = new BufferedWriter(new FileWriter(file));
                        out.write(jta.getText(),0,(jta.getText()).length());
                        out.flush();
                        new JOptionPane().showMessageDialog(df,"日记创建成功！");
                        
                    }
                    catch (IOException err)
                    {
                        // TODO: handle exception
                        new JOptionPane().showMessageDialog(df,"日记创建失败！");
                        err.printStackTrace();    
                    }
                    catch (Exception err)
                    {
                        // TODO: handle exception
                        err.printStackTrace();    
                    }
                    finally
                    {
                        try
                        {
                            if(out != null)
                                out.close();
                            
                        }
                        catch (IOException e1)
                        {
                            // TODO: handle exception
                            e1.printStackTrace();
                        }
                    }
                }//删除按钮处理
            }
            else if(e.getSource() ==bDel)
            {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                String str = node.toString();
                if(node.isLeaf())
                {
                    BufferedWriter out = null;
                    try
                    {
                        //删除文件处理，先将预删除的文件名写入“del.bat”文件 ，在调用del()函数删除
                        String fileName = node.getParent().getParent().toString()+node.getParent().toString()
                                +str+".txt";
                        File file = new File(fileName);
                        out = new BufferedWriter(new FileWriter(new File("del.bat")));
                        String cmd = "del" + file.getAbsolutePath().toString();
                        
                        out.write(cmd);
                        out.newLine();
                        out.flush();
                        jta.setText("文件已被删除");
                        //调用del()hanshu
                        del();    
                    }
                    catch (IOException err)
                    {
                        // TODO: handle exception
                        err.printStackTrace();
                    }
                    finally
                    {
                        try
                        {
                            if(out != null)
                                out.close();
                        }
                        catch (IOException e1)
                        {
                            // TODO: handle exception
                            e1.printStackTrace();
                            
                        }
                    }
                }
            }
        }
        private void del()
        {
            //Runtime 可以与运行的程序环境相连接
            Runtime rt = Runtime.getRuntime();
            try
            {
                //执行批处理文件
                rt.exec("del.bat");
                new JOptionPane().showMessageDialog(df,"日记被删除");
            }
            catch (IOException e)
            {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        public void valueChanged(TreeSelectionEvent e)
        {
            jta.setText("");
            if(e.getSource() == tree)
            {
                //获取节点
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                //只对节点处理
                if(node.isLeaf())
                {
                    String str = node.toString();
                    for(int i = 1;i<=12;i++)
                    {
                        if(node.getParent().toString().equals(i+"月"))
                        {
                            BufferedReader br = null;
                            try
                            {
                                //对于已经存在的日记，将日记文件读入文本区
                                String fileName = node.getParent().getParent().toString()+node.getParent().toString()+str+".txt";
                                File file = new File(fileName);
                                br = new BufferedReader(new FileReader(file));
                                String line = null;
                                while((line = br.readLine() )!=null)
                                {
                                    jta.append(line+"\n");
                                    
                                }
                                
                            }
                            catch (FileNotFoundException err)
                            {
                                // TODO: handle exception
                                jta.setText("未找到日记文件");
                                err.printStackTrace();
                                
                            }
                            catch (IOException err)
                            {
                                // TODO: handle exception
                                err.printStackTrace();
                            }
                            finally
                            {
                                try
                                {
                                    if(br != null)
                                        br.close();
                                }
                                catch (IOException e1)
                                {
                                    // TODO: handle exception
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }

}

