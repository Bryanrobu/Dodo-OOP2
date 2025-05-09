import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * 
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 28-02-2017
 */
public class MyDodo extends Dodo
{
    public MyDodo() {
        this ( WEST );
    }

    public MyDodo( int init_direction ) {
        super ( init_direction );
    }

    /**
     * Go to the edge of the world and
     * walk along the border
     */
    public void act() {
        walkAlongFence();
    }

    private void walkAlongFence() {
        if ( fenceAhead ( ) ) {
            turnRight( );
        } else {
            step( );
            turnLeft( );
        }    
    }
    
    private void handleKeyPress() {
        int newDirection = getNewDirection();
        if ( newDirection != -1 ) {
            setDirection( newDirection );
            step();
        }
    }

    
    /**
     * Checks whether any of the arrow keys has
     * been pressed. If so, the corresponding direction
     * is returned. Otherwise the result will be -1
     */
    private int getNewDirection() {
        if ( Greenfoot.isKeyDown( "left" ) ) {
            return WEST;
        } else {
            return -1;
        }
    }
 
    private void levelFinished(  ) {
        Message.showMessage(  new Compliment ( "Level finished" ), getWorld() );
    }

}
