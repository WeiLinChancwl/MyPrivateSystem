package System;

import java.io.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

public class Diary extends DefaultMutableTreeNode {
	
	DefaultMutableTreeNode month[] = new DefaultMutableTreeNode[13];
	int i;
	DiaryFrame df = new DiaryFrame();
	public Diary(){}
	public Diary(String name) {
	    super(name);
	    //构建日记本中的“月”节点
	    for(i=1;i<=12;i++) {
	        month[i] = new DefaultMutableTreeNode(i+"月");
	        this.add(month[i]);
	    }
	    //根据不同的月，构建日节点
	    for(i = 1;i<=12;i++) {
	        if(i==1 || i==3 || i==5 || i==7 || i==8 || i==10 || i==12) {
	            for(int j = 1;j<=31;j++) {
	            	month[i].add(new DefaultMutableTreeNode(j+"日")); 
	            }
		    } else if(i == 2) {
		        for(int j =1;j<=28;j++) {
		            month[i].add(new DefaultMutableTreeNode(j+"日"));
		        }    
		    }
		    else {
		        for(int j = 1;j<=30;j++) {
		            month[i].add(new DefaultMutableTreeNode(j+"日"));
		        }
		    }
	    }
	}
}