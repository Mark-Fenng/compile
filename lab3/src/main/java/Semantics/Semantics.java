package Semantics;

import java.util.*;
import Lexer.*;
import Grammar.*;

public class Semantics {
    private List<Variable> variableTable = new ArrayList<>();
    private List<Triad> triadTable = new ArrayList<>();
    private List<Quad> quadTable = new ArrayList<>();

    public void action(Formula formula, List<Token> tokenList) {
        System.out.println(formula + " " + tokenList.toString());
    }
}