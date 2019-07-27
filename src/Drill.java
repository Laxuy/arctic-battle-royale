//Drill powerup that destroys blocks and explodes on contact
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;

public class Drill extends Bullet {
   private Image drillRight = Toolkit.getDefaultToolkit().getImage("drillRight.png");
   private Image drillLeft = Toolkit.getDefaultToolkit().getImage("drillLeft.png");

   public Drill(int x, int y, int width, int height, int velX, Player owner, ID id, Handler handler) {
      super(x,y,width,height,velX,owner,id,handler);
   } 

   @Override
   public Rectangle getBounds() {
      return new Rectangle(x,y,width,height);
   }
   
   @Override
   public void tick() {      
      x += velX;
      if (x + width < 0 || x > Game.WIDTH) {
         handler.removeObject(this);
      }
      
      collision();
   }
   
   //Method to check collision with platforms, players, and other weapons
   @Override
   public void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
           
         if (!tempObject.equals(this)) {            
            if (tempObject.id == ID.Platform) {
               Platform tempPlatform = (Platform)tempObject;
               if (getBounds().intersects(tempObject.getBounds())) {
                  if (tempPlatform.getBreakable()) {
                     tempPlatform.setRespawnTimer(500);
                     tempPlatform.disappear();
                  } else {
                     handler.addObject(new Explosion(x - 32 + width/2, y - 32 + height/2, 64, 64, ID.Explosion, handler));
                     handler.removeObject(this);
                  }
               }
            } else if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
               if (getBounds().intersects(tempObject.getBounds()) && tempObject != owner) {
                  handler.addObject(new Explosion(x - 32 + width/2, y - 32 + height/2, 64, 64, ID.Explosion, handler));
                  handler.removeObject(this);
               }    
            }  else if (tempObject.id == ID.Bullet || tempObject.id == ID.ReflectBullet) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  handler.removeObject(tempObject);
               }
            } else if (tempObject.id == ID.Drill) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  handler.addObject(new Explosion(x - 32 + width/2, y - 32 + height/2, 64, 64, ID.Explosion, handler));
                  handler.addObject(new Explosion(tempObject.x - 32 + tempObject.width/2, tempObject.y - 32 + tempObject.height/2, 64, 64, ID.Explosion, handler));
                  handler.removeObject(tempObject);
                  handler.removeObject(this);
               }
            }
         }
      }
   }
   
   @Override
   public void render(Graphics g) {
      if (id == ID.Drill) {
         if (velX > 0) {
            g.drawImage(drillRight,x,y,null);
         } else {
            g.drawImage(drillLeft,x,y,null);
         }
      }
   }
}