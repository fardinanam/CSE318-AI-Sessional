package variables;


import java.util.HashSet;

public class Cell implements Variable<Integer> {
    private final HashSet<Integer> domain;
    private final int x;  // index x
    private final int y;  // index y
    private int value;

    /**
     * Constructs a cell with domain (1 - range)
     * @param range The Highest value of the domain
     * @param x x index of the cell in the square
     * @param y y index of the cell in the square
     */
    public Cell(int x, int y, int range) {
        domain = new HashSet<>(range);
        value = 0;
        this.x = x;
        this.y = y;

        for(int i = 1; i <= range; i++) {
            addToDomain(i);
        }
    }

    /**
     * Adds an element in the domain
     * @param element The element to be added in the domain
     */
    @Override
    public void addToDomain(Integer element) {
        domain.add(element);
    }

    /**
     * Removes an element from domain
     * @param element The element to be removed from domain
     */
    @Override
    public void removeFromDomain(Integer element) {
        domain.remove(element);
    }

    /**
     * Sets the value of the cell if the value exits in the domain.
     * @return true, if it is set successfully. false, otherwise.
     */
    @Override
    public boolean setValue(Integer value) {
        if(!domain.contains(value))
            return false;
        this.value = value;
        return true;
    }

    /**
     * Resets the value
     */
    @Override
    public void removeValue() {
        this.value = 0;
    }

    /**
     * Valid value is 1 - range.
     * 0 means the cell is empty.
     * @return value of the cell
     */
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public HashSet<Integer> getDomain() {
        return domain;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Checks the equality based on the indices
     */
    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof Cell)) return false;
        return this.x == ((Cell)obj).x && this.y == ((Cell)obj).y;
    }
}
