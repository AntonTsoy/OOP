package graph;


public class Edge<L extends Number, N> {
    private N edgeName;
    private L edgeLen;

    private Vertex<N> startVertex;
    private Vertex<N> endVertex;


    public Edge(N name, L len, Vertex<N> startVertex, Vertex<N> endVertex) {
        setEdgeName(name);
        setEdgeLen(len);
        
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }


    public void setEdgeName(N name) {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be a NULL");
        } else {
            this.edgeName = name;
        }
    }


    public N getEdgeName() {
        return this.edgeName;
    }


    public void setEdgeLen(L len) {
        if (Math.abs(len.doubleValue()) < 0.000001) {
            throw new IllegalArgumentException("Len can't be a ZERO");
        } else {
            this.edgeLen = len;
        }
    }


    public L getEdgeLen() {
        return this.edgeLen;
    }


    public Vertex<N> getStartVertex() {
        return this.startVertex;
    }
    

    public Vertex<N> getEndVertex() {
        return this.endVertex;
    }
}