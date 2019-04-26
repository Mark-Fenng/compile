package Grammar;

import java.util.*;
import Lexer.*;

public class Node {
    private List<Node> children = new LinkedList<>();
    private Token token;
    private String type;
    private Object value;
    private Node parent = null;

    public Node(Token token) {
        this.token = token;
    }

    /**
     * @return the children
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    public void addChild(Node childNode) {
        this.children.add(childNode);
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node nodeObj = (Node) obj;
            return nodeObj.getToken().equals(this.token);
        }
        return false;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public boolean hasChildren() {
        return this.children.size() != 0;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return token.toString();
    }
}