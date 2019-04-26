import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import Lexer.*;
import Semantics.Semantics;
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
        // System.out.println("Token table:");
        // System.out.println("Token type value token value");
        // for (Token t : analyse.getTokenList()) {
        // System.out.print("< " + t.getType() + " ,");
        // System.out.print(" " + t.getOriginWord() + " ,");
        // // System.out.print(" " + t.getTableIndex() + " ,");
        // System.out.print(" " + t.getTokenValue() + " >");
        // System.out.println();
        // }
        // System.out.println();
        // System.out.println("Variable table:");
        // for (String str : analyse.getVariable().getWordList()) {
        // System.out.println(str);
        // }

        String grammarInputFile = "grammar.txt";
        new LR1(grammarInputFile);
        Semantics semantics = new Semantics();
        AST ast = new AST();

        int top = 0;
        Stack<Node> symbolStack = new Stack<>();
        Stack<Integer> stateStack = new Stack<>();
        symbolStack.push(new Node(new Token("punctuation", LR1.endString, 0, LR1.endString)));
        stateStack.push(0);
        while (true) {
            Node symbol = new Node(analyse.getTokenList().get(top));
            if (LR1.actionTable.get(stateStack.peek()).containsKey(symbol.getToken().getTokenValue())) {
                String action = LR1.actionTable.get(stateStack.peek()).get(symbol.getToken().getTokenValue());
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
                    // System.out.println("Action " + action);
                    // System.out.println(symbolStack);
                    // System.out.println(stateStack);
                    // System.out.println();
                    top += 1;
                } else if (actionType.equals(REDUCE)) {
                    Formula formula = LR1.grammars.get(actionState);
                    List<String> formulaRight = formula.getSymbols();
                    Stack<Node> stackFormulaRight = new Stack<>();
                    Node formulaLeft = new Node(
                            new Token("Non-Terminator", formula.getPrefix(), 0, formula.getPrefix()));
                    List<Node> nodeList = new ArrayList<>();
                    if (!(formulaRight.size() == 1 && formulaRight.get(0).equals(LR1.nullString))) {
                        for (int i = 0; i < formulaRight.size(); i++) {
                            stackFormulaRight.push(symbolStack.pop());
                            stateStack.pop();
                        }
                        for (String str : formulaRight) {
                            Node temp = stackFormulaRight.pop();
                            nodeList.add(temp);
                            if (!str.equals(temp.getToken().getTokenValue())) {
                                throw new Exception("Error occurs when reducing");
                            }
                        }
                    }
                    for (Node node : nodeList) {
                        node.setParent(formulaLeft);
                        formulaLeft.addChild(node);
                    }
                    symbolStack.push(formulaLeft);
                    // System.out.println(formula.toString());
                    // System.out.println("Action " + action);
                    // System.out.println(symbolStack);
                    // System.out.println(stateStack);
                    // System.out.println();
                    if (LR1.gotoTable.get(stateStack.peek())
                            .containsKey(symbolStack.peek().getToken().getTokenValue())) {
                        // int oldState = stateStack.peek();
                        stateStack.push(LR1.gotoTable.get(stateStack.peek())
                                .get(symbolStack.peek().getToken().getTokenValue()));
                        // System.out.println(
                        // "goto(" + oldState + "," + symbolStack.peek().getToken().getTokenValue() +
                        // ")");
                        // System.out.println(symbolStack);
                        // System.out.println(stateStack);
                        // System.out.println();
                    }
                } else if (action.equals("acc")) {
                    ast.setRoot(symbolStack.pop());
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
        ast.dfs(ast.getRoot());
    }
}