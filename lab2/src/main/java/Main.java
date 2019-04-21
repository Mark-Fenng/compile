import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Stack;
import Lexer.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String filePath = "./hello.vs";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        String content = "";
        while ((line = bufferedReader.readLine()) != null)
            content += line;
        bufferedReader.close();
        analyse analyse = new analyse(content);
        analyse.parse();
        analyse.getTokenList().add(new token("", LR1.endString, 0, LR1.endString));
        System.out.println("Token table:");
        System.out.println("Token type value token value");
        for (token t : analyse.getTokenList()) {
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

        String grammarInputFile = "grammar_rules.txt";
        new LR1(grammarInputFile);
        int top = 0;
        Stack<String> symbolStack = new Stack<>();
        Stack<Integer> stateStack = new Stack<>();
        symbolStack.push(LR1.endString);
        stateStack.push(0);
        while (true) {
            String symbol = analyse.getTokenList().get(top).getTokenValue();
            if (LR1.actionTable.get(stateStack.peek()).containsKey(symbol)) {
                String action = LR1.actionTable.get(stateStack.peek()).get(symbol);
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
                            stackFormulaRight.push(symbolStack.pop());
                            stateStack.pop();
                        }
                        for (String str : formulaRight) {
                            if (!str.equals(stackFormulaRight.pop())) {
                                throw new Exception("Error occurs when reducing");
                            }
                        }
                    }
                    System.out.println(formula.toString());
                    symbolStack.push(formula.getPrefix());
                    System.out.println("Action " + action);
                    System.out.println(symbolStack);
                    System.out.println(stateStack);
                    System.out.println();
                    if (LR1.gotoTable.get(stateStack.peek()).containsKey(symbolStack.peek())) {
                        int oldState = stateStack.peek();
                        stateStack.push(LR1.gotoTable.get(stateStack.peek()).get(symbolStack.peek()));
                        System.out.println("goto(" + oldState + "," + symbolStack.peek() + ")");
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