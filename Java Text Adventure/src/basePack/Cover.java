package basePack;
import java.util.Random;
public class Cover 
{
	protected int CoverXLocation;
	protected int CoverYLocation;
	protected int CoverHealth;
	
	public Cover(int CoverXLocation, int CoverYLocation, int CoverHealth)
	{
		this.CoverXLocation = CoverXLocation;
		this.CoverYLocation = CoverYLocation;
		this.CoverHealth = CoverHealth;
	}
	
	public int CoverXMaker(int x)
	{
		Random RamXLoc = new Random();
		int XLoc = RamXLoc.nextInt(x)+1;
		CoverXLocation = XLoc;
		return CoverXLocation;
	}
	
	public int CoverYMaker(int y)
	{
		Random RamYLoc = new Random();
		int YLoc = RamYLoc.nextInt(y)+1;
		CoverYLocation = YLoc;
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
	
	public int getCoverX()
	{
		return CoverXLocation;
	}
	
	public int getCoverY()
	{
		return CoverYLocation;
	}
		
	public String toString()
	{
		String display = "X:["+CoverXLocation+"] Y:["+CoverYLocation+"] HP:["+CoverHealth+"]";
		return display;
	}
}
