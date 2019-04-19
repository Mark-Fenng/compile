import java.util.HashSet;
import java.util.Set;

public class Item extends Formula {
    private int state = 0;
    private Set<String> searchSymbol = new HashSet<>();

    Item(String formula, int state) {
        super(formula);
        this.state = state;
    }

    Item(Formula formula) {
        super(formula.toString());
        this.state = 0;
    }

    Item(Formula formula, int state) {
        super(formula.toString());
        this.state = state;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * add search symbol if all existed, nothing will change
     * 
     * @param searchSymbol the searchSymbol to set
     * @return if have new symbol is added to the set
     */
    public boolean addSearchSymbol(Set<String> searchSymbol) {
        return this.searchSymbol.addAll(searchSymbol);
    }

    /**
     * @return the searchSymbol
     */
    public Set<String> getSearchSymbol() {
        return searchSymbol;
    }

    public boolean shouldReduce() {
        return state == getSymbols().size();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Item) && ((Item) obj).toString().equals(this.toString())
                && ((Item) obj).getState() == this.state;
    }
}