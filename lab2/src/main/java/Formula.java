import java.util.ArrayList;
import java.util.List;

public class Formula {
    private String prefix;
    private List<String> symbols = new ArrayList();

    Formula(String formula) {
        String[] splitResult = formula.split("->");
        prefix = splitResult[0];
        String suffix = splitResult[1];
        splitResult = suffix.split(" ");
        for (String str : splitResult) {
            symbols.add(str);
        }
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @return the symbols
     */
    public List<String> getSymbols() {
        return symbols;
    }
}