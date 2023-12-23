package graph;

import java.util.ArrayList;

/**
 * The Graph interface defines the basic structure and operations for a graph data structure.
 *
 * @param <L> the type of the edge length.
 * @param <N> the type of the vertex name.
 */
public interface Graph<L extends Number, N> {

    /**
     * Adds a new vertex to the graph with the specified name.
     *
     * @param name the name of the new vertex.
     * @return the newly added vertex.
     */
    @ExcludeFromJacocoGeneratedReport
    Vertex<N> addVertex(N name);

    /**
     * Adds a new edge to the graph with the specified name, length, and start and end vertices.
     *
     * @param name        the name of the new edge.
     * @param len         the length of the new edge.
     * @param startVertex the starting vertex of the edge.
     * @param endVertex   the ending vertex of the edge.
     * @return the newly added edge.
     */
    @ExcludeFromJacocoGeneratedReport
    Edge<L, N> addEdge(N name, L len, Vertex<N> startVertex, Vertex<N> endVertex);

    /**
     * Removes the specified vertex from the graph.
     *
     * @param vertex the vertex to be removed.
     */
    @ExcludeFromJacocoGeneratedReport
    void delVertex(Vertex<N> vertex);

    /**
     * Removes the specified edge from the graph.
     *
     * @param edge the edge to be removed.
     */
    @ExcludeFromJacocoGeneratedReport
    void delEdge(Edge<L, N> edge);

    /**
     * Retrieves the incident edges of the specified vertex.
     *
     * @param currentVertex the vertex to find incident edges for.
     * @return an ArrayList of incident edges.
     */
    @ExcludeFromJacocoGeneratedReport
    ArrayList<Edge<L, N>> getIncidentEdges(Vertex<N> currentVertex);

    /**
     * Retrieves the vertices of the graph.
     *
     * @return an ArrayList of vertices in the graph.
     */
    @ExcludeFromJacocoGeneratedReport
    ArrayList<Vertex<N>> getGraphVertices();

    /**
     * Retrieves the edges of the graph.
     *
     * @return an ArrayList of edges in the graph.
     */
    @ExcludeFromJacocoGeneratedReport
    ArrayList<Edge<L, N>> getGraphEdges();

    /**
     * Default method to change the name of an edge.
     *
     * @param edge    the edge to be modified.
     * @param newName the new name for the edge.
     */
    @ExcludeFromJacocoGeneratedReport
    default void changeEdge(Edge<L, N> edge, N newName) {
        edge.setEdgeName(newName);
    }

    /**
     * Default method to change the name of a vertex.
     *
     * @param vertex  the vertex to be modified.
     * @param newName the new name for the vertex.
     */
    @ExcludeFromJacocoGeneratedReport
    default void changeVertex(Vertex<N> vertex, N newName) {
        vertex.setVertexName(newName);
    }
}
