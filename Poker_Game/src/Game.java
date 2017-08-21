import java.util.*;
public class Game {
	private Player p1;
	private Deck deck;
	private ArrayList<Card> table=new ArrayList<Card>();
	public void startGame(){
		p1=new Player();
		deck=new Deck();
		deck.populate();
		deck.shuffle();
		p1.drawFrom(deck);
		p1.displayHand();
		for(int i=0; i<5; i++){
			table.add(deck.draw());
		}
		p1.compareto(table);
	}
}
