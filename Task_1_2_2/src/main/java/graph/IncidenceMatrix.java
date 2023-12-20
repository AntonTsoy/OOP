package graph;

import java.util.ArrayList;

/**
 * 
 */
public class IncidenceMatrix<L extends Number, N> implements Graph<L, N> {
    private ArrayList<Vertex<N>> vertices;
    private ArrayList<Edge<L, N>> edges;
    private ArrayList<ArrayList<Boolean>> matrix;

    /**
     * 
     */
    public IncidenceMatrix() {
        this.vertices = new ArrayList<Vertex<N>>();
        this.edges = new ArrayList<Edge<L, N>>();
        this.matrix = new ArrayList<ArrayList<Boolean>>();
    }

    /**
     * 
     */
    @Override
    public Vertex<N> addVertex(N name) {
        var newVertex = new Vertex<N>(name);
        vertices.add(newVertex);
        matrix.add(new ArrayList<Boolean>(edges.size()));
        return newVertex;
    }

    /**
     * 
     */
    @Override
    public void delVertex(Vertex<N> vertex) {
        int vertexId = vertices.indexOf(vertex);
        for (int i = 0; i < edges.size(); i++) {
            if (matrix.get(vertexId).get(i) != null) {
                delEdge(edges.get(i));
            }
        }
        vertices.remove(vertexId);
        matrix.remove(vertexId);
    }

    /**
     * 
     */
    @Override
    public ArrayList<Edge<L, N>> getIncidentEdges(Vertex<N> currentVeretex) {
        var incidentEdges = new ArrayList<Edge<L, N>>();
        int vertexId = vertices.indexOf(currentVeretex);
        for (int i = 0; i < edges.size(); i++) {
            if (matrix.get(vertexId).get(i).booleanValue()) {
                incidentEdges.add(edges.get(i));
            }
        }
        return incidentEdges;
    }

    /**
     * 
     */
    @Override
    public ArrayList<Vertex<N>> getGraphVertices() {
        return new ArrayList<Vertex<N>>(vertices);
    }

    /**
     * 
     */
    @Override
    public ArrayList<Edge<L, N>> getGraphEdges() {
        return new ArrayList<Edge<L, N>>(edges);
    }

    /**
     * 
     */
    @Override
    public Edge<L, N> addEdge(N name, L len, Vertex<N> startVertex, Vertex<N> endVertex) {
        int startId = vertices.indexOf(startVertex);
        int endId = vertices.indexOf(endVertex);

        var newEdge = new Edge<L, N>(name, len, startVertex, endVertex);
        edges.add(newEdge);
        for (int i = 0; i < vertices.size(); i++) {
            if (startId == i) {
                matrix.get(i).add(true);
            } else if (endId == i) {
                matrix.get(i).add(false);
            } else {
                matrix.get(i).add(null);
            }
        }

        return newEdge;
    }

    /**
     * 
     */
    @Override
    public void delEdge(Edge<L, N> edge) {
        int edgeId = edges.indexOf(edge);
        for (int i = 0; i < vertices.size(); i++) {
            matrix.get(i).remove(edgeId);
        }
        edges.remove(edge);
    }
}