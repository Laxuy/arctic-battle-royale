//HUD object displaying the controls for each player
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Image;

public class ControlKeys extends HUDObject{
   private Image image;
      
   public ControlKeys(int x, int y, ID player, ID id) {
      super(x,y,id);
      if (player == ID.Player1) {
         image = Toolkit.getDefaultToolkit().getImage("p1Panel.png");
      } else if (player == ID.Player2) {
         image = Toolkit.getDefaultToolkit().getImage("p2Panel.png");
      } else if (player == ID.Player3) {
         image = Toolkit.getDefaultToolkit().getImage("p3Panel.png");
      } else if (player == ID.Player4) {
         image = Toolkit.getDefaultToolkit().getImage("p4Panel.png");
      }
   } 
   
   @Override
   public void tick() {      
   }
      
   @Override
   public void render(Graphics g) {
      g.drawImage(image,x,y,null);
   }  
}