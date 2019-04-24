package TheBox;
import java.io.IOException;
import java.util.*;
public class TheText 
{
	static Scanner keyboard = new Scanner(System.in);
	static Dice AccRolls = new Dice();
	static Dice ArmRolls = new Dice();
	static Dice CrtRolls = new Dice();
	static Dice TrnRolls = new Dice();
	public int Turn_Count = 0;
	public int Turn_Combat = 0;

	static WeaponList Weapon = new WeaponList(null, 0, 0, 0);
	static WeaponList Weapon_2 = new WeaponList(null, 0, 0, 0);
	public int wepCounter = 0;
	public int statCounter = 0;
	static Stats PlayerStats = new Stats (0,0);
	static Stats PlayerStatsRoll = new Stats (0,0);
	public boolean SecndWeapon = false;
	public int AmmoRoll;
	public int PlayerBeltRoll;
	public int PlayerBelt;
	public int PlayerTempHeal_1;
	public int PlayerHealthHit = 0;
	public int PlayerArmourHit = 0;
	public int PlayerMiss = 0;

	public int EnemyHealthHit = 0;
	public int EnemyArmourHit = 0;
	public int EnemyMiss = 0;
	public WeaponList EnemyWeapon = new WeaponList(null, 0, 0, 0);
	static Stats EnemyStats = new Stats (0,0);
	static Stats EnemyStatsRoll = new Stats (0,0);

	private final int Choose_Status = 1;
	private final int Choose_Weapon = 2;
	private final int Begin_Mission = 3;
	private final int Finish = 4;
	private final int Apothecary = 5;
	private final int Options = 6;

	public int Mission_Count = 0;
	public int EnemyCount = 0;
	public boolean Attack = true;
	public int Score = 0;
	public int Precaug = 0;

