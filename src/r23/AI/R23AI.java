/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r23.AI;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Board;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Ship;
import java.util.Random;

/**
 *
 * @author MartinLodahl
 */
public class R23AI implements BattleshipsPlayer {

    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;

    private int nextX;
    private int nextY;
    private boolean whichRow;

    private boolean[][] foundPosition;
    private boolean[][] alreadyShot;
    private Position firstHit;
    private Position secondHit;
    private Position thirdHit;
    private Position fourthHit;
    private boolean hit;
    private boolean hunt;
    private int shortestShip;
    private int numberOfShipsLastTurn;
    private int numberOfShips;

    private int enemyPointsVsConfusion;
    private int counter;
    private int keepIt;
    private Position firstShipPlaced;
    private boolean firstShipVertical;
    private boolean confusionUsed;
    
    private int middleShotsThisTurn;
    private int middleShots;
    private int ourPointsAvg;
    private int ourPointsWithMiddle;
    private boolean middleStrat;
    
    

    public R23AI() {

    }
//8*6

    @Override
    public void placeShips(Fleet fleet, Board board) {
        foundPosition = new boolean[sizeX][sizeY];
        int x;
        int y;
        confusionUsed = false;
        if (counter < 10 || keepIt <= 50) {
            confusionUsed = true;
            for (int i = fleet.getNumberOfShips() - 1; i >= 0; i--) {
                Ship s = fleet.getShip(i);
                Position pos;

                if (i == fleet.getNumberOfShips() - 2 && firstShipVertical) {
                    x = firstShipPlaced.x + 1;
                    y = firstShipPlaced.y + 3;
                    pos = new Position(x, y);
                    for (int j = 0; j < s.size(); j++) {
                        foundPosition[x + j][y] = true;
                    }
                    board.placeShip(pos, s, false);
                    //Skal ligges horizontal
                } else if (i == fleet.getNumberOfShips() - 3 && firstShipVertical) {
                    x = firstShipPlaced.x - 3;
                    y = firstShipPlaced.y + 1;
                    pos = new Position(x, y);
                    for (int j = 0; j < s.size(); j++) {
                        foundPosition[x + j][y] = true;
                    }
                    board.placeShip(pos, s, false);
                } else if (i == fleet.getNumberOfShips() - 2 && !firstShipVertical) {
                    x = firstShipPlaced.x + 3;
                    y = firstShipPlaced.y + 1;
                    pos = new Position(x, y);
                    for (int j = 0; j < s.size(); j++) {
                        foundPosition[x][y + j] = true;
                    }
                    board.placeShip(pos, s, true);
                } else if (i == fleet.getNumberOfShips() - 3 && !firstShipVertical) {
                    x = firstShipPlaced.x + 1;
                    y = firstShipPlaced.y - 3;
                    pos = new Position(x, y);
                    for (int j = 0; j < s.size(); j++) {
                        foundPosition[x][y + j] = true;
                    }
                    board.placeShip(pos, s, true);
                }
                boolean vertical = rnd.nextBoolean();

                if (i == fleet.getNumberOfShips() - 1) {
                    if (vertical) {
                        x = rnd.nextInt(sizeX - 8) + 3;
                        y = rnd.nextInt(sizeY - (s.size() - 1));
                        pos = new Position(x, y);
                    } else {
                        x = rnd.nextInt(sizeX - (s.size() - 1));
                        y = rnd.nextInt(sizeY - 8) + 3;
                        pos = new Position(x, y);
                    }

                    firstShipPlaced = pos;
                    firstShipVertical = vertical;
                    for (int j = 0; j < s.size(); j++) {
                        foundPosition[x][y + j] = true;
                    }
                    board.placeShip(pos, s, vertical);
                }
                if (vertical) {
                    x = rnd.nextInt(sizeX);
                    y = rnd.nextInt(sizeY - (s.size() - 1));

                    while (true) {
                        boolean check = false;
                        for (int j = 0; j < s.size(); j++) {
                            if (foundPosition[x][y + j]) {
                                check = true;
                                x = rnd.nextInt(sizeX);
                                y = rnd.nextInt(sizeY - (s.size() - 1));
                                break;
                            }
                        }
                        if (check == false) {
                            break;
                        }
                    }
                    for (int j = 0; j < s.size(); j++) {
                        foundPosition[x][y + j] = true;
                    }
                    pos = new Position(x, y);
                } else {
                    x = rnd.nextInt(sizeX - (s.size() - 1));
                    y = rnd.nextInt(sizeY);
                    while (true) {
                        boolean check = false;
                        for (int j = 0; j < s.size(); j++) {
                            if (foundPosition[x + j][y]) {
                                check = true;
                                x = rnd.nextInt(sizeX - (s.size()) - 1);
                                y = rnd.nextInt(sizeY);
                            }
                        }
                        if (check == false) {
                            break;
                        }
                    }
                    for (int j = 0; j < s.size(); j++) {
                        foundPosition[x + j][y] = true;
                    }
                    pos = new Position(x, y);
                }
                board.placeShip(pos, s, vertical);
            }
        } else {

            //fleet.getNumbeOfShips retunerer antal af skiber, altså 5
            for (int i = fleet.getNumberOfShips() - 1;
                    i >= 0; i--) {

                // fleet.getship(1) returnerer skibenes info
                Ship s = fleet.getShip(i);

                boolean vertical = rnd.nextBoolean();
                Position pos;
                if (vertical) {
                    x = rnd.nextInt(sizeX);
                    y = rnd.nextInt(sizeY - (s.size() - 1));

                    while (true) {
                        boolean check = false;
                        for (int j = 0; j < s.size(); j++) {
                            if (foundPosition[x][y + j]) {
                                check = true;
                                x = rnd.nextInt(sizeX);
                                y = rnd.nextInt(sizeY - (s.size() - 1));
                                break;
                            }
                        }
                        if (check == false) {
                            break;
                        }
                        /*   int counter = 0;
                    if (counter > 1) {
                        y = rnd.nextInt(sizeY - (s.size()));
                    }
                    boolean check = false;
                    for (int j = 0; j < s.size(); j++) {
                        if (foundPosition[x][y + j]) {
                            check = true;
                            x++;
                            if (x >= sizeX) {
                                x = 0;
                            }
                        }
                    }
                    if (check == false) {
                        break;
                    }
                    counter++;
                }*/
                    }
                    for (int j = 0; j < s.size(); j++) {
                        foundPosition[x][y + j] = true;
//                    foundPosition[x + 1][y + j] = true;
//                    foundPosition[x - 1][y + j] = true;
                    }
                    pos = new Position(x, y);
                } else {
                    x = rnd.nextInt(sizeX - (s.size() - 1));
                    y = rnd.nextInt(sizeY);
                    while (true) {
                        boolean check = false;
                        for (int j = 0; j < s.size(); j++) {
                            if (foundPosition[x + j][y]) {
                                check = true;
                                x = rnd.nextInt(sizeX - (s.size()) - 1);
                                y = rnd.nextInt(sizeY);
                            }
                        }
                        if (check == false) {
                            break;
                        }
                        /*int counter = 0;
                    if (counter > 1) {
                        x = rnd.nextInt(sizeX - (s.size() - 1));
                    }
                    boolean check = false;
                    for (int j = 0; j < s.size(); j++) {
                        if (foundPosition[x + j][y]) {
                            check = true;
                            y++;
                            if (y >= sizeX) {
                                y = 0;
                            }
                        }
                    }
                    if (check == false) {
                        break;
                    }
                    counter++;
                }*/
                    }
                    for (int j = 0; j < s.size(); j++) {
                        foundPosition[x + j][y] = true;
//                    foundPosition[x + j][y + 1] = true;
//                    foundPosition[x + j][y - 1] = true;
                    }
                    pos = new Position(x, y);
                }
                board.placeShip(pos, s, vertical);
            }
        }
    }

