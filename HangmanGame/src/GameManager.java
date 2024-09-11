import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;


public class GameManager {
    private ArrayList<String> words = new ArrayList<>();
    private boolean isOn = true;
    private final Messenger messenger = new Messenger();
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);
    private final int mistakesLimit = 5;

    public void start() {
        this.setWords();
        while (this.isOn) {
            messenger.print("Игра висельник, \n Введите число \n начало игры 1 \n правила, 2 \n Выйти , 3");
            var input = scanner.nextLine();

            switch (input) {
                case "1":
                    this.startGame();
                    break;
                case "2":
                    messenger.print("Правила: Загаданно случайное слово, вам необходимо угадать его побуквенно, при вводе неправильной буквы увеличивается счет ошибок, максимальное число ошибок 5.");
                    break;
                case "3":
                    messenger.print("Выход");
                    isOn = false;
                    break;
                default:
                    messenger.print("Вы ввели не правильную команду");
            }
        }


    }

    private void setWords() {
        words.add("ананас");
        words.add("рыба");
        words.add("печенье");
        words.add("мальберт");
    }

    private void startGame() {
        boolean isPlaying = true;
        var word = words.get(random.nextInt(words.size()));
        var wordSb = new StringBuilder(word.toLowerCase());
        var answer = new StringBuilder(".".repeat(word.length()));
        var usedCharacters = new HashSet<Character>();
        var mistakes = 0;
        while (isPlaying) {
            var inWord = false;
            messenger.print(answer.toString());
            messenger.print(word);
            messenger.print("Ошибок " + mistakes);
            messenger.print("Введите букву");
            var input = scanner.nextLine().toLowerCase();
            if (!checkInput(input)) {
                messenger.print("Неправильный ввод, принимаются только символы русского алфавита, не более 1 символа");
                continue;
            }
            var character = input.charAt(0);
            if (isCharacterUsed(usedCharacters,character)) {
                messenger.print("Такая буква уже была");
                continue;
            }

            usedCharacters.add(character);

            for (int i = 0; i < word.length(); i++) {
                if (wordSb.charAt(i) == character) {
                    answer.setCharAt(i, character);
                    inWord = true;
                }

            }
            if (!inWord) {
                mistakes++;
            }
            if (mistakes >= mistakesLimit) {
                isPlaying = false;
                messenger.print("Вы проиграли");
            }
            if (word.contentEquals(answer)) {
                messenger.print("Вы выиграли! congratulations");
                isPlaying = false;
            }

        }
    }

    private boolean checkInput(String input) {
        var isOneChar = input.length() == 1;
        var isCyrillic = Character.UnicodeBlock.of(input.charAt(0)) == Character.UnicodeBlock.CYRILLIC;
        return isOneChar && isCyrillic;
    }

    private boolean isCharacterUsed(HashSet<Character> usedCharacters, Character testChar) {
        var result = false;
        for (Character character : usedCharacters) {
            if (character.equals(testChar)) {
                result = true;
                break;
            }
        }
        return result;
    }


}
