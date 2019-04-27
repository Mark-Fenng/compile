package Grammar;

import java.util.*;

import Lexer.*;
import Semantics.*;

public class AST {
    private Node root = null;
    List<String> processMessage = new ArrayList<>();
    private static List<String> numType = new ArrayList<>();
    private static List<String> symbolStackState = new ArrayList<>();
    private static List<String> stateStackState = new ArrayList<>();
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
            break;
        case 2: // primary_expression:IDENTIFIER
            break;
        case 3: // primary_expression:L_PAREN expression R_PAREN
            break;
        case 4: // postfix_expression:primary_expression
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
                    String type1, type2, operator;
                    Node root = formulaLeft;
                    Quad quad;
                    Token tempToken;
                    switch (formulaIndex) {
                    case 0:
                        break;
                    case 1: // primary_expression:CONSTANT
                        root.getChildren().get(0).getAttributes().put("type",
                                root.getChildren().get(0).getToken().getType());
                        root.getAttributes().put("type", root.getChildren().get(0).getToken().getType());
                        root.getAttributes().put("table", null);
                        root.getAttributes().put("symbol", root.getChildren().get(0).getToken());
                        break;
                    case 2: // primary_expression:IDENTIFIER
                        variable = Semantics.getVariable(root.getChildren().get(0).getToken().getOriginWord());
                        if (variable == null) {
                            Semantics.addErrorMessage("Error at Line: "
                                    + root.getChildren().get(0).getToken().getLineNumber() + " [The variable "
                                    + root.getChildren().get(0).getToken().getOriginWord() + " has not been declared]");
                        } else {
                            root.getAttributes().put("type", variable.getType());
                            root.getAttributes().put("table", this.variableTable);
                            root.getAttributes().put("symbol", root.getChildren().get(0).getToken());
                        }
                        break;
                    case 3: // primary_expression:L_PAREN expression R_PAREN
                        root.getAttributes().putAll(root.getChildren().get(1).getAttributes());
                        break;
                    case 4: // postfix_expression:primary_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 5: // postfix_expression:postfix_expression L_BRACK expression R_BRACK
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 6: // postfix_expression:postfix_expression L_PAREN R_PAREN
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 7:
                        break;
                    case 8: // unary_expression:postfix_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 9: // unary_expression:NOT postfix_expression
                        if (!root.getChildren().get(1).getType().equals("boolean")) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(0).getToken().getLineNumber()
                                            + " [Not operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", "boolean");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, "boolean", null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad("!", root.getChildren().get(1).getSymbol(), null, tempToken);
                        }
                        break;
                    case 10: // unary_expression:SUB postfix_expression
                        if (!root.getChildren().get(1).getType().equals("integer")
                                && !root.getChildren().get(1).getType().equals("float")) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(0).getToken().getLineNumber()
                                            + " [Sub operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", root.getChildren().get(1).getType());
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, root.getChildren().get(1).getType(), null,
                                    4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad("-", root.getChildren().get(1).getSymbol(), null, tempToken);
                        }
                        break;
                    case 11: // multiplicative_expression:unary_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 12: // multiplicative_expression:multiplicative_expression MUL postfix_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber()
                                            + " [Multiply operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", type1.equals(type2) ? type1 : "float");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, type1.equals(type2) ? type1 : "float",
                                    null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad("*", root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 13: // multiplicative_expression:multiplicative_expression DIV postfix_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber()
                                            + " [Division operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", type1.equals(type2) ? type1 : "float");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, type1.equals(type2) ? type1 : "float",
                                    null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad("/", root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 14: // multiplicative_expression:multiplicative_expression MOD postfix_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if (!type1.equals("integer") && !type2.equals("integer")) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber()
                                            + " [Modulo operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", "integer");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, "integer", null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad("%", root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 15: // additive_expression:multiplicative_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 16: // additive_expression:additive_expression ADD multiplicative_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber()
                                            + " [Add operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", type1.equals(type2) ? type1 : "float");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, type1.equals(type2) ? type1 : "float",
                                    null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad("+", root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 17: // additive_expression:additive_expression SUB multiplicative_expression
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber()
                                            + " [Sub operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", type1.equals(type2) ? type1 : "float");
                            root.getAttributes().put("type", type1.equals(type2) ? type1 : "float");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, type1.equals(type2) ? type1 : "float",
                                    null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad("-", root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 18: // relational_expression:additive_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 19: // relational_expression:relational_expression LT additive_expression
                        operator = "<";
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber() + " ["
                                            + operator + " operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", "boolean");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, "boolean", null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad(operator, root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 20: // relational_expression:relational_expression GT additive_expression
                        operator = ">";
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber() + " ["
                                            + operator + " operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", "boolean");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, "boolean", null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad(operator, root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 21: // relational_expression:relational_expression LE additive_expression
                        operator = "<=";
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber() + " ["
                                            + operator + " operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", "boolean");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, "boolean", null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad(operator, root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 22: // relational_expression:relational_expression GE additive_expression
                        operator = ">=";
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber() + " ["
                                            + operator + " operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", "boolean");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, "boolean", null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad(operator, root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 23: // equality_expression:relational_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 24: // equality_expression:equality_expression EQ relational_expression
                        operator = "==";
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber() + " ["
                                            + operator + " operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", "boolean");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, "boolean", null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad(operator, root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 25: // equality_expression:equality_expression NEQ relational_expression
                        operator = "!=";
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber() + " ["
                                            + operator + " operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", "boolean");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, "boolean", null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad(operator, root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 26: // logical_and_expression:equality_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 27: // logical_and_expression:logical_and_expression AND equality_expression
                        operator = "&";
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber() + " ["
                                            + operator + " operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", "boolean");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, "boolean", null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad(operator, root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 28: // logical_or_expression:logical_and_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 29: // logical_or_expression:logical_or_expression OR logical_and_expression
                        operator = "|";
                        type1 = root.getChildren().get(0).getType();
                        type2 = root.getChildren().get(2).getType();
                        if ((!type1.equals("integer") && !type1.equals("float"))
                                || (!type2.equals("integer") && !type2.equals("float"))) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(1).getToken().getLineNumber() + " ["
                                            + operator + " operator can't be used to other type]");
                        } else {
                            root.getAttributes().put("type", "boolean");
                            index = Semantics.getTempVariableTable().size() - 1;
                            // generate temp variable
                            variable = new Variable(this.tempSymbol + index, "boolean", null, 4, 4 * index);
                            Semantics.addTempVariable(variable);
                            root.getAttributes().put("table", this.tempTable);
                            // generate token
                            tempToken = new Token("temp", this.tempSymbol + index, 0, this.tempSymbol + index, -1);
                            root.getAttributes().put("symbol", tempToken);
                            // generate code
                            quad = new Quad(operator, root.getChildren().get(0).getSymbol(),
                                    root.getChildren().get(2).getSymbol(), tempToken);
                        }
                        break;
                    case 30: // assignment_expression:logical_or_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 31: // assignment_expression:postfix_expression assignment_operator
                             // assignment_expression
                        operator = "=";
                        type2 = root.getChildren().get(2).getType();
                        if (!numType.contains(type2)) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(0).getSymbol().getLineNumber() + " ["
                                            + operator + " operator can't be used to other type]");
                        } else {
                            if (root.getChildren().get(0).getSymbol() != null) {
                                if (root.getChildren().get(0).getTable() == null) {
                                    Semantics.addErrorMessage(
                                            "Error at Line: " + root.getChildren().get(0).getSymbol().getLineNumber()
                                                    + " [Can't assign value to constant]");
                                } else {
                                    variable = Semantics
                                            .getVariable(root.getChildren().get(0).getSymbol().getOriginWord());
                                    if (variable != null) {
                                        variable.setType(type2);
                                    }
                                    root.getChildren().get(0).getAttributes().put("type",
                                            root.getChildren().get(2).getType());
                                    // generate code
                                    quad = new Quad(operator, root.getChildren().get(2).getSymbol(), null,
                                            root.getChildren().get(2).getSymbol());
                                }
                            }
                        }
                        break;
                    case 32: // argument_expression_list:assignment_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 33:
                        break;
                    case 34: // argument_expression_list:assignment_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
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
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(0).getToken().getLineNumber()
                                            + " [Variable " + root.getChildren().get(0).getToken().getOriginWord()
                                            + " has been declared before]");
                        }
                        variable.setOffset(index * 4);
                        break;
                    case 49: // init_declarator:IDENTIFIER ASSIGN initializer
                        variable = new Variable(root.getChildren().get(0).getToken().getOriginWord(),
                                root.getChildren().get(2).getType(),
                                root.getChildren().get(2).getToken().getOriginWord(), 4, 0);
                        index = Semantics.addVariable(variable);
                        if (index == -1) {
                            Semantics.addErrorMessage(
                                    "Error at Line: " + root.getChildren().get(0).getToken().getLineNumber()
                                            + " [Variable has been declared before]");
                        }
                        variable.setOffset(index * 4);
                        break;
                    case 50: // identifier_list:IDENTIFIER
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
                        break;
                    case 51:
                        break;
                    case 52: // initializer:assignment_expression
                        root.getAttributes().putAll(root.getChildren().get(0).getAttributes());
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