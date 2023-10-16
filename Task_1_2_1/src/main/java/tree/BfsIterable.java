package tree;

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Provides an iterable interface for breadth-first traversal of a Tree.
 */
public class BfsIterable<T> implements Iterable<T> {

    /**
     * Implements an iterator interface for breadth-first traversal of a Tree.
     */
    private class BfsIterator implements Iterator<T> {  // Can I make this class STATIC?

        // Queue used to store nodes in breadth-first order.
        public ArrayDeque<Tree<T>> bfsQueue;

        /**
         * Constructor. Initializes the queue with the root node.
         */
        public BfsIterator() {
            bfsQueue = new ArrayDeque<Tree<T>>();
            bfsQueue.add(treeNode);
        }

        /**
         * Returns true if the iteration has more elements. 
         * In other words, returns true if next() would return an element.
         * Rather than throwing an exception.
         *
         * @return true if the iteration has more elements.
         */
        @Override
        public boolean hasNext() {
            return !bfsQueue.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration.
         */
        @Override
        public T next() {
            Tree<T> currentNode = bfsQueue.pollFirst();
            if (currentNode.getNodeValue() == null) {
                throw new ConcurrentModificationException();
            }

            bfsQueue.addAll(currentNode.nodeChildren);
            return currentNode.getNodeValue();
        }
    }

    // The tree to traverse.
    private final Tree<T> treeNode; 

    /**
     * Constructor.
     *
     * @param treeNode The tree to traverse.
     */
    public BfsIterable(Tree<T> treeNode) {
        this.treeNode = treeNode;
    }

    /**
     * Returns an iterator over elements of type T.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new BfsIterator();
    }
}