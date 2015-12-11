package android.com.smartgen;

import android.os.Environment;
import android.support.v4.util.ArrayMap;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by snape on 07.12.15.
 */
public class Utils {
    public static int PASSLEN = 7;
    public static boolean ISSETDIGITS = true;
    public static boolean ISSETCAPITAL = true;
    public static boolean ISSETLOWER = true;
    public static boolean ISSETSPECIALS = true;
    public static boolean ISSETPERSONDATA = true;

    public static String HINT = "";

    public static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public static String digits = "1234567890";
    public static String specials = "<>!@#$%^&*()_-=+/\\'\":;?[]{}`|~";
    public static String russianSymbols = "а б в г д е ё ж з и й к л м н о п р с т у ф х ц ч ш щ ъ ы ь э ю я " +
            "А Б В Г Д Е Ё Ж З И Й К Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я";
    public static String latinAnalogue = "f , d u l t ` ; p b q r k v y j g h c n e a [ w x i o ] s m ' . z " +
            "F < D U L T ~ : P B Q R K V Y J G H C N E A { W X I O } S M \" > Z";

    public static Map<String, String> questions = new LinkedHashMap<>();
    public static Map<String, String> passwords = new LinkedHashMap<>();
    public static final String QUESTIONS_FILE = "SmartGenQuestions.ser";
    public static final String PASSWORDS_FILE = "Passwords.ser";

    static {
        questions.put("Номер школы, в которую вы пошли в первый класс", "");
        questions.put("Номер месяца, когда ты родился", "");
        questions.put("Месяц рождения бабушки (напр. январь)", "");
        questions.put("Ваше прозвище в детстве", "");
        questions.put("Год получения паспорта", "");

        questions.put("Название улицы, на которой был твой первый дом", "");
        questions.put("Город, где родилась мама", "");
        questions.put("Название улицы, на которой место работы/учебы", "");
        questions.put("Автомобильный код региона, где ты живешь", "");
        questions.put("Телефонный код города, где ты живешь", "");

        questions.put("Имя твоего питомца", "");
        questions.put("Любимое животное", "");
        questions.put("В каком зарубежном городе вы побывали впервые", "");
        questions.put("Имя лучшего друга", "");
        questions.put("Девичья фамилия матери", "");

        questions.put("Любимый актер", "");
        questions.put("Любимый спортсмен", "");
        questions.put("Имя главного персонажа в любимом фильме", "");
        questions.put("Имя автора любимой книги", "");
        questions.put("Сколько раз смотрел \"Star wars\"", "");

        questions.put("Номер паспорта", "");
        questions.put("Последние 3 цифры номера мобильного", "");
        questions.put("Номер школы", "");
        questions.put("\"Счастливый номер\"", "");
        questions.put("Сколько у тебя братьев и сестёр", "");
    }

