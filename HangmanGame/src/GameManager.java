import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;


public class GameManager {
    private ArrayList<String> _words = new ArrayList<>();
    private boolean isOn = true;
    private final Messenger _messenger = new Messenger();
    private Random _rand = new Random();
    private Scanner _scanner = new Scanner(System.in);
    private int _mistakesLimint = 5;

    public void Start() {
        this.SetWords();
        while (this.isOn) {
            _messenger.Print("Игра висельник, \n Введите число \n начало игры 1 \n правила, 2 \n Выйти , 3");
            var input = _scanner.nextLine();

            switch (input) {
                case "1":
                    this.StartGame();
                    break;
                case "2":
                    _messenger.Print("Правила: Загаданно случайное слово, вам необходимо угадать его побуквенно, при вводе неправильной буквы увеличивается счет ошибок, максимальное число ошибок 5.");
                    break;
                case "3":
                    _messenger.Print("Выход");
                    isOn = false;
                    break;
                default:
                    _messenger.Print("Вы ввели не правильную команду");
            }
        }


    }

    private void SetWords() {
        _words.add("ананас");
        _words.add("рыба");
        _words.add("печенье");
        _words.add("мальберт");
    }

    private void StartGame() {
        boolean isPlaying = true;
        var word = _words.get(_rand.nextInt(_words.size()));
        var wordSb = new StringBuilder(word.toLowerCase());
        var answer = new StringBuilder(".".repeat(word.length()));
        var usedCharacters = new HashSet<Character>();
        var mistakes = 0;
        while (isPlaying) {
            var inWord = false;
            _messenger.Print(answer.toString());
            _messenger.Print(word);
            _messenger.Print("Ошибок " + mistakes);
            _messenger.Print("Введите букву");
            var input = _scanner.nextLine().toLowerCase();
            if (!CheckInput(input)) {
                _messenger.Print("Неправильный ввод, принимаются только символы русского алфавита, не более 1 символа");
                continue;
            }
            var character = input.charAt(0);
            if (IsCharacterUsed(usedCharacters,character)) {
                _messenger.Print("Такая буква уже была");
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
            if (mistakes >= _mistakesLimint) {
                isPlaying = false;
                _messenger.Print("Вы проиграли");
            }
            if (word.contentEquals(answer)) {
                _messenger.Print("Вы выиграли! congratulations");
                isPlaying = false;
            }

        }
    }

    private boolean CheckInput(String input) {
        var isOneChar = input.length() == 1;
        var isCyrillic = Character.UnicodeBlock.of(input.charAt(0)) == Character.UnicodeBlock.CYRILLIC;
        return isOneChar && isCyrillic;
    }

    private boolean IsCharacterUsed(HashSet<Character> usedCharacters, Character testChar) {
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
