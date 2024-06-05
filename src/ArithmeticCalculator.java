public class ArithmeticCalculator {
    private CustomStack<Double> numStack = new CustomStack<>();
    private CustomStack<String> operatorStack = new CustomStack<>();

    public double evaluateExpression(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isWhitespace(ch)) continue;

            if (Character.isDigit(ch)) {
                int j = i;
                while (j < expression.length() && (Character.isDigit(expression.charAt(j)) || expression.charAt(j) == '.')) {
                    j++;
                }
                double num = Double.parseDouble(expression.substring(i, j));
                numStack.push(num);
                i = j - 1;
            }
            else if ((String.valueOf(ch).equals("-") && Character.isDigit(expression.charAt(i+1)))){
                int j = i+1;
                while (j < expression.length() && (Character.isDigit(expression.charAt(j)) || expression.charAt(j) == '.')) {
                    j++;
                }
                double num = Double.parseDouble(expression.substring(i, j));
                numStack.push(num);
                i = j - 1;
            }
            else if (ch == '(') {
                operatorStack.push(String.valueOf(ch));
            }
            else if (ch == ')') {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    processAnOperator();
                }
                operatorStack.pop(); // pop '('
            }
            else if (isOperator(ch)) {
                String operator = String.valueOf(ch);

                // Check for multi-character operators
                if (i + 1 < expression.length()) {
                    char nextCh = expression.charAt(i + 1);
                    if ((ch == '>' && nextCh == '=') || (ch == '<' && nextCh == '=') ||
                            (ch == '=' && nextCh == '=') || (ch == '!' && nextCh == '=')) {
                        operator += nextCh;
                        i++;
                    }
                }

                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(") &&
                        prio(operator) >= prio(operatorStack.peek()) && numStack.size() >= 2) {
                    processAnOperator();
                }
                operatorStack.push(operator);
            }
        }

        while (!operatorStack.isEmpty()) {
            //System.out.println(operatorStack.peek());
            //System.out.println(numStack.peek());
            processAnOperator();
        }
        if (numStack.isEmpty()){
            throw new IllegalStateException("Stack is empty");
        }

        return numStack.pop();
    }

    private void processAnOperator() {
        //System.out.println(operatorStack.peek());
        //System.out.println(numStack.peek());
        String operator = operatorStack.pop();
        if (!operatorStack.isEmpty() && prio(operator) <= prio(operatorStack.peek())){

        }
        double num2 = numStack.pop();
        double num1 = numStack.pop();
        //System.out.println(num1 + operator + num2);

        switch (operator) {
            case "^":
                numStack.push(Math.pow(num1, num2));
                break;
            case "*":
                numStack.push(num1 * num2);
                break;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                numStack.push(num1 / num2);
                break;
            case "+":
                numStack.push(num1 + num2);
                break;
            case "-":
                numStack.push(num1 - num2);
                break;
            case ">":
                numStack.push(num1 > num2 ? 1.0 : 0.0);
                break;
            case ">=":
                numStack.push(num1 >= num2 ? 1.0 : 0.0);
                break;
            case "<=":
                numStack.push(num1 <= num2 ? 1.0 : 0.0);
                break;
            case "<":
                numStack.push(num1 < num2 ? 1.0 : 0.0);
                break;
            case "==":
                numStack.push(num1 == num2 ? 1.0 : 0.0);
                break;
            case "!=":
                numStack.push(num1 != num2 ? 1.0 : 0.0);
                break;
        }
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^' ||
                ch == '>' || ch == '<' || ch == '=' || ch == '!';
    }

    private int prio(String operator) {
        switch (operator) {
            case "^":
                return 2;
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 4;
            case ">":
            case ">=":
            case "<":
            case "<=":
                return 5;
            case "==":
            case "!=":
                return 6;
            default:
                return 0;
        }
    }
}
