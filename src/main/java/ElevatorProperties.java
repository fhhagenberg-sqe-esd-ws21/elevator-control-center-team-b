package fxmltableview;
 
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ElevatorProperties {
    private final SimpleIntegerProperty position = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty floor = new SimpleIntegerProperty(0);
    private final SimpleStringProperty upDown = new SimpleStringProperty("");
    private final SimpleStringProperty stopPlanned = new SimpleStringProperty("");
 
 public ElevatorProperties() {
         this(0, 0, "", "");
     }
  
     public ElevatorProperties(Integer position, Integer floor, String upDown, String stopPlanned) {
         setPosition(position);
         setFloor(floor);
         setUpDown(upDown);
         setStopPlanned(stopPlanned);
     }
 
     public Integer getPosition() {
         return position.get();
     }
  
     public void setPosition(Integer fPosition) {
         position.set(fPosition);
     }
         
     public Integer getFloor() {
         return floor.get();
     }
     
     public void setFloor(Integer fFloor) {
         floor.set(fFloor);
     }
     
     public String getUpDown() {
         return upDown.get();
     }
     
     public void setUpDown(String fupDown) {
         upDown.set(fupDown);
     }

     public String getStopPlanned() {
        return stopPlanned.get();
    }
    
    public void setStopPlanned(String fupDown) {
        stopPlanned.set(fupDown);
    }
 }
