package PGame;
import java.util.Collections;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

import java.util.Comparator;

public class Card extends Parent implements Comparable<Card>{
	enum Suit{
		hearts, diamonds, clubs, spades
	};
	enum Rank{
		two(2), three(3), four(4), five(5), six(6), seven(7), eight(8), nine(9), ten(10), jack(11), queen(12), king(13), ace(14);
		
		final int value;
		private Rank(int value){
			this.value=value;
		}
	};
	public final Suit suit;
	public final Rank rank;
	public final int value;
	
	public Card(Suit suit, Rank rank){
		this.suit=suit;
		this.rank=rank;
		this.value=rank.value;
		
		Rectangle bg=new Rectangle(80,120);
		bg.setFill(Color.WHITE);
		
		Text text=new Text(toString());
		text.setWrappingWidth(70);
		getChildren().add(new StackPane(bg, text));
	}
	public int compareTo(Card c1){
		return Integer.compare(c1.value, this.value);
	}
	@Override
	public String toString(){
		return rank.toString()+" of "+suit.toString();
	}
}
