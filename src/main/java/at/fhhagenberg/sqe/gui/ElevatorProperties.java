package at.fhhagenberg.sqe.gui;
 
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class ElevatorProperties {

    private Circle position;
    private Integer floor;
    private Circle up;
    private Circle down;
    private Circle stopPlanned;

 
 public ElevatorProperties() {
         this(0, 0, false, false, false);
     }
  
     public ElevatorProperties(Integer position, Integer floor, Boolean up, Boolean down, Boolean stopPlanned) {         
    	 setFloor(floor);
         setPosition(position);
         setUp(up);
         setDown(down);
         setStopPlanned(stopPlanned);
     }
 
     public Circle getPosition() {
         return position;
     }
  
     public void setPosition(Integer fPosition) {
        position = new Circle(5);
        if(fPosition.equals(floor))
        {
            position.setFill(Color.GREEN);
        }
        else 
        {
            position.setFill(Color.RED);
        }
        //position = fPosition;
     }
         
     public Integer getFloor() {
         return floor;
     }
     
     public void setFloor(Integer fFloor) {
         floor = fFloor;
     }
     
     public Circle getUp() {
         return up;
     }
     
     public void setUp(Boolean fup) {       
    	 up = new Circle(5);
    	 up.setFill(fup?Color.GREEN:Color.GRAY);
     }
     
     public Circle getDown() {
         return down;
     }
     
     public void setDown(Boolean fdown) {
    	 down = new Circle(5);
    	 down.setFill(fdown?Color.GREEN:Color.GRAY);
     }

     public Circle getStopPlanned() {
        return stopPlanned;
    }
    
    public void setStopPlanned(Boolean fstopPlanned) {
   	 stopPlanned = new Circle(5);
   	stopPlanned.setFill(fstopPlanned?Color.GREEN:Color.GRAY);
    }
 }
