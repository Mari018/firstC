package academy.mindswap.gameobjects.snake;

import academy.mindswap.field.Field;
import academy.mindswap.field.Position;
import org.w3c.dom.Node;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class Snake {

    private final static int SNAKE_INITIAL_SIZE = 3;
    private Direction direction;
    private boolean alive;
    private static LinkedList<Position> body;


    public Snake(Direction startDirection){

        body = new LinkedList<>();

        for (int i = 0; i < SNAKE_INITIAL_SIZE; i++) {
            body.add(new Position(i+ 3, 15 ));
        }

        this.direction = startDirection;
        alive = true;
    }


    public void increaseSize() {

        body.addFirst(new Position(getTail().getRow(), getTail().getCol()));

    }

    public void move(Direction direction) {
        Position head = getHead();


        switch (direction) {
            case UP:
                if (this.direction == Direction.DOWN) {
                    move();
                    break;
                } else {
                    body.removeFirst();
                    head = new Position(head.getCol(), head.getRow() - 1);
                    this.direction = direction;
                    body.addLast(head);
                    break;
                }
            case DOWN:
                if (this.direction == Direction.UP) {
                    move();
                    break;
                } else {
                    body.removeFirst();
                    head = new Position(head.getCol(), head.getRow() + 1);
                    this.direction = direction;
                    body.addLast(head);
                    break;
                }
            case LEFT:
                if (this.direction == Direction.RIGHT) {
                    move();
                    break;
                } else {
                    body.removeFirst();
                    head = new Position(head.getCol() - 1, head.getRow());
                    this.direction = direction;
                    body.addLast(head);
                    break;
                }
            case RIGHT:
                if (this.direction == Direction.LEFT) {
                    move();
                    break;
                } else {
                    body.removeFirst();
                    head = new Position(head.getCol() + 1, head.getRow());
                    this.direction = direction;
                    body.addLast(head);
                    break;
                }
        }
    }

    public void move(){
        move(direction);

    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public Position getHead() {
        return body.getLast();
    }

    public Position getTail() {
        return body.getFirst();
    }

    public LinkedList<Position> getFullSnake(){
        return body;
    }

    public int getSnakeSize() {
        return body.size();
    }

    public List<Position> getBody(){

        return getFullSnake().subList(1, getSnakeSize()-1);
    }
}

