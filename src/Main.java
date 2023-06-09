import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;
        int numPlayers = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Geben Sie die Anzahl der Spieler ein: ");
            if (scanner.hasNextInt()) {
                numPlayers = scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Ungültige Eingabe. Bitte geben Sie eine Zahl ein.");
                scanner.next();
            }
        }
        String[] players = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Geben Sie den Namen von Spieler " + (i + 1) + " ein: ");
            String name = scanner.next();
            while (!isAlpha(name)) {
                System.out.println("Nur Buchstaben sind gültig. Bitte geben Sie den Namen von Spieler " + (i + 1) + " ein: ");
                name = scanner.next();
            }
            players[i] = name;
        }
        while (playAgain) {
            System.out.println("Geben Sie ein Wort ein: ");
            String word = scanner.next();
            while (!isAlpha(word)) {
                System.out.println("Nur Buchstaben sind gültig. Bitte geben Sie ein Wort ein: ");
                word = scanner.next();
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            char[] letters = word.toCharArray();
            char[] progress = new char[letters.length];
            for (int i = 0; i < progress.length; i++) {
                progress[i] = '_';
            }
            int[] tries = new int[numPlayers];
            int currentPlayer = 0;
            char[] wrongGuesses = new char[26];
            int numWrongGuesses = 0;
            while (!isSame(letters, progress) && !allTriesUsed(tries)) {
                System.out.println("Spieler " + players[currentPlayer] + ", erraten Sie einen Buchstaben: ");
                char guess = scanner.next().charAt(0);
                while (!Character.isLetter(guess)) {
                    System.out.println("Nur Buchstaben sind gültig. Bitte geben Sie einen Buchstaben ein: ");
                    guess = scanner.next().charAt(0);
                }
                boolean correct = false;
                for (int i = 0; i < letters.length; i++) {
                    if (letters[i] == guess) {
                        progress[i] = guess;
                        correct = true;
                    }
                }
                if (!correct) {
                    tries[currentPlayer]++;
                    wrongGuesses[numWrongGuesses] = guess;
                    numWrongGuesses++;
                    System.out.println("Falsch! Versuche übrig für Spieler " + players[currentPlayer] + ": " + (10 - tries[currentPlayer]));
                }
                System.out.print("Falsche Vermutungen: ");
                for (int i = 0; i < numWrongGuesses; i++) {
                    System.out.print(wrongGuesses[i] + " ");
                }
                System.out.println();
                printHangman(numWrongGuesses);
                System.out.println(progress);
                currentPlayer = (currentPlayer + 1) % numPlayers;
            }
            if (allTriesUsed(tries)) {
                System.out.println("Sie haben verloren!");
            } else {
                System.out.println("Sie haben gewonnen!");
            }
            System.out.println("Möchten Sie noch einmal spielen? (j/n)");
            String response = scanner.next();
            if (response.equalsIgnoreCase("n")) {
                playAgain = false;
            }
        }
    }

    public static boolean isSame(char[] a, char[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlpha(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLetter(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean allTriesUsed(int[] tries) {
        for (int i = 0; i < tries.length; i++) {
            if (tries[i] < 10) {
                return false;
            }
        }
        return true;
    }

    public static void printHangman(int numWrongGuesses
    ) {
        if (numWrongGuesses > 3) {
            System.out.println("  +---+");
            System.out.println("  |   |");
        } else {
            System.out.println();
            System.out.println();
        }
        if (numWrongGuesses > 2) {
            System.out.println("  O   |");
        } else {
            System.out.println("      |");
        }
        if (numWrongGuesses > 1) {
            System.out.print(" /");
            if (numWrongGuesses > 0) {
                System.out.print("|");
            }
            if (numWrongGuesses > 7) {
                System.out.println("\\  |");
            } else {
                System.out.println("   |");
            }
        } else {
            System.out.println("      |");
        }
        if (numWrongGuesses > 6) {
            System.out.println(" / \\  |");
        } else if (numWrongGuesses > 5) {
            System.out.println(" /    |");
        } else {
            System.out.println("      |");
        }
        if (numWrongGuesses > 4) {
            System.out.println("      |");
            System.out.println("=========");
        } else {
            System.out.println();
            System.out.println();
        }
    }
}
