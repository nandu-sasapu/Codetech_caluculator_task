import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends Frame implements ActionListener {

    private TextField display;
    private double firstOperand = 0;
    private String operator = "";
    private boolean startInput = true;

    public Calculator() {
        setTitle("Simple Calculator");
        setLayout(new BorderLayout());
        setSize(500, 550); // Set frame size

        display = new TextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 28)); // Set font size for display
        add(display, BorderLayout.NORTH);

        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5)); // 5 rows, 4 columns

        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "CE", "\u221A", "log",
            "backspace" // Backspace button
        }; 
        for (String label : buttonLabels) {
            Button button = new Button(label);
            button.setFont(new Font("Arial", Font.PLAIN, 18)); 
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "C":
                clear();
                break;
            case "CE":
                clearEntry();
                break;
            case "=":
                calculate();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                handleOperator(action);
                break;
            case ".":
                addDecimalPoint();
                break;
            case "\u221A": // Square root
                calculateSquareRoot();
                break;
            case "log":
                calculateLogarithm();
                break;
            case "Backspace":
                removeLastCharacter();
                break;
            default:
                appendDigit(action);
                break;
        }
    }

    private void clear() {
        display.setText("");
        firstOperand = 0;
        operator = "";
        startInput = true;
    }

    private void clearEntry() {
        display.setText("");
        startInput = true;
    }

    private void appendDigit(String digit) {
        if (startInput) {
            display.setText("");
            startInput = false;
        }
        display.setText(display.getText() + digit);
    }

    private void addDecimalPoint() {
        if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private void handleOperator(String newOperator) {
        if (!operator.isEmpty()) {
            calculate();
        }
        firstOperand = Double.parseDouble(display.getText());
        operator = newOperator;
        startInput = true;
    }

    private void calculate() {
        double secondOperand = Double.parseDouble(display.getText());
        double result = 0;
        switch (operator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "-":
                result = firstOperand - secondOperand;
                break;
            case "*":
                result = firstOperand * secondOperand;
                break;
            case "/":
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    display.setText("Error");
                    return;
                }
                break;
        }
        display.setText(String.valueOf(result));
        operator = "";
    }

    private void calculateSquareRoot() {
        double operand = Double.parseDouble(display.getText());
        if (operand >= 0) {
            display.setText(String.valueOf(Math.sqrt(operand)));
        } else {
            display.setText("Error");
        }
        startInput = true;
    }

    private void calculateLogarithm() {
        double operand = Double.parseDouble(display.getText());
        if (operand > 0) {
            display.setText(String.valueOf(Math.log10(operand)));
        } else {
            display.setText("Error");
        }
        startInput = true;
    }

    private void removeLastCharacter() {
        String currentText = display.getText();
        if (!currentText.isEmpty()) {
            display.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
