package Lexer;

public class Token {
    private String type;
    private String originWord;
    private int tableIndex;
    private String tokenValue;
    private int lineNumber;

    public Token(String type, String word, int index, String tokenValue, int lineNumber) {
        this.type = type;
        this.originWord = word;
        this.tableIndex = index;
        this.tokenValue = tokenValue;
        this.lineNumber = lineNumber;
    }

    /**
     * @return the lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the tokenValue
     */
    public String getTokenValue() {
        return tokenValue;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the tableIndex
     */
    public int getTableIndex() {
        return tableIndex;
    }

    /**
     * @return the originWord
     */
    public String getOriginWord() {
        return originWord;
    }

    /**
     * @param originWord the originWord to set
     */
    public void setOriginWord(String originWord) {
        this.originWord = originWord;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token) {
            Token objToken = (Token) obj;
            return objToken.getOriginWord().equals(this.originWord) && objToken.getTableIndex() == this.tableIndex
                    && objToken.getTokenValue().equals(this.tokenValue) && objToken.getType().equals(this.type);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.originWord;
    }
}
// reserved, operator, punctuation, variable, number, comment