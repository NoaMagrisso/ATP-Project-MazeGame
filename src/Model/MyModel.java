package Model;

import Client.Client;
import IO.MyDecompressorInputStream;
import Server.Server;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
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
                        maze.setPositionToZero(getMaze().getGoalPosition());

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
        notifyObservers("maze generated");
        movePlayer(0, 0, null);
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
        notifyObservers("maze solved");
    }

    public Solution getMazeSolution() {
        //solveMaze();
        return mazeSolution;
    }

    @Override
    public void saveMaze(String path){
        try {
            File newFileToSave = new File(path);
            newFileToSave.createNewFile();
            StringBuilder  builder = new StringBuilder();
            builder.append(playerRow+"\n"+ playerCol + "\n" + maze.getGoalPosition().getRowIndex() + "\n" + maze.getGoalPosition().getColumnIndex()+"\n"+maze.getRows()+"\n"+maze.getCols()+"\n");
            for(int i = 0; i < maze.getRows(); i++) {
                for(int j = 0; j < maze.getCols(); j++) {
                    builder.append(String.valueOf(maze.getMatrix()[i][j]));
                    if(j < maze.getCols() - 1)
                        builder.append(",");
                }
                builder.append("\n");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadMaze(File loadFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(loadFile));
            int playerRowIdx = 0;
            int playerColIdx = 0;
            int goalMazeRow = 1;
            int goalMazeCol = 1;
            Position goalMaze = new Position(1,1);
            int rows = 2;
            int cols = 2;

            for (int i = 0 ; i < 6 ; i++) {
                String line = br.readLine();
                if (line != null) {
                    if (i == 0)
                        playerRowIdx = Integer.parseInt(line);
                    if (i == 1)
                        playerColIdx = Integer.parseInt(line);
                    if (i == 2)
                        goalMazeRow = Integer.parseInt(line);
                    if (i == 3)
                        goalMazeCol = Integer.parseInt(line);
                    if (i == 4)
                        rows = Integer.parseInt(line);
                    if (i == 5)
                        cols = Integer.parseInt(line);
                }
            }

            goalMaze = new Position(goalMazeRow, goalMazeCol);

            int[][] matrixForMaze = new int[rows][cols];
            String line = "";
            int rowIdx = 0;
            while ((line = br.readLine()) != null) {
                String[] colsOfMatrix = line.split(",");
                int colIdx = 0;
                for (String col : colsOfMatrix) {
                    matrixForMaze[rowIdx][colIdx] = Integer.parseInt(col);
                    colIdx++;
                }
                rowIdx++;
            }
            br.close();
            this.maze = new Maze(rows, cols);
            this.maze.setMatrix(matrixForMaze);
            this.maze.setEnd(goalMaze);

            this.playerRow = playerRowIdx;
            this.playerCol = playerColIdx;
            setChanged();
            notifyObservers("file loaded");
            System.out.println("Finish Loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



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
