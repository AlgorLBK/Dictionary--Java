//101372552
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        Dictionary dic = new Dictionary();
        dic.printMenu();
        int option;
        String input = keyboard.nextLine();
        while (!dic.isOptionInteger(input) || Integer.parseInt(input) > 6 || Integer.parseInt(input) < 1) {
            System.out.println("Please, enter a number between 1 and 6 inclusive: ");
            dic.printMenu();
            input = keyboard.nextLine();
        }
        option = Integer.parseInt(input);
        while (option != 6) {
            if (option == 1) {
                System.out.println("Please, enter the word you want to add: ");
                String word = keyboard.nextLine();
                System.out.println("Please, enter the meaning of the added word: ");
                String meaning = keyboard.nextLine();
                dic.add(word, meaning);
                dic.printMenu();
                option = Integer.parseInt(keyboard.nextLine());
            }
            if (option == 2) {
                System.out.println("Please, enter the word you want to delete: ");
                String word = keyboard.nextLine();
                dic.delete(word);
                dic.printMenu();
                option = Integer.parseInt(keyboard.nextLine());
            }
            if (option == 3) {
                System.out.println("Please, enter the word you want to get the meaning of: ");
                String word = keyboard.nextLine();
                System.out.println(dic.getMeaning(word));
                dic.printMenu();
                option = Integer.parseInt(keyboard.nextLine());
            }
            if (option == 4) {
                System.out.println("Here is the list of the words contained in the dictionary: ");
                System.out.println(dic.printWordList());;
                dic.printMenu();
                option = Integer.parseInt(keyboard.nextLine());
            }
            if (option == 5) {
                try {
                    System.out.println("Please, enter the path of the file you want to use: ");
                    File file = new File(keyboard.nextLine());
                    dic.spellCheckTextFile(file);
                } catch (FileNotFoundException a) {
                    System.out.println("The file entered does not exist.");
                }
                dic.printMenu();
                option = Integer.parseInt(keyboard.nextLine());
            }
        }

        System.out.println("Thank you");

    }
}