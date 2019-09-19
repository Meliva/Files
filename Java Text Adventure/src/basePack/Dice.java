package basePack;
import java.util.Random;
public class Dice 
{
	public int Accuracy()
	{
		Random RamAccuracy = new Random();
		int accuracy = RamAccuracy.nextInt(15)+1;
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
		int crit = RamCrit.nextInt(20)+1;
		return crit;
	}
	
	public int Turn()
	{
		Random RamTurn = new Random();
		int turn = RamTurn.nextInt(4)+1;
		return turn;
	}
	
	public int Target()
	{
		Random RamTarget = new Random();
		int target = RamTarget.nextInt(6)+1;
		return target;
	}
	
	public int move()
	{
		Random ramMove = new Random();
		int move = ramMove.nextInt(4)+1;
		return move;
	}
	
	public int Pierce()
	{
		Random ramPierce = new Random();
		int pierce = ramPierce.nextInt(6)+1;
		return pierce;
	}
	
	public int Dialog()
	{
		Random ramDialog = new Random();
		int dialog = ramDialog.nextInt(4)+1;
		return dialog;
	}
}
