package PGame;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application{
	private Player p1;
	private Player p2;
	private Table tablee;
	private Deck deck;
	private HBox player1Cards = new HBox(20);
	private HBox tableBox = new HBox(20);
	private HBox player2Cards = new HBox(20);
	Text text=new Text("Player 1");
	private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
	private SimpleBooleanProperty enable = new SimpleBooleanProperty(false);
	
	public void startGame(){
		playable.set(true);
		enable.set(false);
		tablee.clearTable();
		p1.clearHand();
		p2=new Player("Luca",player2Cards.getChildren());
		deck=new Deck();
		deck.populate();
		deck.printList();
		deck.shuffle();
		p2.drawFrom(deck);
		p2.displayHand();
		tablee.drawFrom(deck);
		delay(100);
		tablee.drawFrom(deck);
		delay(100);
		tablee.drawFrom(deck);
		delay(100);
		tablee.drawFrom(deck);
		delay(100);
		tablee.drawFrom(deck);

	}
	private void delay(int milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void endGame(){
		p1.compareTo(p2);
	}
	private Parent createContent() {
        Pane pane = new Pane();
        Region background = new Region();
        VBox rootLayout = new VBox(10);
        Rectangle table = new Rectangle(800, 400);
        table.setArcHeight(70);
        table.setArcWidth(70);
        table.setFill(Color.GREEN);
        Rectangle buttonBG=new Rectangle(800,160);
        buttonBG.setFill(Color.DARKRED);
        
        VBox leftVBox = new VBox(50);
        VBox bottomVBox=new VBox(100);
        p1=new Player("Harry", player1Cards.getChildren());
        tablee=new Table(tableBox.getChildren());
        leftVBox.setAlignment(Pos.TOP_CENTER);
        bottomVBox.setAlignment(Pos.CENTER);
        //leftVBox.getChildren().addAll(player1Cards);
        HBox lefth=new HBox(5);
        bottomVBox.getChildren().addAll(tableBox);
        Button betBT=new Button("BET");
        Button startBT=new Button("START");
        Button drawBT=new Button("DRAW");
        Button restartBT=new Button("RESTART");
        HBox buttonsBox=new HBox (10);
        buttonsBox.getChildren().addAll(startBT, drawBT, betBT);
        lefth.setPadding(new Insets(0, 0, 0, 40));
        lefth.getChildren().addAll(buttonsBox,player1Cards);
        buttonBG.setArcHeight(70);
        buttonBG.setArcWidth(70);
        buttonsBox.setAlignment(Pos.CENTER);
        restartBT.disableProperty().bind(playable);
        startBT.disableProperty().bind(playable);
        drawBT.disableProperty().bind(enable);
        betBT.disableProperty().bind(playable.not());
        
        drawBT.setOnAction(event->{
        	p1.drawFrom(deck);
        	delay(100);
        	p1.drawFrom(deck);
        	p2.evaluatehandWith(tablee);
        	enable.set(true);
        	text.textProperty().bind(new SimpleStringProperty(p1.evaluatehandWith(tablee)));
        });
        
        startBT.setOnAction(event->{
        	startGame();
        });
        betBT.setOnAction(event->{
        	playable.set(false);
        	tablee.clearTable();
        	endGame();

        });
        rootLayout.getChildren().addAll(new StackPane(table, leftVBox, bottomVBox),new StackPane(buttonBG,lefth));
        pane.getChildren().addAll(background, rootLayout);
        

        return pane;
	}
	   @Override
	    public void start(Stage primaryStage) throws Exception {
	        primaryStage.setScene(new Scene(createContent()));
	        primaryStage.setWidth(800);
	        primaryStage.setHeight(600);
	        primaryStage.setResizable(false);
	        primaryStage.setTitle("Poker Game");
	        primaryStage.show();

	    }
	    public static void main(String[] args) {
	        launch(args);
	        
	    }
}
