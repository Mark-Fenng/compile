import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "./input.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        String content = "";
        while ((line = bufferedReader.readLine()) != null)
            content += line;
        analyse analyse = new analyse(content);
        analyse.parse();
        System.out.println("Token table:");
        System.out.println("Token type value token value");
        for (token t : analyse.getTokenList()) {
            System.out.print("< " + t.getType() + " ,");
            System.out.print(" " + t.getOriginWord() + " ,");
            System.out.print(" " + t.getTokenValue() + " >");
            System.out.println();
        }
        System.out.println();
        System.out.println("Variable table:");
        for (String str : analyse.getVariable().getWordList()) {
            System.out.println(str);
        }
        bufferedReader.close();
    }
}