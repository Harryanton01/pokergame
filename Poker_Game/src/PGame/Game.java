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
	private Text playerText;
	private TextField coins;
	Text text=new Text("Player 1");
	private int turn;
	private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
	private SimpleBooleanProperty enable = new SimpleBooleanProperty(false);
	
	public void startGame(){
		turn=1;
		//enable.set(false);
		tablee.clearTable();
		deck=new Deck();
		deck.populate();
		deck.printList();
		deck.shuffle();
		p2.drawFrom(deck);
		p2.drawFrom(deck);
		p2.displayHand();
		tablee.drawFrom(deck);
		tablee.drawFrom(deck);
		tablee.drawFrom(deck);
	}
	private void drawplayer(){
		delay(100);
		p1.drawFrom(deck);
    	delay(100);
    	p1.drawFrom(deck);
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
        turn=0;
     
        p1=new Player("Harry", player1Cards.getChildren());
        p2=new Player("Luca",player2Cards.getChildren());
        tablee=new Table(tableBox.getChildren());
        leftVBox.setAlignment(Pos.TOP_CENTER);
        bottomVBox.setAlignment(Pos.CENTER);
        //leftVBox.getChildren().addAll(player1Cards);
        HBox lefth=new HBox(5);
        bottomVBox.getChildren().addAll(tableBox);
        Button betBT=new Button("BET");
        Button startBT=new Button("START");
        Button restartBT=new Button("FOLD");
        HBox buttonsBox=new HBox (10);
        playerText=new Text(p1.getName()+" chips: "+p1.chips);
        coins=new TextField();
        VBox chipt=new VBox(5);
        chipt.setAlignment(Pos.CENTER);
        chipt.getChildren().addAll(playerText, coins, betBT);
        chipt.setPadding(new Insets(0, -80, 0, 40));
        buttonsBox.getChildren().addAll(startBT, restartBT, chipt);
        lefth.setPadding(new Insets(0, 0, 0, 40));
        lefth.getChildren().addAll(buttonsBox,player1Cards);
        buttonBG.setArcHeight(70);
        buttonBG.setArcWidth(70);
        buttonsBox.setAlignment(Pos.CENTER);
       // restartBT.disableProperty().bind(playable);
       // startBT.disableProperty().bind(playable);
        //drawBT.disableProperty().bind(enable);
        //betBT.disableProperty().bind(playable.not());
        
        
        startBT.setOnAction(event->{
        		playerText.setText(p1.getName()+" chips: "+p1.chips);
        		//playable.set(true);
        		
        		startGame();
        		p2.evaluatehandWith(tablee);
        		drawplayer();
            	enable.set(true);
    			
        });
        betBT.setOnAction(event->{
        	int chips=0;
        	try{
        		chips=Integer.parseInt(coins.getText());
        		betb(chips);
        	}
        	catch(Exception e){
        		playerText.setText("Please input a valid number");
        	}
        
        });
        rootLayout.getChildren().addAll(new StackPane(table, leftVBox, bottomVBox),new StackPane(buttonBG,lefth));
        pane.getChildren().addAll(background, rootLayout);
        

        return pane;
	}
	private void betb(int chips){
		if(p1.bet(chips)&&p2.bet(chips)){
    		if(turn==4){
    			turn=1;
            	p1.evaluatehandWith(tablee);
            	p2.evaluatehandWith(tablee);
            	if(p1.compareTo(p2)){
            		playerText.setText("winner is: "+p1.getName());
            		p1.setChips(tablee.pot);
            	}
            	else{
            		playerText.setText("winner is: "+p2.getName());
            		p2.setChips(tablee.pot);
            	}
            	//delay(2000);
            	tablee.clearPot();
            	tablee.clearTable();
            	p1.clearHand();
            	p2.clearHand();
            	
            	playerText.setText(p1.getName()+" chips: "+p1.chips);
    		}
    		else{
    		turn++;
    		tablee.drawFrom(deck);
    		tablee.addtoPot(chips*2);
    		playerText.setText(p1.getName()+" chips: "+p1.chips);
    		}
		}
		else{
			playerText.setText("not enough chips");
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
