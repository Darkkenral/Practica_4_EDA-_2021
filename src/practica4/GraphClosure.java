package practica4;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import material.graphs.Digraph;
import material.graphs.Vertex;

/**
 *
 * @author mayte
 * @param <V>
 * @param <E>
 */
public class GraphClosure<V, E> {

    public GraphClosure() {
    }

    /**
     * Computes the Digraph's transitive clousure using the Floyd-Wharsall
     * algorithm
     *
     * @param g
     * @return
     */
    public Digraph<V, E> transitiveClosure(Digraph<V, E> g) {
        if (g.vertices().isEmpty()) {
            return g;
        }
        ArrayList<Digraph<V, E>> auxlist = new ArrayList<>();
        auxlist.add(g);
        for (int i = 1; i < g.vertices().size(); i++) {

            Collection<? extends Vertex<V>> aux = g.vertices();
            ArrayList<Vertex<V>> v = new ArrayList<>();
            v.addAll(aux);
            auxlist.add(i, auxlist.get(i - 1));
            for (int j = 1; j < v.size(); j++) {
                for (int k = 1; k < v.size(); k++) {
                    if ((i != j) && (i != k) && (j != k)) {
                        if ((auxlist.get(i - 1).areAdjacent(v.get(j), v.get(i))) && (auxlist.get(i - 1).areAdjacent(v.get(i), v.get(k)))) {
                            auxlist.get(i).insertEdge(v.get(j), v.get(k), null);
                        }

                    }
                }
            }

        }

        return auxlist.get(auxlist.size() - 1);
    }
}
