package correll_deliverer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class Win extends BasicGameState {
    
    

    private StateBasedGame game;
    public Image startimage;

     public Win(int xSize, int ySize) {

    }

    public void init(GameContainer container, StateBasedGame game)

            throws SlickException {
        startimage = new Image("res/win.png"); 

        this.game = game;
        
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g)

            throws SlickException {
            startimage.draw();

        g.setColor(Color.white);
        g.drawString("Press 1 to Play Again", 110, 200);
        
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta)


            throws SlickException {

    }

    public int getID() {

        return 3;

    }




    public void keyReleased(int key, char c) {

        switch (key) {

            case Input.KEY_1:

                Level1.player.x = 550;
                Level1.player.y = 77;
                Level2.player.x = 45;
                Level2.player.y = 200;
                Special.isvisible = true;
                Level1.reveal = false;
                Level1.pass = false;
                Level1.draw = false;
                Orb.isvisible = true;
                OrbRed.isvisible = true;
                OrbYellow.isvisible = true;
                Marble.isVisible = false;
                Level2.blueb = false;
                Level2.redb = false;
                Level2.yellowb = false;
                Level2.orbb = false;
                Level2.orbs = 0;
                
                game.enterState(0, new FadeOutTransition(Color.white), new FadeInTransition(Color.white));

                break;
                
            default:

                break;

        }
    }
}
