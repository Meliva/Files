package TheBox;

public class Stats 
{
	private int health;
	private int armour;
	
	public Stats(int health,int armour)
	{
		this.health = health;
		this.armour = armour;
	}
	
	public int getHealth() { return health; }
	public int getArmour() { return armour; }
	
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
	
	public int healHp(int hp)
	{
		hp = health;
		return health;
	}
	
	public int healArmour(int arm)
	{
		arm = armour;
		return armour;
	}
	
	public String toString()
	{
		String display = "Health: " + health + "\nArmour: " + armour;
		return display;
	}
	
}
