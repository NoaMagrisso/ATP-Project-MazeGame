package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer{

    private IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) { //TODO idooooooooooooooooooooo
        setChanged();
        notifyObservers(arg);
    }

    public Maze getMaze(){
        return this.model.getMaze();
    }

    public int getPlayerRow(){
        return this.model.getPlayerRow();
    }

    public int getPlayerCol(){
        return this.model.getPlayerCol();
    }

    public void generateMaze(int rows, int cols){
        this.model.generateMaze(rows, cols);
    }

    public void setMaze(Maze newMaze) {
        this.model.setMaze(newMaze);
    }

    public void stop() {
        model.stop();
    }

    public void updatePlayerLocation(KeyEvent keyEvent) {

        MovementDirection direction;
        switch (keyEvent.getCode()){
            case NUMPAD8 -> direction = MovementDirection.UP;
            case NUMPAD2 -> direction = MovementDirection.DOWN;
            case NUMPAD4 -> direction = MovementDirection.LEFT;
            case NUMPAD6 -> direction = MovementDirection.RIGHT;
            case NUMPAD7 -> direction = MovementDirection.UPLEFT;
            case NUMPAD9 -> direction = MovementDirection.UPRIGHT;
            case NUMPAD3 -> direction = MovementDirection.DOWNRIGHT;
            case NUMPAD1 -> direction = MovementDirection.DOWNLEFT;

            default -> {
                // no need to move the player...
                return;
            }
        }
        model.updatePlayerLocation(direction);
    }

    public Solution getSolution(){
        return model.getMazeSolution();
    }

    public void solveMaze(){
        model.solveMaze();
    }

}
