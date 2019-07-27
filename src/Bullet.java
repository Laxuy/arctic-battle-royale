//Basic bullet weapon that every character can shoot
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;

public class Bullet extends GameObject {
   protected Handler handler;
   protected Player owner;
   protected Image bulletImage;
      
   public Bullet(int x, int y, int width, int height, int velX, Player owner, ID id, Handler handler) {
      super(x,y,width,height,id);
      this.velX = velX;
      this.owner = owner;
      this.handler = handler;
      solid = false;
      if (owner.getCharacter() == ID.Panda) {
         bulletImage = Toolkit.getDefaultToolkit().getImage("greenBullet.png");
      } else if (owner.getCharacter() == ID.Seal) {
         bulletImage = Toolkit.getDefaultToolkit().getImage("blueBullet.png");
      } else if (owner.getCharacter() == ID.Dino) {
         bulletImage = Toolkit.getDefaultToolkit().getImage("yellowBullet.png");
      } else if (owner.getCharacter() == ID.Eagle) {
         bulletImage = Toolkit.getDefaultToolkit().getImage("redBullet.png");
      }
   } 

   @Override
   public Rectangle getBounds() {
      return new Rectangle(x+8,y,16,height);
   }
   
   @Override
   public void tick() {      
      x += velX;
      if (x + width < 0 || x > Game.WIDTH) {
         handler.removeObject(this);
      }
      
      if (owner.getCharacter() == ID.Seal) {
         y += velY;
         velY += 1;
      }
      
      collision();
   }
   
   //Method to check collision with platforms, players, and other weapons
   public void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
         
         if (!tempObject.equals(this)) {            
            if (tempObject.id == ID.Platform) {
               if (owner.getCharacter() == ID.Seal) {
                  if (velY > 0 && tempObject.y > y && velY > tempObject.y - (y+height)
                     && x + width > tempObject.x && x < tempObject.x + tempObject.width) {
                     velY = -5;
                     y += tempObject.y - (y+height);
                  }
               }
               if (getBounds().intersects(tempObject.getBounds())) {
                  handler.removeObject(this);                 
               }
            } else if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
               if (getBounds().intersects(tempObject.getBounds()) && tempObject != owner) {
                  Player tempPlayer = (Player)tempObject;
                  if (owner.getCharacter() == ID.Dino) {
                     handler.addObject(new Explosion(x - 32 + width/2, y - 32 + height/2, 64, 64, ID.Explosion, handler));
                  } else {
                     if (tempPlayer.getShielded()) {
                        tempPlayer.setShielded(false);
                     } else if (owner.getCharacter() == ID.Eagle) {
                        tempPlayer.setHealth(tempPlayer.getHealth() - 5);
                     } else {
                        tempPlayer.setHealth(tempPlayer.getHealth() - 10);
                     }
                  }
                  handler.removeObject(this);
               }    
            } else if (tempObject.id == ID.Bullet) {
               if (getBounds().intersects(tempObject.getBounds())) {
                  handler.removeObject(this);
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
   
   //Getters and Setters
   public Player getOwner() {
      return owner;
   }
   
   public void setOwner(Player owner) {
      this.owner = owner;
   } 
   
}