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
	private Player player;
	private Player opponent;
	private Table table;
	private Deck deck;
	
	private HBox playerCards = new HBox(20);
	private HBox tableCards = new HBox(20);
	private HBox opponentCards = new HBox(20);
	private Text playerText = new Text("");
	private TextField coinField;
	Text text;
	private int turn;
	
	private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
	private SimpleBooleanProperty enable = new SimpleBooleanProperty(false);
	
	public void startGame(){
		deck=new Deck();
		player=new Player("You", playerCards.getChildren());
        opponent=new Player("Opponent",opponentCards.getChildren());
        table=new Table(tableCards.getChildren());
        playerText.setText(player.getName()+": "+player.chips);
		playerDraw();
		opponent.drawFrom(deck);
		opponent.drawFrom(deck);
	}
	private void nextGame() {
		delay(5000);
		deck=new Deck();
		table=new Table(tableCards.getChildren());
		playerText.setText(player.getName()+": "+player.chips);
		playerDraw();
		opponent.drawFrom(deck);
		opponent.drawFrom(deck);
	}
	private void playerDraw(){
		delay(100);
		player.drawFrom(deck);
    	delay(100);
    	player.drawFrom(deck);
	}
	private void tableDraw() {
		delay(100);
		table.drawFrom(deck);
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
		turn=1;
		int pot=table.pot;
		opponent.displayHand();
		player.evaluatehandWith(table);
		opponent.evaluatehandWith(table);
		if(player.compareTo(opponent)) {
			player.addChips(pot);
		}
		else {
			opponent.addChips(pot);
		}
		table.clearPot();
		table.clearTable();
		player.clearHand();
		opponent.displayHand();
		opponent.clearHand();
		
		nextGame();
	}
	private Parent createContent() {
        Pane pane = new Pane();
        Region background = new Region();
        VBox rootLayout = new VBox(10);
        
        Rectangle tableBG = new Rectangle(800, 400);
        tableBG.setArcHeight(70);
        tableBG.setArcWidth(70);
        tableBG.setFill(Color.GREEN);
        
        Rectangle buttonBG=new Rectangle(800,160);
        buttonBG.setArcHeight(70);
        buttonBG.setArcWidth(70);
        buttonBG.setFill(Color.DARKRED);
       
        VBox handVBox = new VBox(50);
        handVBox.setAlignment(Pos.TOP_CENTER);
        handVBox.getChildren().addAll(playerCards);
        
        VBox tableVBox=new VBox(10);
        tableVBox.setAlignment(Pos.CENTER);
        tableVBox.getChildren().addAll(tableCards);
        
        Button betBT=new Button("BET");
        betBT.setScaleX(1.2);
        betBT.setScaleY(1.2);
        Button startBT=new Button("START");
        Button restartBT=new Button("FOLD");
        
        VBox betSection=new VBox(15);
        coinField=new TextField("10");
        betSection.setAlignment(Pos.CENTER);
        betSection.getChildren().addAll(playerText, coinField, betBT);
        betSection.setPadding(new Insets(0, -60, 0, 40));
        
        HBox buttonsBox=new HBox (10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(startBT, restartBT, betSection);
        
        HBox bottomHBox=new HBox(5);
        bottomHBox.setPadding(new Insets(0, 0, 0, 40));
        bottomHBox.getChildren().addAll(buttonsBox,playerCards);
        
       // restartBT.disableProperty().bind(playable);
        
        startBT.setOnAction(event->{
        	startGame();	
        });
        betBT.setOnAction(event->{
        	betb();
        
        });
        
        rootLayout.getChildren().addAll(new StackPane(tableBG,handVBox, tableVBox),new StackPane(buttonBG,bottomHBox));
        pane.getChildren().addAll(background, rootLayout);
        return pane;
	}
	private void betb(){
		int chips=0;
		try {
			chips=Integer.parseInt(coinField.getText());
			if(player.hasChips(chips) && opponent.hasChips(chips)) {
				if(table.hand.size()==0) {
					turn++;
					table.addtoPot(chips*2);
					playerText.setText(player.getName()+": "+player.chips+" || chips in pot: "+table.pot);
					tableDraw();
					tableDraw();
					tableDraw();
				}
				else if(table.hand.size()==5) {
					endGame();
				}
				else {
					turn++;
					table.addtoPot(chips*2);
					playerText.setText(player.getName()+": "+player.chips+" || chips in pot: "+table.pot);
					tableDraw();
					System.out.println(turn);
				}
			}
			else{
				playerText.setText("not enough chips");
			}
		}
		catch(Exception e){
    		playerText.setText("Please input a valid number");
    	}

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
