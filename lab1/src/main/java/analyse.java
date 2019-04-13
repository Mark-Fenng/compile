import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class analyse {
    private final wordTable reserved = new wordTable();
    private final wordTable variable = new wordTable();
    private final wordTable punctuation = new wordTable();
    private final List<token> tokenList = new ArrayList<>();
    private final String content;
    private int index = 0;

    analyse(String content) {

        reserved.setWordList(Arrays.asList("return", "void", "int", "float", "bool", "if", "else", "while"));
        punctuation.setWordList(Arrays.asList("=", ";", "(", ")", ",", "{", "}"));

        this.content = content;

    }

    void parse() {
        try {
            while (index < content.length()) {
                System.out.print(getChar(index));
                if (Character.isDigit(getChar(index)) || getChar(index) == '-') {
                    String digit = getDigit();
                    tokenList.add(new token("number", digit));
                } else if (Character.isLetter(getChar(index)) || getChar(index) == '_') {
                    String word = getWord();
                    if (reserved.getWordList().contains(word)) {
                        tokenList.add(new token("reserved", word));
                    } else {
                        tokenList.add(new token("variable", word));
                        variable.addWord(word);
                    }
                } else if (getChar(index) == '/' && index + 1 < content.length() && getChar(index + 1) == '*') {
                    String comment = getComment();
                    tokenList.add(new token("comment", comment));
                } else {
                    if (punctuation.getWordList().contains("" + getChar(index))) {
                        if (!(getChar(index) == '=' && index + 1 < content.length() && getChar(index + 1) == '=')) {
                            tokenList.add(new token("punctuation", "" + getChar(index)));
                            index += 1;
                            continue;
                        }
                    }
                    String operator = getOperator();
                    if (!operator.equals("")) {
                        tokenList.add(new token("operator", operator));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getDigit() throws Exception {
        String digit = "";
        boolean negativeFlag = getChar(index) == '-' ? true : false;
        boolean floatFlag = false;
        while (Character.isDigit(getChar(index)) || getChar(index) == '-' || getChar(index) == '.') {
            if (getChar(index) == '-' && negativeFlag) {
                throw new Exception("Wrong digit");
            }
            if (getChar(index) == '.') {
                if (floatFlag) {
                    throw new Exception("Wrong digit");
                } else {
                    floatFlag = true;
                }
            }
            digit += getChar(index);
            index += 1;
        }
        return digit;
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
        case '*':
            operator += '*';
            index += 1;
            break;
        case '/':
            operator += '/';
            index += 1;
            break;
        case '!':
            if (index + 1 < content.length() && getChar(index) == '=') {
                operator += "!=";
                index += 1;
            } else
                operator += '!';
            index += 1;
            break;
        case '&':
            if (index + 1 < content.length() && getChar(index) == '&') {
                operator += "&&";
                index += 1;
            } else {
                operator += '&';
            }
            index += 1;
            break;
        case '|':
            if (index + 1 < content.length() && getChar(index) == '|') {
                operator += "||";
                index += 1;
            } else {
                operator += '|';
            }
            index += 1;
            break;
        case '=':
            if (index + 1 < content.length() && getChar(index) == '=') {
                operator += "==";
                index += 1;
            }
            index += 1;
            break;
        case '>':
            if (index + 1 < content.length() && getChar(index) == '=') {
                operator += ">=";
                index += 1;
            } else {
                operator += '>';
            }
            index += 1;
            break;
        case '<':
            if (index + 1 < content.length() && getChar(index) == '=') {
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
    public List<token> getTokenList() {
        return tokenList;
    }

    /**
     * @return the variable
     */
    public wordTable getVariable() {
        return variable;
    }
}