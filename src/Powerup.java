//Powerup that randomly spawns and gives player's special weapons
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.AlphaComposite;
import java.awt.Toolkit;
import java.awt.Image;

public class Powerup extends GameObject {
   private Handler handler;
   private Random r;
   private int cooldown;
   private Color shieldOutline;
   private Image powerupImage;
      
   public Powerup(int x, int y, ID id, Handler handler) {
      super(x,y,id);
      this.handler = handler;
      r = new Random();
      cooldown = 0;
      shieldOutline = new Color(0, 128, 255);
      solid = false;
   } 

   @Override
   public Rectangle getBounds() {
      return new Rectangle(x,y,20,20);
   }
   
   @Override
   public void tick() {
      collision();
      if (cooldown > 0) {
         cooldown--;
      } else {
         spawn();
      }
   }
   
   //Method to check collision with platforms and players
   private void collision() {
      for(int i = 0; i < handler.getObjectList().size(); i++) {
         GameObject tempObject = handler.getObjectList().get(i); 
                     
         if (tempObject.id == ID.Platform) {
            if (getBounds().intersects(tempObject.getBounds())) {
               spawn();
            }
         } else if (tempObject.id == ID.Player1 || tempObject.id == ID.Player2 || tempObject.id == ID.Player3 || tempObject.id == ID.Player4) {
            if (getBounds().intersects(tempObject.getBounds())) {
               Player tempPlayer = (Player)tempObject;
               if (id == ID.ShieldPowerup) {
                  tempPlayer.setShielded(true);
               } else {
                  tempPlayer.setPowerup(id);
                  if (id == ID.DrillPowerup) {
                     tempPlayer.setSpecialAmmo(1);
                  } else if (id == ID.BombPowerup) {
                     tempPlayer.setSpecialAmmo(2);
                  } else if (id == ID.MinePowerup) {
                     tempPlayer.setSpecialAmmo(2);
                  } else if (id == ID.ReflectBulletPowerup) {
                     tempPlayer.setSpecialAmmo(5);
                  }
               }
               cooldown = 100;
               x = Game.WIDTH + 100;
               y = Game.HEIGHT + 100;
            }           
         }
      }
   }
   
   //Method to randomize spawn location and type
   private void spawn() {
      x = r.nextInt(Game.WIDTH - 32);
      y = r.nextInt(Game.HEIGHT - 55);
      cooldown = 2000;
      int choice = r.nextInt(5);
      switch (choice) {
         case 0:
            id = ID.DrillPowerup;
            powerupImage = Toolkit.getDefaultToolkit().getImage("drillPowerup.png");
            break;
         case 1:
            id = ID.BombPowerup;
            powerupImage = Toolkit.getDefaultToolkit().getImage("bombPowerup.png");
            break;
         case 2:
            id = ID.MinePowerup;
            powerupImage = Toolkit.getDefaultToolkit().getImage("minePowerup.png");
            break;
         case 3:
            id = ID.ReflectBulletPowerup;
            powerupImage = Toolkit.getDefaultToolkit().getImage("reflectPowerup.png");
            break;
         case 4:
            id = ID.ShieldPowerup;
            break;
         default:
            break;
      }
   }
   
   @Override
   public void render(Graphics g) {
      if (id == ID.ShieldPowerup) {
         g.setColor(Color.cyan);
         Graphics2D g2d = (Graphics2D)g;
         g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
         g2d.fillOval(x,y,20,20);
         g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
         g2d.setColor(shieldOutline);
         g2d.drawOval(x,y,20,20);
      } else {
         g.drawImage(powerupImage,x,y,null);
      }
   }
   
   //Getters and setters
   public int getCooldown() {
      return cooldown;
   }
   
   public void setCooldown() {
      this.cooldown = cooldown;
   }
   
}