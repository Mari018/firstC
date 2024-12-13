package academy.mindswap.field;

public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getCol() {
        return x;
    }

    public int getRow() {
        return y;
    }


    @Override public boolean equals(Object other)
    {
        boolean result = false;

        if(other instanceof Position that)
        {
            result = (getCol() == that.getCol() && getRow() == that.getRow());
        }

        return result;
    }
}
