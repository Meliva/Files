package TheBox;

public class WeaponList 
{
	private String name;
	private int range;
	private int damage;
	private String length;
	private int ammo;
	//public int Clip;

	public WeaponList(String name,int damage,int range, int ammo)
	{
		this.name = name;
		this.damage = damage;
		this.range = range;
		setLength();
		if (range > 1)
		{
			this.ammo = ammo;
		}
	}

	public void incRange()
	{
		++range;
		setLength();
	}
	public void dexRange()
	{
		--range;
		setLength();
	}

	public void Fire()
	{
		--ammo;
	}

	public int Reload (int Round)
	{
		ammo = Round;
		return ammo;
	}

	public String getName() { return name; }
	public int getDamage() { return damage; }
	public int getRange() { return range; }
	public String getLength() { return length; }
	public int getAmmo() { return ammo; }

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
			length = "Close";
		}
		else if (range == 3)
		{
			length = "Medium";
		}
		else if (range == 4)
		{
			length = "Far";
		}
		else if (range == 5)
		{
			length = "Very Far";
		}
		else
		{
			System.out.println("Out of range index");
		}
	}

	public String toString()
	{
		String display = "Name: " + name + "\nDamage: " + damage + "\nRange: " + length +" ("+range+") " + " \nAmmo:  [" + ammo + "] ";
		return display;
	}
}
