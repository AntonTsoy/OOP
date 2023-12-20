package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GraphViewParser {
    
    private static ArrayList<ArrayList<String>> graphStrings;

    private static void splitResource(String resourceTitle) throws IOException {
        InputStream stream = GraphViewParser.class.getResourceAsStream(resourceTitle);
        InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        graphStrings = new ArrayList<ArrayList<String>>();
        String line = bufferedReader.readLine();
        while (line != null) {
            graphStrings.add(new ArrayList<String>(
                Arrays.stream(line.split(" "))
                      .filter(elem -> !elem.isEmpty())
                      .collect(Collectors.toList())
                )
            );

            line = bufferedReader.readLine();
        }
    }

    public static <L extends Number, N> void parseResource(Graph<L, N> graph, String resourceTitle)
        throws IOException {

        splitResource(resourceTitle);

        ArrayList<String> verticesString = graphStrings.get(0);
        ArrayList<Vertex<N>> vertices = new ArrayList<>();
        for (int i = 1; i < verticesString.size(); i++) {
            vertices.add(graph.addVertex((N) verticesString.get(i)));
        }

        ArrayList<String> currEdges;
        for (int i = 1; i < graphStrings.size(); i++) {
            currEdges = graphStrings.get(i);
            for (int j = 1; j < currEdges.size(); j++) {
                if (currEdges.get(j) != "*") {
                    String edgeName = verticesString.get(i-1) + verticesString.get(j-1);
                    Double edgeLen = Double.parseDouble(currEdges.get(j));
                    graph.addEdge((N) edgeName, (L) edgeLen, vertices.get(i-1), vertices.get(j-1));
                }
            }
        }
    }
}
