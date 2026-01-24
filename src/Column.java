//every Column represents a cell on the board where the values for each row under it can be traversed.

public class Column extends Node{
    int size;
    String name;
    public Column(String name) {
        super();
        this.name = name;
        this.size = 0;
        this.column = this;
    }

    public void linkRight(Node a, Node b){
        b.right = a.right;
        b.left = a;
        a.right.left = b;
        a.right = b;

        //DLX insertion (horizontal)
    }

    public void linkDown(Column column, Node node) {
        node.down = column;
        node.up = column.up;

        column.up.down = node;
        column.up = node;

        node.column = column;
        column.size++;

        //DLX insertion of a node in a column (vertical)
    }

}
