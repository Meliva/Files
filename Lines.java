package basePack;

public class Lines 
{
	protected int deathLine = 0;
	protected static String Word;
	
	public static String GrabWord(int a)
	{
		switch (a)
		{
		case 1 :
			Word = "Blleehh";
			break;
		case 2 :
			Word = "Aarrgghh";
			break;
		case 3 :
			Word = "*Death Noise*";
			break;
		case 4 :
			Word = "...Ouch...";
			break;
		case 5 :
			Word = "*Death Gurgle*";
			break;
		case 6 :
			Word = "gguuhh";
			break;
		case 7 :
			Word = "Why are my organs over there?";
			break;
		case 8 :
			Word = "...Ow...";
			break;
		case 9 :
			Word = "...Nnoo...";
			break;
		}
		return Word;
	}
	public static String GrabAlly(int b)
	{
		switch (b)
		{
		case 1 :
			Word = "...Remeber me...";
			break;
		case 2 :
			Word = "I...cannot...die";
			break;
		case 3 :
			Word = "*Death Noise*";
			break;
		case 4 :
			Word = "*Death Gurgle*";
			break;
		case 5 :
			Word = "I..have...fallen";
			break;
		case 6 :
			Word = "*Death Noise*";
			break;
		}
		return Word;
	}
	public static String getWord()
	{
		return Word;
	}
}
