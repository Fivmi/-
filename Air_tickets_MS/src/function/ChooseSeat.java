package function;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import exception.Myexception;
import imformation.fillInformation;
import order.Order;

/*
 * ѡ��λ����
 */
public class ChooseSeat
{
	JFrame frame = new JFrame("ѡ����λ");
	JPanel leftpanel = new JPanel();
	JPanel midpanel = new JPanel();
	JPanel rightpanel = new JPanel();
	JPanel uppanel = new JPanel();
	JPanel downpanel = new JPanel();
	JPanel mainpanel = new JPanel();
	JButton bt[] = new JButton[63];
	JButton confirm = new JButton();
	JButton cancel = new JButton();
	ImageIcon whiteicon = new ImageIcon("pictures\\��ť3.png");
	ImageIcon redicon = new ImageIcon("pictures\\��ť2.png");
	ImageIcon greenicon = new ImageIcon("pictures\\��ť1.png");
	public static int a[] = new int[70];
	int number,cnt;			
	String u,v;
	Order result;
	Vector<String> seat = new Vector<String>();
	String r,c;
	public ChooseSeat( Order result,int number )
	{
		this.result = result;
		this.number = number;
		cnt = 0;	
		leftpanel.setLayout( new GridLayout(0, 3) );
		rightpanel.setLayout( new GridLayout(0,3) );
		leftpanel.setPreferredSize( new Dimension(180,150 ) );
		rightpanel.setPreferredSize( new Dimension(180, 150) );
		
		midpanel.setLayout( new GridLayout(0,1) );
		JLabel aisle = new JLabel("����",JLabel.CENTER);		//�м䲿�ֵ�����
		
		uppanel.setLayout(new GridLayout(0,8) );		//������8��
		uppanel.setPreferredSize( new Dimension(0,30) );
		JLabel uplabel[] = new JLabel[8];		//���� ABC DEF
		for( int i = 0 ; i < 8 ; i++ )
		{
			if( i == 3 || i == 4 )
			{
				uplabel[i] = new JLabel("     ",JLabel.CENTER);
				uppanel.add(uplabel[i]);
				continue;
			}
			
			String s = String.valueOf( (char)('A'+i ) );
			uplabel[i] = new JLabel(s,JLabel.CENTER);
			uplabel[i].setForeground(Color.BLUE);
			uplabel[i].setFont( new Font("����",Font.BOLD,20) );
			uppanel.add(uplabel[i]);
		}
		confirm.setText("ȷ��");
		cancel.setText("����");  			// ������������ť ȷ��  �� ����
		confirm.setForeground(Color.black);
		cancel.setForeground(Color.black);
		confirm.setFont( new Font("΢���ź�",Font.BOLD,18) );
		cancel.setFont( new Font("΢���ź�",Font.BOLD,18) );
		
		downpanel.add(confirm);
		downpanel.add(cancel);
		

		
		aisle.setFont( new Font("΢���ź�",Font.PLAIN,18)  );
		aisle.setForeground( Color.red );
		aisle.setBackground(Color.WHITE);
		midpanel.add(aisle);
		
		
		addbutton();
		mainpanel.setLayout( new BorderLayout() );
		mainpanel.add(leftpanel,BorderLayout.WEST);
		mainpanel.add(midpanel, BorderLayout.CENTER);
		mainpanel.add(rightpanel,BorderLayout.EAST);
		mainpanel.add(uppanel, BorderLayout.NORTH);
		mainpanel.add(downpanel, BorderLayout.SOUTH);
		
		frame.add(mainpanel);
		frame.setVisible(true);
		frame.setSize(500, 700);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	public void addbutton()
	{
		for( int i = 1 ; i <= 30 ; i++ )
		{
			if( a[i] == 0 )
				bt[i] = new JButton(whiteicon);		//����λ����white
			else bt[i] = new JButton(redicon);		//��ռλ����red
			
			bt[i].addActionListener( getlistener() );
			leftpanel.add(bt[i]);
		}
		for( int i = 31 ; i <= 60 ; i++ )
		{
			if( a[i] == 0 )
				bt[i] = new JButton(whiteicon);
			else bt[i] = new JButton(redicon);
			
			bt[i].addActionListener( getlistener() );
			rightpanel.add(bt[i]);
		}
		
		confirm.addActionListener( new ActionListener() 	//���ȷ�� ���¼�
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 		//������Ϣ��д����
			{
				for( int i = 1 ; i <= 60 ; i++ )
				{
					if( bt[i].getIcon() == whiteicon )
						a[i] = 0;
					else a[i] = 1;
				}
				if( cnt != number )
					new Myexception("��ѡ������������������һ�£�");
				else
				{
					new fillInformation(number,result,seat);
					frame.dispose();
				}
			}
		});
		cancel.addActionListener( new ActionListener()		//������ذ�ť
		{	
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				frame.dispose();
			}
		});
	}
	
	
	public ActionListener getlistener()			//��λ��ť�� ���֮��ı���ɫ
	{
		return new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 		
			{
				for( int i = 1 ; i <= 60 ; i++ )
					if( e.getSource() == bt[i] )
					{
						
						if( bt[i].getIcon() == whiteicon )	//֮ǰ�ǰ׵�
						{
							bt[i].setIcon(greenicon);
							seat.add(row(i)+col(i));
							cnt++;
						}
						else if(bt[i].getIcon() == greenicon)	//֮ǰ���̵ģ�ȡ����ѡ��
						{
							bt[i].setIcon(whiteicon);
							seat.remove(seat.size()-1);
							cnt--;
						}	
						break;
					}
			}
		};
	}
	public String row(int i) {
		if(i<=30) {
			if(i%3!=0) {
				return (i/3+1)+"";
			}
			else
				return	(i/3)+"";
		}
		else {
			if((i-30)%3!=0) {
				return ((i-30)/3+1)+"";
			}
			else
				return	((i-30)/3)+"";
		}
		
	}
	public String col(int i) {
		if(i<=30) {
			switch(i%3) {
			case 1:return "A";
			case 2:return "B";
			case 0:return "C";
			}
		}
		else {
			switch(i%3) {
			case 1:return "F";
			case 2:return "G";
			case 0:return "H";
			}
		}
		return null;
	}

}
