import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Font;
import java.util.Random;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {
   public static final int WIDTH = 1024, HEIGHT = WIDTH/12 * 9;
   private Thread thread;
   private boolean running = false, started = false, over = false;
   private ID winner;
   private Random r;
   private static ArrayList<Player> alive;
   private ID[] availableCharacters = {ID.NoCharacter, ID.Panda, ID.Seal, ID.Dino, ID.Eagle};
   private CharacterPanel[] charPanels; 
   private Color background;
   private Handler handler;
   private HUDHandler hudHandler;
   private int stageChoice;                
   
   public Game() {
      super();
      alive = new ArrayList<Player>();
      charPanels = new CharacterPanel[4];
      handler = new Handler();
      hudHandler = new HUDHandler();
      this.addKeyListener(new KeyInput(handler, hudHandler, this));
      this.addMouseListener(new MouseInput(hudHandler, this));
      new Window(WIDTH, HEIGHT, "Arctic Battle Royale 2", this);
      background = new Color(192, 192, 255);
      r = new Random();
      
      charPanels[0] = new CharacterPanel(25, 70, availableCharacters, charPanels, ID.Player1, ID.CharacterPanel);
      charPanels[1] = new CharacterPanel(275, 70, availableCharacters, charPanels, ID.Player2, ID.CharacterPanel);
      charPanels[2] = new CharacterPanel(525, 70, availableCharacters, charPanels, ID.Player3, ID.CharacterPanel);
      charPanels[3] = new CharacterPanel(775, 70, availableCharacters, charPanels, ID.Player4, ID.CharacterPanel);
      hudHandler.addObject(new StartScreen(0, 0, WIDTH, HEIGHT, ID.StartScreen));
      hudHandler.addObject(new StartButton(WIDTH/2 - 150, 600, 300, 100, ID.StartButton));
      for (int i = 0; i < charPanels.length; i++) {
         hudHandler.addObject(charPanels[i]);
      }
      hudHandler.addObject(new ControlKeys(25, 350, ID.Player1, ID.ControlKeys));
      hudHandler.addObject(new ControlKeys(275, 350, ID.Player2, ID.ControlKeys));
      hudHandler.addObject(new ControlKeys(525, 350, ID.Player3, ID.ControlKeys));
      hudHandler.addObject(new ControlKeys(775, 350, ID.Player4, ID.ControlKeys));
   }
   
   public synchronized void start() {
      thread = new Thread(this);
      thread.start();
      running = true;
   }
   
   public synchronized void stop() {
      try
      {
         thread.join();
         running = false;
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
   
   public void run() {
      requestFocus();                                    
      long lastTime = System.nanoTime();
      double amountOfTicks = 60.0;
      double ns = 1000000000/amountOfTicks;
      double delta = 0;
      long timer = System.currentTimeMillis();
      int frames = 0;
      while(running)
      {
         long now = System.nanoTime();
         delta += (now - lastTime)/ns;
         lastTime = now;
         while(delta >= 1)
         {
            tick();
            delta--;
         }
         if(running)
            render();
         frames++;
         if(System.currentTimeMillis() - timer > 1000)
         {
            timer += 1000;
            //System.out.println("FPS: " + frames);
            frames = 0;
         }
      }
      stop();
   }
   
   public void createStage() {
      hudHandler.getObjectList().clear();
      started = true;
      stageChoice = r.nextInt(1);
      switch (stageChoice) {
         case 0:
            for (int i = 0; i < 3; i++) {
               handler.addObject(new Platform(i * 32 + 32, 96, 32, 16, true, ID.Platform, handler));
               handler.addObject(new Platform(WIDTH - i * 32 - 64, 96, 32, 16, true, ID.Platform, handler));
            }
            for (int i = 0; i < 8; i++) {
               handler.addObject(new Platform(i * 32 + 384, 96, 32, 16, true, ID.Platform, handler));
            }
            for (int i = 0; i < 4; i++) {
               handler.addObject(new Platform(i * 32 + 192, 192, 32, 16, true, ID.Platform, handler));
               handler.addObject(new Platform(i * 32 + 704, 192, 32, 16, true, ID.Platform, handler));
            }
            for (int i = 0; i < 5; i++) {
               handler.addObject(new Platform(i * 32 + 32, 288, 32, 16, true, ID.Platform, handler));
               handler.addObject(new Platform(WIDTH - i * 32 - 64, 288, 32, 16, true, ID.Platform, handler));
            }
            for (int i = 0; i < 10; i++) {
               handler.addObject(new Platform(i * 32 + 352, 320, 32, 16, true, ID.Platform, handler));
            }
            for (int i = 0; i < 12; i++) {
               handler.addObject(new Platform(i * 32 + 320, 416, 32, 16, true, ID.Platform, handler));
            }
            for (int i = 0; i < 6; i++) {
               handler.addObject(new Platform(i * 32 + 160, 512, 32, 16, true, ID.Platform, handler));
               handler.addObject(new Platform(WIDTH - i * 32 - 192, 512, 32, 16, true, ID.Platform, handler));
               handler.addObject(new Platform(i * 32 + 96, 608, 32, 16, true, ID.Platform, handler));
               handler.addObject(new Platform(WIDTH - i * 32 - 128, 608, 32, 16, true, ID.Platform, handler));
            }
            for (int i = 0; i < 4; i++) {
               handler.addObject(new Platform(i * 32 + 448, 544, 32, 16, true, ID.Platform, handler));
            }
            if (charPanels[0].getCharacter() != ID.NoCharacter)
               alive.add(new Player(32, 64, 32, 32, ID.Player1, charPanels[0].getCharacter(), handler, hudHandler));
            if (charPanels[1].getCharacter() != ID.NoCharacter) 
               alive.add(new Player(WIDTH - 64, 64, 32, 32, ID.Player2, charPanels[1].getCharacter(), handler, hudHandler));
            if (charPanels[2].getCharacter() != ID.NoCharacter)
               alive.add(new Player(32, 600, 32, 32, ID.Player3, charPanels[2].getCharacter(), handler, hudHandler));
            if (charPanels[3].getCharacter() != ID.NoCharacter)
               alive.add(new Player(WIDTH - 64, 600, 32, 32, ID.Player4, charPanels[3].getCharacter(), handler, hudHandler));
            break;
         case 1:
            break;
         case 2:
            break;
         default:
            break;
      }
      
      for (int i = 0; i < alive.size(); i++) {
         handler.addObject(alive.get(i));
      }  
    
      for (int i = 0; i < HEIGHT; i+=32) {
         handler.addObject(new Platform(0, i, 32, 32, false, ID.Platform, handler));
         handler.addObject(new Platform(WIDTH - 32, i, 32, 32, false, ID.Platform, handler));
      } 
      for (int i = 0; i < WIDTH; i+=32) {
         handler.addObject(new Platform(i, 0, 32, 32, false, ID.Platform, handler));  
         handler.addObject(new Platform(i, HEIGHT - 80, 32, 70, false, ID.Platform, handler));
      }
      handler.addObject(new Powerup(250, 50, ID.DrillPowerup, handler));
      handler.addObject(new Powerup(350, 50, ID.DrillPowerup, handler));
      handler.addObject(new Powerup(450, 50, ID.DrillPowerup, handler));
   }
   
   private void tick() {
      handler.tick();
      hudHandler.tick();
      if (alive.size() == 1 && !over) {
         over = true;
         winner = alive.get(0).getCharacter();
      }
   }
   
   private void render() {
      BufferStrategy bs = this.getBufferStrategy();
      if (bs == null)
      {
         this.createBufferStrategy(3);
         return;
      }
      Graphics g = bs.getDrawGraphics();
      
      g.setColor(background);
      g.fillRect(0,0,WIDTH,HEIGHT);
      
      handler.render(g);
      hudHandler.render(g);
      
      if (over) {
         g.setColor(Color.white);
         g.setFont(new Font("Arial", Font.BOLD, 100));
         if (winner == ID.Panda) {
            g.drawString("Panda Wins!", getWidth()/2 - 300, getHeight()/2);  
         } else if (winner == ID.Seal) {
            g.drawString("Seal Wins!", getWidth()/2 - 250, getHeight()/2);  
         } else if (winner == ID.Dino) {
            g.drawString("Dino Wins!", getWidth()/2 - 250, getHeight()/2);  
         } else if (winner == ID.Eagle) {
            g.drawString("Eagle Wins!", getWidth()/2 - 300, getHeight()/2);  
         }  
      }
      
      g.dispose();
      bs.show();
   }
   
   //Method to prevent values from exceeding a min or max   
   public static int clamp(int var, int min, int max) {
      if(var >= max)
         return max;
      if (var <= min)
         return min;
      else
         return var;
   }
   
   public static ArrayList<Player> getAlive() {
      return alive;
   }
   
   public CharacterPanel[] getCharPanels() {
      return charPanels;
   }
   
   public boolean getStarted() {
      return started;
   }
   
   public void setStarted(boolean started) {
      this.started = started;
   }
   
   public static void main(String[] args) {
      new Game();
   }
}