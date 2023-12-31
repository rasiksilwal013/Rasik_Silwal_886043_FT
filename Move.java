import java.util.ArrayList;
import java.util.List;

class Move {
    private final List<Box> boxes;

    public Move(int initialCapacity) {
        boxes = new ArrayList<>(initialCapacity);
    }

    // method to add box to the move
    public void addBox(Box box) {
        boxes.add(box);
    }

    // method to print the contents of the move
    public void print() {
        System.out.println("The objects of my move are:");
        for (Box box : boxes) {
            box.print();
        }
    }

    // method to find box number where an object with a given name is
    public int find(String itemName) {
        for (int i = boxes.size() - 1; i >= 0; i--) {
            int boxNumber = boxes.get(i).find(itemName);
            if (boxNumber > 0) {
                return boxNumber;
            }
        }
        return -1;
    }
}

class Box {
    private final List<Object> items;
    private final int boxNumber;

    // constructor for creating a box capacity and box number
    public Box(int capacity, int boxNumber) {
        items = new ArrayList<>(capacity);
        this.boxNumber = boxNumber;
    }

    // adding an item
    public void addItem(Object item) {
        items.add(item);
    }

    // printing the contents of the box
    public void print() {
        for (Object item : items) {
            if (item instanceof SingleObject) {
                System.out.println(((SingleObject) item).getName());
            } else if (item instanceof Box) {
                ((Box) item).print();
            }
        }
    }

    //finding box number with a given name of the item
    public int find(String itemName) {
        for (Object item : items) {
            if (item instanceof SingleObject && ((SingleObject) item).getName().equals(itemName)) {
                return boxNumber;
            } else if (item instanceof Box) {
                int result = ((Box) item).find(itemName);
                if (result > 0) {
                    return result;
                }
            }
        }
        return -1;
    }
}

class SingleObject {
    private final String name;

    // constructr creating a single object with a specified name
    public SingleObject(String name) {
        this.name = name;
    }

    // getting the name of single object
    public String getName() {
        return name;
    }

    // Main method to demonstrate the usage of the Move, Box, and SingleObject classes
    public static void main(String[] args) {
        // We create a move that will hold 2 main boxes
        Move move = new Move(2);

        /*
         * We create and then fill 3 boxes
         * Arguments of the constructor of Box:
         * argument 1: number of items (simple items/objects or box) that the box can hold
         * argument 2: box number
         */

        // box 1 contains scissors
        Box box1 = new Box(1, 1);
        box1.addItem(new SingleObject("scissors"));

        // box 2 contains one book
        Box box2 = new Box(1, 2);
        box2.addItem(new SingleObject("book"));

        // box 3 contains one compass
        // and one box containing one scarf
        Box box3 = new Box(2, 3);
        box3.addItem(new SingleObject("compass"));
        Box box4 = new Box(1, 4);
        box4.addItem(new SingleObject("scarf"));
        box3.addItem(box4);

        // We add the three boxes to the first box of move - see below
        Box box5 = new Box(3, 5);
        box5.addItem(box1);
        box5.addItem(box2);
        box5.addItem(box3);

        // We add one box containing 3 objects to move
        Box box6 = new Box(3, 6);
        box6.addItem(new SingleObject("pencils"));
        box6.addItem(new SingleObject("pens"));
        box6.addItem(new SingleObject("rubber"));

        // We add the two most external boxes to the move
        move.addBox(box5);
        move.addBox(box6);

        // We print all the contents of the move
        move.print();

        // We print the number of the outermost cardboard containing the item "scarf"
        System.out.println("The scarf is in the cardboard number " + move.find("scarf"));
    }
}