    public static void saveAnswersToStorage() {
        try {
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir = new File (root.getAbsolutePath() + "/SmartGen");
            dir.mkdirs();
            File qf = new File(dir, Utils.QUESTIONS_FILE);

            FileOutputStream fileOut = new FileOutputStream(qf);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(questions);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePasswordToStorage() {
        try {
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir = new File (root.getAbsolutePath() + "/SmartGen");
            dir.mkdirs();
            File qf = new File(dir, Utils.PASSWORDS_FILE);

            FileOutputStream fileOut = new FileOutputStream(qf);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(passwords);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadAnswersFromStorage() {
        try {
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir = new File (root.getAbsolutePath() + "/SmartGen");
            dir.mkdirs();
            File qf = new File(dir, Utils.QUESTIONS_FILE);

            FileInputStream fileIn = new FileInputStream(qf);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            questions = (Map<String, String>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadPasswordsFromStorage() {
        try {
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir = new File (root.getAbsolutePath() + "/SmartGen");
            dir.mkdirs();
            File qf = new File(dir, Utils.PASSWORDS_FILE);

            FileInputStream fileIn = new FileInputStream(qf);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            passwords = (Map<String, String>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getAlphabetForGenerator() {
        String result = "";
        if(ISSETLOWER)
            result += alphabet.toLowerCase();
        if(ISSETCAPITAL)
            result += alphabet.toUpperCase();
        if(ISSETDIGITS)
            result += digits;
        if(ISSETSPECIALS)
            result += specials;

        if(result.isEmpty())
            result = " ";

        return result;
    }

    public static boolean emptyAnswers() {
        loadAnswersFromStorage();

        for ( final Map.Entry<String, String> question : Utils.questions.entrySet())
            if(!question.getValue().isEmpty())
                return false;

        return true;
    }

    public static Map<String, String> generateAnswerParts() {
        Map<String, String> not_empty = new HashMap<>();
        Map<String, String> parts = new HashMap<>();

        for ( final Map.Entry<String, String> question : Utils.questions.entrySet())
            if(!question.getValue().isEmpty())
                not_empty.put(question.getKey(), question.getValue());

        int answers_count = (Utils.PASSLEN >= 6 && Utils.PASSLEN <= 8) ? 2 :
                (Utils.PASSLEN >= 9 && Utils.PASSLEN <= 12) ? 2 :
                        (Utils.PASSLEN >= 13 && Utils.PASSLEN <= 15) ? 3 : 1;

        Random generator = new Random();

        for(int i = 0; i < answers_count; i++) {
            int random_index = generator.nextInt(not_empty.size());
            String randomAnswer = (String) not_empty.values().toArray()[random_index];
            String randomQuestion = (String) not_empty.keySet().toArray()[random_index];
            String[] part = getPartsWithPartedQuestions(randomQuestion, randomAnswer);
            parts.put(part[0], part[1]);

        }


        return parts;
    }

    private static String[] getPartsWithPartedQuestions(String randomQuestion, String randomAnswer) {
        Random rand = new Random();
        int maxcount = (Utils.PASSLEN >= 6 && Utils.PASSLEN <= 8) ? 3 :
                        (Utils.PASSLEN >= 9 && Utils.PASSLEN <= 12) ? 4 :
                        (Utils.PASSLEN >= 13 && Utils.PASSLEN <= 15) ? 4 : 1;
        
        int position = (randomAnswer.length() >= 5) ? rand.nextInt(randomAnswer.length() - maxcount) :
                        (randomAnswer.length() == 4 && maxcount == 3) ? rand.nextInt(randomAnswer.length() - 2) : 0;

        int count = (randomAnswer.length() >= 4) ? maxcount : randomAnswer.length();

        String part = randomAnswer.substring(position, position + count);
        String question = randomQuestion;

        if(randomAnswer.length() >= 4)
            question += "(" + count +  "символа" + " с позиции " + (position + 1) + ")";

        if(isRussianString(part)) {
            part = getLatinAnalogue(part);
            question += "(Русские символы в латинской раскладке)";
        }

        return new String[]{part, question};
    }

    private static String getLatinAnalogue(String part) {
        String result = "";
        for (int i = 0; i < part.length(); i++) {
            String sym = String.valueOf(part.charAt(i));
            if(russianSymbols.contains(sym)) {
                result += String.valueOf(latinAnalogue.charAt(russianSymbols.indexOf(sym)));
            } else {
                result += sym;
            }

        }

        return result;
    }

    private static boolean isRussianString(String part) {
        for (int i = 0; i < part.length(); i++)
            if(russianSymbols.contains(String.valueOf(part.charAt(i))))
                return true;

        return false;
    }


    public static String generatePersonalDataPassword(String gen_pass) {
        ArrayList<String> allTokens = new ArrayList<>();
        ArrayList<String> allNotes = new ArrayList<>();
        int partsLen = 0;
        Map<String, String> parts = Utils.generateAnswerParts();

        for ( final Map.Entry<String, String> part : parts.entrySet()) {
            partsLen += part.getKey().length();
            allTokens.add(part.getKey());
            allNotes.add(part.getValue());
        }

        StringBuilder builder_tokens = new StringBuilder();
        StringBuilder builder_notes = new StringBuilder();

        if(gen_pass.length() != 0) {

            char[] delimeters = gen_pass.substring(0, gen_pass.length() - partsLen).toCharArray();
            for (char delim : delimeters) {
                allTokens.add(String.valueOf(delim));
                allNotes.add(String.valueOf(delim));
            }
        }

        Random rgen = new Random();

        for (int i = 0; i < allTokens.size(); i++) {
            int randPos = rgen.nextInt(allTokens.size());
            String temp_symb = allTokens.get(i);
            allTokens.set(i, allTokens.get(randPos));
            allTokens.set(randPos, temp_symb);

            String temp_note = allNotes.get(i);
            allNotes.set(i, allNotes.get(randPos));
            allNotes.set(randPos, temp_note);
        }

        for(String s : allTokens)
            builder_tokens.append(s);

        for(int i = 0; i < allNotes.size(); i++) {
            builder_notes.append("'");
            builder_notes.append(allNotes.get(i));
            builder_notes.append("'");
            if(i != allNotes.size() - 1)
                builder_notes.append(" + ");
        }

        HINT = builder_notes.toString();

        return builder_tokens.toString();
    }

    public static boolean questionsInStorage() {
        File root = android.os.Environment.getExternalStorageDirectory();
        File file = new File (root.getAbsolutePath() + "/SmartGen/" + Utils.QUESTIONS_FILE);
        if(file.exists())
            return true;

        return false;
    }

    public static String generateHaveAllSymbolsPassword() {
        String result = "";
        ArrayList<String> alphabets = new ArrayList<>();
        if(ISSETDIGITS)
            alphabets.add(digits);
        if(ISSETCAPITAL)
            alphabets.add(alphabet.toUpperCase());
        if(ISSETLOWER)
            alphabets.add(alphabet);
        if(ISSETSPECIALS)
            alphabets.add(specials);

        if(!alphabets.isEmpty())
            result = generateFromAlphabets(alphabets);

        return result;
    }

    private static String generateFromAlphabets(ArrayList<String> alphabets) {
        String result = "";

        for(String alphabet : alphabets)
            result += RandomStringUtils.random(1, alphabet);

        for(int i = 0; i < Utils.PASSLEN - alphabets.size(); i++) {
            Random rn = new Random();
            result += RandomStringUtils.random(1, alphabets.get(rn.nextInt(alphabets.size())));
        }

        return shuffleString(result);
    }

    private static String shuffleString(String input) {
        List<Character> characters = new ArrayList<Character>();
        for(char c:input.toCharArray())
            characters.add(c);

        StringBuilder output = new StringBuilder(input.length());
        while(characters.size() != 0) {
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }

        return output.toString();
    }
}