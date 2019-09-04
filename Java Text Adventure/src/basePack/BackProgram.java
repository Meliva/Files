package basePack;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class BackProgram extends Screen
{
	protected static int BuildType;// An int which tells builds are being applied to (i.e player or ally)
	protected static Stats BuildStats = new Stats (0,0);//A neutral stat which is chosen by player
	protected static WeaponList BuildWeapon = new WeaponList(null,0,0,0,0);// A neutral weapon which is chosen by player
	protected static int result = 0;//Used to print if the player dies or completes the missions

	protected static Dice AccRolls = new Dice();//The dice for accuracy 
	protected static Dice ArmRolls = new Dice();//The dice for armour\armor saving
	protected static Dice CrtRolls = new Dice();//The dice for critical hits
	protected static Dice TrnRolls = new Dice();//The dice for deciding who goes first
	protected static Dice T1_Target = new Dice();//The dice for which person is targeted
	protected static Dice T2_Target = new Dice();//Same as one above, to avoid overriding of targeting wrong person
	protected static WeaponList PlayerWeapon = new WeaponList(null,0,0,0,0);//Player weapon which is applied by build weapon
	protected static WeaponList PlayerRollWeapon = new WeaponList(null,0,0,0,0);//Player roll back weapon used to reset the stats
	protected static Stats PlayerStats = new Stats (0,0);//Player stat which is applied by build stat
	protected static Stats PlayerStatsRoll = new Stats (0,0);//Player stat roll back needed for when healing option is selected 
	protected static int AmmoRoll = 0;// Ammo roll for player weapon to reset value once the reload function is executed 
	protected static String PlayerName = null;//Player name
	protected static Stats AllyStat = new Stats (0,0);// A neutral stat which is applied by build stat
	protected static Stats AllyStatRoll = new Stats (0,0);// A roll back to heal the first ally to full values
	protected static WeaponList AllyWeapon = new WeaponList(null,0,0,0,0); // A Ally weapon  which is applied by build weapon
	protected static int AllyAmmoRoll = 0; // Ammo roll for ally weapon for when they reload
	protected static String AllyName = null; // Ally name
	protected static Stats AllyStat_2 = new Stats (0,0);// Second ally stat applied by build stat
	protected static Stats AllyStat_2Roll = new Stats (0,0);// Roll back for healing of second ally
	protected static WeaponList Ally_2Weapon = new WeaponList(null,0,0,0,0); // Second ally weapon applied by build weapon
	protected static int Ally_2AmmoRoll = 0;//Roll back for ammo
	protected static String Ally_2Name = null;// Second ally name
	protected static int AllyCount = 0;// Counter for the how many allies the player has
	protected static int statCounter = 0;//Used to allow the player to enter stats once
	protected static int AttackType = 0;//Attack type of which person is attacking
	protected static boolean Hit = false;//A boolean of the hit value will turn true if the dice is equal, automatically set to false with each attack
	protected static Stats NeutralStats = new Stats (0,0);//Used in the attack area and used to damage the target's stats of the one it has been given
	protected static WeaponList NeutralWeapon = new WeaponList(null,0,0,0,0);//Used to grab the attacker's weapon values
	protected static int NeutralAmmoRoll = 0;//Used in attack area to reload the attackers weapon
	protected static int minDamage = 0;//minDamage value used in attacks
	protected static int maxDamage = 0;//maxDamage value used in attacks
	protected static int attackDamage = 0;//The damage value that is selected
	protected static Stats BuildEnemyStats = new Stats (0,0);//The neutral stats for the enemy
	protected static WeaponList BuildEnemyWeapon = new WeaponList(null,0,0,0,0);//The neutral weapon for the enemy
	protected static WeaponList EnemyWeapon = new WeaponList(null,0,0,0,0);//Weapon for the first enemy
	protected static int EnemyAmmoRoll = 0;//Ammo roll for the first enemy
	protected static Stats EnemyStats = new Stats (0,0);//Stats for the first enemy
	protected static Stats EnemyStatsRoll = new Stats (0,0);//Stats roll back for the first enemy
	protected static int EnemyParty = 0;// Counter for the enemy party (will have the stats and weapons given but will only be active if you have allies[goes on a 1:1 scale])
	protected static WeaponList EnemyUnitWeapon = new WeaponList(null,0,0,0,0);//Weapon for the secound enemy
	protected static int EnemyAmmoUnitRoll = 0;//Ammo roll for secound enemy
	protected static Stats EnemyUnitStats = new Stats (0,0);//Stats for secound enemy
	protected static Stats EnemyUnitStatsRoll = new Stats (0,0);//Stats roll back for secound enemy
	protected static WeaponList EnemyUnit_2Weapon = new WeaponList(null,0,0,0,0);//Weapon for third enemy
	protected static int EnemyAmmoUnit_2Roll = 0;//Ammo roll back for third enemy
	protected static Stats EnemyUnit_2Stats = new Stats (0,0);//Stats for third enemy
	protected static Stats EnemyUnit_2StatsRoll = new Stats (0,0);//Stats roll back for third enemy
	protected static WeaponList EnemyWeaponBoss = new WeaponList(null,0,0,0,0);//Weapon for enemy
	protected static int EnemyAmmoBoss = 0;//Ammo roll back for boss
	protected static Stats EnemyBoss = new Stats (0,0);//Stats for boss
	protected static Stats EnemyBossRoll = new Stats (0,0);//Stats roll back for boss
	protected static String RollLine;//Used to a be applied to the screen for a death line of ally or enemy
	protected static int Grabber;//Int applied to a random number (from class) and used to pull a text block of a line
	protected static int randFlag;//Used for if the player decides to randomize the enemies or not
	protected static int enemyCounter = 0;//The counter for how many enemies have been made by the player
	protected static int kills = 0;//Keeps score of kills
	protected static int Score = 0;//The score for the player which can increase
	protected static int Random = 0;//The int value which the value of what the enemy will be
	protected static int bossIntAll = 0;//Used for value of if boss is allowed
	protected static int bossMinAttack = 0;//Used to boost min attack of boss
	protected static int bossMaxAttack = 0;//Used to boost max attack of boss
	protected static int bossHP = 0;//Used to boost HP of boss
	protected static int bossArmour = 0;//Used to boost Armour of boss
	protected static int bossCounter = 0;//Used to keep track of what is boosted for the boss
	protected static int baseX = 0;//The bottom of X value will stay at 0
	protected static int baseY = 0;//The bottom of Y value will stay at 0
	protected static int customX = 0;//Will be the maximum of the X value
	protected static int customY = 0;//Will be the maximum of the Y value
	protected static int PlayerXLocation;//The X location for the player (and allies)
	protected static int PlayerYLocation;//The Y location for the player (and allies)
	protected static int EnemyXLocation;//The X location for Enemies (as well as other enemies)
	protected static int EnemyYLocation;//The Y location for Enemies (as well as other enemies)
	protected static Dice enemyMove = new Dice();//Dice function for which direction the enemy will move in
	protected static Cover MakeCover = new Cover (0, 0, 0);//Cover object Ignored by melee
	protected static Cover MakeCover1 = new Cover (0, 0, 0);//Bonus cover
	protected static Cover MakeCover2 = new Cover (0, 0, 0);//Bonus cover
	protected static Cover MakeCover3 = new Cover (0, 0, 0);//Bonus cover
	protected static Crate MakeCrate = new Crate (0, 0, 0, 0);//Crate that affects the player only and can only be picked up by the player
	protected static Crate MakeCrate1 = new Crate (0, 0, 0, 0);//Bonus Crate
	protected static Crate MakeCrate2 = new Crate (0, 0, 0, 0);//Bonus Crate
	protected static Crate MakeCrate3 = new Crate (0, 0, 0, 0);//Bonus Crate
	protected static int itemCounter = 0;//Used for how many items are put in a map
	protected static int coverType = 0;//used for the enemy if they have cover or not
	protected static int turn = 0;//Counter for how many turns have been going
	protected static int loop = 0;//Used for the generating map (cover and location)
	protected static int theAttacker = 0;//Used to switch the attacker
	protected static int count = 0;//Used to count the turns
	protected static int playerCount = 0;//Used when attacking to safely ensure all attackers get a turn
	protected static int enemyCount = 0;//Same function as one above
	protected static boolean coverCheck = false;//Boolean for cover
	protected static int deathType = 0;//If an ally died or an enemy 
	protected static int mapCounter = 0;//Used to count the map to hide functions
	protected static int combatTimer = 0;//Timer used for when movement is done, combat starts when it reaches 0
	protected static int attackLine = 0;//Used to get an attack line

	public static void start()
	{
		TF.clear();
		TABase.clear();
		String S1 = "Status";
		String S2 = "Begin";
		String S3 = "Finish";
		String S4 = "Healing";
		String S5 = "Barracks";
		String S6 = "Options";
		TABase.appendText("\nMenu ");
		TABase.appendText("\n\t("+S1+") Pick Health, Armour & Weapon");
		TABase.appendText("\n\t("+S2+") Begin Mission");
		TABase.appendText("\n\t("+S3+") Finish Mission");
		TABase.appendText("\n\t("+S4+") Apothecary");
		TABase.appendText("\n\t("+S5+") Barracks");
		TABase.appendText("\n\t("+S6+") Options");
		TF.clear();
		proceedB.setOnAction((e) ->
		{
			if(TF.getText().equalsIgnoreCase(S1))
			{
				buildPlayer();
			}
			else if(TF.getText().equalsIgnoreCase(S2))
			{
				buildMissions();
			}
			else if(TF.getText().equalsIgnoreCase(S3))
			{
				finishMissions();
			}
			else if(TF.getText().equalsIgnoreCase(S4))
			{
				healing();
			}
			else if(TF.getText().equalsIgnoreCase(S5))
			{
				barracks();
			}
			else if(TF.getText().equalsIgnoreCase(S6))
			{
				setOptions();
			}
			else
			{
				Alert update = new Alert(Alert.AlertType.WARNING);
				update.setHeaderText("Invalid Entry");
				update.setContentText("Value entered is null or not equal to values one screen");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}
		});
		setOptions();
	}
	public static void buildType()
	{
		if(BuildType == 1)
		{
			PlayerStats = BuildStats;
			PlayerWeapon = BuildWeapon;
			PlayerRollWeapon = PlayerWeapon;
			PlayerStatsRoll = new Stats (PlayerStats.getHealth(),PlayerStats.getArmour());
			AmmoRoll = PlayerWeapon.getAmmo(); 
			statCounter++;
		}
		else if(BuildType == 2)
		{
			AllyStat = BuildStats;
			AllyStatRoll = new Stats (AllyStat.getHealth(),AllyStat.getArmour());
			AllyWeapon = BuildWeapon;
			AllyAmmoRoll = AllyWeapon.getAmmo();
		}
		else if(BuildType == 3)
		{
			AllyStat_2 = BuildStats;
			AllyStat_2Roll = AllyStat_2;
			Ally_2Weapon = BuildWeapon;
			Ally_2AmmoRoll = Ally_2Weapon.getAmmo();
		}
		start();
	}

	public static void healing()
	{
		TF.clear();
		PlayerStats.HealHp(PlayerStatsRoll.getHealth());
		PlayerStats.HealArmour(PlayerStatsRoll.getArmour());
		AllyStat.HealHp(AllyStatRoll.getHealth());
		AllyStat.HealArmour(AllyStatRoll.getArmour());
		AllyStat_2.HealHp(AllyStat_2Roll.getHealth());
		AllyStat_2.HealArmour(AllyStat_2Roll.getArmour());
	}
	
	public static void finishMissions() //When player has finished a set amount of missions
	{
		TF.clear();
		TABase.clear();
		TABase.appendText("You can finish the campain at any time, just type end. \nYou will get a score of: ["+Score+"] if you exit.");
		TABase.appendText("\nTyping back will send you back to the menu");
		proceedB.setOnAction((e) ->
		{
			if(TF.getText().equalsIgnoreCase("end"))
			{
				try 
				{
					result = 1;
					print();		
				} 
				catch (FileNotFoundException o) 
				{
					TABase.appendText("\nFile not found");
				}
			}
			else if(TF.getText().equalsIgnoreCase("back"))
			{
				start();
			}
			else
			{
				Alert update = new Alert(Alert.AlertType.WARNING);
				update.setHeaderText("Invalid Entry n");
				update.setContentText("Value entered is null or not equal to values shown on the screen");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}
		});
	}

	public static void buildMissions()
	{
		if (enemyCounter == 0 || PlayerStats == null)
		{
			TABase.appendText("\nEnsure Player is given Stats and the enemy type is decided:");
		}
		else
		{
			clearB.setDisable(false);
			TALocation.setVisible(true);
			TALog.setVisible(true);
			TABase.appendText("\nEnemy Sigheted");
			PrimtiveStike();
			moveMissions();
		}
	}

	public static void moveMissions()
	{
		if(AllyCount == 2)
		{
			EnemyParty = 2;
		}
		else if(AllyCount == 1)
		{
			EnemyParty = 1;
		}

		if(combatTimer != 0)
		{
			Enemymove();
			Move();	
			combatTimer--;
			locationShow();
		}
		else
		{
			TABase.clear();
			attackMission();	
		}
	}

	public static void attackMission()
	{
		TF.clear();
		TABase.clear();
		if(enemyCounter == 0)
		{
			TABase.appendText("\nAll Enmies Killed");
			coverType = 0;
			Score = Score + 200;
			turn = 0;
			TABase.clear();
			PlayerWeapon = PlayerRollWeapon;
			start();
		}
		else if (enemyCounter != 0)
		{
			TABase.appendText("-------------------------------------------------------------");
			TABase.appendText("\nEnimes: "+enemyCounter+"\nAllies: "+AllyCount);
			TABase.appendText("\nType 'Combat' to Attack \nType 'Stats' for Status\nType 'Heal' to heal a small amount");
			proceedB.setOnAction((e) ->
			{
				if(TF.getText().equalsIgnoreCase("Combat"))
				{
					combatCombo();
				}
				else if(TF.getText().equalsIgnoreCase("Stats"))
				{
					displayStatus();
				}
				else if(TF.getText().equalsIgnoreCase("Heal"))
				{
					tempHeal();
				}
				else
				{
					Alert update = new Alert(Alert.AlertType.WARNING);
					update.setHeaderText("Invalid Entry b");
					update.setContentText("Value entered is null or the text entered was not Combat, Stats or Heal");
					Optional<ButtonType> result =update.showAndWait();
					if(result.get() == ButtonType.OK)
					{
						TF.clear();
					}
				}
			});
		}
	}

	public static int PrimtiveStike()
	{
		if(TrnRolls.Turn() == 1 || TrnRolls.Turn() == 2)
		{
			theAttacker = 1;
			TABase.appendText("\nYou Get The Upper Hand!");
		}
		else
		{
			theAttacker = 2;
			TABase.appendText("\nThe Enemy Gets The Upper Hand!");				
		}
		return theAttacker;
	}

	public static void combatCombo()
	{
		if(count == 2)
		{
			count = 0;
			TF.clear();
			attackMission();
		}
		else
		{
			if(theAttacker == 1)
			{
				turn = turn +1;
				count++;
				PlayerAttack();

			}
			else if(theAttacker == 2)
			{
				turn = turn +1;
				count++;
				EnemyAttack();
			}
		}
	}

	public static void PlayerAttack()
	{
		if(coverType == 1)
		{
			coverCheck = true;
		}
		else
		{
			coverCheck = false;
		}
		theAttacker = 2;
		if(playerCount == 3)
		{
			playerCount = 0;
			combatCombo();
		}
		else
		{
			if(playerCount == 0)
			{
				AttackType = 1;
				turn = turn +1;
				if(AllyCount == 0)
				{
					playerCount = 3;
				}
				else if(AllyCount == 1 || AllyCount == 2)
				{
					playerCount++;
				}
				Attack();
			}
			else if(playerCount == 1)
			{
				AttackType = 2;
				turn = turn +1;
				if(AllyCount == 1)
				{
					playerCount = 3;
				}
				else if (AllyCount == 2)
				{
					playerCount++;
				}
				Attack();
			}
			else if(playerCount == 2)
			{
				AttackType = 3;
				turn = turn +1;
				playerCount++;
				Attack();
			}
		}		
	}

	public static void EnemyAttack()
	{
		attackLine = 1;
		LineGrabber();
		attackLine = 0;
		if(coverType == 2)
		{
			coverCheck = true;
		}
		else
		{
			coverCheck = false;
		}
		theAttacker = 1;
		if(enemyCount == 3)
		{
			enemyCount = 0;
			combatCombo();
		}
		else
		{
			if(enemyCount == 0)
			{
				AttackType = 4;
				turn = turn +1;
				if(EnemyParty == 0)
				{
					enemyCount = 3;
				}
				else if(EnemyParty == 1 || EnemyParty == 2)
				{
					enemyCount++;
				}
				Attack();
			}
			else if(enemyCount == 1)
			{
				AttackType = 5;
				turn = turn +1;
				if(EnemyParty == 1)
				{
					enemyCount = 3;
				}
				else if(EnemyParty == 2)
				{
					enemyCount++;
				}
				Attack();
			}
			else if(enemyCount == 2)
			{
				AttackType = 6;
				turn = turn +1;
				enemyCount++;
				Attack();
			}
		}	
	}

	public static void Attack()//All Neutral values are overridden by different attacking people
	{
		minDamage = 0;
		maxDamage = 0;
		attackDamage = 0;
		if(AttackType == 1 || AttackType == 2 || AttackType == 3)
		{
			int T1 = T1_Target.Target();
			if(AttackType == 1)
			{
				TALog.appendText("\nPlayer Turn:");
				minDamage = PlayerWeapon.getminDamage();
				maxDamage = PlayerWeapon.getmaxDamage();
				NeutralWeapon = PlayerWeapon;
				NeutralAmmoRoll = AmmoRoll;
			}
			else if(AttackType == 2)
			{
				TALog.appendText("\nAlly Turn:");
				minDamage = AllyWeapon.getminDamage();
				maxDamage = AllyWeapon.getmaxDamage();
				NeutralWeapon = AllyWeapon;
				NeutralAmmoRoll =  AllyAmmoRoll;
			}
			else if(AttackType == 3)
			{
				TALog.appendText("\nAlly Turn:");
				minDamage = Ally_2Weapon.getminDamage();
				maxDamage = Ally_2Weapon.getminDamage();
				NeutralWeapon = Ally_2Weapon;
				NeutralAmmoRoll = Ally_2AmmoRoll;
			}
			if(EnemyParty == 2)
			{
				if(T1 == 3 || T1== 6)
				{
					NeutralStats = EnemyUnitStats;
					TALog.appendText("\nTargeting Enemy #2");
				}
				else if(T1 == 2 || T1 == 5)
				{
					NeutralStats = EnemyUnit_2Stats;
					TALog.appendText("\nTargeting Enemy #3");
				}
				else if(T1 == 1 ||  T1 == 4)
				{
					NeutralStats = EnemyStats;
					TALog.appendText("\nTargeting Enemy #1");
				}
			}
			else if(EnemyParty == 1)
			{
				if(T1 == 3 || T1 == 6 || T1 == 2)
				{
					NeutralStats = EnemyUnitStats;
					TALog.appendText("\nTargeting Enemy #2");
				}
				else if(T1 == 1 ||  T1 == 4 || T1 == 5)
				{
					NeutralStats = EnemyStats;
					TALog.appendText("\nTargeting Enemy #1");
				}
			}
			else if(EnemyParty == 0)
			{
				NeutralStats = EnemyStats;
				TALog.appendText("\nTargeting Enemy");
			}		
		}
		if(AttackType == 4 || AttackType == 5 || AttackType == 6)
		{	
			int T2 = T2_Target.Target();
			if(AttackType == 4)
			{
				TALog.appendText("\nEnemy #1 Turn");
				minDamage = EnemyWeapon.getminDamage();
				maxDamage = EnemyWeapon.getminDamage();
				NeutralWeapon = EnemyWeapon;
				NeutralAmmoRoll = EnemyAmmoRoll;	
			}
			else if(AttackType == 5)
			{
				TALog.appendText("\nEnemy #2 Turn");
				minDamage = EnemyUnitWeapon.getminDamage();
				maxDamage = EnemyUnitWeapon.getmaxDamage();
				NeutralWeapon = EnemyUnitWeapon;
				NeutralAmmoRoll = EnemyAmmoUnitRoll;
			}
			else if(AttackType == 6)
			{
				TALog.appendText("\nEnemy #3 Turn");
				minDamage = EnemyUnit_2Weapon.getminDamage();
				maxDamage = EnemyUnit_2Weapon.getmaxDamage();
				NeutralWeapon = EnemyUnit_2Weapon;
				NeutralAmmoRoll = EnemyAmmoUnit_2Roll;	
			}
			if(AllyCount == 2)
			{
				if(T2 == 3 || T2 == 6)
				{
					NeutralStats = AllyStat;
					TALog.appendText("\nTargeting "+AllyName);
				}
				if(T2 == 2 ||T2 == 5)
				{
					NeutralStats = AllyStat_2;
					TALog.appendText("\nTargeting "+Ally_2Name);
				}
				if(T2 == 1 ||  T2 == 4)
				{
					NeutralStats = PlayerStats;
					TALog.appendText("\nTargeting "+PlayerName);
				}
			}
			if(AllyCount == 1)
			{
				if(T2 == 3 || T2 == 6 || T2 == 2)
				{
					NeutralStats = AllyStat;
					TALog.appendText("\nTargeting "+AllyName);
				}
				if(T2 == 1 ||  T2 == 4 || T2 == 5)
				{
					NeutralStats = PlayerStats;
					TALog.appendText("\nTargeting "+PlayerName);
				}
			}
			if(AllyCount == 0)
			{
				NeutralStats = PlayerStats;
				TALog.appendText("\nTargeting "+PlayerName);
			}
		}
		//Attack begins with neutral weapon
		if(NeutralWeapon.getAmmo() == 0 && NeutralWeapon.getRange() != 1)
		{
			TALog.appendText("\nRealoading...");
			NeutralWeapon.Reload(NeutralAmmoRoll);
		}
		else
		{
			if(NeutralWeapon.getRange() == 1) 
			{
				if(AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 9)
				{
					Hit = false;
				}
				else
				{
					Hit = true;
				}
			}
			else if(NeutralWeapon.getRange() > 1)
			{
				if(coverCheck == true && MakeCover.getCoverHealth() != 0 && AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 6)
				{
					Hit = false;
					MakeCover.DamageCover();
				} 
				else if(coverCheck == false && AccRolls.Accuracy() == 2 || AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 6 || AccRolls.Accuracy() == 8)
				{
					Hit = false;
				}
				else
				{
					Hit = true;
				}
			}
			if(Hit == false)
			{
				TALog.appendText("\nAttack missed!");
			}
			else if(Hit == true)
			{
				Random ramDam = new Random();
				attackDamage = ramDam.nextInt((maxDamage-minDamage+1))+minDamage;//Generate a damage value between the min and max value

				if(NeutralWeapon.getRange() > 1 && CrtRolls.Crit() == 11 || CrtRolls.Crit() == 12)
				{
					attackDamage = attackDamage * 2;
					TALog.appendText("A Critical Hit!");
				}
				if(ArmRolls.Save() == 1 || ArmRolls.Save() == 3 || ArmRolls.Save() == 5)
				{
					if(NeutralStats.getArmour() <= 0)//Used if hit was armour save but no armour exists
					{
						TALog.appendText("\nHealth Damage: "+attackDamage);
						NeutralStats.damageHealth(attackDamage);
					}
					else
					{
						TALog.appendText("\nArmour Damage: "+attackDamage);
						NeutralStats.damageArmour(attackDamage);
					}
				}
				else
				{
					TALog.appendText("\nHealth Damage: "+attackDamage);
					NeutralStats.damageHealth(attackDamage);
				}

			}
			NeutralWeapon.Fire();//Fires Weapon
		}
		Checker();//Checks If Anything has died
		Hit = false;//Rests Hit Boolean
		if(AttackType == 1||AttackType == 2||AttackType == 3)
		{
			PlayerAttack();
		}
		else if(AttackType == 4||AttackType == 5||AttackType == 6)
		{
			EnemyAttack();
		}
	}

	public static void Checker()
	{
		if(PlayerStats.getHealth() <= 0)
		{
			PlayerDeath();
		}
		if(AllyCount <= 0 && AllyCount != 0)
		{
			if(AllyStat.getHealth() <=0 || AllyStat_2.getHealth() <=0)
			{
				deathType = 1;
				AllyDeath();
			}
		}
		if(EnemyStats.getHealth() <= 0 || EnemyUnitStats.getHealth() <= 0 || EnemyUnit_2Stats.getHealth() <=0)
		{
			deathType = 2;
			EnemyKilled();
		}
	}

	public static void tempHeal()
	{
		int quadhp = PlayerStatsRoll.getHealth() /4;
		int quadhp1 = AllyStatRoll.getHealth() /4;
		int quadhp2 = AllyStat_2Roll.getHealth() /4;
		PlayerStats.HealHp(quadhp);
		AllyStat.HealHp(quadhp1);
		AllyStat_2.HealHp(quadhp2);
	}

	public static void barracks()
	{
		TF.clear();
		if(AllyCount == 2)
		{
			TABase.appendText("\nMax Ally Count Reached");
		}
		else if (AllyCount == 1) //Add ally count 
		{
			TABase.appendText("\n-------------------------------------------------------------");
			if(Score == 300)
			{
				BuildType = 3;
				TABase.appendText("\nEnter Ally Name >> ");
				proceedB.setOnAction((e) ->
				{
					Ally_2Name = TF.getText();
					statBuilder();
				});
			}
			else
			{
				TABase.appendText("\nYou need a score of 300 to get your first ally");
			}
		}
		else
		{
			TABase.appendText("\n-------------------------------------------------------------");
			if(Score == 150)
			{
				BuildType = 2;
				TABase.appendText("\nEnter Ally Name >> ");
				proceedB.setOnAction((e) ->
				{
					AllyName = TF.getText();
					statBuilder();
				});
			}
			else
			{
				TABase.appendText("\nYou need a score of 150 to get your first ally");
			}
		}
	}

	public static void setOptions()
	{
		TF.clear();
		TABase.clear();
		TABase.appendText("Set Enemy Count >> ");
		proceedB.setOnAction((e) ->
		{
			String hap = TF.getText();
			try
			{
				enemyCounter = Integer.parseInt(hap);
				if(enemyCounter <= 0)
				{
					TF.clear();
				}
				else
				{
					randomEnemy();
				}
			}
			catch(NumberFormatException ex)
			{
				Alert update = new Alert(Alert.AlertType.WARNING);
				update.setHeaderText("Invalid Entry c");
				update.setContentText("Value entered is null or not equal to values one screen");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}
		});		
	}

	public static void randomEnemy()
	{
		TF.clear();
		TABase.clear();
		TABase.appendText("Randomize enemy? yes/no >> ");
		proceedB.setOnAction((e) ->
		{
			if(TF.getText().equalsIgnoreCase("yes"))
			{
				randFlag = 1;
				EnemyMaker();
			}
			else if(TF.getText().equalsIgnoreCase("no"))
			{
				randFlag = 0;
				EnemyMaker();
			}
			else
			{
				Alert update = new Alert(Alert.AlertType.WARNING);
				update.setHeaderText("Invalid Entry d");
				update.setContentText("Only Value accepted is yes or no");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}
		});
	}

	public static void EnemyMaker()
	{
		TF.clear();
		if(randFlag == 1)
		{
			Random RamEnemy = new Random();
			Random = RamEnemy.nextInt(7)+1;
			TABase.clear();
			bossMaker();
		}
		else if(randFlag == 0)
		{
			TABase.appendText("-------------------------------------------------------------");
			TABase.appendText("\n|-Number-|-Name-|-Health-|-Armour-|-Damage-|-Range-|-Rounds-|");
			TABase.appendText("\nEnemy List:");
			TABase.appendText("\n| 1 | Traitors | 40 | 40 | 4-7 | Ranged (2) | 7 |");
			TABase.appendText("\n| 2 | Pointy Ears | 20 | 15 | 5-8  | Ranged (2) | 5 |");
			TABase.appendText("\n| 3 | Green Ones* | 15 | 5 | 3-6 | Melee (1) | - |");
			TABase.appendText("\n| 4 | Hungry Ones | 20 | 20 | 7-10 | Melee (1) | - |");
			TABase.appendText("\n| 5 | Metal Skeletons | 30 | 50 | 4-7 | Ranged (2) | 6 |");
			TABase.appendText("\n| 6 | Blue Shooters | 15 | 15 | 11-14 | Ranged (2) | 10 ");
			TABase.appendText("\n| 7 | Fleshy Ones* | 20 | 0 | 11-14 | Ranged (2) | 10 ");;
			TABase.appendText("\n *-No boss but double enemy Amount");
			proceedB.setOnAction((e) ->
			{
				if(TF.getText().equals("1") || TF.getText().equals("2") || TF.getText().equals("3") || TF.getText().equals("4") || TF.getText().equals("5") || TF.getText().equals("6") || TF.getText().equals("7"))
				{
					String leop = TF.getText();
					Random = Integer.parseInt(leop);
					TABase.clear();
					bossMaker();
				}
				else
				{
					Alert update = new Alert(Alert.AlertType.WARNING);
					update.setHeaderText("Invalid Entry e");
					update.setContentText("Value entered is null or not equal to values one screen");
					Optional<ButtonType> result =update.showAndWait();
					if(result.get() == ButtonType.OK)
					{
						TF.clear();
					}
				}
			});
		}
	}

	public static void bossMaker()
	{
		TF.clear();
		if(enemyCounter == 1 || Random == 7 || Random == 3)
		{
			moop();
		}

		if(bossCounter==0)
		{
			TABase.appendText("Do You want a boss to be made?");
			TABase.appendText("\nYes or No");
		}
		else if(bossCounter==1)
		{
			TABase.appendText("\nBoss HP bonus->");
		}
		else if(bossCounter==2)
		{
			TABase.appendText("\nBoss Armour bonus->");
		}
		else if(bossCounter==3)
		{
			TABase.appendText("\nBoss Damage bonus->");
		}
		if(bossCounter <= 4)
		{
			proceedB.setOnAction((o)->
			{
				if(bossCounter == 0 && TF.getText().equalsIgnoreCase("No"))
				{
					bossIntAll = 0;
					bossMinAttack = 0;
					bossMaxAttack = 0;
					bossHP = 0;
					bossArmour = 0;
					moop();
				}
				else if(bossCounter == 0 && TF.getText().equalsIgnoreCase("Yes"))
				{
					bossIntAll = 1;
					bossCounter ++;
					bossMaker();
				}
				else if(bossCounter == 1 && !TF.getText().contains("-"))
				{		
					String hp = TF.getText();
					try
					{
						bossHP = Integer.parseInt(hp);
						bossMaker();
						bossCounter ++;
					}
					catch(NumberFormatException ex)
					{
						Alert update = new Alert(Alert.AlertType.WARNING);
						update.setHeaderText("Invalid Entry BossHP");
						update.setContentText("Value entered is null or not equal to values one screen");
						Optional<ButtonType> result =update.showAndWait();
						if(result.get() == ButtonType.OK)
						{
							TF.clear();
						}
					}
				}
				else if(bossCounter == 2 && !TF.getText().contains("-"))
				{
					String arm = TF.getText();
					try
					{
						bossArmour = Integer.parseInt(arm);
						bossCounter ++;
						bossMaker();
					}
					catch(NumberFormatException ex)
					{
						Alert update = new Alert(Alert.AlertType.WARNING);
						update.setHeaderText("Invalid Entry BossArmour");
						update.setContentText("Value entered is null or not equal to values one screen or negative value was added");
						Optional<ButtonType> result =update.showAndWait();
						if(result.get() == ButtonType.OK)
						{
							TF.clear();
						}
					}
				}
				else if(bossCounter == 3 && !TF.getText().contains("-"))
				{
					String dam = TF.getText();
					try
					{
						bossMinAttack = Integer.parseInt(dam);
						moop();
					}
					catch(NumberFormatException ex)
					{
						Alert update = new Alert(Alert.AlertType.WARNING);
						update.setHeaderText("Invalid Entry BossDamage");
						update.setContentText("Value entered is null or not equal to values one screen");
						Optional<ButtonType> result =update.showAndWait();
						if(result.get() == ButtonType.OK)
						{
							TF.clear();
						}
					}
				}
				else
				{
					Alert update = new Alert(Alert.AlertType.WARNING);
					update.setHeaderText("Invalid Entry Boss Yes or No");
					update.setContentText("Value entered is null or not equal to values one screen");
					Optional<ButtonType> result =update.showAndWait();
					if(result.get() == ButtonType.OK)
					{
						TF.clear();
					}
				}
			});
		}
	}

	public static void moop()
	{
		bossCounter = 0;
		if(Random == 1)
		{
			BuildEnemyStats = new Stats(40,40);
			BuildEnemyWeapon = new WeaponList("Evil Bolter",4,7,2,7);
		}
		else if(Random == 2)
		{
			BuildEnemyStats = new Stats(20,15);
			BuildEnemyWeapon = new WeaponList("Thin Gun",5,8,2,5);
		}
		else if(Random == 3)
		{
			BuildEnemyStats = new Stats(15,5);
			BuildEnemyWeapon = new WeaponList("Weird Club",3,6,1,0);
			enemyCounter = enemyCounter*2;
		}
		else if(Random == 4)
		{
			BuildEnemyStats = new Stats(20,20);
			BuildEnemyWeapon = new WeaponList("Sharp Claws",7,10,1,0);
		}
		else if(Random == 5)
		{
			BuildEnemyStats = new Stats(30,50);
			BuildEnemyWeapon = new WeaponList("Green Lazers",4,7,2,6);
		}
		else if(Random == 6)
		{
			BuildEnemyStats = new Stats(15,15);
			BuildEnemyWeapon = new WeaponList("Wat Beam",11,14,2,10);
		}
		else if(Random == 7)
		{
			BuildStats = new Stats(20,0);
			BuildEnemyWeapon = new WeaponList("Fleshy Punch",2,5,1,0);
			enemyCounter = enemyCounter*2;
		}
		//Builds First Enemy
		EnemyStats = BuildEnemyStats;
		EnemyStatsRoll = new Stats (EnemyStats.getHealth(),EnemyStats.getArmour());
		EnemyWeapon = BuildEnemyWeapon;
		EnemyAmmoRoll = EnemyWeapon.getAmmo();
		//Builds Second Enemy
		EnemyUnitStats = BuildEnemyStats;
		EnemyUnitStatsRoll = new Stats (EnemyUnitStats.getHealth(),EnemyUnitStats.getArmour());
		EnemyUnitWeapon = BuildEnemyWeapon;
		EnemyAmmoUnitRoll = EnemyUnitWeapon.getAmmo();
		//Builds Third Enemy
		EnemyUnit_2Stats = BuildEnemyStats;
		EnemyUnit_2StatsRoll = new Stats (EnemyUnit_2Stats.getHealth(),EnemyUnit_2Stats.getArmour());
		EnemyUnit_2Weapon = BuildEnemyWeapon;
		EnemyAmmoUnit_2Roll = EnemyUnit_2Weapon.getAmmo();
		//Builds The Boss
		if(bossIntAll == 1)
		{
			EnemyWeaponBoss = BuildEnemyWeapon;
			EnemyWeaponBoss.addminDamage(bossMinAttack);
			EnemyWeaponBoss.addmaxDamage(bossMaxAttack);
			EnemyAmmoBoss = EnemyWeaponBoss.getAmmo();
			EnemyBoss = BuildEnemyStats;
			EnemyBoss.addHp(bossHP);
			EnemyBoss.addArmour(bossArmour);
			EnemyBossRoll = new Stats (EnemyBoss.getHealth(),EnemyBoss.getArmour());
		}
		MapMaker();//Makes map
	}

	public static void buildPlayer()
	{
		TF.clear();
		TABase.clear();
		if(statCounter == 1 )
		{
			TABase.appendText("\nStatus Already Selected");
		}
		else
		{
			TABase.appendText("(Numbers not accepted)");
			TABase.appendText("\nEnter Name >> ");
			proceedB.setOnAction((e) ->
			{
				if(!TF.getText().matches("[0-9]+"))//The text does not contain any numbers
				{
					PlayerName = TF.getText();
					BuildType = 1;
					statBuilder();
				}
				else
				{
					Alert update = new Alert(Alert.AlertType.WARNING);
					update.setHeaderText("Invalid Entry k");
					update.setContentText("Value entered is null or has numbers");
					Optional<ButtonType> result =update.showAndWait();
					if(result.get() == ButtonType.OK)
					{
						TF.clear();
					}
				}
			});
		}
	}

	public static void statBuilder()//Build health and armour
	{
		TF.clear();
		TABase.clear();
		TABase.appendText("|-Number-|-Health-|-Armour-|");
		TABase.appendText("\n| 1 | 20 | 20 |");
		TABase.appendText("\n| 2 | 25 | 25 |");
		TABase.appendText("\n| 3 | 30 | 30 |");
		TABase.appendText("\n| 4 | 35 | 35 |");
		TABase.appendText("\n| 5 | 40 | 40 |");
		TABase.appendText("\nEnter first line number >> ");
		proceedB.setOnAction((e) ->
		{
			if(TF.getText().equals("1"))
			{
				BuildStats = new Stats (20,20);
				weaponBuilder();
			}
			else if(TF.getText().equals("2"))
			{
				BuildStats = new Stats (25,25);
				weaponBuilder();
			}
			else if(TF.getText().equals("3"))
			{
				BuildStats = new Stats (30,30);
				weaponBuilder();
			}
			else if(TF.getText().equals("4"))
			{
				BuildStats = new Stats (35,35);
				weaponBuilder();
			}
			else if(TF.getText().equals("5"))
			{
				BuildStats = new Stats (40,40);
				weaponBuilder();
			}
			else 
			{
				Alert update = new Alert(Alert.AlertType.WARNING);
				update.setHeaderText("Invalid Entry l");
				update.setContentText("Value entered is null or the value entered is not 1,2 or 3");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}

		});
	}

	public static void weaponBuilder()//Build weapon
	{

		TABase.clear();
		TF.clear();
		TABase.appendText("|-Name-|-Damage-|-Range-|-Rounds-|");
		TABase.appendText("\n| Power Sword | 5-8 | Melee | 0 |");
		TABase.appendText("\n| Bolter | 2-5 | Ranged | 6 |");
		TABase.appendText("\n| Shotgun | 4-7 | Ranged | 4 |");
		TABase.appendText("\n| Sniper Bolter | 9-12 | Ranged | 1 |");
		TABase.appendText("\n| Power Hammer | 6-9 | Melee | 0 |");
		TABase.appendText("\nEnter name of the weapon >> ");
		proceedB.setOnAction((e) ->
		{
			if(TF.getText().equalsIgnoreCase("Power Sword"))
			{
				BuildWeapon = new WeaponList("Power Sword",5,8,1,0);
				buildType();
			}
			else if(TF.getText().equalsIgnoreCase("Bolter"))
			{
				BuildWeapon = new WeaponList("Bolter",2,5,2,6);
				buildType();
			}
			else if(TF.getText().equalsIgnoreCase("Shotgun"))
			{
				BuildWeapon = new WeaponList("Shotgun",4,7,2,4);
				buildType();
			}
			else if(TF.getText().equalsIgnoreCase("Sniper Bolter"))
			{
				BuildWeapon = new WeaponList("Sniper Bolter",9,12,2,1);
				buildType();
			}
			else if(TF.getText().equalsIgnoreCase("Power Hammer"))
			{
				BuildWeapon = new WeaponList("Power Hammer",6,9,1,0);
				buildType();
			}
			else
			{
				Alert update = new Alert(Alert.AlertType.WARNING);
				update.setHeaderText("Invalid Entry m");
				update.setContentText("Value entered is null or not equal to values shown on the screen");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}
		});
	}

	public static void PlayerDeath()//When Play dies this class is activated
	{
		TABase.appendText("\nYou Died!");
		result = 3;
		try 
		{
			print();		
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
		}
	}

	public static void AllyDeath()	//When AlLy dies, checks which ally has 0 or less health and resets the stats and reduces ally count
	{
		if(AllyStat.getHealth() <=0)
		{
			TABase.appendText("/n"+AllyName+" Has Fallen!");
			LineGrabber();
			AllyName = null;
			AllyStat = new Stats (0,0);
			AllyWeapon = new WeaponList(null,0,0,0,0);
			AllyCount = AllyCount - 1;
			switcher();
		}
		else if (AllyStat_2.getHealth() <=0 && AllyCount == 2)
		{
			TABase.appendText("/n"+Ally_2Name+" Has Fallen!");
			LineGrabber();
			Ally_2Name = null;
			AllyStat_2 = new Stats (0,0);
			Ally_2Weapon = new WeaponList(null,0,0,0,0);
			AllyCount = AllyCount - 1;
		}
	}

	public static void switcher()//Switches ally 2 with ally 1 as this class only goes off if the first ally has died and nullify ally 2
	{	
		if(AllyCount == 1 && AllyStat.getHealth() <= 0 && AllyStat_2.getHealth() !=0)
		{
			TABase.appendText("/n"+Ally_2Name+" is moveed to position 1!");
			AllyName = Ally_2Name;
			AllyStat = AllyStat_2;
			AllyWeapon = Ally_2Weapon;
			Ally_2Name = null;
			AllyStat_2 = new Stats (0,0);
			Ally_2Weapon = new WeaponList(null,0,0,0,0);
		}
	}

	public static void LineGrabber()//Grabs a line to be used with a death of any ally/enemy
	{
		Random RamLine = new Random();
		if(deathType == 1)
		{
			Grabber = RamLine.nextInt(9)+1;
			Lines.GrabWord(Grabber);
			RollLine = Lines.getWord();
			TALog.appendText("\n"+RollLine);
		}
		else if(deathType == 2)
		{
			Grabber = RamLine.nextInt(6)+1;
			Lines.GrabAlly(Grabber);
			RollLine = Lines.getWord();
			TALog.appendText("\n"+RollLine);
		}
		else if(attackLine == 1)
		{
			Grabber = RamLine.nextInt(3)+1;
			Lines.combat(Grabber);
			RollLine = Lines.getWord();
			TALog.appendText("\n"+RollLine);
		}
	}

	public static void EnemyKilled()//Checks which enemy has been killed
	{
		if(EnemyStats.getHealth() <= 0)
		{
			TABase.appendText("\nEnemy #1 Killed");
			LineGrabber();
			Score = Score + 50;
			enemyCounter = enemyCounter -1;
			kills ++;
			EnemyStats.HealHp(EnemyStatsRoll.getHealth());
			EnemyStats.HealArmour(EnemyStatsRoll.getArmour());
		}
		else if(EnemyUnitStats.getHealth() <= 0  && (EnemyParty == 1 || EnemyParty == 2))
		{
			TABase.appendText("\nEnemy #2 Killed");
			LineGrabber();
			Score = Score + 50;
			enemyCounter = enemyCounter -1;
			kills ++;
			EnemyUnitStats.HealHp(EnemyUnitStatsRoll.getHealth());
			EnemyUnitStats.HealArmour(EnemyUnitStatsRoll.getArmour());
		}
		else if(EnemyUnit_2Stats.getHealth() <=0 && EnemyParty == 2)
		{
			TABase.appendText("\nEnemy #3 Killed");
			LineGrabber();
			Score = Score + 50;
			enemyCounter = enemyCounter -1;
			kills ++;
			EnemyUnit_2Stats.HealHp(EnemyUnit_2StatsRoll.getArmour());
			EnemyUnit_2Stats.HealArmour(EnemyUnit_2StatsRoll.getArmour());
		}
		if(enemyCounter == 1 && bossIntAll == 1)
		{
			TABase.appendText("The last Enemy has become the boss!");
			EnemyStats = EnemyBoss;
			EnemyWeapon = EnemyWeaponBoss;
			EnemyAmmoRoll = EnemyAmmoBoss;
		}
	}

	public static void displayStatus()
	{
		TALog.appendText("\n====");
		TALog.appendText("\n"+PlayerName);
		TALog.appendText("\n "+PlayerStats);
		TALog.appendText("\n===");
		if(AllyCount == 2)
		{
			TALog.appendText("\n===");
			TALog.appendText("\n"+Ally_2Name);
			TALog.appendText("\n"+AllyStat_2);
			TALog.appendText("\n===");
			TALog.appendText("\n"+AllyName);
			TALog.appendText("\n"+AllyStat);
			TALog.appendText("\n===");
		}
		else if(AllyCount == 1)
		{
			TALog.appendText("\n===");
			TALog.appendText("\n"+AllyName);
			TALog.appendText("\n"+AllyStat);
			TALog.appendText("\n===");
		}
		TALog.appendText("\n===");
		TALog.appendText("\nEnemy Status");
		TALog.appendText("\n"+EnemyStats);
		TALog.appendText("\n===");
		if(EnemyParty == 2)
		{
			TALog.appendText("\n===");
			TALog.appendText("\nEnemy #3 Status");
			TALog.appendText("\n"+EnemyUnit_2Stats);
			TALog.appendText("\n===");
			TALog.appendText("\nEnemy Status");
			TALog.appendText("\n"+EnemyUnitStats);
			TALog.appendText("\n===");
		}
		else if(EnemyParty == 1)
		{
			TALog.appendText("\n===");
			TALog.appendText("\nEnemy #2 Status");
			TALog.appendText("\n"+EnemyUnitStats);
			TALog.appendText("\n===");
		}
	}

	public static void MapMaker()
	{ 
		if(mapCounter == 0)
		{
			TF.clear();
			TABase.clear();
			TABase.appendText("Map size Small: 10x10 \nMap size Medium: 15x15 \nMap size Large 20x20 \nNao size Extra Large 25x25");
			TABase.appendText("\nEnter map size >> ");
			proceedB.setOnAction((e) ->
			{
				if(TF.getText().equalsIgnoreCase("Small"))
				{
					customX = 10;
					customY = 10;
					combatTimer = 5;
					itemCounter = 2;
					mapCounter = 1;
					MapMaker();	
				}
				else if(TF.getText().equalsIgnoreCase("Medium"))
				{
					customX = 15;
					customY = 15;
					combatTimer = 10;
					itemCounter = 4;
					mapCounter = 1;
					MapMaker();	
				}
				else if(TF.getText().equalsIgnoreCase("Large"))
				{
					customX = 20;
					customY = 20;
					combatTimer = 15;
					itemCounter = 6;
					mapCounter = 1;
					MapMaker();	
				}
				else if(TF.getText().equalsIgnoreCase("Extra Large"))
				{
					customX = 25;
					customY = 25;
					combatTimer = 20;	
					itemCounter = 8;
					mapCounter = 1;
					MapMaker();	
				}
				else
				{
					Alert update = new Alert(Alert.AlertType.WARNING);
					update.setHeaderText("Invalid Entry MapMaker");
					update.setContentText("Value entered is null or not equal to values shown on the screen");
					Optional<ButtonType> result =update.showAndWait();
					if(result.get() == ButtonType.OK)
					{
						TF.clear();
					}
					mapCounter = 0;
				}	
			});
		}
		else if(mapCounter == 1)
		{
			//Generates Cover
			if(loop == 0)
			{
				loop = loop + 1;
				ObjectGenerator();
			}
			else
			{
				mapCounter = 0;
				start();		
			}
		}
	}

	public static void ObjectGenerator()
	{
		if(itemCounter >= 2)
		{
			MakeCrate = new Crate (0, 0, 1, 0);
			MakeCrate.CrateXMaker(customX);
			MakeCrate.CrateYMaker(customY);

			MakeCover = new Cover (0, 0, 3);
			MakeCover.CoverXMaker(customX);
			MakeCover.CoverYMaker(customY);
			System.out.println("Hoop1");
			
		}		
		if(itemCounter >= 4)
		{
			MakeCrate1 = new Crate (0, 0, 1, 0);
			MakeCrate1.CrateXMaker(customX);
			MakeCrate1.CrateYMaker(customY);

			MakeCover1 = new Cover (0, 0, 3);
			MakeCover1.CoverXMaker(customX);
			MakeCover1.CoverYMaker(customY);
			System.out.println("Hoop2");
		}	
		if(itemCounter >= 6)
		{
			MakeCrate2 = new Crate (0, 0, 1, 0);
			MakeCrate2.CrateXMaker(customX);
			MakeCrate2.CrateYMaker(customY);

			MakeCover2 = new Cover (0, 0, 3);
			MakeCover2.CoverXMaker(customX);
			MakeCover2.CoverYMaker(customY);
			System.out.println("Hoop3");
		}
		if(itemCounter == 8)
		{
			MakeCrate3 = new Crate (0, 0, 1, 0);
			MakeCrate3.CrateXMaker(customX);
			MakeCrate3.CrateYMaker(customY);

			MakeCover3 = new Cover (0, 0, 3);
			MakeCover3.CoverXMaker(customX);
			MakeCover3.CoverYMaker(customY);
			System.out.println("Hoop4");
		}
		//MapCheck();
	}

	public static void MapCheck()
	{
		System.out.println("Hoop");
		if(EnemyXLocation == baseX || EnemyXLocation == customX || EnemyYLocation == baseY || EnemyYLocation == customY || 
				PlayerXLocation == baseX || PlayerXLocation == customX || PlayerYLocation == baseY || PlayerYLocation == customY)//Checks when the enemy or player spawn are on the edge of the map and they are sorted again if they are
		{
			Randomizer();
		}
		
		if(itemCounter == 2 && (MakeCover.getCoverX() == baseX || MakeCover.getCoverX() == customX || MakeCover.getCoverY() == baseY || MakeCover.getCoverY() == customY || 
				MakeCrate.getCrateX() == baseX || MakeCrate.getCrateX() == customX || MakeCrate.getCrateY() == baseY || MakeCrate.getCrateY() == customY))
		{
			ObjectGenerator();
		}
		else if(itemCounter == 2 && (MakeCrate.getCrateX() == MakeCover.getCoverX() && MakeCrate.getCrateY() == MakeCover.getCoverY()))
		{
			ObjectGenerator();
		}
		else if(itemCounter >= 4 && (MakeCover1.getCoverX() == baseX || MakeCover1.getCoverX() == customX || MakeCover1.getCoverY() == baseY || MakeCover1.getCoverY() == customY || 
				MakeCrate1.getCrateX() == baseX || MakeCrate1.getCrateX() == customX || MakeCrate1.getCrateY() == baseY || MakeCrate1.getCrateY() == customY))
		{
			ObjectGenerator();
		}
		else if(itemCounter == 4 && ((MakeCrate.getCrateX() == MakeCover.getCoverX() && MakeCrate.getCrateY() == MakeCover.getCoverY())||
				(MakeCrate1.getCrateX() == MakeCover1.getCoverX() && MakeCrate1.getCrateY() == MakeCover1.getCoverY())))
		{
			ObjectGenerator();
		}
		else if(itemCounter == 6 && (MakeCover2.getCoverX() == baseX || MakeCover2.getCoverX() == customX || MakeCover2.getCoverY() == baseY || MakeCover2.getCoverY() == customY || 
				MakeCrate2.getCrateX() == baseX || MakeCrate2.getCrateX() == customX || MakeCrate2.getCrateY() == baseY || MakeCrate2.getCrateY() == customY))
		{
			ObjectGenerator();
		}
		else if(itemCounter == 6 && (MakeCrate.getCrateX() == MakeCover.getCoverX() && MakeCrate.getCrateY() == MakeCover.getCoverY())||
				(MakeCrate1.getCrateX() == MakeCover1.getCoverX() && MakeCrate1.getCrateY() == MakeCover1.getCoverY())||
				(MakeCrate2.getCrateX() == MakeCover2.getCoverX() && MakeCrate2.getCrateY() == MakeCover2.getCoverY()))
		{
			ObjectGenerator();
		}
		else if(itemCounter == 8 && (MakeCover3.getCoverX() == baseX || MakeCover3.getCoverX() == customX || MakeCover3.getCoverY() == baseY || MakeCover3.getCoverY() == customY || 
				MakeCrate3.getCrateX() == baseX || MakeCrate3.getCrateX() == customX || MakeCrate3.getCrateY() == baseY || MakeCrate3.getCrateY() == customY))
		{
			ObjectGenerator();
		}
		else if(itemCounter == 8 && (MakeCrate.getCrateX() == MakeCover.getCoverX() && MakeCrate.getCrateY() == MakeCover.getCoverY())||
				(MakeCrate1.getCrateX() == MakeCover1.getCoverX() && MakeCrate1.getCrateY() == MakeCover1.getCoverY())||
				(MakeCrate2.getCrateX() == MakeCover2.getCoverX() && MakeCrate2.getCrateY() == MakeCover2.getCoverY())||
				(MakeCrate3.getCrateX() == MakeCover3.getCoverX() && MakeCrate3.getCrateY() == MakeCover3.getCoverY()))
		{
			ObjectGenerator();
		}
		else
		{
			MapMaker();
		}
	}
	
	public static void Randomizer()//Scrambles both player and the enemy locations
	{
		Random randomY = new Random ();
		Random randomX = new Random ();
		PlayerYLocation = randomY.nextInt(customY) + 1;
		PlayerXLocation = randomX.nextInt(customX) + 1;
		Random eRandomY = new Random ();
		Random eRandomX = new Random ();
		EnemyYLocation = eRandomY.nextInt(customY) + 1;
		EnemyXLocation = eRandomX.nextInt(customX) + 1;
		MapCheck();
	}

	public static void BoundryCheck()
	{
		if(EnemyXLocation == baseX)//Checks when the enemy spawn to move it away from the edges of the map
		{
			EnemyXLocation = EnemyXLocation + 1;
			Enemymove();
		}
		else if(EnemyXLocation == customX)
		{
			EnemyXLocation = EnemyXLocation - 1;
			Enemymove();
		}
		else if(EnemyYLocation == baseY)
		{
			EnemyYLocation = EnemyYLocation + 1;
			Enemymove();
		}
		else if(EnemyYLocation == customY)
		{
			EnemyYLocation = EnemyYLocation - 1;
			Enemymove();
		}

		if(PlayerXLocation == baseX)//Checks if the player reaches the limits of the map they are moved back one 
		{
			TABase.appendText("\nYou cannot go any futher right");
			PlayerXLocation = PlayerXLocation + 1;
			Move();
		}
		else if(PlayerXLocation == customX)
		{
			TABase.appendText("\nYou cannot go any futher left");
			PlayerXLocation = PlayerXLocation - 1;
			Move();
		}
		else if(PlayerYLocation == baseY)
		{
			TABase.appendText("\nYou cannot go any futher down");
			PlayerYLocation = PlayerYLocation + 1;
			Move();
		}
		else if(PlayerYLocation == customY)
		{
			TABase.appendText("\nYou cannot go any futher up");
			PlayerYLocation = PlayerYLocation - 1;
			Move();
		}
	}

	public static void Move()
	{
		TF.clear();
		TABase.clear();
		//Allows the player to move in 4 directions 
		TABase.appendText("-Move up- \n-Move down- \n-Move left- \n-Move right-");
		TABase.appendText("\nEnter movement choice >> ");
		proceedB.setOnAction((e) ->
		{		
			if(TF.getText().equalsIgnoreCase("move up"))
			{
				PlayerYLocation = PlayerYLocation + 1;
				TABase.appendText("\nYou move up");
				BoundryCheck();//Checks if player moves towards edge of map
				moveMissions();//Moves player back to the move mission as it causes a loop
			}
			else if(TF.getText().equalsIgnoreCase("move down"))
			{
				PlayerYLocation = PlayerYLocation - 1;
				TABase.appendText("\nYou move down");
				BoundryCheck();
				moveMissions();
			}
			else if(TF.getText().equalsIgnoreCase("move left"))
			{
				PlayerXLocation = PlayerXLocation + 1;
				TABase.appendText("\nYou move left");
				BoundryCheck();
				moveMissions();
			}
			else if(TF.getText().equalsIgnoreCase("move right"))
			{
				PlayerXLocation = PlayerXLocation - 1;
				TABase.appendText("\nYou move right");	
				BoundryCheck();
				moveMissions();
			}
			else if(TF.getText().equalsIgnoreCase("stay"))
			{
				PlayerXLocation = PlayerXLocation - 1;
				TABase.appendText("\nYou stay where you are");	
				moveMissions();
			}
			else
			{
				Alert update = new Alert(Alert.AlertType.WARNING);
				update.setHeaderText("Invalid Entry o");
				update.setContentText("Value entered is null or the text entered was not matching the text on the screen");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}
		});
		if(PlayerYLocation == MakeCover.getCoverY() && PlayerXLocation == MakeCover.getCoverX())
		{
			TABase.appendText("\nYou are in cover");	
			coverType = 1;//Gives the int a value of 1 as it is the player that is in cover	
		}
		else if(PlayerYLocation == MakeCrate.getCrateY() && PlayerXLocation == MakeCrate.getCrateX())
		{
			if(MakeCrate.getCrateUse() != 0)
			{
				TABase.appendText("\nYou picked up a crate");
				MakeCrate.useCrate();
				MakeCrate.genCrateType();
				if(MakeCrate.getCrateType() == 1 || MakeCrate.getCrateType() == 2 || MakeCrate.getCrateType() == 3)
				{
					TABase.appendText("\nIncreased weapon damage by 2");
					PlayerWeapon.addminDamage(2);
					PlayerWeapon.addmaxDamage(2);
				}
				else if(MakeCrate.getCrateType() == 4 || MakeCrate.getCrateType() == 5 || MakeCrate.getCrateType() == 6)
				{
					TABase.appendText("\nIncreased health by 5");
					PlayerStats.addHp(5);
					TABase.appendText("\nIncreased armour by 5");
					PlayerStats.addArmour(5);
				}
				else if(MakeCrate.getCrateType() == 7)
				{
					TABase.appendText("\nIncreased health by 7");
					PlayerStats.addHp(7);
					TABase.appendText("\nIncreased armour by 7");
					PlayerStats.addArmour(7);
				}
				else if(MakeCrate.getCrateType() == 8)
				{
					TABase.appendText("\nIncreased weapon damage by 4");
					PlayerWeapon.addminDamage(4);
					PlayerWeapon.addmaxDamage(4);
				}
				else if(MakeCrate.getCrateType() == 9)
				{
					TABase.appendText("\nThe crate is empty");
				}
			}
			else
			{
				TABase.appendText("\nThis crate has already been used");
			}
		}
	}

	public static void Enemymove()//Enemy moves in a random direction, will 'bounce' if goes near a border and move another direction, if it is on the same X or Y value of a cover piece it shall move towards it
	{
		if(EnemyYLocation == MakeCover.getCoverY() && EnemyXLocation == MakeCover.getCoverX())
		{
			coverType = 2;
			BoundryCheck();//Checks if enemy moves towards edge of map
		}
		else if(coverType != 2)	//Enemy will move around randomly until the coverType is equal to 2
		{
			if(enemyMove.move() == 1)
			{
				EnemyYLocation = EnemyYLocation + 1;
			}
			else if(enemyMove.move() == 2)
			{
				EnemyYLocation = EnemyYLocation - 1;
			}
			else if(enemyMove.move() == 3)
			{
				EnemyXLocation = EnemyXLocation + 1;
			}
			else if(enemyMove.move() == 4)
			{
				EnemyXLocation = EnemyXLocation - 1;
			}
			BoundryCheck();
		}
	}

	public static void locationShow()//Shows location of both player and enemy as well as a map
	{
		TALocation.appendText("\nPlayer: Y-["+PlayerYLocation+"] X-["+PlayerXLocation+"]");
		TALocation.appendText("\nEnemy: Y-["+EnemyYLocation+"] X-["+EnemyXLocation+"]");
		TALocation.appendText("\nCover: Y-["+MakeCover.getCoverY()+"] X-["+MakeCover.getCoverX()+"]");
		TALocation.appendText("\nCrate: Y-["+MakeCrate.getCrateY()+"] X-["+MakeCrate.getCrateX()+"]\n");
	}

	public static void print() throws FileNotFoundException //Prints results to the text document
	{
		TF.clear();
		TABase.clear();
		try
		{	
			PrintWriter out = new PrintWriter(theFile);
			out.println("-----------");
			if(result == 1)
			{
				out.println("Result: Player finished");
			}
			else if(result == 3)
			{
				out.println("Result: Player death");
			}
			out.println("Name: "+PlayerName);
			out.println("Score: "+Score);
			out.println("Kills: "+kills);
			out.println("-----------");
			out.close();		
			clear();
			start();
		}
		catch(Exception ex)
		{
			TABase.appendText("\nFile not found");
		}
	}

	public static void clear()//Clears all values
	{
		PlayerWeapon = new WeaponList(null,0,0,0,0);
		PlayerRollWeapon = new WeaponList(null,0,0,0,0);
		PlayerStats = new Stats (0,0);
		PlayerStatsRoll = new Stats (0,0);
		AmmoRoll = 0;
		PlayerName = null;
		AllyStat = new Stats (0,0);
		AllyStatRoll = new Stats (0,0);
		AllyWeapon = new WeaponList(null,0,0,0,0);
		AllyAmmoRoll= 0;
		AllyName = null;
		AllyStat_2 = new Stats (0,0);
		AllyStat_2Roll = new Stats (0,0);
		Ally_2Weapon = new WeaponList(null,0,0,0,0);
		Ally_2AmmoRoll = 0;
		Ally_2Name = null;
		AllyCount = 0;
		statCounter = 0;
		EnemyWeapon = new WeaponList(null,0,0,0,0);
		EnemyAmmoRoll = 0;
		EnemyStats = new Stats (0,0);
		EnemyParty = 0;
		EnemyUnitWeapon = new WeaponList(null,0,0,0,0);
		EnemyAmmoUnitRoll = 0;
		EnemyUnitStats = new Stats (0,0);
		EnemyUnitStatsRoll = new Stats (0,0);
		EnemyUnit_2Weapon = new WeaponList(null,0,0,0,0);
		EnemyAmmoUnit_2Roll = 0;
		EnemyUnit_2Stats = new Stats (0,0);
		EnemyUnit_2StatsRoll = new Stats (0,0);
		EnemyWeaponBoss = new WeaponList(null,0,0,0,0);
		EnemyAmmoBoss = 0;
		EnemyBoss = new Stats (0,0);
		EnemyBossRoll = new Stats (0,0);
	}
}
