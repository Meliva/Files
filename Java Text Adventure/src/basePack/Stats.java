package basePack;

import java.util.ArrayList;

public class Stats 
{
	protected int health;
	protected int armour;
	
	public Stats(int health,int armour)
	{
		this.health = health;
		this.armour = armour;
	}
	
	public int getHealth() 
	{ 
		return health; 
	}
	
	public int getArmour() 
	{ 
		return armour; 
	}
	
	public int damageHealth(int damage)
	{
		health = health - damage;
		return health;
	}
	
	public int damageArmour(int damage)
	{
		armour = armour - damage;
		return armour;
	}
	
	public int HealHp(int hp)
	{
		health = hp;
		return health;
	}
	
	public int addHp(int ah) 
	{ 
		health = health + ah;
		return health; 
	}
	
	public int HealArmour(int arm)
	{
		armour = arm;
		return armour;
	}
	
	public int addArmour(int aa) 
	{ 
		armour = armour + aa;
		return armour; 
	}
	
	public String toString()
	{
		ArrayList<String> display = new ArrayList<String>();
		int count = 0;
		int bars = getHealth();
		int armourCount = 0;
		int armourBars = getArmour();
		display.add("\n--Health--\n");
		display.add("[");
		while(count < bars)
		{
			display.add("/");
			count ++;
		}	
		display.add("]");
		display.add("\n--Armour--\n");
		display.add("[");
		while(armourCount < armourBars)
		{
			display.add("/");
			armourCount ++;
		}
		display.add("]");
		StringBuilder listArray = new StringBuilder();
		for (String array : display)
		{
			listArray.append(array);
		}		
		return listArray.toString();
	}
	
}