    @Override
    public void incoming(Position pos) {

    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        Position shot;
        if (hunt) {
            shot = huntShip();
            Position negativeShot = new Position(-1, -1);
            if (shot.compareTo(negativeShot) != 0) {
                return shot;
            } else {
                hunt = false;
                firstHit = null;
                secondHit = null;
                thirdHit = null;
                fourthHit = null;
            }
        }
        if (ourPointsAvg >= 50 && middleShotsThisTurn >= 14 || middleShots<10) {
            shot = middleShootStrat();
            middleStrat = true;
            middleShotsThisTurn ++;
        } else {
            shot = shoot();
        }

        firstHit = shot;
        this.numberOfShipsLastTurn = this.numberOfShips;

        return shot;
    }

    private Position huntShip() {
        System.out.println("FirstHit:" + firstHit);
        System.out.println("SecondHit: " + secondHit);
        System.out.println("ThirdHit: " + thirdHit);
        System.out.println("FourthHit: " + fourthHit);
        System.out.println("Hit: " + hit);

        if (hit && this.numberOfShips == this.numberOfShipsLastTurn) {
            if (fourthHit != null) {
                if (fourthHit.y == firstHit.y) {
                    int xShot;
                    Position shot;
                    int xDifference = firstHit.compareTo(fourthHit);
                    System.out.println(xDifference);
                    if (xDifference < 0) {
                        xShot = fourthHit.x + 1;
                        shot = new Position(xShot, fourthHit.y);
                    } else {
                        xShot = fourthHit.x - 1;
                        shot = new Position(xShot, fourthHit.y);
                    }
                    fourthHit = shot;
                    return shot;

                } else {
                    int yShot;
                    Position shot;
                    int yDifference = firstHit.compareTo(fourthHit);
                    if (yDifference < 0) {
                        yShot = fourthHit.y + 1;
                        shot = new Position(fourthHit.x, yShot);
                    } else {
                        yShot = fourthHit.y - 1;
                        shot = new Position(fourthHit.x, yShot);
                    }
                    fourthHit = shot;
                    return shot;
                }
            } else if (thirdHit != null) {
                if (thirdHit.y == firstHit.y) {
                    int xShot;
                    Position shot;
                    int xDifference = firstHit.compareTo(thirdHit);
                    if (xDifference < 0) {
                        xShot = thirdHit.x + 1;
                        shot = new Position(xShot, thirdHit.y);
                    } else {
                        xShot = thirdHit.x - 1;
                        shot = new Position(xShot, thirdHit.y);
                    }
                    thirdHit = shot;
                    return shot;

                } else {
                    int yShot;
                    Position shot;
                    int yDifference = firstHit.compareTo(thirdHit);
                    if (yDifference < 0) {
                        yShot = thirdHit.y + 1;
                        shot = new Position(thirdHit.x, yShot);
                    } else {
                        yShot = thirdHit.y - 1;
                        shot = new Position(thirdHit.x, yShot);
                    }
                    thirdHit = shot;
                    return shot;
                }
            } else if (secondHit != null) {
                if (secondHit.y == firstHit.y) {
                    int xShot;
                    Position shot;
                    int xDifference = firstHit.compareTo(secondHit);
                    if (xDifference < 0) {
                        xShot = secondHit.x + 1;
                        shot = new Position(xShot, firstHit.y);
                        if (alreadyShot[shot.x][shot.y]) {
                            shot = new Position(firstHit.x - 1, firstHit.y);
                        }
                    } else {
                        xShot = secondHit.x - 1;
                        shot = new Position(xShot, firstHit.y);
                        if (alreadyShot[shot.x][shot.y]) {
                            shot = new Position(firstHit.x + 1, firstHit.y);
                        }
                    }
                    if (alreadyShot[shot.x][shot.y]) {
                        shot = new Position(-1, -1);
                    }
                    thirdHit = shot;
                    return shot;
                } else {
                    int yShot;
                    Position shot;
                    int xDifference = firstHit.compareTo(secondHit);
                    if (xDifference < 0) {
                        yShot = secondHit.y + 1;
                        shot = new Position(firstHit.x, yShot);
                        if (alreadyShot[shot.x][shot.y]) {
                            shot = new Position(firstHit.x, yShot + 1);
                            if (alreadyShot[firstHit.x][yShot + 1]) {
                                shot = new Position(-1, -1);
                            }
                        }
                    } else {
                        yShot = secondHit.y - 1;
                        shot = new Position(firstHit.x, yShot);
                        if (alreadyShot[shot.x][shot.y]) {
                            shot = new Position(firstHit.x, yShot - 1);
                            if (alreadyShot[firstHit.x][yShot - 1]) {
                                shot = new Position(-1, -1);
                            }
                        }
                    }
                    thirdHit = shot;
                    return shot;
                }
            }
        } else if (hit && this.numberOfShips < this.numberOfShipsLastTurn) {
            hunt = false;
            secondHit = null;
            thirdHit = null;
            fourthHit = null;
            return new Position(-1, -1);
        }
        if (thirdHit != null) {
            if (secondHit.y == firstHit.y) {
                int xShot;
                Position shot;
                int xDifference = firstHit.compareTo(secondHit);
                if (xDifference < 0) {
                    xShot = firstHit.x - 1;
                    shot = new Position(xShot, firstHit.y);
                } else {
                    xShot = firstHit.x + 1;
                    shot = new Position(xShot, firstHit.y);
                }
                if (alreadyShot[shot.x][shot.y]) {
                    if (firstHit.y + 1 < sizeY && !alreadyShot[firstHit.x][firstHit.y + 1]) {
                        shot = new Position(firstHit.x, firstHit.y + 1);
                    } else if (firstHit.y - 1 > 0 && !alreadyShot[firstHit.x][firstHit.y - 1]) {
                        shot = new Position(firstHit.x, firstHit.y - 1);
                    } else {
                        return new Position(-1, -1);
                    }
                }
                fourthHit = shot;
                return shot;
            } else {
                int yShot;
                Position shot;
                int yDifference = firstHit.compareTo(secondHit);
                if (yDifference < 0) {
                    yShot = firstHit.y - 1;
                    shot = new Position(firstHit.x, yShot);
                } else {
                    yShot = firstHit.x + 1;
                    shot = new Position(firstHit.x, yShot);
                }
                if (alreadyShot[shot.x][shot.y]) {
                    if (firstHit.x + 1 < sizeX && !alreadyShot[firstHit.x + 1][firstHit.y]) {
                        shot = new Position(firstHit.x + 1, firstHit.y);
                    } else if (firstHit.x - 1 > 0 && !alreadyShot[firstHit.x - 1][firstHit.y]) {
                        shot = new Position(firstHit.x - 1, firstHit.y);
                    } else {
                        return new Position(-1, -1);
                    }

                }
                fourthHit = shot;
                return shot;
            }
        } else if (firstHit.x + 1 < sizeX && !alreadyShot[firstHit.x + 1][firstHit.y]) {
            this.alreadyShot[firstHit.x + 1][firstHit.y] = true;
            Position shot = new Position(firstHit.x + 1, firstHit.y);
            secondHit = shot;
            return shot;
        } else if (firstHit.x - 1 >= 0 && !alreadyShot[firstHit.x - 1][firstHit.y]) {
            this.alreadyShot[firstHit.x - 1][firstHit.y] = true;
            Position shot = new Position(firstHit.x - 1, firstHit.y);
            secondHit = shot;
            return shot;
        } else if (firstHit.y + 1 < sizeY && !alreadyShot[firstHit.x][firstHit.y + 1]) {
            this.alreadyShot[firstHit.x][firstHit.y + 1] = true;
            Position shot = new Position(firstHit.x, firstHit.y + 1);
            secondHit = shot;
            return shot;
        } else if (firstHit.y - 1 >= 0 && !alreadyShot[firstHit.x][firstHit.y - 1]) {
            this.alreadyShot[firstHit.x][firstHit.y - 1] = true;
            Position shot = new Position(firstHit.x, firstHit.y - 1);
            secondHit = shot;
            return shot;
        }

        return new Position(-1, -1);
    }

