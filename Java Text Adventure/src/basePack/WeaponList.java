package basePack;

public class WeaponList 
{
	protected String name;
	protected int range;
	protected int minDamage;
	protected int maxDamage;
	protected String length;
	protected int ammo;

	public WeaponList(String name,int minDamage,int maxDamage,int range, int ammo)
	{
		this.name = name;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.range = range;
		setLength();
		if (range > 1)
		{
			this.ammo = ammo;
		}
	}

	public void Fire()
	{
		--ammo;
	}

	public int addminDamage(int i) 
	{ 
		minDamage = minDamage + i;
		return minDamage; 
	}
	
	public int addmaxDamage(int i) 
	{ 
		maxDamage = maxDamage + i;
		return maxDamage; 
	}
	
	public int Reload (int Round)
	{
		ammo = Round;
		return ammo;
	}

	public String getName() 
	{ 
		return name; 
	}
	
	public int getminDamage()
	{
		return minDamage;
	}
	
	public int getmaxDamage() 
	{ 
		return maxDamage; 
	}
	public int getRange() 
	{ 
		return range; 
	}
	public String getLength() 
	{ 
		return length; 
	}
	public int getAmmo() 
	{ 
		return ammo; 
	}

	private void setLength()
	{
		if (range == 0)
		{
			length = "NULL";
		}
		else if (range == 1)
		{
			length = "Melee";
		}
		else if (range == 2)
		{
			length = "Ranged";
		}
		else
		{
			System.out.println("Out of range index");
		}
	}

	public String toString()
	{
		String display = "Name: " + name + "\nDamage: " + minDamage +"-"+ maxDamage + "\nRange: " + length +" ("+range+") " + " \nAmmo:  [" + ammo + "] ";
		return display;
	}
}
