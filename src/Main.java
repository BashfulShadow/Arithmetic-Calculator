import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double x = Double.parseDouble("-2.0");
        ArithmeticCalculator calculator = new ArithmeticCalculator();
        //System.out.println(calculator.evaluateExpression("(2 + 3) * (5 - 2) ^ 2 / 2"));
        //System.out.println(calculator.evaluateExpression("2 + 4 * 3 * 2 + 4"));
        try (Scanner scanner = new Scanner(new File("input.txt"));
             PrintWriter writer = new PrintWriter(new File("output.txt"))) {
            while (scanner.hasNextLine()) {
                String expression = scanner.nextLine();
                try{
                    double result = calculator.evaluateExpression(expression);
                    writer.println("Expression: " + expression);
                    writer.println("Result: " + result);
                }
                catch (ArithmeticException a){
                    writer.println("Division by 0 error");
                }
                catch (IllegalStateException i){
                    writer.println("Invalid or missing operator");
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}