public enum PentominoColor {
    RED(new boolean[][] {
            {false,true,false},
            {true,true,true},
            {false,true,false}
    }),
    ORANGE(new boolean[][]{
            {false,false,true},
            {true,true,true}
    }),
    LGREEN(new boolean[][]{
            {false,true,true},
            {true,true,false},
            {true,false,false}
    }),
    BLUE(new boolean[][]{
            {true,true,true},
            {true,false,false},
            {true,false,false}
    }),
    CYAN(new boolean[][]{
            {true,true,false},
            {false,true,false},
            {false,true,true}
    }),
    NAVYBLUE(new boolean[][]{
            {true,true,true,true}
    }),
    BROWN(new boolean[][]{
            {true,true,true,true},
            {false,false,true,false}
    }),
    YELLOW(new boolean[][]{
            {true,true},
            {true,false},
            {true,true}
    }),
    GREEN(new boolean[][]{
            {false,false,true},
            {true,true,true},
            {false,false,true}
    }),
    PINK(new boolean[][]{
            {true,false},
            {true,true},
            {true,true}
    }),
    GREY(new boolean[][]{
            {false,false,true},
            {true,true,true},
            {false,true,false}
    }),
    VIOLET(new boolean[][]{
            {true,true,false,false},
            {false,true,true,true}
    }),

    ;

    private final Pentomino pentomino;

    PentominoColor(boolean[][] shape){
        this.pentomino= new Pentomino(this, shape);
    }

    public Pentomino getPentomino(){
        return pentomino;
    }
};
