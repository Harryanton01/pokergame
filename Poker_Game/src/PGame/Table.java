package PGame;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class Table {
	public ArrayList<Card> hand=new ArrayList<Card>();
	private ObservableList<Node> cards;
	
	public Table(ObservableList<Node> cards){
		this.hand=new ArrayList<Card>();
		this.cards=cards;
	}
	public void drawFrom(Deck d){
		Card no1=d.draw();
		cards.add(no1);
		hand.add(no1);
	}
	public void displayTable(){
		for(Card c:hand){
			System.out.println(c.toString());
		}
	}
	public void clearTable(){
		cards.clear();
		hand.clear();
	}
}
