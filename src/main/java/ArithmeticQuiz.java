import textfiles.WriteFile;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.json.*;

import static java.lang.String.valueOf;


public class ArithmeticQuiz {

    private static String name;
    private static int length = 1;
    private static String file_name = "/home/abbas/LocalRepo/AbbasPlay/src/main/java/textfiles/results.json";

    private int runTheQuiz(int firstMin, int firstMax, int secondMin, int secondMax, String symbol) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int score = 0;
        for (int i = length; i > 0; i--) {
            int tries = 0;
            String answer;
            String ilyasTry;
            int num1 = random.nextInt(firstMin, firstMax);
            int num2 = random.nextInt(secondMin, secondMax);
            double num1D = generateRandomDecimal(firstMin, firstMax);
            double num2D = random.nextInt(secondMin, secondMax);

            if (num2 == 0 && symbol.equals("/") || num1 == 0 && symbol.equals("/")) {
                do {
                    num1 = random.nextInt(firstMin, firstMax);
                    num2 = random.nextInt(secondMin, secondMax);
                } while (num2 == 0 || num1 == 0);
            }
            do {
                switch (symbol) {
                    case "+":
                        answer = generateAdditionQuestion(num1, num2);
                        break;
                    case "-":
                        answer = generateSubtractionQuestion(num1, num2);
                        break;
                    case "*":
                        answer = generateMultiplicationQuestion(num1, num2);
                        break;
                    case "/":
                        answer = generateDivisionQuestion(num1, num2);
                        break;
                    case "decimal":
                        answer = generateDecimalQuestion(num1D, num2D);
                        break;
                    default:
                        answer = "somethings gone wrong";
                }
                ilyasTry = generateAnswer();
                if (valueOf(answer).equals(ilyasTry)) {
                    System.out.println("Correct well done " + name);
                    tries = 0;
                    score++;
                    break;
                } else {
                    System.out.println("unlucky " + name);
                    tries++;
                }
            } while (tries < 3);

            if (tries == 3) {
                System.out.println("the correct answer is " + answer);
            }
        }
        return score;
    }


    private String generateAdditionQuestion(int num1, int num2) {

        int answer;
        System.out.println(num1 + "+" + num2);
        answer = num1 + num2;
        return valueOf(answer);
    }

    private String generateSubtractionQuestion(int num1, int num2) {

        int answer;
        System.out.println(num1 + "-" + num2);
        answer = num1 - num2;
        return valueOf(answer);
    }

    private String generateMultiplicationQuestion(int num1, int num2) {

        int answer;
        System.out.println(num1 + "x" + num2);
        answer = num1 * num2;
        return valueOf(answer);
    }

    private String generateDivisionQuestion(int num1, int num2) {

        int answer;
        int divider = num1 * num2;
        System.out.println(divider + "/" + num1);
        answer = divider / num1;
        return valueOf(answer);
    }

    private String generateDecimalQuestion(double num1, double num2) {

        double answer;
        System.out.println(num1 + "+" + num2);
        answer = num1 + num2;
        return valueOf(answer);

    }

    private double generateRandomDecimal(int min, int max) {

        double x = Math.random() * ((min - max) + 1) + min;
        return Math.round(x * 100.0) / 100.0;
    }


    private String generateAnswer() {
        Scanner in = new Scanner(System.in);
        return in.next();
    }

    private static void outputToScreen(int addingScore, int minusScore, int mutiplyScore, int divideScore, int decimalScore, String totalDurationS) {
        System.out.println("you took " + totalDurationS.substring(2));
        System.out.println("Thanks for taking the test " + name + " you are awesome!!!!!!!!");
        System.out.println("in addition you got " + addingScore + " out of " + length);
        System.out.println("in subtraction you got " + minusScore + " out of " + length);
        System.out.println("in multiplication you got " + mutiplyScore + " out of " + length);
        System.out.println("in division you got " + divideScore + " out of " + length);
        System.out.println("in decimal adding you got " + decimalScore + " out of " + length);
    }

    private static void outputToJSON(int addingScore, int minusScore, int multiplyScore, int divideScore, int decimalScore, String totalDurationS, String addDurationS, String minusDurationS, String multiplyDurationS, String divideDurationS, String decimalDurationS) throws IOException{

        JSONArray jsonFileArray = new JSONArray();
        int totalScore = addingScore + minusScore + multiplyScore + divideScore + decimalScore;
        DateFormat dateFormat = new SimpleDateFormat("E dd MMM hh.mm a");
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonBreakDownArr = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("Addition Score", addingScore);
        item.put("Addition Time", addDurationS.substring(2));
        item.put("Subtraction Score", minusScore);
        item.put("Subtraction Time", minusDurationS.substring(2));
        item.put("Multiplication Score", multiplyScore);
        item.put("Multiplication Time", multiplyDurationS.substring(2));
        item.put("Division Score", divideScore);
        item.put("Division Time", divideDurationS.substring(2));
        item.put("Decimal Score", decimalScore);
        item.put("Decimal Time", decimalDurationS.substring(2));
        jsonBreakDownArr.put(item);
        jsonObj.put("Breakdown", jsonBreakDownArr);
        jsonObj.put("Total Time", totalDurationS.substring(2));
        jsonObj.put("Total Number of Correct Answers", totalScore);
        jsonObj.put("Total Number of Questions", length * 5);
        jsonObj.put("Date and Time", dateFormat.format(Calendar.getInstance().getTime()));
        jsonObj.put("Name", name);
        jsonFileArray.put(jsonObj);
        WriteFile data = new WriteFile(file_name);
        data.writeToFile(jsonFileArray.toString());

    }


    public static void main(String[] args) throws IOException{
        System.out.println("***Hiya Welcome To 11+ Timed Practice***");

        System.out.println("please enter your name ");

        Scanner in = new Scanner(System.in);

        name = in.next();

        ArithmeticQuiz gog = new ArithmeticQuiz();

        Instant addStart = Instant.now();
        int addingScore = gog.runTheQuiz(-100, 100, -100, 100, "+");
        Instant addEnd = Instant.now();

        Instant minusStart = Instant.now();
        int minusScore = gog.runTheQuiz(-100, 100, -100, 100, "-");
        Instant minusEnd = Instant.now();

        Instant multiplyStart = Instant.now();
        int mutiplyScore = gog.runTheQuiz(-10, 10, -10, 10, "*");
        Instant multiplyEnd = Instant.now();

        Instant divideStart = Instant.now();
        int divideScore = gog.runTheQuiz(-10, 10, -10, 10, "/");
        Instant divideEnd = Instant.now();

        Instant decimalStart = Instant.now();
        int decimalScore = gog.runTheQuiz(0, 100, 0, 100, "decimal");
        Instant decimalEnd = Instant.now();


        Duration addDuration = Duration.between(addStart, addEnd);
        Duration minusDuration = Duration.between(minusStart, minusEnd);
        Duration multiplyDuration = Duration.between(multiplyStart, multiplyEnd);
        Duration divideDuration = Duration.between(divideStart, divideEnd);
        Duration decimalDuration = Duration.between(decimalStart, decimalEnd);

        Duration totalDuration = Duration.between(addStart, decimalEnd);
        String totalDurationS = totalDuration.toString();

        String addDurationS = addDuration.toString();
        String minusDurationS = minusDuration.toString();
        String multiplyDurationS = multiplyDuration.toString();
        String divideDurationS = divideDuration.toString();
        String decimalDurationS = decimalDuration.toString();

        outputToScreen(addingScore, minusScore, mutiplyScore, divideScore, decimalScore, totalDurationS);
        outputToJSON(addingScore, minusScore, mutiplyScore, divideScore, decimalScore, totalDurationS, addDurationS, minusDurationS, multiplyDurationS, divideDurationS, decimalDurationS);

    }
}