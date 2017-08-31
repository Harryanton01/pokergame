package PGame;
import java.util.*;
import javafx.scene.Node;
import javafx.collections.ObservableList;

public class Player {
	public ArrayList<Card> hand=new ArrayList<Card>();
	private ObservableList<Node> cards;
	private ArrayList<Card> handrank=new ArrayList<Card>();
	private String pname;
	public int pairVal;
	public int chips;
	private int rank;
	Card highcard;
	Card handhighcard;
	public Player(String name, ObservableList<Node> cards){
		this.cards=cards;
		this.pname=name;
		this.hand=new ArrayList<Card>();
		this.chips=100;
	}
	public boolean hasChips(int chips){
		int deduction=this.chips-chips;
		if(deduction<0){
			return false;
		}
		else{
			this.chips-=chips;
			return true;
		}
	}
	public void drawFrom(Deck d){
		Card no1=d.draw();
		hand.add(no1);
		cards.add(no1);
		System.out.println(no1.toString());
	}
	public void addChips(int i){
		chips+=i;
	}
	public void clearHand(){
		cards.clear();
		hand.clear();
	}
	public void displayHand(){
		System.out.println("--------------------------------------------------------------");
		System.out.println(pname);
		System.out.println("HAND");
		System.out.println("Cards: "+hand.get(0).toString()+" "+hand.get(1).toString());
		System.out.println("--------------------------------------------------------------");
	}
	public String evaluatehandWith(Table table){
		ArrayList<Card> newList=new ArrayList<Card>(this.hand);
		newList.addAll(table.hand);
		Collections.sort(newList);
		if(fullHouse(newList)){
			setRank(9);
			return pname+" has a fullhouse";
		}
		else if(straightFlush(newList)){
			setRank(8);
			return pname+" has a straight flush";
		}
		else if(fourofaKind(newList)){
			setRank(7);
			return pname+" has four of a kind of "+pairVal+"s";
		}
		/*else if(flush(newList)){
			System.out.println(pname+" has a flush");
			setRank(6);
		}*/
		else if(straight(newList)){
			setRank(5);
			return pname+" has a straight";
		}
		else if(threeofaKind(newList)){
			setRank(4);
			return pname+" has three of a kind of "+pairVal+"s";
		}
		else if(twoPair(newList)){
			setRank(3);
			return pname+" has two pairs";
		}
		else if(onePair(newList)){
			setRank(2);
			return pname+" has one pair of "+pairVal+"s";
		}
		else{
			findHighcard(newList);
			findHandHighcard();
			setRank(1);
			return pname+" has a high card of "+highcard.suit+" "+highcard.value;
		}
	}
	public boolean compareTo(Player p){
		if(this.rank>p.rank){
			System.out.println(this.getName()+" WINS!!");
			return true;
		}
		else if(this.rank==p.rank && this.rank==1){
			if(this.gethighcard().value>p.gethighcard().value){
				System.out.println(this.getName()+" WINS!!");
				return true;
			}
			else{
				System.out.println("draw between "+pname+" "+p.getName());
				System.out.println("high card: "+p.highcard.value+" "+this.highcard.value);
				System.out.println(p.getName()+" WINS!!");
				return false;
			}
		}
		else if(this.rank==p.rank && this.rank>1){
			if(this.pairVal>p.pairVal){
				System.out.println(this.getName()+" WINS!!");
				return true;
			}
			else if(this.pairVal==p.pairVal){
				System.out.println("Draw!");
				return false;
			}
			else{
				System.out.println("draw between "+pname+" "+p.getName());
				System.out.println("high card: "+p.highcard.rank+" "+this.highcard.rank);
				System.out.println(p.getName()+" WINS!!");
				return false;
			}
		}
		else{
			System.out.println(p.getName()+" WINS!!");
			return false;
		}
	}
	public String getName(){
		return pname;
	}
	private void setRank(int handVal){
		rank=handVal;
	}
	public int getRank(){
		return rank;
	}
	public boolean onePair(ArrayList<Card> hand){
		int repeats=1;
		boolean bool=false;
		outer:for(int i=0;i<hand.size();i++){
			repeats=1;
			for(int j=1;j<hand.size();j++){
				if(hand.get(i).value == hand.get(j).value && !hand.get(i).suit.equals(hand.get(j).suit)){
					repeats++;
					if(repeats==2){
						if(!rankContains(hand.get(i))){
							handrank.add(hand.get(i));
						}
						if(!rankContains(hand.get(j))){
							handrank.add(hand.get(j));
						}
						pairVal=hand.get(i).value;
						bool=true;
						System.out.println("pair: "+hand.get(i).suit+" "+hand.get(i).rank+", "+hand.get(j).suit+" "+hand.get(j).rank);
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
				if(hand.get(i).value == hand.get(j).value && !hand.get(i).suit.equals(hand.get(j).suit) && !list.contains(hand.get(i).value)){
					repeats++;
					if(repeats==2){
						if(!rankContains(hand.get(i))){
							handrank.add(hand.get(i));
						}
						if(!rankContains(hand.get(j))){
							handrank.add(hand.get(j));
						}
						list.add(hand.get(i).value);
						repeats=1;
						repeatsNo++;
						if(repeatsNo==2){
							bool=true;
							break outer;
							}
						}
					}
				}
			}
		return bool;
	}
	private boolean rankContains(Card c){
		return handrank.contains(c) && handrank.size()<=5;
	}
	public boolean threeofaKind(ArrayList<Card> hand){
		int repeats=1;
		boolean bool=false;
		outer:
			for(int i=0;i<hand.size();i++){
				repeats=1;
				for(int j=1;j<hand.size();j++){
					if(hand.get(i).value == hand.get(j).value && !hand.get(i).suit.equals(hand.get(j).suit)){
						repeats++;
						if(!rankContains(hand.get(i))){
							handrank.add(hand.get(i));
						}
						if(!rankContains(hand.get(j))){
							handrank.add(hand.get(j));
						}
						if(repeats==3){
							
							pairVal=hand.get(i).value;
							bool=true;
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
				if(hand.get(i).value == hand.get(j).value && !hand.get(i).suit.equals(hand.get(j).suit)){
					repeats++;
					if(repeats==4){
						if(!rankContains(hand.get(i))){
							handrank.add(hand.get(i));
						}
						if(!rankContains(hand.get(j))){
							handrank.add(hand.get(j));
						}
						pairVal=hand.get(i).value;
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
			if(hand.get(i).value-hand.get(i+1).value==1){
				//handrank.add(hand.get(i));
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

	/*private boolean flush(ArrayList<Card> hand){
		int clubs=0;
		int hearts=0;
		int diamonds=0;
		int spades=0;
		for(Card c:hand){
			switch(c.suit){
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
	}*/
	
	private boolean straightFlush(ArrayList<Card> hand){
		//return flush(hand) && straight(hand);
		return false;
	}
	
	private boolean fullHouse(ArrayList<Card> hand){
		int repeats=1;
		boolean bool=false;
		if(threeofaKind(hand)){
			outer:for(int i=0;i<hand.size();i++){
				repeats=1;
				for(int j=1;j<hand.size();j++){
					if(hand.get(i).value == hand.get(j).value && !hand.get(i).suit.equals(hand.get(j).suit) && hand.get(i).value!=pairVal){
							repeats++;
							if(repeats==2){
								if(!rankContains(hand.get(i))){
									handrank.add(hand.get(i));
								}
								if(!rankContains(hand.get(j))){
									handrank.add(hand.get(j));
								}
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
	private void findHighcard(ArrayList<Card> hand){
		highcard=hand.get(0);
		handrank.add(hand.get(0));
	}
	private Card gethighcard(){
		return highcard;
	}
	/*private Card gethandhighcard(){
		return handhighcard;
	}*/
	private void findHandHighcard(){
		Collections.sort(hand);
		handhighcard=hand.get(0);
	}
}
