public class Node {
    Node left;
    Node right;
    Node up;
    Node down;
    Column column;
    Placement placement; // ← metadata

    public Node() {
        left = right = up = down = this;
    }
}
