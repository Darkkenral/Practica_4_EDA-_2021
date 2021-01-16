package practica4.parchis;

import material.graphs.Vertex;
import practica4.parchis.ParchisBoard.Color;
import practica4.parchis.ParchisBoard.Square;

/**
 *
 * @author jvelez
 */
class ParchisChip {

    private Vertex<Square> posicion;
    private Color color;

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

    public ParchisChip(Vertex<Square> posicion, String color) {
        this.posicion = posicion;
        selectColor(color);
    }

    public Vertex<Square> getPosicion() {
        return posicion;
    }

    public void setPosicion(Vertex<Square> posicion) {
        this.posicion = posicion;
    }

    public String getColor() {
        return color.name();
    }

    @Override
    public String toString() {
        return "Color :" + getColor();
    }

}
