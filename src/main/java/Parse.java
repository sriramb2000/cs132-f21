import java.util.Scanner;

public class Parse {
    private Scanner sc = new Scanner(System.in);
    private String input;

    public boolean parse(String s) {
        String match =  S_Recognizer(s);
        return match.equals("")
    }

    private void parse_error(){
        System.out.println("Parse error");
        System.exit(1);
    }

    public void consume(String match) {
        input.trim();
        if (input.indexOf(match) == 0) {
            input = input.substring(match.length());
        } else {
            parse_error();
        }
    }

    // Match everything that matches an E
    public void E_Recognizer() {
        input.trim();
        if (input.charAt(0) == "t") {
            this.consume("true");
        } else if (input.charAt(0) == "f") {
            this.consume("false");
        } else {
            this.consume("!");
            E_Recognizer();
        }
    }

    // Match everything that matches an L
    public void L_Recognizer() {
        input.trim();
        if (input == "") {
            return;
        } else {
            S_Recognizer();
            L_Recognizer();
        }
    }

    // Match everything that matches an S
    public String S_Recognizer() {
        input.trim();
        if (input.charAt(0) == "{") {
            this.consume("{");
            this.E_Parentheses_Recognizer();
            this.consume("}");
        } else if (input.charAt(0) == "S") {
            this.consume("System.out.println");
            this.E_Parentheses_Recognizer();
            this.consume(";")
        } else if (input.charAt(0) == "i") {
            this.consume("if");
            this.E_Parentheses_Recognizer();
            S_Recognizer();
            this.consume("else");
            S_Recognizer();
        } else {
            this.consume("while");
            this.E_Parentheses_Recognizer();
            S_Recognizer();
        }
    }

    // Match ( E )
    public void E_Parentheses_Recognizer() {
        input.trim();
        this.consume("(");
        E_Recognizer();
        this.consume(")");
    }

    public static void main(String[] args) {
        while (sc.hasNext()) {
            input += sc.nextLine();
        }

        this.parse(input);
        System.out.println("Program parsed successfully");
    }
}