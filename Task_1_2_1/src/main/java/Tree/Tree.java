package Tree;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a tree data structure that stores generic values T.
 * Provides methods for adding/removing nodes, traversing the tree, etc.
 */
public class Tree<T> {
    // The children list of this tree node.
    ArrayList<Tree<T>> nodeChildren;
    // The parent node of this tree, or null if this is the root node.
    private Tree<T> nodeParent;
    // The value stored at this tree node.
    private T nodeValue;

    /**
     * Gets the parent of this node.
     *
     * @return The node parent.
     */
    public Tree<T> getNodeParent() {
        return nodeParent;
    }

    /**
     * Gets the value stored at this node.
     *
     * @return The node value.
     */
    public T getNodeValue() {
        return nodeValue;
    }

    /**
     * Sets the value stored at this node.
     *
     * @param nodeValue The new node value.
     */
    public void setNodeValue(T nodeValue) {
        if (nodeValue == null) {
            throw new IllegalArgumentException("Node can't be a NULL");
        }
        this.nodeValue = nodeValue;
    }
    
    /**
     * Creates a new tree node with the given value.
     *
     * @param value The value to store in the node.
    */
    public Tree(T value) {
        this.nodeChildren = new ArrayList<Tree<T>>();
        setNodeValue(value);
    }

    /**
     * Adds a child subtree. Sets this tree as the parent of the subtree.
     *
     * @param subtree The subtree to add.
     *
     * @return The added subtree, or null if it already had a parent.
     */
    public Tree<T> addChild(Tree<T> subtree) {
        if (subtree.nodeParent == null) {
            subtree.nodeParent = this;
            this.nodeChildren.add(subtree);
        }

        return null;
    }

    /**
     * Creates a new leaf node with the given value and adds it as a child.
     *
     * @param leaf The leaf value to add.
     *
     * @return The new leaf node.
     */
    public Tree<T> addChild(T leaf) {
        Tree<T> newChild = new Tree<T>(leaf);
        newChild.nodeParent = this;
        this.nodeChildren.add(newChild);

        return newChild;
    }

    /**
     * Removes this node and all its children from the tree.
     */
    public void remove() {
        while (!nodeChildren.isEmpty()) {
            nodeChildren.get(0).remove();
        }

        if (nodeParent != null) {
            nodeParent.nodeChildren.remove(this);
            nodeParent = null;
        }

        nodeValue = null;
    }

    /**
     * Returns a flattened list of all node values in the subtree rooted at this node.
     *
     * @return The flattened subtree values.
     */
    private ArrayList<T> subtreeStuff() {           
        var childrenValues = new ArrayList<T>();
        for (Tree<T> child : nodeChildren) {
            childrenValues.add(child.nodeValue);
            if (!child.nodeChildren.isEmpty()) {
                childrenValues.addAll(child.subtreeStuff());
            }
        }

        return childrenValues;
    }

    /**
     * Returns a hash code value for the object. This method is supported
     * for the benefit of hash tables such as those provided by HashMap.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        Object[] fields = {nodeValue, this.subtreeStuff()};
        return Arrays.deepHashCode(fields);
    }

    /**
     * Checks the equality of this subtree and the another. 
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

        return Arrays.equals(this.subtreeStuff().toArray(), comparable.subtreeStuff().toArray())
               && this.nodeValue == comparable.nodeValue;
    }

    /**
     * Returns an iterable that performs a breadth-first traversal 
     * of this tree.
     *
     * @return Breadth-first iterable.
     */
    public Iterable<T> BFS() {
        return new BFSIterable<T>(this);
    }

    /**
     * Returns an iterable that performs a depth-first traversal
     * of this tree. 
     *
     * @return Depth-first iterable.
     */
    public Iterable<T> DFS() {
        return new DFSIterable<T>(this);
    }

    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        Tree<Integer> treeI = new Tree<>(1);
        Tree<Integer> child1 = treeI.addChild(3);
        Tree<Integer> child2 = treeI.addChild(2);
        child1.addChild(5);
        child2.addChild(4);

        for (Integer a : treeI.DFS()) {
            System.out.print(a + " ");
        }

    }
}