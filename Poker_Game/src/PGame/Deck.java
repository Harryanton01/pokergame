package PGame;
import java.util.*;

import PGame.Card.Rank;
import PGame.Card.Suit;

public class Deck{
private int deck_size=52;
ArrayList<Card> deck_array=new ArrayList<Card>();

public Deck(){
	deck_array=new ArrayList<Card>();
	this.populate();
	this.shuffle();
}
public final void populate(){
	deck_array.clear();
	int i=0;
	for(Suit suit: Suit.values()){
		for(Rank rank: Rank.values()){
			deck_array.add(i, new Card(suit,rank));
		}
	}
}
public void shuffle(){
	Collections.shuffle(deck_array);
}
public void printList(){
	for(Card c: deck_array){
		System.out.println(c.toString());
	}
	
}
public Card draw(){
	Card pleb=deck_array.get(deck_size-1);
	pleb.animateCard();
	deck_array.remove(deck_size-1);
	deck_size--;
	return pleb;
}
}
