package basePack;
import java.util.Random;
public class Dice 
{
	public int Accuracy()
	{
		Random RamAccuracy = new Random();
		int accuracy = RamAccuracy.nextInt(8)+1;
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
	
	public int Target()
	{
		Random RamTarget = new Random();
		int Target = RamTarget.nextInt(6)+1;
		return Target;
	}
	
	public int move()
	{
		Random ramMove = new Random();
		int Move = ramMove.nextInt(4)+1;
		return Move;
	}
	
	public int dodge()
	{
		Random ramMove = new Random();
		int Move = ramMove.nextInt(4)+1;
		return Move;
	}
}
