public class ArithmeticCalculator {
    //stack for the numbers
    private CustomStack<Double> numStack = new CustomStack<>();
    //stack for the operators
    private CustomStack<String> operatorStack = new CustomStack<>();

    public double evaluateExpression(String expression) {

        //read expression character by character
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isWhitespace(ch)) continue;

            //if the character is a digit, check to see if it is a multi digit number or a decimal number, then add it to the stack
            if (Character.isDigit(ch)) {
                int j = i;
                while (j < expression.length() && (Character.isDigit(expression.charAt(j)) || expression.charAt(j) == '.')) {
                    j++;
                }
                double num = Double.parseDouble(expression.substring(i, j));
                numStack.push(num);
                i = j - 1;
            }

            //if the character is a minus, check to see if the following character is a digit, and if it is then make it a negative number and add it to the stack
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

                //process the operators that were inside the parentheses
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    processAnOperator();
                }
                operatorStack.pop(); // pop '('
            }
            else if (isOperator(ch)) {
                String operator = String.valueOf(ch);

                //check for multi-character operators
                if (i + 1 < expression.length()) {
                    char nextCh = expression.charAt(i + 1);
                    if ((ch == '>' && nextCh == '=') || (ch == '<' && nextCh == '=') ||
                            (ch == '=' && nextCh == '=') || (ch == '!' && nextCh == '=')) {
                        operator += nextCh;
                        i++;
                    }
                }

                //process operators of higher priority
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(") &&
                        prio(operator) >= prio(operatorStack.peek()) && numStack.size() >= 2) {
                    processAnOperator();
                }
                operatorStack.push(operator);
            }
        }

        while (!operatorStack.isEmpty() && numStack.size() >= 2) {
            processAnOperator();
        }

        //checking for invalid number of operators
        if (numStack.isEmpty() || !operatorStack.isEmpty()){

            //get rid of anymore invalid operators left in the stack before throwing the error
            while (!operatorStack.isEmpty()){
                operatorStack.pop();
            }
            throw new IllegalStateException("Stack is empty");
        }

        return numStack.pop();
    }

    private void processAnOperator() {
        String operator = operatorStack.pop();
        double num2 = numStack.pop();
        double num1 = numStack.pop();

        //switch statement for various operator cases
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

    //check if a character is an accepted operator
    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^' ||
                ch == '>' || ch == '<' || ch == '=' || ch == '!';
    }

    //check priority of operators
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
