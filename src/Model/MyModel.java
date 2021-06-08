package Model;

import Client.Client;
import IO.MyDecompressorInputStream;
import Server.Server;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import Client.IClientStrategy;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyModel extends Observable implements IModel{

    private Maze maze;
    private int playerRow;
    private int playerCol;
    private Solution mazeSolution;

    private Server mazeGenerateServer;
    private Server solveMazeServer;

    public MyModel() {
        this.playerRow = 0;
        this.playerCol = 0;
        this.mazeSolution = null;
        this.mazeGenerateServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        this.solveMazeServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());

        this.mazeGenerateServer.start();
        this.solveMazeServer.start();
    }


    public void stop() {
        this.mazeGenerateServer.stop();
        this.solveMazeServer.stop();
    }

    @Override
    public void generateMaze(int rows, int cols) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, cols};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        int totalBytes = rows * cols + 12;
                        byte[] decompressedMaze = new byte[totalBytes];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers(maze);
    }


    private void movePlayer(int row, int col, MovementDirection dirc){
        this.playerRow = row;
        this.playerCol = col;
        setChanged();
        notifyObservers("player moved");
    }

    @Override
    public Maze getMaze() {
        return this.maze;
    }

    @Override
    public void setMaze(Maze newMaze) {
        this.maze = newMaze;
    }

    public void solveMaze() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        mazeSolution = (Solution)fromServer.readObject();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers(mazeSolution);
    }

    public Solution getMazeSolution() {
        return mazeSolution;
    }

