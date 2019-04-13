import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "./lab1/input.c";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        String content = "";
        while ((line = bufferedReader.readLine()) != null)
            content += line;
        analyse analyse = new analyse(content);
        analyse.parse();
        System.out.println("Token table:");
        System.out.println("Token type  value");
        for (token t : analyse.getTokenList()) {
            System.out.print("< " + t.getType() + " ,");
            System.out.print(" " + t.getOriginWord() + " >");
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