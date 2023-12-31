package graph;

/**
 * Represents a vertex in a graph.
 *
 * @param <N> the type of the vertex name.
 */
public class Vertex<N> {
    // The name of the vertex.
    private N vertexName;

    /**
     * Constructs a new Vertex with the specified name.
     *
     * @param name the name of the vertex.
     * @throws IllegalArgumentException if the provided name is null.
     */
    public Vertex(N name) {
        setVertexName(name);
    }

    /**
     * Sets the name of the vertex.
     *
     * @param name the new name for the vertex.
     * @throws IllegalArgumentException if the provided name is null.
     */
    public void setVertexName(N name) {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be a NULL");
        } else {
            this.vertexName = name;
        }
    }

    /**
     * Gets the name of the vertex.
     *
     * @return the name of the vertex.
     */
    public N getVertexName() {
        return this.vertexName;
    }
}
