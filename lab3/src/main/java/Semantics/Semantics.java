package Semantics;

import java.util.*;
import Lexer.*;
import Grammar.*;

public class Semantics {
    private static List<String> errorMessage = new ArrayList<>();
    private static List<Variable> variableTable = new ArrayList<>();
    private static List<Triad> triadTable = new ArrayList<>();
    private static List<Quad> quadTable = new ArrayList<>();

    public void action(Formula formula, List<Token> tokenList) {
        System.out.println(formula + " " + tokenList.toString());
    }

    public static int addVariable(Variable variable) {
        if (!variableTable.contains(variable)) {
            variableTable.add(variable);
            return variableTable.size() - 1;
        } else {
            return -1;
        }
    }

    public static Variable getVariable(String variable) {
        for (Variable v : variableTable) {
            if (v.getSymbol().equals(variable)) {
                return v;
            }
        }
        return null;
    }

    /**
     * @return the variableTable
     */
    public static List<Variable> getVariableTable() {
        return variableTable;
    }

    public static void addErrorMessage(String err) {
        errorMessage.add(err);
    }

    /**
     * @return the errorMessage
     */
    public static List<String> getErrorMessage() {
        return errorMessage;
    }

    public static void clearAll() {
        variableTable = new ArrayList<>();
        errorMessage = new ArrayList<>();
    }
}