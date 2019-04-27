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

}