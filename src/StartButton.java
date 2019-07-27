//Start screen HUD Object that creates the stage when clicked
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Font;

public class StartButton extends HUDObject{
   private Color color;
      
   public StartButton(int x, int y, int width, int height, ID id) {
      super(x,y,width,height,id);
      color = new Color(136, 136, 252);
   } 
   
   @Override
   public void tick() {      
   }
      
   @Override
   public void render(Graphics g) {
      g.setColor(color);
      g.fillRect(x,y,width,height);
      g.setFont(new Font("Arial", Font.BOLD, 50));
      g.setColor(Color.white);
      g.drawRect(x-1,y-1,width+2,height+2);
      g.drawRect(x-2,y-2,width+4,height+4);
      g.drawRect(x-3,y-3,width+6,height+6);
      g.drawString("Start", x + 90, y + 70);
   }  
}