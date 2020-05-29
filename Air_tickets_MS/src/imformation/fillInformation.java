package imformation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;

import connect_SQL.Connect;
import exception.Myexception;
import order.Order;

public class fillInformation
{
	static int cnt = 0;
	JFrame frame = new JFrame("信息填写");
	JPanel panel = new JPanel();
	JLabel idlabel = new JLabel("身份证号:");
	JLabel namelabel = new JLabel("姓名:");
	JTextField idtxt = new JTextField();
	JTextField nametxt = new JTextField();
	JButton confirm,cancel;
	JLabel counterlabel = new JLabel("first");
	Order result;
	Vector<String> name,id;
	String startp,endp;
	Vector<String> seat = new Vector<String>();
	static int number;
	public void init()
	{
		cnt = 0;
		panel.setLayout(null);
		confirm = new JButton("确定");
		cancel = new JButton("返回");
		
		panel.add(idlabel);
		panel.add(namelabel);
		panel.add(idtxt);
		panel.add(nametxt);
		panel.add(confirm);
		panel.add(cancel);
		String s = "请输入乘客的信息";
		
		counterlabel = new JLabel(s);
		counterlabel.setBounds(150, 50, 180, 40);
		counterlabel.setFont( new Font("微软雅黑",Font.BOLD,18));
		panel.add(counterlabel);
		
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
	public fillInformation( int number,Order result,Vector<String> seat)
	{
		for(int i=0;i<seat.size();i++)
			this.seat.add(seat.get(i));
		
		id = new Vector<String>();
		name = new Vector<String>();
		init();
		addevent();
		this.number = number;
		this.result = result;
			
		
	}
	
	public void addevent()
	{
		confirm.addActionListener( new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				
				String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
			                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
				String a = idtxt.getText();
				String b = nametxt.getText();
				if(a.matches(regularExpression)) {
					id.add(a);
					name.add(b);
					cnt++;
					if( cnt == number )
					{
						save();
						frame.dispose();
						new Myexception("添加成功");
					}
					else
						{
							String s = "请输入第"+String.valueOf(cnt+1)+"位乘客的信息";
							new Myexception(s);
					}
				}
				else {
					new Myexception("身份证号有误！请重输！");
				}
						
				idtxt.setText("");
				nametxt.setText("");
				
				
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
	public void save()
	{
		Statement st;
		
		try
		{
			int flight_id = result.id;
			String query;
			ResultSet rs;
			for( int i = 0 ; i < id.size() ; i++ )
			{
				 st = new Connect().st;
				 String ID,Name;
				 ID = id.get(i);
				 Name = name.get(i);
				 query = "select * from u where uid = '"+id.get(i)+"'";
				 rs = st.executeQuery(query);
				 
				 if( !rs.next() )
				 {
					 st = new Connect().st;
					 query = "insert into u values('"+ID+"','"+Name+"')";
					 st.execute(query);
				 }
				 st = new Connect().st;
				 query = "insert into user_flight values('"+ID+"','"+String.valueOf(result.id)+"','"+seat.get(i)+"')";
				 st.execute(query);
				 
			}
		}
		catch( Exception ee )
		{
			ee.printStackTrace();
			new Myexception("添加失败");
		}
	}
	
}
