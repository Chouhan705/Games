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
