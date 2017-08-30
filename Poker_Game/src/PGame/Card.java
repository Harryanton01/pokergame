package PGame;

import java.io.File;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Card extends Parent implements Comparable<Card>{
	enum Suit{
		hearts, diamonds, clubs, spades;
	}
	enum Rank{
		two(2), three(3), four(4), five(5), six(6), seven(7), eight(8), nine(9), ten(10), jack(11), queen(12), king(13), ace(14);
		
		final int value;
		private Rank(int value){
			this.value=value;
		}
	}
	public final Suit suit;
	public final Rank rank;
	public final int value;
	private Image image;
	ImageView view;
	Rectangle bg;
	Text text;
	public Card(Suit suit, Rank rank){
		this.suit=suit;
		this.rank=rank;
		this.value=rank.value;
		File file=new File("images/".concat(this.toString().toLowerCase()).concat(".png"));
		this.image=new Image(file.toURI().toString());
		view=new ImageView(this.image);
		view.setFitHeight(120);
		view.setFitWidth(80);
		text=new Text("");
		getChildren().addAll(text,view);
	}
	public void animateCard(){
		TranslateTransition tt=new TranslateTransition(Duration.millis(1000),view);
		tt.setFromX(5500);
        tt.setToX(150);
	    tt.play();
	}
	public int compareTo(Card c1){
		return Integer.compare(c1.value, this.value);
	}

	@Override
	public String toString(){
		return rank.toString()+suit.toString();
	}
}
