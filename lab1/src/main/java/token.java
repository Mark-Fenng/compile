public class token {
    private String type;
    private String originWord;
    private int typeValue;

    token(String type, String word) {
        this.type = type;
        this.originWord = word;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the typeValue
     */
    public int getTypeValue() {
        return typeValue;
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

    /**
     * @param typeValue the typeValue to set
     */
    public void setTypeValue(int typeValue) {
        this.typeValue = typeValue;
    }
}
// reserved, operator, punctuation, variable, number, comment