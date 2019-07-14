package basePack;
import java.util.Random;
public class Cover 
{
	private int CoverXLocation;
	private int CoverYLocation;
	private int CoverNumber;
	private int CoverHealth;
	
	private boolean value;
	
	public Cover(int CoverXLocation, int CoverYLocation, int CoverHealth)
	{
		this.CoverXLocation = CoverXLocation;
		this.CoverYLocation = CoverYLocation;
		this.CoverHealth = CoverHealth;
		//this.CoverNumber = CoverNumber;
	}
	
	public int CoverXMaker(int x)
	{
		Random RamLoc = new Random();
		int Loc = RamLoc.nextInt(x)+1;
		CoverXLocation = Loc;
		return CoverXLocation;
	}
	
	public int CoverYMaker(int y)
	{
		Random ramLoc = new Random();
		int loc = ramLoc.nextInt(y)+1;
		CoverYLocation = loc;
		return CoverYLocation;
	}
	
	public void DamageCover()
	{
		CoverHealth--;
	}
	
	public int getCoverHealth()
	{
		return CoverHealth;
	}
	
	public int getCoverXLocation()
	{
		return CoverXLocation;
	}
	
	public int getCoverYLocation()
	{
		return CoverYLocation;
	}
	
	public int getCoverNumber()
	{
		return CoverNumber;
	}
	
	public boolean CoverTrue(int a)
	{
		if(a == CoverNumber)
		{
			value = true;
		}
		else
		{
			value = false;
		}
		return value;
	}
	
	public String toString()
	{
		String display = "X:["+CoverXLocation+"] Y:["+CoverYLocation+"] HP:["+CoverNumber+"]";
		return display;
	}
}
