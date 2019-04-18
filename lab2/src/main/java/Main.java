import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String grammarInputFile = "grammar.txt";
        List<String> terminators = new ArrayList<>(), non_terminators = new ArrayList<>();
        List<Formula> grammars = new ArrayList<>();
        readGrammar(terminators, non_terminators, grammars, grammarInputFile);
    }

    public static void readGrammar(List<String> terminators, List<String> non_terminators, List<Formula> grammars,
            String filePath) throws IOException {
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