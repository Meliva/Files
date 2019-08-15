
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

public class Screen extends Application //extends BackProgram
{
	protected static TextArea TABase = new TextArea();//Main screen
	protected static TextField TF = new TextField();//Text field for what the player can enter
	protected static Button clearB = new Button("Clear");//Clear button clears all screens
	protected static Button proceedB = new Button("Proceed");//Button to go forward with anything that is entered into the screen
	protected static Button helpB = new Button("Help");//The help button
	protected static TextArea TALog = new TextArea();//The screen which shows combat things
	protected static TextArea TALocation = new TextArea();//The screen that shows the X and Y location of the player and enemy
	protected static File theFile = new File ("Results\\PlayerResults.txt"); //A file to where the results are released to

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
			}
		});
		helpB.setOnAction((e)->
		{
			Alert update = new Alert(Alert.AlertType.INFORMATION);
			update.setHeaderText(null);
			update.setContentText("The help information is in here");
			Optional<ButtonType> result = update.showAndWait();
			if(result.get() == ButtonType.OK)
			{

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
		HBox midArea2 = new HBox(clearB, helpB);
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
		boop();
	}
	public void boop()
	{
		BackProgram.start();
	}
}