package tree;

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Provides an iterable interface for depth-first traversal of a Tree.
 */
public class DfsIterable<T> implements Iterable<T> {

    /**
     * Implements an iterator interface for depth-first traversal of a Tree.
     */
    private class DfsIterator implements Iterator<T> {  // Can I make this class STATIC?

        // Stack used to store nodes in depth-first order. 
        public ArrayDeque<Tree<T>> DfsStack;

        /**
         * Constructor. Initializes the queue with the root node.
         */       
        public DfsIterator() {
            DfsStack = new ArrayDeque<Tree<T>>();
            DfsStack.add(treeNode);
        }

        /**
         * Returns true if the iteration has more elements. 
         * In other words, returns true if next() would return an element rather than throwing an exception.
         *
         * @return true if the iteration has more elements.
         */
        @Override
        public boolean hasNext() {
            return !DfsStack.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration.
         */
        @Override
        public T next() {
            Tree<T> currentNode = DfsStack.pollLast();
            if (currentNode.getNodeValue() == null) {
                throw new ConcurrentModificationException();
            }

            DfsStack.addAll(currentNode.nodeChildren);
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
    public DfsIterable(Tree<T> treeNode) {
        this.treeNode = treeNode;
    }
    
    /**
     * Returns an iterator over elements of type T.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new DfsIterator();
    }
}