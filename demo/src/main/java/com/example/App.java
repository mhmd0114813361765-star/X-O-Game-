package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private boolean xTurn = true;
    private Button[][] cells = new Button[3][3];

    private boolean checkRows() {

    for (int row = 0; row < 3; row++) {

        if (!cells[row][0].getText().isEmpty()
                && cells[row][0].getText().equals(cells[row][1].getText())
                && cells[row][1].getText().equals(cells[row][2].getText())) {

            return true;
        }
    }

    return false;
}

private boolean checkColumns() {

    for (int col = 0; col < 3; col++) {

        if (!cells[0][col].getText().isEmpty()
                && cells[0][col].getText().equals(cells[1][col].getText())
                && cells[1][col].getText().equals(cells[2][col].getText())) {

            return true;
        }
    }

    return false;
}


private boolean checkDiagonals() {

    if (!cells[0][0].getText().isEmpty()
            && cells[0][0].getText().equals(cells[1][1].getText())
            && cells[1][1].getText().equals(cells[2][2].getText())) {
        return true;
    }

    if (!cells[0][2].getText().isEmpty()
            && cells[0][2].getText().equals(cells[1][1].getText())
            && cells[1][1].getText().equals(cells[2][0].getText())) {
        return true;
    }

    return false;
}

    @Override
    public void start(Stage stage) throws IOException {
        // scene = new Scene(loadFXML("primary"), 640, 480);
      
        Label title = new Label("TIC TAC TOE");
        title.setTextFill(Color.WHITE);
        title.setFont(new Font(30));

        Label status = new Label("Player X Turn");
        status.setFont(new Font(20));
        status.setTextFill(Color.BLUE);

        Button restart = new Button("Restart");

        GridPane board = new GridPane();
        board.setHgap(5);
        board.setVgap(5);
        board.setAlignment(Pos.CENTER);

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                Button cell = new Button();
                cell.setStyle(
                    "-fx-background-color: #253854;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 0;"+
                        "-fx-border-width: 1;"+
                        "-fx-border-color: #e0e4ea;" +
                        "-fx-border-radius: 3;" +
                        "-fx-background-radius: 3;"
                );

                cell.setPrefSize(80, 80);
                cell.setFont(new Font(25));

                cells[row][col] = cell;

                board.add(cell, col, row);

                cell.setOnAction(e -> {

                    if (cell.getText().isEmpty()) {

                        if (xTurn) {
                            cell.setText("X");
                            cell.setTextFill(Color.BLUE);
                        } else {
                            cell.setText("O");
                            cell.setTextFill(Color.RED);

                        }

                        if (checkRows() || checkColumns() || checkDiagonals()) {

                            status.setText(cell.getText() + " Wins!");
                            status.setTextFill(Color.GREEN);

                            for (int r = 0; r < 3; r++) {
                                for (int c = 0; c < 3; c++) {
                                    cells[r][c].setDisable(true);
                                }
                            }

                            return;
                        }

                        xTurn = !xTurn;

                        if (xTurn) {
                            status.setText("Player X Turn");
                            status.setTextFill(Color.BLUE);
                        } else {
                            status.setText("Player O Turn");
                            status.setTextFill(Color.RED);
                        }
                    }
                });

            }
        }

        restart.setOnAction(e -> {

            xTurn = true;

            status.setText("Player X Turn");
            status.setTextFill(Color.BLUE);

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {

                    cells[row][col].setText("");
                    cells[row][col].setDisable(false);
                }
            }
        });

        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(status, restart);

        VBox root = new VBox(20);
        root.setStyle(
            "-fx-background-color: #1E293B;" +
            "-fx-padding: 25;"
        );
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, controls, board);

        Scene scene = new Scene(root, 400, 400);

        stage.setTitle("X-O Game");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