//    @Override
//    public void updatePlayerLocation(KeyCode direction) {
//        switch (direction) {
//            case NUMPAD8 -> {
//                if ((playerRow > 0) && ((this.maze.getMatrix()[playerRow - 1][playerCol]) != 1))
//                    movePlayer(playerRow - 1, playerCol, direction);
//            }
//            case NUMPAD2 -> {
//                if ((playerRow < maze.getRows() - 1) && ((this.maze.getMatrix()[playerRow + 1][playerCol]) != 1))
//                    movePlayer(playerRow + 1, playerCol, direction);
//            }
//            case NUMPAD4 -> {
//                if ((playerCol > 0) && ((this.maze.getMatrix()[playerRow][playerCol - 1]) != 1))
//                    movePlayer(playerRow, playerCol - 1, direction);
//            }
//            case NUMPAD6 -> {
//                if ((playerCol < maze.getCols() - 1) && ((this.maze.getMatrix()[playerRow][playerCol + 1]) != 1))
//                    movePlayer(playerRow, playerCol + 1, direction);
//            }
//            case NUMPAD7 -> {
//                if ((playerRow > 0) && (playerCol > 0) && ((this.maze.getMatrix()[playerRow - 1][playerCol - 1]) != 1))
//                    if (((this.maze.getMatrix()[playerRow - 1][playerCol]) != 1) || ((this.maze.getMatrix()[playerRow][playerCol - 1]) != 1))
//                        movePlayer(playerRow - 1, playerCol - 1, direction);
//            }
//            case NUMPAD9 -> {
//                if ((playerRow > 0) && (playerCol < maze.getCols() - 1) && ((this.maze.getMatrix()[playerRow - 1][playerCol + 1]) != 1))
//                    if (((this.maze.getMatrix()[playerRow - 1][playerCol]) != 1) || ((this.maze.getMatrix()[playerRow][playerCol + 1]) != 1))
//                        movePlayer(playerRow - 1, playerCol + 1, direction);
//            }
//            case NUMPAD3 -> {
//                if ((playerRow < maze.getRows() - 1) && (playerCol < maze.getCols() - 1) && ((this.maze.getMatrix()[playerRow + 1][playerCol + 1]) != 1))
//                    if (((this.maze.getMatrix()[playerRow + 1][playerCol]) != 1) || ((this.maze.getMatrix()[playerRow][playerCol + 1]) != 1))
//                        movePlayer(playerRow + 1, playerCol + 1, direction);
//            }
//            case NUMPAD1 -> {
//                if ((playerRow < maze.getRows() - 1) && (playerCol > 0) && ((this.maze.getMatrix()[playerRow + 1][playerCol - 1]) != 1))
//                    if (((this.maze.getMatrix()[playerRow + 1][playerCol]) != 1) || ((this.maze.getMatrix()[playerRow][playerCol - 1]) != 1))
//                        movePlayer(playerRow + 1, playerCol - 1, direction);
//            }
//        }
//        if ((playerRow == maze.getGoalPosition().getRowIndex()) && (playerCol == maze.getGoalPosition().getColumnIndex())) {
//            //TODO
//        }
//    }


    public void updatePlayerLocation(MovementDirection direction) {
        switch (direction) {
            case UP -> {
                if ((playerRow > 0) && ((this.maze.getMatrix()[playerRow - 1][playerCol]) != 1))
                    movePlayer(playerRow - 1, playerCol, direction);
            }
            case DOWN -> {
                if ((playerRow < maze.getRows() - 1) && ((this.maze.getMatrix()[playerRow + 1][playerCol]) != 1))
                    movePlayer(playerRow + 1, playerCol, direction);
            }
            case LEFT -> {
                if ((playerCol > 0) && ((this.maze.getMatrix()[playerRow][playerCol - 1]) != 1))
                    movePlayer(playerRow, playerCol - 1, direction);
            }
            case RIGHT -> {
                if ((playerCol < maze.getCols() - 1) && ((this.maze.getMatrix()[playerRow][playerCol + 1]) != 1))
                    movePlayer(playerRow, playerCol + 1, direction);
            }
            case UPLEFT -> {
                if ((playerRow > 0) && (playerCol > 0) && ((this.maze.getMatrix()[playerRow - 1][playerCol - 1]) != 1))
                    if (((this.maze.getMatrix()[playerRow - 1][playerCol]) != 1) || ((this.maze.getMatrix()[playerRow][playerCol - 1]) != 1))
                        movePlayer(playerRow - 1, playerCol - 1, direction);
            }
            case UPRIGHT -> {
                if ((playerRow > 0) && (playerCol < maze.getCols() - 1) && ((this.maze.getMatrix()[playerRow - 1][playerCol + 1]) != 1))
                    if (((this.maze.getMatrix()[playerRow - 1][playerCol]) != 1) || ((this.maze.getMatrix()[playerRow][playerCol + 1]) != 1))
                        movePlayer(playerRow - 1, playerCol + 1, direction);
            }
            case DOWNRIGHT -> {
                if ((playerRow < maze.getRows() - 1) && (playerCol < maze.getCols() - 1) && ((this.maze.getMatrix()[playerRow + 1][playerCol + 1]) != 1))
                    if (((this.maze.getMatrix()[playerRow + 1][playerCol]) != 1) || ((this.maze.getMatrix()[playerRow][playerCol + 1]) != 1))
                        movePlayer(playerRow + 1, playerCol + 1, direction);
            }
            case DOWNLEFT -> {
                if ((playerRow < maze.getRows() - 1) && (playerCol > 0) && ((this.maze.getMatrix()[playerRow + 1][playerCol - 1]) != 1))
                    if (((this.maze.getMatrix()[playerRow + 1][playerCol]) != 1) || ((this.maze.getMatrix()[playerRow][playerCol - 1]) != 1))
                        movePlayer(playerRow + 1, playerCol - 1, direction);
            }
        }
//        if ((playerRow == maze.getGoalPosition().getRowIndex()) && (playerCol == maze.getGoalPosition().getColumnIndex())) {
//            //TODO
//        }
    }

    @Override
    public int getPlayerRow() {
        return this.playerRow;
    }

    @Override
    public int getPlayerCol() {
        return playerCol;
    }

}
