import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws Exception {
        String grammarInputFile = "grammar.txt";
        new LR1(grammarInputFile);
        // String inputFilePath = "input.txt";
        // BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath));
        // String line;
        // String content = "";
        // while ((line = bufferedReader.readLine()) != null) {
        //     content += line;
        // }
        // Stack<String> symbolStack = new Stack<>();
        // Stack<Integer> stateStack = new Stack<>();
        
    }
}