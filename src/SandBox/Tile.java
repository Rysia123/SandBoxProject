package SandBox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Tile extends Rectangle {
    Type type;

    public Tile(Type type) {
        super(Main.TILE_SIZE, Main.TILE_SIZE);
        changeTypeTo(type);
    }
    public void changeTypeTo (Type type){
        this.type = type;
        if(type == Type.EMPTY){
            setFill(Color.BLACK);
        }
        else if (type == Type.SAND){
            setFill( new Color(
                    (Math.random()*30+220)/255.0, (Math.random()*30+220)/255.0, 100/255.0,1));
        }
    }
}
