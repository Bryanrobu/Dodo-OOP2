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
            if (getDirection() == EAST) {
                turnLeft();
                move();
                turnRight();
                move();
                move();
                turnRight();
                move();
                turnLeft();
            } else if (getDirection() == WEST) {
                turnRight();
                move();
                turnLeft();
                move();
                move();
                turnLeft();
                move();
                turnRight();
            }
        }
        else {
            showError("Your not infront of a fence");
        }
    }
    
    public void climbOverMultipleFences() {
        if (fenceAhead()) {
            if (getDirection() == EAST) {
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
            } else if (getDirection() == WEST) {
                turnRight();
                move();
                turnLeft();
                move();
                move();
                turnLeft();
                while (fenceAhead()) {
                    if (fenceAhead()) {
                        turnRight();
                        move();
                        turnLeft();
                    }
                }
                move();
                turnRight();
            }
            } else {
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
    
    public boolean detectFenceOnRight() {
        turnRight();
        boolean right = fenceAhead();
        turnLeft();
        
        if (right == true) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean detectFenceOnLeft() {
        turnLeft();
        boolean left = fenceAhead();
        turnRight();
        
        if (left == true) {
            return true;
        } else {
            return false;
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
            faceEast();
        } else {
            showError("There is no fence");
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
            faceEast();
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
            } else if (nestAhead()) {
                move();
            } else if (!nestAhead()) {
                findNest();
                move();
            } else {
                break;
            }
        }
    }
    
    public void geefCompliment(String tekst) {
        getWorld().addObject(new Compliment(tekst), getX(), getY());
    }
    
    public void solveBasicMazeToNest() {
        while (!onNest()) {
            if (detectFenceOnRight() == false) {
                turnRight();
                move();
            } else if (fenceAhead() == false) {
                move();
            } else if (detectFenceOnLeft() == false) {
                turnLeft();
                move();
            } else {
                turn180();
            }
        }
        faceEast();
        layEgg();
    }
    
    public void faceEast() {
        while (getDirection() != EAST) {
            if (getDirection() == SOUTH) {
                turnLeft();
                break;
            }
            turnRight();
        }
    }
    
    public void faceNorth() {
        while (getDirection() != NORTH) {
            if (getDirection() == EAST) {
                turnLeft();
                break;
            }
            turnRight();
        }
    }
    
    public void faceSouth() {
        while (getDirection() != SOUTH) {
            if (getDirection() == WEST) {
                turnLeft();
                break;
            }
            turnRight();
        }
    }
    
    public void faceWest() {
        while (getDirection() != WEST) {
            if (getDirection() == NORTH) {
                turnLeft();
                break;
            }
            turnRight();
        }
    }
    
    public boolean locationReached(int x, int y) {
        if (getX() == x && getY() == y) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean canStepBackwards() {
        turn180();
        boolean result = canMove();
        turn180();
        return result;
    }
    
    public void goToLocation(int x, int y) {
        if (validCoordinates(x, y)) {
            while (!locationReached(x, y)) {
                if (getX() < x) {
                    faceEast();
                    if (!borderAhead() && !fenceAhead()) {
                        move();
                    } else if (fenceAhead()) {
                        if (canStepBackwards()) {
                            stepOneCellBackwards();
                        } else {
                            climbOverMultipleFences();
                        }
                    } else {
                        break;
                    }             
                } else if (getX() > x) {
                    faceWest();
                    if (!borderAhead() && !fenceAhead()) {
                        move();
                    } else if (fenceAhead()) {
                        if (canStepBackwards()) {
                            stepOneCellBackwards();
                        } else {
                            climbOverMultipleFences();
                        }                        
                    } else {
                        break;
                    }
                }
                
                if (getY() < y) {
                    faceSouth();
                    if (!borderAhead() && !fenceAhead()) {
                        move();
                    } else if (fenceAhead()) {
                        climbOverMultipleFences();
                    } else {
                        break;
                    }               
                } else if (getY() > y) {
                    faceNorth();
                    if (!borderAhead() && !fenceAhead()) {
                        move();
                    } else if (fenceAhead()) {
                        climbOverMultipleFences();
                    } else {
                        break;
                    }         
                }
            }
            faceEast();
        }
    }
    
    public boolean validCoordinates(int x, int y) {
        if (x > getWorld().getWidth() -1 || y > getWorld().getHeight()-1) {
            showError("Invalid Coordinates");
            return false;
        } else {
            return true;
        }
    }
    
    public int countEggsInRow() {
        int totalEggs = 0;
        int startx = getX();
        int starty = getY();
        goBackToStartOfRowAndFaceBack();
        if (onEgg()) {
                totalEggs++;
            }
        while (!borderAhead()) {
            if (!fenceAhead()) {
                move();
                if (onEgg()) {
                    totalEggs++;
                }
            } else {
                climbOverMultipleFences();
            }
        }
        goToLocation(startx, starty);
        return totalEggs;
    }
    
    public int countEggsInRowForFullMap() {
        int totalEggs = 0;
        if (onEgg()) {
                totalEggs++;
            }
        while (!borderAhead()) {
            if (!fenceAhead()) {
                move();
                if (onEgg()) {
                    totalEggs++;
                }
            } else {
                showError("Something went wrong");
                break;               
            }
        }
        return totalEggs;
        }
    
    
    public boolean layTrailOfEggs(int n) {
        if (getDirection() == EAST || getDirection() == WEST) {
            if (getX() + n-1  < getWorld().getWidth()) {
                for (int i = 1 ; i < n ; i++) {
                    layEgg();
                    move();
                }
                layEgg();
            } else {
                showError("Out of bounds!");
                return false;
            }
        } else if (getDirection() == NORTH || getDirection() == SOUTH) {
            if (getX() + n-1  < getWorld().getHeight()) {
                for (int i = 1 ; i < n ; i++) {
                    layEgg();
                    move();
                }
                layEgg();
            } else {
                showError("Out of bounds!");
                return false;
            }
        }
        return true;
    }
    
    public boolean turnAroundToNextRow() {
        if (getDirection() == EAST || getDirection() == NORTH) {
            turnRight();
            if (borderAhead()) {
                return true;
            }
            move();
            turnRight();
            return false;
        } else if (getDirection() == WEST || getDirection() == SOUTH) {
            turnLeft();
            if (borderAhead()) {
                return true;
            }            
            move();
            turnLeft();
            return false;
        } else {
            return true;
        }
    }
    
    public int countEggsInWorld() {
        int startx = getX();
        int starty = getY();
        boolean end = false;
        int totalEggs = 0;
        if (validCoordinates(0, 0)) {
            goToLocation(0, 0);
            faceEast();           
            while (end == false) {
                totalEggs = totalEggs + countEggsInRowForFullMap();
                end = turnAroundToNextRow();
            }
            if (validCoordinates(startx, starty)) {
                goToLocation(startx, starty);
            }            
        }
        return totalEggs;
    }
    
    public int countWhichRowHasMostEggs() {
        int startx = getX();
        int starty = getY();
        boolean end = false;
        int tempRow = 0;
        int maxRowEggs = 0;
        int activeRowEggs = 0;
        int finalRow = 0;
        if (validCoordinates(0, 0)) {
            goToLocation(0, 0);
            faceEast();
            while (end == false) {
                tempRow++;
                activeRowEggs = countEggsInRowForFullMap();
                if (activeRowEggs >= maxRowEggs) {
                    maxRowEggs = activeRowEggs;
                    finalRow = tempRow;
                }
                end = turnAroundToNextRow();
            }
            if (validCoordinates(startx, starty)) {
                goToLocation(startx, starty);
            }
        }
        return finalRow;
    }
    
    public boolean turnAroundToSamex(int x) {
        faceSouth();
        if (borderAhead()) {
            return true;
        }
        move();
        faceWest();
        while (getX() > x) {
            move();
        }
        return false;
    }
    
    public boolean turnAroundToPreviousx(int x) {
        faceSouth();
        if (borderAhead()) {
            return true;
        }
        move();
        faceWest();
        while (getX() > x-1) {
            move();
            if (borderAhead()) {
            return true;
            }
        }
        return false;
    }
    
    public void monumentOfEggs() {
        int startx = getX();
        int starty = getY();
        int eggsToLay = 1;
        boolean end = false;
        boolean canMove = true;
        while (end == false) {
            faceEast();
            canMove = layTrailOfEggs(eggsToLay);
            if (canMove == false) {
                faceEast();
                break;
            }
            end = turnAroundToSamex(startx);
            eggsToLay++;
        }
        faceEast();
    }
    
    public void stableMonumentOfEggs() {
        int startx = getX();
        int starty = getY();
        int eggsToLay = 1;
        boolean end = false;
        boolean canMove = true;
        while (end == false) {
            faceEast();
            canMove = layTrailOfEggs(eggsToLay);
            if (canMove == false) {
                faceEast();
                break;
            }
            end = turnAroundToSamex(startx);
            eggsToLay = eggsToLay * 2;
        }
        faceEast();
    }
    
    public void stablePyramidOfEggs() {
        int startx = 0;
        int starty = 0;
        int eggsToLay = 1;
        boolean end = false;
        boolean canMove = true;
        while (end == false) {
            startx = getX();
            starty = getY();
            faceEast();
            canMove = layTrailOfEggs(eggsToLay);
            if (canMove == false) {
                faceEast();
                break;
            }
            end = turnAroundToPreviousx(startx);
            eggsToLay = eggsToLay += 2;
        }
        faceEast();
    }
    
    public double countAverageEggsPerRow() {
        int startx = getX();
        int starty = getY();
        boolean end = false;
        double totalEggs = 0;
        double averageEggsPerRow = 0;
        double rows = 0;
        if (validCoordinates(0, 0)) {
            goToLocation(0, 0);   
            while (end == false) {
                rows++;
                totalEggs = totalEggs + countEggsInRowForFullMap();
                end = turnAroundToNextRow();
            }
            if (validCoordinates(startx, starty)) {
                goToLocation(startx, starty);
            }
            averageEggsPerRow = totalEggs / rows;
        }
        return averageEggsPerRow;
    }
    
    private boolean evenEggsInRow(int x) {
        if (x == 2) {
            return true;
        }
        return false;
    }
    
    public void fixBrokenRowAndColumn() {
        int startx = getX();
        int starty = getY();
        boolean end = false;
        int rows = -1;
        int rowEggs = 0;
        int brokenRow = 0;
        int brokenColumn = 0;
        if (validCoordinates(0, 0)) {
            goToLocation(0, 0); 
            while (end == false) {
                rows++;
                if (countEggsInRowForFullMap() % 2 != 0) {
                    brokenRow = rows;
                }
                end = turnAroundToNextRow();
            }
            end = false;
            goToLocation(0, 0);  
            faceSouth();
            rows = -1;
            while (end == false) {
                rows++;
                if (countEggsInRowForFullMap() % 2 != 0) {
                    brokenColumn = rows;
                }
                end = turnAroundToNextRow();
            }
            goToLocation(brokenColumn, brokenRow);
            layEgg();
            goToLocation(0, 0);
        }
    }
}

