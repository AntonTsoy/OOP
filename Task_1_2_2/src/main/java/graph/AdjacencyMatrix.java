package graph;

import java.util.ArrayList;


public class AdjacencyMatrix<L extends Number, N> implements Graph<L, N> {

    private ArrayList<Vertex<N>> vertices;
    private ArrayList<ArrayList<L>> matrix;
    private ArrayList<Edge<L, N>> edges;


    public AdjacencyMatrix() {
        this.vertices = new ArrayList<Vertex<N>>();
        this.matrix = new ArrayList<ArrayList<L>>();
        this.edges = new ArrayList<Edge<L, N>>();
    }


    @Override
    public Vertex<N> addVertex(N name) {
        Vertex<N> newVertex = new Vertex<N>(name);
        vertices.add(newVertex);
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


    @Override
    public void delVertex(Vertex<N> vertex) {
        int vertexId = vertices.indexOf(vertex);
        for (int i = 0; i < vertices.size(); i++) {
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


    @Override
    public ArrayList<Edge<L, N>> getIncidentEdges(Vertex<N> currentVeretex) {
        var incidentEdges = new ArrayList<Edge<L, N>>();
        for (Edge<L, N> edge : edges) {
            if (edge.getStartVertex() == currentVeretex) {
                incidentEdges.add(edge);
            }
        }

        return incidentEdges;
    }


    @Override
    public ArrayList<Vertex<N>> getGraphVertices() {
        return new ArrayList<Vertex<N>>(vertices);
    }


    @Override
    public ArrayList<Edge<L, N>> getGraphEdges() {
        return new ArrayList<Edge<L, N>>(edges);
    }


    @Override
    public Edge<L, N> addEdge(N name, L len, Vertex<N> startVertex, Vertex<N> endVertex) {
        int startId = vertices.indexOf(startVertex);
        int endId = vertices.indexOf(endVertex);
        matrix.get(startId).set(endId, len);

        var newEdge = new Edge<L, N>(name, len, startVertex, endVertex);
        edges.add(newEdge);
        return newEdge;
    }


    @Override
    public void delEdge(Edge<L, N> edge) {
        int startId = vertices.indexOf(edge.getStartVertex());
        int endId = vertices.indexOf(edge.getEndVertex());
        matrix.get(startId).set(endId, null);
        edges.remove(edge);
    }
}
