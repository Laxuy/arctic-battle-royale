//Handles all ticking and rendering for game objects
import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
   private LinkedList<GameObject> objectList = new LinkedList<GameObject>();
   
   public void tick() {
      for(int i = 0; i < objectList.size(); i++) {
         GameObject tempObject = objectList.get(i);
         tempObject.tick();
      }
   }
   
   public void render(Graphics g) {
      for(int i = 0; i < objectList.size(); i++) {
         GameObject tempObject = objectList.get(i);
         tempObject.render(g);
      }

   }
   
   public void addObject(GameObject object) {
      objectList.add(object);
   }
   
   public void removeObject(GameObject object) {
      objectList.remove(object);
   }
   
   //Getters and Setters
   public LinkedList<GameObject> getObjectList() {
      return objectList;
   }
   
   public void setObjectList(LinkedList<GameObject> objectList) {
      this.objectList = objectList;
   }
   
}