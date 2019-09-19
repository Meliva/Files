package basePack;

public class WeaponList 
{
	protected String name;
	protected int range;
	protected int minDamage;
	protected int maxDamage;
	protected String length;
	protected int ammo;
	protected int pierce;

	public WeaponList(String name,int minDamage,int maxDamage, int pierce,int range,int ammo)
	{
		this.name = name;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.pierce = pierce;
		this.range = range;
		setLength();
		this.ammo = ammo;
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

	public int getpierceDamage()
	{
		return pierce;
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
	}

	public String toString()
	{
		String display = "Name: "+name+"\nDamage: ["+minDamage+"-"+maxDamage+"]\nPierce: "+pierce+"\nRange: "+length+" ("+range+") "+" \nAmmo:  ["+ ammo +"] ";
		return display;
	}
}
