import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class Cell {
    private int cellNo ;
    private ArrayList<Player> playersOnCell ;
    private String symbol ;
    Color color ;
    private int x;
    private int y;

    Cell (int cellNo, String symbol, int x, int y, Color color){
        this.cellNo = cellNo;
        this.symbol = symbol ;
        this.playersOnCell = new ArrayList<>() ;
        this.color = color ;
        this.x = x;
        this.y = y;
    }
    public String getLine1() {
        String[] arr = {"|",this.symbol,".",".","."} ;
        if (this instanceof Property && ((Property)this).getisSold()){
            arr[3] = ((Property)this).getOwner().getInitial() ;
            arr[4] = String.valueOf(((Property)this).getHouseCount());
        }
        return arr[0] + arr[1] + arr[2] + arr[3] + arr[4] ;
    }
    public String getLine2() {
        String[] arr = {"|",".",".",".","."} ;
        for(int i = 0 ; i < this.playersOnCell.size(); i++){
            arr[i+1] = this.playersOnCell.get(i).getInitial();
        }
        return arr[0] + arr[1] + arr[2] + arr[3] + arr[4] ;
    }
    public int getCellNo() {
        return cellNo;
    }
    public ArrayList<Player> getPlayersOnCell() {
        return playersOnCell;
    }
    public String getSymbol() {
        return symbol;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((x-1)*120, (y-1)*120, 120, 120);
        if(g.getColor() != Color.DARK_GRAY){
            g.drawRect((x-1)*120, (y-1)*120, 120, 120); 
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 36));
        g.drawString(symbol, (x-1)*120 + 45, (y-1)*120 + 72);
        for (int i = 0; i < playersOnCell.size() && i < 2; i++){
            g.setColor(playersOnCell.get(i).getColor());
            g.fillOval((x-1)*120 + 60*i, (y-1)*120, 60, 60);
            g.setColor(Color.BLACK);
            g.drawOval((x-1)*120 + 60*i, (y-1)*120, 60, 60);
        }
        for (int i = 2; i < playersOnCell.size(); i++){
            g.setColor(playersOnCell.get(i).getColor());
            g.fillOval((x-1)*120 + 60*(i-2), (y-1)*120 + 60, 60, 60);
            g.setColor(Color.BLACK);
            g.drawOval((x-1)*120 + 60*(i-2), (y-1)*120 + 60, 60, 60);
        }
    }
}
