package function;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.Mypanel;
import connect_SQL.Connect;
import exception.Myexception;
import imformation.deleteInformation;

public class CancleTickets
{
	JFrame frame = new JFrame("删除机票");
	ImageIcon icon = new ImageIcon("pictures//飞机2.jpg");
	Mypanel panel = new Mypanel(icon);
	JLabel idlabel = new JLabel("身份证号:");
	JLabel namelabel = new JLabel("姓名");
	JTextField idtxt = new JTextField();
	JTextField nametxt = new JTextField();
	JButton confirm,cancel;
	JLabel uplabel = new JLabel("请填写信息查询您的机票");
	public void init()
	{
		panel.setLayout(null);
		confirm = new JButton("确定");
		cancel = new JButton("返回");
		
		panel.add(idlabel);
		panel.add(namelabel);
		panel.add(idtxt);
		panel.add(nametxt);
		panel.add(confirm);
		panel.add(cancel);
		panel.add(uplabel);
		
		uplabel.setBounds(140, 40, 240, 40);
		uplabel.setFont( new Font("微软雅黑",Font.BOLD,19));
		
		
		idlabel.setBounds(120, 100, 90, 30);
		idlabel.setFont( new Font("微软雅黑",Font.BOLD,16) );
		idtxt.setBounds(220, 105, 150, 22);
		
		namelabel.setBounds(130, 150,60, 30);
		namelabel.setFont( new Font("微软雅黑",Font.BOLD,16) );
		nametxt.setBounds(220, 155,150,22);
		
		confirm.setBounds(150, 250, 80, 30);
		cancel.setBounds(250, 250, 80, 30);
		
		frame.add(panel);
		frame.setSize(500, 350);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public CancleTickets()
	{
		this.init();
		addevent();
	}
	public void addevent()
	{
		confirm.addActionListener( new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String ID = idtxt.getText();
				Statement st = new Connect().st;
				try {
					
					ResultSet rs = st.executeQuery("select * from u where uid = '"+ID+"'");
					if( rs.next() )	//如果有这个人
					{
						new deleteInformation(idtxt.getText(),nametxt.getText() );
						frame.dispose();
					}
				} catch (SQLException e) {
					new Myexception("查无此人，请重输");
				}
			}
		});
		cancel.addActionListener( new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				frame.dispose();
			}
		});
	}
	
}
