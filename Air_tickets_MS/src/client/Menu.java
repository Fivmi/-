package client;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import function.CancleTickets;
import order.SearchTickets;

public class Menu 
{
	private void init()
	{
		JFrame frame = new JFrame("���˵�");
		JButton bt[] = new JButton[3];
		Mypanel panel;
		JLabel label = new JLabel("���ն�Ʊϵͳ");
		ImageIcon backgroud;
		backgroud = new ImageIcon("pictures\\�ɻ�1.jpg");
		panel = new Mypanel(backgroud);
		bt[0] = new JButton("��ƱԤ��");
		bt[1] = new JButton("��Ʊ");
		panel.setLayout(null);
		panel.add(bt[0]);
		panel.add(bt[1]);
		panel.add(label);
		label.setFont( new Font("΢���ź�",Font.BOLD,40) );
		label.setForeground( Color.RED );
		bt[0].setBounds(125, 300, 120, 50);	
		bt[1].setBounds(325, 300, 120, 50);	
		bt[0].setFont( new Font("΢���ź�",Font.PLAIN,17) );
		bt[1].setFont( new Font("΢���ź�",Font.PLAIN,17) );
		label.setBounds(222, 20,250, 60);				
		bt[0].addActionListener( new ActionListener()		
		{
			public void actionPerformed(ActionEvent e) 
			{
				new SearchTickets();
			}
		} ); 
		bt[1].addActionListener( new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				new CancleTickets();
			}
		});
		
		frame.add(panel);
		frame.setSize(650,450);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public Menu()
	{
		this.init();
	}
	
	public static void main(String[] args) 
	{
		new Menu();
	}

}
