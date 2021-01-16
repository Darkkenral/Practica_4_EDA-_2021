package practica4.parchis;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class ParchisBoardTest {

    public ParchisBoardTest() {
    }

    

    /**
     * Test of move method, of class ParchisBoard.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        ParchisBoard board = new ParchisBoard();
        ParchisChip a = board.getChip(0, ParchisBoard.SquareType.Home, ParchisBoard.Color.yellow);
        assertEquals(board.move(a, 1), null);
    }

    /**
     * Test of canMove method, of class ParchisBoard.
     */
    @Test
    public void testCanMove() {
        System.out.println("canMove");
        ParchisBoard board = new ParchisBoard();
        ParchisChip a = board.getChip(0, ParchisBoard.SquareType.Home, ParchisBoard.Color.yellow);
        assertEquals(board.canMove(a, 1), true);
    }

    /**
     * Test of drawBoard method, of class ParchisBoard.
     */
    @Test
    public void testDrawBoard() {
        System.out.println("drawBoard");
        ParchisBoard board = new ParchisBoard();
        ParchisChip a = board.getChip(0, ParchisBoard.SquareType.Home, ParchisBoard.Color.yellow);
        String expResult = "YellowHome->Yellow(4)\nBlue Home->Blue(4)\nRed Home->Red(4)\nGreen Home->Green(4)\n5->Yellow(1)";
        assertEquals(expResult, board.drawBoard());
    }

}
