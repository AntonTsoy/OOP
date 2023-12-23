package graph;

/**
 * 
 */
public class Edge<L extends Number, N> {
    private N edgeName;
    private L edgeLen;

    private Vertex<N> startVertex;
    private Vertex<N> endVertex;

    /**
     * 
     * @param name
     * @param len
     * @param startVertex
     * @param endVertex
     */
    public Edge(N name, L len, Vertex<N> startVertex, Vertex<N> endVertex) {
        setEdgeName(name);
        setEdgeLen(len);
        
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    /**
     * 
     * @param name
     */
    public void setEdgeName(N name) {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be a NULL");
        } else {
            this.edgeName = name;
        }
    }

    /**
     * 
     * @return
     */
    public N getEdgeName() {
        return this.edgeName;
    }

    /**
     * 
     * @param len
     */
    public void setEdgeLen(L len) {
        if (Math.abs(len.doubleValue()) < 0.000001) {
            throw new IllegalArgumentException("Len can't be a ZERO");
        } else {
            this.edgeLen = len;
        }
    }

    /**
     * 
     * @return
     */
    public L getEdgeLen() {
        return this.edgeLen;
    }

    /**
     * 
     * @return
     */
    public Vertex<N> getStartVertex() {
        return this.startVertex;
    }
    
    /**
     * 
     * @return
     */
    public Vertex<N> getEndVertex() {
        return this.endVertex;
    }
}
