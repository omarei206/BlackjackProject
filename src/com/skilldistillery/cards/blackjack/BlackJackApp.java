package com.skilldistillery.cards.blackjack;

import java.util.Scanner;

import com.skilldistillery.cards.common.Deck;

public class BlackJackApp {

	public static void main(String[] args) {

		System.out.println("Welcome to Omarei's BlackJack realm ^_^");

		// Create the playing deck
		Deck playingDeck = new Deck();
		playingDeck.createFullDeck();
		playingDeck.shuffle();

		// Create a deck for player
		Deck playerDeck = new Deck();

		Deck dealerDeck = new Deck();

		double playerMoney = 100.00;

		Scanner input = new Scanner(System.in);

		// The game's loop
		while (playerMoney > 0) {
			// Keep playing
			System.out.println("You have $" + playerMoney + ", how much do you want to bet?");
			double playerBet = input.nextDouble();
			if (playerBet > playerMoney) {
				System.out.println("Are you trying to scam us? GET OUT!");
				break;
			}

			boolean endRound = false;
			// start dealing
			// player receives cards
			playerDeck.draw(playingDeck);
			playerDeck.draw(playingDeck);

			// Dealer gets two cards
			dealerDeck.draw(playingDeck);
			dealerDeck.draw(playingDeck);

			while (true) {
				System.out.println("Your hand is: ");
				System.out.println(playerDeck.toString());
				System.out.println("Your deck is valued at: " + playerDeck.cardsValue());

				// Display Dealer Hand
				System.out.println("Dealer Hand: " + dealerDeck.getCard(0).toString() + " and [Hidden]");

				// What does the player want to do?
				System.out.println("Would you like to (1)Hit or (2)Stand?");
				int response = input.nextInt();

				// They hit
				if (response == 1) {
					playerDeck.draw(playingDeck);
					System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize() - 1).toString());
					// Bust if over 21
					if (playerDeck.cardsValue() > 21) {
						System.out.println("You busted. Current hand value: " + playerDeck.cardsValue());
						playerMoney -= playerBet;
						endRound = true;
						break;
					}
				}

				if (response == 2) {
					break;
				}
			}
			// Reveal dealers cards
			System.out.println("Dealer's Cards: " + dealerDeck.toString());
			// See if dealer has more points than player
			if (dealerDeck.cardsValue() > playerDeck.cardsValue() && endRound == false) {
				System.out.println("The dealer won!");
				playerMoney -= playerBet;
				endRound = true;
			}
			// Dealer draws at 16 and stands at 17
			while (dealerDeck.cardsValue() < 17 && endRound == false) {
				dealerDeck.draw(playingDeck);
				System.out.println("Dealer draws: " + dealerDeck.getCard(dealerDeck.deckSize() - 1).toString());
			}
			// Display total value for dealer
			System.out.println("Dealer's hand is: " + dealerDeck.cardsValue());
			// Determine if the dealer busted
			if (dealerDeck.cardsValue() > 21 && endRound == false) {
				System.out.println("Dealer busted! You win ^_^");
				playerMoney += playerBet;
				endRound = true;
			}

			// Determine if push(tie)
			if (playerDeck.cardsValue() == dealerDeck.cardsValue() && endRound == false) {
				System.out.println("Push");
				endRound = true;
			}

			if (playerDeck.cardsValue() > dealerDeck.cardsValue() && endRound == false) {
				System.out.println("You win the hand! ^_^");
				playerMoney += playerBet;
				endRound = true;
			} else if (endRound == false) {
				System.out.println("You lose the hand.");
				playerMoney -= playerBet;
				endRound = true;
			}

			playerDeck.moveAllToDeck(playingDeck);
			dealerDeck.moveAllToDeck(playingDeck);
			System.out.println("End of Hand!");

		}

		System.out.println("Game over! You lost all of your money. :(");
	}

}
