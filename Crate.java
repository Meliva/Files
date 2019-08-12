package basePack;
import java.util.Random;
public class Crate 
{
	protected int CrateXLocation;
	protected int CrateYLocation;
	protected int CrateUse;
	protected int CrateType;
	
	public Crate(int CrateXLocation, int CrateYLocation, int CrateUse, int CrateType)
	{
		this.CrateXLocation = CrateXLocation;
		this.CrateYLocation = CrateYLocation;
		this.CrateUse = CrateUse;
		this.CrateType = CrateType;
	}
	
	public int CrateXMaker(int x)
	{
		Random RamXLoc = new Random();
		int XLoc = RamXLoc.nextInt(x)+1;
		CrateXLocation = XLoc;
		return CrateXLocation;
	}
	
	public int CrateYMaker(int y)
	{
		Random RamYLoc = new Random();
		int YLoc = RamYLoc.nextInt(y)+1;
		CrateYLocation = YLoc;
		return CrateYLocation;
	}
	
	public int useCrate()
	{
		CrateUse--;
		return CrateUse;
	}
	
	public int genCrateType()
	{
		Random RanGen = new Random();
		int Gen = RanGen.nextInt(9)+1;
		CrateType = Gen;
		return CrateType;
	}
	
	public int getCrateX()
	{
		return CrateXLocation;
	}
	
	public int getCrateY()
	{
		return CrateYLocation;
	}
	
	public int getCrateUse()
	{
		return CrateUse;
	}
	
	public int getCrateType()
	{
		return CrateType;
	}

}
