import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ItemSet {
    private List<Item> closure = new ArrayList<>();
    private List<Item> initItems = new LinkedList<>();

    ItemSet(List<Item> initItems) {
        for (Item item : initItems) {
            if (this.initItems.contains(item)) {
                this.initItems.get(this.initItems.indexOf(item)).addSearchSymbol(item.getSearchSymbol());
            }else{
                this.initItems.add(item);
            }
        }
    }

    public void computeClosure() {
        this.closure = getClosure(initItems);
        for (Item item : this.closure) {
            if (!item.shouldReduce() && item.getSymbol().equals(LR1.nullString)) {
                item.setState(1);
            }
        }
    }

    /**
     * get closure of item list as parameter: initItems
     */
    public static List<Item> getClosure(List<Item> initItems) {
        List<Item> itemSet = new ArrayList<>();
        for (Item item : initItems) {
            itemSet.addAll(getClosure(item));
        }
        return itemSet;
    }

    /**
     * get closure of item as parameter: initItem
     * 
     * @param initItem the init item whose closure needed to be computed
     * @return initItem's closure
     */
    public static List<Item> getClosure(Item initItem) {
        // init list to store Item in closure
        List<Item> itemSet = new ArrayList<>();
        itemSet.add(initItem);
        // init set to store added item in one loop round
        Set<Item> addItemSet;
        // flag if the itemSet has changed in one loop round
        boolean changedFlag = false;
        do {
            changedFlag = false;
            addItemSet = new HashSet<>();
            for (Item item : itemSet) {
                if (!item.shouldReduce()) {
                    int state = item.getState();
                    String prefix = item.getSymbols().get(state);

                    // search formula in grammars, whose prefix equals symbol of old item
                    for (Formula formula : LR1.grammars) {
                        if (formula.getPrefix().equals(prefix)) {
                            Item newItem = new Item(formula);
                            String tempString = (state + 1 < item.getSymbols().size())
                                    ? item.getSymbols().get(state + 1)
                                    : "";

                            // the item with the same formula and state has been added before
                            if (itemSet.contains(newItem)) {
                                newItem = itemSet.get(itemSet.indexOf(newItem));
                                if (newItem.addSearchSymbol(LR1.getFirstSet(tempString, item.getSearchSymbol()))) {
                                    changedFlag = true;
                                }
                            } else {
                                newItem.addSearchSymbol(LR1.getFirstSet(tempString, item.getSearchSymbol()));
                                changedFlag = true;
                                addItemSet.add(newItem);
                            }
                        }
                    }
                }
            }
            itemSet.addAll(addItemSet);
        } while (changedFlag);
        return itemSet;
    }

    /**
     * @return the items
     */
    public List<Item> getClosure() {
        return closure;
    }

    /**
     * @return the initItems
     */
    public List<Item> getInitItems() {
        return initItems;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemSet) {
            ItemSet itemSet = (ItemSet) obj;
            for (Item item : this.initItems) {
                if (itemSet.getInitItems().contains(item)) {
                    Item itemTemp = itemSet.getInitItems().get(itemSet.getInitItems().indexOf(item));
                    if (!(itemTemp.getSearchSymbol().containsAll(item.getSearchSymbol())
                            && item.getSearchSymbol().containsAll(itemTemp.getSearchSymbol())))
                        return false;
                } else {
                    return false;
                }
            }
            for (Item item : itemSet.getInitItems()) {
                if (this.initItems.contains(item)) {
                    Item itemTemp = this.initItems.get(this.initItems.indexOf(item));
                    if (!(itemTemp.getSearchSymbol().containsAll(item.getSearchSymbol())
                            && item.getSearchSymbol().containsAll(itemTemp.getSearchSymbol())))
                        return false;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;

    }

    @Override
    public String toString() {
        return closure.toString();
    }
}