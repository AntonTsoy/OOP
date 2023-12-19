 package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 
 */
public class SortGraph {

    private static HashMap<Vertex<N>, Double> distanceMap;

    private static void initSingleSource(Graph<L, N> graphView, Vertex<N> sourceVertex) {
        distanceMap = new HashMap<Vertex<N>, Double>();
        ArrayList<Vertex<N>> graphVertices = graphView.getGraphVertices();

        for (Vertex<N> currVertex : graphVertices) {
            distanceMap.put(currVertex, Double.MAX_VALUE);    // I should to something in this
        }
        distanceMap.put(sourceVertex, 0.0);
    }

    private static void relax(Vertex<N> prevVertex, Vertex<N> vertex, Double distance) {
        if (distanceMap.get(vertex) > distanceMap.get(prevVertex) + distance) {
            distanceMap.put(vertex, distanceMap.get(prevVertex) + distance);
        }
    }

    /**
     * 
     * @param <L>
     * @param <N>
     * @param graphView
     * @param currentVertex
     * @return
     */
    public static <L extends Number, N> ArrayList<Vertex<N>> Dijkstra(
        Graph<L, N> graphView, Vertex<N> sourceVertex
    ) {
        
        initSingleSource(graphView, sourceVertex);
        PriorityComparator<Vertex<N>> vertexComparator = new PriorityComparator();
        PriorityQueue<Vertex<N>> verticesQueue = new PriorityQueue<>(vertexComparator);
        verticesQueue.addAll(graphView.getGraphVertices());

        while (verticesQueue.size() > 0) {
            Vertex<N> currVertex = verticesQueue.poll();
            ArrayList<Edge<L, N>> incidentEdges = graphView.getIncidentEdges(currVertex);
            for (Edge<L, N> currEdge : incidentEdges) {
                relax(currVertex, currEdge.endVertex, currEdge.edgeLen);
            }
        }

        ArrayList<Vertex<N>> resultList = graphView.getGraphVertices();
        resultList.sort(vertexComparator);
        return resultList;
    }


    public static <L extends Number, N> ArrayList<Vertex<N>> Bellman_Ford(
        Graph<L, N> graphView, Vertex<N> sourceVertex
    ) {
        
        initSingleSource(graphView, sourceVertex);
        ArrayList<Edge<L, N>> graphEdges = graphView.getGraphEdges();
        for (int i = 1; i < resultList.size(); i++) {
            for (Edge<L, N> currEdge : graphEdges) {
                relax(currEdge.startVertex, currEdge.endVertex, currEdge.edgeLen);
            }
        }

        ArrayList<Vertex<N>> resultList = graphView.getGraphVertices();
        resultList.sort(vertexComparator);
        return resultList;
    }

    private static class PriorityComparator implements Comparator<Vertex<N>> {

        @Override
        int compare(Vertex<N> obj1, Vertex<N> obj2) {
            double difference = distanceMap.get(obj1) - distanceMap.get(obj2);
            if (difference < -0.00001) {
                return -1;
            } else if (difference > 0.00001) {
                return 1;
            }
            return 0;
        }
    }
}
