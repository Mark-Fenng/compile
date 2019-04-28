package Grammar;

import java.util.*;

import Lexer.*;

public class AST {
    private Node root = null;
    List<String> processMessage = new ArrayList<>();
    private static List<String> numType = new ArrayList<>();
    private List<String> symbolStackState = new ArrayList<>();
    private List<String> stateStackState = new ArrayList<>();
    private static List<String> errorMessage = new ArrayList<>();
    private String variableTable = "variable";
    private String tempTable = "temp";
    private String tempSymbol = "$";
    static {
        numType.add("boolean");
        numType.add("integer");
        numType.add("float");
    }

    public AST(Analyse analyse) throws Exception {
        generateAST(analyse);
    }

    public static void addErrorMessage(String error) {
        errorMessage.add(error);
    }

    /**
     * @return the symbolStackState
     */
    public List<String> getSymbolStackState() {
        return symbolStackState;
    }

    /**
     * @return the stateStackState
     */
    public List<String> getStateStackState() {
        return stateStackState;
    }

    /**
     * @return the errorMessage
     */
    public List<String> getErrorMessage() {
        return errorMessage;
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

    public Formula getFormula(Node parent, List<Node> children) {
        String formula = "";
        formula = parent.getToken().getTokenValue();
        formula += LR1.separatorString;
        for (Node node : children) {
            formula += node.getToken().getTokenValue();
            formula += " ";
        }
        return new Formula(formula.substring(0, formula.length() - 1));
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
            if (symbol.getToken().getType().equals("comment")) {
                top += 1;
                continue;
            }
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
                    symbolStackState.add(symbolStack.toString());
                    stateStackState.add(stateStack.toString());
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
                    symbolStack.push(formulaLeft);
                    processMessage.add("Action " + action + "  " + formula.toString());
                    symbolStackState.add(symbolStack.toString());
                    stateStackState.add(stateStack.toString());
                    if (LR1.gotoTable.get(stateStack.peek())
                            .containsKey(symbolStack.peek().getToken().getTokenValue())) {
                        int oldState = stateStack.peek();
                        stateStack.push(LR1.gotoTable.get(stateStack.peek())
                                .get(symbolStack.peek().getToken().getTokenValue()));
                        processMessage
                                .add("goto(" + oldState + "," + symbolStack.peek().getToken().getTokenValue() + ")");
                        symbolStackState.add(symbolStack.toString());
                        stateStackState.add(stateStack.toString());
                    }
                } else if (action.equals("acc")) {
                    processMessage.add("Accepted");
                    this.root = symbolStack.pop();
                    break;
                } else {
                    throw new Exception("Unsupported action type");
                }
            } else {
                this.addErrorMessage("Error at Line: " + analyse.getTokenList().get(top).getLineNumber()
                        + " [Semantics analyse error near word: " + analyse.getTokenList().get(top).getOriginWord()
                        + "]");
                return;
            }
        }
    }
}