package correll_deliverer;

import static correll_deliverer.Level2.blueb;
import static correll_deliverer.Level2.damage;
import static correll_deliverer.Level2.orbb;
import static correll_deliverer.Level2.orbs;
import static correll_deliverer.Level2.player;
import static correll_deliverer.Level2.redb;
import static correll_deliverer.Level2.yellowb;
import org.newdawn.slick.*;
import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

public class Level1 extends BasicGameState {
    
    public GoodPortal gportal;
    public Special special;
    public Trap trap;
    public Trap trap2;
    public Trap trap3;
    public Crystal crystal;
    public static Marble damage;
    
    public static Music music;
    public static boolean reveal = false;
    public static boolean pass = false;
    public static boolean draw = false;
    
    public ArrayList<GoodPortal> gp = new ArrayList();
    public ArrayList<Item> stuff = new ArrayList();
    public ArrayList<Special> item = new ArrayList();
    public ArrayList<Trap> tr = new ArrayList();
    public ArrayList<Crystal> cry = new ArrayList();
    
    private static TiledMap grassMap2;
    private static AppGameContainer app;
    private static Camera camera;
    private static final int SIZE = 32;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 750;
    
    public static Player player;

    public Level1(int xSize, int ySize) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {

        music = new Music("res/music.ogg");
        music.loop(1.0f, 5.0f);
        gc.setTargetFrameRate(60);
        gc.setShowFPS(false);
        grassMap2 = new TiledMap("res/level1.tmx");
        camera = new Camera(gc, grassMap2);
        Blocked2.blocked2 = new boolean[grassMap2.getWidth()][grassMap2.getHeight()];
        
        for (int xAxis = 0; xAxis < grassMap2.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap2.getHeight(); yAxis++) {
                int tileID = grassMap2.getTileId(xAxis, yAxis, 0);
                String value = grassMap2.getTileProperty(tileID,
                        "blocked2", "false");
                if ("true".equals(value)) {
                    Blocked2.blocked2[xAxis][yAxis] = true;
                    
                }
            }
        }
        
