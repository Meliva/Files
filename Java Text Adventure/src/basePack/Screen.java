
package basePack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Screen extends Application //extends BackProgram
{
	protected static TextArea TABase = new TextArea();//Main screen
	protected static TextField TF = new TextField();//Text field for what the player can enter
	protected static Button proceedB = new Button("Proceed");//Button to go forward with anything that is entered into the screen
	private Button mainB = new Button("To main screen");//Exits to main screen
	protected static Button actExitB = new Button("Exit");//Used to exit the application
	protected static TextArea TALog = new TextArea();//The screen which shows combat things
	protected static TextArea TALocation = new TextArea();//The screen that shows the X and Y location of the player and enemy
	protected static File playerFile = new File ("Results\\PlayerResults.txt"); //A file to where the results are released to
	private Button resB = new Button("Apply resolution");// Used to apply resolution of the main screen
	private int selct = 0;

	public static void main(String[] args) throws IOException
	{
		if(!playerFile.exists())//Used to create the file within the location specified if it does not exist
		{
			playerFile.createNewFile(); 
		}
		Application.launch();
	}

	public void start(Stage stage) throws Exception 
	{
		build(stage);
		stage.setTitle("Text Adventure!");
		stage.hide();
		stage.setOnCloseRequest(e->
		{
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

		Stage stoge = new Stage();
		stoge.setTitle("Resultion");
		stoge.initOwner(stage);
		stoge.show();
		stoge.setWidth(800);
		stoge.setHeight(600);	
		stoge.setOnCloseRequest(e->
		{
			Platform.exit();
			System.exit(0);		
		});

		Label descLabel = new Label ("Note, only words shown on the screen will be accepted");
		Label resLabel = new Label ("Resultion Options");
		ToolBar toolB = new ToolBar();
		RadioButton radioB1 = new RadioButton("1280 x 720");
		RadioButton radioB2 = new RadioButton("1600 x 900");
		RadioButton radioB3 = new RadioButton("1680 x 1050");
		RadioButton radioB4 = new RadioButton("1920 x 1080");
		RadioButton FullB = new RadioButton("Full Screen");
		toolB.getItems().add(radioB1);
		toolB.getItems().add(new Separator());
		toolB.getItems().add(radioB2);
		toolB.getItems().add(new Separator());
		toolB.getItems().add(radioB3);
		toolB.getItems().add(new Separator());
		toolB.getItems().add(radioB4);
		ToolBar toolB2 = new ToolBar();
		toolB2.getItems().add(FullB);
		ToggleGroup group = new ToggleGroup();		
		radioB1.setToggleGroup(group);
		radioB2.setToggleGroup(group);
		radioB3.setToggleGroup(group);	
		radioB4.setToggleGroup(group);
		FullB.setToggleGroup(group);
		selct = 0;
		resB.setOnAction((e)->
		{
			if(radioB1.isSelected())
			{
				stage.setWidth(1280);
				stage.setHeight(720);
				selct = 1;
			}
			else if(radioB2.isSelected())
			{
				stage.setWidth(1600);
				stage.setHeight(900);
				selct = 1;
			}
			else if(radioB3.isSelected())
			{	
				stage.setWidth(1680);
				stage.setHeight(1050);
				selct = 1;
			}
			else if(radioB4.isSelected())
			{	
				stage.setWidth(1920);
				stage.setHeight(1080);
				selct = 1;
			}
			else if(FullB.isSelected())
			{
				stage.setFullScreen(true);
				selct = 1;
			}
			else if(!FullB.isSelected())
			{
				stage.setFullScreen(false);
			}
		});

		mainB.setOnAction((e)->
		{
			if(selct == 1)
			{
				stoge.close();
				stage.show();
				boop();	
			}
			else
			{
				Alert updoot = new Alert(Alert.AlertType.ERROR);
				updoot.setHeaderText(null);
				updoot.setContentText("No Resultion selected");
				Optional<ButtonType> resoot = updoot.showAndWait();
				if(resoot.get() == ButtonType.OK)
				{

				}
			}
		});

		HBox topTop = new HBox(resLabel,toolB2);
		topTop.setStyle("-fx-alignment: center;-fx-spacing:10");
		HBox top = new HBox(toolB);
		top.setStyle("-fx-alignment: center;-fx-spacing:10");
		HBox mid = new HBox(mainB,resB);
		mid.setStyle("-fx-alignment: center;-fx-spacing:10");
		HBox bot = new HBox(descLabel);
		bot.setStyle("-fx-alignment: center;-fx-spacing:10");
		VBox root = new VBox(topTop,top,mid,bot);
		root.setStyle("-fx-font-size: 20;"+"-fx-alignment: center;-fx-spacing:20");
		Scene scene = new Scene(root);
		stoge.setScene(scene);	

	}

	public void build(Stage stage) throws Exception 
	{	
		//Min and Max size of the TABase text area
		TABase.setMinHeight(300);
		TABase.setMaxHeight(300);
		TABase.setMinWidth(550);
		TABase.setMaxWidth(550);
		TABase.setEditable(false);
		//Min and Max size of the TALog text area
		TALog.setMinWidth(300);
		TALog.setMaxWidth(300);
		TALog.setMinHeight(300);
		TALog.setMaxHeight(300);
		TALog.setEditable(false);
		TALog.setVisible(false);
		Label LogLabel = new Label ("Combat Log");
		//Min and Max size of the TALocation text area
		TALocation.setMinWidth(300);
		TALocation.setMaxWidth(300);
		TALocation.setMinHeight(300);
		TALocation.setMaxHeight(300);
		TALocation.setEditable(false);
		TALocation.setVisible(false);

		actExitB.setOnAction((e)->
		{
			Alert update = new Alert(Alert.AlertType.CONFIRMATION);
			update.setHeaderText(null);
			update.setContentText("Exit the application?");
			Optional<ButtonType> result = update.showAndWait();
			if(result.get() == ButtonType.OK)
			{
				Platform.exit();
				System.exit(0);			
			}
		});

		Label LocationLabel = new Label ("Location");

		Label L = new Label("Input Here->");
		TF.setMaxWidth(100);
		TF.setMinWidth(100);
		//Section of smaller areas divided to give a good amount of spacing
		//Top Section
		VBox topLeftArea = new VBox(LocationLabel,TALocation);
		topLeftArea.setStyle("-fx-alignment: center;-fx-spacing:20");
		HBox topMidArea = new HBox(TABase);
		topMidArea.setStyle("-fx-alignment: center;-fx-spacing:20");
		VBox topRightArea = new VBox(LogLabel,TALog);
		topRightArea.setStyle("-fx-alignment: center;-fx-spacing:20");
		//Mid Section
		HBox midArea1 = new HBox(L,TF,proceedB);
		midArea1.setStyle("-fx-alignment: center;-fx-spacing:30");
		HBox midArea2 = new HBox(actExitB);
		midArea2.setStyle("-fx-alignment: center;-fx-spacing:10");
		//Grouping of smaller areas into one bigger area
		HBox topArea = new HBox(topLeftArea,topMidArea,topRightArea);
		topArea.setStyle("-fx-alignment: center;-fx-spacing:20");
		HBox midArea = new HBox(midArea1,midArea2);
		midArea.setStyle("-fx-alignment: center;-fx-spacing:120");
		VBox root = new VBox(topArea,midArea);
		root.setStyle("-fx-font-size: 20;"+"-fx-alignment: center;-fx-spacing:20");
		Scene scene = new Scene(root);
		stage.setScene(scene);	
	}
	public void boop()
	{
		BackProgram.start();
	}
}