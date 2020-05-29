
/*
 *  功能：显示航班信息
 *	包括价格，地点等信息
 *
 */

package function;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import comparator.DateComparator;
import comparator.PriceComparator;
import connect_SQL.Connect;
import exception.Myexception;
import order.Order;


public class bookTickets
{
	JFrame frame = new JFrame("航班信息");
	JPanel panel = new JPanel();
	JPanel downpanel = new JPanel();
	ResultSet rs;
	Vector rowdata1,rowdata2,columnnames;
	JScrollPane pricescroll,datescroll;
	JTable pricetable,datetable;
	JButton pricebutton,datebutton;
	String u,v,startairport,endairport;	
	Statement st[] = new Statement[4];
	static int number;
	Vector<Order> temp1,temp2;	
	CardLayout card = new CardLayout();
	String getyear( String date )
	{
		int ans = 0;
		for( int i = 0 ; i < 4 ; i++ )
			ans = ans*10+(date.charAt(i)-'0' );
		return String.valueOf(ans);
	}
	String getmonth( String date )
	{
		return String.valueOf( (date.charAt(5)-'0')*10+(date.charAt(6)-'0') );
	}
	String getday( String date )
	{
		return String.valueOf( (date.charAt(8)-'0')*10+(date.charAt(9)-'0') );
	}
	
	public void showtable( )
	{
		
		try
		{
			columnnames = new Vector();
			rowdata1 = new Vector();
			rowdata2 = new Vector();
			columnnames.add("航班信息");	//表格最上方的
			panel.setLayout( card );
			String companyname;
			String time1,time2;
			String total;
			float price,discount;
			temp1 = new Vector<Order>();//价格排序
			temp2 = new Vector<Order>();//时间排序
			int id;
			while( rs.next() )	//将之前SQL查询的数据弄到 Vector中
			{
				
				time1 = rs.getTime(1).toString().trim()+" 出发";
				time2 = rs.getTime(2).toString().trim()+" 到达";
				companyname = rs.getString(3);
				price = rs.getFloat(4);
				id = rs.getInt(5);
				Order tt = new Order(companyname,u,v,startairport,endairport,time1,time2,price,id);
				temp1.add(tt);
				temp2.add(tt);
				
			}
			temp1.sort( new PriceComparator() );
			temp2.sort( new DateComparator() );
			//temp1按照价格排序，temp2按照时间排序
			
			Vector row1,row2;
			for( int i = 0 ; i < temp1.size() ; i++ )
			{
				row1 = new Vector();
				Order rr = (Order)temp1.get(i);
				String s = concat(rr);
				row1.add(s);
				rowdata1.add(row1);
				
				row2 = new Vector();
				rr = (Order)temp2.get(i);
				s = concat(rr);
				row2.add(s);
				rowdata2.add(row2);
			}
			
			pricetable = new JTable(rowdata1,columnnames);
			datetable = new JTable(rowdata2,columnnames);
			settable();
			pricescroll = new JScrollPane(pricetable);
			datescroll = new JScrollPane(datetable);
			
			
			
			addEvent();					//为按钮添加事件
			pricebutton.setFont(new Font("微软雅黑",Font.BOLD,16) );
			datebutton.setFont( new Font("微软雅黑",Font.BOLD,16) );
			panel.add(pricescroll);
			panel.add(datescroll);
			
			downpanel.add(pricebutton);
			downpanel.add(datebutton);
			
			frame.add(panel);
			frame.add(downpanel,BorderLayout.SOUTH);
			frame.setVisible(true);
			frame.setSize(500, 600);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocationRelativeTo(null);
		}
		catch( Exception ee )
		{
			ee.printStackTrace();
			new Myexception("显示航班错误");
		}
		
	}
	public void addEvent()			//两种排序方式按钮
	{
		//控制显示上一张的按钮
		pricebutton = new JButton("价格排序");
		pricebutton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				card.previous(panel);
			}
		});
		//控制显示下一张的按钮
		datebutton = new JButton("出发时间排序");
		datebutton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				card.next(panel);
			}
		});
		
	}
	//将查询结果按格式显示
	public String concat( Order result )
	{
		String total,place,time,airport,last;
		String space = "&nbsp;";	//HTML中的空格
		String tab = "&#9;";		//HTML中的tab
		String sspace = space+space+space;
		place = Generate_HTML_String("微软雅黑", "150%", "red", result.startplace+"---------"+result.endplace);
				
		time = Generate_HTML_String("微软雅黑", "115%", "black", result.starttime+sspace+sspace+result.endtime);
		
		airport = Generate_HTML_String("微软雅黑", "125%", "black", result.startairport+sspace+sspace+result.endairport);
		
		last = Generate_HTML_String("微软雅黑", "125%", "black", result.companyname+sspace+"RMB:"+String.valueOf(result.price) );
		total = "<html>"+place+time+airport+last+"</html>";
		//System.out.println(total);
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
	class MyMouseAdapter extends MouseAdapter	
	{
		public void mouseClicked( MouseEvent e )
		{
			
			int t1 = pricetable.getSelectedRow();
			int t2 = datetable.getSelectedRow();
			
			if( t1 >= 0 )
			{
				new ChooseSeat( temp1.get(t1),number );
				frame.dispose();
			}
			else if( t2 >= 0 )
			{
				new ChooseSeat( temp2.get(t2),number );
				frame.dispose();
			}
			
		}
	}
	public void settable()
	{
		pricetable.setRowHeight(130);			//设置高度
		datetable.setRowHeight(130);
		pricetable.setBackground(Color.WHITE);		//修改表格颜色
		datetable.setBackground(Color.WHITE);
		
		pricetable.addMouseListener( new MyMouseAdapter()  );
		datetable.addMouseListener( new MyMouseAdapter() );
		
		
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();	//表格字体居中
		cr.setHorizontalAlignment(JLabel.CENTER);
		pricetable.setDefaultRenderer(Object.class, cr);
		datetable.setDefaultRenderer(Object.class, cr);					//表格字体居中
		
	}
	public bookTickets(String u,String v,String date,int number )		
	{																	
		
		try
		{
			this.number = number;
			this.u = u;
			this.v = v;
			panel.setLayout(null);
			String cc = "Select departure_time,arrival_time,company_name,price,flight_id "
					+ "from airline_company,flight "
					+ " where airline_company.company_id = flight.company_id "
					+"and flight.flight_id in "
					+"("
						+"select flight_id from flight where "
						+ "departure_place = '"+u+"' and destination ='"+v+"'"
						+" and year(departure_time) ="+getyear(date)
						+" and month(departure_time)="+getmonth(date)
						+" and day(departure_time)="+getday(date)
					+")";
			st[1] = new Connect().st;
			rs = st[1].executeQuery(cc);
			
			st[2] = new Connect().st;
			ResultSet tt = st[2].executeQuery("select airport_name from airport " + "where airport_place = '"+u+"'");
			tt.next();
			startairport = tt.getString(1);
			st[3] = new Connect().st;
			String ss = "select airport_name from airport " + "where airport_place = '"+v+"'";
			tt = st[3].executeQuery(ss);
			
			tt.next();
			endairport = tt.getString(1);
			showtable();
			
		}
		catch( Exception ee )
		{
			new Myexception("查询失败，可能是没数据");
			ee.printStackTrace();
		}
	}

}
