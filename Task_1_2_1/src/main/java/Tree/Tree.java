package Tree;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tree class.
 */
public class Tree<T> {

    //
    ArrayList<Tree<T>> nodeChildren;
    //
    private Tree<T> nodeParent;
    //
    private T nodeValue;

    /**
     * @return
     */
    public T getNodeValue() {
        return nodeValue;
    }

    /**
     * @param nodeValue
     */
    public void setNodeValue(T nodeValue) {
        this.nodeValue = nodeValue;
    }

    /**
     * @param value of current node.
     */
    public Tree(T value) {
        this.nodeChildren = new ArrayList<Tree<T>>();
        setNodeValue(value);
    }

    /**
     * @param subtree
     * @return
     */
    public Tree<T> addChild(Tree<T> subtree) {
        if (subtree.nodeParent == null) {
            subtree.nodeParent = this;
            this.nodeChildren.add(subtree);
        }

        return null;
    }

    /**
     * @param leaf
     * @return
     */
    public Tree<T> addChild(T leaf) {
        Tree<T> newChild = new Tree<T>(leaf);
        newChild.nodeParent = this;
        this.nodeChildren.add(newChild);

        return newChild;
    }

    /**
     * 
     */
    public void remove() {
        while (!nodeChildren.isEmpty()) {
            nodeChildren.get(0).remove();
        }

        if (nodeParent != null) {
            nodeParent.nodeChildren.remove(this);
        }

        setNodeValue(null);
    }

    /**
     * Returns a hash code value for the object. This method is supported
     * for the benefit of hash tables such as those provided by HashMap.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        Object[] fields = {nodeValue, nodeChildren.toArray()};
        return Arrays.deepHashCode(fields);
    }

    /**
     * @return 
     */
    private ArrayList<T> subtreeStuff() {
        var childrenValues = new ArrayList<T>();
        for (Tree<T> child : nodeChildren) {
            childrenValues.add(child.nodeValue);
        }
        return childrenValues;
    }

    /**
     * @param anotherSubtree
     * @return
     */
    private boolean childrenEquals(Tree<T> anotherSubtree) {
        return this.subtreeStuff().containsAll(anotherSubtree.subtreeStuff());
    }

    /**
     * Checks the equality of this subtree and the other subtrees. 
     *
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */ 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        if (this.hashCode() != obj.hashCode()) {
            return false;
        }

        final Tree<T> comparable = (Tree<T>) obj;
        boolean valueCondition = this.nodeValue == comparable.nodeValue;

        return valueCondition && childrenEquals(comparable);
    }

    /**
     * @return
     */
    public Iterable<T> BFS() {
        return new BFSIterable<T>(this);
    }

    /**
     * @return
     */
    public Iterable<T> DFS() {
        return new DFSIterable<T>(this);
    }

    // TODO implement checking for the circuits

    /**
     * Static entry method.
     *
     * @param args Cmd args.
     */
    public static void main(String[] args) {
        Tree<String> tree = new Tree<>("R1");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        tree.addChild(subtree);
        var a = tree.addChild("A");
        var b = a.addChild("B");

        for (String S : tree.DFS()) {
            if (b.getNodeValue() != null) {
                b.remove();
            }
            System.out.print(S + " ");
        }
    }
}