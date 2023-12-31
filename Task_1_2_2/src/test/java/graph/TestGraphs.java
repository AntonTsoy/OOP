package graph;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;


/**
 * Main Test class.
 */
class TestGraphs {

    static class GraphsArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(new AdjacencyList<Double, String>()),
                    Arguments.of(new IncidenceMatrix<Double, String>()),
                    Arguments.of(new AdjacencyMatrix<Double, String>())
            );
        }
    }

    @Test
    void testVertex() {
        Vertex<String> vertex = new Vertex<String>("123");
        vertex.setVertexName("abc");
        Assertions.assertEquals("abc", vertex.getVertexName());
    }

    @Test
    void setVertexException() {
        Vertex<Double> vertex = new Vertex<>(1.0);
        assertThrows(IllegalArgumentException.class, () -> {
            vertex.setVertexName(null);
        });
    }

    @Test
    void testEdge() {
        Vertex<String> vertex1 = new Vertex<String>("1");
        Vertex<String> vertex2 = new Vertex<String>("2");
        Edge<Integer, String> edge = new Edge("12", 10, vertex1, vertex2);
        edge.setEdgeName("100");
        edge.setEdgeLen(100);
        Assertions.assertEquals(edge.getEdgeName(), edge.getEdgeLen().toString());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testGraph(Graph<Double, String> graph) {
        Vertex<String> vertexExpected1 = graph.addVertex("A");
        Vertex<String> vertexExpected2 = graph.addVertex("B");
        Edge<Double, String> edge = graph.addEdge("AB", 1.0, vertexExpected1, vertexExpected2);
        graph.changeVertex(vertexExpected2, "C");
        graph.changeEdge(edge, "AC");
        Assertions.assertEquals(
            vertexExpected1.getVertexName().toString() + "C", edge.getEdgeName()
        );
    }
    
    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testAddVertex(Graph<Double, String> graph) {
        Vertex<String> vertexExpected = graph.addVertex("A");
        ArrayList<Vertex<String>> result = graph.getGraphVertices();
        Assertions.assertEquals(vertexExpected, result.get(0));
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testAddEdge(Graph<Double, String> graph) {
        Vertex<String> vertexExpected1 = graph.addVertex("A");
        Vertex<String> vertexExpected2 = graph.addVertex("B");
        Edge<Double, String> edge = graph.addEdge("AB", 1.0, vertexExpected1, vertexExpected2);
        Assertions.assertEquals(vertexExpected2, edge.getEndVertex());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testGraphs(Graph<Double, String> graph) {
        ArrayList<Vertex<String>> expected = new ArrayList<>();
        expected.add(graph.addVertex("A"));
        expected.add(graph.addVertex("B"));
        expected.add(graph.addVertex("C"));
        graph.addEdge("AB", 1.0, expected.get(0), expected.get(1));
        graph.addEdge("BC", 4.0, expected.get(1), expected.get(2));
        graph.addEdge("CA", 5.0, expected.get(2), expected.get(0));
        ArrayList<Vertex<String>> result = SortGraph.sortDijkstra(graph, expected.get(0));
        Assertions.assertEquals(expected, result);
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testDeletions(Graph<Double, String> graph) {
        var a = graph.addVertex("A");
        var b = graph.addVertex("B");
        var c = graph.addVertex("C");
        var bc = graph.addEdge("BC", 4.0, b, c);
        graph.addEdge("AB", 1.0, a, b);
        graph.addEdge("CA", 5.0, c, a);
        graph.delEdge(bc);
        graph.delVertex(a);
        Assertions.assertEquals(0, graph.getGraphEdges().size());
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testResourceDijkstra(Graph<Double, String> graph) throws IOException {
        GraphViewParser.parseResource(graph, "graph1.txt");
    
        ArrayList<Vertex<String>> vertices = graph.getGraphVertices();
        ArrayList<Vertex<String>> result = SortGraph.sortDijkstra(graph, vertices.get(0));
        Assertions.assertEquals(vertices, result);
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testResourceBellmanFord(Graph<Double, String> graph) throws IOException {
        GraphViewParser.parseResource(graph, "graph2.txt");
    
        ArrayList<Vertex<String>> vertices = graph.getGraphVertices();
        ArrayList<Vertex<String>> result = SortGraph.sortBellmanFord(graph, vertices.get(0));
        Collections.swap(vertices, 0, 1);
        Collections.swap(vertices, 3, 4);  // {b, a, c, e, d, f}
        Assertions.assertEquals(vertices, result);
    }

    
}