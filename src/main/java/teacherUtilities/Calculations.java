package teacherUtilities;


public class Calculations
{	
	int a, b;
	
	Calculations(int a, int b)
	{
		this.a = a;
		this.b = b;
	}
	
	static int random(int value_range)
    {        
        return (int)(Math.random() * value_range);  
    }
}
