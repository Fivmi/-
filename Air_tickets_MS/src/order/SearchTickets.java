package order;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.Mypanel;
import exception.Myexception;
import function.bookTickets;
import function.DateChooserJButton;

public class SearchTickets 
{
	JFrame frame = new JFrame("查询界面");
	JLabel lb[]= new JLabel[10];
	ImageIcon icon = new ImageIcon("pictures\\飞机3.jpg");
	Mypanel panel = new Mypanel(icon);
	static String city[] = {"北京","上海","武汉","广东","深圳"};
	JButton confirm,cancel;
	DateChooserJButton datechoose;			//日期选择按钮
	JTextField txt[] = new JTextField[5];
	JComboBox combo[] = new JComboBox[5];
	public SearchTickets()
	{
		panel.setLayout(null);
		lb[1] = new JLabel("机票查询");
		lb[2] = new JLabel("出发城市");
		lb[3] = new JLabel("到达城市");
		lb[4] = new JLabel("出发日期");
		lb[5] = new JLabel("人数");
		combo[1] = new JComboBox<String>();
		combo[2] = new JComboBox<String>();
		combo[3] = new JComboBox<Integer>();
		for( int i = 0 ; i < city.length ; i++ )
		{
			combo[1].addItem(city[i]);
			combo[2].addItem(city[i]);
		}
		for( int i = 1 ; i <=6 ; i++ )
			combo[3].addItem(i);
		confirm = new JButton("查询");
		cancel = new JButton("返回");
		datechoose = new DateChooserJButton("点我选择日期");
		for( int i = 1 ; i <= 5 ; i++ )
			panel.add(lb[i]);
		
		panel.add(combo[1]);
		panel.add(combo[2]);
		panel.add(combo[3]);
		panel.add(datechoose);
		panel.add(confirm);
		panel.add(cancel);
		
		lb[1].setBounds(200, 10, 80, 60);
		lb[1].setFont( new Font("微软雅黑",Font.BOLD,17));	
		lb[2].setBounds(50,50,80,60);
		lb[2].setFont( new Font("微软雅黑",Font.BOLD,16));
		lb[3].setBounds(50, 90, 80, 60);
		lb[3].setFont( new Font("微软雅黑",Font.BOLD,16) );
		lb[4].setBounds(50, 130, 80, 60);
		lb[4].setFont(new Font("微软雅黑",Font.BOLD,16) );
		lb[5].setBounds(50, 170, 60, 60);
		lb[5].setFont(new Font("微软雅黑",Font.BOLD,16) );
		combo[1].setBounds(150, 70, 80, 30);
		combo[2].setBounds(150, 110, 80, 30);
		combo[3].setBounds(150,190,40,20);
		datechoose.setBounds(150, 147, 130, 30);
		confirm.setBounds(160, 220, 80, 30);
		cancel.setBounds(250, 220, 80, 30);
		datechoose.setFont( new Font("微软雅黑",Font.PLAIN,15) );
		confirm.setFont( new Font("微软雅黑",Font.BOLD,15));
		cancel.setFont( new Font("微软雅黑",Font.BOLD,15) );
		
		
		addEvent();
		frame.add(panel);
		frame.setSize(500,300);	
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void addEvent()
	{
		confirmEvent();	
	}
	public void confirmEvent()	
	{
		confirm.addActionListener( new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				
				String u = (String)combo[1].getSelectedItem();
				String v = (String)combo[2].getSelectedItem();
				if( u.equals(v) )
				{
					new Myexception("起点和终点不能相同!");
					return;
				}
				String date = datechoose.getText();
				int number = (int)combo[3].getSelectedItem();
				new bookTickets(u,v,date,number);
				frame.dispose();
			}
		});
		cancel.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
	}
	
	
}
