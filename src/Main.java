import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArithmeticCalculator calculator = new ArithmeticCalculator();
        try (Scanner scanner = new Scanner(new File("input.txt"));
             PrintWriter writer = new PrintWriter(new File("output.txt"))) {
            while (scanner.hasNextLine()) {
                String expression = scanner.nextLine();
                try{
                    writer.println("Expression: " + expression);
                    writer.print("Result: ");
                    writer.println(calculator.evaluateExpression(expression));
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