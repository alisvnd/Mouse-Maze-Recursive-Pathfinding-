import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Maze {
    public static int x = 5;
    public static int y  = 5;
    public static Cell mouse ;
    public static Cell cheese ;
    public static Cell [] [] cells ;

    public static void main(String[] args) {
        createCells();
        setMouse(0,0) ;
        setCheese(4,4) ;
        SwingUtilities.invokeLater(() -> new GUI(cells));
        
    }

    public static void findWays(Cell cell) {
        for (Cell nextCell : nearCells(cell)){
            if ((nextCell.getIsEmpty() || nextCell.getHasCheese()) && (nextCell.getMinSteps() == -1 || nextCell.getMinSteps() > cell.getMinSteps() + 1)){
                nextCell.setMinSteps(cell.getMinSteps() + 1);
                if (!nextCell.getHasCheese()){
                    findWays(nextCell);
                }
            }
        }
    }

    public static ArrayList<Cell> nearCells(Cell cell){
        ArrayList<Cell> nearCells = new ArrayList<>() ;
        int cellX = cell.getX() ;
        int cellY = cell.getY() ;
        if (cellX < x - 1){
            nearCells.add(cells[cellX + 1][cellY]) ;
        }
        if (cellX > 0){
            nearCells.add(cells[cellX - 1][cellY]) ;
        }
        if (cellY < y - 1){
            nearCells.add(cells[cellX][cellY + 1]) ;
        }
        if (cellY > 0){
            nearCells.add(cells[cellX ][cellY - 1]) ;
        }
        return nearCells ;
    }

    public static boolean createPath () {
        Cell cellInPath = cheese ;
        if (cellInPath.getMinSteps() == -1){
            return false;
        }
        mouse.setColor(Color.GREEN);
        do {
            for (Cell nextCell : nearCells(cellInPath)){
                if (nextCell.getMinSteps() + 1 == cellInPath.getMinSteps()){
                    cellInPath.setColor(Color.GREEN);
                    cellInPath = nextCell;
                    break;
                }
            }
        }  while (cellInPath != mouse) ;
        return true ;
    }

    public static void createCells() {
        cells = new Cell[x][y] ;
        for (int i = 0; i < x ; i++){
            for (int k = 0; k < y ; k++){
                cells [i][k] = new Cell(i, k) ;
            }
        }
    }

    public static void setMouse(int x, int y) {
        if (mouse != null){
            mouse.setIsEmpty(true);
        }
        mouse = cells[x][y] ;
        mouse.setHasMouse(true);
    }

    public static void setCheese(int x, int y) {
        if (cheese != null){
            cheese.setIsEmpty(true);
        }
        cheese = cells[x][y] ;
        cheese.setHasCheese(true);
    }

    public static void reset() {
        for (int i = 0; i < x ; i++){
            for (int k = 0; k < y ; k++){
                Cell cell = cells [i][k] ;
                cell.setIsEmpty(true);
                cell.setColor(Color.WHITE);
                cell.setMinSteps(-1);
            }
        }
        setMouse(0, 0);
        setCheese(4, 4);
    }
}
