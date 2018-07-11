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
    //JTree ����Ϊ��̬ �� һ������
    static JTree tree = null;
    //���ڵ�root ��ֻ��һ������
    static DefaultMutableTreeNode root = new DefaultMutableTreeNode("MY Diary");
    //����nodes������ڵ�root�ϵ��ӽڵ㣬ÿ���ӽڵ���Ϊһ���ռǱ�
    //�÷���ָ�������е�Ԫ��DefaultMultableTreeNode��������
    static ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
    JTextArea jta = new JTextArea(20,30);
    //�ղ˵���menu
    MenuBar menu = null;
    //�����˵�m1,m2,m3
    Menu m1,m2,m3, m4;
    //�˵�ѡ��
    MenuItem m1a,m1b,m1c,m1d,m2a,m2b,m3a,m3b,pma,pmb,pmc;
    //����ʽ�˵�pMenu
    PopupMenu pMenu = null;
    //�����ɾ����ť
    JButton bSave,bDel;
    //�ռ���Diary������
    Diary diary = null;
    //DefaulttableTreeNode
    static DefaultMutableTreeNode n = new Diary("�����ռ�");
    
    //�����ʼ��
    public void launchFrame()
    {
        int width = 640,height = 480;
        setTitle("�ҵ��ռ�");
        setSize(width,height);
        //����Java����ͼ��
      	ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/title.png"));	
      	this.setIconImage(imageIcon.getImage());	
        //����������ֵ�λ������Ļ�м�
        setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-width)/2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-height)/2);
        //��ʼ���˵������˵��Ͳ˵�ѡ��
        menu = new MenuBar();
        pMenu = new PopupMenu();
        m1 = new Menu("�ļ�");
        m2 = new Menu("�༭");
        m3 = new Menu("����");
        m4 = new Menu("�˳�");
        
        m1a = new MenuItem("new diary");
        //���ÿ�ݼ�
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

        m3a = new MenuItem("��������");
        m3b = new MenuItem("ʹ���ֲ�");
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
        //��Ӳ˵���
        this.setMenuBar(menu);
        //�ø��ڵ�root��ʼ��JTree
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
        //JSpliPane�������ָ�������������
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp1,jp);
        //���һ��Ĭ���ռǱ��������ռ� ��
        nodes.add(n);
        //�����࣬���ڼ���JTree��JButton
        MyListener listener = new MyListener();
        tree.addTreeSelectionListener(listener);
        //���ó�ʼ��JTree�ķ���
        initTree();
        bSave.addActionListener(listener);
        bDel.addActionListener(listener);
        //�˵��ļ�����
        MenuListener menuListener = new MenuListener();
        m1.addActionListener(menuListener);
        m2.addActionListener(menuListener);
        m3.addActionListener(menuListener);
        
        this.getContentPane().add(splitPane);
        this.setVisible(true);    
    }
    //JTree�ڵ�ĳ�ʼ������������������Ͻڵ����ӹ���
    private static void initTree()
    {
        //���ڵ㼯���еĽڵ㣬ȫ����ӵ�����
        Iterator<DefaultMutableTreeNode> it = nodes.iterator();
        while(it.hasNext())
        {
            DefaultMutableTreeNode n = it.next();
            root.add(n);
        }
        tree.updateUI();
        tree.repaint();
    }
    //�ڲ��࣬�˵�����
    class MenuListener implements ActionListener
    {
        DiaryFrame  df = new DiaryFrame();
        public void actionPerformed(ActionEvent e)
        {
            //��ȡ�û������Ĳ˵�����
            String label = ((MenuItem)e.getSource()).getLabel();
            if(label.equals("�ļ�"))
            {
                //�����û�ѡ��Ĳ˵��ʵ�ֲ�ͬ����
                if(e.getActionCommand().equals("new diary"))
                {
                    //����Ի��򣬶��½����ռǱ�����
                    String value = new JOptionPane().showInputDialog(df,"�������½����ռǱ�����");
                    //���û�������ռǱ������½������ռǱ����ܵĽڵ㡿
                    DefaultMutableTreeNode node =  new Diary(value);
                    //���ڵ���ӵ�����
                    nodes.add(node);
                    //���³�ʼ��һ�νڵ�
                    DiaryFrame.initTree();
                }
            }
            else if(label.equals("edit"))
            {
                //�ԡ��༭���˵��Ĵ���
                
            }
            else
            {
                //�԰����˵��Ĵ���
            }
        }
    }
    //�ڲ��࣬����JTree�ڵ�ѡ���JButton
    class MyListener implements ActionListener,TreeSelectionListener
    {
        DiaryFrame df = new DiaryFrame();
        public void actionPerformed(ActionEvent e)
        {
            //���ݲ�ͬ�İ�ť��������Ӧʱ��
            //�����水ť����
            if(e.getSource() == bSave)
            {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                //�ڵ�node������
                String  str = node.toString();
                //����ڵ���Ҷ�ڵ㣬�ԡ��ռǱ���+��+�ա�Ϊ�ļ��������ռ��ı�д���ļ�
                if(node.isLeaf())
                {
                    BufferedWriter out = null;
                    try
                    {
                        //�����ļ���
                        String fileName = node.getParent().getParent().toString()+node.getParent().toString()+str+".txt";
                        //�����ļ�
                        File file = new File(fileName);
                        //�ı��ļ�д����
                        out = new BufferedWriter(new FileWriter(file));
                        out.write(jta.getText(),0,(jta.getText()).length());
                        out.flush();
                        new JOptionPane().showMessageDialog(df,"�ռǴ����ɹ���");
                        
                    }
                    catch (IOException err)
                    {
                        // TODO: handle exception
                        new JOptionPane().showMessageDialog(df,"�ռǴ���ʧ�ܣ�");
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
                }//ɾ����ť����
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
                        //ɾ���ļ������Ƚ�Ԥɾ�����ļ���д�롰del.bat���ļ� ���ڵ���del()����ɾ��
                        String fileName = node.getParent().getParent().toString()+node.getParent().toString()
                                +str+".txt";
                        File file = new File(fileName);
                        out = new BufferedWriter(new FileWriter(new File("del.bat")));
                        String cmd = "del" + file.getAbsolutePath().toString();
                        
                        out.write(cmd);
                        out.newLine();
                        out.flush();
                        jta.setText("�ļ��ѱ�ɾ��");
                        //����del()hanshu
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
            //Runtime ���������еĳ��򻷾�������
            Runtime rt = Runtime.getRuntime();
            try
            {
                //ִ���������ļ�
                rt.exec("del.bat");
                new JOptionPane().showMessageDialog(df,"�ռǱ�ɾ��");
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
                //��ȡ�ڵ�
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                //ֻ�Խڵ㴦��
                if(node.isLeaf())
                {
                    String str = node.toString();
                    for(int i = 1;i<=12;i++)
                    {
                        if(node.getParent().toString().equals(i+"��"))
                        {
                            BufferedReader br = null;
                            try
                            {
                                //�����Ѿ����ڵ��ռǣ����ռ��ļ������ı���
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
                                jta.setText("δ�ҵ��ռ��ļ�");
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

