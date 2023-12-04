package graph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @param <N>
 * @param <L>
 * 
 */
public class SortGraph<L extends Number, N> {

    HashMap<Vertex<N>, L> distanceMap;

    private void initSingleSource(Graph<L, N> graphView, Vertex<N> sourceVertex) {
        distanceMap = new HashMap<Vertex<N>, L>();
        ArrayList<Vertex<N>> graphVertices = graphView.getGraphVertices();

        for (Vertex<N> currVertex : graphVertices) {
            distanceMap.put(currVertex, null);
        }
    }

    private void relax(Vertex<N> prevVert, Vertex<N> vert, L dist) {
        if (distanceMap.get(vert).doubleValue() > 
            distanceMap.get(prevVert).doubleValue() + dist.doubleValue()) {
                distanceMap.put(vert, distanceMap.get(prevVert).doubleValue() + dist.doubleValue());
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
    <L extends Number, N> ArrayList<Edge<L, N>> Dijkstra(Graph<L, N> graphView, Vertex<N> sourceVertex) {
        
    }
}
