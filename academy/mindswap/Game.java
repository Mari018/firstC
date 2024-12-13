package academy.mindswap;

import academy.mindswap.field.Field;
import academy.mindswap.field.Obstacle;
import academy.mindswap.field.Position;
import academy.mindswap.gameobjects.fruit.Fruit;
import academy.mindswap.gameobjects.snake.Direction;
import academy.mindswap.gameobjects.snake.Snake;
import com.googlecode.lanterna.input.Key;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static academy.mindswap.field.Field.getHeight;
import static academy.mindswap.field.Field.getWidth;
import static javax.swing.plaf.basic.BasicGraphicsUtils.drawString;


public class Game {

    private Snake snake;
    private Fruit fruit;
    private int delay;
    private Obstacle obstacle; //= new Obstacle(generateRandomPosicion());
    private LinkedList<Position> obPosicions = new LinkedList<>();
    public boolean restart;





    public Game(int cols, int rows, int delay) {
        Field.init(cols, rows);
        snake = new Snake(Direction.RIGHT);
        this.delay = delay;
    }

    public void start() throws InterruptedException {

             generateFruit();// uncomment when it's time to introduce fruits
             generateObstacle();

        while (snake.isAlive()) {
            Thread.sleep(delay);
            Field.clearTail(snake);
            moveSnake();
            checkCollisions();
            Field.drawSnake(snake);
        }
        endGame();
    }



    private Position generateRandomPosicion() {
        int min = 1;
        int xMax = getWidth() - 1;
        int yMax = getHeight() - 1;

        int x = (int) (Math.random() * (xMax - min)) + min;
        int y = (int) (Math.random() * (yMax - min)) + min;

        Position random = new Position(x, y);

        if (snake.getFullSnake().contains(random)) {
            return generateRandomPosicion();
        }

        return random;
    }


    private void generateFruit() {

            fruit = new Fruit(generateRandomPosicion());

            Field.drawFruit(fruit);

    }

    private void generateObstacle() {
        obstacle = new Obstacle(generateRandomPosicion());
        Iterator<Position> iterator = obPosicions.iterator();


        if(obPosicions.isEmpty()){
            obPosicions.add(obstacle.getPosition());
        }

        while (!iterator.hasNext()) {
            Position o = iterator.next();
            if (o == null) {
                obPosicions.add(obstacle.getPosition());
            }
        }
        Field.drawObstacle(obstacle);
    }

    private void moveSnake() {

        Key k = Field.readInput();

        if (k != null) {
            switch (k.getKind()) {
                case ArrowUp:
                    snake.move(Direction.UP);
                    return;

                case ArrowDown:
                    snake.move(Direction.DOWN);
                    return;

                case ArrowLeft:
                    snake.move(Direction.LEFT);
                    return;

                case ArrowRight:
                    snake.move(Direction.RIGHT);
                    return;
            }
        }
        snake.move();
    }

    private void checkCollisions() throws InterruptedException {
        Position head = snake.getHead();
        Iterator<Position> iterator = snake.getBody().iterator() ;

        while (iterator.hasNext()){
            Position bodyPart = iterator.next();
            if(head.equals(bodyPart)) {
                snake.die();
            }
        }

        if (head.getCol() < 1 || head.getCol() >= 99) {
            snake.die();

        }

        if (head.getRow() < 1 || head.getRow() >= 24) {
            snake.die();

        }


        if(snake.getHead().equals(fruit.getPosition())){
            snake.increaseSize();
            generateFruit();
            delay--;
            //Field.clearObstacle(obstacle);
            generateObstacle();

        }

        if (obPosicions.contains(snake.getHead())) {
            snake.die();
        }
    }

    private void endGame() throws InterruptedException {

        while (true) {
            Thread.sleep(500);
            Key k = Field.readInput();

            if (k != null) {
                switch (k.getKind()){
                    case Enter -> {
                        restart = true;
                        return;
                    }
                    case Backspace -> {
                        Field.closeTerminal();
                    }
                }
            }
        }
    }


}
