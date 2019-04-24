package TheBox;
import java.util.*;
public class Dice 
{
	public int Accuracy()
	{
		Random RamAccuracy = new Random();
		int accuracy = RamAccuracy.nextInt(6)+1;
		return accuracy;
	}
	
	public int Save()
	{
		Random RamSave = new Random();
		int save = RamSave.nextInt(6)+1;
		return save;
	}
	
	public int Crit()
	{
		Random RamCrit = new Random();
		int Crit = RamCrit.nextInt(12)+1;
		return Crit;
	}
	
	public int Turn()
	{
		Random RamTurn = new Random();
		int Turn = RamTurn.nextInt(4)+1;
		return Turn;
	}
}
