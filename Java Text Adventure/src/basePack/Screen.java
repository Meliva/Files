
package basePack;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class Screen extends Application 
{
	static Dice AccRolls = new Dice();
	static Dice ArmRolls = new Dice();
	static Dice CrtRolls = new Dice();
	static Dice TrnRolls = new Dice();
	static Dice T1_Target = new Dice();
	static Dice T2_Target = new Dice();
	private int Turn_Count = 0;
	private int Turn_Combat = 0;

	private int BuildType;
	static Stats BuildStats = new Stats (0,0);
	static WeaponList BuildWeapon = new WeaponList(null, 0, 0, 0);

	static WeaponList PlayerWeapon = new WeaponList(null, 0, 0, 0);
	static Stats PlayerStats = new Stats (0,0);
	static Stats PlayerStatsRoll = new Stats (0,0);
	public int AmmoRoll;
	public String PlayerName = null;

	static Stats AllyStat = new Stats (0,0);
	static Stats AllyStatRoll = new Stats (0,0);
	static WeaponList AllyWeapon = new WeaponList(null, 0, 0, 0);
	public int AllyAmmoRoll;
	public String AllyName = null;
	static Stats AllyStat_2 = new Stats (0,0);
	static Stats AllyStat_2Roll = new Stats (0,0);
	static WeaponList Ally_2Weapon = new WeaponList(null, 0, 0, 0);
	public int Ally_2AmmoRoll;
	public String Ally_2Name = null;
	private int AllyCount = 0;

	public int statCounter = 0;
	public int AttackType = 0;
	public boolean Hit = false;

	static Stats NeutralStats = new Stats (0,0);
	static WeaponList NeutralWeapon = new WeaponList(null, 0, 0, 0);
	public int NeutralAmmoRoll;

	static Stats BuildEnemyStats = new Stats (0,0);
	static WeaponList BuildEnemyWeapon = new WeaponList(null, 0, 0, 0);

	static WeaponList EnemyWeapon = new WeaponList(null, 0, 0, 0);
	public int EnemyAmmoRoll;
	static Stats EnemyStats = new Stats (0,0);
	static Stats EnemyStatsRoll = new Stats (0,0);

	public int EnemyParty = 0;

	static WeaponList EnemyUnitWeapon = new WeaponList(null, 0, 0, 0);
	public int EnemyAmmoUnitRoll;
	static Stats EnemyUnitStats = new Stats (0,0);
	static Stats EnemyUnitStatsRoll = new Stats (0,0);

	public WeaponList EnemyUnit_2Weapon = new WeaponList(null, 0, 0, 0);
	public int EnemyAmmoUnit_2Roll;
	static Stats EnemyUnit_2Stats = new Stats (0,0);
	static Stats EnemyUnit_2StatsRoll = new Stats (0,0);

	static WeaponList EnemyWeaponBoss = new WeaponList(null, 0, 0, 0);
	public int EnemyAmmoBoss;
	static Stats EnemyBoss = new Stats (0,0);
	static Stats EnemyBossRoll = new Stats (0,0);

	private String RollLine;
	private int Grabber;
	private int randFlag;

	private int Mission_Count = 0;
	private int EnemyCount = 0;
	private boolean Attack = true;
	private int Score = 0;
	private int Random = 0;

	private int baseX = 0;
	private int baseY = 0;
	//The base X and Y are not changed as they are the map
	private int customX = 0;
	private int customY = 0;

	private int PlayerXLocation;
	private int PlayerYLocation;
	private int EnemyXLocation;
	private int EnemyYLocation;
	static Dice enemyMove = new Dice();

	private boolean PlayerRange = false;
	private boolean EnemyRange = false;

	Cover MakeCover = new Cover (0, 0, 0);
	public int coverType = 0;
	private int turn = 0;
	private int loop = 0;

	private TextArea TABase = new TextArea();
	private TextField TF = new TextField();
	private Button clearB = new Button("Clear");
	private Button proceedB = new Button("Proceed");
	private TextArea TALog = new TextArea();
	private TextArea TALocation = new TextArea();

	//-------------------------------------------------------
	public static void main(String[] args) 
	{
		Application.launch();
	}

	public void start(Stage stage) throws Exception 
	{
		build(stage);
		stage.setTitle("Text Adventure!");
		stage.show();
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
		//LogLabel.setVisible(false);
		//Min and Max size of the TALocation text area
		TALocation.setMinWidth(150);
		TALocation.setMaxWidth(200);
		TALocation.setMinHeight(150);
		TALocation.setMaxHeight(200);
		TALocation.setEditable(false);
		Label LocationLabel = new Label ("Location");
		TALocation.setVisible(false);
		//LocationLabel.setVisible(false);

		Label L = new Label("Input Here->");
		TF.setMaxWidth(100);
		TF.setMinWidth(100);
		//TF.setEditable(false);
		clearB.setDisable(true);
		clearB.setOnAction((e)->
		{
			Alert update = new Alert(Alert.AlertType.CONFIRMATION);
			update.setHeaderText(null);
			update.setContentText("Screen Wipe (sent back to Start)");
			Optional<ButtonType> result = update.showAndWait();
			if(result.get() == ButtonType.OK)
			{
				TABase.clear();
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
		String S4 = "MedBay";
		String S5 = "Members";
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
		//TF.setEditable(true);
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
				exit();
			}
			else if(TF.getText().equalsIgnoreCase(S4))
			{
				Healing();
			}
			else if(TF.getText().equalsIgnoreCase(S5))
			{
				Barracks();
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
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void exit() //Exit function
	{
		if(Mission_Count == 3)
		{
			//Checks if mission count is equal or greater then 3
			TABase.appendText("\nEnough Missions have been done! ("+Mission_Count+"/3)");
			TABase.appendText("\nScore: ["+Score+"]");
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
			TABase.appendText("\nMore than enough missions have been done! ("+Mission_Count+"/3)");
			TABase.appendText("\nScore: ["+Score+"]");
			sleep();	
		}
		else
		{
			TABase.appendText("\nNot enough Missions! ("+Mission_Count+"/3)");
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void buildMissions()
	{
		//clearB.setDisable(true);
		if (EnemyCount == 0 || PlayerStats == null || PlayerWeapon == null)
		{
			TABase.appendText("\nEnsure Player is given Stats and the enemy type is decided:");
			sleep();
		}
		else
		{
			TALocation.setVisible(true);
			locationShow();
			TALog.setVisible(true);
			TABase.appendText("\nEnemy Sigheted");
			PrimtiveStike();
			moveMissions();
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
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

			if(Turn_Combat == 1)
			{
				Move();
				Enemymove();
			}
			else
			{
				Enemymove();
				Move();
			}
		}
		if(PlayerRange == true && EnemyRange == true)
		{
			attackMissions();	
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void attackMissions()
	{
		if(Attack = false)
		{
			TABase.appendText("\nAll Enmies Killed");
			Mission_Count = Mission_Count + 1;
			turn = 0;
			start();
		}
		else if (Attack = true)
		{
			TABase.appendText("\n-------------------------------------------------------------");
			TABase.appendText("\nEnimes: "+EnemyCount +"\nAllies: "+AllyCount);
			TABase.appendText("Press '1' to Attack \nPress '2' for Status \n>>");
			TALog.appendText("\n-------------------------------------------------------------");
			TALog.appendText("\nTurn: "+turn);
			TALog.appendText("\n-------------------------------------------------------------");
			proceedB.setOnAction((e) ->
			{
				if(TF.getText().equalsIgnoreCase("Combat"))
				{
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
				}
			});
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
				TABase.appendText("\nYou Get The Upper Hand!");
			}
			else
			{
				Turn_Combat = 2;
				Turn_Count = 1;
				TABase.appendText("\nThe Enemy Gets The Upper Hand!");				
			}
		}
		sleep();
		return Turn_Combat;
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void PlayerAttack()
	{
		AttackType = 1;
		Attack();
		if(AllyCount == 2)
		{
			AttackType = 2;
			Attack();
			AttackType = 3;
			Attack();
		}
		if(AllyCount == 1)
		{
			AttackType = 2;
			Attack();
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void EnemyAttack()
	{
		AttackType = 4;
		Attack();
		if(EnemyParty == 2)
		{
			AttackType = 5;
			Attack();
			AttackType = 6;
			Attack();
		}
		if(EnemyParty == 1)
		{
			AttackType = 5;
			Attack();
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void Attack()
	{
		int damage = 0;
		//All Neutral values are overridden  by different attacks times
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
			if(AttackType == 2)
			{
				TALog.appendText("\nAlly Turn:");
				damage = AllyWeapon.getDamage();
				NeutralWeapon = AllyWeapon;
				NeutralAmmoRoll =  AllyAmmoRoll;
			}
			if(AttackType == 3)
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
				if(T1 == 2 || T1 == 5)
				{
					NeutralStats = EnemyUnit_2Stats;
					TALog.appendText("\nTargeting Enemy #3");
				}
				if(T1 == 1 ||  T1 == 4)
				{
					NeutralStats = EnemyStats;
					TALog.appendText("\nTargeting Enemy #1");
				}
			}
			if(EnemyParty == 1)
			{
				if(T1 == 3 || T1 == 6 || T1 == 2)
				{
					NeutralStats = EnemyUnitStats;
					TALog.appendText("\nTargeting Enemy #2");
				}
				if(T1 == 1 ||  T1 == 4 || T1 == 5)
				{
					NeutralStats = EnemyStats;
					TALog.appendText("\nTargeting Enemy #1");
				}
			}
			if(EnemyParty == 0)
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
			if(AttackType == 5)
			{
				TALog.appendText("\nEnemy #2 Turn");
				damage = EnemyUnitWeapon.getDamage();
				NeutralWeapon = EnemyUnitWeapon;
				NeutralAmmoRoll = EnemyAmmoUnitRoll;
			}
			if(AttackType == 6)
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
					TABase.appendText("\nTargeting "+PlayerName);
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
				if(AccRolls.Accuracy() == 1 || AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 5 || AccRolls.Accuracy() == 7)
				{
					Hit = true;
				}
			}
			else if(NeutralWeapon.getRange() > 1)
			{
				if(AccRolls.Accuracy() == 1 || AccRolls.Accuracy() == 3 || AccRolls.Accuracy() == 4 || AccRolls.Accuracy() == 7)
				{
					Hit = true;
				}
			}
			if(Hit == false)
			{
				TALog.appendText("\n'Missed'!");
			}
			if(Hit == true)
			{
				if(CrtRolls.Crit() == 11 || CrtRolls.Crit() == 12)
				{
					damage = damage * 2;
					TALog.appendText("A Critical Hit!");
				}
				if(ArmRolls.Save() == 1 || ArmRolls.Save() == 3 || ArmRolls.Save() == 5)
				{
					if(NeutralStats.getArmour() <= 0)
					{
						TALog.appendText("\nHealth Damage: "+damage);
						NeutralStats.damageHealth(damage);
					}
					else
					{
						TALog.appendText("\nArmour Save: "+damage);
						NeutralStats.damageArmour(damage);
					}
				}
				else
				{
					TALog.appendText("\nHealth Damage: "+damage);
					NeutralStats.damageHealth(damage);
				}
			}
			NeutralWeapon.Fire();
			//Fires Weapon
		}
		Checker();
		//Checks If Anything has died
		Hit = false;
		//Rests Hit Boolean
		sleep();
		attackMissions();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
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
				LineGrabber();
				AllyDeath();
			}
		}
		if(EnemyStats.getHealth() <= 0 || EnemyUnitStats.getHealth() <= 0 || EnemyUnit_2Stats.getHealth() <=0)
		{
			LineGrabber();
			EnemyKilled();
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void Healing()
	{
		PlayerStats.HealHp(PlayerStatsRoll.getHealth());
		PlayerStats.HealArmour(PlayerStatsRoll.getArmour());
		AllyStat.HealHp(AllyStatRoll.getHealth());
		AllyStat.HealArmour(AllyStatRoll.getArmour());
		AllyStat_2.HealHp(AllyStat_2Roll.getHealth());
		AllyStat_2.HealArmour(AllyStat_2Roll.getArmour());
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void Barracks()
	{
		TF.clear();
		if(AllyCount == 2)
		{
			TABase.appendText("\nMax Ally Count Reached");
			sleep();
		}
		else if (AllyCount == 1) //Add ally count 
		{
			BuildType = 3;
			TABase.appendText("\n-------------------------------------------------------------");
			TABase.appendText("\nEnter Ally Name >> ");
			TABase.appendText("\n-------------------------------------------------------------");
			proceedB.setOnAction((e) ->
			{
				Ally_2Name = TF.getText();
				statBuilder();
			});
		}
		else
		{
			BuildType = 2;
			TABase.appendText("\n-------------------------------------------------------------");
			TABase.appendText("\nEnter Ally Name >> ");
			TABase.appendText("\n-------------------------------------------------------------");
			proceedB.setOnAction((e) ->
			{
				AllyName = TF.getText();
				statBuilder();
			});
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void setOptions()
	{
		TF.clear();
		TABase.appendText("\nSet Enemy Count >> ");
		proceedB.setOnAction((e) ->
		{
			String hap = TF.getText();
			EnemyCount = Integer.parseInt(hap);
			randomEnemy();
		});		
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void randomEnemy()
	{
		TF.clear();
		TABase.appendText("\nRandomize enemy? y/n >> ");
		proceedB.setOnAction((e) ->
		{
			if(TF.getText().equalsIgnoreCase("y"))
			{
				randFlag = 1;
			}
			if(TF.getText().equalsIgnoreCase("n"))
			{
				randFlag = 0;
			}
			EnemyMaker();
		});
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void EnemyMaker()
	{
		if(randFlag == 1)
		{
			Random RamEnemy = new Random();
			Random = RamEnemy.nextInt(6)+1;
			TABase.appendText("\nEnemy Type Randomized!");
			moop();
		}
		else
		{
			TABase.appendText("\n-------------------------------------------------------------");
			TABase.appendText("\n|-Number-|-Name-|-Health-|-Armour-|");
			TABase.appendText("\nEnemy List:");
			TABase.appendText("\n| 1 |Traitors |40 |40 |");
			TABase.appendText("\n| 2 | Pointy Ears | 20 | 15 |");
			TABase.appendText("\n| 3 | Green Ones | 15 | 20 |");
			TABase.appendText("\n| 4 | Hungry Ones | 20 | 20 |");
			TABase.appendText("\n| 5 | Metal Skeletons | 30 | 50 |");
			TABase.appendText("\n| 6 | Blue Shooters | 15 | 15 |");
			TABase.appendText("\n|--------|------|-Damage-|-Range-|-Rounds-|");
			TABase.appendText("\n| 1 | Evil Bolter | 6 | Medium (3) | 3 |");
			TABase.appendText("\n| 2 | Thin Shooty Gun | 7  | Medium (3) | 4 |");
			TABase.appendText("\n| 3 | Weird Club | 8 | Melee (1) | - |");
			TABase.appendText("\n| 4 | Sharp Claws | 10 | Melee (1) | - |");
			TABase.appendText("\n| 5 | Green Lazers | 6 | Close (2) | 5 |");
			TABase.appendText("\n| 6 | Wat Beam | 14 | Very Far (5) | 7 |");
			TABase.appendText("\n-------------------------------------------------------------");
			proceedB.setOnAction((e) ->
			{
				if(TF.getText().equals("1") || TF.getText().equals("2") || TF.getText().equals("3") || TF.getText().equals("4") || TF.getText().equals("5") || TF.getText().equals("6"))
				{
					String leop = TF.getText();
					Random = Integer.parseInt(leop);
					moop();
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
	public void moop()
	{
		if(Random == 1)
		{
			BuildEnemyStats = new Stats (40,40);
			BuildEnemyWeapon = new WeaponList("Evil Bolter", 6, 3, 3);
		}
		else if(Random == 2)
		{
			BuildEnemyStats = new Stats (20,15);
			BuildEnemyWeapon = new WeaponList("Thin Shooty Gun", 7, 3, 4);
		}
		else if(Random == 3)
		{
			BuildEnemyStats = new Stats (15,20);
			BuildEnemyWeapon = new WeaponList("Weird Club", 8, 1, 0);
		}
		else if(Random == 4)
		{
			BuildEnemyStats = new Stats (20,20);
			BuildEnemyWeapon = new WeaponList("Sharp Claws", 10, 1, 0);
		}
		else if(Random == 5)
		{
			BuildEnemyStats = new Stats (30,50);
			BuildEnemyWeapon = new WeaponList("Green Lazers", 6, 2, 5);
		}
		else if(Random == 6)
		{
			BuildEnemyStats = new Stats (15,15);
			BuildEnemyWeapon = new WeaponList("Wat Beam", 14, 5, 7);
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
		EnemyWeaponBoss = BuildEnemyWeapon;
		EnemyWeaponBoss.addDamage(3);
		EnemyAmmoBoss = EnemyWeaponBoss.getAmmo();
		EnemyBoss = BuildEnemyStats;
		EnemyBossRoll = new Stats (EnemyBoss.getHealth(),EnemyBoss.getArmour());
		//Bounces Map
		MapMaker();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void buildPlayer()
	{
		TF.clear();
		if(statCounter == 1 )
		{
			TABase.appendText("\nStatus Already Selected");
			sleep();
		}
		else
		{
			TABase.appendText("\n-------------------------------------------------------------");
			TABase.appendText("\nEnter Name >> ");
			TABase.appendText("\n-------------------------------------------------------------");
			proceedB.setOnAction((e) ->
			{
				if(TF.getText() == null)
				{
					PlayerName = TF.getText();
					BuildType = 1;
					statBuilder();
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
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void statBuilder()
	{
		//Build health and armour
		TF.clear();
		TABase.appendText("\n-------------------------------------------------------------");
		TABase.appendText("\n|-Number-|-Health-|-Armour-|");
		TABase.appendText("\n| 1   | 20   | 20   |");
		TABase.appendText("\n| 2   | 30   | 30   |");
		TABase.appendText("\n| 3   | 40   | 40   |");
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
				BuildStats = new Stats (30,30);
				weaponBuilder();
			}
			else if(TF.getText().equals("3"))
			{
				BuildStats = new Stats (40,40);
				weaponBuilder();
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
	public void weaponBuilder()
	{
		//Build weapon
		TF.clear();
		TABase.appendText("\n-------------------------------------------------------------");
		TABase.appendText("\n|-Name-|-Damage-|-Range-|-Rounds-|");
		TABase.appendText("\n| Power Sword | 8 | Melee (1) | - |");
		TABase.appendText("\n| Bolter | 5 | Medium (3) | 4 |");
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
				BuildWeapon = new WeaponList("Bolter",5,3,4);
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
				update.setContentText("Value entered is null or not equal to values one screen");
				Optional<ButtonType> result =update.showAndWait();
				if(result.get() == ButtonType.OK)
				{
					TF.clear();
				}
			}
		});
	}
	public void buildType()
	{
		//Checks the build type number and applies the values selected to that class		
		if(BuildType == 1)
		{
			PlayerStats = BuildStats;
			PlayerWeapon = BuildWeapon;
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
	public void PlayerDeath()
	{
		//When Play dies this class is activated
		TABase.appendText("\nYou Died!");
		TABase.appendText("\nScore: ["+Score+"]");
		System.exit(0);
	}
	public void AllyDeath()
	{
		//When AlLy dies, checks which ally has 0 or less health and resets the stats and reduces ally count
		if(AllyStat.getHealth() <=0)
		{
			TABase.appendText(AllyName+" Has Fallen!");
			AllyName = null;
			AllyStat = new Stats (0,0);
			AllyWeapon = new WeaponList(null, 0, 0, 0);
			AllyCount = AllyCount - 1;
		}
		if (AllyStat_2.getHealth() <=0)
		{
			TABase.appendText(Ally_2Name+" Has Fallen!");
			Ally_2Name = null;
			AllyStat_2 = new Stats (0,0);
			Ally_2Weapon = new WeaponList(null, 0, 0, 0);
			AllyCount = AllyCount - 1;
		}
		switcher();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void switcher()
	{
		//Switches ally 2 with ally 1 as the second ally will be removed
		if(AllyCount == 1 && AllyStat.getHealth() == 0 && AllyStat_2.getHealth() !=0)
		{
			TABase.appendText(Ally_2Name+" is moveed to position 1!");
			AllyName = Ally_2Name;
			AllyStat = AllyStat_2;
			AllyWeapon = Ally_2Weapon;
		}
	}	//---------------------------------------------------------------------------------------------------------------------------------------
	public void LineGrabber()
	{
		//Grabs a line to be used with a death of any ally/enemy
		Random RamLine = new Random();
		Grabber = RamLine.nextInt(9)+1;
		Lines.GrabWord(Grabber);
		RollLine = Lines.getWord();
		TABase.appendText(RollLine);
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void EnemyKilled()
	{
		//Checks which enemy has been killed
		if(EnemyStats.getHealth() <= 0)
		{
			TABase.appendText("Enemy #1 Killed");
			Score = Score + 50;
			EnemyCount = EnemyCount -1;
			EnemyStats.HealHp(EnemyStatsRoll.getHealth());
			EnemyStats.HealArmour(EnemyStatsRoll.getArmour());
		}
		if(EnemyUnitStats.getHealth() <= 0)
		{
			TABase.appendText("Enemy #2 Killed");
			Score = Score + 50;
			EnemyCount = EnemyCount -1;
			EnemyUnitStats.HealHp(EnemyUnitStatsRoll.getHealth());
			EnemyUnitStats.HealArmour(EnemyUnitStatsRoll.getArmour());
		}
		if(EnemyUnit_2Stats.getHealth() <=0)
		{
			TABase.appendText("Enemy #3 Killed");
			Score = Score + 50;
			EnemyCount = EnemyCount -1;
			EnemyUnit_2Stats.HealHp(EnemyUnit_2StatsRoll.getArmour());
			EnemyUnit_2Stats.HealArmour(EnemyUnit_2StatsRoll.getArmour());
		}
		if(EnemyCount != 0)
		{
			TABase.appendText("Another Enemy Sighted");
			Turn_Combat = 0;
			Turn_Count = 0;
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void display_Status()
	{
		TABase.appendText("=========================");
		TABase.appendText(PlayerName);
		TABase.appendText("[--Stats--] \n"+PlayerStats);
		TABase.appendText("--Weapon-- \n"+PlayerWeapon);
		TABase.appendText("=========================");
		sleep();
		if(AllyCount == 2)
		{
			TABase.appendText("=========================");
			TABase.appendText(Ally_2Name);
			TABase.appendText("--Stats-- \n"+AllyStat_2);
			TABase.appendText("--Weapon-- \n"+Ally_2Weapon);
			TABase.appendText("=========================");
			TABase.appendText("=========================");
			TABase.appendText(AllyName);
			TABase.appendText("--Stats-- \n"+AllyStat);
			TABase.appendText("--Weapon-- \n"+AllyWeapon);
			TABase.appendText("=========================");
		}
		else if(AllyCount == 1)
		{
			TABase.appendText("=========================");
			TABase.appendText(AllyName);
			TABase.appendText("--Stats-- \n"+AllyStat);
			TABase.appendText("--Weapon-- \n"+AllyWeapon);
			TABase.appendText("=========================");
		}
		TABase.appendText("=========================");
		TABase.appendText("Enemy Status");
		TABase.appendText("--Enemy Stats-- \n"+EnemyStats);
		TABase.appendText("--Enemy Weapon-- \n"+EnemyWeapon);
		TABase.appendText("=========================");
		if(EnemyParty == 2)
		{
			TABase.appendText("=========================");
			TABase.appendText("Enemy Status");
			TABase.appendText("--Enemy Stats-- \n"+EnemyUnit_2Stats);
			TABase.appendText("--Enemy Weapon-- \n"+EnemyUnit_2Weapon);
			TABase.appendText("=========================");
			TABase.appendText("=========================");
			TABase.appendText("Enemy Status");
			TABase.appendText("--Enemy Stats-- \n"+EnemyUnitStats);
			TABase.appendText("--Enemy Weapon-- \n"+EnemyUnitWeapon);
			TABase.appendText("=========================");
		}
		else if(EnemyParty == 1)
		{
			TABase.appendText("=========================");
			TABase.appendText("Enemy Status");
			TABase.appendText("--Enemy Stats-- \n"+EnemyUnitStats);
			TABase.appendText("--Enemy Weapon-- \n"+EnemyUnitWeapon);
			TABase.appendText("=========================");
		}
		sleep();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void Xloc()
	{
		TF.clear();
		TABase.appendText("\nEnter X Length >> ");
		proceedB.setOnAction((e) ->
		{
			String x = TF.getText();
			customX = Integer.parseInt(x);
			MapMaker();
		});
	}
	public void Yloc()
	{
		TF.clear();
		TABase.appendText("\nEnter Y Length >> ");
		proceedB.setOnAction((e) ->
		{
			String y = TF.getText();
			customY = Integer.parseInt(y);
			MapMaker();
		});
	}
	public void MapMaker()
	{
		if(customX == 0)
		{
			Xloc();
		}
		else if(customY == 0)
		{
			Yloc();
		}
		else if(customX <= 5 || customY <= 5)
		{
			TABase.appendText("\nMap size too small ensure both values are larger then 5");
			customX = 0;
			customY = 0;	
			Xloc();
		}
		else
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
	//---------------------------------------------------------------------------------------------------------------------------------------
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
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void CoverMaker()
	{
		MakeCover = new Cover (0, 0, 3);
		MakeCover.CoverXMaker(customX);
		MakeCover.CoverYMaker(customY);
		MapCheck();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void RangeCheck()
	{
		//Checks player location and adds weapon range and if it equals the Enemy location the player is in range of the enemy the minus one is to only include the weapon range
		if((PlayerXLocation + PlayerWeapon.getRange()) == EnemyXLocation)
		{
			PlayerRange = true;
		}
		if((PlayerYLocation + PlayerWeapon.getRange()) == EnemyYLocation)
		{
			PlayerRange = true;
		}

		//Like the one above but for the enemy to get into range of the player
		if((EnemyXLocation + EnemyWeapon.getRange()) == PlayerXLocation)
		{
			EnemyRange = true;
		}
		if((EnemyYLocation + EnemyWeapon.getRange()) == PlayerYLocation)
		{
			EnemyRange = true;
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void MapCheck()
	{
		//Checks when the enemy spawn are on the edge of the map and they are sorted again if they are
		if(EnemyXLocation == baseX || EnemyXLocation == customX || EnemyYLocation == baseY || EnemyYLocation == customY)
		{
			Randomizer();
		}
		else
		{
			MapMaker();
		}

		//Checks cover piece and if it has reached edge of map
		if(MakeCover.getCoverXLocation() == baseX || MakeCover.getCoverXLocation() == customX || MakeCover.getCoverYLocation() == baseY || MakeCover.
				getCoverYLocation() == customY)
		{
			CoverMaker();
		}
		else
		{
			MapMaker();
		}

		//Checks if the player reaches the limits of the map they are moved back one and they are sorted again
		if(PlayerXLocation == baseX || PlayerXLocation == customX || PlayerYLocation == baseY || PlayerYLocation == customY)
		{
			PlayerXLocation = PlayerXLocation + 1;
			Randomizer();
		}
		else
		{
			MapMaker();
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
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
			TABase.appendText("You cannot go any futher right");
			PlayerXLocation = PlayerXLocation + 1;
			Move();
		}
		else if(PlayerXLocation == customX)
		{
			TABase.appendText("You cannot go any futher left");
			PlayerXLocation = PlayerXLocation - 1;
			Move();
		}
		else if(PlayerYLocation == baseY)
		{
			TABase.appendText("You cannot go any futher down");
			PlayerYLocation = PlayerYLocation + 1;
			Move();
		}
		else if(PlayerYLocation == customY)
		{
			TABase.appendText("You cannot go any futher up");
			PlayerYLocation = PlayerYLocation - 1;
			Move();
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
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
				TABase.appendText("You move up");
				//Checks if player moves towards edge of map
				BoundryCheck();
			}
			else if(TF.getText().equalsIgnoreCase("move down"))
			{
				PlayerYLocation = PlayerYLocation - 1;
				TABase.appendText("You move down");
				//Checks if player moves towards edge of map
				BoundryCheck();
			}
			else if(TF.getText().equalsIgnoreCase("move left"))
			{
				PlayerXLocation = PlayerXLocation + 1;
				TABase.appendText("You move left");
				//Checks if player moves towards edge of map
				BoundryCheck();
			}
			else if(TF.getText().equalsIgnoreCase("move right"))
			{
				PlayerXLocation = PlayerXLocation - 1;
				TABase.appendText("You move right");
				//Checks if player moves towards edge of map
				BoundryCheck();
			}
		});
		if(PlayerYLocation == MakeCover.getCoverYLocation() && PlayerXLocation == MakeCover.getCoverXLocation())
		{
			coverType = 1;
			//Gives the int a value of 1 as it is the player that is in cover
		}
		else
		{
			coverType = 0;
		}
		//Range check is used at the end of a movement
		RangeCheck();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void Enemymove()
	{
		//Enemy moves in a random direction, will 'bounce' if goes near a border and move another direction, if it is on the same X or Y value of a cover piece it shall move towards it
		TALog.appendText("The enemy moves...");
		if(EnemyYLocation == MakeCover.getCoverYLocation() && EnemyXLocation <= MakeCover.getCoverXLocation())
		{
			EnemyXLocation = EnemyXLocation - 1;
		}
		else if(EnemyYLocation == MakeCover.getCoverYLocation() && EnemyXLocation >= MakeCover.getCoverXLocation())
		{
			EnemyXLocation = EnemyXLocation + 1;
		}
		else if(EnemyXLocation == MakeCover.getCoverXLocation() && EnemyYLocation <= MakeCover.getCoverYLocation())
		{
			EnemyYLocation = EnemyYLocation - 1;
		}
		else if(EnemyXLocation == MakeCover.getCoverXLocation() && EnemyYLocation >= MakeCover.getCoverYLocation())
		{
			EnemyYLocation = EnemyYLocation + 1;
		}
		else if(EnemyYLocation == MakeCover.getCoverYLocation() && EnemyXLocation == MakeCover.getCoverXLocation())
		{
			coverType = 1;
			//Gives the int a value of 1 as it is the enemy that is in cover
		}
		else
		{
			TABase.appendText("No cover in location");
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
		//Checks if enemy moves towards edge of map
		BoundryCheck();
		//Range check is used at the end of a movement
		RangeCheck();
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void locationShow()
	{
		//Shows location of both player and enemy as well as a map
		TALocation.appendText("\nPlayer: Y-["+PlayerYLocation+"] X-["+PlayerXLocation+"]");
		TALocation.appendText("\nEnemy: Y-["+EnemyYLocation+"] X-["+EnemyXLocation+"]\n");
		/*
		int x = 0;
		int y = 0;
		for (x=0; x <= customX; x++) 
		{
			for(y=0; y <= customY; y++)
			{
				if(PlayerYLocation == y && PlayerXLocation == x)
				{
					TALocation.appendText(" + ");
				}
				if(EnemyYLocation == y && EnemyXLocation == x)
				{
					TALocation.appendText(" - ");
				}
				if(MakeCover.getCoverXLocation() == x && MakeCover.getCoverYLocation() == y)
				{
					TALocation.appendText("[");
					if(PlayerYLocation == MakeCover.getCoverYLocation() && PlayerXLocation == MakeCover.getCoverXLocation())
					{
						TALocation.appendText("+");
					}
					else if(EnemyYLocation == MakeCover.getCoverYLocation() && EnemyXLocation == MakeCover.getCoverXLocation())
					{
						TALocation.appendText("-");
					}
					else
					{
						TALocation.appendText(" ");
					}
					TALocation.appendText("]");
				}
				else
				{
					TALocation.appendText(" * ");
				}
			}
			TALocation.appendText("\n ");
		}
		 */
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
	public void sleep()
	{
		try
		{
			Thread.sleep(500);
		}
		catch(InterruptedException e)
		{
			System.err.println(e.getMessage());
		}
	}
	//---------------------------------------------------------------------------------------------------------------------------------------
}
