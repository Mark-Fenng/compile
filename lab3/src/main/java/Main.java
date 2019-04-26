import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import Lexer.*;
import Semantics.Semantics;
import Grammar.*;
import Gui.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String grammarInputFile = "grammar.txt";
        new LR1(grammarInputFile);
        new Gui();
        // Semantics semantics = new Semantics();
        // ast.dfs(ast.getRoot());
    }
}