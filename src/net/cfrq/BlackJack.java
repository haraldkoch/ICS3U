package net.cfrq;

import java.util.Scanner;

/**
 * BlackJack - an example of a simple BlackJack program that does not use
 * Objects or Collections (including arrays).
 * 
 * Yes this is ugly code. I wrote it at the same time as my daughter while
 * she was working on this as an assignment in her computer science course.
 * 
 * @author Harald Koch <chk@pobox.com>
 *
 */
public class BlackJack {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		String playerName = "";
		playerName = scanner.nextLine().trim();

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
				int dealerHandScore = 0;
				String dealerHandString = "";

				// get and display dealer cards
				int dealerCard1 = dealACard();
				if (isAce(dealerCard1)) {
					dealerAceCount++;
				}
				;
				int dealerCard2 = dealACard();
				if (isAce(dealerCard2)) {
					dealerAceCount++;
				}
				;
				dealerHandString = "XX " + formatCard(dealerCard2);
				dealerHandScore = getCardScore(dealerCard1)
						+ getCardScore(dealerCard2);

				// get and display first two player cards
				int playerCard1 = dealACard();
				if (isAce(playerCard1)) {
					playerAceCount++;
				}
				;
				int playerCard2 = dealACard();
				if (isAce(playerCard2)) {
					playerAceCount++;
				}
				;
				playerHandString = formatCard(playerCard1) + " "
						+ formatCard(playerCard2);

				playerHandScore = getCardScore(playerCard1)
						+ getCardScore(playerCard2);

				System.out.println("Dealer: " + dealerHandString);
				System.out.println("Player: " + playerHandString + " ... "
						+ playerHandScore);
				boolean anotherCard = true;
				boolean canDouble = true;
				if (bet * 2 > bank) {
					canDouble = false;
				}
				while (anotherCard) {
					int resp;
					if (canDouble) {
						resp = getInput(
								"Choose an action (1) Hit (2) Stay (3) Double: ",
								1, 3);
					} else {
						resp = getInput("Choose an action (1) Hit (2) Stay: ",
								1, 2);
					}

					if (resp == 1 || resp == 3) {
						if (resp == 3) {
							// player doubled; so double the bet and no more
							// cards
							anotherCard = false;
							bet *= 2;
						}
						canDouble = false;

						// get another card and add it to the player's hand
						int nextCard = dealACard();
						if (isAce(nextCard)) {
							playerAceCount++;
						}
						playerHandString += " " + formatCard(nextCard);
						playerHandScore += getCardScore(nextCard);

						// handle over 21 and aces
						if (playerHandScore > 21 && playerAceCount > 0) {
							playerAceCount--;
							playerHandScore -= 10;
						}

						System.out.println("Player: " + playerHandString
								+ " ... " + playerHandScore);

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

				// now we can display the dealer's hole card
				dealerHandString = formatCard(dealerCard1) + " "
						+ formatCard(dealerCard2);

				if (playerHandScore <= 21 && dealerHandScore < 16) {
					System.out.println("----- it's the dealer's turn!");
					System.out.println("Dealer: " + dealerHandString + " ... "
							+ dealerHandScore);
					anotherCard = true;
					while (anotherCard) {
						// get dealer card
						int nextCard = dealACard();
						if (isAce(nextCard)) {
							dealerAceCount++;
						}
						dealerHandString += " " + formatCard(nextCard);
						dealerHandScore += getCardScore(nextCard);

						// handle over 21 and aces
						if (dealerHandScore > 21 && dealerAceCount > 0) {
							dealerAceCount--;
							dealerHandScore -= 10;
						}

						System.out.println("Dealer: " + dealerHandString
								+ " ... " + dealerHandScore);

						// if dealer bust then done
						if ((dealerHandScore > 21) // dealer bust
								|| (dealerHandScore > playerHandScore) // dealer
																		// wins
								|| (dealerHandScore > 16) // dealer stays
						) {
							System.out.println("Dealer is done.");
							anotherCard = false;
						}
					}
				}

				// final displays and accounting
				System.out.println();
				System.out.println("----- final result:");
				System.out.println("Dealer: " + dealerHandString + " ... "
						+ dealerHandScore);
				System.out.println("Player: " + playerHandString + " ... "
						+ playerHandScore);

				if (playerHandScore > 21) {
					// bust
					System.out.println("You went bust!");
					bank -= bet;
				} else if (dealerHandScore > 21) {
					System.out.println("The dealer went bust!");
					bank += bet;
				} else if (playerHandScore > dealerHandScore) {
					System.out.println("Player wins!");
					bank += bet;
				} else {
					System.out.println("Dealer wins!");
					bank -= bet;
				}

				System.out.println("Bank is now: " + bank);
			}
		}
	}

	static int getInput(String prompt, int lowest, int highest) {
		while (true) {
			System.out.print(prompt);
			String nextLine = scanner.nextLine();
			int value;
			try {
				value = Integer.valueOf(nextLine);
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number.");
				continue;
			}
			if (value >= lowest && value <= highest) {
				return value;
			} else {
				System.out.println("Please enter a number between " + lowest + " and " + highest);
			}
		}
	}

	private static int getCardScore(int card) {
		int rank = getRank(card);
		int value;
		if (rank == 1) {
			value = 11;
		} else if (rank >= 10) {
			value = 10;
		} else {
			value = rank;
		}

		return value;
	}

	private static int dealACard() {
		return (int) (Math.random() * 52);
	}

	private static boolean isAce(int card) {
		return getRank(card) == 1;
	}

	private static int getRank(int card) {
		return (card % 13) + 1;
	}

	private static String formatCard(int card) {
		String[] suits = { "C", "D", "H", "S" };

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

		return cardAsString + suits[card / 13];
	}
}
