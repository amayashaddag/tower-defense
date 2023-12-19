package tools;

public class Triplet {
    private int x, y, z;

    public Triplet(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int get(int index) {
        if (index == 0) return x;
        else if (index == 1) return y;
        else return z;
    }

    public void decrement(int index) {
        if (index == 0) this.x--;
        else if (index == 1) this.y--;
        else this.z--;
    }

    public boolean isNull() {
        return x == y && y == z && z == 0;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
