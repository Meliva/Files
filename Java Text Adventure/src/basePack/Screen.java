
package basePack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Screen extends Application 
{
	protected Dice AccRolls = new Dice();//The dice for accuracy 
	protected Dice ArmRolls = new Dice();//The dice for armour\armor saving
	protected Dice CrtRolls = new Dice();//The dice for critical hits
	protected Dice TrnRolls = new Dice();//The dice for deciding who goes first
	protected Dice T1_Target = new Dice();//The dice for which person is targeted
	protected Dice T2_Target = new Dice();//Same as one above, to avoid overriding of targeting wrong person
	protected int BuildType;// An int which tells builds are being applied to (i.e player or ally)
	protected Stats BuildStats = new Stats (0,0);//A neutral stat which is chosen by player
	protected WeaponList BuildWeapon = new WeaponList(null, 0, 0, 0);// A neutral weapon which is chosen by player
	protected WeaponList PlayerWeapon = new WeaponList(null, 0, 0, 0);//Player weapon which is applied by build weapon
	protected WeaponList PlayerRollWeapon = new WeaponList(null, 0, 0, 0);//Player roll back weapon used to reset the stats
	protected Stats PlayerStats = new Stats (0,0);//Player stat which is applied by build stat
	protected Stats PlayerStatsRoll = new Stats (0,0);//Player stat roll back needed for when healing option is selected 
	protected int AmmoRoll;// Ammo roll for player weapon to reset value once the reload function is executed 
	protected String PlayerName = null;//Player name
	protected Stats AllyStat = new Stats (0,0);// A neutral stat which is applied by build stat
	protected Stats AllyStatRoll = new Stats (0,0);// A roll back to heal the first ally to full values
	protected WeaponList AllyWeapon = new WeaponList(null, 0, 0, 0); // A Ally weapon  which is applied by build weapon
	protected int AllyAmmoRoll; // Ammo roll for ally weapon for when they reload
	protected String AllyName = null; // Ally name
	protected Stats AllyStat_2 = new Stats (0,0);// Second ally stat applied by build stat
	protected Stats AllyStat_2Roll = new Stats (0,0);// Roll back for healing of second ally
	protected WeaponList Ally_2Weapon = new WeaponList(null, 0, 0, 0); // Second ally weapon applied by build weapon
	protected int Ally_2AmmoRoll;//Roll back for ammo
	protected String Ally_2Name = null;// Second ally name
	protected int AllyCount = 0;// Counter for the how many allies the player has
	protected int statCounter = 0;//Used to allow the player to enter stats once
	protected int AttackType = 0;//Attack type of which person is attacking
	protected boolean Hit = false;//A boolean of the hit value will turn true if the dice is equal, automatically set to false with each attack
	protected Stats NeutralStats = new Stats (0,0);//Used in the attack area and used to damage the target's stats of the one it has been given
	protected WeaponList NeutralWeapon = new WeaponList(null, 0, 0, 0);//Used to grab the attacker's weapon values
	protected int NeutralAmmoRoll;//Used in attack area to reload the attackers weapon
	protected int damage = 0;//Damage value used in attacks
	protected Stats BuildEnemyStats = new Stats (0,0);//The neutral stats for the enemy
	protected WeaponList BuildEnemyWeapon = new WeaponList(null, 0, 0, 0);//The neutral weapon for the enemy
	protected WeaponList EnemyWeapon = new WeaponList(null, 0, 0, 0);//Weapon for the first enemy
	protected int EnemyAmmoRoll;//Ammo roll for the first enemy
	protected Stats EnemyStats = new Stats (0,0);//Stats for the first enemy
	protected Stats EnemyStatsRoll = new Stats (0,0);//Stats roll back for the first enemy
	protected int EnemyParty = 0;// Counter for the enemy party (will have the stats and weapons given but will only be active if you have allies[goes on a 1:1 scale])
	protected WeaponList EnemyUnitWeapon = new WeaponList(null, 0, 0, 0);//Weapon for the secound enemy
	protected int EnemyAmmoUnitRoll;//Ammo roll for secound enemy
	protected Stats EnemyUnitStats = new Stats (0,0);//Stats for secound enemy
	protected Stats EnemyUnitStatsRoll = new Stats (0,0);//Stats roll back for secound enemy
	protected WeaponList EnemyUnit_2Weapon = new WeaponList(null, 0, 0, 0);//Weapon for third enemy
	protected int EnemyAmmoUnit_2Roll;//Ammo roll back for third enemy
	protected Stats EnemyUnit_2Stats = new Stats (0,0);//Stats for third enemy
	protected Stats EnemyUnit_2StatsRoll = new Stats (0,0);//Stats roll back for third enemy
	protected WeaponList EnemyWeaponBoss = new WeaponList(null, 0, 0, 0);//Weapon for enemy
	protected int EnemyAmmoBoss;//Ammo roll back for boss
	protected Stats EnemyBoss = new Stats (0,0);//Stats for boss
	protected Stats EnemyBossRoll = new Stats (0,0);//Stats roll back for boss
	protected String RollLine;//Used to a be applied to the screen for a death line of ally or enemy
	protected int Grabber;//Int applied to a random number (from class) and used to pull a text block of a line
	protected int randFlag;//Used for if the player decides to randomize the enemies or not
	protected int Mission_Count = 0;//Counter for how many missions have been accomplished
	protected int EnemyCount = 0;//The counter for how many enemies have been made by the player
	protected int Score = 0;//The score for the player which can increase
	protected int Random = 0;//The int value which the value of what the enemy will be
	protected int bossIntAll = 0;//Used for value of if boss is allowed
	protected int bossAttack = 0;//Used to boost attack of boss
	protected int bossHP = 0;//Used to boost HP of boss
	protected int bossArmour = 0;//Used to boost Armour of boss
	protected int bossCounter = 0;//Used to keep track of what is boosted for the boss
	protected int baseX = 0;//The bottom of X value will stay at 0
	protected int baseY = 0;//The bottom of Y value will stay at 0
	protected int customX = 0;//Will be the maximum of the X value
	protected int customY = 0;//Will be the maximum of the Y value
	protected int mapCounter = 0;
	protected int PlayerXLocation;//The X location for the player (and allies)
	protected int PlayerYLocation;//The Y location for the player (and allies)
	protected int EnemyXLocation;//The X location for Enemies (as well as other enemies)
	protected int EnemyYLocation;//The Y location for Enemies (as well as other enemies)
	protected Dice enemyMove = new Dice();//Dice function for which direction the enemy will move in
	protected boolean PlayerRange = false;//Boolean for if the player is in range (only takes the player range)
	protected boolean EnemyRange = false;//Boolean for if the enemy is in range (enemy has the same range all round)
	protected Cover MakeCover = new Cover (0, 0, 0);//Cover object Ignored by melee
	protected Crate MakeCrate = new Crate (0, 0, 0, 0);
	protected int coverType = 0;//used for the enemy if they have cover or not
	protected int turn = 0;//Counter for how many turns have been going
	protected int loop = 0;//Used for the generating map (cover and location)
	protected TextArea TABase = new TextArea();//Main screen
	protected TextField TF = new TextField();//Text field for what the player can enter
	protected Button clearB = new Button("Clear");//Clear button clears all screens
	protected Button proceedB = new Button("Proceed");//Button to go forward with anything
	protected TextArea TALog = new TextArea();//The screen which shows combat things
	protected TextArea TALocation = new TextArea();//The screen that shows the X and Y location of the player and enemy
	protected static File theFile = new File ("Results\\PlayerResults.txt"); //A file to where the results are released to
	protected Date theDate;//The current date on the machine	
	protected int result = 0;//Used to print if the player dies or completes the missions
	protected int theAttacker = 0;//Used to switch the attacker
	protected int count = 0;//Used to count the turns
	protected int playerCount = 0;
	protected int enemyCount = 0;
	protected boolean coverCheck = false;//Boolean for cover
	protected int deathType = 0;
	//-------------------------------------------------------
	public static void main(String[] args) throws IOException
	{
		theFile.createNewFile(); //Used to create the file within the location specified 
		Application.launch();
	}

	public void start(Stage stage) throws Exception 
	{
		build(stage);
		stage.setTitle("Text Adventure!");
		stage.show();
		stage.setOnCloseRequest(e->{
			Platform.exit();
			System.exit(0);
		});
		Thread.currentThread().setUncaughtExceptionHandler((thread, exception) ->
		{
			Alert update = new Alert(Alert.AlertType.ERROR);
			update.setHeaderText(null);
			update.setContentText("Stage Error:"+exception);
			Optional<ButtonType> mesult =update.showAndWait();
			if(mesult.get() == ButtonType.OK)
			{
				System.exit(0);
			}
		});
	}

	public void build(Stage stage) throws Exception 
	{
		//Min and Max size of the TABase text area
		TABase.setMinHeight(200);
		TABase.setMaxHeight(400);
		TABase.setMinWidth(500);
		TABase.setMaxWidth(550);
		TABase.setEditable(false);
		//Min and Max size of the TALog text area
		TALog.setMinWidth(150);
		TALog.setMaxWidth(200);
		TALog.setMinHeight(150);
		TALog.setMaxHeight(200);
		TALog.setEditable(false);
		Label LogLabel = new Label ("Combat Log");
		TALog.setVisible(false);
		//Min and Max size of the TALocation text area
		TALocation.setMinWidth(150);
		TALocation.setMaxWidth(200);
		TALocation.setMinHeight(150);
		TALocation.setMaxHeight(200);
		TALocation.setEditable(false);
		Label LocationLabel = new Label ("Location");
		TALocation.setVisible(false);
		Label L = new Label("Input Here->");
		TF.setMaxWidth(100);
		TF.setMinWidth(100);
		//TF.setEditable(false);
		clearB.setDisable(true);
		clearB.setOnAction((e)->
		{
			Alert update = new Alert(Alert.AlertType.CONFIRMATION);
			update.setHeaderText(null);
			update.setContentText("Screen Wipe (clear both location and combat log?)");
			Optional<ButtonType> result = update.showAndWait();
			if(result.get() == ButtonType.OK)
			{
				TALocation.clear();
				TALog.clear();
				start();
			}
		});
		//Section of smaller areas divided to give a good amount of spacing
		//Top Section
		VBox topLeftArea = new VBox(LocationLabel,TALocation);
		topLeftArea.setStyle("-fx-alignment: center;-fx-spacing:10");
		HBox topMidArea = new HBox(TABase);
		topMidArea.setStyle("-fx-alignment: center;-fx-spacing:10");
		VBox topRightArea = new VBox(LogLabel,TALog);
		topRightArea.setStyle("-fx-alignment: center;-fx-spacing:10");
		//Mid Section
		HBox midArea1 = new HBox(L,TF,proceedB);
		midArea1.setStyle("-fx-alignment: center;-fx-spacing:20");
		HBox midArea2 = new HBox(clearB);
		midArea2.setStyle("-fx-alignment: center;-fx-spacing:10");
		//Grouping of smaller areas into one bigger area
		HBox topArea = new HBox(topLeftArea,topMidArea,topRightArea);
		topArea.setStyle("-fx-alignment: center;-fx-spacing:10");
		HBox midArea = new HBox(midArea1,midArea2);
		midArea.setStyle("-fx-alignment: center;-fx-spacing:100");
		VBox root = new VBox(topArea,midArea);
		root.setStyle("-fx-font-size: 20;"+"-fx-alignment: center;-fx-spacing:10");
		Scene scene = new Scene(root);
		stage.setScene(scene);	
		start();
	}

	public void start()
	{
		String S1 = "Status";
		String S2 = "Begin";
		String S3 = "Finish";
		String S4 = "Healing";
		String S5 = "Barracks";
		String S6 = "Options";
		TABase.appendText("-------------------------------------------------------------");
		TABase.appendText("\nMenu ");
		TABase.appendText("\n\t("+S1+") Pick Health, Armour & Weapon");
		TABase.appendText("\n\t("+S2+") Begin Mission");
		TABase.appendText("\n\t("+S3+") Finish Mission");
		TABase.appendText("\n\t("+S4+") Apothecary");
		TABase.appendText("\n\t("+S5+") Barracks");
		TABase.appendText("\n\t("+S6+") Options");
		TABase.appendText("\n-------------------------------------------------------------");
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
	}

	public void finishMissions() //When player has finished a set amount of missions
	{
		if(Mission_Count == 3)
		{
			TABase.appendText("\nEnough Missions have been done! ("+Mission_Count+"/3)");
			TABase.appendText("\nScore: ["+Score+"]");
			result = 1;
			try 
			{
				print();		
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("File not found");
			}
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
			TABase.appendText("\nMore than enough missions have been done! ("+Mission_Count+"/3)");
			TABase.appendText("\nScore: ["+Score+"]");
			result = 2;
			try 
			{
				print();		
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("File not found");
			}
		}
		else
		{
			TABase.appendText("\nNot enough Missions! ("+Mission_Count+"/3)");
		}
	}

	public void buildMissions()
	{
		//clearB.setDisable(true);
		if (EnemyCount == 0 || PlayerStats == null)
		{
			TABase.appendText("\nEnsure Player is given Stats and the enemy type is decided:");
		}
		else
		{
			clearB.setDisable(false);
			TALocation.setVisible(true);
			locationShow();
			TALog.setVisible(true);
			TABase.appendText("\nEnemy Sigheted");
			PrimtiveStike();
			moveMissions();
		}
	}

	public void moveMissions()
	{
		if(AllyCount == 2)
		{
			EnemyParty = 2;
		}
		else if(AllyCount == 1)
		{
			EnemyParty = 1;
		}

		if(PlayerRange == false && EnemyRange == false)
		{
			if(PlayerRange == true)
			{
				PlayerAttack();
			}
			if(EnemyRange == true)
			{
				EnemyAttack();
			}
			Enemymove();
			Move();
			locationShow();
		}
		if(PlayerRange == true && EnemyRange == true)
		{
			TABase.clear();
			attackMission();	
		}
	}

	public void attackMission()
	{
		TF.clear();
		if(EnemyCount == 0)
		{
			TABase.appendText("\nAll Enmies Killed");
			Mission_Count = Mission_Count + 1;
			turn = 0;
			TABase.clear();
			PlayerWeapon = PlayerRollWeapon;
			PlayerRange = false;
			EnemyRange = false;
			start();
		}
		else if (EnemyCount != 0)
		{
			TABase.appendText("\n-------------------------------------------------------------");
			TABase.appendText("\nEnimes: "+EnemyCount+"\nAllies: "+AllyCount);
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
					update.setHeaderText("Invalid Entry");
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

	public int PrimtiveStike()
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

	public void combatCombo()
	{
		System.out.println("Begin: "+count);
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

	public void PlayerAttack()
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

	public void EnemyAttack()
	{
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

	public void Attack()//All Neutral values are overridden  by different attacking people
	{
		damage = 0;		
		if(AttackType == 1 || AttackType == 2 || AttackType == 3)
		{
			int T1 = T1_Target.Target();
			if(AttackType == 1)
			{
				TALog.appendText("\nPlayer Turn:");
				damage = PlayerWeapon.getDamage();
				NeutralWeapon = PlayerWeapon;
				NeutralAmmoRoll = AmmoRoll;
			}
			else if(AttackType == 2)
			{
				TALog.appendText("\nAlly Turn:");
				damage = AllyWeapon.getDamage();
				NeutralWeapon = AllyWeapon;
				NeutralAmmoRoll =  AllyAmmoRoll;
			}
			else if(AttackType == 3)
			{
				TALog.appendText("\nAlly Turn:");
				damage = Ally_2Weapon.getDamage();
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
				damage = EnemyWeapon.getDamage();
				NeutralWeapon = EnemyWeapon;
				NeutralAmmoRoll = EnemyAmmoRoll;	
			}
			else if(AttackType == 5)
			{
				TALog.appendText("\nEnemy #2 Turn");
				damage = EnemyUnitWeapon.getDamage();
				NeutralWeapon = EnemyUnitWeapon;
				NeutralAmmoRoll = EnemyAmmoUnitRoll;
			}
			else if(AttackType == 6)
			{
				TALog.appendText("\nEnemy #3 Turn");
				damage = EnemyUnit_2Weapon.getDamage();
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
				if(Hit == true && coverCheck == true && MakeCover.getCoverHealth() != 0 && AccRolls.Accuracy() == 1 || AccRolls.Accuracy() == 2 || AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 5 || AccRolls.Accuracy() == 7 ||AccRolls.Accuracy() == 8 || AccRolls.Accuracy() == 9)
				{
					Hit = false;
					MakeCover.DamageCover();
				}
				else if(Hit == true && coverCheck == true && MakeCover.getCoverHealth() != 0 && AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 6 || AccRolls.Accuracy() == 10)
				{
					Hit = true;
				}
				else if(AccRolls.Accuracy() == 1 || AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 5 || AccRolls.Accuracy() == 7 || AccRolls.Accuracy() == 9)
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
				if(NeutralWeapon.getRange() > 1 && CrtRolls.Crit() == 11 || CrtRolls.Crit() == 12)
				{
					damage = damage * 2;
					TALog.appendText("A Critical Hit!");
				}
				if(ArmRolls.Save() == 1 || ArmRolls.Save() == 3 || ArmRolls.Save() == 5)
				{
					if(NeutralStats.getArmour() <= 0)//Used if hit was armour save but no armour exists
					{
						TALog.appendText("\nHealth Damage: "+damage);
						NeutralStats.damageHealth(damage);
					}
					else
					{
						TALog.appendText("\nArmour Damage: "+damage);
						NeutralStats.damageArmour(damage);
					}
				}
				else
				{
					TALog.appendText("\nHealth Damage: "+damage);
					NeutralStats.damageHealth(damage);
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

	public void Checker()
	{
		if(PlayerStats.getHealth() <= 0)
		{
			PlayerDeath();
		}
		if(AllyCount >= 0 && AllyCount != 0)
		{
			if(AllyStat.getHealth() <=0 || AllyStat_2.getHealth() <=0)
			{
				deathType = 1;
				LineGrabber();
				AllyDeath();
			}
		}
		if(EnemyStats.getHealth() <= 0 || EnemyUnitStats.getHealth() <= 0 || EnemyUnit_2Stats.getHealth() <=0)
		{
			deathType = 2;
			LineGrabber();
			EnemyKilled();
		}
	}

	public void healing()
	{
		PlayerStats.HealHp(PlayerStatsRoll.getHealth());
		PlayerStats.HealArmour(PlayerStatsRoll.getArmour());
		AllyStat.HealHp(AllyStatRoll.getHealth());
		AllyStat.HealArmour(AllyStatRoll.getArmour());
		AllyStat_2.HealHp(AllyStat_2Roll.getHealth());
		AllyStat_2.HealArmour(AllyStat_2Roll.getArmour());
	}

	public void tempHeal()
	{
		int quadhp = PlayerStatsRoll.getHealth() /4;
		int quadhp1 = AllyStatRoll.getHealth() /4;
		int quadhp2 = AllyStat_2Roll.getHealth() /4;
		PlayerStats.HealHp(quadhp);
		AllyStat.HealHp(quadhp1);
		AllyStat_2.HealHp(quadhp2);
	}

	public void barracks()
	{
		TF.clear();
		if(AllyCount == 2)
		{
			TABase.appendText("\nMax Ally Count Reached");
		}
		else if (AllyCount == 1) //Add ally count 
		{
			TABase.appendText("\n-------------------------------------------------------------");
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
			TABase.appendText("\n-------------------------------------------------------------");
			BuildType = 2;
			TABase.appendText("\nEnter Ally Name >> ");
			proceedB.setOnAction((e) ->
			{
				AllyName = TF.getText();
				statBuilder();
			});
		}
	}

	public void setOptions()
	{
		TF.clear();
		TABase.appendText("\nSet Enemy Count >> ");
		proceedB.setOnAction((e) ->
		{
			String hap = TF.getText();
			try
			{
				EnemyCount = Integer.parseInt(hap);
				if(EnemyCount <= 0)
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
				update.setHeaderText("Invalid Entry");
				update.setContentText("Value entered is null or not equal to values one screen");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}
		});		
	}

	public void randomEnemy()
	{
		TF.clear();
		TABase.appendText("\nRandomize enemy? yes/no >> ");
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
				update.setHeaderText("Invalid Entry");
				update.setContentText("Only Value accepted is yes or no");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}
		});
	}

	public void EnemyMaker()
	{
		TF.clear();
		if(randFlag == 1)
		{
			Random RamEnemy = new Random();
			Random = RamEnemy.nextInt(7)+1;
			TABase.appendText("\nEnemy Type Randomized!");
			bossMaker();
		}
		else
		{
			TABase.appendText("\n-------------------------------------------------------------");
			TABase.appendText("\n|-Number-|-Name-|-Health-|-Armour-|");
			TABase.appendText("\nEnemy List:");
			TABase.appendText("\n| 1 |Traitors | 40 |40 |");
			TABase.appendText("\n| 2 | Pointy Ears | 20 | 15 |");
			TABase.appendText("\n| 3 | Green Ones* | 15 | 5 |");
			TABase.appendText("\n| 4 | Hungry Ones | 20 | 20 |");
			TABase.appendText("\n| 5 | Metal Skeletons | 30 | 50 |");
			TABase.appendText("\n| 6 | Blue Shooters | 15 | 15 |");
			TABase.appendText("\n| 7 | Fleshy Ones* | 20 | 0 |");
			TABase.appendText("\n *-No boss but double enemy Amount");
			TABase.appendText("\n|--------|------|-Damage-|-Range-|-Rounds-|");
			TABase.appendText("\n| 1 | Evil Bolter | 7 | Medium (3) | 7 |");
			TABase.appendText("\n| 2 | Thin Gun | 8  | Medium (3) | 5 |");
			TABase.appendText("\n| 3 | Weird Club | 6 | Melee (1) | - |");
			TABase.appendText("\n| 4 | Sharp Claws | 10 | Melee (1) | - |");
			TABase.appendText("\n| 5 | Green Lazers | 7 | Close (2) | 6 |");
			TABase.appendText("\n| 6 | Wat Beam | 14 | Very Far (5) | 10 |");
			TABase.appendText("\n| 7 | Fleshy Punch | 5 | Melee (1) | - |");
			TABase.appendText("\n-------------------------------------------------------------");
			proceedB.setOnAction((e) ->
			{
				if(TF.getText().equals("1") || TF.getText().equals("2") || TF.getText().equals("3") || TF.getText().equals("4") || TF.getText().equals("5") || TF.getText().equals("6") || TF.getText().equals("7"))
				{
					String leop = TF.getText();
					Random = Integer.parseInt(leop);
					bossMaker();
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
		}
	}

	public void bossMaker()
	{
		TF.clear();
		if(EnemyCount==1 || Random == 7 || Random == 3)
		{
			moop();
		}
		else if(bossCounter==0)
		{
			TABase.appendText("\n-------------------------------------------------------------");
			TABase.appendText("\nDo You want a boss to be made?");
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
		proceedB.setOnAction((e)->
		{
			if(TF.getText().equalsIgnoreCase("Yes")&&bossCounter == 0)
			{
				bossIntAll = 1;
				bossCounter ++;
			}
			else if(TF.getText().equalsIgnoreCase("No")&&bossCounter == 0)
			{
				bossIntAll = 0;
				bossAttack = 0;
				bossHP = 0;
				bossArmour = 0;
				moop();
			}
			else if(bossCounter == 1 && !TF.getText().contains("-"))
			{
				bossCounter ++;
				String hp = TF.getText();
				try
				{
					bossHP = Integer.parseInt(hp);
				}
				catch(NumberFormatException ex)
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
			}
			else if(bossCounter == 2 && !TF.getText().contains("-"))
			{
				bossCounter ++;
				String arm = TF.getText();
				try
				{
					bossArmour = Integer.parseInt(arm);
				}
				catch(NumberFormatException ex)
				{
					Alert update = new Alert(Alert.AlertType.WARNING);
					update.setHeaderText("Invalid Entry");
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
					bossAttack = Integer.parseInt(dam);
					moop();
				}
				catch(NumberFormatException ex)
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
	}

	public void moop()
	{
		bossCounter = 0;
		if(Random == 1)
		{
			BuildEnemyStats = new Stats(40,40);
			BuildEnemyWeapon = new WeaponList("Evil Bolter",7,3,7);
		}
		else if(Random == 2)
		{
			BuildEnemyStats = new Stats(20,15);
			BuildEnemyWeapon = new WeaponList("Thin Shooty Gun",8,3,5);
		}
		else if(Random == 3)
		{
			BuildEnemyStats = new Stats(15,5);
			BuildEnemyWeapon = new WeaponList("Weird Club",6,1,0);
			EnemyCount = EnemyCount*2;
		}
		else if(Random == 4)
		{
			BuildEnemyStats = new Stats(20,20);
			BuildEnemyWeapon = new WeaponList("Sharp Claws", 10,1,0);
		}
		else if(Random == 5)
		{
			BuildEnemyStats = new Stats(30,50);
			BuildEnemyWeapon = new WeaponList("Green Lazers",7,2,6);
		}
		else if(Random == 6)
		{
			BuildEnemyStats = new Stats(15,15);
			BuildEnemyWeapon = new WeaponList("Wat Beam",14,5,10);
		}
		else if(Random == 7)
		{
			BuildStats = new Stats(20,0);
			BuildEnemyWeapon = new WeaponList("Fleshy Punch",5,1,0);
			EnemyCount = EnemyCount*2;
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
			EnemyWeaponBoss.addDamage(bossAttack);
			EnemyAmmoBoss = EnemyWeaponBoss.getAmmo();
			EnemyBoss = BuildEnemyStats;
			EnemyBoss.addHp(bossHP);
			EnemyBoss.addArmour(bossArmour);
			EnemyBossRoll = new Stats (EnemyBoss.getHealth(),EnemyBoss.getArmour());
		}
		//Bounces Map
		MapMaker();
	}

	public void buildPlayer()
	{
		TF.clear();
		if(statCounter == 1 )
		{
			TABase.appendText("\nStatus Already Selected");
		}
		else
		{
			TABase.appendText("\n-------------------------------------------------------------");
			TABase.appendText("\nEnter Name >> ");
			TABase.appendText("\n-------------------------------------------------------------");
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
					update.setHeaderText("Invalid Entry");
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

	public void statBuilder()//Build health and armour
	{
		TF.clear();
		TABase.appendText("\n-------------------------------------------------------------");
		TABase.appendText("\n|-Number-|-Health-|-Armour-|");
		TABase.appendText("\n| 1   | 20   | 20   |");
		TABase.appendText("\n| 2   | 25   | 25   |");
		TABase.appendText("\n| 3   | 30   | 30   |");
		TABase.appendText("\n| 4   | 35   | 35   |");
		TABase.appendText("\n| 5   | 40   | 40   |");
		TABase.appendText("\nEnter Number >> ");
		TABase.appendText("\n-------------------------------------------------------------");
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
				update.setHeaderText("Invalid Entry");
				update.setContentText("Value entered is null or the value entered is not 1,2 or 3");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}

		});
	}

	public void weaponBuilder()//Build weapon
	{
		TF.clear();
		TABase.appendText("\n-------------------------------------------------------------");
		TABase.appendText("\n|-Name-|-Damage-|-Range-|-Rounds-|");
		TABase.appendText("\n| Power Sword | 8 | Melee (1) | - |");
		TABase.appendText("\n| Bolter | 5 | Medium (3) | 6 |");
		TABase.appendText("\n| Shotgun | 7 | Close (2) | 4 |");
		TABase.appendText("\n| Sniper Bolter | 12 | Very Far (5) | 1 |");
		TABase.appendText("\n| Power Hammer | 9 | Melee (1) | - |");
		TABase.appendText("\nEnter Name >> ");
		TABase.appendText("\n-------------------------------------------------------------");
		proceedB.setOnAction((e) ->
		{
			if(TF.getText().equalsIgnoreCase("Power Sword"))
			{
				BuildWeapon = new WeaponList("Power Sword",8,1,0);
				buildType();
			}
			else if(TF.getText().equalsIgnoreCase("Bolter"))
			{
				BuildWeapon = new WeaponList("Bolter",5,3,6);
				buildType();
			}
			else if(TF.getText().equalsIgnoreCase("Shotgun"))
			{
				BuildWeapon = new WeaponList("Shotgun",7,2,4);
				buildType();
			}
			else if(TF.getText().equalsIgnoreCase("Sniper Bolter"))
			{
				BuildWeapon = new WeaponList("Sniper Bolter",12,5,1);
				buildType();
			}
			else if(TF.getText().equalsIgnoreCase("Power Hammer"))
			{
				BuildWeapon = new WeaponList("Power Hammer",9,1,0);
				buildType();
			}
			else
			{
				Alert update = new Alert(Alert.AlertType.WARNING);
				update.setHeaderText("Invalid Entry");
				update.setContentText("Value entered is null or not equal to values shown on the screen");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}
		});
	}

	public void buildType()//Checks the build type number and applies the values selected to that class	
	{
		if(BuildType == 1)
		{
			PlayerStats = BuildStats;
			PlayerWeapon = BuildWeapon;
			PlayerRollWeapon = PlayerWeapon;
			PlayerStatsRoll = new Stats (PlayerStats.getHealth(),PlayerStats.getArmour());
			AmmoRoll = PlayerWeapon.getAmmo(); 
			statCounter = statCounter + 1;
		}
		else if(BuildType == 2)
		{
			AllyStat = BuildStats;
			AllyStatRoll = new Stats (AllyStat.getHealth(),AllyStat.getArmour());
			AllyWeapon = BuildWeapon;
			AllyAmmoRoll = AllyWeapon.getAmmo();
			AllyCount = AllyCount +1;
		}
		else if(BuildType == 3)
		{
			AllyStat_2 = BuildStats;
			AllyStat_2Roll = AllyStat_2;
			Ally_2Weapon = BuildWeapon;
			Ally_2AmmoRoll = Ally_2Weapon.getAmmo();
			AllyCount = AllyCount +1;
		}
		start();
	}

	public void PlayerDeath()//When Play dies this class is activated
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

	public void AllyDeath()	//When AlLy dies, checks which ally has 0 or less health and resets the stats and reduces ally count
	{
		if(AllyStat.getHealth() <=0)
		{
			TABase.appendText("/n"+AllyName+" Has Fallen!");
			AllyName = null;
			AllyStat = new Stats (0,0);
			AllyWeapon = new WeaponList(null, 0, 0, 0);
			AllyCount = AllyCount - 1;
			switcher();
		}
		else if (AllyStat_2.getHealth() <=0 && AllyCount == 2)
		{
			TABase.appendText("/n"+Ally_2Name+" Has Fallen!");
			Ally_2Name = null;
			AllyStat_2 = new Stats (0,0);
			Ally_2Weapon = new WeaponList(null, 0, 0, 0);
			AllyCount = AllyCount - 1;
		}
	}

	public void switcher()//Switches ally 2 with ally 1 as this class only goes off if the first ally has died and nullify ally 2
	{	
		if(AllyCount == 1 && AllyStat.getHealth() <= 0 && AllyStat_2.getHealth() !=0)
		{
			TABase.appendText("/n"+Ally_2Name+" is moveed to position 1!");
			AllyName = Ally_2Name;
			AllyStat = AllyStat_2;
			AllyWeapon = Ally_2Weapon;
			Ally_2Name = null;
			AllyStat_2 = new Stats (0,0);
			Ally_2Weapon = new WeaponList(null, 0, 0, 0);
		}
	}

	public void LineGrabber()//Grabs a line to be used with a death of any ally/enemy
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
	}

	public void EnemyKilled()//Checks which enemy has been killed
	{
		if(EnemyStats.getHealth() <= 0)
		{
			TABase.appendText("\nEnemy #1 Killed");
			Score = Score + 50;
			EnemyCount = EnemyCount -1;
			EnemyStats.HealHp(EnemyStatsRoll.getHealth());
			EnemyStats.HealArmour(EnemyStatsRoll.getArmour());
		}
		else if(EnemyUnitStats.getHealth() <= 0)
		{
			TABase.appendText("\nEnemy #2 Killed");
			Score = Score + 50;
			EnemyCount = EnemyCount -1;
			EnemyUnitStats.HealHp(EnemyUnitStatsRoll.getHealth());
			EnemyUnitStats.HealArmour(EnemyUnitStatsRoll.getArmour());
		}
		else if(EnemyUnit_2Stats.getHealth() <=0 && EnemyParty == 2)
		{
			TABase.appendText("\nEnemy #3 Killed");
			Score = Score + 50;
			EnemyCount = EnemyCount -1;
			EnemyUnit_2Stats.HealHp(EnemyUnit_2StatsRoll.getArmour());
			EnemyUnit_2Stats.HealArmour(EnemyUnit_2StatsRoll.getArmour());
		}
		if(EnemyCount == 1 && bossIntAll == 1)
		{
			TABase.appendText("The last Enemy has become the boss!");
			EnemyStats = EnemyBoss;
			EnemyWeapon = EnemyWeaponBoss;
			EnemyAmmoRoll = EnemyAmmoBoss;
		}
	}

	public void displayStatus()
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

	public void MapMaker()
	{
		if(mapCounter == 0)
		{
			TF.clear();
			TABase.appendText("\nEnter X Length >> ");
			proceedB.setOnAction((e) ->
			{
				if(TF.getText().matches("[0-9]+"))//The text does contain numbers
				{
					String x = TF.getText();
					customX = Integer.parseInt(x);
					MapMaker();
				}
				else
				{
					TF.clear();
				}
				mapCounter++;
				MapMaker();
			});
		}
		else if(mapCounter == 1)
		{
			TF.clear();
			TABase.appendText("\nEnter Y Length >> ");
			proceedB.setOnAction((e) ->
			{
				if(TF.getText().matches("[0-9]+"))//The text does contain numbers
				{
					String y = TF.getText();
					customY = Integer.parseInt(y);
					MapMaker();
				}
				else
				{	
					TF.clear();
				}
				mapCounter++;
				MapMaker();
			});
		}
		else if(mapCounter == 2 && (customX <= 5 || customY <= 5))
		{
			TABase.appendText("\nMap size too small ensure both values are larger then 5");
			customX = 0;
			customY = 0;	
			mapCounter = 0;
			MapMaker();
		}
		else if(mapCounter == 2)
		{
			//Generates Cover
			if(loop == 0)
			{
				loop = loop + 1;
				CoverMaker();
			}
			else if(loop == 1)
			{
				loop = loop + 1;
				Randomizer();
			}
			else
			{
				start();
			}
		}
	}

	public void Randomizer()
	{
		//Randomly places character within map
		Random randomY = new Random ();
		Random randomX = new Random ();
		PlayerYLocation = randomY.nextInt(customY) + 1;
		PlayerXLocation = randomX.nextInt(customX) + 1;
		//Randomly places enemy within map
		Random eRandomY = new Random ();
		Random eRandomX = new Random ();
		EnemyYLocation = eRandomY.nextInt(customY) + 1;
		EnemyXLocation = eRandomX.nextInt(customX) + 1;
		MapCheck();
	}

	public void CrateMaker()
	{
		MakeCrate = new Crate (0, 0, 1, 0);
		MakeCrate.CrateXMaker(customX);
		MakeCrate.CrateYMaker(customY);
		MapCheck();
	}

	public void CoverMaker()
	{
		MakeCover = new Cover (0, 0, 3);
		MakeCover.CoverXMaker(customX);
		MakeCover.CoverYMaker(customY);
		MapCheck();
	}

	public void RangeCheck()
	{
		//Checks player location and adds weapon range and if it equals the Enemy location the player is in range of the enemy the minus one is to only include the weapon range
		if((PlayerXLocation + PlayerWeapon.getRange()) == EnemyXLocation)
		{
			PlayerRange = true;
		}
		else if((PlayerYLocation + PlayerWeapon.getRange()) == EnemyYLocation)
		{
			PlayerRange = true;
		}
		else
		{
			PlayerRange = false;
		}

		//Like the one above but for the enemy to get into range of the player
		if((EnemyXLocation + EnemyWeapon.getRange()) == PlayerXLocation)
		{
			EnemyRange = true;
		}
		else if((EnemyYLocation + EnemyWeapon.getRange()) == PlayerYLocation)
		{
			EnemyRange = true;
		}
		else
		{
			EnemyRange = false;
		}
	}

	public void MapCheck()
	{
		if(EnemyXLocation == baseX || EnemyXLocation == customX || EnemyYLocation == baseY || EnemyYLocation == customY)//Checks when the enemy spawn are on the edge of the map and they are sorted again if they are
		{
			Randomizer();
		}
		else if(MakeCover.getCoverX() == baseX || MakeCover.getCoverX() == customX || MakeCover.getCoverY() == baseY || MakeCover.getCoverY() == customY)//Checks cover piece and if it has reached the edge of the map
		{
			CoverMaker();
		}
		else if(MakeCrate.getCrateX() == baseX || MakeCrate.getCrateX() == customX || MakeCrate.getCrateY() == baseY || MakeCrate.getCrateY() == customY)//Checks crate location and if it has reached the edge of the map or not
		{
			CrateMaker();
		}
		else if(PlayerXLocation == baseX || PlayerXLocation == customX || PlayerYLocation == baseY || PlayerYLocation == customY)//Checks if the player reaches the limits of the map they are moved back one and they are sorted again
		{
			Randomizer();
		}
		else if(MakeCrate.getCrateX() == MakeCover.getCoverX() && MakeCrate.getCrateY() == MakeCover.getCoverY())
		{
			int Ch = 0;
			Random Y = new Random();
			Ch = Y.nextInt(2)+1;
			if(Ch == 1)
			{
				CrateMaker();
			}
			else if(Ch == 2)
			{
				CoverMaker();
			}
		}
		else
		{
			MapMaker();
		}
	}

	public void BoundryCheck()
	{
		//Checks when the enemy spawn to move it away from the edges of the map
		if(EnemyXLocation == baseX)
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
		//Checks if the player reaches the limits of the map they are moved back one 
		if(PlayerXLocation == baseX)
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

	public void Move()
	{
		TF.clear();
		//Allows the player to move in 4 directions 
		TABase.appendText("\n-Move up- \n-Move down- \n-Move left- \n-Move right- \n-Show location-");
		TABase.appendText("\nEnter movement choice >> ");
		proceedB.setOnAction((e) ->
		{		
			if(TF.getText().equalsIgnoreCase("move up"))
			{
				PlayerYLocation = PlayerYLocation + 1;
				TABase.appendText("\nYou move up");
				BoundryCheck();//Checks if player moves towards edge of map
				RangeCheck();//Range check is used at the end of a movement
				moveMissions();//Moves player back to the move mission as it causes a loop
			}
			else if(TF.getText().equalsIgnoreCase("move down"))
			{
				PlayerYLocation = PlayerYLocation - 1;
				TABase.appendText("\nYou move down");
				BoundryCheck();
				RangeCheck();
				moveMissions();
			}
			else if(TF.getText().equalsIgnoreCase("move left"))
			{
				PlayerXLocation = PlayerXLocation + 1;
				TABase.appendText("\nYou move left");
				BoundryCheck();
				RangeCheck();
				moveMissions();
			}
			else if(TF.getText().equalsIgnoreCase("move right"))
			{
				PlayerXLocation = PlayerXLocation - 1;
				TABase.appendText("\nYou move right");	
				BoundryCheck();
				RangeCheck();
				moveMissions();
			}
			else if(TF.getText().equalsIgnoreCase("stay"))
			{
				PlayerXLocation = PlayerXLocation - 1;
				TABase.appendText("\nYou stay where you are");	
				RangeCheck();
				moveMissions();
			}
			else
			{
				Alert update = new Alert(Alert.AlertType.WARNING);
				update.setHeaderText("Invalid Entry");
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
					PlayerWeapon.addDamage(2);
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
					PlayerWeapon.addDamage(4);
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
		else
		{
			coverType = 0;
		}
	}

	public void Enemymove()//Enemy moves in a random direction, will 'bounce' if goes near a border and move another direction, if it is on the same X or Y value of a cover piece it shall move towards it
	{
		if(EnemyYLocation == MakeCover.getCoverY() && EnemyXLocation == MakeCover.getCoverX())
		{
			coverType = 2;		
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
			coverType = 0;
		}
		BoundryCheck();//Checks if enemy moves towards edge of map
		RangeCheck();//Range check is used at the end of a movement
	}

	public void locationShow()//Shows location of both player and enemy as well as a map
	{
		TALocation.appendText("\nPlayer: Y-["+PlayerYLocation+"] X-["+PlayerXLocation+"]");
		TALocation.appendText("\nEnemy: Y-["+EnemyYLocation+"] X-["+EnemyXLocation+"]\n");
	}

	public void print() throws FileNotFoundException //Prints results to the text document
	{
		PrintWriter out = new PrintWriter("PlayerResults.txt");
		out.println("-----------");
		if(result == 1)
		{
			out.println("Result: Player finished");
		}
		else if(result == 2)
		{
			out.println("Result: Player finished with extra score");
		}
		else if(result == 3)
		{
			out.println("Result: Player death");
		}
		out.println("Name: "+PlayerName);
		out.println("Date: "+theDate);
		out.println("Score: "+Score);
		out.println("-----------");
		out.close();
	}
}