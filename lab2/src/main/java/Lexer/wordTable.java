package Lexer;

import java.util.ArrayList;
import java.util.List;

public class wordTable {
    private String type;
    private List<String> wordList = new ArrayList();

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
     * @param wordList the wordList to set
     */
    public void setWordList(List<String> wordList) {
        this.wordList.addAll(wordList);
    }

    public int addWord(String word) {
        if (!wordList.contains(word)) {
            this.wordList.add(word);
        }
        return wordList.indexOf(word);

    }

    /**
     * @return the wordList
     */
    public List<String> getWordList() {
        return wordList;
    }
}