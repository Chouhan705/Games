
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application
{

    private static final int GRID_SIZE = 10;
    private static final int TOTAL_JACKPOT_POINTS = 5;

    private int x = GRID_SIZE / 2;
    private int y = GRID_SIZE / 2;
    private int jackpotCount = 0;

    private char[][] map = new char[GRID_SIZE][GRID_SIZE];
    private Rectangle[][] gridCells = new Rectangle[GRID_SIZE][GRID_SIZE];
    private List<int[]> jackpotPoints = new ArrayList<>();
    private Label tallyLabel = new Label("0/" + TOTAL_JACKPOT_POINTS);

    private BorderPane root;
    private GridPane gridPane;
    private Label winLabel = new Label("You Win!");
    private Button playAgainButton = new Button("Play Again");

    @Override
    public void start(Stage primaryStage)
    {
        // Initial welcome screen
        StackPane welcomeScreen = new StackPane();
        Label welcomeLabel = new Label("Jackpot Explorer Game");
        welcomeLabel.setStyle("-fx-font-size: 35px; -fx-font-weight: bold; -fx-text-fill: lightgreen; -fx-font-style: Times New Roman;");
        Button startButton = new Button("Start");

        // Game controls label
        Label controlsLabel = new Label("   [Controls]\n" +
                "\t   ^\n" +
                "\t  W\n" +
                "<  A\t\tD  >\n" +
                "\t  S\n" +
                "\t  v\n");
        controlsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: red; -fx-font-style: Times New Roman;");

        startButton.setOnAction(e -> setupGame(primaryStage));

        // Layout for welcome screen
        welcomeScreen.getChildren().addAll(welcomeLabel, startButton, controlsLabel);
        StackPane.setAlignment(welcomeLabel, javafx.geometry.Pos.TOP_CENTER);
        StackPane.setAlignment(startButton, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(controlsLabel, javafx.geometry.Pos.BOTTOM_CENTER);

        Scene welcomeScene = new Scene(welcomeScreen, 400, 450);
        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("Jackpot Explorer Game");
        primaryStage.show();
    }

    private void setupGame(Stage primaryStage)
    {
        // Initialize map and UI components
        for (int i = 0; i < GRID_SIZE; i++)
        {
            for (int j = 0; j < GRID_SIZE; j++)
            {
                map[i][j] = ' ';
            }
        }
        map[x][y] = '*';
        spawnJackpotPoints();

        // Set up grid
        gridPane = new GridPane();
        for (int i = 0; i < GRID_SIZE; i++)
        {
            for (int j = 0; j < GRID_SIZE; j++)
            {
                Rectangle cell = new Rectangle(40, 40, Color.LIGHTGRAY);
                gridCells[i][j] = cell;
                gridPane.add(cell, j, i);
            }
        }

        // Initialize root layout
        root = new BorderPane();
        root.setCenter(gridPane);
        root.setTop(tallyLabel);
        tallyLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-style: Times New Roman;");
        BorderPane.setAlignment(tallyLabel, javafx.geometry.Pos.TOP_CENTER);

        renderMap();

        Scene gameScene = new Scene(root, 400, 450);
        primaryStage.setScene(gameScene);

        gameScene.setOnKeyPressed(event -> {
            if (jackpotCount < TOTAL_JACKPOT_POINTS)
            {
                if (event.getCode() == KeyCode.W && x > 0)
                {
                    moveCharacter(-1, 0);
                }
                else if (event.getCode() == KeyCode.S && x < GRID_SIZE - 1)
                {
                    moveCharacter(1, 0);
                }
                else if (event.getCode() == KeyCode.A && y > 0)
                {
                    moveCharacter(0, -1);
                }
                else if (event.getCode() == KeyCode.D && y < GRID_SIZE - 1)
                {
                    moveCharacter(0, 1);
                }
            }
        });
    }

    private void spawnJackpotPoints()
    {
        Random random = new Random();
        while (jackpotPoints.size() < TOTAL_JACKPOT_POINTS)
        {
            int jackpotX = random.nextInt(GRID_SIZE);
            int jackpotY = random.nextInt(GRID_SIZE);

            if (map[jackpotX][jackpotY] == ' ' && !(jackpotX == x && jackpotY == y))
            {
                map[jackpotX][jackpotY] = 'J';
                jackpotPoints.add(new int[]{jackpotX, jackpotY});
            }
        }
    }

    private void moveCharacter(int dx, int dy)
    {
        map[x][y] = '#';
        x += dx;
        y += dy;

        if (map[x][y] == 'J')
        {
            jackpotCount++;
            tallyLabel.setText(jackpotCount + "/" + TOTAL_JACKPOT_POINTS);
            map[x][y] = '*';

            if (jackpotCount == TOTAL_JACKPOT_POINTS)
            {
                tallyLabel.setText("All Jackpots Found!");
                animateRowWiseTraversal();
            }
        }
        else
        {
            map[x][y] = '*';
        }

        renderMap();
    }

    private void renderMap()
    {
        for (int i = 0; i < GRID_SIZE; i++)
        {
            for (int j = 0; j < GRID_SIZE; j++)
            {
                if (map[i][j] == '*')
                {
                    gridCells[i][j].setFill(Color.GREEN);
                }
                else if (map[i][j] == '#')
                {
                    gridCells[i][j].setFill(Color.DARKGRAY);
                }
                else if (map[i][j] == 'J')
                {
                    gridCells[i][j].setFill(Color.GOLD);
                }
                else
                {
                    gridCells[i][j].setFill(Color.LIGHTGRAY);
                }
            }
        }
    }

    private void animateRowWiseTraversal()
    {
        List<int[]> rowWisePath = generateRowWisePath();

        for (int i = 0; i < rowWisePath.size(); i++)
        {
            int[] pos = rowWisePath.get(i);
            int delay = i;

            PauseTransition pause = new PauseTransition(Duration.millis(30 * delay));
            pause.setOnFinished(event -> {
                map[x][y] = '#';
                x = pos[0];
                y = pos[1];
                map[x][y] = '*';
                renderMap();

                if (delay == rowWisePath.size() - 1)
                {
                    showWinScreen();
                }
            });
            pause.play();
        }
    }

    private List<int[]> generateRowWisePath()
    {
        List<int[]> path = new ArrayList<>();
        for (int row = 0; row < GRID_SIZE; row++)
        {
            for (int col = 0; col < GRID_SIZE; col++)
            {
                path.add(new int[]{row, col});
            }
        }
        return path;
    }

    private void showWinScreen()
    {
        winLabel.setStyle("-fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: gold; -fx-font-style: Times New Roman");
        playAgainButton.setOnAction(e -> resetGame());

        BorderPane winPane = new BorderPane();
        winPane.setCenter(winLabel);
        winPane.setBottom(playAgainButton);
        BorderPane.setAlignment(playAgainButton, Pos.BOTTOM_CENTER);

        root.setCenter(winPane);
    }

    private void resetGame()
    {
        jackpotCount = 0;
        tallyLabel.setText(jackpotCount + "/" + TOTAL_JACKPOT_POINTS);
        x = GRID_SIZE / 2;
        y = GRID_SIZE / 2;
        jackpotPoints.clear();

        setupGame((Stage) root.getScene().getWindow());
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
