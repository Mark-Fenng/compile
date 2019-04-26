package Grammar;

import java.util.*;

import Lexer.*;

public class AST {
    private Node root = null;
    List<String> processMessage = new ArrayList<>();

    public AST(Analyse analyse) throws Exception {
        generateAST(analyse);
    }

    /**
     * @return the root
     */
    public Node getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    public void dfs(Node root) {
        for (Node node : root.getChildren()) {
            System.out.println(node);
            dfs(node);
        }
    }

    /**
     * @return the processMessage
     */
    public List<String> getProcessMessage() {
        return processMessage;
    }

    public void generateAST(Analyse analyse) throws Exception {
        int top = 0;
        Stack<Node> symbolStack = new Stack<>();
        Stack<Integer> stateStack = new Stack<>();
        symbolStack.push(new Node(new Token("punctuation", LR1.endString, 0, LR1.endString, -1)));
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
                    processMessage.add("Action " + action);
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
                            new Token("Non-Terminator", formula.getPrefix(), 0, formula.getPrefix(), -1));
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
                    processMessage.add("Action " + action + "  " + formula.toString());

                    // System.out.println(formula.toString());
                    // System.out.println("Action " + action);
                    // System.out.println(symbolStack);
                    // System.out.println(stateStack);
                    // System.out.println();
                    if (LR1.gotoTable.get(stateStack.peek())
                            .containsKey(symbolStack.peek().getToken().getTokenValue())) {
                        int oldState = stateStack.peek();
                        stateStack.push(LR1.gotoTable.get(stateStack.peek())
                                .get(symbolStack.peek().getToken().getTokenValue()));
                        processMessage
                                .add("goto(" + oldState + "," + symbolStack.peek().getToken().getTokenValue() + ")");
                        // System.out.println(symbolStack);
                        // System.out.println(stateStack);
                    }
                } else if (action.equals("acc")) {
                    processMessage.add("Accepted");
                    this.root = symbolStack.pop();
                    break;
                } else {
                    throw new Exception("Unsupported action type");
                }
            } else {
                System.err.println("Error occurred at token " + top);
            }
        }
    }
}