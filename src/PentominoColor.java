public enum PentominoColor {
    RED('R', new boolean[][]{
            {false, true, false},
            {true, true, true},
            {false, true, false}
    }),
    ORANGE('O',new boolean[][]{
            {false, false,false, true},
            {true, true, true, true}
    }),
    LGREEN('g',new boolean[][]{
            {false, true, true},
            {true, true, false},
            {true, false, false}
    }),
    BLUE('B',new boolean[][]{

           {true, true, true},
            {true, false, false},
            {true, false, false}
    }),
    CYAN('C',new boolean[][]{
            {true, true, false},
            {false, true, false},
            {false, true, true}
    }),
    NAVYBLUE('N',new boolean[][]{
            {true, true, true, true, true}
    }),
    BROWN('b',new boolean[][]{
            {true, true, true, true},
            {false, false, true, false}
    }),
    YELLOW('Y',new boolean[][]{
            {true, true},
            {true, false},
            {true, true}
    }),
    GREEN('G',new boolean[][]{
            {false, false, true},
            {true, true, true},
            {false, false, true}
    }),
    PINK('P',new boolean[][]{
            {true, false},
            {true, true},
            {true, true}
    }),
    GREY('G',new boolean[][]{
            {false, false, true},
            {true, true, true},
            {false, true, false}
    }),
    VIOLET('V',new boolean[][]{
            {true, true, false, false},
            {false, true, true, true}
    }),

    ;

    private final Pentomino pentomino;
    private final char symbol;

    PentominoColor(char symbol, boolean[][] shape) {
        this.symbol = symbol;
        this.pentomino = new Pentomino(this, shape);
    }

    public char getSymbol() {
        return symbol;
}

    public Pentomino getPentomino(){
        return pentomino;
    }
};
