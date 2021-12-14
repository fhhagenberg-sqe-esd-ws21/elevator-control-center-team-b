package at.fhhagenberg.sqe;
 
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class ElevatorProperties {
    //private final SimpleIntegerProperty position = new SimpleIntegerProperty(0);


    private Circle position;
    //private Integer position;
    private Integer floor;
    private String up;
    private String down;
    private String stopPlanned;

 
 public ElevatorProperties() {
         this(0, 0, "", "", "");
     }
  
     public ElevatorProperties(Integer position, Integer floor, String up, String down, String stopPlanned) {         
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
        if(fPosition == floor)
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
     
     public String getUp() {
         return up;
     }
     
     public void setUp(String fup) {
         up = fup;
     }
     
     public String getDown() {
         return down;
         
     }
     
     public void setDown(String fdown) {
         down = fdown;
     }

     public String getStopPlanned() {
        return stopPlanned;
    }
    
    public void setStopPlanned(String fStopPlanned) {
        stopPlanned = fStopPlanned;
    }
 }
