package Tree;

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Tree class.
 */
public class BFSIterable<T> implements Iterable<T> {

    //
    private final Tree<T> treeNode; 

    /**
     * Tree class.
     */
    public BFSIterable(Tree<T> treeNode) {
        this.treeNode = treeNode;
    }

    /**
     * Tree class.
     */
    @Override
    public Iterator<T> iterator() {
        return new BFSIterator();
    }

    /**
     * Tree class.
     */
    private class BFSIterator implements Iterator<T> {  // can I make this class STATIC?

        //
        public ArrayDeque<Tree<T>> BFSqueue;

        /**
         * Tree class.
         */        
        public BFSIterator() {
            BFSqueue = new ArrayDeque<Tree<T>>();
            BFSqueue.add(treeNode);
        }

        /**
         * Tree class.
         */
        @Override
        public boolean hasNext() {
            return !BFSqueue.isEmpty();
        }

        /**
         * Tree class.
         */
        @Override
        public T next() {
            Tree<T> currentNode = BFSqueue.pollFirst();
            if (currentNode.getNodeValue() == null) {
                throw new ConcurrentModificationException();
            }

            BFSqueue.addAll(currentNode.nodeChildren);
            return currentNode.getNodeValue();
        }
    }
}