	public static void main(String [ ] args) throws IOException
	{
		TheText TT = new TheText();
		TT.run();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void run() throws IOException
	{
		int theChoice = -1;
		try
		{
			while(true)
			{
				System.out.println("---------------------------------------------");
				System.out.println("Menu ");
				System.out.println("\t(" + Choose_Status + ") Pick Health & Armour");
				System.out.println("\t(" + Choose_Weapon + ") Pick Weapon");
				System.out.println("\t(" + Begin_Mission + ") Begin Mission");
				System.out.println("\t(" + Finish + ") Finish Mission");
				System.out.println("\t(" + Apothecary + ") Apothecary");
				System.out.println("\t(" + Options + ") Options");
				System.out.print("Enter choice >> ");
				theChoice = keyboard.nextInt();
				keyboard.nextLine();
				System.out.println("---------------------------------------------");
				switch(theChoice)
				{
				case Choose_Status :
					buildStats();
					break;
				case Choose_Weapon :
					buildWeapon();
					break;
				case Begin_Mission:
					buildMissions();
					break;
				case Finish:
					exit();
					break;
				case Apothecary:
					display_Enemy_Status();
					display_Status();
					//Healing();
					break;
				case Options:
					Set_Options();
					break;
				default:
					System.out.println("Invalid choice");
					break;
				}
			}
		}
		catch(InputMismatchException ex)
		{	
			System.out.println("Critical Error: Only numbers are allowed to be used, no doubles or string");
			System.out.println(ex);
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void exit()
	{
		if(Mission_Count == 3)
		{
			System.out.println("Enough Missions have been done! ("+Mission_Count+"/3)");
			System.out.println("Score: ["+Score+"]");
			System.exit(0);
		}
		else if(Mission_Count > 3)
		{
			int Counter = 0;
			Counter = Mission_Count;
			while(Counter > 3)
			{
				Score = Score + 1000;
				Counter--;
			}
			System.out.println("More than enough missions have been done! ("+Mission_Count+"/3)");
			System.out.println("Score: ["+Score+"]");
			System.exit(0);	
		}
		else
		{
			System.out.println("Not enough Missions! ("+Mission_Count+"/3)");
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void buildMissions()
	{
		if (EnemyCount == 0 || PlayerStats == null || Weapon == null)
		{
			System.out.println("Ensure all values are filled:");
			System.out.println("Enemy count: "+"["+EnemyCount+"]");
			System.out.println("Health: "+PlayerStats.getHealth());
			System.out.println("Armour: "+PlayerStats.getArmour());
			System.out.println(Weapon);
			sleep();
		}
		else
		{
			int action;
			int turn = 0;
			System.out.println("Enemy Sigheted");
			PrimtiveStike();
			while(Attack = true)
			{
				if(EnemyCount == 0)
				{
					Attack = false;
				}
				if(Attack == false)
				{
					System.out.println("All Enmies Killed");
					Mission_Count = Mission_Count + 1;
					turn = 0;
					PlayerHealthHit = 0;
					PlayerArmourHit = 0;
					PlayerMiss = 0;
					EnemyHealthHit = 0;
					EnemyArmourHit = 0;
					EnemyMiss = 0;
					return;
				}
				System.out.println("=====================");
				System.out.println("Turn: "+turn);
				System.out.println("Enimes: "+EnemyCount);
				System.out.println("Belt: "+PlayerBelt);
				System.out.println("Press '1' to Attack \nPress '2' for Status");
				System.out.print(">> ");
				action = keyboard.nextInt();
				switch(action)
				{
				case 1:
					if(Turn_Combat == 1)
					{
						PlayerAttack();
						EnemyAttack();
					}
					else
					{
						EnemyAttack();
						PlayerAttack();				
					}
					turn = turn +1;
					break;
				case 2:
					System.out.println("Player Status");
					display_Status();
					System.out.println("\nEnemy Status");
					display_Enemy_Status();
					break;	
				default:
					System.out.println("Invalid choice");
					break;
				}		
			}
		}

	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public int PrimtiveStike()
	{
		if(Turn_Count != 1)
		{
			if(TrnRolls.Turn() == 1 || TrnRolls.Turn() == 3)
			{
				Turn_Combat = 1;
				Turn_Count = 1;
				System.out.println("\nYou Get The Upper Hand!");
			}
			else
			{
				Turn_Combat = 2;
				Turn_Count = 1;
				System.out.println("\nThe Enemy Gets The Upper Hand!");
			}
		}
		if(PlayerStats.getHealth() != PlayerStatsRoll.getHealth())
		{
			System.out.println("\nYou Take the Time To Temperaly Heal Yourself");
			System.out.println("Health: |"+PlayerStats.getHealth()+"| + "+PlayerTempHeal_1);
			PlayerStats.healHp(PlayerTempHeal_1);
			if(PlayerStats.getHealth() >= PlayerStatsRoll.getHealth())
			{
				PlayerStats.healHp(PlayerStatsRoll.getHealth());
				System.out.println("Went Over Max Health");
			}
			PlayerBeltRoll = PlayerBelt;
		}
		return Turn_Combat;
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void PlayerAttack()
	{
		System.out.println("\nPlayer Turn");
		if (PlayerBelt == 0 &&  SecndWeapon == false)
		{
			AttackNoAmmoRanged();
		}
		else if (PlayerBelt == 0 && SecndWeapon == true)
		{
			AttackMeele();
		}
		else if (PlayerBelt >= 0 && SecndWeapon == true)
		{
			AttackMeeleRanged();
		}
		else
		{
			AttackRanged();
		}
		if(EnemyStats.getHealth() <= 0)
		{
			EnemyKilled();
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void EnemyKilled()
	{
		System.out.println("Enemy Killed");
		System.out.println(Score+" + 50");
		Score = Score + 50;
		EnemyCount = EnemyCount -1;
		if(EnemyCount != 0)
		{
			System.out.println("Another Enemy Sighted");
			EnemyStats.healHp(EnemyStatsRoll.getHealth());
			EnemyStats.healArmour(EnemyStatsRoll.getArmour());
			Turn_Combat = 0;
			Turn_Count = 0;
			PrimtiveStike();
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void EnemyAttack()
	{
		System.out.println("\nEnemy Turn");
		if(EnemyWeapon.getRange() == 1)
		{
			EnemyMeeleAttack();
		}
		else
		{
			EnemyRangedAttack();	
		}
		if(PlayerStats.getHealth() <= 0)
		{
			PlayerDeath();
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void EnemyRangedAttack()
	{
		int Enemydamage = EnemyWeapon.getDamage();
		System.out.println("Enemy Shoots You With: "+ EnemyWeapon.getName());
		if(EnemyWeapon.getAmmo() != 0)
		{
			if(AccRolls.Accuracy() == 1 || AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 5)
			{
				if(CrtRolls.Crit() == 12)
				{
					Enemydamage = EnemyWeapon.getDamage() * 2;
					System.out.println("A Critical Hit!");
				}
				else
				{
					Enemydamage = EnemyWeapon.getDamage();
				}
				if(ArmRolls.Save() == 1 || ArmRolls.Save() == 3 || ArmRolls.Save() == 5)
				{
					if(PlayerStats.getArmour() <= 0)
					{
						System.out.println("Health Damage: ["+PlayerStats.getHealth()+"] - "+Enemydamage);
						PlayerStats.damageHealth(Enemydamage);
						EnemyHealthHit = EnemyHealthHit + 1;
					}
					else
					{
						System.out.println("Armour Damage: ["+PlayerStats.getArmour()+"] - "+Enemydamage);
						PlayerStats.damageArmour(Enemydamage);
						EnemyArmourHit = EnemyArmourHit + 1;
					}
				}
				else
				{
					System.out.println("Health Damage: ["+PlayerStats.getHealth()+"] - "+Enemydamage);
					PlayerStats.damageHealth(Enemydamage);
					EnemyHealthHit = EnemyHealthHit + 1;
				}
			}
			else
			{
				System.out.println("Enemy Missed");
				EnemyMiss = EnemyMiss + 1;
			}
		}
		else if (EnemyWeapon.getAmmo() == 0)
		{
			System.out.println("Realoading...");
			EnemyWeapon.Reload(AmmoRoll);
			PlayerBelt = PlayerBelt -1;
		}
		sleep();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void EnemyMeeleAttack()
	{
		int Enemydamage = EnemyWeapon.getDamage();
		System.out.println("Enemy Hits You With: "+ EnemyWeapon.getName());
		if(AccRolls.Accuracy() == 1 || AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 5)
		{
			if(CrtRolls.Crit() == 12)
			{
				Enemydamage = EnemyWeapon.getDamage() * 2;
				System.out.println("A Critical Hit!");
			}
			else
			{
				Enemydamage = EnemyWeapon.getDamage();
			}
			if(ArmRolls.Save() == 1 || ArmRolls.Save() == 3 || ArmRolls.Save() == 5)
			{
				if(PlayerStats.getArmour() <= 0)
				{
					System.out.println("Health Damage: ["+PlayerStats.getHealth()+"] - "+Enemydamage);
					PlayerStats.damageHealth(Enemydamage);
					EnemyHealthHit = EnemyHealthHit + 1;
				}
				else
				{
					System.out.println("Armour Damage: ["+PlayerStats.getArmour()+"] - "+Enemydamage);
					PlayerStats.damageArmour(Enemydamage);
					EnemyArmourHit = EnemyArmourHit + 1;
				}
			}
			else
			{
				System.out.println("Health Damage: ["+PlayerStats.getHealth()+"] - "+Enemydamage);
				PlayerStats.damageHealth(Enemydamage);
				EnemyHealthHit = EnemyHealthHit + 1;
			}
		}
		else
		{
			System.out.println("Enemy Missed");
			EnemyMiss = EnemyMiss + 1;
		}
		sleep();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void AttackNoAmmoRanged()
	{
		int damage = 0;
		if(AccRolls.Accuracy() == 1 || AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 5)
		{
			System.out.println("Hit Enemy With: "+ Weapon.getName());
			if(CrtRolls.Crit() == 12)
			{
				damage = Weapon.getDamage() * 2;
				System.out.println("A Critical Hit!");
			}	
			else
			{
				damage = Weapon.getDamage() % 2;
			}
			if(ArmRolls.Save() == 1 || ArmRolls.Save() == 3 || ArmRolls.Save() == 5)
			{
				if(EnemyStats.getArmour() <= 0)
				{
					System.out.println("Wounded An Enemy: ["+EnemyStats.getHealth()+"] - "+damage);
					EnemyStats.damageHealth(damage);
					PlayerHealthHit = PlayerHealthHit + 1;
				}
				else
				{
					System.out.println("Armour Save: ["+EnemyStats.getArmour()+"] - "+damage);
					EnemyStats.damageArmour(damage);
					PlayerArmourHit = PlayerArmourHit + 1;
				}
			}
			else
			{
				System.out.println("Wounded An Enemy: ["+EnemyStats.getHealth()+"] - "+damage);
				EnemyStats.damageHealth(damage);
				PlayerHealthHit = PlayerHealthHit + 1;
			}
		}
		else
		{
			System.out.println("Missed");
			PlayerMiss = PlayerMiss + 1;
		}
		sleep();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void AttackMeele()
	{
		int damage = 0;
		System.out.println("Hit Enemy With: "+ Weapon_2.getName());
		if(AccRolls.Accuracy() == 1 || AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 5)
		{
			if(CrtRolls.Crit() == 12)
			{
				damage = Weapon_2.getDamage() * 2;
				System.out.println("A Critical Hit!");
			}	
			else
			{
				damage = Weapon_2.getDamage();
			}
			if(ArmRolls.Save() == 1 || ArmRolls.Save() == 3 || ArmRolls.Save() == 5)
			{
				if(EnemyStats.getArmour() <= 0)
				{
					System.out.println("Wounded An Enemy: ["+EnemyStats.getHealth()+"] - "+damage);
					EnemyStats.damageHealth(damage);
					PlayerHealthHit = PlayerHealthHit + 1;
				}
				else
				{
					System.out.println("Armour Save: ["+EnemyStats.getArmour()+"] - "+damage);
					EnemyStats.damageArmour(damage);
					PlayerArmourHit = PlayerArmourHit + 1;
				}
			}
			else
			{
				System.out.println("Wounded An Enemy: ["+EnemyStats.getHealth()+"] - "+damage);
				EnemyStats.damageHealth(damage);
				PlayerHealthHit = PlayerHealthHit + 1;
			}
		}
		else
		{
			System.out.println("Missed");
			PlayerMiss = PlayerMiss + 1;
		}
		sleep();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void AttackMeeleRanged()
	{
		int damage = 0;
		if (Weapon.getAmmo() != 0)
		{
			if(AccRolls.Accuracy() == 1 || AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 5)
			{
				if(CrtRolls.Crit() == 12)
				{
					damage = Weapon.getDamage() * 2;
					System.out.println("A Critical Hit!");
				}
				else
				{
					damage = Weapon.getDamage();
				}
				if(ArmRolls.Save() == 1 || ArmRolls.Save() == 3 || ArmRolls.Save() == 5)
				{
					if(EnemyStats.getArmour() <= 0)
					{
						System.out.println("Wounded An Enemy: ["+EnemyStats.getHealth()+"] - "+damage);
						EnemyStats.damageHealth(damage);
						PlayerHealthHit = PlayerHealthHit + 1;
					}
					else
					{
						System.out.println("Armour Save: ["+EnemyStats.getArmour()+"] - "+damage);
						EnemyStats.damageArmour(damage);
						PlayerArmourHit = PlayerArmourHit + 1;
					}
				}
				else
				{
					System.out.println("Wounded An Enemy: ["+EnemyStats.getHealth()+"] - "+damage);
					EnemyStats.damageHealth(damage);
					PlayerHealthHit = PlayerHealthHit + 1;
				}
			}
			else
			{
				System.out.println("Missed");
				PlayerMiss = PlayerMiss + 1;
			}
			Weapon.Fire();
		}
		else if (Weapon.getAmmo() == 0)
		{
			System.out.println("Realoading...");
			Weapon.Reload(AmmoRoll);
			PlayerBelt = PlayerBelt -1;
		}
		sleep();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void AttackRanged()
	{
		int damage = 0;
		System.out.println("Shot Enemy With: "+ Weapon.getName());
		if (Weapon.getAmmo() != 0)
		{
			if(AccRolls.Accuracy() == 1 || AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 5)
			{
				if(CrtRolls.Crit() == 12)
				{
					damage = Weapon.getDamage() * 2;
					System.out.println("A Critical Hit!");
				}
				else
				{
					damage = Weapon.getDamage();
				}
				if(ArmRolls.Save() == 1 || ArmRolls.Save() == 3 || ArmRolls.Save() == 5)
				{
					if(EnemyStats.getArmour() <= 0)
					{
						System.out.println("Wounded An Enemy: ["+EnemyStats.getHealth()+"] - "+damage);
						EnemyStats.damageHealth(damage);
						PlayerHealthHit = PlayerHealthHit + 1;
					}
					else
					{
						System.out.println("Armour Save: ["+EnemyStats.getArmour()+"] - "+damage);
						EnemyStats.damageArmour(damage);
						PlayerArmourHit = PlayerArmourHit + 1;
					}
				}
				else
				{
					System.out.println("Wounded An Enemy: ["+EnemyStats.getHealth()+"] - "+damage);
					EnemyStats.damageHealth(damage);
					PlayerHealthHit = PlayerHealthHit + 1;
				}
			}
			else
			{
				System.out.println("Missed");
				PlayerMiss = PlayerMiss + 1;
			}
			Weapon.Fire();
		}
		else if (Weapon.getAmmo() == 0)
		{
			System.out.println("Realoading...");
			Weapon.Reload(AmmoRoll);
			PlayerBelt = PlayerBelt -1;
		}
		sleep();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void Healing()
	{
		PlayerStats.healHp(PlayerStatsRoll.getHealth());
		PlayerStats.healArmour(PlayerStatsRoll.getArmour());
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void Set_Options()
	{
		int count;
		System.out.print("Set Enemy Count >> ");
		count = keyboard.nextInt();
		EnemyCount = count;
		int Type = 0;
		System.out.println("Enemy List");
		System.out.println("1 = Traitors");
		System.out.println("2 = Pointy Ears");
		System.out.println("3 = Green Ones");
		System.out.println("4 = Hungry Ones");
		System.out.println("5 = Metal Skeletons");
		System.out.println("6 = Blue Shooters");
		System.out.println("9 = Dummy");
		System.out.print("Enemy Type >> ");
		Type = keyboard.nextInt();
		switch(Type)
		{
		case 1 :
			EnemyStats = new Stats (40,40);
			EnemyWeapon = new WeaponList("Evil Bolter", 9, 3, 3);
			break;
		case 2 :
			EnemyStats = new Stats (20,15);
			EnemyWeapon = new WeaponList("Thin Shooty Gun", 6, 3, 4);
			break;
		case 3:
			EnemyStats = new Stats (15,20);
			EnemyWeapon = new WeaponList("Weird Club", 6, 1, 0);
			break;
		case 4:
			EnemyStats = new Stats (20,20);
			EnemyWeapon = new WeaponList("Sharp Claws", 10, 1, 0);
			break;
		case 5:
			EnemyStats = new Stats (30,50);
			EnemyWeapon = new WeaponList("Green Lazers", 7, 2, 5);
			break;
		case 6:
			EnemyStats = new Stats (15,15);
			EnemyWeapon = new WeaponList("Wat Beam", 13, 5, 7);
			break;
		case 9:
			EnemyStats = new Stats (90,90);
			EnemyWeapon = new WeaponList("Nothing", 0, 1, 0);
			break;
		default:
			System.out.println("Invalid choice");
			break;
		}
		EnemyStatsRoll = new Stats (EnemyStats.getHealth(),EnemyStats.getArmour());
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void buildStats()
	{
		if(statCounter == 1)
		{
			System.out.println("Status Already Selected");
			sleep();
		}
		else
		{
			int build;
			System.out.println("1 Health: 20 Armour: 20 Ammo Belt: 8");
			System.out.println("2 Health: 30 Armour: 30 Ammo Belt: 6");
			System.out.println("3 Health: 40 Armour: 40 Ammo Belt: 4");
			System.out.print("Enter choice >> ");
			build = keyboard.nextInt();
			switch (build)
			{
			case 1 :
				PlayerStats = new Stats (20,20);
				PlayerBelt = 7;
				break;
			case 2 :
				PlayerStats = new Stats (30,30);
				PlayerBelt = 6;
				break;
			case 3 :
				PlayerStats = new Stats (40,40);
				PlayerBelt = 5;
				break;
			default:
				System.out.println("Only Strings are accepted");
				break;
			}
			PlayerStatsRoll = new Stats (PlayerStats.getHealth(),PlayerStats.getArmour());
			PlayerTempHeal_1 = PlayerStats.getHealth() / 3;
			statCounter++;
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void buildWeapon()
	{
		if(wepCounter == 1)
		{
			System.out.println("Weapon already selected");
			sleep();
		}
		else
		{
			int wep;
			System.out.println("|-Number-|-----Name-----|-Damage-|----Range----|-Rounds-|");
			System.out.println("| 1      | Power-Sword  | 9      | Melee (1)   | 1      |");
			System.out.println("| 1      | Hand-Bolter  | 6      | Close (2)   | 3      |");
			System.out.println("| 2      | Bolter       | 8      | Medium (3)  | 4      |");
			System.out.print("Enter choice >> ");
			wep = keyboard.nextInt();
			switch (wep)
			{
			case 1 :
				Weapon = new WeaponList("Hand-Bolter",6,2,3);
				SecndWeapon = true;
				Weapon_2 = new WeaponList("Power-Sword",9,1,1);
				break;
			case 2 :
				Weapon = new WeaponList("Bolter",8,3,4);
				break;
			default:
				System.out.println("Only Numbers Are Accepted");
				break;
			}
			AmmoRoll = Weapon.getAmmo(); 
			wepCounter ++;
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void PlayerDeath()
	{
		System.out.println("You Died!");
		System.out.println("Total Health Hits: " + PlayerHealthHit);
		System.out.println("Total Armour Hits: " + PlayerArmourHit);
		System.out.println("Total Misses: " + PlayerMiss);
		System.out.println("Score: ["+Score+"]");
		System.exit(0);
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void display_Status()
	{
		System.out.println("=====================");
		System.out.println("Stats-- \n"+PlayerStats);
		System.out.println("Weapon-- \n"+Weapon);
		if(SecndWeapon == true)
		{
			System.out.println("Weapon: "+Weapon_2);
		}
		System.out.println("Total Health Hits: "+PlayerHealthHit);
		System.out.println("Total Armour Hits: "+PlayerArmourHit);
		System.out.println("Total Misses: "+PlayerMiss);
		System.out.println("=====================");
		sleep();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void display_Enemy_Status()
	{
		System.out.println("=====================");
		System.out.println("Enemy Stats-- \n"+EnemyStats);
		System.out.println("Enemy Weapon-- \n"+EnemyWeapon);
		System.out.println("Total Health Hits: "+EnemyHealthHit);
		System.out.println("Total Armour Hits: "+EnemyArmourHit);
		System.out.println("Total Misses: "+EnemyMiss);
		System.out.println("=====================");
		sleep();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void sleep()
	{
		try
		{
			Thread.sleep(3500);
		}
		catch(InterruptedException e)
		{
			System.err.println(e.getMessage());
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
}