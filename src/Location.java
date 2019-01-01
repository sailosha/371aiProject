/**
 * @author Yiwei Mao
 * Last modify on 2018/12/14 ${time}
 */
public class Location
{
	int x,y;
	Location (int a,int b)
	{
		x = a;
		y = b;
	}

	void printLocation()
	{
		System.out.println("x: "+x+" y:"+y);
	}

	boolean compairTo(Location index)
	{
		if(x==index.x&&y==index.y)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
