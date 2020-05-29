package imformation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import connect_SQL.Connect;
import exception.Myexception;
import function.ChooseSeat;
import order.Order;

public class deleteInformation
{
	String id,name;
	JFrame frame = new JFrame("删除界面");
	JPanel panel = new JPanel();
	ResultSet rs;
	JTable table = new JTable();
	JScrollPane scroll;
	Vector rowdata = new Vector();
	Vector columnnames = new Vector();
	JButton delete = new JButton("删除");
	JButton cancel = new JButton("返回");
	Vector row;
	Vector<Order> rr = new Vector<Order>();
	
	public void init()
	{
		panel.setLayout(null);
		panel.add(scroll);
		panel.add(delete);
		panel.add(cancel);
		
		delete.setBounds(150, 400, 80, 30);
		cancel.setBounds(240, 400, 80, 30);
		scroll.setBounds(0, 50, 500, 300);
		
		table.setRowHeight(250);
		
		
		delete.setFont( new Font("微软雅黑",Font.BOLD,18) );
		cancel.setFont( new Font("微软雅黑",Font.BOLD,18) );
		
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(500, 550);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
	}
	public deleteInformation( String id,String name )
	{
		this.id = id;
		this.name = name;
		Statement st = new Connect().st;
		String query = "select * from flight where flight_id in ( select flight_id from user_flight where uid = '"+id+"' )";
		try
		{
			rs = st.executeQuery(query);
			addevent();
			gettable();
			init();
		}
		catch( Exception ee ) {
			new Myexception("数据库异常");
		}
		
	}
	public void addevent()
	{
		delete.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) //点击触发删除事件
			{
				int r = table.getSelectedRow();
				try
				{
					Statement st = new Connect().st;
					ResultSet rs;
					String seat;	//表示座位号
					int flight_id = ( (Order)rr.get(r) ).id;
					String query;
					//先查询是用户是哪一个座位
					query = "select seat from user_flight where flight_id = "+String.valueOf(flight_id)
							+" and uid = '"+id+"'";
					rs = st.executeQuery(query);
					rs.next();
					seat = rs.getString(1);	//得到座位号
					int row=seat.charAt(0)-'0';
					char col=seat.charAt(1);
					int place=0;
					if(getcol(col)<=3) {
						place=(row-1)*3+getcol(col);
					}
					else {
						place=30+(row-1)*3+getcol(col)-3;
					}
					//System.out.println(place);
					ChooseSeat.a[place]=0;
					st = new Connect().st;
					query = "delete from user_flight where uid = "+String.valueOf(id);
					st.executeUpdate(query);	
					frame.dispose();
					new deleteInformation(id, name);
					new Myexception("更新成功");
				}
				catch( Exception  ee )
				{
					ee.printStackTrace();
					new Myexception("删除失败");
				}
			}
		});
		cancel.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}
	public void gettable()
	{
		try 
		{
			columnnames.add("您的机票");
			
			String total;
			String first,time;
			String companyname,starttime,endtime,startplace,endplace;
			String startairport,endairport;
			String seat;
			float price;
			while( rs.next() )
			{
				row = new Vector();
				int flight_id = rs.getInt(1);
				int companyid = rs.getInt(2);
				companyname = getcompany(companyid);
				starttime = rs.getString(3);
				endtime = rs.getString(4);
				price = rs.getFloat(5);
				startplace = rs.getString(6);
				endplace = rs.getString(7);
				startairport = getStartairport(flight_id);
				endairport = getEndairport(flight_id);
				
				starttime = starttime.substring(11, 16)+"  出发";
				endtime = endtime.substring(11, 16)+"  到达";
				seat=getSeat(flight_id);
				Order r = new Order(companyname,startplace,endplace,startairport,endairport,starttime,endtime,price,flight_id,seat);
				rr.add(r);			//维持一个复制，以便删除机票使用
				row.add( concat(r) );
				rowdata.add(row);
			}
			table = new JTable(rowdata,columnnames);
			table.setRowHeight(150);
			scroll = new JScrollPane(table);
			
			DefaultTableCellRenderer cr = new DefaultTableCellRenderer();	//表格字体居中
			cr.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, cr);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	public int getcol(char c) {
		switch(c) {
		case 'A':return 1;
		case 'B':return 2;
		case 'C':return 3;
		case 'D':return 4;
		case 'E':return 5;
		case 'F':return 6;
		}
		return c;
	}
	public String getSeat(int flight_id) {
		Statement st = new Connect().st;
		try
		{
			ResultSet rs = st.executeQuery("select seat from user_flight where flight_id ="+String.valueOf(flight_id) );
			rs.next();
			return rs.getString(1);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getcompany( int companyid )
	{
		Statement st = new Connect().st;
		try
		{
			ResultSet rs = st.executeQuery("select company_name from airline_company where company_id ="+String.valueOf(companyid) );
			rs.next();
			return rs.getString(1);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getStartairport( int flight_id )
	{
		Statement st = new Connect().st;
		try
		{
			String query = 
			"select airport_name from airport where airport_id in ("
			+ "select airport_id from start_airport where flight_id = "+String.valueOf(flight_id)
			+")";
			ResultSet rs = st.executeQuery(query);
			rs.next();
			return rs.getString(1);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getEndairport( int flight_id )
	{
		Statement st = new Connect().st;
		try
		{
			String query = 
			"select airport_name from airport where airport_id in "
			+ "("
			+ "select airport_id from arrival_airport where flight_id = "+String.valueOf(flight_id)
			+")";
			ResultSet rs = st.executeQuery(query);
			rs.next();
			return rs.getString(1);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String concat( Order result )
	{
		String total,place,time,airport,last,seat;
		String space = "&nbsp;";	//HTML中的空格
		String tab = "&#9;";		//HTML中的tab
		String sspace = space+space+space;
		seat = Generate_HTML_String("微软雅黑", "200%", "black", result.seat);
		place = Generate_HTML_String("微软雅黑", "150%", "red", result.startplace+"---------"+result.endplace);
				
		time = Generate_HTML_String("微软雅黑", "120%", "black", result.starttime+tab+result.endtime);
		
		airport = Generate_HTML_String("微软雅黑", "125%", "black", result.startairport+sspace+sspace+result.endairport);
		
		last = Generate_HTML_String("微软雅黑", "125%", "black", result.companyname+sspace+"RMB:"+String.valueOf(result.price) );
		total = "<html>"+seat+place+time+airport+last+"</html>";
		return total;
	}
	public String Generate_HTML_String(String font,String size_percentage,String color,String content )
	{
		String q = "\"";
		String total = "<p style = "+q+"font-family:"+font+";"
				+"font-size:"+size_percentage+";"
				+"color:"+color+q+">"
				+content
				+"</p>";
		return total;
	}

}
