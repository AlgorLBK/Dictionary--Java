//101372552
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Dictionary {
    public WordInfo[] words;
    public int numWords;
    public Dictionary() throws IOException {
        words = new WordInfo[1500];
        numWords = 0;
        readFromTextFile(words);
    }
    public void readFromTextFile(WordInfo[] arr) throws IOException {
        File file = new File("wordList.txt");
        Scanner scFile = new Scanner(file);
        String[] word = new String[1500];
        int i = 0;
        String wordSaved = "";
        String meaningSaved = "";

        while (scFile.hasNext()) {
            word[i] = scFile.nextLine();
            wordSaved = word[i].split(":")[0];
            if (word[i].split(":").length > 1) {
                meaningSaved = word[i].split(":")[1];
            }
            arr[i] = new WordInfo(wordSaved, meaningSaved);
            numWords++;
            i++;
        }
        scFile.close();
    }
    public static void writeOnTextFile(String word, String meaning) throws IOException {
        FileWriter fw = new FileWriter("wordList.txt",true);
        if (!meaning.isEmpty() && !meaning.isBlank()) {
            meaning = ":" + meaning;
        }
        fw.write(word + meaning + "\n");
        fw.close();
    }
    public void printMenu() {
        System.out.println(
                "1) Add new word\n" +
                        "2) Delete word\n" +
                        "3) Get meaning\n" +
                        "4) Dictionary list\n" +
                        "5) Spell check a text file\n" +
                        "6) Exit"
        );
    }
    public boolean isOptionInteger(String op) {
        try {
            Integer.parseInt(op);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean add(String word, String meaning) throws IOException {
        word = word.toLowerCase();
        meaning = meaning.toLowerCase();
        if (exists(word)) {
            System.out.println("This word is already in the dictionary");
            return false;
        } else {
        words[numWords] = new WordInfo(word, meaning);
        numWords++;
        for (int start = 2; start < numWords; start++) {
            int previous = start - 1;
            String tempWord = words[start].word;
            String tempMeaning = words[start].meaning;
            while (previous >= 0 && tempWord.compareTo(words[previous].word) < 0) {
                words[previous + 1].word = words[previous].word;
                words[previous + 1].meaning = words[previous].meaning;
                previous--;
            }
            words[previous + 1].word = tempWord;
            words[previous + 1].meaning = tempMeaning;
        }
        FileWriter fw = new FileWriter("wordList.txt");
        fw.write("");
        for (int i = 0; i < numWords; i++) {
            writeOnTextFile(words[i].word, words[i].meaning);
        }
        return true; }
    }

    public boolean exists(String word) {
        word = word.toLowerCase();
        int low = 0;
        int high = numWords - 1;

        while (low <= high) {

            int midpoint = (high + low) / 2;
            if (words[midpoint].word.compareTo(word) == 0) {
                return true;
            }
            if (words[midpoint].word.compareTo(word) > 0) high = midpoint - 1;
            if (words[midpoint].word.compareTo(word) < 0) low = midpoint + 1;
        }
        return false;
    }

    public boolean delete(String word) throws IOException {
        word = word.toLowerCase();
        int low = 0;
        int high = numWords - 1;

        while (low <= high) {
            int midpoint = (high + low) / 2;
            if (words[midpoint].word.compareTo(word) == 0) {
                System.out.println("Found");
                words[midpoint].word = words[numWords -1].word;
                words[midpoint].meaning = words[numWords-1].meaning;
                numWords--;
                for (int start = 2; start < numWords; start++) {
                    int previous = start - 1;
                    String tempWord = words[start].word;
                    String tempMeaning = words[start].meaning;
                    while (previous >= 0 && tempWord.compareTo(words[previous].word) < 0) {
                        words[previous + 1].word = words[previous].word;
                        words[previous + 1].meaning = words[previous].meaning;
                        previous--;
                    }
                    words[previous + 1].word = tempWord;
                    words[previous + 1].meaning = tempMeaning;
                }
                FileWriter fw = new FileWriter("wordList.txt");
                fw.write("");
                for (int i = 0; i < numWords; i++) {
                    writeOnTextFile(words[i].word, words[i].meaning);
                }
                return true;
            }
            if (words[midpoint].word.compareTo(word) > 0) high = midpoint - 1;
            if (words[midpoint].word.compareTo(word) < 0) low = midpoint + 1;
        }
        System.out.println("Not found !");
        return false;
    }
    public String getMeaning(String word) {
        word = word.toLowerCase();
        int low = 0;
        int high = numWords - 1;

        while (low <= high) {

            int midpoint = (high + low) / 2;
            if (words[midpoint].word.compareTo(word) == 0) {
                if (words[midpoint].meaning.isEmpty() || words[midpoint].meaning.isBlank()) {
                    return "This word does not have a meaning in this dictionary.";
                }

                return "The meaning is: " + words[midpoint].meaning;

            }
            if (words[midpoint].word.compareTo(word) > 0) high = midpoint - 1;
            if (words[midpoint].word.compareTo(word) < 0) low = midpoint + 1;
        }
        return "The dictionary does not contain this word.";
    }

    public int getCount() {
        return numWords;
    }

    public String printWordList() {
        String wordList = "";
        for (int i = 1; i < numWords; i++) {
            wordList += words[i].word + "\n";
        }
        return wordList;
    }

    public void printDictionary() {
        for (int i = 1; i < numWords; i++) {
            if (!words[i].meaning.isBlank() || !words[i].meaning.isEmpty()) {
                System.out.println(words[i].word + " : " + words[i].meaning);
            } else System.out.println(words[i].word);

        }
    }

    public void spellCheckTextFile(File file) throws FileNotFoundException {
        String[] word = new String[1500];
        String[] verify;
        Scanner scFile = new Scanner(file);
        int i = 0;
        System.out.println("Here is a list containing the words that are not in the dictionary: ");
        while (scFile.hasNext()) {
            word[i] = scFile.nextLine();
            word[i] = word[i].replace(".","/");
            word[i] = word[i].replace(",","/");
            word[i] = word[i].replace(" ","/");
            verify = word[i].split("/");
            for (int j = 0; j < verify.length; j++) {
                if (!exists(verify[j])) {
                    if (!verify[j].isEmpty() || !verify[j].isBlank()) {
                    System.out.println(verify[j]);
                } 
            }
            i++;
        }
    }


}}
