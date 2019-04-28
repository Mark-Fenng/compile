package Lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Analyse {
    private final WordTable reserved = new WordTable();
    private final WordTable variable = new WordTable();
    private final WordTable punctuation = new WordTable();
    private final WordTable punctuationTokens = new WordTable();
    private final WordTable operators = new WordTable();
    private final WordTable operatorTokens = new WordTable();
    private final List<Token> tokenList = new ArrayList<>();
    private final String content;
    private int index = 0;
    private int lineNumber = 1;

    public Analyse(String content) {

        reserved.setWordList(Arrays.asList("var", "val", "func", "if", "elif", "else", "while", "for", "return",
                "break", "continue"));
        punctuation.setWordList(Arrays.asList("=", ";", ",", "(", ")", "[", "]", "{", "}"));
        punctuationTokens.setWordList(Arrays.asList("ASSIGN", "SEMICOLON", "COMMA", "L_PAREN", "R_PAREN", "L_BRACK",
                "R_BRACK", "L_CURLY", "R_CURLY"));
        operators.setWordList(
                Arrays.asList("+", "-", "*", "/", "!=", " !", "&", "|", "==", ">=", ">", "<=", "<", "&&", "||"));
        operatorTokens.setWordList(Arrays.asList("ADD", "SUB", "MUL", "DIV", "NEQ", "NOT", "AND", "OR", "EQ", "GE",
                "GT", "LE", "LT", "DA", "DO"));
        this.content = content;

    }

    public void parse() {
        try {
            while (index < content.length()) {
                if (getChar(index) == '\n') {
                    index++;
                    lineNumber++;
                    continue;
                }
                if (Character.isDigit(getChar(index))) {
                    List<Object> resultList = getDigit();
                    String digit = (String) resultList.get(0);
                    boolean isFloat = (boolean) resultList.get(1);
                    tokenList.add(new Token(isFloat ? "float" : "integer", digit, 0, "CONSTANT", lineNumber));
                } else if (Character.isLetter(getChar(index)) || getChar(index) == '_') {
                    String word = getWord();
                    if (word.equals("true") || word.equals("false")) {
                        tokenList.add(new Token("boolean", word, 0, "CONSTANT", lineNumber));
                    } else if (reserved.getWordList().contains(word)) {
                        tokenList.add(new Token("reserved", word, reserved.getWordList().indexOf(word),
                                word.toUpperCase(), lineNumber));
                    } else {
                        int tableIndex = variable.addWord(word);
                        tokenList.add(new Token("variable", word, tableIndex, "IDENTIFIER", lineNumber));
                    }
                } else if (getChar(index) == '/' && index + 1 < content.length() && getChar(index + 1) == '*') {
                    String comment = getComment();
                    tokenList.add(new Token("comment", comment, 0, "COMMENT", lineNumber));
                } else {
                    if (punctuation.getWordList().contains("" + getChar(index))) {
                        if (!(getChar(index) == '=' && index + 1 < content.length() && getChar(index + 1) == '=')) {
                            tokenList.add(new Token("punctuation", "" + getChar(index),
                                    punctuation.getWordList().indexOf("" + getChar(index)),
                                    punctuationTokens.getWordList()
                                            .get(punctuation.getWordList().indexOf(String.valueOf(getChar(index)))),
                                    lineNumber));
                            index += 1;
                            continue;
                        }
                    }
                    String operator = getOperator();
                    if (!operator.equals("")) {
                        tokenList
                                .add(new Token("operator", operator,
                                        operators.getWordList().indexOf(String.valueOf(operator)),
                                        operatorTokens.getWordList()
                                                .get(operators.getWordList().indexOf(String.valueOf(operator))),
                                        lineNumber));
                    }
                }
            }
        } catch (Exception e) {
            // System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    List<Object> getDigit() throws Exception {
        String digit = "";
        boolean floatFlag = false;
        while (Character.isDigit(getChar(index)) || getChar(index) == '-' || getChar(index) == '.') {
            if (getChar(index) == '.') {
                if (floatFlag) {
                    throw new Exception("Wrong digit: " + digit + getChar(index));
                } else {
                    floatFlag = true;
                }
            }
            digit += getChar(index);
            index += 1;
        }
        List<Object> returnList = new ArrayList<>();
        returnList.add(digit);
        returnList.add(floatFlag);
        return returnList;
    }

    String getWord() {
        String word = "";
        while (Character.isLetterOrDigit(getChar(index)) || getChar(index) == '_') {
            word += getChar(index);
            index += 1;
        }
        return word;
    }

    String getComment() throws Exception {
        String comment = "";
        while (index + 1 < content.length() && !(getChar(index) == '*' || getChar(index + 1) == '/')) {
            comment += getChar(index);
            index += 1;
        }
        if (index + 1 == content.length())
            throw new Exception("Wrong comment");
        return comment;
    }

    String getOperator() throws Exception {
        String operator = "";
        switch (getChar(index)) {
        case '+':
            operator += '+';
            index += 1;
            break;
        case '-':
            operator += '-';
            index += 1;
            break;
        case '*':
            operator += '*';
            index += 1;
            break;
        case '/':
            operator += '/';
            index += 1;
            break;
        case '!':
            if (index + 1 < content.length() && getChar(index + 1) == '=') {
                operator += "!=";
                index += 1;
            } else
                operator += '!';
            index += 1;
            break;
        case '&':
            if (index + 1 < content.length() && getChar(index + 1) == '&') {
                operator += "&&";
                index += 1;
            } else {
                operator += '&';
            }
            index += 1;
            break;
        case '|':
            if (index + 1 < content.length() && getChar(index + 1) == '|') {
                operator += "||";
                index += 1;
            } else {
                operator += '|';
            }
            index += 1;
            break;
        case '=':
            if (index + 1 < content.length() && getChar(index + 1) == '=') {
                operator += "==";
                index += 1;
            }
            index += 1;
            break;
        case '>':
            if (index + 1 < content.length() && getChar(index + 1) == '=') {
                operator += ">=";
                index += 1;
            } else {
                operator += '>';
            }
            index += 1;
            break;
        case '<':
            if (index + 1 < content.length() && getChar(index + 1) == '=') {
                operator += "<=";
                index += 1;
            } else {
                operator += '<';
            }
            index += 1;
            break;
        case ' ':
            index += 1;
            break;
        default:
        }
        return operator;
    }

    char getChar(int index) {
        return content.charAt(index);
    }

    /**
     * @return the tokenList
     */
    public List<Token> getTokenList() {
        return tokenList;
    }

    /**
     * @return the variable
     */
    public WordTable getVariable() {
        return variable;
    }
}