public class Item extends Formula {
    private int state = 0;
    private String searchSymbol = "";

    Item(String formula, int state) {
        super(formula);
        state = state;
    }

    Item(Formula formula) {
        super(formula.toString());
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @return the searchSymbol
     */
    public String getSearchSymbol() {
        return searchSymbol;
    }
}