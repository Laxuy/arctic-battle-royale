//Background for the start screen
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Font;

public class StartScreen extends HUDObject {
   private Color color;
      
   public StartScreen(int x, int y, int width, int height, ID id) {
      super(x,y,width,height,id);
      color = new Color(192, 192, 255);
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
      g.drawString("Arctic Battle Royale 2", x + width/2 - 300, y + 50);
   }
}