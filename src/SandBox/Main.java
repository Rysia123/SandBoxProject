package SandBox;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


import static SandBox.Type.*;

public class Main extends Application {
    static final int TILE_SIZE = 10;
    static final int HEIGHT = 500;
    static final int WIGHT = 500;
    public static final int ROWS = HEIGHT / TILE_SIZE;

    public static final int COLUMNS = WIGHT / TILE_SIZE;

    Tile[][] tiles = new Tile[ROWS][COLUMNS];
    Type currentType = ROCK;

    public static void  main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        AnchorPane background = new AnchorPane();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                Tile tile = new Tile(EMPTY);
                tiles[r][c] = tile;
                root.add(tile, c, r);


            }
        }
        Rectangle menu = new Rectangle(WIGHT - 50, HEIGHT - 499, 50, 200);
        menu.setFill(Color.WHITE);

        Rectangle empty = new Rectangle(WIGHT - 40, HEIGHT - 498, 20, 20);
        empty.setFill(Color.BLACK);
        empty.setOnMouseClicked(event -> {
            currentType = EMPTY;
        });

        Rectangle sand = new Rectangle(WIGHT - 40, HEIGHT - 478, 20, 20);
        sand.setFill(Color.YELLOW);
        sand.setOnMouseClicked(event -> {
            currentType = SAND;
        });

        Rectangle rock = new Rectangle(WIGHT - 40, HEIGHT - 458, 20, 20);
        rock.setFill(Color.GRAY);
        rock.setOnMouseClicked(event -> {
            currentType = ROCK;
        });
        Rectangle bomb = new Rectangle(WIGHT - 40, HEIGHT - 438, 20, 20);
        bomb.setFill(new Color(36 / 225.0, 37 / 225.0, 38 / 225.0, 1));
        bomb.setOnMouseClicked(event -> {
            currentType = BOMB;
        });
        Rectangle acid = new Rectangle(WIGHT - 40, HEIGHT - 418, 20, 20);
        acid.setFill(Color.GREEN);
        acid.setOnMouseClicked(event -> {
            currentType = ACID;
        });

        Rectangle water = new Rectangle(WIGHT - 40, HEIGHT - 398, 20, 20);
        water.setFill(Color.BLUE);
        water.setOnMouseClicked(event -> {
            currentType = WATER;
        });


        background.getChildren().addAll(root, menu, empty, sand, rock, bomb, acid,water);


        Scene scene = new Scene(background, WIGHT, HEIGHT);

        scene.setOnMouseDragged(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (currentType != BOMB) {
                    double x = event.getX();
                    double y = event.getY();
                    int row = (int) (y / TILE_SIZE);
                    int column = (int) (x / TILE_SIZE);
                    if (currentType == SAND && tiles[row][column].type == EMPTY) {
                        tiles[row][column].changeTypeTo(currentType);
                    } else if (currentType == EMPTY) {
                        tiles[row][column].changeTypeTo(currentType);
                    } else if (currentType == ROCK) {
                        tiles[row][column].changeTypeTo(currentType);
                    } else if (currentType == ACID) {
                        tiles[row][column].changeTypeTo(currentType);
                    } else if (currentType == GAS) {
                        tiles[row][column].changeTypeTo(currentType);
                    } else if (currentType == WATER) {
                        tiles[row][column].changeTypeTo(currentType);
                    }
                }

            }
        });

        scene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (currentType == BOMB) {
                    double x = event.getX();
                    double y = event.getY();
                    int row = (int) (y / TILE_SIZE);
                    int column = (int) (x / TILE_SIZE);
                    tiles[row][column].changeTypeTo(currentType);


                }
            }
        });


        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            logicSand();
            logicRock();
            logicBomb();
            logicAcid();
            logicGas();
            logicWater();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void logicWater() {
        for (int r = ROWS - 1; r >= 0; r--) {
            for (int c = 0; c < COLUMNS; c++) {

                if (tiles[r][c].type == WATER) {
                    tiles[r][c].life --;
                    if(tiles[r][c]. life<= 0){
                        tiles[r][c].changeTypeTo(EMPTY);
                        continue;
                    }

                    if (r + 1 < ROWS && tiles[r + 1][c].type == EMPTY) {
                        tiles[r + 1][c].changeTypeTo(WATER);
                    }
                    else if (r + 1 < ROWS && tiles[r + 1][c].type == WATER){
                        if (Math.random() < 0.5) {
                            //check left then right
                            if (r + 1 < ROWS && c - 1 >=0 && tiles[r + 1][c-1].type == EMPTY) {
                                tiles[r + 1][c - 1].changeTypeTo(WATER);
                            }
                            else if (r + 1 < ROWS && c + 1 < COLUMNS&&  tiles[r + 1][c+1].type == EMPTY) {
                                tiles[r + 1][c + 1].changeTypeTo(WATER);
                            }
                        }
                        else {
                            if (r + 1 < ROWS && c +1 <COLUMNS &&  tiles[r + 1][c+1].type == EMPTY) {
                                tiles[r + 1][c + 1].changeTypeTo(WATER);
                            }
                            else if(r+1<ROWS&& c -1 >=0&&  tiles[r + 1][c-1].type == EMPTY){

                                tiles[r + 1][c - 1].changeTypeTo(WATER);
                            }
                        }

                    }


                }

            }
        }

    }

    private void logicGas() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                if (tiles[r][c].type == GAS) {
                    tiles[r][c].life--;
                    if(tiles[r][c]. life<= 0){
                        tiles[r][c].changeTypeTo(EMPTY);
                        continue;
                    }
                    double n = Math.random();
                    if(n<0.3){
                    if (r - 1 >= 0 && tiles[r - 1][c].type == EMPTY) {
                        tiles[r - 1][c].changeTypeTo(GAS);
                    }
                    else if(n<0.3 && n>0.6){
                        if (r - 1 >0 && c - 1 >=0 && tiles[r - 1][c-1].type == EMPTY) {
                            tiles[r + 1][c - 1].changeTypeTo(GAS);
                        }
                        else if (r -1 >0 && c + 1 < COLUMNS&&  tiles[r - 1][c+1].type == EMPTY) {
                            tiles[r - 1][c + 1].changeTypeTo(GAS);
                        }

                    }
                    else{
                        if (r -1>0 && c +1 <COLUMNS &&  tiles[r - 1][c+1].type == EMPTY) {
                            tiles[r - 1][c + 1].changeTypeTo(GAS);
                        }
                        else if(r-1>0&& c -1 >=0&&  tiles[r - 1][c-1].type == EMPTY){

                            tiles[r - 1][c - 1].changeTypeTo(GAS);
                        }

                    }


                    }
                }
            }
        }
    }


    private void logicAcid() {
        for (int r = ROWS - 1; r >= 0; r--) {
            for (int c = 0; c < COLUMNS; c++) {

                if (tiles[r][c].type == ACID) {
                    tiles[r][c].life --;
                    if(tiles[r][c]. life<= 0){
                        tiles[r][c].changeTypeTo(EMPTY);
                        continue;
                    }

                    if (r + 1 < ROWS && tiles[r + 1][c].type == EMPTY) {
                        tiles[r + 1][c].changeTypeTo(ACID);
                    }
                    else if (r + 1 < ROWS && tiles[r + 1][c].type == ACID){
                        if (Math.random() < 0.5) {
                            //check left then right
                            if (r + 1 < ROWS && c - 1 >=0 && tiles[r + 1][c-1].type == EMPTY) {
                                tiles[r + 1][c - 1].changeTypeTo(ACID);
                            }
                            else if (r + 1 < ROWS && c + 1 < COLUMNS&&  tiles[r + 1][c+1].type == EMPTY) {
                                tiles[r + 1][c + 1].changeTypeTo(ACID);
                            }
                        }
                        else {
                            if (r + 1 < ROWS && c +1 <COLUMNS &&  tiles[r + 1][c+1].type == EMPTY) {
                                tiles[r + 1][c + 1].changeTypeTo(ACID);
                            }
                            else if(r+1<ROWS&& c -1 >=0&&  tiles[r + 1][c-1].type == EMPTY){

                                tiles[r + 1][c - 1].changeTypeTo(ACID);
                            }
                        }

                    }
                    else if(r + 1 < ROWS && tiles[r + 1][c].type != ACID && tiles[r + 1][c-1].type != EMPTY){
                        tiles[r][c].changeTypeTo(EMPTY);
                        tiles[r + 1][c - 1].changeTypeTo(GAS);
                    }

                }

            }
        }



    }

    private void logicBomb() {

        for (int r = ROWS - 1; r >= 0; r--) {
            for (int c = 0; c < COLUMNS; c++) {
                if (tiles[r][c].type == BOMB) {
                    if (r + 1 < ROWS && tiles [r +1][c].type == EMPTY ) {
                        tiles[r][c].changeTypeTo(EMPTY);
                        tiles[r + 1][c].changeTypeTo(BOMB);
                    }
                    else if (r+1 < ROWS){
                        tiles[r][c].changeTypeTo(EMPTY);
                        for (int i = 1; i < 5; i++) {
                            int count = 5;
                            while (count != 0) {
                                if (Math.random() < 0.5 && r + i + 1 < ROWS && r - i - 1 > 0 && r + i < ROWS && r - 1 > 0 && c + i + 1 < COLUMNS && c - i - 1 > 0 && c + i < COLUMNS && r - 1 > 0) {
                                    tiles[r + i + 1][c + 1].changeTypeTo(tiles[r + i][c].type);
                                    tiles[r + 1][c + i + 1].changeTypeTo(tiles[r][c + i].type);
                                    tiles[r + i + 1][c + i + 1].changeTypeTo(tiles[r + i][c + i].type);
                                    tiles[r - i - 1][c - 1].changeTypeTo(tiles[r - i][c].type);
                                    tiles[r - 1][c - i - 1].changeTypeTo(tiles[r][c - i].type);
                                    tiles[r - i - 1][c - i - 1].changeTypeTo(tiles[r - i][c - i].type);
                                    tiles[r + i + 1][c - i - 1].changeTypeTo(tiles[r + i][c - i].type);
                                    tiles[r - i - 1][c + i + 1].changeTypeTo(tiles[r - i][c + i].type);
                                } else if (r + i + 1 < ROWS && r - i - 1 > 0 && r + i < ROWS && r - 1 > 0 && c + i + 1 < COLUMNS && c - i - 1 > 0 && c + i < COLUMNS && r - 1 > 0) {
                                    tiles[r + i + 1][c + 1].changeTypeTo(tiles[r - i][c].type);
                                    tiles[r + 1][c + i + 1].changeTypeTo(tiles[r][c - i].type);
                                    tiles[r + i + 1][c + i + 1].changeTypeTo(tiles[r - i][c - i].type);
                                    tiles[r - i - 1][c - 1].changeTypeTo(tiles[r + i][c].type);
                                    tiles[r - 1][c - i - 1].changeTypeTo(tiles[r][c + i].type);
                                    tiles[r - i - 1][c - i - 1].changeTypeTo(tiles[r + i][c + i].type);
                                    tiles[r + i + 1][c - i - 1].changeTypeTo(tiles[r - i][c + i].type);
                                    tiles[r - i - 1][c + i + 1].changeTypeTo(tiles[r + i][c - i].type);
                                }

                                count--;
                            }


                            tiles[r + i][c].changeTypeTo(EMPTY);
                            tiles[r][c + i].changeTypeTo(EMPTY);
                            tiles[r + i][c + i].changeTypeTo(EMPTY);
                            tiles[r - i][c].changeTypeTo(EMPTY);
                            tiles[r - i][c - i].changeTypeTo(EMPTY);
                            tiles[r + i][c - i].changeTypeTo(EMPTY);
                            tiles[r][c - i].changeTypeTo(EMPTY);
                            tiles[r - i][c + i].changeTypeTo(EMPTY);

                        }
                    }
                }
            }
        }
    }




    private void logicRock() {
    }




    private void logicSand() {
        for (int r = ROWS-1; r >= 0; r--) {
            for (int c = 0; c < COLUMNS; c++) {
                if (tiles[r][c].type == SAND) {
                    //check down
                    if (r + 1 < ROWS && tiles[r + 1][c].type == EMPTY) {
                        tiles[r][c].changeTypeTo(EMPTY);
                        tiles[r + 1][c].changeTypeTo(SAND);
                    }
                    else {
                        if (Math.random() < 0.5) {
                            //check left then right
                            if (r + 1 < ROWS && c - 1 >=0 && tiles[r + 1][c-1].type == EMPTY) {
                                tiles[r][c].changeTypeTo(EMPTY);
                                tiles[r + 1][c - 1].changeTypeTo(SAND);
                            }
                            else if (r + 1 < ROWS && c + 1 < COLUMNS&&  tiles[r + 1][c+1].type == EMPTY) {
                                tiles[r][c].changeTypeTo(EMPTY);
                                tiles[r + 1][c + 1].changeTypeTo(SAND);
                            }
                        }
                        else {
                            //check right then left
                            if (r + 1 < ROWS && c +1 <COLUMNS &&  tiles[r + 1][c+1].type == EMPTY) {
                                tiles[r][c].changeTypeTo(EMPTY);
                                tiles[r + 1][c + 1].changeTypeTo(SAND);
                            }
                            else if(r+1<ROWS&& c -1 >=0&&  tiles[r + 1][c-1].type == EMPTY){
                                tiles[r][c].changeTypeTo(EMPTY);
                                tiles[r + 1][c - 1].changeTypeTo(SAND);
                            }
                        }
                    }



                }
            }
        }
    }
}




