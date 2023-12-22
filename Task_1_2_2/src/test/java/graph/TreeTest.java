package graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

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



    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testResource(Graph<Double, String> graph) throws IOException {
        GraphViewParser.parseResource(graph, "graph.txt");
    

        ArrayList<Vertex<String>> vertices = graph.getGraphVertices();
        ArrayList<Vertex<String>> result = SortGraph.Dijkstra(graph, vertices.get(0));
        Assertions.assertEquals(vertices, result);
    }

    @ParameterizedTest
    @ArgumentsSource(GraphsArgumentsProvider.class)
    void testGraph(Graph<Double, String> graph) {
        ArrayList<Vertex<String>> expected = new ArrayList<>();
        expected.add(graph.addVertex("A"));
        expected.add(graph.addVertex("B"));
        expected.add(graph.addVertex("C"));
        graph.addEdge("AB", 1.0, expected.get(0), expected.get(1));
        graph.addEdge("BC", 4.0, expected.get(1), expected.get(2));
        graph.addEdge("CA", 5.0, expected.get(2), expected.get(0));
        ArrayList<Vertex<String>> result = SortGraph.Dijkstra(graph, expected.get(0));
        Assertions.assertEquals(expected, result);
    }

}