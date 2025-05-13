import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

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
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }
    
    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public String jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
        return ("moved " + nrStepsTaken);
    }

    
    
    
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdgePrintingCoordinates( ){
        while( !borderAhead()){
            //System.out.println ("x: " + getX() + ", " + "y: " + getY());
            move();
            if (!canMove()) {
                break;
            }
        }
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
       if( onEgg() ){
            return false;
        }else{
            return true;
        }
    }  
    
    public void turn180() {
        turnRight();
        turnRight();
    }
    
    public void climbOverFence() {
        if (fenceAhead()) {
            turnLeft();
            move();
            turnRight();
            move();
            move();
            turnRight();
            move();
            turnLeft();
        }
        else {
            showError("Your not infront of a fence");
        }
    }
    
    public void climbOverMultipleFences() {
        if (fenceAhead()) {
                turnLeft();
                move();
                turnRight();
                move();
                move();
                turnRight();
                while (fenceAhead()) {
                    if (fenceAhead()) {
                        turnLeft();
                        move();
                        turnRight();
                    }
                }
            move();
            turnLeft();
            }
            else {
            showError("Your not infront of a fence");
        }
    }
    
    public boolean grainAhead() {
        boolean grain = false;
        move();
        if (onGrain()) {
            grain = true;
        } 
        stepOneCellBackwards();
        return grain;
    }
    
    public void gotoEgg() {
        while (!onEgg()) {
            move();
            if (!canMove()) {
                break;
            }
        }
        if (onEgg()) {
            System.out.println("Egg found!");
        }
    }
    
    public void goBackToStartOfRowAndFaceBack() {
        turn180();
        walkToWorldEdgePrintingCoordinates();
        turn180();
    }
    
    public void walktoWorldEdgeClimbingOverFences() {
        while (!borderAhead()) {
            if (fenceAhead()) {
                climbOverMultipleFences();
            } else {
                move();
            }
        }
        }
    
    
    public void pickUpGrainsAndPrintCoordinates() {
        while( !borderAhead()){
            if (onGrain()) {
                System.out.println ("x: " + getX() + ", " + "y: " + getY());
                pickUpGrain();
            }
            move();
            if (onGrain()) {
                System.out.println ("x: " + getX() + ", " + "y: " + getY());
                pickUpGrain();
            }
        }
    }
    
    public void stepOneCellBackwards() {
        turn180();
        move();
        turn180();
    }
    
    public void walkToWorldEdgeFillEmptyNests() {
        while( !borderAhead()){
            if (onNest() && !onEgg()) {
                layEgg();
            }
            move();
            if (onNest() && !onEgg()) {
                layEgg();
            }
        }
    }
    
    public void walkToNestClimbingOverFences() {
        while ( !borderAhead()) {
            if (onNest()) {
                if (!onEgg()) {
                    layEgg();
                    break;
                }
            }
            if (fenceAhead()) {
                climbOverMultipleFences();
            }
            if (onNest()) {
                if (!onEgg()) {
                    layEgg();
                    break;
                }
            }
            move();
            if (onNest()) {
                if (!onEgg()) {
                    layEgg();
                    break;
                }
            }
            if (fenceAhead()) {
                climbOverMultipleFences();
            }
            if (onNest()) {
                if (!onEgg()) {
                    layEgg();
                    break;
                }
            }
        }
    }
    
    public String detectFence() {
        boolean forward = fenceAhead();
        turnRight();
        boolean right = fenceAhead();
        turnRight();
        boolean behind = fenceAhead();
        turnRight();
        boolean left = fenceAhead();
        turnRight();
        
        if (forward == true) {
            return "forward";
        } if (right == true) {
            return "right";
        } else if (behind == true) {
            return "behind";
        } else if (left == true) {
            return "left";
        } else {
            return "none";
        }
    }
    
    public String positionToRightOfFence() {
        String direction = detectFence();
        if (direction == "left") {
            turn180();
            return "done";
        } else if (direction == "forward") {
            turnLeft();
            return "wasForward";
        } else if (direction == "behind") {
            turnRight();
            return "done";
        } else if (direction == "right") {
            return "done";
        }
        return "none";
    }
    
    public String positionToLeftOfFence() {
        String direction = detectFence();
        if (direction == "left") {
            return "done";
        } else if (direction == "forward") {
            turnRight();
            return "done";
        } else if (direction == "behind") {
            turnLeft();
            return "done";
        } else if (direction == "right") {
            turn180();
            return "done";
        }
        return "none";
    }
    
    public void walkAroundFencedAreaToTheRight() {
        if (detectFence() != "none") {
            while (!onEgg()) {
                if (positionToRightOfFence() == "none") {
                    turnRight();
                    move();
                } else if (positionToRightOfFence() == "done") {
                    move();
                }
            }
            positionToRightOfFence();
        }
    }
    
    public void walkAroundFencedAreaToTheLeft() {
        if (detectFence() != "none") {
            while (!onEgg()) {
                if (positionToLeftOfFence() == "none") {
                    turnLeft();
                    move();
                } else if (positionToLeftOfFence() == "done") {
                    move();
                }
            }
            positionToLeftOfFence();
        }
    }
    
    public void walkAndPickup() {
        move();
        pickUpEgg();
    }
    
    public boolean eggToYourLeft() {
        turnLeft();
        boolean egg = eggAhead();
        turnRight();
        return egg;
    }
    
    public boolean eggToYourRight() {
        turnRight();
        boolean egg = eggAhead();
        turnLeft();
        return egg;
    }
    
    public void findNest() {
        if (!nestAhead()) {
            turnRight();
        }
        if (!nestAhead()) {
            turnRight();
        }
        if (!nestAhead()) {
            turnRight();
        }
        if (!nestAhead()) {
            turnRight();
        }
    }
    
    public void eggTrailToNest() {
        while (!onNest()) {
            if (eggAhead()) {
                walkAndPickup();
            } else if (eggToYourRight()) {
                turnRight();
                walkAndPickup();
            } else if (eggToYourLeft()) {
                turnLeft();
                walkAndPickup();
            } else if (!nestAhead()) {
                findNest();
                move();
            } else {
                break;
            }
        }
    }
}
