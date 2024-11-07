package com.example.game_2048;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;

import java.util.Random;


public class GameBoard extends Application {
    private int[][] square = new int[4][4];
    private Pane pane;
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene scene = firstScene();
        primaryStage.setTitle("Game Board");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private Scene firstScene(){
        Button start = new Button("Start The Game");
        start.setOnAction(e ->startGame());
        pane = new StackPane();
        pane.getChildren().add(start);
        return new Scene(pane, 200, 100);
    }

    private void startGame() {
        generateRandomNumbers();
        if (!checkbox()) {
            pane = getBoxes();
            Scene scene = new Scene(pane, 500, 500);
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.UP) {
                    moveUp();
                } else if (e.getCode() == KeyCode.DOWN) {
                    moveDown();
                } else if (e.getCode() == KeyCode.LEFT) {
                    moveLeft();
                } else if (e.getCode() == KeyCode.RIGHT) {
                    moveRight();
                }
            });
            primaryStage.setScene(scene);
        }
        else{
            pane = gameOver();
            Scene scene = new Scene(pane, 500, 500);
            primaryStage.setScene(scene);
        }
    }

    private GridPane gameOver(){
        GridPane grid = new GridPane();
        Text text = new Text("  Game Over, try again?");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        text.setFill(Color.BLACK);
        grid.setAlignment(Pos.CENTER);
        grid.add(text, 0, 0);
        Button yesBtn = new Button("Yes");
        yesBtn.setOnAction(e -> {
            square = new int[4][4];
            startGame();
        });
        Button noBtn = new Button("No");
        noBtn.setOnAction(e -> {
            primaryStage.close();
        });
        grid.add(yesBtn, 0, 1);
        grid.add(noBtn, 1, 1);
        return grid;
    }

    private boolean checkbox(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (square[i][j] == 0) { return false;}
                else if (square[i][j] == square[i][j+1]) {return false;}
                if (i == 1 || i == 2){
                    if (square[i][j] == square[i-1][j] || square[i][j] == square[i+1][j]){ return false;}
                }
            }
        }
        return true;
    }
    private void generateRandomNumbers(){
        Random ran = new Random();
        int zeros = 0;
        int biggest = square[0][0];
        for (int[] row : square) {
            for(int num: row){
                if (num == 0){
                    zeros++;
                }
                biggest = Math.max(num, biggest);
            }
        }
        int power = 0;
        while(biggest/2 != 0){
            power++;
            biggest /= 2;
        }
        double number = power - 2;
        int ranNumber = switch(power){
            case 0, 1, 2, 3, 4 -> 4;
            case 5 -> 8;
            default -> (int) Math.pow(2.0, number)/4;
        };
        int count = switch (zeros) {
            case 0 -> 2;
            case 1 -> 1;
            default -> 0;
        };
        while (count != 2) {
            int x = ran.nextInt(4), y = ran.nextInt(4);
            if (square[x][y] != 0){ continue;}
            else {
                if (ran.nextInt(1, 11) > 8) {
                    square[x][y] = ranNumber;
                } else {
                    square[x][y] = ranNumber / 2;
                }
            }
            count++;
        }
    }

    private void moveRight(){
        int column = square.length;
        int row = square[0].length;
        for (int i = 0; i < column; i++){
            for (int j = row - 2; j >= 0; j--){
                int rightWall = 3 - j;
                for (int z = 1; z <= rightWall; z++){
                    if (square[i][j+z-1] == square[i][j+z]){
                        square[i][j+z] += square[i][j+z-1];
                        square[i][j+z-1] = 0;
                    }
                    else if (square[i][j+z] == 0 && square[i][j+z-1] != 0){
                        square[i][j+z] = square[i][j+z-1];
                        square[i][j+z-1] = 0;
                    }
                }
            }
        }
        startGame();
    }

    private void moveLeft(){
        int column = square.length;
        int row = square[0].length;
        for (int i = 0; i < column; i++){
            for (int j = 1; j < row; j++){
                for (int z = 0; z < j; z++){
                    if (square[i][j-z-1] == square[i][j-z] && square[i][j-z] != 0){
                        square[i][j-z-1] += square[i][j-z];
                        square[i][j-z] = 0;
                    }
                    else if (square[i][j-z-1] == 0 && square[i][j-z] != 0){
                        square[i][j-z-1] = square[i][j-z];
                        square[i][j-z] = 0;
                    }
                }
            }
        }
        startGame();
    }

    private void moveDown(){
        for (int j = 0; j < 4; j++){
            for (int i = 2; i >= 0; i--){
                int step = 3 - i;
                for (int k = 0; k < step; k++){
                    if (square[i+k+1][j] == square[i+k][j] && square[i+k][j] != 0){
                        square[i+k+1][j] += square[i+k][j];
                        square[i+k][j] = 0;
                    }
                    else if (square[i+k+1][j] == 0 && square[i+k][j] != 0){
                        square[i+k+1][j] = square[i+k][j];
                        square[i+k][j] = 0;
                    }
                }
            }
        }
        startGame();
    }
    private void moveUp(){
        for (int j = 0; j < 4; j++){
            for (int i = 1; i < 4; i++){
                for (int k = 0; k < i; k++){
                    if (square[i-k-1][j] == square[i-k][j] && square[i-k][j] != 0){
                        square[i-k-1][j] += square[i-k][j];
                        square[i-k][j] = 0;
                    }
                    else if (square[i-k-1][j] == 0 && square[i-k][j] != 0){
                        square[i-k-1][j] = square[i-k][j];
                        square[i-k][j] = 0;
                    }
                }

            }
        }
        startGame();
    }
    private GridPane getBoxes(){
        GridPane boxes = new GridPane();
        boxes.setAlignment(Pos.CENTER);
        for (int row = 0; row < 4; row++){
            for (int col = 0; col < 4; col++){
                StackPane sPane = makeBorder(row, col);
                boxes.add(sPane, col, row);
            }
        }
        return boxes;
    }

    private StackPane makeBorder(int row, int col){
        StackPane sPane = new StackPane();
        Rectangle rectangle = new Rectangle(100, 100);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);
        sPane.getChildren().add(rectangle);
        Text getNumber = new Text(Integer.toString(square[row][col]));
        getNumber.setFont(Font.font("Verdana", FontWeight.BOLD, 35));
        sPane.getChildren().add(getNumber);
        double value = getColorValue(square[row][col]);
        Color color = adjustColor(value);
        sPane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, null)));
        return sPane;
    }

    private double getColorValue(int num){
        int power = 0;
        int stop = 1;
        while (stop != num){
            power++;
            stop *= 2;
        }
        return (double) power/100;
    }
    private Color adjustColor(double value){
        double r = Color.TAN.getRed() + value * (.7 - Color.TAN.getRed());
        double g = Color.TAN.getGreen() + value * (.7 - Color.TAN.getGreen());
        double b = Color.TAN.getBlue() + value * (.7 - Color.TAN.getBlue());

        return new Color(r,g,b, Color.TAN.getOpacity());
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
