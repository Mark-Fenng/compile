package Grammar;

import java.util.*;

import Lexer.*;

public class AST {
    private Node root = null;

    /**
     * @return the root
     */
    public Node getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    public void dfs(Node root) {
        for (Node node : root.getChildren()) {
            System.out.println(node);
            dfs(node);
        }
    }
}