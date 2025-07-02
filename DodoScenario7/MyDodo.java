import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.1 -- 29-07-2017
 */
public class MyDodo extends Dodo
{
    /* ATTRIBUTE DECLARATIONS: */
    private int myNrOfStepsTaken;
           
    public MyDodo() {
        super( EAST );
        /* INITIALISATION OF ATTRIBUTES: */
        myNrOfStepsTaken = 0;
    }

    /* METHODS OF THE CLASS: */

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, 
     * i.e. there are no obstructions or end of world in the cell in front of her.
     * 
     * <p> Initial:   Dodo is somewhere in the world
     * <p> Final:     Same as initial situation
     * 
     * @return  boolean true if Dodo can move (thus, no obstructions ahead)
     *                  false if Dodo can't move
     *                      there is an obstruction or end of world ahead
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
    }

    /**
    * Places all the Egg objects in the world in a list.
    * 
    * @return List of Egg objects in the world
    */
    public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }
    
    public List<Integer> createListOfNumbers() {
        return new ArrayList<> (Arrays.asList( 2, 43, 7, -5, 12, 7 ));
    }

    /**
     * Method for praciticing with lists.
     */
    public void practiceWithLists( ){
        List<Integer> listOfNumbers = createListOfNumbers();
        System.out.println("First element: " + listOfNumbers.get(1) ); 
    }

    public void practiceWithListsOfSurpriseEgss( ){
        List<SurpriseEgg>  listOfEgss = SurpriseEgg.generateListOfSurpriseEggs( 12, getWorld() );
    }
    
    /**
    * Faces east
    */
    public void faceEast() {
        while (getDirection() != EAST) {
            if (getDirection() == SOUTH) {
                turnLeft();
                break;
            }
            turnRight();
        }
    }
    
    /**
    * Faces north
    */
    public void faceNorth() {
        while (getDirection() != NORTH) {
            if (getDirection() == EAST) {
                turnLeft();
                break;
            }
            turnRight();
        }
    }
    
    /**
    * Faces south
    */
    public void faceSouth() {
        while (getDirection() != SOUTH) {
            if (getDirection() == WEST) {
                turnLeft();
                break;
            }
            turnRight();
        }
    }
    
    /**
    * Faces west
    */
    public void faceWest() {
        while (getDirection() != WEST) {
            if (getDirection() == NORTH) {
                turnLeft();
                break;
            }
            turnRight();
        }
    }
    
    /**
    * Faces a random direction 
    */
    public void turnRandomly() {
        if (randomDirection() == 0) {
            faceNorth();
        } else if (randomDirection() == 1) {
            faceEast();
        }else if (randomDirection() == 1) {
            faceSouth();
        }else if (randomDirection() == 1) {
            faceWest();
        }
    }
    
    /**
    * Moves around randomly while counting down steps and picking up eggs
    */
    public void moveRandomly() {
        int myNrOfStepsTaken = Mauritius.MAXSTEPS;
        int score = 0;
        if(onBlueEgg()) {
            pickUpEgg();
            score =+ 1;
        } else if (onGoldEgg()) {
            pickUpEgg();
            score =+ 5;
        }
        while (myNrOfStepsTaken != 0) {
            turnRandomly();
            if (!canMove()) {
                turnRandomly();
            } else {
                move();
                if(onBlueEgg()) {
                    pickUpEgg();
                    score =+ 1;
                } else if (onGoldEgg()) {
                    pickUpEgg();
                    score =+ 5;
                }
                myNrOfStepsTaken--;
                ((Mauritius)getWorld()).updateScore(myNrOfStepsTaken, score);
            }
        }
        faceEast();
    }
    
    
    /**
    * Goes to a specefic egg
    * 
    * @param: Egg item, score, steps taken
    * 
    * @return: int ofnew steps taken
    */
    public int goToEgg(Egg closestEgg, int score, int myNrOfStepsTaken) {
        while (getX() < closestEgg.getX() && myNrOfStepsTaken > 0) {
            faceEast();
            move();
            myNrOfStepsTaken--;
            ((Mauritius)getWorld()).updateScore(myNrOfStepsTaken, score);
        }
        while (getX() > closestEgg.getX() && myNrOfStepsTaken > 0) {
            faceWest();
            move();
            myNrOfStepsTaken--;
            ((Mauritius)getWorld()).updateScore(myNrOfStepsTaken, score);
        }
        
        while (getY() < closestEgg.getY() && myNrOfStepsTaken > 0) {
            faceSouth();
            move();
            myNrOfStepsTaken--;
            ((Mauritius)getWorld()).updateScore(myNrOfStepsTaken, score);
        }
        while (getY() > closestEgg.getY() && myNrOfStepsTaken > 0) {
            faceNorth();
            move();
            myNrOfStepsTaken--;
            ((Mauritius)getWorld()).updateScore(myNrOfStepsTaken, score);
        }
        return myNrOfStepsTaken;
    }
    
    /**
    * Finds the closest egg to the dodo and measuring if its worth going to a
    * golden egg or not
    * 
    * @param: list of eggs
    * 
    * @return: item of egg
    */
    public Egg findClosestEgg(List<Egg> listOfEggs) {
        Egg closestEgg = null;
        int goldenEggRange = 8;
        int shortestDistance = Integer.MAX_VALUE;
        for (Egg egg : listOfEggs) {
            int dist = Math.abs(egg.getX() - getX()) + Math.abs(egg.getY() - getY());
            if (dist < shortestDistance) {
                shortestDistance = dist;
                closestEgg = egg;
            }
            if (egg instanceof GoldenEgg && dist < goldenEggRange) {
                closestEgg = egg;
                break;
            }
        }
        return closestEgg;
    }
   
    /**
    * Finds the ideal path to all the eggs, counting steps along the way and updating the
    * scoreboard
    */
    public void dodoRace() {
        int myNrOfStepsTaken = Mauritius.MAXSTEPS;
        int score = 0;
        int deviation = 0;
        List<Egg> listOfEggs= getListOfEggsInWorld();
        while (myNrOfStepsTaken != 0 && !listOfEggs.isEmpty()) {
            Egg closestEgg = findClosestEgg(listOfEggs);
    
            if (closestEgg != null) {
                myNrOfStepsTaken = goToEgg(closestEgg, score, myNrOfStepsTaken);
        
                if (onEgg()) {
                    Egg eggvalue = pickUpEgg();
                    score += eggvalue.getValue();
                }              
        
                listOfEggs = getListOfEggsInWorld();
                updateScores(myNrOfStepsTaken, score);
            } else {
                break;
            }
        }
    }
}
