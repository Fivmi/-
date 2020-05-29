package order;
public class Order 
{
	
	public String companyname;
	public String startplace,endplace;
	public String startairport,endairport;
	public String starttime,endtime;
	public int id;
	public float price;
	public String seat;
	public Order(String companyname,String startplace,String endplace,
			
	String startairport,String endairport,String starttime,String endtime,float price,int id )
	{
		this.id = id;
		this.companyname = companyname;
		this.starttime = starttime;
		this.startplace = startplace;
		this.startairport = startairport;
		this.endplace = endplace;
		this.endtime = endtime;
		this.endairport = endairport;
		this.price = price;
		
	}
	public Order(String companyname,String startplace,String endplace,
	String startairport,String endairport,String starttime,String endtime,float price,int id ,String seat)
	{
		this.id = id;
		this.companyname = companyname;
		this.starttime = starttime;
		this.startplace = startplace;
		this.startairport = startairport;
		this.endplace = endplace;
		this.endtime = endtime;
		this.endairport = endairport;
		this.price = price;
		this.seat=seat;
	}
}
