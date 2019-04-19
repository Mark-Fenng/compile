import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static Set<String> terminators = new HashSet<>(), non_terminators = new HashSet<>();
    public static List<Formula> grammars = new ArrayList<>();
    public static Map<String, Set<String>> firstSets = new HashMap<>(); // first set of all symbols
    public static List<ItemSet> items = new ArrayList<>(); // store all item set
    public static List<HashMap<String, Integer>> gotoTable = new ArrayList<>(); // store goto table for LR(1)
    public static List<HashMap<String, String>> actionTable = new ArrayList<>(); // store action table for LR(1)
    public static String nullString = "ε"; // define the null symbol as variable nullString
    public static String endString = "#"; // define the end symbol as variable endString

    public static void main(String[] args) throws Exception {
        String grammarInputFile = "grammar.txt";
        readGrammar(grammarInputFile);
        for (String term : non_terminators) {
            firstSets.put(term, getFirstSet(term));
        }
        for (String term : terminators) {
            firstSets.put(term, Collections.singleton(term));
        }

        Item rootItem = new Item(grammars.get(0));
        rootItem.addSearchSymbol(Collections.singleton("#"));
        items.add(new ItemSet(getClosure(rootItem)));
        gotoTable.add(new HashMap<>());
        actionTable.add(new HashMap<>());

        getItemSets();
        System.out.println(gotoTable);
    }

    public static void getItemSets() throws Exception {
        Queue<ItemSet> queue = new LinkedList<>();
        queue.add(items.get(0));
        List<Item> itemSet;
        while (queue.size() != 0) {
            ItemSet itemClosure = queue.poll();
            itemSet = itemClosure.getItems();
            for (Item item : itemSet) {
                if (!item.getSymbols().get(0).equals(nullString) && !item.shouldReduce()) {
                    Item newItem = new Item(item.toString(), item.getState() + 1);
                    newItem.addSearchSymbol(item.getSearchSymbol());
                    ItemSet tempSet = new ItemSet(Collections.singletonList(newItem));
                    String symbol = item.getSymbols().get(item.getState());
                    int oldIndex = items.indexOf(itemClosure);
                    int newIndex = oldIndex;
                    if (!items.contains(tempSet)) {
                        tempSet = new ItemSet(getClosure(newItem));
                        items.add(tempSet);
                        gotoTable.add(new HashMap<>());
                        actionTable.add(new HashMap<>());
                        queue.offer(tempSet);
                        newIndex = items.indexOf(tempSet);
                    }
                    if (terminators.contains(symbol)) {
                        actionTable.get(oldIndex).put(symbol, "s" + newIndex);
                    } else if (non_terminators.contains(symbol)) {
                        gotoTable.get(oldIndex).put(symbol, newIndex);
                    } else {
                        throw new Exception("Unsupported symbol:" + symbol);
                    }
                    if (newItem.shouldReduce()) {
                        for (String str : newItem.getSymbols()) {
                            int tempInt = grammars.indexOf(newItem.getFormula());
                            actionTable.get(newIndex).put(str, "r" + tempInt);
                        }
                    }
                }
            }
        }
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
                    for (Formula formula : grammars) {
                        if (formula.getPrefix().equals(prefix)) {
                            Item newItem = new Item(formula);
                            String tempString = (state + 1 < item.getSymbols().size())
                                    ? item.getSymbols().get(state + 1)
                                    : "";

                            // the item with the same formula and state has been added before
                            if (itemSet.contains(newItem)) {
                                newItem = itemSet.get(itemSet.indexOf(newItem));
                                if (newItem.addSearchSymbol(getFirstSet(tempString, item.getSearchSymbol()))) {
                                    changedFlag = true;
                                }
                            } else {
                                newItem.addSearchSymbol(getFirstSet(tempString, item.getSearchSymbol()));
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
     * @return the firstSets
     */
    public static Set<String> getFirstSet(String firstSymbol, Set<String> searchSymbol) {
        Set<String> firstSet = new HashSet<>();
        Set<String> tempSet = null;
        if (!firstSymbol.equals("")) {
            tempSet = firstSets.get(firstSymbol);
            firstSet.addAll(tempSet);
        }
        if (tempSet == null || tempSet.contains(nullString)) {
            for (String str : searchSymbol) {
                firstSet.addAll(firstSets.get(str));
            }
        }
        return firstSet;
    }

    /**
     * get prefix's first set
     * 
     * @param prefix one non-terminator whose first set is needed to be computed
     * @return prefix's first set
     */
    public static Set<String> getFirstSet(String prefix) {
        Set<String> firstSet = new HashSet<>();
        for (Formula formula : grammars) {

            // iterate all grammar formulas to find formulas like this: prefix->...
            if (formula.getPrefix().equals(prefix)) {
                List<String> symbols = formula.getSymbols();
                int i = 1;
                if (i >= symbols.size() + 1)
                    firstSet.add(nullString);
                while (i <= symbols.size()) {
                    String tempString = symbols.get(i - 1);

                    // current symbol is a terminator
                    if (terminators.contains(tempString)) {
                        firstSet.add(tempString);
                        break;
                    }

                    // current symbol is a non-terminator
                    if (non_terminators.contains(tempString)) {
                        if (!prefix.equals(tempString)) {
                            Set<String> tempSet;

                            // the first set of current symbol has been computed before
                            if (firstSets.containsKey(tempString)) {
                                tempSet = firstSets.get(tempString);
                            } else {
                                tempSet = getFirstSet(tempString);
                            }

                            // add all symbol in current symbol's first set except null string
                            for (String str : tempSet) {
                                if (!str.equals(nullString)) {
                                    firstSet.add(str);
                                }
                            }
                            if (!tempSet.contains(nullString)) {
                                break;
                            }
                        }

                    }
                    i += 1;
                }
                if (i == symbols.size() + 1)
                    firstSet.add(nullString);
            }
        }
        return firstSet;
    }

    /**
     * read terminator set, non-terminator set and grammar formulas from file
     * 
     * file format example
     * 
     * null
     * 
     * E
     * 
     * end
     * 
     * #
     * 
     * terminator
     * 
     * a,+,b
     * 
     * non-terminator
     * 
     * C
     * 
     * grammar
     * 
     * C->a + b
     * 
     * @param filePath file path
     * @throws IOException read exception
     */
    public static void readGrammar(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean terminator = false, non_terminator = false, grammar = false, readNull = false, readEnd = false;
        while ((line = bufferedReader.readLine()) != null) {
            if (!line.equals("")) {
                if (!line.equals("")) {
                    switch (line.toLowerCase()) {
                    case "terminator":
                        terminator = true;
                        non_terminator = false;
                        grammar = false;
                        readNull = false;
                        readEnd = false;
                        break;
                    case "non-terminator":
                        terminator = false;
                        non_terminator = true;
                        grammar = false;
                        readNull = false;
                        readEnd = false;
                        break;
                    case "grammar":
                        terminator = false;
                        non_terminator = false;
                        grammar = true;
                        readNull = false;
                        readEnd = false;
                        break;
                    case "null":
                        terminator = false;
                        non_terminator = false;
                        grammar = false;
                        readNull = true;
                        readEnd = false;
                        break;
                    case "end":
                        terminator = false;
                        non_terminator = false;
                        grammar = false;
                        readNull = false;
                        readEnd = true;
                        break;

                    default:
                        break;
                    }
                }
                if (!line.toLowerCase().equals("terminator") && terminator) {
                    for (String str : line.split(",")) {
                        terminators.add(str);
                    }
                }
                if (!line.toLowerCase().equals("non-terminator") && non_terminator) {
                    for (String str : line.split(",")) {
                        non_terminators.add(str);
                    }
                }
                if (!line.toLowerCase().equals("grammar") && grammar) {
                    grammars.add(new Formula(line));
                }
                if (!line.toLowerCase().equals("null") && readNull) {
                    nullString = line;
                }
                if (!line.toLowerCase().equals("end") && readEnd) {
                    endString = line;
                }
            }
        }
        bufferedReader.close();
    }
}