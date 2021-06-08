package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.property.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {

    public DoubleProperty cellHeight = new SimpleDoubleProperty(1);
    public DoubleProperty cellWidth = new SimpleDoubleProperty(1);
    private int[][] mazeMatrix;
    private Solution solution;
    private int playerRow = 0;
    private int playerCol = 0;
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    private Position goal;


    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        draw();
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        draw();
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String imageFileNameWallProperty() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public void drawMaze(Maze maze) {
        maze.print();
        this.goal = maze.getGoalPosition();
        this.mazeMatrix = maze.getMatrix();
        draw();
    }

    private void draw() {

        if (mazeMatrix != null) {

            try {
                Image wallImage = new Image(new FileInputStream("resources\\images\\iceWall.jpg"));
                Image goalImage = new Image(new FileInputStream("resources\\images\\goalPicJerry.png"));
                Image startImage = new Image(new FileInputStream("resources\\images\\tomPic.png"));
                Image wayImage = new Image(new FileInputStream("resources\\images\\wayPic.png"));
                double canvasHeight = getHeight();
                double canvasWidth = getWidth();
                int rows = mazeMatrix.length;
                int cols = mazeMatrix[0].length;

                double cellHeight = canvasHeight / rows;
                double cellWidth = canvasWidth / cols;
                GraphicsContext graphicsContext = getGraphicsContext2D();
                graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
                drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols, wallImage, goalImage, startImage, wayImage);
                drawPlayer(graphicsContext, cellHeight, cellWidth, startImage);
            } catch (Exception e) {
                e.printStackTrace();
            }


            //if (solution != null)
            //    drawSolution(graphicsContext, cellHeight, cellWidth);
        }
    }

    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        // need to be implemented
        System.out.println("drawing solution...");
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols, Image wallImage, Image goalImage, Image startImage, Image wayImage) {


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
//                if (i == 0 && j == 0) {
//                    graphicsContext.drawImage(startImage, 0, 0, cellWidth, cellHeight); //TODO if the image was not found
//
//                }
                if (i == this.goal.getRowIndex() && j == this.goal.getColumnIndex()) {
                    graphicsContext.drawImage(goalImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight); //TODO if the image was not found
                }
                else if (mazeMatrix[i][j] == 1 && (i != this.goal.getRowIndex() || j != this.goal.getColumnIndex())) {
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight); //TODO if the image was not found
                }
                else {
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    graphicsContext.drawImage(wayImage, x, y, cellWidth, cellHeight); //TODO if the image was not found
                }

            }
        }
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth, Image player) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.drawImage(player, x, y, cellWidth, cellHeight);
    }
}
