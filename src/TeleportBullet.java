//Eagle special ability that teleports it to a wall or on top of a player it hits, dodges most other special weapons
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class TeleportBullet extends Bullet {   

   public TeleportBullet(int x, int y, int width, int height, int velX, Player owner, ID id, Handler handler) {
      super(x,y,width,height,velX,owner,id,handler);
      if (velX > 0) {
         bulletImage = Toolkit.getDefaultToolkit().getImage("teleportBulletRight.png");
      } else {
         bulletImage = Toolkit.getDefaultToolkit().getImage("teleportBulletLeft.png");
      }
   } 
   
   //Method to check collision with platforms, players, and other weapons
   @Override
   public void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
         
         if (!tempObject.equals(this)) {            
            if (tempObject.id == ID.Platform || tempObject.id == ID.FireCloud) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  if (velX > 0) {
                     owner.x = tempObject.x - owner.width;
                  } else {
                     owner.x = tempObject.x + tempObject.width;
                  }
                  owner.y = y + 5 - owner.height/2;
                  handler.removeObject(this);                 
               }
            } else if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  if (tempObject != owner) {
                     Player tempPlayer = (Player)tempObject;
                     if (tempPlayer.getShielded()) {
                        tempPlayer.setShielded(false);
                     } else {
                        tempPlayer.setHealth(tempPlayer.getHealth() - 10);
                     }
                     owner.x = tempObject.x;
                     owner.y = tempObject.y - tempObject.height;
                     handler.removeObject(this);
                  }
               }    
            } else if (tempObject.id == ID.Bullet || tempObject.id == ID.ReflectBullet) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  handler.removeObject(tempObject);
               }
            }
         }
      }
   }
   
   @Override
   public void render(Graphics g) {
      g.drawImage(bulletImage,x,y,null);
   }
}
