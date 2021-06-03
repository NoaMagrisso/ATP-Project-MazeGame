package Model;

import algorithms.mazeGenerators.Maze;
import javafx.scene.input.KeyCode;
import java.util.Observer;

public interface IModel {

    void generateMaze(int rows, int cols);
    Maze getMaze();
    void setMaze(Maze newMaze);
    void updatePlayerLocation(KeyCode direction);
    void solveMaze();
    int getPlayerRow();
    int getPlayerCol();
}
