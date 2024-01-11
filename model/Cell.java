package model;

public class Cell {
    private boolean isPath;
    private boolean isBase;

    public Cell(boolean isPath, boolean isBase) {
        this.isPath = isPath;
        this.isBase = isBase;
    }

    public boolean isPath() {
        return this.isPath;
    }

    public boolean isBase() {
        return this.isBase;
    }

    @Override
    public String toString() {
        if (this.isBase) {
            return "B";
        }
        if (this.isPath) {
            return "+";
        }
        return ".";
    }
}