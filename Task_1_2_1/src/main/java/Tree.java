import java.util.ArrayList;

/**
 * Tree class.
 */
public class Tree<T> {

    //
    private ArrayList<Tree<T>> children;
    //
    private Tree<T> parent;

    // 
    public T value;

    /**
     * @param value of current node. 
     */
    public Tree(T value) {
        this.children = new ArrayList<Tree<T>>();
        this.value = value;
    }

    /**
     * @param subtree
     * @return
     */
    public Tree<T> addChild(Tree<T> subtree) {
        if (subtree.parent == null) {
            subtree.parent = this;
            this.children.add(subtree);
        }

        return null;
    }

    /**
     * @param leaf
     * @return
     */
    public Tree<T> addChild(T leaf) {
        Tree<T> newChild = new Tree<T>(leaf);
        newChild.parent = this;
        this.children.add(newChild);

        return newChild;
    }

    public void remove() {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }

        if (this.children.size() > 0) {
            for (Tree<T> childTree : this.children) {
                childTree.remove();
            }
            this.children.clear();
        }

        this.value = null;
    }

    class BFS implements Iterable<T> {
        public Iterator<T> iterator() {
            return new 
        }
    }

    /**
     * Static entry method.
     *
     * @param args Cmd args.
     */
    public static void main(String[] args) {
        
    }
}