package exception;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/*
 * ��ʾ����
 */
public class Myexception extends Exception 
{
	public Myexception() {
		super();
	}
	
	public Myexception(String s )
	{
		JFrame frame = new JFrame("��ʾ");
		JLabel label;
		JPanel panel,panel1,panel2;
		JButton button;
		button = new JButton("ȷ��");
		button.setFont( new Font("΢���ź�",Font.PLAIN,16));
		panel = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		label = new JLabel(s,JLabel.CENTER);
		label.setForeground(Color.red);
		label.setFont( new Font( "΢���ź�",Font.BOLD,20) );
		
		panel1.add(label);
		panel2.add(button);
		panel.setLayout( new BoxLayout(panel, BoxLayout.Y_AXIS) );
		panel.add( Box.createRigidArea( new Dimension(40,50) ) );
		
		panel.add(panel1);
		panel.add(panel2);
		
		
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(240,200);
		frame.setLocationRelativeTo(null);
		
		button.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				frame.setVisible(false);
				frame.dispose();
			}
		});
	}
}
