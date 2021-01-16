package material.graphs;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import material.Position;
import material.tree.narytree.LinkedTree;

/**
 *
 * @author jvelez
 * @param <V>
 * @param <E>
 */
public class BreadthSearch<V, E> {

    enum Label {
        DISCOVERY, CROSS
    };

    private List<Edge<E>> pathToRoot(Graph<V, E> g, Position<Vertex<V>> node, LinkedTree<Vertex<V>> tree) {
        List<Edge<E>> result = new LinkedList<>();

        while (node != tree.root()) {
            Position<Vertex<V>> parent = tree.parent(node);
            Edge<E> edge = g.areAdjacent(node.getElement(), parent.getElement());
            result.add(0, edge);
            node = parent;
        }

        return result;
    }

    /**
     * Get the path between two vertex with minimun number of nodes.
     *
     * @param graph
     * @param startVertex
     * @param endVertex
     * @return The edge list
     */
    public List<Edge<E>> getPath(Graph<V, E> graph, Vertex<V> startVertex, Vertex<V> endVertex) {
        LinkedTree<Vertex<V>> tree = new LinkedTree<>();
        HashMap<Edge<E>, Label> edgeLabels = new HashMap<>();

        Queue<Position<Vertex<V>>> queue = new LinkedList<>();
        HashSet<Vertex<V>> visitedNodes = new HashSet<>();

        visitedNodes.add(startVertex);
        tree.addRoot(startVertex);
        queue.add(tree.root());

        while (!queue.isEmpty()) {
            Position<Vertex<V>> vetexPos = queue.poll();
            Vertex<V> vertex = vetexPos.getElement();
            for (Edge<E> edge : graph.incidentEdges(vertex)) {
                if (edgeLabels.get(edge) == null) {
                    Vertex<V> nextNode = graph.opposite(vertex, edge);
                    if (!visitedNodes.contains(nextNode)) {
                        edgeLabels.put(edge, Label.DISCOVERY);
                        visitedNodes.add(nextNode);
                        Position<Vertex<V>> treeNode = tree.add(nextNode, vetexPos);
                        queue.add(treeNode);
                        if (nextNode == endVertex) {
                            return pathToRoot(graph, treeNode, tree);
                        }
                    } else {
                        edgeLabels.put(edge, Label.CROSS);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Devuelve el conjunto de vértices visitados al hacer un recorrido en profundidad partiendo de root
     * @param g
     * @param root
     * @return 
     */
    public Set<Vertex<V>> traverse(Graph<V, E> g, Vertex<V> root) {
        Set<Vertex<V>> visited = new HashSet<>();
        Queue<Vertex<V>> q = new ArrayDeque<>();
        q.add(root);
        visited.add(root);
        while (!q.isEmpty()) {
            Vertex<V> v = q.peek();
            visited.add(v);
            for (Edge<E> e : g.incidentEdges(v)) {
                Vertex<V> op = g.opposite(v, e);
                if (!visited.contains(op)) {
                    q.add(op);
                    visited.add(op);
                }
            }
            q.poll();
        }
        return visited;
    }
}
