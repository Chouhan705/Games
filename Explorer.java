@Override
public void start(Stage primaryStage) {
    // Initial welcome screen
    StackPane welcomeScreen = new StackPane();
    Label welcomeLabel = new Label("Jackpot Explorer Game");
    welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: lightgreen;");
    Button startButton = new Button("Start");
    startButton.setOnAction(e -> setupGame(primaryStage));
    welcomeScreen.getChildren().addAll(welcomeLabel, startButton);
    StackPane.setAlignment(welcomeLabel, javafx.geometry.Pos.TOP_CENTER);
    StackPane.setAlignment(startButton, javafx.geometry.Pos.CENTER);

    Scene welcomeScene = new Scene(welcomeScreen, 400, 450);
    primaryStage.setScene(welcomeScene);
    primaryStage.setTitle("Jackpot Explorer Game");
    primaryStage.show();
}
private void setupGame(Stage primaryStage) {
    // Initialize map and UI components
    for (int i = 0; i < GRID_SIZE; i++) {
        for (int j = 0; j < GRID_SIZE; j++) {
            map[i][j] = ' ';
        }
    }
    map[x][y] = '*';
    spawnJackpotPoints();

    // Set up grid
    gridPane = new GridPane();
    for (int i = 0; i < GRID_SIZE; i++) {
        for (int j = 0; j < GRID_SIZE; j++) {
            Rectangle cell = new Rectangle(40, 40, Color.LIGHTGRAY);
            gridCells[i][j] = cell;
            gridPane.add(cell, j, i);
        }
    }

    // Initialize root layout
    root = new BorderPane();
    root.setCenter(gridPane);
    root.setTop(tallyLabel);
    tallyLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
    BorderPane.setAlignment(tallyLabel, javafx.geometry.Pos.TOP_RIGHT);

    renderMap();

    Scene gameScene = new Scene(root, 400, 450);
    primaryStage.setScene(gameScene);
}
gameScene.setOnKeyPressed(event -> {
    if (jackpotCount < TOTAL_JACKPOT_POINTS) {
        if (event.getCode() == KeyCode.W && x > 0) {
            moveCharacter(-1, 0);
        } else if (event.getCode() == KeyCode.S && x < GRID_SIZE - 1) {
            moveCharacter(1, 0);
        } else if (event.getCode() == KeyCode.A && y > 0) {
            moveCharacter(0, -1);
        } else if (event.getCode() == KeyCode.D && y < GRID_SIZE - 1) {
            moveCharacter(0, 1);
        }
    }
});
private void spawnJackpotPoints() {
    Random random = new Random();
    while (jackpotPoints.size() < TOTAL_JACKPOT_POINTS) {
        int jackpotX = random.nextInt(GRID_SIZE);
        int jackpotY = random.nextInt(GRID_SIZE);

        if (map[jackpotX][jackpotY] == ' ' && !(jackpotX == x && jackpotY == y)) {
            map[jackpotX][jackpotY] = 'J';
            jackpotPoints.add(new int[]{jackpotX, jackpotY});
        }
    }
}


