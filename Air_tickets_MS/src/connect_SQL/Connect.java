package connect_SQL;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

import javax.swing.*;
/*
 * ���ݿ�����
 */
public class Connect
{
	
	public Connection con;
	public Statement st;
	
	public Connect()
	{
		  String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";//���ݿ�����

		  //String dbURL="jdbc:sqlserver://192.168.43.155:1433;DatabaseName=AirTickets_MS";//���ӵ�ַ
		  String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=AirTickets_MS";//���ӵ�ַ

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
			  JFrame frame = new JFrame("��ʾ");
			  JPanel panel = new JPanel();
			  JLabel label = new JLabel("���ݿ�����ʧ�ܣ�����1433�˿�");
			  label.setFont( new Font("����",Font.BOLD,13) );
			  panel.add(label);
			  frame.add(panel);
			  
			  frame.setVisible(true);
			  frame.setSize(300, 300);
			  frame.setLocationRelativeTo(null);
			  
		  }
	}
}