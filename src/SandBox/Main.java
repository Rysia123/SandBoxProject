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
    static final int WIGHT =500;
    static  final int ROWS = HEIGHT/ TILE_SIZE;

    static  final int COLUMNS = WIGHT/ TILE_SIZE;

    Tile[][] tiles = new Tile[ROWS][COLUMNS];
    Type currentType =ROCK;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        AnchorPane background = new AnchorPane();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS ; c++) {
                Tile tile = new Tile(EMPTY);
                tiles[r][c] = tile;
                root.add(tile,c,r);


            }
        }
        Rectangle menu = new Rectangle(WIGHT-50,HEIGHT-499 , 50, 100);
        menu.setFill(Color.WHITE);

        Rectangle empty = new Rectangle(WIGHT-40,HEIGHT-498 , 20, 20);
        empty.setFill(Color.BLACK);
        empty.setOnMouseClicked(event -> {
            currentType = EMPTY;
        });

        Rectangle sand = new Rectangle(WIGHT-40,HEIGHT-478 , 20, 20);
        sand.setFill(Color.YELLOW);
        sand.setOnMouseClicked(event -> {
            currentType = SAND;
        });

        Rectangle rock = new Rectangle(WIGHT-40,HEIGHT-458 , 20, 20);
        rock.setFill(Color.GRAY);
        rock.setOnMouseClicked(event -> {
            currentType = ROCK;
        });
        Rectangle bomb = new Rectangle(WIGHT-40,HEIGHT-438 , 20, 20);
        bomb.setFill(new Color(36/225.0, 37/225.0, 38/225.0,1));
        bomb.setOnMouseClicked(event -> {
            currentType = BOMB;
        });
        Rectangle acid = new Rectangle(WIGHT-40,HEIGHT-418 , 20, 20);
        acid.setFill(Color.GREEN);
        acid.setOnMouseClicked(event -> {
            currentType = ACID;
        });




        background.getChildren().addAll( root, menu, empty,sand, rock, bomb, acid);



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
                    }
                    else if (currentType == ACID) {
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
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void logicBomb() {
        for (int r = ROWS-1; r >= 0; r--) {
            for (int c = 0; c < COLUMNS; c++) {
                if (tiles[r][c].type == BOMB) {
                    if (r + 1 < ROWS && tiles[r + 1][c].type == EMPTY) {
                        tiles[r][c].changeTypeTo(EMPTY);
                        tiles[r + 1][c].changeTypeTo(BOMB);
                    }
                    else {
                        tiles[r][c].changeTypeTo(EMPTY);
                        for (int i = 1; i < 5; i++) {
                            int count = 5;
                            while (count != 0) {
                                if (Math.random()<0.5){
                                    tiles[r+i+1][c+1].changeTypeTo(tiles[r+i][c].type);
                                    tiles[r+1][c+i+1].changeTypeTo(tiles[r][c+i].type);
                                    tiles[r+i+1][c+i+1].changeTypeTo(tiles[r+i][c+i].type);
                                    tiles[r-i-1][c-1].changeTypeTo(tiles[r-i][c].type);
                                    tiles[r-1][c-i-1].changeTypeTo(tiles[r][c-i].type);
                                    tiles[r-i-1][c-i-1].changeTypeTo(tiles[r-i][c-i].type);
                                    tiles[r+i+1][c-i-1].changeTypeTo(tiles[r+i][c-i].type);
                                    tiles[r-i-1][c+i+1].changeTypeTo(tiles[r-i][c+i].type);
                                }
                                else{
                                    tiles[r+i+1][c+1].changeTypeTo(tiles[r-i][c].type);
                                    tiles[r+1][c+i+1].changeTypeTo(tiles[r][c-i].type);
                                    tiles[r+i+1][c+i+1].changeTypeTo(tiles[r-i][c-i].type);
                                    tiles[r-i-1][c-1].changeTypeTo(tiles[r+i][c].type);
                                    tiles[r-1][c-i-1].changeTypeTo(tiles[r][c+i].type);
                                    tiles[r-i-1][c-i-1].changeTypeTo(tiles[r+i][c+i].type);
                                    tiles[r+i+1][c-i-1].changeTypeTo(tiles[r-i][c+i].type);
                                    tiles[r-i-1][c+i+1].changeTypeTo(tiles[r+i][c-i].type);
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
                            else if(r+1<ROWS&& c -1 >=0&&  tiles[r + 1][c+1].type == EMPTY){
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


