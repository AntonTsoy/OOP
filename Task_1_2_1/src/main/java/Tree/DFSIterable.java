package Tree;

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Provides an iterable interface for depth-first traversal of a Tree.
 */
public class DFSIterable<T> implements Iterable<T> {

    /**
     * Implements an iterator interface for depth-first traversal of a Tree.
     */
    private class DFSIterator implements Iterator<T> {  // Can I make this class STATIC?

        // Stack used to store nodes in depth-first order. 
        public ArrayDeque<Tree<T>> DFSqueue;

        /**
         * Constructor. Initializes the queue with the root node.
         */       
        public DFSIterator() {
            DFSqueue = new ArrayDeque<Tree<T>>();
            DFSqueue.add(treeNode);
        }

        /**
         * Returns true if the iteration has more elements. 
         * In other words, returns true if next() would return an element rather than throwing an exception.
         *
         * @return true if the iteration has more elements.
         */
        @Override
        public boolean hasNext() {
            return !DFSqueue.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration.
         */
        @Override
        public T next() {
            Tree<T> currentNode = DFSqueue.pollLast();
            if (currentNode.getNodeValue() == null) {
                throw new ConcurrentModificationException();
            }

            DFSqueue.addAll(currentNode.nodeChildren);
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
    public DFSIterable(Tree<T> treeNode) {
        this.treeNode = treeNode;
    }
    
    /**
     * Returns an iterator over elements of type T.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new DFSIterator();
    }
}