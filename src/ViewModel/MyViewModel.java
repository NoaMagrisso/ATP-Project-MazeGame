package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer{

    private IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        //this.model.assignObserver(this); //Observe the Model for it's changes
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

    //TODO setPlayerRom/Col ????????

    public void stop() {
        model.stop();
    }

    public void updatePlayerLocation(KeyCode direction) {
        model.updatePlayerLocation(direction);
    }

    public Solution getSolution(){
        return model.getMazeSolution();
    }

    public void solveMaze(){
        model.solveMaze();
    }



}
