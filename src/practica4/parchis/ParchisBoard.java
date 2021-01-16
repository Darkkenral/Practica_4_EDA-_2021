package practica4.parchis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import material.graphs.BreadthSearch;
import material.graphs.Digraph;
import material.graphs.ELDigraph;
import material.graphs.Vertex;

/**
 *
 * @author jvelez
 */
public class ParchisBoard {
    
    public static class Square {
        
        SquareType tipo;
        int numero;
        Color color;
        List<ParchisChip> lista;
        
        public Square(String t, String c) {
            selectType(t);
            selectColor(c);
            lista = new ArrayList<>();
        }
        
        private void selectColor(String c) {
            switch (c) {
                case "red":
                    color = Color.red;
                    break;
                case "blue":
                    color = Color.blue;
                    break;
                case "yellow":
                    color = Color.yellow;
                    break;
                case "green":
                    color = Color.green;
                    break;
                default:
                    color = null;
                    break;
            }
        }
        
        private void selectType(String t) {
            switch (t) {
                case "Normal":
                    tipo = SquareType.Normal;
                    break;
                case "Home":
                    tipo = SquareType.Home;
                    break;
                case "Destiny":
                    tipo = SquareType.Destiny;
                    break;
            }
        }
        
        public String getTipo() {
            return tipo.name();
        }
        
        public String getColor() {
            return color.name();
        }
        
        public int getNumero() {
            return numero;
        }
        
        public void setNumero(int i) {
            numero = i;
        }
        
        public List<ParchisChip> getLista() {
            return lista;
        }
        
    }
    
    public enum Color {
        red, green, blue, yellow
    };
    
    public enum SquareType {
        Normal, Home, Destiny
    };
    
    private Digraph<Square, String> tablero;

    /**
     * Crea un tablero de parchis con todas sus casillas y con 4 piezas de cada
     * color en sus casas correspondientes.
     */
    public ParchisBoard() {
        tablero = new ELDigraph<>();
        Square red = new Square("Home", "red");
        Square yellow = new Square("Home", "yellow");
        Square blue = new Square("Home", "blue");
        Square green = new Square("Home", "green");
        Square s = new Square("Normal", "");
        s.setNumero(1);
        Vertex<Square> v1 = tablero.insertVertex(s);
        
        for (int i = 2; i < 69; i++) {

            // Para las casillas normales
            s = new Square("Normal", "");
            s.setNumero(i);
            Vertex<Square> v2 = tablero.insertVertex(s);
            String aux = Integer.toString(i - 1);
            aux = aux.concat(" - ");
            aux = aux.concat(Integer.toString(i));
            tablero.insertEdge(v1, v2, aux);
            v1 = v2;

            //casos especiales 
            switch (i) {
                case 5:
                    setHomeSquare(yellow, i, v2, "Yellow Home", "yellow");
                    break;
                case 17:
                    setDestinySquare(i, v2, "blue", "B");
                    break;
                case 22:
                    setHomeSquare(yellow, i, v2, "Blue Home", "blue");
                    break;
                case 34:
                    setDestinySquare(i, v2, "red", "R");
                    break;
                case 39:
                    
                    setHomeSquare(yellow, i, v2, "Red Home", "red");
                    break;
                case 51:
                    setDestinySquare(i, v2, "green", "G");
                    
                    break;
                case 56:
                    setHomeSquare(yellow, i, v2, "Green Home", "green");
                    break;
                case 68:
                    setDestinySquare(i, v2, "yellow", "Y");
                    break;
                default:
                    break;
            }
            
        }
    }
    
    private void setDestinySquare(int i, Vertex<Square> v2, String color, String leter) {
        String aux;
        for (int j = 1; j < 9; j++) {
            Square d = new Square("Destiny", color);
            d.setNumero(j);
            Vertex<Square> des = tablero.insertVertex(d);
            if (j == 1) {
                aux = Integer.toString(i);
                aux = aux.concat(" - " + leter);
                aux = aux.concat(Integer.toString(j));
            } else {
                aux = leter;
                aux = aux.concat(Integer.toString(j - 1));
                aux = aux.concat(" - " + leter);
                aux = aux.concat(Integer.toString(j));
            }
            tablero.insertEdge(v2, des, aux);
            v2 = des;
        }
    }
    
    private void setHomeSquare(Square s, int i, Vertex<Square> v2, String colorHome, String color) {
        String aux;
        s.setNumero(0);
        Vertex<Square> y = tablero.insertVertex(s);
        aux = colorHome;
        aux = aux.concat(" - ");
        aux = aux.concat(Integer.toString(i));
        tablero.insertEdge(y, v2, aux);
        for (int j = 0; j < 4; j++) {
            ParchisChip c = new ParchisChip(y, color);
            y.getElement().getLista().add(c);
        }
    }

    /**
     * Devuelve una referencia a una ficha contenida en la casilla indicada por
     * squeareName y cuyo color se corresponde con el color indicado.
     *
     * @param squareNumber número de 0 a 68. - El número de las casillas
     * normales varia de 1 a 68. - El número de las casillas destino varia de 1
     * a 9. - El número de las casillas home simpre es 0.
     * @param type tipo de la casilla.
     * @param color de la ficha a devolver.
     * @return Si en la casilla no está la ficha devuelve null, en otro caso
     * devuelve la ficha obtenida.
     */
    ParchisChip getChip(int squareNumber, SquareType type, Color color) {
        Collection<? extends Vertex<Square>> vertices = tablero.vertices();
        for (Vertex<Square> vertice : vertices) {
            if (vertice.getElement().getNumero()== squareNumber) {
                List<ParchisChip> lista = vertice.getElement().getLista();
                for (ParchisChip parchisChip : lista) {
                    if ( parchisChip.getColor()== color.name()) {
                        return parchisChip;
                    }

                }
                
            }
            
        }
        
        return null;
    }

    /**
     * Mueve la ficha c el número de posiciones indicado por n. Si la ficha
     * llega a la casilla de entrada a casa debe tomar el camino
     * correspondiente.
     *
     * @param c el color
     * @return Si la ficha cae en una casilla en la que hay una ficha de otro
     * color (y no es segura) devuelve la otra ficha (que se ha comido) y que
     * automáticamente se habrá ido a la casa de su color. En otro caso devuelve
     * null. Si el movimiento no fue posible (por haber un puente en el destino
     * ) lanzará una excepción.
     */
    ParchisChip move(ParchisChip c, int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param c la ficha a mover
     * @param n el número de casillas a mover.
     * @return Devuelve true si el movimiento es posible y false en caso
     * contraio.
     */
    boolean canMove(ParchisChip c, int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return Devuelve una cadena con las casillas ocupadas del tablero y su
     * contenido.
     */
    String drawBoard() {
        String out = "";
        Collection<? extends Vertex<Square>> listvertex = tablero.vertices();
        for (Vertex<Square> vertex : listvertex) {
            List<ParchisChip> listofchips = vertex.getElement().getLista();
            if (!listofchips.isEmpty()) {
                out = out.concat("Casilla:" + vertex.getElement().getNumero() + " , Contenido: ");
                
                for (ParchisChip chip : listofchips) {
                    out = out.concat(chip.toString());
                }
                out = out.concat("\n");
            }
            System.out.println(out );
        }
        return out;
    }
    
}
