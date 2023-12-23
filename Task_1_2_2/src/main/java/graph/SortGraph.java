package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class SortGraph<N> {

    private static class PriorityComparator<N> implements Comparator<Vertex<N>> {

        private HashMap<Vertex<N>, Double> distanceMap;

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

    private static <L extends Number, N> void initSingleSource(
        Graph<L, N> graphView, Vertex<N> sourceVertex, HashMap<Vertex<N>, Double> distanceMap
    ) {

        ArrayList<Vertex<N>> graphVertices = graphView.getGraphVertices();

        for (Vertex<N> currVertex : graphVertices) {
            distanceMap.put(currVertex, Double.MAX_VALUE);
        }
        distanceMap.put(sourceVertex, 0.0);
    }

    private static <N> void relax(
        Vertex<N> prevVertex, Vertex<N> vertex, 
        Double distance, HashMap<Vertex<N>, Double> distanceMap
    ) {

        if (distanceMap.get(vertex) > distanceMap.get(prevVertex) + distance) {
            distanceMap.put(vertex, distanceMap.get(prevVertex) + distance);
        }
    }
    

    public static <L extends Number, N> ArrayList<Vertex<N>> Dijkstra(
        Graph<L, N> graphView, Vertex<N> sourceVertex
    ) {
        
        HashMap<Vertex<N>, Double> distanceMap = new HashMap<Vertex<N>, Double>();
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


    public static <L extends Number, N> ArrayList<Vertex<N>> Bellman_Ford(
        Graph<L, N> graphView, Vertex<N> sourceVertex
    ) {
        
        HashMap<Vertex<N>, Double> distanceMap = new HashMap<Vertex<N>, Double>();
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
