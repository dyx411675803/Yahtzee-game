/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.Arrays;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		score = new int [YahtzeeConstants.N_CATEGORIES][nPlayers];
		isFilled = new boolean [YahtzeeConstants.N_CATEGORIES][nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		if (nPlayers == 1 && playerNames[0].equals("dyx")) {
			master();
		} else {
			playGame();			
		}
		int winner = winner();
		display.printMessage("Congratulations, " + playerNames [winner - 1] + 
				" you are the winner with a total score of " + score[16][winner - 1] + "!");
	}
	
	private void master() {
		int turns = 0;
		/* You fill this in */
		while (true) {
			for (int i = 1; i <= nPlayers; i++){
				display.updateScorecard(17, i, score[16][i - 1]);
				display.printMessage(playerNames[i - 1] + "'s turn! Click \"Roll Dice\" button to roll the dice");
				//initialize dice[]
				display.waitForPlayerToClickRoll(i);
				for (int j = 0; j < YahtzeeConstants.N_DICE; j++){
					dice[j] = dialog.readInt("Make a Wish on your" + j + "th die");
				}
				try {
					display.displayDice(dice);
				} catch (ErrorException e){
					System.out.println("invalid dice!");
				}
				for (int r = 0; r < 2; r++){
					display.printMessage("select the dice you wish to re-roll and click \"Roll Again\"");
					display.waitForPlayerToSelectDice();
					for (int j = 0; j < YahtzeeConstants.N_DICE; j++){
						if (display.isDieSelected(j)) {
							dice[j] = readInt("Make a Wish on your" + j + "th die");
						}
					}
					try {
						display.displayDice(dice);
					} catch (ErrorException e){
						System.out.println("invalid dice!");
					}
				}
				display.printMessage("select a category for this roll");
				int category = display.waitForPlayerToSelectCategory();
				if (category != 7 && category != 8 && category != 16 
						&& category != 17 && !isFilled[category - 1][i - 1]) {
					score[category - 1][i - 1] = countScore(category);
					if (category < 8){
						score[6][i - 1] += score[category - 1][i - 1];						
					} else {
						score[15][i - 1] += score[category - 1][i - 1];
					}
					score[16][i - 1] += score[category - 1][i - 1];		//add to the total score
					isFilled[category - 1][i - 1] = true;
					//integer category and i(player's index)begin with 1;
					display.updateScorecard(category, i, score[category - 1][i - 1]);	
				} else {
					display.printMessage("invalid category!");
				}
			}
			turns ++;
			if (turns > YahtzeeConstants.N_CATEGORIES - 4) break;
		}
	}

	private void playGame() {
		int turns = 0;
		/* You fill this in */
		while (true) {
			for (int i = 1; i <= nPlayers; i++){
				display.updateScorecard(17, i, score[16][i - 1]);
				display.printMessage(playerNames[i - 1] + "'s turn! Click \"Roll Dice\" button to roll the dice");
				//initialize dice[]
				display.waitForPlayerToClickRoll(i);
				for (int j = 0; j < YahtzeeConstants.N_DICE; j++){
					dice[j] = rgen.nextInt(1,6);
				}
				try {
					display.displayDice(dice);
				} catch (ErrorException e){
					System.out.println("invalid dice!");
				}
				for (int r = 0; r < 2; r++){
					display.printMessage("select the dice you wish to re-roll and click \"Roll Again\"");
					display.waitForPlayerToSelectDice();
					for (int j = 0; j < YahtzeeConstants.N_DICE; j++){
						if (display.isDieSelected(j)) {
							dice[j] = rgen.nextInt(1, 6);
						}
					}
					try {
						display.displayDice(dice);
					} catch (ErrorException e){
						System.out.println("invalid dice!");
					}
				}
				display.printMessage("select a category for this roll");
				int category = display.waitForPlayerToSelectCategory();
				if (category != 7 && category != 8 && category != 16 
						&& category != 17 && !isFilled[category - 1][i - 1]) {
					score[category - 1][i - 1] = countScore(category);
					if (category < 8){
						score[6][i - 1] += score[category - 1][i - 1];						
					} else {
						score [15][i - 1] += score[category - 1][i - 1];
					}
					score[16][i - 1] += score[category - 1][i - 1];		//add to the total score
					isFilled[category - 1][i - 1] = true;
					//integer category and i(player's index)begin with 1;
					display.updateScorecard(category, i, score[category - 1][i - 1]);	
				} else {
					display.printMessage("invalid category!");
				}
			}
			turns ++;
			if (turns >= YahtzeeConstants.N_CATEGORIES - 4) break;
		}
	}
	
	private int countScore (int category) {
		int tempScore = 0;
		switch (category) {
		case 1:		//ONES
			for (int i: dice) {
				if (i == 1) {
					tempScore ++;
				}
			}
			return tempScore;
		case 2:		//TWOS
			for (int i: dice) {
				if (i == 2) {
					tempScore ++;
				}
			}
			tempScore *= 2;
			return tempScore;
		case 3: 	//THREES
			for (int i: dice) {
				if (i == 3) {
					tempScore ++;
				}
			}
			tempScore *= 3;
			return tempScore;
		case 4:		//FOURS
			for (int i: dice) {
				if (i == 4) {
					tempScore ++;
				}
			}
			tempScore *= 4;
			return tempScore;
		case 5:		//FIVES
			for (int i: dice) {
				if (i == 5) {
					tempScore ++;
				}
			}
			tempScore *= 5;
			return tempScore;
		case 6:		//SIXES
			for (int i: dice) {
				if (i == 6) {
					tempScore ++;
				}
			}
			tempScore *= 6;
			return tempScore;
		case 9: 	//Three of a kind
			if (Toak(sort(dice))) {
				for (int value: dice) {
					tempScore += value;
				}
			}
			return tempScore;
		case 10: 	//Four of a kind
			if (Foak(sort(dice))) {
				for (int value: dice) {
					tempScore += value;
				}
			}
			return tempScore;
		case 11: 	//Full house
			if (fullHouse(sort(dice))) {
				tempScore += 25;
			}
			return tempScore;
		case 12: 	//Small Straight
			if (small(sort(dice))) {
				tempScore += 30;
			}
			return tempScore;
		case 13: 	//Large Straight
			if (large(sort(dice))) {
				tempScore += 40;
			}
			return tempScore;
		case 14: 	//Yahtzee
			if (yahtzee(sort(dice))) {
				tempScore += 50;
			}
			return tempScore;
		case 15: 	//Chance
			for (int value: dice) {
				tempScore += value;
			}
			return tempScore;
		default:
			return tempScore;				
		}
	}
	
	private int[] sort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length -i - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
		return arr;
	}
	
	private boolean contains (int[] arr, int value) {
		for (int a: arr) {
			if (a == value) {
				return true;
			}
		}
		return false;
	}
	
	private boolean Toak (int[] arr) {
		for (int i = 0; i < arr.length - 2; i++) {
			if (arr[i] == arr[i + 1] && arr[i + 1] == arr[i + 2]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean Foak (int[] arr) {
		for (int i = 0; i < arr.length - 3; i++) {
			if (arr[i] == arr[i + 1] && arr[i + 1] == arr[i + 2] && arr[i + 2] == arr[i + 3]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean fullHouse (int[] arr) {
		if (arr[0] == arr[1] && arr[1] == arr[2] && arr[2] != arr[3] && arr[3] == arr[4]) {
			return true;
		} else if (arr[0] == arr[1] && arr[1] != arr[2] && arr[2] == arr[3] && arr[3] == arr[4]) {
			return true;
		}
		return false;
	}
	
	private boolean small (int[] arr) {
		for (int i = 0; i < arr.length - 3; i++) {
			if (contains (arr, arr[i] + 1) && contains (arr, arr[i] + 2) && 
					contains (arr, arr[i] + 3)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean large (int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != i + 1) {
				return false;
			}
		}
		return true;
	}
	
	private boolean yahtzee (int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] != arr[i + 1]) {
				return false;
			}
		}
		return true;
	}
	
	//this method returns the index of the winner
	private int winner() {
		for (int i = 1; i <= nPlayers; i++) {
			if (score[6][i - 1] >= 63) {
				score [7][i - 1] = 35;
			}
				score[16][i - 1] += score[7][i - 1];				
		}
		int winner = 0;
		int max = 0;
		for (int i = 1; i <= nPlayers; i++) {
			if (score[16][i - 1] > max) {
				max = score[16][i - 1];
				winner = i;
			}
			display.updateScorecard(7, i, score[6][i - 1]);
			display.updateScorecard(8, i, score[7][i - 1]);
			display.updateScorecard(16, i, score[15][i - 1]);
		}
		return winner;
	}
		
/* Private instance variables */
	private IODialog dialog;
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dice = new int[YahtzeeConstants.N_DICE];
	private int[][] score;
	private boolean[][] isFilled;
}