        for (int xAxis = 0; xAxis < grassMap2.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap2.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked2.blocked2[xBlock][yBlock]) {
                    if (yBlock % 7 == 0 && xBlock % 15 == 0) {

                    }
                }
            }
        }

        for (int xAxis = 0; xAxis < grassMap2.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap2.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked2.blocked2[xBlock][yBlock]) {
                    if (xBlock % 9 == 0 && yBlock % 25 == 0) {

                    }
                }
            }
        }

        player = new Player();
        player.gameState = 1;
        
        gportal = new GoodPortal(55, 670);
        special = new Special(180, 705);
        trap = new Trap(200, 110);
        trap2 = new Trap(725, 450);
        trap3 = new Trap(210, 590);
        crystal = new Crystal(700, 40);
        damage = new Marble((int) player.x, (int) player.y);
        
        gp.add(gportal);
        item.add(special);
        tr.add(trap);
        tr.add(trap2);
        tr.add(trap3);
        cry.add(crystal);

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {

        camera.centerOn((int) player.x, (int) player.y);
        camera.drawMap();
        camera.translateGraphics();
        player.sprite.draw((int) player.x, (int) player.y);
                //coordinates
		//g.drawString("x: " + (int)player.x + "," + " y: " +(int)player.y , player.x, player.y - 10);
                g.drawString("Orb Count: " + Level2.orbs, camera.cameraX + 275, camera.cameraY + 3);
           
            if (damage.isVisible) {
            
            damage.currentImage.draw(damage.getX(), damage.getY());
                //g.draw(damage.hitbox);
            
        }    
                
            for (GoodPortal p : gp) {
                if (p.isvisible) {
                
                    p.currentImage.draw(p.x, p.y);
                    //g.draw(s.hitbox);

            }
        }
                
            for (Special s : item) {
                if (s.isvisible) {
                
                    s.currentImage.draw(s.x, s.y);
                    //g.draw(s.hitbox);

            }
        }
            for (Trap t : tr) {
                
                    t.currentImage.draw(t.x, t.y);
                    //g.draw(t.hitbox);
            
        }
            
            for (Crystal c : cry) {
                if (c.isvisible) {
                    
                    c.currentImage.draw(c.x, c.y);
                    //g.draw(c.hitbox);
                    
            }
        }

            if (draw) {
                g.drawString("Press R to turn \non the Portal!", camera.cameraX + 1, camera.cameraY + 3);
                
        }   
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {

        Input input = gc.getInput();
        float fdelta = delta * player.speed;
        player.setpdelta(fdelta);
        double rightlimit = (grassMap2.getWidth() * SIZE) - (SIZE * 0.75);
        float projectedright = player.x + fdelta + SIZE;
        boolean cangoright = projectedright < rightlimit;
        
        if (input.isKeyDown(Input.KEY_UP)) {
            player.setDirection(0);
            player.sprite = player.up;
            float fdsc = (float) (fdelta - (SIZE * .15));

            if (!(isBlocked2(player.x, player.y + 5 -fdelta) || isBlocked2((float) (player.x + SIZE + 1.5), player.y + fdelta))) {
                player.sprite.update(delta);
                player.y -= fdelta;
                
            }
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            player.setDirection(2);
            player.sprite = player.down;

            if (!isBlocked2(player.x, player.y + SIZE + fdelta)
                    || !isBlocked2(player.x + SIZE - 1, player.y + SIZE + fdelta)) {
                player.sprite.update(delta);
                player.y += fdelta;

            }
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            player.setDirection(3);
            player.sprite = player.left;

            if ((player.getPlayersX() > 5) && (!(isBlocked2(player.x - fdelta, player.y) || isBlocked2(player.x
                    - fdelta, player.y + SIZE - 1)) || isBlocked2(player.x - SIZE / 2 - fdelta, player.y
                            + SIZE - 1))) {
                player.sprite.update(delta);
                player.x -= fdelta;

            }
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            player.setDirection(1);
            player.sprite = player.right;

            if (cangoright
                    && (!(isBlocked2(player.x + SIZE + fdelta,
                            player.y) || isBlocked2(player.x + SIZE + fdelta, player.y
                            + SIZE - 1)))) {
                player.x += fdelta;

            }
        
        } else if (input.isKeyDown(Input.KEY_I)) {     
            
                sbg.enterState(5, new FadeOutTransition(Color.white), new FadeInTransition(Color.white));
            
        } else if (input.isKeyDown(Input.KEY_R) && (reveal)) {     
            
            pass = true;
            
        } else if (input.isKeyDown(Input.KEY_Q) && (orbb)) {     
            
            damage.setX((int) player.x);
            damage.setY((int) player.y);
            damage.hitbox.setX(damage.getX());
            damage.hitbox.setY(damage.getY());
            damage.setIsVisible(!damage.isIsVisible());
            
        }
        
        if (blueb && redb && yellowb) {
            
            orbb = true;
            
        } else {
            
            orbb = false;
            
        }

        Level1.player.rect.setLocation(Level1.player.getPlayershitboxX(),
                Level1.player.getPlayershitboxY() + 50);
        
        for (Special s : item) {
            if (Level1.player.rect.intersects(s.hitbox)) {
                if (s.isvisible) {
                    
                    draw = true;
                    reveal = true;
                    s.isvisible = false;
                    
                }
            }
        }
        
        for (GoodPortal p : gp) {
            if (Level1.player.rect.intersects(p.hitbox)) {
                if (p.isvisible) {
                    if(pass) {
                    
                    Level2.drawo = true;
                    Level2.player.x = 45;
                    Level2.player.y = 200;
                    player.gameState = 2;
                    sbg.enterState(2, new FadeOutTransition(Color.white), new FadeInTransition(Color.white));
                    
                }
            }
        }
    }
     
        for (Crystal c : cry) {
            if (damage.hitbox.intersects(c.hitbox)) {
                if (c.isvisible) {
                    
                    c.isvisible = false;
                    damage.setX(100);
                    damage.setY(100);
                    sbg.enterState(3, new FadeOutTransition(Color.white), new FadeInTransition(Color.white));
                    
                }                      
            }
        }
        
        for (Trap t : tr) {
            if (player.rect.intersects(t.hitbox)) {
                if (Level2.orbs > 0) {
                    if (!Orb.isvisible) {
                        
                        blueb = false;
                        Orb.isvisible = true;
                        
                    } 
                    
                    else if (!OrbRed.isvisible) {
                        
                        redb = false;
                        OrbRed.isvisible = true;
                        
                    }
                    
                    else if (!OrbYellow.isvisible) {
                        
                        yellowb = false;
                        OrbYellow.isvisible = true;
                        
                    }
                        Level2.orbs--;
                        player.x = 50;
                        player.y = 560;
                    
                } else {
                    
                    sbg.enterState(4, new FadeOutTransition(Color.white), new FadeInTransition(Color.white));
                
                }
              
        }
        }
        
        if (damage.isIsVisible()) {
            if (player.getDirection() == 0) {

                    damage.setX((int) player.x);
                    damage.setY(damage.getY() - 5);
                    
                } else if (player.getDirection() == 2) {
                    
                    damage.setX((int) player.x);
                    damage.setY(damage.getY() + 5);
                    
                } else if (player.getDirection() == 3) {
                    
                    damage.setX(damage.getX() - 5);
                    damage.setY(damage.getY());
                    
                } else if (player.getDirection() == 1) {
                    
                    damage.setX(damage.getX() + 5);
                    damage.setY(damage.getY());
                    
                }
                
                damage.hitbox.setX(damage.getX());
                damage.hitbox.setY(damage.getY());
                
            } else {
                
                damage.setIsVisible(false);
            
            }
    }

    public int getID() {
        
        return 1;

    }

    public void makevisible() {
        for (Item i : stuff) {
            i.isvisible = true;
            
        }
    }

    private boolean isBlocked2(float tx, float ty) {
        int xBlock = (int) tx / SIZE;
        int yBlock = (int) ty / SIZE;
        return Blocked2.blocked2[xBlock][yBlock];

    }
}

