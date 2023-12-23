package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Utility class for sorting graphs using various algorithms.
 *
 * @param <N> the type of the vertex name.
 */
public class SortGraph<N> {

    /**
     * Comparator for comparing vertices based on their distances.
     *
     * @param <N> the type of the vertex name.
     */
    private static class PriorityComparator<N> implements Comparator<Vertex<N>> {

        private HashMap<Vertex<N>, Double> distanceMap;

        /**
         * Constructs a PriorityComparator with the given distance map.
         *
         * @param distanceMap the distance map for vertices.
         */
        public PriorityComparator(HashMap<Vertex<N>, Double> distanceMap) {
            this.distanceMap = new HashMap<>(distanceMap);
        }

        @Override
        public int compare(Vertex<N> obj1, Vertex<N> obj2) {
            double difference = distanceMap.get(obj1) - distanceMap.get(obj2);
            if (difference < -0.00001) {
                return -1;
            } else if (difference > 0.00001) {
                return 1;
            }

            return 0;
        }
    }

    /**
     * Initializes the distance map with maximum values for all vertices except the source vertex.
     *
     * @param <L>          the type of the edge length.
     * @param <N>          the type of the vertex name.
     * @param graphView    the graph view.
     * @param sourceVertex the source vertex.
     * @param distanceMap  the distance map to be initialized.
     */
    private static <L extends Number, N> void initSingleSource(
        Graph<L, N> graphView, Vertex<N> sourceVertex, HashMap<Vertex<N>, Double> distanceMap
    ) {
        ArrayList<Vertex<N>> graphVertices = graphView.getGraphVertices();

        for (Vertex<N> currVertex : graphVertices) {
            distanceMap.put(currVertex, Double.MAX_VALUE);
        }
        distanceMap.put(sourceVertex, 0.0);
    }

    /**
     * Relaxes the distance from the previous vertex to the current vertex.
     *
     * @param <N>           the type of the vertex name.
     * @param prevVertex    the previous vertex.
     * @param vertex        the current vertex.
     * @param distance      the distance between the vertices.
     * @param distanceMap   the distance map.
     */
    private static <N> void relax(
        Vertex<N> prevVertex, Vertex<N> vertex,
        Double distance, HashMap<Vertex<N>, Double> distanceMap
    ) {
        if (distanceMap.get(vertex) > distanceMap.get(prevVertex) + distance) {
            distanceMap.put(vertex, distanceMap.get(prevVertex) + distance);
        }
    }

    /**
     * Applies Dijkstra's algorithm to find the shortest paths from a source vertex.
     *
     * @param <L>          the type of the edge length.
     * @param <N>          the type of the vertex name.
     * @param graphView    the graph view.
     * @param sourceVertex the source vertex.
     * @return an ArrayList of vertices sorted by their distances from the source.
     */
    public static <L extends Number, N> ArrayList<Vertex<N>> Dijkstra(
        Graph<L, N> graphView, Vertex<N> sourceVertex
    ) {
        HashMap<Vertex<N>, Double> distanceMap = new HashMap<>();
        initSingleSource(graphView, sourceVertex, distanceMap);
        PriorityComparator<N> vertexComparator = new PriorityComparator<>(distanceMap);
        PriorityQueue<Vertex<N>> verticesQueue = new PriorityQueue<>(vertexComparator);
        verticesQueue.addAll(graphView.getGraphVertices());

        while (verticesQueue.size() > 0) {
            Vertex<N> currVertex = verticesQueue.poll();
            ArrayList<Edge<L, N>> incidentEdges = graphView.getIncidentEdges(currVertex);
            for (Edge<L, N> currEdge : incidentEdges) {
                relax(
                    currVertex, currEdge.getEndVertex(),
                    currEdge.getEdgeLen().doubleValue(), distanceMap
                );
            }
        }

        ArrayList<Vertex<N>> resultList = graphView.getGraphVertices();
        resultList.sort(vertexComparator);
        return resultList;
    }

    /**
     * Applies Bellman-Ford algorithm to find the shortest paths from a source vertex.
     *
     * @param <L>          the type of the edge length.
     * @param <N>          the type of the vertex name.
     * @param graphView    the graph view.
     * @param sourceVertex the source vertex.
     * @return an ArrayList of vertices sorted by their distances from the source.
     */
    public static <L extends Number, N> ArrayList<Vertex<N>> Bellman_Ford(
        Graph<L, N> graphView, Vertex<N> sourceVertex
    ) {
        HashMap<Vertex<N>, Double> distanceMap = new HashMap<>();
        initSingleSource(graphView, sourceVertex, distanceMap);
        ArrayList<Edge<L, N>> graphEdges = graphView.getGraphEdges();
        ArrayList<Vertex<N>> resultList = graphView.getGraphVertices();
        for (int i = 1; i < resultList.size(); i++) {
            for (Edge<L, N> currEdge : graphEdges) {
                relax(
                    currEdge.getStartVertex(), currEdge.getEndVertex(),
                    currEdge.getEdgeLen().doubleValue(), distanceMap
                );
            }
        }

        PriorityComparator<N> vertexComparator = new PriorityComparator<>(distanceMap);
        resultList.sort(vertexComparator);
        return resultList;
    }
}
