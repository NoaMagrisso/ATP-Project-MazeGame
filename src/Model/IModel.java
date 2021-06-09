package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.Observer;

public interface IModel {

    void generateMaze(int rows, int cols);
    Maze getMaze();
    void setMaze(Maze newMaze);
    void updatePlayerLocation(MovementDirection direction);
    void solveMaze();
    int getPlayerRow();
    int getPlayerCol();
    void stop();
    Solution getMazeSolution();

    void saveMaze(String path);
}
