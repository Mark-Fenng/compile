import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Stack;
import Lexer.*;
import Grammar.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String filePath = "./input.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        String content = "";
        while ((line = bufferedReader.readLine()) != null)
            content += line;
        bufferedReader.close();
        Analyse analyse = new Analyse(content);
        analyse.parse();
        analyse.getTokenList().add(new Token("", LR1.endString, 0, LR1.endString));
        System.out.println("Token table:");
        System.out.println("Token type value token value");
        for (Token t : analyse.getTokenList()) {
            System.out.print("< " + t.getType() + " ,");
            System.out.print(" " + t.getOriginWord() + " ,");
            // System.out.print(" " + t.getTableIndex() + " ,");
            System.out.print(" " + t.getTokenValue() + " >");
            System.out.println();
        }
        System.out.println();
        // System.out.println("Variable table:");
        // for (String str : analyse.getVariable().getWordList()) {
        // System.out.println(str);
        // }

        String grammarInputFile = "grammar.txt";
        new LR1(grammarInputFile);
        int top = 0;
        Stack<Token> symbolStack = new Stack<>();
        Stack<Integer> stateStack = new Stack<>();
        symbolStack.push(new Token("punctuation", LR1.endString, 0, LR1.endString));
        stateStack.push(0);
        while (true) {
            Token symbol = analyse.getTokenList().get(top);
            if (LR1.actionTable.get(stateStack.peek()).containsKey(symbol.getTokenValue())) {
                String action = LR1.actionTable.get(stateStack.peek()).get(symbol.getTokenValue());
                String actionType = String.valueOf(action.charAt(0));
                final String SHIFT = "s";
                final String REDUCE = "r";
                Integer actionState = null;
                if (!action.equals("acc")) {
                    actionState = Integer.parseInt(action.substring(1, action.length()));
                }
                if (actionType.equals(SHIFT)) {
                    symbolStack.push(symbol);
                    stateStack.push(actionState);
                    System.out.println("Action " + action);
                    System.out.println(symbolStack);
                    System.out.println(stateStack);
                    System.out.println();
                    top += 1;
                } else if (actionType.equals(REDUCE)) {
                    Formula formula = LR1.grammars.get(actionState);
                    List<String> formulaRight = formula.getSymbols();
                    Stack<String> stackFormulaRight = new Stack<>();
                    if (!(formulaRight.size() == 1 && formulaRight.get(0).equals(LR1.nullString))) {
                        for (int i = 0; i < formulaRight.size(); i++) {
                            stackFormulaRight.push(symbolStack.pop().getTokenValue());
                            stateStack.pop();
                        }
                        for (String str : formulaRight) {
                            if (!str.equals(stackFormulaRight.pop())) {
                                throw new Exception("Error occurs when reducing");
                            }
                        }
                    }
                    // action(formula);
                    System.out.println(formula.toString());
                    symbolStack.push(new Token("Non-Terminator", formula.getPrefix(), 0, formula.getPrefix()));
                    System.out.println("Action " + action);
                    System.out.println(symbolStack);
                    System.out.println(stateStack);
                    System.out.println();
                    if (LR1.gotoTable.get(stateStack.peek()).containsKey(symbolStack.peek().getTokenValue())) {
                        int oldState = stateStack.peek();
                        stateStack.push(LR1.gotoTable.get(stateStack.peek()).get(symbolStack.peek().getTokenValue()));
                        System.out.println("goto(" + oldState + "," + symbolStack.peek().getTokenValue() + ")");
                        System.out.println(symbolStack);
                        System.out.println(stateStack);
                        System.out.println();
                    }
                } else if (action.equals("acc")) {
                    break;
                } else {
                    throw new Exception("Unsupported action type");
                }
            } else {
                System.err.println("Error occurred at token " + top);
                return;
            }
        }
        System.out.println("accepted");
    }
}