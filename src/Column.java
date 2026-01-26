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

    public void linkRight(Node node){
        node.right = this.right;
        node.right.left = node;
        node.left = this;
        this.right = node;

        //DLX insertion (horizontal)
    }

    public void linkDown(Node node) {
        node.down = this;
        node.up = this.up;

        this.up.down = node;
        this.up = node;

        node.column = this;
        size++;
    }

    public void cover(){

        //remove column
        right.left = left; 
        left.right = right;

        //this is Knuth's algorithm to unlink all other rows where the column has options
        for (Node r = down; r!= this; r = r.down){
            for (Node j = r.right; j != r; j = j.right){ //walking right or left does not matter since the list is circular left = right = this

                j.down.up = j.up;
                j.up.down = j.down;
                j.column.size--;
            }
        }

    }

    public void uncover() {
        //recover the column
        for (Node r = up; r != this; r = r.up) {
            for (Node j = r.left; j != r; j = j.left) {

                j.down.up = j;
                j.up.down = j;

                j.column.size++;
            }
        }

        right.left = this;
        left.right = this;
    }


}
