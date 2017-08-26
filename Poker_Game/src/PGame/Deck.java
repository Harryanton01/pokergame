import java.util.*;
public class Deck{
private int deck_size=52;
ArrayList<Card> deck_array=new ArrayList<Card>();
private static String[] suit_array=new String[] {"clubs", "hearts", "spades", "diamonds"};
private static String[] value_array=new String[] {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

public Deck(){
	deck_array=new ArrayList<Card>();
}
public void populate(){
	deck_array.clear();
	for(int i=0;i<4;i++){
		for(int j=0;j<13;j++){
			deck_array.add(new Card(suit_array[i],value_array[j]));
		}
	}
}
public void shuffle(){
	Collections.shuffle(deck_array);
}
public void printList(){
	for(int k=0;k<deck_size;k++){
		String n=deck_array.get(k).getSuit();
		String l=deck_array.get(k).getValue();
		System.out.println("Suit: "+n+" Value: "+l);
	}
	System.out.println("--------------------------------------------------------------");
}
public Card draw(){
	Card pleb=deck_array.get(deck_size-1);
	deck_array.remove(deck_size-1);
	deck_size--;
	System.out.println("card drawn: "+pleb.getSuit()+" "+pleb.getValue());
	return pleb;
}
}
