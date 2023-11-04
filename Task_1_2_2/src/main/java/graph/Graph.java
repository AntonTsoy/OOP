package graph;

import java.util.ArrayList;

/**
 * 
 */
public interface Graph<L extends Number, N> {
    
    Vertex<N> addVertex(N name);
    
    void delVertex(Vertex<N> vertex);
    
    /**
     * 
     * @param vertex
     * @return
     * 
     */
    default N getVertex(Vertex<N> vertex) {
        return vertex.getVertexName();
    }
    
    /**
     * 
     * @param vertex
     * @param newName
     * 
     */
    default void changeVertex(Vertex<N> vertex, N newName) {
        vertex.setVertexName(newName);
    }

    Edge<L, N> addEdge(N name, L len, Vertex<N> startVertex, Vertex<N> endVertex);
    
    void delEdge(Edge<L,N> edge);

    /**
     * 
     * @param edge
     * @return
     * 
     */
    default N getEdge(Edge<L, N> edge) {
        return edge.getEdgeName();
    }

    ArrayList<Edge<L, N>> getIncidentEdges(Vertex<N> currentVeretex);
    
    /**
     * 
     * @param edge
     * @param newName
     * 
     */
    default void changeEdge(Edge<L, N> edge, N newName) {
        edge.setEdgeName(newName);
    }
}

