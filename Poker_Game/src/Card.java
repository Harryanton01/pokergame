import java.util.Collections;
import java.util.Comparator;

public class Card implements Comparable<Card>{
	private String suit;
	private String value;
	private int rank;
	public void setRank(){
		switch(value){
		case "ace": 
			rank=14;
			break;
		case "2": 
			rank=2;
			break;
		case "3":
			rank=3;
			break;
		case "4": 
			rank=4;
			break;
		case "5": 
			rank=5;
			break;
		case "6": 
			rank=6;
			break;
		case "7": 
			rank=7;
			break;
		case "8": 
			rank=8;
			break;
		case "9": 
			rank=9;
			break;
		case "10": 
			rank=10;
			break;
		case "jack": 
			rank=11;
			break;
		case "queen": 
			rank=12;
			break;
		case "king": 
			rank=13;
			break;
		default:
			System.out.println("ranks were not set!");
		}
		
	}
	public int getRank(){
		return rank;
	}
	public Card(String suit, String value){
		this.suit=suit;
		this.value=value;
		this.setRank();
	}
	public void setSuit(String p){
		suit=p;
	}
	public void setValue(String p){
		value=p;
	}
	public String getSuit(){
		return suit;
	}
	public String getValue(){
		return value;
	}
	public int compareTo(Card c1){
		return Integer.compare(c1.getRank(), this.getRank());
	}
	
}
