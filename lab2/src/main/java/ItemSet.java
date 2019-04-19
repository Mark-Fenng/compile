import java.util.ArrayList;
import java.util.List;

public class ItemSet {
    private List<Item> items = new ArrayList<>();

    ItemSet(List<Item> items) {
        this.items = items;
    }

    /**
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ItemSet) && ((ItemSet) obj).getItems().get(0).equals(this.items.get(0));
    }

    @Override
    public String toString() {
        return items.toString();
    }
}