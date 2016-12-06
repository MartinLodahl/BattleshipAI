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

    private boolean[][] foundPosition;
    private boolean[][] alreadyShot;
    private Position firstHit;
    private Position secondHit;
    private boolean hit;
    private boolean hunt;
    private int shortestShip;
    private int numberOfShipsLastTurn;
    private int numberOfShips;

    public R23AI() {

    }

    @Override
    public void placeShips(Fleet fleet, Board board) {

        nextX = 0;
        nextY = 0;
        foundPosition = new boolean[sizeX][sizeY];
        for (int i = fleet.getNumberOfShips() - 1; i >= 0; i--) {
            Ship s = fleet.getShip(i);
            boolean vertical = rnd.nextBoolean();
            Position pos;
            if (vertical) {
                int x = rnd.nextInt(sizeX);
                int y = rnd.nextInt(sizeY - (s.size()));

                while (true) {
                    boolean check = false;
                    for (int j = 0; j < s.size(); j++) {
                        if (foundPosition[x][y + j]) {
                            check = true;
                            x = rnd.nextInt(sizeX);
                            y = rnd.nextInt(sizeY - (s.size()));
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
                }
                pos = new Position(x, y);
            } else {
                int x = rnd.nextInt(sizeX - (s.size() - 1));
                int y = rnd.nextInt(sizeY);
                while (true) {
                    boolean check = false;
                    for (int j = 0; j < s.size(); j++) {
                        if (foundPosition[x + j][y]) {
                            check = true;
                            x = rnd.nextInt(sizeX - (s.size() - 1));
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
                }
                pos = new Position(x, y);
            }
            board.placeShip(pos, s, vertical);
        }
    }

    @Override
    public void incoming(Position pos) {

    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        if (hit && this.numberOfShips == this.numberOfShipsLastTurn) {
            hunt = true;
            if (secondHit != null){
                secondHit.compareTo(firstHit);
            } else if (!alreadyShot[firstHit.x][firstHit.y + 1]) {
                Position shot = new Position(firstHit.x, firstHit.y + 1);
                this.secondHit = shot;
                return shot;
            } else if (!alreadyShot[firstHit.x][firstHit.y - 1]) {
                Position shot = new Position(firstHit.x, firstHit.y - 1);
                this.secondHit = shot;
                return shot;
            }else if (!alreadyShot[firstHit.x+1][firstHit.y]) {
                Position shot = new Position(firstHit.x+1, firstHit.y);
                this.secondHit = shot;
                return shot;
            }else if (!alreadyShot[firstHit.x-1][firstHit.y]) {
                Position shot = new Position(firstHit.x-1, firstHit.y);
                this.secondHit = shot;
                return shot;
            }
        } else if (hit && this.numberOfShips < this.numberOfShipsLastTurn) {
            hunt = false;

        } else if (hunt) {

        }
        Position shot = new Position(nextX, nextY);
        ++nextX;
        if (nextX >= sizeX) {
            nextX = 0;
            ++nextY;
            if (nextY >= sizeY) {
                nextY = 0;
            }
        }

        firstHit = shot;
        this.numberOfShipsLastTurn = this.numberOfShips;
        this.alreadyShot[shot.x][shot.y] = true;
        return shot;
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        this.shortestShip = enemyShips.getShip(0).size();
        this.numberOfShips = enemyShips.getNumberOfShips();
        this.hit = hit;
    }

    @Override
    public void startMatch(int rounds, Fleet ships, int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public void startRound(int round) {
        //Do nothing
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
        //Do nothing
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
        //Do nothing
    }
}
