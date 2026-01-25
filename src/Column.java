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

    public void cover(Column c){

        //remove column
        c.right.left = c.left; //previously c.right was linked to d
        c.left.right = c.right;

        //this is Knuth's algorithm to unlink all other rows where the column has options
        for (Node r = c.down; r!= c; r = r.down){
            for (Node j = r.right; j != r; j = j.right){ //walking right or left does not matter since the list is circular left = right = this

                j.down.up = j.up;
                j.up.down = j.down;
                j.column.size--;
            }
        }

    }

    public void uncover(Column c) {
        //recover the column
        for (Node r = c.up; r != c; r = r.up) {
            for (Node j = r.left; j != r; j = j.left) {

                j.down.up = j;
                j.up.down = j;

                j.column.size++;
            }
        }

        c.right.left = c;
        c.left.right = c;
    }


}
