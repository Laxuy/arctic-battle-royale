//Parent class for all game objects
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
   protected int x, y, width, height;
   protected ID id;
   protected int velX, velY;
   protected boolean solid;
   
   public GameObject(int x, int y, ID id) {
      this.x = x;
      this.y = y;
      this.id = id;
   }
   
   public GameObject(int x, int y, int width, int height, ID id) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.id = id;
   }
   
   public abstract void tick();
   
   public abstract void render(Graphics g);
   
   public abstract Rectangle getBounds();
   
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
   
   public int getVelX() {
      return velX;
   }
   
   public void setVelX(int velX) {
      this.velX = velX;
   }
   
   public int getVelY() {
      return velY;
   }
   
   public void setVelY(int velY) {
      this.velY = velY;
   }
   
   public boolean getSolid() {
      return solid;
   }
   
   public void setSolid(boolean solid) {
      this.solid = solid;
   }
}