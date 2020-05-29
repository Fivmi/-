package comparator;

import java.util.Comparator;

import order.Order;

public class DateComparator implements Comparator<Order>
{

	@Override
	public int compare(Order a, Order b) 
	{
		return a.starttime.compareTo(b.starttime);
	}

}
