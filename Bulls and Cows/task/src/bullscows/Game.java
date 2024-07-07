package bullscows;

import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);
    int bull = 0;
    int cow = 0;
    int count = 0;
    int symbolsNum = 0;
    int secretCodeLength = 0;
    String symbol = "0123456789abcdefghijklmnopqrstuvwxyz";
    StringBuilder secretCode = new StringBuilder();

    public void play() {
        System.out.println("Please, enter the secret code's length:");
        secretCodeLength = getLength();
        if (secretCodeLength == -1) {
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");
        symbolsNum = getSymbolsNum();
        if (symbolsNum == -1) {
            return;
        }
        secretCode = generateSecretCode();
        System.out.println("Okay, let's start a game!");
        boolean isGuessed = false;
        while (!isGuessed) {
            isGuessed = check();
        }


    }

    public boolean check() {
        System.out.println("Turn " + ++count + ":");
        String input = getAnswer();

        for (int i = 0; i < input.length(); i++) {
            if (secretCode.indexOf(String.valueOf(input.charAt(i))) != -1) {
                if (secretCode.charAt(i) == input.charAt(i)) {
                    bull++;
                } else {
                    cow++;
                }
            }
        }
        String bullStr = bull == 1 ? "bull" : "bulls";
        String cowStr = cow == 1 ? "cow" : "cows";
        if (bull == 0 && cow == 0) {
            System.out.println("None.");
        } else if (bull == 0) {
            System.out.printf("Grade: %d %s.\n", cow, cowStr);
        } else if (cow == 0) {
            System.out.printf("Grade: %d %s.\n", bull, bullStr);
        } else {
            System.out.printf("Grade: %d %s and %d %s.\n", bull, bullStr, cow, cowStr);
        }
        if (bull == secretCode.length() && input.contentEquals(secretCode)) {
            System.out.println("Congratulations! You guessed the secret code.");
            return true;
        }
        resetGrade();
        return false;
    }

    public StringBuilder generateSecretCode() {
        StringBuilder secretCode = new StringBuilder();
        while (secretCode.length() < secretCodeLength) {
            int index = (int) (Math.random() * symbolsNum);
            char symbolChar = symbol.charAt(index);
            if (secretCode.indexOf(String.valueOf(symbolChar)) == -1) {
                secretCode.append(symbolChar);
            }
        }

        String asterisks = "*".repeat(secretCodeLength);
        String conditions = "";
        if (symbolsNum == 10) {
            conditions = "(0-9)";
        } else {
            conditions = String.format("(0-9, a-%c)", symbol.charAt(symbolsNum - 1));
        }
        System.out.printf("The random secret number is %s. %s\n", asterisks, conditions);
        return secretCode;
    }

    public int getLength() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (!input.matches("\\d+")) {
            System.out.printf("Error: %s isn't a valid number.\n", input);
        } else {
            int inputInt = Integer.parseInt(input);
            if (inputInt < 1 || inputInt > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            } else {
                return inputInt;
            }
        }
        return -1;
    }

    public int getSymbolsNum() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (!input.matches("\\d+")) {
            System.out.printf("Error: %s isn't a valid number.\n", input);
        } else {
            int inputInt = Integer.parseInt(input);
            if (inputInt < 10 || inputInt > 36) {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.\n", inputInt);
            } else if (inputInt < secretCodeLength) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", secretCodeLength, inputInt);
            } else {
                return inputInt;
            }
        }
        return -1;
    }

    public void resetGrade() {
        bull = 0;
        cow = 0;
    }

    public String getAnswer() {

        boolean isValid = false;
        String input = "";
        while (!isValid) {
            input = scanner.nextLine();
            if (input.length() != secretCodeLength) {
                System.out.printf("Error: the answer must contain %d digits.\n", secretCodeLength);
            } else {
                isValid = true;
            }
        }
        return input;
    }

}

