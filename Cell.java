import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cell {
    private int x ;
    private int y ;
    private int minSteps ;
    private boolean isEmpty ;
    private boolean hasMouse ;
    private boolean hasWall ;
    private boolean hasCheese ;
    private Color color ;
    private BufferedImage mouse;
    private BufferedImage cheese;
    private BufferedImage wall;

    Cell(int x, int y) {
        this.x = x ;
        this.y = y ;
        minSteps = -1 ;
        isEmpty = true ;
        hasMouse = false ;
        hasWall = false ;
        hasCheese = false ;
        color = Color.WHITE ;
        try {
            mouse = ImageIO.read(new File("mouse.png"));
            cheese = ImageIO.read(new File("cheese.png"));
            wall = ImageIO.read(new File("wall.png"));
            } catch (IOException e) {
            System.out.println(e);
        }
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public int getMinSteps() {
        return minSteps;
    }
    public void setMinSteps(int minSteps) {
        this.minSteps = minSteps;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setIsEmpty(boolean isEmpty) {
        if (isEmpty){
            this.minSteps = -1 ;
            this.hasCheese = false ;
            this.hasMouse = false ;
            this.hasWall = false ;
        }
        this.isEmpty = isEmpty;
    }
    public boolean getIsEmpty() {
        return isEmpty ;
    }
    public void setHasCheese(boolean hasCheese) {
        this.isEmpty = !hasCheese ;
        this.hasCheese = hasCheese;
    }
    public void setHasMouse(boolean hasMouse) {
        if (hasMouse){
            minSteps = 0;
        }
        else{
            minSteps = -1 ;
        }
        this.isEmpty = !hasMouse ;
        this.hasMouse = hasMouse ;
    }
    public void setHasWall(boolean hasWall) {
        this.isEmpty = !hasWall ;
        this.hasWall = hasWall;
    }
    public boolean getHasMouse () {
        return hasMouse ;
    }
    public boolean getHasCheese () {
        return hasCheese ;
    }
    public boolean getHasWall () {
        return hasWall ;
    }
    public void draw (Graphics g) {
        g.setColor(color) ;
        g.fillRect(x*120, y*120, 120, 120) ;
        g.setColor(Color.BLACK);
        g.drawRect(x*120, y*120, 120, 120);
        if (hasMouse) {
            g.drawImage(mouse, x*120, y*120, 120, 120, null);
        }
        if (hasCheese) {
            g.drawImage(cheese, x*120, y*120, 120, 120, null);
        }
        if (hasWall) {
            g.drawImage(wall, x*120, y*120, 120, 120, null);
        }
    }
}
