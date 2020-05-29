package connect_SQL;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

import javax.swing.*;
/*
 * 数据库连接
 */
public class Connect
{
	
	public Connection con;
	public Statement st;
	
	public Connect()
	{
		  String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";//数据库引擎

		  //String dbURL="jdbc:sqlserver://192.168.43.155:1433;DatabaseName=AirTickets_MS";//链接地址
		  String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=AirTickets_MS";//链接地址

		  String userName="sa";

		  //String userPwd="869890345";
		  String userPwd="123456";

		  try

		  {
			   Class.forName(driverName);
			   con = DriverManager.getConnection(dbURL,userName,userPwd);
			   	st = con.createStatement();
		        
			}

		  catch(Exception e)

		  {
	
			   e.printStackTrace();
			  JFrame frame = new JFrame("提示");
			  JPanel panel = new JPanel();
			  JLabel label = new JLabel("数据库连接失败，请检查1433端口");
			  label.setFont( new Font("楷体",Font.BOLD,13) );
			  panel.add(label);
			  frame.add(panel);
			  
			  frame.setVisible(true);
			  frame.setSize(300, 300);
			  frame.setLocationRelativeTo(null);
			  
		  }
	}
}