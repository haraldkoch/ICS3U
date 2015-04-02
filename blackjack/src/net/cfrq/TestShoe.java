package net.cfrq;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestShoe {

	ShoeOfCards cardShoe = new ShoeOfCards();

	@Before
	public void setUp() {
		
	}

	@Test
	public void testUnshuffled() {
		for (int deck=0 ; deck<6 ; deck++) {
			for (int card = 0 ; card < 52 ; card++) {
				int deckCard = cardShoe.getCard();
				assertEquals(card, deckCard);
			}
		}
	}

}
