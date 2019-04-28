package Grammar;

import java.util.ArrayList;
import java.util.List;

public class Formula {
    private String prefix;
    private List<String> symbols = new ArrayList<>();

    Formula(String formula) {
        String[] splitResult = formula.split(LR1.separatorString);
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

    @Override
    public String toString() {
        String suffix = "";
        for (String str : symbols) {
            suffix += str;
            suffix += " ";
        }
        suffix = suffix.substring(0, suffix.length() - 1);
        return prefix + LR1.separatorString + suffix;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Formula) && ((Formula) obj).toString().equals(this.toString());
    }
}