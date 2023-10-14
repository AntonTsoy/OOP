package Tree;

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Tree class.
 */
public class DFSIterable<T> implements Iterable<T> {

    /**
     * Tree class.
     */
    private class DFSIterator implements Iterator<T> {  // can I make this class STATIC?

        // 
        public ArrayDeque<Tree<T>> DFSqueue;

        /**
         * Tree class.
         */        
        public DFSIterator() {
            DFSqueue = new ArrayDeque<Tree<T>>();
            DFSqueue.add(treeNode);
        }

        /**
         * Tree class.
         */
        @Override
        public boolean hasNext() {
            return !DFSqueue.isEmpty();
        }

        /**
         * Tree class.
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

    // 
    private final Tree<T> treeNode;

    /**
     * Tree class.
     */
    public DFSIterable(Tree<T> treeNode) {
        this.treeNode = treeNode;
    }
    
    /**
     * Tree class.
     */
    @Override
    public Iterator<T> iterator() {
        return new DFSIterator();
    }
}