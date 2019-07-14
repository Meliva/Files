package basePack;

public class Lines 
{
	int deathLine = 0;
	static String Word;
	public static String GrabWord(int a)
	{
		switch (a)
		{
		case 1 :
			Word = "Bleh";
			break;
		case 2 :
			Word = "Aarrgghh";
			break;
		case 3 :
			Word = "*Death Noise*";
			break;
		case 4 :
			Word = "...Ouch";
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
			Word = "*Death Noise*";
			break;
		case 9 :
			Word = "...Ow";
			break;
		}
		return Word;
	}
	public static String getWord()
	{
		return Word;
	}
}
