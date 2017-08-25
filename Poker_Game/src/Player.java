import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Player {
	private ArrayList<Card> hand=new ArrayList<Card>();
	private int pairVal;
	public int chips;
	public Player(){
		hand=new ArrayList<Card>();
		this.chips=0;
	}
	public void drawFrom(Deck d){
		hand.add(d.draw());
		hand.add(d.draw());
	}
	public void setChips(int i){
		chips+=i;
	}
	public void clearHand(){
		hand.clear();
	}
	public void displayHand(){
		System.out.println("--------------------------------------------------------------");
		System.out.println("HAND");
		System.out.println("First card: "+hand.get(0).getValue()+" of "+hand.get(0).getSuit()+" rank: "+hand.get(0).getRank());
		System.out.println("Second card: "+hand.get(1).getValue()+" of "+hand.get(1).getSuit()+" rank: "+hand.get(1).getRank());
		System.out.println("--------------------------------------------------------------");
		System.out.println("CARDS ON TABLE");
	}
	public void compareto(ArrayList<Card> table){
		ArrayList<Card> newList=new ArrayList<Card>(this.hand);
		newList.addAll(table);
		Collections.sort(newList);
		if(fullHouse(newList)){
			System.out.println("fullhouse");
		}
		else if(straightFlush(newList)){
			System.out.println("straight flush");
		}
		else if(fourofaKind(newList)){
			System.out.println("four of a kind");
		}
		else if(flush(newList)){
			System.out.println("flush");
		}
		else if(straight(newList)){
			System.out.println("straight");
		}
		else if(threeofaKind(newList)){
			System.out.println("three of a kind");
		}
		else if(twoPair(newList)){
			System.out.println("two pair");
		}
		else if(onePair(newList)){
			System.out.println("one pair");
		}
		else{
			Card hc=getHighcard(newList);
			Card hhc=getHandHighcard();
			System.out.println("high card: "+hc.getSuit()+" "+hc.getValue()+" hand high card: "+hhc.getSuit()+" "+hhc.getValue());
		}
		
	}
	public boolean onePair(ArrayList<Card> hand){
		int repeats=1;
		boolean bool=false;
		outer:for(int i=0;i<hand.size();i++){
			repeats=1;
			for(int j=1;j<hand.size();j++){
				if(hand.get(i).getRank() == hand.get(j).getRank() && !hand.get(i).getSuit().equals(hand.get(j).getSuit())){
						repeats++;
						if(repeats==2){
							bool=true;
							System.out.println("pair: "+hand.get(i).getSuit()+" "+hand.get(i).getValue()+", "+hand.get(j).getSuit()+" "+hand.get(j).getValue());
							break outer;
							}
						}
				}
			}
		return bool;
	    
	}
	private boolean twoPair(ArrayList<Card> hand){
		int repeats=1;
		int repeatsNo=0;
		ArrayList<Integer> list=new ArrayList<Integer>();
		boolean bool=false;
		outer:for(int i=0;i<hand.size();i++){
			repeats=1;
			for(int j=1;j<hand.size();j++){
				if(hand.get(i).getRank() == hand.get(j).getRank() && !hand.get(i).getSuit().equals(hand.get(j).getSuit()) && !list.contains(hand.get(i).getRank())){
					repeats++;
					if(repeats==2){
						list.add(hand.get(i).getRank());
						repeats=1;
						String fsuit=hand.get(i).getSuit();
						String fvalue=hand.get(i).getValue();
						String f2suit=hand.get(j).getSuit();
						String f2value=hand.get(j).getValue();
						System.out.println(fsuit+fvalue+f2value);
						repeatsNo++;
						if(repeatsNo==2){
							bool=true;
							String isuit=hand.get(i).getSuit();
							String ivalue=hand.get(i).getValue();
							String ksuit=hand.get(j).getSuit();
							String kvalue=hand.get(j).getValue();
							System.out.println("--------------------------------------------------------------");
							System.out.println(isuit+" "+ivalue+" is a pair with "+ksuit+" "+kvalue+" and "+fsuit+" "+fvalue+" is a pair with "+f2suit+" "+f2value);
							break outer;
							}
						}
					}
				}
			}
		return bool;
	}
	public boolean threeofaKind(ArrayList<Card> hand){
		int repeats=1;
		boolean bool=false;
		outer:
			for(int i=0;i<hand.size();i++){
				repeats=1;
				for(int j=1;j<hand.size();j++){
					if(hand.get(i).getRank() == hand.get(j).getRank() && !hand.get(i).getSuit().equals(hand.get(j).getSuit())){
						repeats++;
						if(repeats==3){
							pairVal=hand.get(i).getRank();
							bool=true;
							String isuit=hand.get(i).getSuit();
							String ivalue=hand.get(i).getValue();
							System.out.println("--------------------------------------------------------------");
							System.out.println(isuit+" "+ivalue+" three of a kind");
							break outer;
							}
						}
					}
				}
		return bool;
	}
	public boolean fourofaKind(ArrayList<Card> hand){
		int repeats=1;
		boolean bool=false;
		outer:for(int i=0;i<hand.size();i++){
			repeats=1;
			for(int j=1;j<hand.size();j++){
				if(hand.get(i).getRank() == hand.get(j).getRank() && !hand.get(i).getSuit().equals(hand.get(j).getSuit())){
						repeats++;
						if(repeats==4){
							bool=true;
							String isuit=hand.get(i).getSuit();
							String ivalue=hand.get(i).getValue();
							System.out.println(isuit+" "+ivalue+" four of a kind");
							System.out.println("--------------------------------------------------------------");
							break outer;
							}
						}
				}
			}
		return bool;
	}
	private boolean straight(ArrayList<Card> hand){
		int straight=0;
		boolean bool=false;
		int i=0;
		while(i<hand.size()-1 && !bool){
			if(hand.get(i).getRank()-hand.get(i+1).getRank()==1){
				straight++;
				if(straight==6){
					bool=true;
				}
				else{
					i++;
				}
			}
			else{
				i++;
			}
		}
		return bool;
	}

	private boolean flush(ArrayList<Card> hand){
		int clubs=0;
		int hearts=0;
		int diamonds=0;
		int spades=0;
		for(Card c:hand){
			switch(c.getSuit()){
			case "clubs":
				clubs++;
				break;
			case "hearts":
				hearts++;
				break;
			case "diamonds":
				diamonds++;
				break;
			case "spades":
				spades++;
				break;
			}
		}
		return(clubs >=5 || hearts >=5 || diamonds >=5 || spades >=5);
	}
	
	private boolean straightFlush(ArrayList<Card> hand){
		return flush(hand) && straight(hand);
	}
	
	private boolean fullHouse(ArrayList<Card> hand){
		int repeats=1;
		boolean bool=false;
		if(threeofaKind(hand)){
			outer:for(int i=0;i<hand.size();i++){
				repeats=1;
				for(int j=1;j<hand.size();j++){
					if(hand.get(i).getRank() == hand.get(j).getRank() && !hand.get(i).getSuit().equals(hand.get(j).getSuit()) && hand.get(i).getRank()!=pairVal){
							repeats++;
							if(repeats==2){
								bool=true;
								System.out.println("full house");
								break outer;
								}
							}
					}
				}
		}
		return bool;
	}
	private Card getHighcard(ArrayList<Card> hand){
		return hand.get(0);
	}
	private Card getHandHighcard(){
		Collections.sort(hand);
		return hand.get(0);
	}
}
