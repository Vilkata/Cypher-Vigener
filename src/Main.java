import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static ArrayList<String> results = new ArrayList<>();


    //Създаване на резултат
    public static void addResult(String command, String text, String key, StringBuilder result, char[] alphabet) {

        StringBuilder sb = new StringBuilder();
        sb.append("азбука -->").append(Arrays.toString(alphabet)).append("\n");
        sb.append(command).append(" - ").append(command.equals("c") ? "P = " : "C = ").append(text);
        if (key != null) {
            sb.append(" (key: ").append(key).append(")");
        }
        sb.append(" -> ").append(command.equals("c") ? "C= " : "P= ").append(result).append(";\n");
        results.add(sb.toString());
    }

    //Създаване на азбука
    public static char[] getAlphabet(boolean randomized) {
        char[] alphabet = new char[26];
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = (char) (i + 97);
        }

        if (randomized) {
            Random random = new Random();
            for (int i = 0; i < alphabet.length; i++) {
                int swapIndex = random.nextInt(alphabet.length);
                char temp = alphabet[swapIndex];
                alphabet[swapIndex] = alphabet[i];
                alphabet[i] = temp;
            }
        }

        return alphabet;
    }

    public static void Vigener(Scanner scanner, String command) {
        System.out.println("Въведи \"R\" за рандомизирана азбука или продължи с който и да е клавиш");
        boolean randomized = scanner.nextLine().equalsIgnoreCase("r");

        System.out.println("Въведи \"K\" за използване на ключ или продължи с който и да е клавиш");
        boolean usedKey = scanner.nextLine().equalsIgnoreCase("k");

        System.out.println("Въведи начален текст:");
        String text = scanner.nextLine().toLowerCase();

        char[] alphabet = getAlphabet(randomized);
        String key = null;
        if (usedKey) {
            System.out.println("Въведи ключ:");
            key = scanner.nextLine().toLowerCase();
        }

        StringBuilder result = new StringBuilder();
        upper:
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            for (int j = 0; j < alphabet.length; j++) {
                char letter = alphabet[j];
                if (letter == ch) {
                    int alphabetIndex = 0;
                    if (command.equals("c")) {// Проверка дали шифрираме или дешифриране
                        if (usedKey) {
                            int keyIndex = key.charAt(i % key.length()) - 97;
                            alphabetIndex = (j + keyIndex) % 26;
                        } else {
                            alphabetIndex = (j + i + 1) % 26;
                        }
                    } else {
                        if (usedKey) {
                            int keyIndex = key.charAt(i % key.length()) - 97;
                            alphabetIndex = (j - keyIndex) < 0 ? 26 + (j - keyIndex) : (j - keyIndex);
                        } else {
                            alphabetIndex = (j - i - 1) < 0 ? 26 + (j - i - 1) : (j - i - 1);
                        }

                    }
                    result.append(alphabet[alphabetIndex]);
                    continue upper;
                }
            }
        }
        addResult(command, text, key, result, alphabet);//Добавяме резултата в списък с резултати
        System.out.printf("%s\n\n", result);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Програма демонстрираща шифъра на Виженер\n");

        String ch = null;
        do {
            System.out.println("""
                    Въведи една от следните опции:
                    C - за шифриране
                    D - за дешифриране
                    R - за показване на всички резултати
                    E - за затваряне на програмата
                    """);
            ch = scanner.nextLine().toLowerCase();

            if (ch.length() > 1) {
                System.out.println("Въведи валидна команда");
                continue;
            }

            switch (ch) {
                case "c", "d":
                    Vigener(scanner, ch);
                    break;
                case "e":
                    System.out.println("Програмата е затворена");
                    break;
                case "r":
                    System.out.println("<Резултати до сега>");
                    for(String result: results) {
                        System.out.println(result);
                    }
                    System.out.println("<Резултати до сега>\n");
                    break;
                default:
                    System.out.println("Въведи валидна команда");
            }
        } while (!ch.equals("e"));
    }
}