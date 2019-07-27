//Handles all ticking and rendering for HUD objects so that they render on top of game objects
import java.awt.Graphics;
import java.util.LinkedList;

public class HUDHandler {
   private LinkedList<HUDObject> objectList = new LinkedList<HUDObject>();
   
   public void tick() {
      for(int i = 0; i < objectList.size(); i++) {
         HUDObject tempObject = objectList.get(i);
         tempObject.tick();
      }
   }
   
   public void render(Graphics g) {
      for(int i = 0; i < objectList.size(); i++) {
         HUDObject tempObject = objectList.get(i);
         tempObject.render(g);
      }
   }
   
   public void addObject(HUDObject object) {
      objectList.add(object);
   }
   
   public void removeObject(HUDObject object) {
      objectList.remove(object);
   }
   
   //Getters and Setters
   public LinkedList<HUDObject> getObjectList() {
      return objectList;
   }
   
   public void setObjectList(LinkedList<HUDObject> objectList) {
      this.objectList = objectList;
   }
   
}