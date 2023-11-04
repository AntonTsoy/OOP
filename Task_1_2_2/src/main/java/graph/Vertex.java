package graph;

public class Vertex<N> {
    private N vertexName;

    /**
     * 
     * @param name
     */
    public Vertex(N name) {
        setVertexName(name);
    }

    /**
     * 
     * @param name
     */
    public void setVertexName(N name) {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be a NULL");
        } else {
            this.vertexName = name;
        }
    }

    /**
     * 
     * @return
     */
    public N getVertexName() {
        return this.vertexName;
    }
}
