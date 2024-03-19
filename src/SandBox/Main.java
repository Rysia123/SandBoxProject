package SandBox;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import static SandBox.Type.*;

public class Main extends Application {
    static final int TILE_SIZE = 10;
    static final int HEIGHT = 500;
    static final int WIGHT =500;
    static  final int ROWS = HEIGHT/ TILE_SIZE;

    static  final int COLUMNS = WIGHT/ TILE_SIZE;

    Tile[][] tiles = new Tile[ROWS][COLUMNS];
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS ; c++) {
                Tile tile = new Tile(EMPTY);
                tiles[r][c] = tile;
                root.add(tile,r,c);


            }
        }

        tiles[1][1].changeTypeTo(SAND);

        Scene scene = new Scene(root, WIGHT, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}