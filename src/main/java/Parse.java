import java.util.Scanner;

public class Parse {
    private String input;
    private boolean debug = false;

    public Parse(String input) {
        this.input = input;
    }

    public void parse(String s) {
        S_Recognizer();
        if (input.trim().length() > 0) {
            parse_error();
        }
    }

    private void parse_error(){
        System.out.println("Parse error");
        System.exit(1);
    }

    public void consume(String match) {
        input = input.trim();
        if (input.indexOf(match) == 0) {
            input = input.substring(match.length());
        } else {
            parse_error();
        }
    }

    // Match everything that matches an E
    public void E_Recognizer() {
        if (debug) {
            System.out.println("Running E Recognizer " + input);
        }
        input = input.trim();
        if (input.charAt(0) == 't') {
            this.consume("true");
        } else if (input.charAt(0) == 'f') {
            this.consume("false");
        } else {
            this.consume("!");
            E_Recognizer();
        }
        if (debug) {
            System.out.println("E Recognizer terminated " + input);
        }
    }

    // Match everything that matches an L
    public void L_Recognizer() {
        if (debug) {        
            System.out.println("Running L Recognizer " + input);
        }
        input = input.trim();
        if (S_Checker()) {
            S_Recognizer();
            L_Recognizer();
        }
        if (debug) {
            System.out.println("L Recognizer terminated " + input);
        }
    }

    public boolean S_Checker() {
        input = input.trim();
        return (
            input.indexOf("{") == 0 ||
            input.indexOf("System.out.println") == 0 ||
            input.indexOf("if") == 0 ||
            input.indexOf("while") == 0
        );
    }

    // Match everything that matches an S
    public void S_Recognizer() {
        if (debug) {
            System.out.println("Running S Recognizer " + input);
        }
        input = input.trim();
        if (input.charAt(0) == '{') {
            this.consume("{");
            L_Recognizer();
            this.consume("}");
        } else if (input.charAt(0) == 'S') {
            this.consume("System.out.println");
            this.E_Parentheses_Recognizer();
            this.consume(";");
        } else if (input.charAt(0) == 'i') {
            this.consume("if");
            this.E_Parentheses_Recognizer();
            S_Recognizer();
            this.consume("else");
            S_Recognizer();
        } else if (input.charAt(0) == 'w') {
            this.consume("while");
            this.E_Parentheses_Recognizer();
            S_Recognizer();
        } else {
            parse_error();
        }
        if (debug) {
            System.out.println("S Recognizer terminated " + input);
        }
    }

    // Match ( E )
    public void E_Parentheses_Recognizer() {
        if (debug) {
            System.out.println("Running E Paren Recognizer " + input);
        }
        input = input.trim();
        // System.out.println(input);
        this.consume("(");
        E_Recognizer();
        this.consume(")");
        if (debug) {
            System.out.println("E Paren Recognizer terminated " + input);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = "";

        while (sc.hasNext()) {
            input += sc.nextLine();
        }

        Parse parser = new Parse(input);

        parser.parse(input);
        System.out.println("Program parsed successfully");
    }
}