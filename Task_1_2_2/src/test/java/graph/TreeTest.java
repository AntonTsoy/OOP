package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestGraphs {

    static class StringGraphsArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(new ListGraph<String>()),
                    Arguments.of(new IncidentMatrixGraph<String>()),
                    Arguments.of(new AdjacencyMatrixGraph<String>())
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(StringGraphsArgumentsProvider.class)
    void testGraph(AbstractGraph<String> graph) {
        Main.readDataForGraphFromFile(graph, "./input.txt");
        var result = graph.constructShortestDistances("C");
        Assertions.assertEquals(result, getPredictedMap());
    }
   
}