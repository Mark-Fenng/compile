import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class wordTable {
    private String type;
    private Set<String> wordList = new HashSet<>();

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

    public void addWord(String word) {
        this.wordList.add(word);
    }

    /**
     * @return the wordList
     */
    public Set<String> getWordList() {
        return wordList;
    }
}