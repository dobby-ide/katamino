public class Node {
    Node left;
    Node right;
    Node up;
    Node down;
    Column column;

    public Node() {
        left = right = up = down = this;
    }
}
