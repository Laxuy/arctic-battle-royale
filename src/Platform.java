import java.awt.Graphics;
import java.awt.Graphics2D;
//Basic platform that is solid for every player and many weapons
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.GradientPaint;

public class Platform extends GameObject {
   private Handler handler;
   private boolean breakable;
   private int respawnTimer, originalX, originalY;
   private Color color;
   private GradientPaint blueToDark;
   
   public Platform(int x, int y, int width, int height, boolean breakable, ID id, Handler handler) {
      super(x,y,width,height,id);
      this.originalX = x;
      this.originalY = y;
      this.breakable = breakable;
      this.handler = handler;
      solid = true;
      respawnTimer = 0;
      blueToDark = new GradientPaint(x, y, new Color(87,164,218), x + width, y + height,
            new Color(29, 94, 140));
   } 

   @Override
   public Rectangle getBounds() {
      return new Rectangle(x,y,width,height);
   }
   
   @Override
   public void tick() {
      if (respawnTimer > 0) {
         respawnTimer--;
      } else if (x != originalX) {
         reappear();
      }
   }
   
   @Override
   public void render(Graphics g) {
      Graphics2D g2d = (Graphics2D)g;
      if (id == ID.Platform) {
         g2d.setPaint(blueToDark);
      }
      g.fillRect(x,y,width,height);
   }
   
   public void disappear() {
      x = Game.WIDTH;
      y = Game.HEIGHT;
   }
   
   public void reappear() {
      x = originalX;
      y = originalY;
   }
   
   //Getters and Setters
   public boolean getBreakable() {
      return breakable;
   }
   
   public void setBreakable(boolean breakable) {
      this.breakable = breakable;
   }
   
   public int getRespawnTimer() {
      return respawnTimer;
   }
   
   public void setRespawnTimer(int respawnTimer) {
      this.respawnTimer = respawnTimer;
   }
   
   public int getOriginalX() {
      return originalX;
   }
   
   public void setOriginalX(int originalX) {
      this.originalX = originalX;
   }
   
   public int getOriginalY() {
      return originalY;
   }
   
   public void setOriginalY(int originalY) {
      this.originalY = originalY;
   }
   
}