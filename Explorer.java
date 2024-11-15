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

