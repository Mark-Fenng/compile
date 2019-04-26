package Grammar;

import java.util.*;

import Lexer.*;
import Semantics.Semantics;
import Semantics.Variable;

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
        int formulaIndex = -1;
        if (root.hasChildren()) {
            formulaIndex = LR1.grammars.indexOf(getFormula(root, root.getChildren()));
        }

        for (Node node : root.getChildren()) {
            dfs(node);
        }
        switch (formulaIndex) {
        case 0:
            break;
        case 1:
            break;
        case 2:
            break;
        case 3:
            break;
        case 4:
            break;
        case 5:
            break;
        case 6:
            break;
        case 7:
            break;
        case 8:
            break;
        case 9:
            break;
        case 10:
            break;
        case 11:
            break;
        case 12:
            break;
        case 13:
            break;
        case 14:
            break;
        case 15:
            break;
        case 16:
            break;
        case 17:
            break;
        case 18:
            break;
        case 19:
            break;
        case 20:
            break;
        case 21:
            break;
        case 22:
            break;
        case 23:
            break;
        case 24:
            break;
        case 25:
            break;
        case 26:
            break;
        case 27:
            break;
        case 28:
            break;
        case 29:
            break;
        case 30:
            break;
        case 31:
            break;
        case 32:
            break;
        case 33:
            break;
        case 34:
            break;
        case 35:
            break;
        case 36:
            break;
        case 37:
            break;
        case 38:
            break;
        case 39:
            break;
        case 40:
            break;
        case 41:
            break;
        case 42:
            break;
        case 43:
            break;
        case 44:
            break;
        case 45: // declaration:VAL init_declarator_list SEMICOLON

            break;
        case 46:
            break;
        case 47:
            break;
        case 48: // init_declarator:IDENTIFIER
            Variable variable = new Variable(root.getChildren().get(0).getToken().getOriginWord(), "", null, 4, 0);
            int index = Semantics.addVariable(variable);
            if (index == -1) {
                System.out.println("Variable has been declared before Line:"
                        + root.getChildren().get(0).getToken().getLineNumber());
            }
            variable.setOffset(index * 4);
            break;
        case 49:
            break;
        case 50:
            break;
        case 51:
            break;
        case 52:
            break;
        case 53:
            break;
        case 54:
            break;
        case 55:
            break;
        case 56:
            break;
        case 57:
            break;
        case 58:
            break;
        case 59:
            break;
        case 60:
            break;
        case 61:
            break;
        case 62:
            break;
        case 63:
            break;
        case 64:
            break;
        case 65:
            break;
        case 66:
            break;
        case 67:
            break;
        case 68:
            break;
        case 69:
            break;
        case 70:
            break;
        case 71:
            break;
        case 72:
            break;
        case 73:
            break;
        case 74:
            break;
        case 75:
            break;
        case 76:
            break;
        case 77:
            break;
        case 78:
            break;
        case 79:
            break;
        case 80:
            break;
        case 81:
            break;
        case 82:
            break;
        case 83:
            break;
        case 84:
            break;
        case 85:
            break;
        case 86:
            break;
        case 87:
            break;
        case 88:
            break;
        case 89:
            break;
        case 90:
            break;
        case 91:
            break;
        case 92:
            break;
        case 93:
            break;
        case 94:
            break;
        default:
            break;
        }

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