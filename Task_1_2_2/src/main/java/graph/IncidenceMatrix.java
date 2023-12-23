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
        Vertex<N> newVertex = new Vertex<N>(name);
        vertices.add(newVertex);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.get(i).add(false);
        }

        ArrayList<Boolean> addedVertexList = new ArrayList<Boolean>();
        matrix.add(addedVertexList);
        for (int i = 0; i < vertices.size(); i++) {
            addedVertexList.add(false);
        }
        
        return newVertex;
    }


    /**
     * 
     */
    @Override
    public void delVertex(Vertex<N> vertex) {
        int vertexId = vertices.indexOf(vertex);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.get(i).remove(vertexId);
        }
        matrix.remove(vertexId);

        int edgeId = 0;
        while (edgeId < edges.size()) {
            if (edges.get(edgeId).getStartVertex() == vertex) {
                edges.remove(edgeId);
            } else if (edges.get(edgeId).getEndVertex() == vertex) {
                edges.remove(edgeId);
            } else {
                edgeId++;
            }
        }
        
        vertices.remove(vertex);
    }

    /**
     * 
     */
    @Override
    public ArrayList<Edge<L, N>> getIncidentEdges(Vertex<N> currentVertex) {
        var incidentEdges = new ArrayList<Edge<L, N>>();
        for (Edge<L, N> currEdge : edges) {
            if (currEdge.getStartVertex() == currentVertex) {
                incidentEdges.add(currEdge);
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
        matrix.get(startId).set(endId, true);

        var newEdge = new Edge<L, N>(name, len, startVertex, endVertex);
        edges.add(newEdge);

        return newEdge;
    }

    /**
     * 
     */
    @Override 
    public void delEdge(Edge<L, N> edge) {
        int startId = vertices.indexOf(edge.getStartVertex());
        int endId = vertices.indexOf(edge.getEndVertex());
        matrix.get(startId).set(endId, false);
        edges.remove(edge);
    }
}