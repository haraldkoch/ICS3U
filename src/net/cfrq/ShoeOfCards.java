package net.cfrq;

/* we model a shoe of cards as an array of cards. Each deck of cards has 52 cards, and you can set the number of decks. */

public class ShoeOfCards {

	int numberOfDecks;
	int [] cards;
	private int currentCard;
	private int endOfShoe;

	public ShoeOfCards() {
		setNumberOfDecks(6);
	}

	public void setNumberOfDecks(int decks) {
		numberOfDecks = decks;
		cards = new int[52*decks];
		for (int deck = 0 ; deck < decks ; deck++) {
			for (int card = 0 ; card < 52 ; card ++) {
				cards[deck*52+card] = card;
			}
		}
	}
	
	public void shuffle() {
		// simplest shuffle on the planet
		for(int count = 0 ; count < numberOfDecks*1000 ; count++) {
			int firstCard = (int) (Math.random() * 52 * numberOfDecks);
			int secondCard = (int) (Math.random() * 52 * numberOfDecks);
			
			int tempCard = cards[firstCard];
			cards[firstCard] = cards[secondCard];
			cards[secondCard] = tempCard;
		}

		currentCard = 0;
		// put the end card somewhere in the last deck. This is an arbitrary choice.
		endOfShoe = ((int) (Math.random() * 52)) + (numberOfDecks - 1) * 52;
	}

	public int getCard() {
		if (currentCard >= endOfShoe) {
			shuffle();
		}
		return cards[currentCard++];
	}
}
