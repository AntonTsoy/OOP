package graph;

import java.util.ArrayList;

/**
 * 
 */
public class AdjacencyList<L extends Number, N> implements Graph<L, N> {

    private ArrayList<Vertex<N>> vertices;
    private ArrayList<ArrayList<Edge<L, N>>> incidenceList;

    /**
     * 
     */
    public AdjacencyList() {
        this.vertices = new ArrayList<Vertex<N>>();
        this.incidenceList = new ArrayList<ArrayList<Edge<L, N>>>();
    }

    /**
     * 
     */
    @Override
    public Vertex<N> addVertex(N name) {
        var newVertex = new Vertex<N>(name);
        vertices.add(newVertex);
        return newVertex;
    }

    /**
     * 
     */
    @Override
    public void delVertex(Vertex<N> vertex) {
        for (int i = 0; i < vertices.size(); i++) {
            ArrayList<Edge<L, N>> curVertex = incidenceList.get(i);
            for (int j = 0; j < curVertex.size(); j++) {
                if (curVertex.get(j).getEndVertex() == vertex) {
                    curVertex.remove(j);
                    break;
                }
            }
        }
        vertices.remove(vertex);
    }

    /**
     * 
     */
    @Override
    public ArrayList<Edge<L, N>> getIncidentEdges(Vertex<N> currentVeretex) {
        return incidenceList.get(vertices.indexOf(currentVeretex));
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
    public Edge<L, N> addEdge(N name, L len, Vertex<N> startVertex, Vertex<N> endVertex) {
        int startId = vertices.indexOf(startVertex);
        var newEdge = new Edge<L, N>(name, len, startVertex, endVertex);
        incidenceList.get(startId).add(newEdge);
        
        return newEdge;
    }

    /**
     * 
     */
    @Override
    public void delEdge(Edge<L, N> edge) {
        int curVertexId = 0;
        while (curVertexId < vertices.size()) {
            ArrayList<Edge<L, N>> curVertex = incidenceList.get(curVertexId);
            if (curVertex.contains(edge)) {
                curVertex.remove(edge);
                break;
            }
            curVertexId++;
        }
    }
}