    private Position middleShootStrat() {
        //4,4 og 5,5 whichRow == true;
        
        if(whichRow){
            if (nextX == 0 && nextY == 0){
                nextX = sizeX/2;
                nextY = sizeY/2;
            } else {
                nextX++;
                nextY++;
                if (nextX >= sizeX -2){
                    nextX = 2;
                    nextY = 2;
                }
                if (nextX == sizeX/2 && nextY == sizeY/2){
                    nextY++;
                    nextX-=3;               
                }
                if (nextY >= sizeY){
                    nextY-=10;
                    nextX-=2;
                }
            }
        }
        return new Position(nextX, nextY);
    }
    
    private Position shoot() {

        for (int i = 0; i < 150; i++) {
            if (whichRow) {

                nextY = rnd.nextInt(sizeY);
                if (nextY % 2 == 0) {
                    nextX = rnd.nextInt(sizeX / 2) * 2;

                } else {
                    nextX = rnd.nextInt(sizeX / 2) * 2 + 1;

                }
            } else {
                nextY = rnd.nextInt(sizeY);
                if (nextY % 2 == 0) {
                    nextX = rnd.nextInt(sizeX / 2) * 2 + 1;

                } else {
                    nextX = rnd.nextInt(sizeX / 2) * 2;

                }
            }

            if (!alreadyShot[nextX][nextY]) {
                break;
            }
        }

        this.alreadyShot[nextX][nextY] = true;
        Position shot = new Position(nextX, nextY);
        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
//        this.shortestShip = enemyShips.getShip(0).size();
        this.numberOfShips = enemyShips.getNumberOfShips();
        if (hit) {
            this.hunt = hit;
        }
        this.hit = hit;
    }

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        counter = 0;
    }

    @Override
    public void startRound(int round) {
        alreadyShot = new boolean[sizeX][sizeY];
        hunt = false;
        nextX = 0;
        nextY = 0;
        firstHit = null;
        secondHit = null;
        thirdHit = null;
        fourthHit = null;
        int whichRowInt = rnd.nextInt(1);
        rnd.nextInt(2);
        if (whichRowInt == 1) {
            whichRow = true;
        } else {
            whichRow = false;
        }
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        if (confusionUsed) {
            this.enemyPointsVsConfusion += enemyPoints;
            counter++;
            keepIt = enemyPointsVsConfusion / counter;
        }
        System.out.println("enemyPoints : " + enemyPoints);
        System.out.println("keepIt: " + keepIt);
        System.out.println("Counter: " + counter);
        if(middleStrat){
            this.ourPointsWithMiddle += points;
            middleShots++;
            this.ourPointsAvg = ourPointsWithMiddle/middleShots;
            middleStrat = false;
        }
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
    }

    
}
