

public class Main {

		public static void main(String[] args) {
			Deck deck=new Deck();
			deck.populate();
			//deck.printList();
			deck.shuffle();
			//deck.printList();
			Game g=new Game();
			g.startGame();
		}

	}


