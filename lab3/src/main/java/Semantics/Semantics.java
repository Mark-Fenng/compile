package Semantics;

import java.util.*;
import Lexer.*;
import Grammar.*;

public class Semantics {
    public void action(Formula formula, List<Token> tokenList) {
        System.out.println(formula + " " + tokenList.toString());
    }
}