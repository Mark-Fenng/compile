package Grammar;

import java.util.*;

import Lexer.*;
import Semantics.Semantics;
import Semantics.Variable;

public class AST {
    private Node root = null;
    List<String> processMessage = new ArrayList<>();
    private static List<String> numType = new ArrayList<>();
    private static List<String> symbolStackState = new ArrayList<>();
    private static List<String> stateStackState = new ArrayList<>();
    static {
        numType.add("boolean");
        numType.add("integer");
        numType.add("float");
    }

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
        int index;
        Variable variable;
        String type1, type2;
        switch (formulaIndex) {
        case 0:
            break;
        case 1: // primary_expression:CONSTANT
            type1 = root.getType();
            type2 = root.getChildren().get(0).getToken().getType();
            if (!type1.equals(type2)) {
                System.out.println(
                        "Can't assign value to constant Line:" + root.getChildren().get(0).getToken().getLineNumber());
            }
            break;
        case 2: // primary_expression:IDENTIFIER
            root.getChildren().get(0).setType(root.getType());
            variable = Semantics.getVariable(root.getChildren().get(0).getToken().getOriginWord());
            variable.setType(root.getType());
            break;
        case 3: // primary_expression:L_PAREN expression R_PAREN
            break;
        case 4: // postfix_expression:primary_expression
            root.getChildren().get(0).setType(root.getType());
            break;
        case 5: // postfix_expression:postfix_expression L_BRACK expression R_BRACK
            break;
        case 6: // postfix_expression:postfix_expression L_PAREN R_PAREN
            break;
        case 7:
            break;
        case 8: // unary_expression:postfix_expression
            break;
        case 9: // unary_expression:NOT postfix_expression
            break;
        case 10: // unary_expression:SUB postfix_expression
            break;
        case 11: // multiplicative_expression:unary_expression
            break;
        case 12: // multiplicative_expression:multiplicative_expression MUL postfix_expression
            break;
        case 13: // multiplicative_expression:multiplicative_expression DIV postfix_expression
            break;
        case 14: // multiplicative_expression:multiplicative_expression MOD postfix_expression
            break;
        case 15: // additive_expression:multiplicative_expression
            break;
        case 16: // additive_expression:additive_expression ADD multiplicative_expression
            break;
        case 17: // additive_expression:additive_expression SUB multiplicative_expression
            break;
        case 18: // relational_expression:additive_expression
            break;
        case 19: // relational_expression:relational_expression LT additive_expression
            break;
        case 20: // relational_expression:relational_expression GT additive_expression
            break;
        case 21: // relational_expression:relational_expression LE additive_expression
            break;
        case 22: // relational_expression:relational_expression GE additive_expression
            break;
        case 23: // equality_expression:relational_expression
            break;
        case 24: // equality_expression:equality_expression EQ relational_expression
            break;
        case 25: // equality_expression:equality_expression NEQ relational_expression
            break;
        case 26: // logical_and_expression:equality_expression
            break;
        case 27: // logical_and_expression:logical_and_expression AND equality_expression
            break;
        case 28: // logical_or_expression:logical_and_expression
            break;
        case 29: // logical_or_expression:logical_or_expression OR logical_and_expression
            break;
        case 30: // assignment_expression:logical_or_expression
            break;
        case 31: // assignment_expression:postfix_expression assignment_operator
                 // assignment_expression
            break;
        case 32: // argument_expression_list:assignment_expression
            break;
        case 33:
            break;
        case 34: // argument_expression_list:assignment_expression
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
            break;
        case 49: // init_declarator:IDENTIFIER ASSIGN initializer
            break;
        case 50: // identifier_list:IDENTIFIER

            break;
        case 51:
            break;
        case 52: // initializer:assignment_expression
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
        for (Node node : root.getChildren()) {
            dfs(node);
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
                    for (Node node : nodeList) {
                        node.setParent(formulaLeft);
                        formulaLeft.addChild(node);
                    }

                    // semantics analyse
                    int formulaIndex = LR1.grammars.indexOf(formula);
                    int index;
                    Variable variable;
                    String type1, type2;
                    Node root = formulaLeft;
                    switch (formulaIndex) {
                    case 0:
                        break;
                    case 1: // primary_expression:CONSTANT
                        root.getChildren().get(0).setType(root.getChildren().get(0).getToken().getType());
                        root.setType(root.getChildren().get(0).getToken().getType());
                        break;
                    case 2: // primary_expression:IDENTIFIER
                        variable = Semantics.getVariable(root.getChildren().get(0).getToken().getOriginWord());
                        root.setType(variable.getType());
                        break;
                    case 3: // primary_expression:L_PAREN expression R_PAREN
                        root.setType(root.getChildren().get(1).getToken().getType());
                        break;
                    case 4: // postfix_expression:primary_expression
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 5: // postfix_expression:postfix_expression L_BRACK expression R_BRACK
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 6: // postfix_expression:postfix_expression L_PAREN R_PAREN
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 7:
                        break;
                    case 8: // unary_expression:postfix_expression
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 9: // unary_expression:NOT postfix_expression
                        if (!root.getChildren().get(1).getType().equals("boolean")) {
                            System.out.println("Not operator can't be used to other type Line:"
                                    + root.getChildren().get(0).getToken().getLineNumber());
                        } else {
                            root.setType("boolean");
                        }
                        break;
                    case 10: // unary_expression:SUB postfix_expression
                        if (!root.getChildren().get(1).getType().equals("integer")
                                && !root.getChildren().get(1).getType().equals("float")) {
                            System.out.println("Sub operator can't be used to other type Line:"
                                    + root.getChildren().get(0).getToken().getLineNumber());
                        } else {
                            root.setType(root.getChildren().get(1).getType());
                        }
                        break;
                    case 11: // multiplicative_expression:unary_expression
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 12: // multiplicative_expression:multiplicative_expression MUL postfix_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("Multiply operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType(type1.equals(type2) ? type1 : "float");
                        }
                        break;
                    case 13: // multiplicative_expression:multiplicative_expression DIV postfix_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("Division operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType(type1.equals(type2) ? type1 : "float");
                        }
                        break;
                    case 14: // multiplicative_expression:multiplicative_expression MOD postfix_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if (!type1.equals("integer") && !type2.equals("integer")) {
                            System.out.println("Modulo operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType(type1.equals(type2) ? type1 : "float");
                        }
                        break;
                    case 15: // additive_expression:multiplicative_expression
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 16: // additive_expression:additive_expression ADD multiplicative_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("Add operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType(type1.equals(type2) ? type1 : "float");
                        }
                        break;
                    case 17: // additive_expression:additive_expression SUB multiplicative_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("Sub operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType(type1.equals(type2) ? type1 : "float");
                        }
                        break;
                    case 18: // relational_expression:additive_expression
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 19: // relational_expression:relational_expression LT additive_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("< operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType("boolean");
                        }
                        break;
                    case 20: // relational_expression:relational_expression GT additive_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("> operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType("boolean");
                        }
                        break;
                    case 21: // relational_expression:relational_expression LE additive_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("<= operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType("boolean");
                        }
                        break;
                    case 22: // relational_expression:relational_expression GE additive_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println(">= operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType("boolean");
                        }
                        break;
                    case 23: // equality_expression:relational_expression
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 24: // equality_expression:equality_expression EQ relational_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("== operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType("boolean");
                        }
                        break;
                    case 25: // equality_expression:equality_expression NEQ relational_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("!= operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType("boolean");
                        }
                        break;
                    case 26: // logical_and_expression:equality_expression
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 27: // logical_and_expression:logical_and_expression AND equality_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("And operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType("boolean");
                        }
                        break;
                    case 28: // logical_or_expression:logical_and_expression
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 29: // logical_or_expression:logical_or_expression OR logical_and_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            System.out.println("Or operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.setType("boolean");
                        }
                        break;
                    case 30: // assignment_expression:logical_or_expression
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 31: // assignment_expression:postfix_expression assignment_operator
                             // assignment_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if (!numType.contains(type1) && !numType.contains(type2)) {
                            System.out.println("Assign operator can't be used to other type Line:"
                                    + root.getChildren().get(1).getToken().getLineNumber());
                        } else {
                            root.getChildren().get(0).setType(root.getChildren().get(2).getType());
                            root.setType(root.getChildren().get(0).getType());
                        }
                        break;
                    case 32: // argument_expression_list:assignment_expression
                        root.setType(root.getChildren().get(0).getType());
                        break;
                    case 33:
                        break;
                    case 34: // argument_expression_list:assignment_expression
                        root.setType(root.getChildren().get(0).getType());
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
                        variable = new Variable(root.getChildren().get(0).getToken().getOriginWord(), "", null, 4, 0);
                        index = Semantics.addVariable(variable);
                        if (index == -1) {
                            System.out.println("Variable has been declared before Line:"
                                    + root.getChildren().get(0).getToken().getLineNumber());
                        }
                        variable.setOffset(index * 4);
                        break;
                    case 49: // init_declarator:IDENTIFIER ASSIGN initializer
                        variable = new Variable(root.getChildren().get(0).getToken().getOriginWord(),
                                root.getChildren().get(2).getType(),
                                root.getChildren().get(2).getToken().getOriginWord(), 4, 0);
                        index = Semantics.addVariable(variable);
                        if (index == -1) {
                            System.out.println("Variable has been declared before Line:"
                                    + root.getChildren().get(0).getToken().getLineNumber());
                        }
                        variable.setOffset(index * 4);
                        break;
                    case 50: // identifier_list:IDENTIFIER

                        break;
                    case 51:
                        break;
                    case 52: // initializer:assignment_expression
                        root.setType(root.getChildren().get(0).getType());
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
                System.err.println("Error occurred at token " + top);
            }
        }
    }
}