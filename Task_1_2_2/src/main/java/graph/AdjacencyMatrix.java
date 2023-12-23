package graph;

import java.util.ArrayList;

/**
 * Represents a graph using an adjacency matrix.
 *
 * @param <L> the type of the edge length.
 * @param <N> the type of the vertex name.
 */
public class AdjacencyMatrix<L extends Number, N> implements Graph<L, N> {
    // List of vertices in the graph.
    private ArrayList<Vertex<N>> vertices;
    // Adjacency matrix representing the relationships between vertices and edges.
    private ArrayList<ArrayList<L>> matrix;
    // List of edges in the graph.
    private ArrayList<Edge<L, N>> edges;

    /**
     * Constructs an empty AdjacencyMatrix graph.
     */
    public AdjacencyMatrix() {
        this.vertices = new ArrayList<Vertex<N>>();
        this.matrix = new ArrayList<ArrayList<L>>();
        this.edges = new ArrayList<Edge<L, N>>();
    }

    /**
     * Adds a new vertex to the graph with the specified name.
     *
     * @param name the name of the new vertex.
     * @return the newly added vertex.
     */
    @Override
    public Vertex<N> addVertex(N name) {
        Vertex<N> newVertex = new Vertex<N>(name);
        vertices.add(newVertex);

        // Adjust the adjacency matrix to include the new vertex.
        for (int i = 0; i < matrix.size(); i++) {
            matrix.get(i).add(null);
        }

        ArrayList<L> addedVertexList = new ArrayList<L>();
        matrix.add(addedVertexList);
        for (int i = 0; i < vertices.size(); i++) {
            addedVertexList.add(null);
        }

        return newVertex;
    }

    /**
     * Removes the specified vertex from the graph.
     *
     * @param vertex the vertex to be removed.
     */
    @Override
    public void delVertex(Vertex<N> vertex) {
        int vertexId = vertices.indexOf(vertex);

        // Adjust the adjacency matrix to remove the vertex.
        for (int i = 0; i < vertices.size(); i++) {
            matrix.get(i).remove(vertexId);
        }
        matrix.remove(vertexId);

        // Remove edges associated with the vertex.
        int edgeId = 0;
        while (edgeId < edges.size()) {
            if (edges.get(edgeId).getStartVertex() == vertex || edges.get(edgeId).getEndVertex() == vertex) {
                edges.remove(edgeId);
            } else {
                edgeId++;
            }
        }

        // Remove the vertex itself.
        vertices.remove(vertex);
    }

    /**
     * Retrieves the incident edges of the specified vertex.
     *
     * @param currentVertex the vertex to find incident edges for.
     * @return an ArrayList of incident edges.
     */
    @Override
    public ArrayList<Edge<L, N>> getIncidentEdges(Vertex<N> currentVertex) {
        var incidentEdges = new ArrayList<Edge<L, N>>();
        for (Edge<L, N> edge : edges) {
            if (edge.getStartVertex() == currentVertex) {
                incidentEdges.add(edge);
            }
        }

        return incidentEdges;
    }

    /**
     * Retrieves the vertices of the graph.
     *
     * @return an ArrayList of vertices in the graph.
     */
    @Override
    public ArrayList<Vertex<N>> getGraphVertices() {
        return new ArrayList<Vertex<N>>(vertices);
    }

    /**
     * Retrieves the edges of the graph.
     *
     * @return an ArrayList of edges in the graph.
     */
    @Override
    public ArrayList<Edge<L, N>> getGraphEdges() {
        return new ArrayList<Edge<L, N>>(edges);
    }

    /**
     * Adds a new edge to the graph with the specified name, length, and start and end vertices.
     *
     * @param name        the name of the new edge.
     * @param len         the length of the new edge.
     * @param startVertex the starting vertex of the edge.
     * @param endVertex   the ending vertex of the edge.
     * @return the newly added edge.
     */
    @Override
    public Edge<L, N> addEdge(N name, L len, Vertex<N> startVertex, Vertex<N> endVertex) {
        int startId = vertices.indexOf(startVertex);
        int endId = vertices.indexOf(endVertex);
        matrix.get(startId).set(endId, len);

        var newEdge = new Edge<L, N>(name, len, startVertex, endVertex);
        edges.add(newEdge);
        return newEdge;
    }

    /**
     * Removes the specified edge from the graph.
     *
     * @param edge the edge to be removed.
     */
    @Override
    public void delEdge(Edge<L, N> edge) {
        int startId = vertices.indexOf(edge.getStartVertex());
        int endId = vertices.indexOf(edge.getEndVertex());
        matrix.get(startId).set(endId, null);
        edges.remove(edge);
    }
}
