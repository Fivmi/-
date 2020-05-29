package comparator;

import java.util.Comparator;

import order.Order;

import java.util.*;

public class PriceComparator implements Comparator<Order>
{

	@Override
	public int compare(Order o1, Order o2) 
	{
		if( o1.price > o2.price )
			return 1;
		return -1;
	}
	
}
