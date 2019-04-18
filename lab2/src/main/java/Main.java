import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static Set<String> terminators = new HashSet<>(), non_terminators = new HashSet<>();
    public static List<Formula> grammars = new ArrayList<>();
    public static Map<String, Set<String>> firstSets = new HashMap<>();
    public static String nullString = "ε";

    public static void main(String[] args) throws Exception {
        String grammarInputFile = "grammarTest.txt";
        readGrammar(grammarInputFile);
        for (String term : non_terminators) {
            firstSets.put(term, getFirstSet(term));
        }
        System.out.println(firstSets.toString());
    }

    public static Set<String> getFirstSet(String prefix) {
        Set<String> firstSet = new HashSet<>();
        for (Formula formula : grammars) {
            if (formula.getPrefix().equals(prefix)) {
                List<String> symbols = formula.getSymbols();
                int i = 1;
                if (i >= symbols.size() + 1)
                    firstSet.add(nullString);
                while (i <= symbols.size()) {
                    String tempString = symbols.get(i - 1);
                    if (terminators.contains(tempString)) {
                        firstSet.add(tempString);
                        break;
                    }
                    if (non_terminators.contains(tempString)) {
                        if (!prefix.equals(tempString)) {
                            Set<String> tempSet;
                            if (firstSets.containsKey(tempString)) {
                                tempSet = firstSets.get(tempString);
                            } else {
                                tempSet = getFirstSet(tempString);
                            }
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

    public static void readGrammar(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean terminator = false, non_terminator = false, grammar = false;
        while ((line = bufferedReader.readLine()) != null) {
            if (!line.equals("")) {
                if (!line.equals("")) {
                    switch (line.toLowerCase()) {
                    case "terminator":
                        terminator = true;
                        non_terminator = false;
                        grammar = false;
                        break;
                    case "non-terminator":
                        terminator = false;
                        non_terminator = true;
                        grammar = false;
                        break;
                    case "grammar":
                        terminator = false;
                        non_terminator = false;
                        grammar = true;
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
            }
        }
        bufferedReader.close();
    }
}