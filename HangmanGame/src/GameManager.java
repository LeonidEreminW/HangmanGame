import java.util.ArrayList;
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
        var mistakes = 0;
        while (isPlaying) {
            var inWord = false;
            _messenger.Print(answer.toString());
            _messenger.Print(word);
            _messenger.Print("Ошибок "+mistakes);
            _messenger.Print("Введите букву");
            var letter = _scanner.nextLine().toLowerCase().charAt(0);
            for (int i = 0; i<word.length(); i++) {
                if(wordSb.charAt(i)==letter) {
                    answer.setCharAt(i,letter);
                    inWord = true;
                };
            }
            if (!inWord){
                mistakes++;
            }
            if (mistakes>=_mistakesLimint){
                isPlaying = false;
                _messenger.Print("Вы проиграли");
            }
            if (word.contentEquals(answer)){
                isPlaying = false;
            }

        }
    }
//    private void StartGame() {
//        boolean isPlaying = true;
//        int mistakes = 0;
//        var word = _words.get(_rand.nextInt(_words.size()));
//        var wordSb = new StringBuilder(word);
//        var answer = new StringBuilder(".".repeat(word.length()));
//        while (isPlaying) {
//            _messenger.Print(answer.toString());
//            _messenger.Print(word);
//            _messenger.Print("Ошибок 0");
//            _messenger.Print("Введите букву");
//            var correctInput = false;
//            char currentChar = '1';
//            while (!correctInput) {
//                var in = new Scanner(System.in);
//                var input = in.nextLine();
//
//                if (input.length() != 1) {
//                    correctInput = true;
//                    currentChar = input.charAt(0);
//
//                }
//            }
//
//            for (int i = 0; i < word.length(); i++) {
//                var flag = false;
//                if (wordSb.charAt(i) == currentChar) {
//                    flag = true;
//                    answer.setCharAt(i, currentChar);
//                }
//                if (!flag) {
//                    mistakes++;
//                }
//            }
//
//
//        }
//
//
//    }

    private String CreateString(int length) {
        return ".".repeat(Math.max(0, length));
    }
}
