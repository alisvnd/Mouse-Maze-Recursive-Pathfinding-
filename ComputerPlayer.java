import java.util.Random ;
import java.awt.Color ;
public class ComputerPlayer extends Player {
    
    ComputerPlayer(String name, Cell position, Color color){
        super(name, position, color) ;
    }
    public boolean buyProperty() {
        Random random = new Random() ;
        int i = random.nextInt(100) ;
        if (super.getBudget() > 8){
            return true ;
        }
        else if (super.getBudget() > 4){
            return i < 70 ;
        }
        else{
            return i < 38 ;
        }
    }

    public void buildHouse(){
        Random random = new Random() ;
        if (random.nextBoolean()){
            for (int i = 0; i < this.getProperties().size(); i++){
                if(this.getProperties().get(i).getHouseCount() < 4){
                    this.getProperties().get(i).buildHouse();
                    return;
                }
            }
        }
    }
}
