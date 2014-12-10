package net.cfrq;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BlackJack {

	static int getInput(String prompt, int lowest, int highest) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print(prompt);
			String nextLine = scanner.nextLine();
			int value = Integer.valueOf(nextLine);
			if (value >= lowest && value <= highest) {
				scanner.close();
				return value;
			}
		}
	}

	public static void main(String[] args) {

		String playerName = "";
		
		while (!playerName.equalsIgnoreCase("quit")) {
			// get locale
			// initialize bank
			int bank = 500;

			while (true /* keep betting */) {
				// get player bet
				int bet = getInput("Please enter an amount to bet: ", 1, bank);

				// initialize hand counters
				int playerAceCount = 0;
				int playerHandScore = 0;
				String playerHandString = "";
				int dealerAceCount = 0;
				String dealerHandString = "";

				// get and display dealer cards
				int dealerCard1 = dealACard();
				if (isAce(dealerCard1)) { dealerAceCount++; };
				int dealerCard2 = dealACard();
				if (isAce(dealerCard2)) { dealerAceCount++; };
				dealerHandString = "XX " + formatCard(dealerCard2);
				
				// get and display first two player cards
				int playerCard1 = dealACard();
				if (isAce(playerCard1)) { playerAceCount++; };
				int playerCard2 = dealACard();
				if (isAce(playerCard2)) { playerAceCount++; };
				playerHandString = formatCard(playerCard1) + " " + formatCard(playerCard2);

				playerHandScore = getCardScore(playerCard1) + getCardScore(playerCard2);

				System.out.println("Dealer: " + dealerHandString);
				System.out.println("Player: " + playerHandString + " ... " + playerHandScore);
				boolean anotherCard = true;

				while (anotherCard) {
					int resp = getInput("Choose an action (1) Hit (2) Stay (3) Double: ", 1, 3);
					if (resp == 1 || resp == 3) {
						if (resp == 3) {
							// player doubled; so double the bet and no more cards
							anotherCard = false;
							bet *= 2;
						}

						// get another card and add it to the player's hand
						int nextCard = dealACard();
						if (isAce(nextCard)) { playerAceCount++; }
						playerHandString += " " + formatCard(nextCard);
						playerHandScore += getCardScore(nextCard);

						// handle over 21 and aces
						if (playerHandScore > 21 && playerAceCount > 0) {
							playerAceCount--;
							playerHandScore -= 10;
						}

						System.out.println("Player: " + playerHandString + " ... " + playerHandScore);

						// player bust?
						if (playerHandScore > 21) {
							System.out.println("you've gone bust!");
							anotherCard = false;
						}
					} else if (resp == 2) {
						// player stay
						anotherCard = false;
					}
				}
				while (true /* dealer hand */) {
					// get dealer card
					// if dealer bust then done
					// if dealer win then done
					// if dealer > 16 then done
				}
			}
		}
		
	}

	private static int getCardScore(int card) {
		int rank = getRank(card);
		int value;
		if (rank == 1) {
			value = 11;
		}
		else if (rank >= 10) {
			value = 10;
		}
		else {
			value = rank;
		}

		return value;
	}

	private static int dealACard() {
		return (int)(Math.random()*52);
	}

	private static boolean isAce(int card) {
		return getRank(card) == 1;
	}

	private static int getRank(int card) {
		return (card % 13) + 1;
	}

	private static String formatCard(int card) {
		String [] suits = { "C", "D", "H", "S" };
		
		int rank = getRank(card);

		String cardAsString;
		switch (rank) {
		case 1:
			cardAsString = "A";
			break;
		case 11:
			cardAsString = "J";
			break;
		case 12:
			cardAsString = "Q";
			break;
		case 13:
			cardAsString = "K";
			break;
		default:
			cardAsString = String.valueOf(rank);
			break;
		}

		return cardAsString + suits[card/13];
	}
}
