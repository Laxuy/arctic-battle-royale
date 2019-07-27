//Parent class for all HUD objects
import java.awt.Graphics;
import javax.swing.JComponent;

public abstract class HUDObject {
   protected int x, y, width, height;
   protected ID id;
   
   public HUDObject(int x, int y, ID id) {
      this.x = x;
      this.y = y;
      this.id = id;
   }
   
   public HUDObject(int x, int y, int width, int height, ID id) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.id = id;
   }
   
   public abstract void tick();
   
   public abstract void render(Graphics g);
   
   public int getX() {
      return x;
   }   
   
   public void setX(int x) {
      this.x = x;
   }
   
   public int getY() {
      return y;
   }
   
   public void setY(int y) {
      this.y = y;
   }
   
   public int getWidth() {
      return width;
   }
   
   public void setWidth(int width) {
      this.width = width;
   }
   
   public int getHeight() {
      return height;      
   }
   
   public void setHeight(int height) {
      this.height = height;
   }
   
   public ID getId() {
      return id;
   }
   
   public void setId(ID id) {
      this.id = id;
   }
}