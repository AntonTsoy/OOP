package graph;

/**
 * Represents an edge in a graph.
 *
 * @param <L> the type of the edge length.
 * @param <N> the type of the vertex name.
 */
public class Edge<L extends Number, N> {
    // The name of the edge.
    private N edgeName;
    // The length of the edge.
    private L edgeLen;

    // The starting vertex of the edge.
    private Vertex<N> startVertex;
    // The ending vertex of the edge.
    private Vertex<N> endVertex;

    /**
     * Constructs a new Edge with the specified name, length, and start and end vertices.
     *
     * @param name        the name of the edge.
     * @param len         the length of the edge.
     * @param startVertex the starting vertex of the edge.
     * @param endVertex   the ending vertex of the edge.
     */
    public Edge(N name, L len, Vertex<N> startVertex, Vertex<N> endVertex) {
        setEdgeName(name);
        setEdgeLen(len);

        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    /**
     * Sets the name of the edge.
     *
     * @param name the new name for the edge.
     * @throws IllegalArgumentException if the provided name is null.
     */
    public void setEdgeName(N name) {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be NULL");
        } else {
            this.edgeName = name;
        }
    }

    /**
     * Gets the name of the edge.
     *
     * @return the name of the edge.
     */
    public N getEdgeName() {
        return this.edgeName;
    }

    /**
     * Sets the length of the edge.
     *
     * @param len the new length for the edge.
     * @throws IllegalArgumentException if the provided length is zero.
     */
    public void setEdgeLen(L len) {
        if (Math.abs(len.doubleValue()) < 0.000001) {
            throw new IllegalArgumentException("Len can't be ZERO");
        } else {
            this.edgeLen = len;
        }
    }

    /**
     * Gets the length of the edge.
     *
     * @return the length of the edge.
     */
    public L getEdgeLen() {
        return this.edgeLen;
    }

    /**
     * Gets the starting vertex of the edge.
     *
     * @return the starting vertex of the edge.
     */
    public Vertex<N> getStartVertex() {
        return this.startVertex;
    }

    /**
     * Gets the ending vertex of the edge.
     *
     * @return the ending vertex of the edge.
     */
    public Vertex<N> getEndVertex() {
        return this.endVertex;
    }
}
