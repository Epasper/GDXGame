package com.mygdx.game;

public class MarioCharacter {

    private Coordinates coordinates = new Coordinates();

    private boolean leftTileIsOccupied;
    private boolean rightTileIsOccupied;
    private boolean upperTileIsOccupied;
    private boolean lowerTileIsOccupied;

    private int occupiedTileX;

    private int occupiedTileY;
    private int tileSize = 62;
    private boolean goLeft;
    private boolean goRight;
    private boolean jump;
    private boolean crouch;
    private boolean running;
    private boolean touchingTheGround;
    private int startingJumpHeight = 5;
    private double jumpHeight = startingJumpHeight; //increasing this will make the animation faster but jumping will not be as high
    private double gravity = 0.5;


    MarioCharacter() {
        coordinates.setxPosition(100);
        coordinates.setyPosition(200);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setJumpHeight(double jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isCrouch() {
        return crouch;
    }

    public void moveMarioBy(float deltaX, float deltaY) {
        if (deltaX == 0 && deltaY == 0) return;
        System.out.println(deltaX + "-" + deltaY);
        System.out.println("Moving. Ground found: " + touchingTheGround + " Jumping: " + isJump());
        if (isCrouch() && touchingTheGround) return;

        if (!jump && (goRight || goLeft) && (!lowerTileIsOccupied)) {
            startFalling();
        } else if ((goRight
                && !rightTileIsOccupied
        ) || (goLeft
                && !leftTileIsOccupied
        ) || (jump)) {
            moveMarioByADistance(deltaX, deltaY);
        } else {
            snapMarioAfterWallCollision(deltaY);
        }
        checkTheOccupiedTileCoordinates(tileSize);
        boolean isFalling;
        isFalling = deltaY > 0;
        checkGroundContact(isFalling);
        checkTheSurroundings();

        System.out.println("Moving after check. Ground found: " + touchingTheGround + " Jumping: " + isJump());
        if (!jump && touchingTheGround && (!goLeft || !goRight)) {
            setJumpHeight(startingJumpHeight);
            snapMarioAfterFloorCollision(deltaX, true);
        }
    }

    private void startFalling() {
        jump = true;
        jumpHeight = 0;
    }

    private void snapMarioAfterFloorCollision(float deltaX, boolean snappingToUpperEdge) {
        float xCoordinate = coordinates.getxPosition();
        float x = xCoordinate + deltaX;
        float yPos;
        if (snappingToUpperEdge) {
            yPos = tileSize * (occupiedTileY) + 11;
        } else {
            yPos = tileSize * (occupiedTileY) - 30;
        }
        moveMarioToPosition(x, yPos);
        System.out.println("Floor! SNAP! - snapped to: " + (occupiedTileY - 1) + " tile. Coords snapped: " + yPos);
    }

    private void relocate(float x, float y) {

    }

    private void snapMarioAfterWallCollision(float deltaY) {
        float currentY = coordinates.getyPosition();
        float y = currentY + deltaY;
        float xPos = 0;
        if (leftTileIsOccupied) {
            xPos = tileSize * occupiedTileX + 30;
        }
        if (rightTileIsOccupied) {
            xPos = tileSize * occupiedTileX - 25;
        }
        moveMarioToPosition(xPos, y);
        System.out.println("Wall! SNAP!");
        checkTheSurroundings();
    }

    public void moveMarioToPosition(float x, float y) {
        coordinates.setxPosition(x);
        coordinates.setyPosition(y);
        this.relocate(coordinates.getxPosition(), coordinates.getyPosition());
    }


    public void moveMarioByADistance(float x, float y) {
        float currentX = coordinates.getxPosition();
        float currentY = coordinates.getyPosition();


        if (jump && upperTileIsOccupied) {
            jump = false;
            upperTileIsOccupied = false;
            snapMarioAfterFloorCollision((int) x, false);
            startFalling();
//            currentY = 0;
        }
        coordinates.setxPosition(currentX + x);
        coordinates.setyPosition(currentY + y);
        System.out.println("Mario Position: X: " + coordinates.getxPosition() + " Y: " + coordinates.getyPosition());
        //todo add "if" clause with the boundary checking at the later stage
        //this.setTranslateX(coordinates.getxPosition());
        //this.setTranslateY(coordinates.getyPosition());
        relocate(coordinates.getxPosition(), coordinates.getyPosition());
    }

    private void checkTheOccupiedTileCoordinates(int tileSize) {
        occupiedTileX = ((int) ((Math.round(coordinates.getxPosition()) + (tileSize)) / tileSize));
        occupiedTileY = ((int) ((Math.round(coordinates.getyPosition() - 15) + (tileSize)) / tileSize));
        System.out.println("Current Tile: " + occupiedTileX + " - - " + occupiedTileY);

    }

    public void checkTheSurroundings() {
        int leftCheck = occupiedTileX - 1;
        int rightCheck = occupiedTileX + 1;
        int upperCheck = occupiedTileY - 1;
        int lowerCheck = occupiedTileY + 1;

/*        lowerTileIsOccupied = tileGrid[occupiedTileX][lowerCheck].tileTypes == TileTypes.BRICK ||
                tileGrid[occupiedTileX][lowerCheck].tileTypes == TileTypes.FLOOR;
        upperTileIsOccupied = tileGrid[occupiedTileX][upperCheck].tileTypes == TileTypes.BRICK ||
                tileGrid[occupiedTileX][upperCheck].tileTypes == TileTypes.FLOOR;
        leftTileIsOccupied = tileGrid[leftCheck][occupiedTileY - 1].tileTypes == TileTypes.BRICK ||
                tileGrid[leftCheck][occupiedTileY - 1].tileTypes == TileTypes.FLOOR;
        rightTileIsOccupied = tileGrid[rightCheck][occupiedTileY - 1].tileTypes == TileTypes.BRICK ||
                tileGrid[rightCheck][occupiedTileY - 1].tileTypes == TileTypes.FLOOR;*/
        /*try {
            System.out.println("Right box: " + rightCheck + "--" + rightTileIsOccupied + tileGrid[occupiedTileX + 1][occupiedTileY - 1].tileTypes.name());
        } catch (NullPointerException ignored) {
            System.out.println("No bricks or floor tiles found on the right side");
        }
        try {
            System.out.println(" Left box: " + leftCheck + "--" + leftTileIsOccupied + tileGrid[occupiedTileX - 1][occupiedTileY - 1].tileTypes.name());
        } catch (NullPointerException ignored) {
            System.out.println("No bricks or floor tiles found on the left side");
        }
        try {
            System.out.println(" Upper box: " + upperCheck + "--" + upperTileIsOccupied + tileGrid[occupiedTileX][upperCheck].tileTypes.name());
        } catch (NullPointerException ignored) {
            System.out.println("No bricks or floor tiles found on the upper side");
        }
        try {
            System.out.println(" Lower box: " + lowerCheck + "--" + lowerTileIsOccupied + tileGrid[occupiedTileX][lowerCheck].tileTypes.name());
        } catch (NullPointerException ignored) {
            System.out.println("No bricks or floor tiles found on the lower side");
        }*/
    }

    public void checkGroundContact(boolean isFalling) {

        /*if (tileGrid[occupiedTileX][occupiedTileY + 1].tileTypes == TileTypes.BRICK ||
                tileGrid[occupiedTileX][occupiedTileY + 1].tileTypes == TileTypes.FLOOR) {
            System.out.println("Found ground. Current tile coordinates: " + occupiedTileX + "--" + (occupiedTileY));
            touchingTheGround = true;
            if (isFalling) {
                setJump(false);
            }
        } else {
            System.out.println("No ground found");
            touchingTheGround = false;
        }*/
    }

    public void jump(int jumpingSteps, Coordinates coordinates) {
        while (coordinates.getxPosition() < 0) {

        }
    }
}
