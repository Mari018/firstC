package academy.mindswap.gameobjects.fruit;

import academy.mindswap.field.Position;

public class Fruit {
    private Position fruit;

    public Fruit(Position fruit){
        this.fruit = fruit;
    }

    public Position getPosition() {
        return fruit;
    }


}
