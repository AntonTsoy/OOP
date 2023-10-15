package Tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TreeTest {

    Tree<Integer> treeI;
    Tree<String> treeS;

    @BeforeEach
    void setUp() {
        treeI = new Tree<Integer>(1);
        treeS = new Tree<String>("Root");
    }

    @Test
    void testSetValueException() {
        assertThrows(IllegalArgumentException.class, () -> {
            treeS.setNodeValue(null); 
        });
    }

    @Test
    void testGetSetValueI() {
        treeI.setNodeValue(5);
        assertEquals(5, treeI.getNodeValue());
    }

    @Test
    void testGetSetValueS() {
        treeS.setNodeValue("Root0");
        assertEquals("Root0", treeS.getNodeValue());
    }

    @Test
    void testAddChildSubtreeI() {  //////////////////////////////////////////////
        Tree<Integer> child = new Tree<>(2);
        treeI.addChild(child);
        assertEquals(1, treeI.nodeChildren.size());
        assertEquals(child, treeI.nodeChildren.get(0));
        assertEquals(treeI, child.getNodeParent());
    }

    @Test
    void testAddChildSubtreeS() {  //////////////////////////////////////////////
        Tree<String> child = new Tree<>("RootChild");
        treeS.addChild(child);
        assertEquals(1, treeS.nodeChildren.size());
        assertEquals(child, treeS.nodeChildren.get(0));
        assertEquals(treeS, child.getNodeParent());
    }

    @Test
    void testAddChildLeafI() {  //////////////////////////////////////////////
        Tree<Integer> leaf = treeI.addChild(3);
        assertEquals(1, treeI.nodeChildren.size());
        assertEquals(3, leaf.getNodeValue());
    }

    @Test
    void testAddChildLeafS() {  //////////////////////////////////////////////
        Tree<String> leaf = treeS.addChild("RootChild");
        assertEquals(1, treeS.nodeChildren.size());
        assertEquals("RootChild", leaf.getNodeValue());
    }

    @Test
    void testRemoveI() {  //////////////////////////////////////////////
        Tree<Integer> child = new Tree<>(2);
        treeI.addChild(child);
        treeI.remove();
        assertNull(treeI.getNodeValue());
        assertNull(treeI.getNodeParent());
        assertEquals(0, treeI.nodeChildren.size());
    }

    @Test
    void testRemoveS() {  //////////////////////////////////////////////
        Tree<String> child = new Tree<>("RootChild");
        treeS.addChild(child);
        treeS.remove();
        assertNull(treeS.getNodeValue());
        assertNull(treeS.getNodeParent());
        assertNull(child.getNodeValue());
        assertNull(child.getNodeParent());
        assertEquals(0, treeS.nodeChildren.size());
    }

    @Test
    void testHashCodeI() {  //////////////////////////////////////////////
        Tree<Integer> sameTree = new Tree<>(1);
        assertEquals(treeI.hashCode(), sameTree.hashCode());

        Tree<Integer> diffTree = new Tree<>(2);
        assertNotEquals(treeI.hashCode(), diffTree.hashCode());
    }

    @Test
    void testHashCodeS() {  //////////////////////////////////////////////
        Tree<String> sameTree = new Tree<>("Root");
        assertEquals(treeS.hashCode(), sameTree.hashCode());

        Tree<String> diffTree = new Tree<>("AnotherRoot");
        assertNotEquals(treeS.hashCode(), diffTree.hashCode());
    }

    @Test
    void testEqualsI() {  //////////////////////////////////////////////
        Tree<Integer> sameTree = new Tree<>(1);
        assertEquals(treeI, sameTree);

        Tree<Integer> diffTree = new Tree<>(2);
        assertNotEquals(treeI, diffTree);
    }

    @Test
    void testEqualsS() {  //////////////////////////////////////////////
        Tree<String> sameTree = new Tree<>("Root");
        assertEquals(treeS, sameTree);

        Tree<String> diffTree = new Tree<>("AnotherRoot");
        assertNotEquals(treeS, diffTree);
    }

    @Test
    void testDifGenEquals() {  //////////////////////////////////////////////
        Tree<Integer> sameTree = new Tree<>(1);
        assertEquals(treeI, sameTree);

        Tree<Integer> diffTree = new Tree<>(2);
        assertNotEquals(treeI, diffTree);
    }

    @Test
    void testNullEquals() {  //////////////////////////////////////////////
        Tree<Integer> sameTree = new Tree<>(1);
        assertEquals(treeI, sameTree);

        Tree<Integer> diffTree = new Tree<>(2);
        assertNotEquals(treeI, diffTree);
    }

    @Test
    void testBFS() {
        Tree<Integer> child1 = treeI.addChild(2);
        Tree<Integer> child2 = treeI.addChild(3);
        child1.addChild(4);
        child2.addChild(5);

        Iterable<Integer> bfs = treeI.BFS();
        Iterator<Integer> itr = bfs.iterator();

        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> result = new ArrayList<>();
        while (itr.hasNext()) {
            result.add(itr.next());
        }

        assertEquals(expected, result);
    }

    @Test
    void testDFS() {
        Tree<String> child1 = treeS.addChild("B");
        Tree<String> child2 = treeS.addChild("A");
        child1.addChild("B_child");
        child2.addChild("A2");
        child2.addChild("A1");

        Iterable<String> bfs = treeS.DFS();
        Iterator<String> itr = bfs.iterator();

        ArrayList<String> expected = new ArrayList<>(Arrays.asList("Root", "A", "A1", "A2", "B", "B_child"));
        ArrayList<String> result = new ArrayList<>();
        while (itr.hasNext()) {
            result.add(itr.next());
        }
        
        assertEquals(expected, result);
    }

}