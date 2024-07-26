import java.util.Random;
import java.util.Scanner;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);
    private String[][] array = new String[3][3];
    private String usersSign;
    private String computersSign;
    private int place;
    private boolean draw = false;
    private int rounds = 1;
    private int draws = 0;
    private int computerWins = 0;
    private int playerWins = 0;
    private char continuue;
    private boolean diagonalequals = false;
    private boolean horizontequals = false;
    private boolean verticalequals = false;

    public void start() {
        System.out.println("-------XOX Game-------");
        System.out.println("The rules of the game: \nAt first you need to choose the sign X or O \nAt second you need to chose one of evaluable places and try to win the computer :) \nIf you are playing for X you move first else your move will be after computer's \nTo win you need to collect 3 of you signs in one of the verticals or horizontals or diagonals");
        array = fillTheArray(array);
        chooseTheSign();
        System.out.println("Here you can see the starting board \nNow you need to chose one of the available places from 1 to 9 where you want to put place your sign");
        printingTheArray(array);
        if (usersSign.equals("X")) {
            usersMove();
        } else {
            computersMove();
        }
    }

    public String[][] fillTheArray(String[][] array) {
        int num = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j] = String.valueOf(num + 1);
                num++;
            }
        }
        return array;
    }

    public void printingTheArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if ((i < 3) && (j < 2)) {
                    System.out.print(" " + array[i][j] + " " + "|");
                } else if ((i < 3) && (j == 2)) {
                    System.out.print(" " + array[i][j] + " " + "\n");
                }
            }
            if (i < 2) {
                System.out.println("———————————");
            } else
                System.out.println();
        }
    }

    public void chooseTheSign() {
        System.out.println("Choose your sign ( Enter X or O)");
        usersSign = scanner.nextLine();
        if (usersSign.equals("X")) {
            computersSign = "O";
            System.out.println("Now you are playing for " + usersSign);
        } else if (usersSign.equals("O")) {
            computersSign = "X";
            System.out.println("Now you are playing for " + usersSign);
        } else {
            System.out.println("You entered wrong sign, please, try one more time");
            chooseTheSign();
        }
    }

    public void usersMove() {
        System.out.println("Please enter the number of the place (from 1 to 9)");
        place = scanner.nextInt();
        scanner.nextLine();
        if ((place < 1) || (place > 9)) {
            System.out.println("You entered wrong number, please, try one more time");
            usersMove();
        } else {
            String placeStr = String.valueOf(place);
            boolean validMove = false;
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    if (array[i][j].equals(placeStr)) {
                        array[i][j] = usersSign;
                        validMove = true;
                        break;
                    }
                }
                if (validMove) break;
            }
            if (!validMove) {
                System.out.println("Place is already taken, please, try one more time");
                usersMove();
            } else {
                printingTheArray(array);
                checkIfWin();
                if (isDraw()) {
                    System.out.println("The game ends with a draw");
                    draws++;
                    question();
                }
                if ((horizontequals) || (verticalequals) || (diagonalequals)) {
                    System.out.println("You win");
                    playerWins++;
                    question();
                } else {
                    computersMove();
                }
            }
        }
    }

    public void computersMove() {
        boolean validMove = false;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (!array[i][j].equals(usersSign) && !array[i][j].equals(computersSign)) {
                    array[i][j] = computersSign;
                    validMove = true;
                    break;
                }
            }
            if (validMove) break;
        }
        printingTheArray(array);
        checkIfWin();
        if (isDraw()) {
            System.out.println("The game ends with a draw");
            draws++;
            question();
        }
        if ((horizontequals) || (verticalequals) || (diagonalequals)) {
            System.out.println("Computer wins");
            computerWins++;
            question();
        } else {
            usersMove();
        }
    }

    public boolean isDraw() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (!array[i][j].equals(usersSign) && !array[i][j].equals(computersSign)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void question() {
        System.out.println("Do you want to continue? \nYes - enter Y, no - enter N");
        continuue = scanner.nextLine().charAt(0);
        if (continuue == 'N') {
            end();
            System.exit(0);
        } else if (continuue == 'Y') {
            continueGame();
        } else {
            System.out.println("You input was wrong, please, try one more time");
            question();
        }
    }

    public void end() {
        System.out.println("Game over");
        System.out.println("The number of rounds is " + rounds);
        System.out.println("The number of draws is " + draws);
        System.out.println("The number of your wins is " + playerWins);
        System.out.println("The number of computer's wins is " + computerWins);
    }

    public void continueGame() {
        rounds++;
        array = fillTheArray(array);
        chooseTheSign();
        printingTheArray(array);
        if (usersSign.equals("X")) {
            usersMove();
        } else {
            computersMove();
        }
    }

    public void checkIfWin() {
        checkDiagonal();
        checkHorizontal();
        checkVertical();
    }

    public void checkDiagonal() {
        diagonalequals = (array[0][0].equals(array[1][1]) && array[0][0].equals(array[2][2]) && !array[0][0].matches("\\d")) ||
                (array[0][2].equals(array[1][1]) && array[0][2].equals(array[2][0]) && !array[0][2].matches("\\d"));
    }

    public void checkHorizontal() {
        horizontequals = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i][0].equals(array[i][1]) && array[i][0].equals(array[i][2]) && !array[i][0].matches("\\d")) {
                horizontequals = true;
                break;
            }
        }
    }

    public void checkVertical() {
        verticalequals = false;
        for (int i = 0; i < array[0].length; i++) {
            if (array[0][i].equals(array[1][i]) && array[0][i].equals(array[2][i]) && !array[0][i].matches("\\d")) {
                verticalequals = true;
                break;
            }
        }
    }
}
