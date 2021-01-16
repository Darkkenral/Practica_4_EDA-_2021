package material.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mayte
 * @param <V>
 * @param <E>
 */
public class ALGraph<V, E> implements Graph<V, E> {

    private Set<ALEdge<E>> edges;
    private Set<ALVertex<V>> vertices;

    private class ALVertex<V> implements Vertex<V> {

        private Set<ALEdge<E>> adjacentList;
        private V value;

        public ALVertex(V value) {
            this.value = value;
            this.adjacentList = new HashSet<>();
        }

        public Set<ALEdge<E>> getAdjacentList() {
            return adjacentList;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public V getElement() {
            return this.value;
        }
    }

    private class ALEdge<E> implements Edge<E> {

        private ALVertex<V> origin;
        private ALVertex<V> destiny;
        private E value;

        public ALEdge(ALVertex<V> origin, ALVertex<V> destiny, E value) {
            this.origin = origin;
            this.destiny = destiny;
            this.value = value;
        }

        public ALVertex<V> getOrigin() {
            return origin;
        }

        public ALVertex<V> getDestiny() {
            return destiny;
        }

        public void setValue(E value) {
            this.value = value;
        }

        @Override
        public E getElement() {
            return this.value;

        }

    }

    public ALGraph() {
        this.edges = new HashSet<>();
        this.vertices = new HashSet<>();
    }

    @Override
    public Collection<? extends Vertex<V>> vertices() {
        return Collections.unmodifiableCollection(vertices);
    }

    @Override
    public Collection<? extends Edge<E>> edges() {
        return Collections.unmodifiableCollection(edges);
    }

    @Override
    public Collection<? extends Edge<E>> incidentEdges(Vertex<V> v) {
        ALVertex<V> vertex = checkVertex(v);
        return Collections.unmodifiableCollection(vertex.getAdjacentList());

    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        ALEdge<E> edge = checkEdge(e);
        ALVertex<V> vertex = checkVertex(v);
        if (!vertex.adjacentList.contains(edge)) {
            throw new RuntimeException("La arista y el grafo no coinciden");
        }
        if (edge.getOrigin() == vertex) {
            return edge.getDestiny();
        }

        return edge.getOrigin();
    }

    @Override
    public List<Vertex<V>> endVertices(Edge<E> edge) {
        ALEdge<E> e = checkEdge(edge);
        List<Vertex<V>> list = new ArrayList<>();
        list.add(e.getOrigin());
        list.add(e.getDestiny());

        return list;
    }

    @Override
    public Edge<E> areAdjacent(Vertex<V> v1, Vertex<V> v2) {
        ALVertex<V> vertex1 = checkVertex(v1);
        ALVertex<V> vertex2 = checkVertex(v2);
        Set<ALEdge<E>> adjacent = vertex1.getAdjacentList();
        Iterator<ALEdge<E>> it = adjacent.iterator();
        while (it.hasNext()) {
            ALEdge<E> next = it.next();
            if ((next.getOrigin() == vertex1) || (next.getOrigin() == vertex2)) {
                return next;
            }

        }
        return null;
    }

    @Override
    public V replace(Vertex<V> vertex, V vertexValue) {
        ALVertex<V> v = checkVertex(vertex);
        V aux = v.getElement();
        v.setValue(vertexValue);
        return aux;
    }

    @Override
    public E replace(Edge<E> edge, E edgeValue) {
        ALEdge<E> e = checkEdge(edge);
        E aux = e.getElement();
        e.setValue(edgeValue);
        return aux;
    }

    @Override
    public Vertex<V> insertVertex(V value) {
        ALVertex<V> v = new ALVertex<>(value);
        this.vertices.add(v);
        return v;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> v1, Vertex<V> v2, E edgeValue) {
        ALVertex<V> vertex1 = checkVertex(v1);
        ALVertex<V> vertex2 = checkVertex(v2);
        Edge<E> areAdjacent = areAdjacent(v1, v2);
        if (areAdjacent == null) {
            ALEdge<E> edge = new ALEdge<>(vertex1, vertex2, edgeValue);
            vertex1.getAdjacentList().add(edge);
            vertex2.getAdjacentList().add(edge);
            edges.add(edge);
            return edge;
        }
        this.replace(areAdjacent, edgeValue);
        return areAdjacent;

    }

    @Override
    public V removeVertex(Vertex<V> vertex) {
        ALVertex<V> v = checkVertex(vertex);
        Iterator<ALEdge<E>> it = v.getAdjacentList().iterator();
        while (it.hasNext()) {
            ALEdge<E> next = it.next();
            removeEdge(next);
        }

        this.vertices.remove(v);
        return v.getElement();

    }

    @Override
    public E removeEdge(Edge<E> edge) {
        ALEdge<E> e = checkEdge(edge);
        e.getOrigin().getAdjacentList().remove(e);
        e.getDestiny().getAdjacentList().remove(e);
        this.edges.remove(e);
        return e.getElement();
    }

    private ALEdge<E> checkEdge(Edge<E> edge) {
        if (edge instanceof ALEdge) {
            ALEdge<E> e = (ALEdge<E>) edge;
            if (edges.contains(e)) {
                return e;
            }
        }
        throw new RuntimeException("The edge is not in the graph");
    }

    private ALVertex<V> checkVertex(Vertex<V> vertex) {
        if (vertex instanceof ALVertex) {
            ALVertex<V> v = (ALVertex<V>) vertex;
            if (vertices.contains(v)) {
                return v;
            }
        }
        throw new RuntimeException("The edge is not in the graph");
    }

}
