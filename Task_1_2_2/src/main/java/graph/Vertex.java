package graph;


public class Vertex<N> {
    private N vertexName;


    public Vertex(N name) {
        setVertexName(name);
    }


    public void setVertexName(N name) {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be a NULL");
        } else {
            this.vertexName = name;
        }
    }


    public N getVertexName() {
        return this.vertexName;
    }
}
