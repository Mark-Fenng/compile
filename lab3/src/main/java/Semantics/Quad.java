package Semantics;

import Lexer.*;

public class Quad {
    private String operator;
    private Token v1, v2, result;

    public Quad(String operator, Token v1, Token v2, Token result) {
        this.operator = operator;
        this.v1 = v1;
        this.v2 = v2;
        this.result = result;
    }

    @Override
    public String toString() {

        switch (operator) {
        case "+":
        case "*":
        case "/":
        case "%":
        case "&":
        case "|":
        case "==":
        case "!=":
        case "!":
        case ">":
        case "<":
        case "<=":
        case ">=":
            return result.getOriginWord() + "=" + v1.getOriginWord() + operator + v2.getOriginWord();
        case "-":
            if (v2 == null) {
                return result.getOriginWord() + "=0-" + v1.getOriginWord();
            } else {
                return result.getOriginWord() + "=" + v1.getOriginWord() + operator + v2.getOriginWord();
            }
        case "=":
            return result.getOriginWord() + "=" + v1.getOriginWord();
        case "if":
            return "if " + v1.getOriginWord() + " goto " + result.getOriginWord();
        case "goto":
            return "goto " + result.getOriginWord();
        default:
            return "";
        }
    }

}