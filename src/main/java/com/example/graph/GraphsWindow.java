package com.example.graph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GraphsWindow extends Application {
    private static final Logger logger = LogManager.getLogger(GraphsWindow.class);
    public int cycles;
    public long time;

    /**
     * the entry point to the application
     *
     * @param args Command Line parameter
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Launches the application and displays the first window
     *
     * @param stage An instance of the Stage class
     * @throws IOException An exception occurs when an input/output error occurs
     */
    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Starting program...");
        FXMLLoader fxmlLoader = new FXMLLoader(GraphsWindow.class.getResource("viewFirst.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("Graph");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The handler for closing the application, blocks the termination of the program
     */
    @Override
    public void stop() {
        logger.info("Stop program");
    }

    /**
     * Launches a window with the generated graph
     *
     * @param stage              An instance of the Stage class
     * @param value_textF_QNodes Number of nodes in the graph (Instance of the Integer class)
     * @param value_textF_QLoops Number of loops in the graph (Instance of the Integer class)
     */
    public void graphWindow(Stage stage, Integer value_textF_QNodes, Integer value_textF_QLoops) {
        logger.info("Open graph window");
        GraphsCreate graphsCreate = new GraphsCreate();
        Pane root = graphsCreate.createGraph(value_textF_QNodes, value_textF_QLoops);
        cycles = graphsCreate.getCycles();
        time = graphsCreate.getTime();
        Scene scene = new Scene(new ScrollPane(root), 750, 750);

        stage.setTitle("Graph");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the calculation window: number of loops and calculation duration
     *
     * @param stage An instance of the Stage class
     * @throws IOException An exception occurs when an input/output error occurs
     */
    public void calculationWindow(Stage stage) throws IOException {
        logger.info("Open calculation window");
        FXMLLoader fxmlLoader = new FXMLLoader(GraphsWindow.class.getResource("viewSecond.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 300);

        GraphController graphController;
        graphController = fxmlLoader.getController();

        initializeTextFlow(graphController.textF_QCycles, cycles);
        initializeTextFlow(graphController.textF_Time, time);

        stage.setTitle("Calculation");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes instances of the TextFlow class
     *
     * @param textFlow An instance of the TextFlow class
     * @param value    Type long
     */
    private void initializeTextFlow(TextFlow textFlow, long value) {
        Text text = new Text(String.valueOf(value));
        text.setFont(Font.font(35));
        text.setFill(Color.WHITE);
        textFlow.getChildren().add(text);
    }
}