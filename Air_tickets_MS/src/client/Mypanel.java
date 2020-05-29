package client;
import java.awt.*;
import javax.swing.*;
public class Mypanel extends JPanel
{
	ImageIcon img;
	public Mypanel( ImageIcon img )
	{
		this.img = img;
	}
	public void paintComponent( Graphics g )
	{
		super.paintComponent(g);
		g.drawImage(img.getImage(),0,0,null);
	}
}
