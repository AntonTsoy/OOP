package graph;

import java.util.ArrayList;

/**
 * Represents a graph using an adjacency list.
 *
 * @param <L> the type of the edge length.
 * @param <N> the type of the vertex name.
 */
public class AdjacencyList<L extends Number, N> implements Graph<L, N> {
    // List of vertices in the graph.
    private ArrayList<Vertex<N>> vertices;
    // Incidence list representing the relationships between vertices and edges.
    private ArrayList<ArrayList<Edge<L, N>>> incidenceList;

    /**
     * Constructs an empty AdjacencyList graph.
     * Here we need the list of vertices and list of lists of edges.
     */
    public AdjacencyList() {
        this.vertices = new ArrayList<Vertex<N>>();
        this.incidenceList = new ArrayList<ArrayList<Edge<L, N>>>();
    }

    /**
     * Adds a new vertex to the graph with the specified name.
     * Also this function creates new list-elemnt in the incidenceList.
     * Index of vertice corresponds the index of its list-element in incidenceList.
     *
     * @param name the name of the new vertex.
     * @return the newly added vertex.
     */
    @Override
    public Vertex<N> addVertex(N name) {
        Vertex<N> newVertex = new Vertex<>(name);
        vertices.add(newVertex);

        ArrayList<Edge<L, N>> newEdges = new ArrayList<>();
        incidenceList.add(newEdges);

        return newVertex;
    }

    /**
     * Removes the specified vertex from the graph and removes all the edges linked with vertex.
     * Removes corresponding list-element from incidenceList.
     *
     * @param vertex the vertex to be removed.
     */
    @Override
    public void delVertex(Vertex<N> vertex) {
        for (int i = 0; i < vertices.size(); i++) {
            ArrayList<Edge<L, N>> curVertex = incidenceList.get(i);
            int edgeId = 0;
            while (edgeId < curVertex.size()) {
                if (curVertex.get(edgeId).getStartVertex() == vertex 
                    || curVertex.get(edgeId).getEndVertex() == vertex
                ) {
                    curVertex.remove(edgeId);
                } else {
                    edgeId++;
                }
            }
        }

        int delVertexId = vertices.indexOf(vertex);
        incidenceList.remove(delVertexId);
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
        return new ArrayList<>(incidenceList.get(vertices.indexOf(currentVertex)));
    }

    /**
     * Retrieves the vertices of the graph.
     *
     * @return an ArrayList of vertices in the graph.
     */
    @Override
    public ArrayList<Vertex<N>> getGraphVertices() {
        return new ArrayList<>(vertices);
    }

    /**
     * Retrieves the edges of the graph.
     *
     * @return an ArrayList of edges in the graph.
     */
    @Override
    public ArrayList<Edge<L, N>> getGraphEdges() {
        ArrayList<Edge<L, N>> graphEdges = new ArrayList<>();
        for (ArrayList<Edge<L, N>> currEdges : incidenceList) {
            graphEdges.addAll(currEdges);
        }

        return graphEdges;
    }

    /**
     * Adds a new edge to the graph with the specified name, length, and start and end vertices.
     * This method creates a new object of Edge. Start and end vertex of edge must be graph part.
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
        Edge<L, N> newEdge = new Edge<>(name, len, startVertex, endVertex);
        incidenceList.get(startId).add(newEdge);

        return newEdge;
    }

    /**
     * Removes the specified edge from the graph.
     *
     * @param edge the edge to be removed.
     */
    @Override
    public void delEdge(Edge<L, N> edge) {
        int startVertexId = vertices.indexOf(edge.getStartVertex());
        incidenceList.get(startVertexId).remove(edge);
    }
}
