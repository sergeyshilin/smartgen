package android.com.smartgen;

import android.os.Environment;
import android.support.v4.util.ArrayMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by snape on 07.12.15.
 */
public class Utils {
    public static Map<String, String> questions = new LinkedHashMap<>();
    public static final String QUESTIONS_FILE = "SmartGenQuestions.ser";

    static {
        questions.put("День рождения мамы (число)", "");
        questions.put("Номер месяца, когда ты родился", "");
        questions.put("Год, когда родилась бабушка", "");
        questions.put("Год окончания школы", "");
        questions.put("Год получения паспорта", "");

        questions.put("Название улицы, на которой был твой первый дом", "");
        questions.put("Город, где родилась мама", "");
        questions.put("Название улицы, на которой место работы/учебы", "");
        questions.put("Автомобильный код региона, где ты живешь", "");
        questions.put("Телефонный код города, где ты живешь", "");

        questions.put("Имя твоего питомца", "");
        questions.put("Любимое животное", "");
        questions.put("Чем оно питается", "");
        questions.put("Любимая порода собак/кошек", "");
        questions.put("Любимое растение", "");

        questions.put("Любимый актер", "");
        questions.put("Любимый спортсмен", "");
        questions.put("Имя главного персонажа в любимом фильме", "");
        questions.put("Имя автора любимой книги", "");
        questions.put("Сколько раз смотрел \"Star wars\"", "");

        questions.put("Серия паспорта", "");
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

            System.out.println(qf.toString());
            FileOutputStream fileOut = new FileOutputStream(qf);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(questions);
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

            System.out.println(qf.toString());

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

    public static boolean emptyAnswers() {
        loadAnswersFromStorage();

        for ( final Map.Entry<String, String> question : Utils.questions.entrySet())
            if(!question.getValue().isEmpty())
                return false;

        return true;
    }
}