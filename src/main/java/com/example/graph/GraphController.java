package com.example.graph;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GraphController {
    private static final Logger logger = LogManager.getLogger(GraphsWindow.class);
    @FXML
    public TextFlow textF_Time;
    @FXML
    private Button buttonNext;
    @FXML
    private TextField textF_QNodes;
    @FXML
    private TextField textF_QLoops;
    @FXML
    public TextFlow textF_QCycles;

    /**
     * Handler for the "Next" button click event
     * @throws IOException An exception occurs when an input/output error occurs
     * @throws InvalidNumberOrderException An exception occurs if the number of loops is greater than the number of nodes
     * @throws InvalidNumberException An exception occurs if the number of nodes is less than three
     */
    @FXML
    protected void onNextButtonClick() throws IOException, InvalidNumberOrderException, InvalidNumberException {
        logger.info("The next button is pressed");
        int value_textF_QNodes = Integer.parseInt(textF_QNodes.getText());
        int value_textF_QLoops = Integer.parseInt(textF_QLoops.getText());

        limitationNodes(value_textF_QNodes);
        compareNumbers(value_textF_QNodes, value_textF_QLoops);
        GraphsWindow graphs = new GraphsWindow();
        Stage stage = (Stage) buttonNext.getScene().getWindow();
        stage.close();

        stage = new Stage();
        graphs.graphWindow(stage, value_textF_QNodes, value_textF_QLoops);
        stage = new Stage();
        graphs.calculationWindow(stage);
    }

    /**
     * Comparing two numbers. Causes an exception if the second number is greater than or equal to the first
     * @param value_textF_QNodes Int type, number of nodes in the graph
     * @param value_textF_QLoops Int type, number of loops in the graph
     * @throws InvalidNumberOrderException
     */
    protected void compareNumbers(int value_textF_QNodes, int value_textF_QLoops) throws InvalidNumberOrderException {
        if (value_textF_QNodes <= value_textF_QLoops) {
            logger.error("The number of loops is greater thn the number of nodes");
            throw new InvalidNumberOrderException();
        }
    }

    /**
     * Checking for the number of nodes in the graph. Causes an exception if there are fewer than three nodes
     * @param value_textF_QNodes Int type, number of nodes in the graph
     * @throws InvalidNumberException
     */
    protected void limitationNodes(int value_textF_QNodes) throws InvalidNumberException{
        if (value_textF_QNodes < 3) {
            logger.error("The number of nodes is less than three");
            throw new InvalidNumberException();
        }
    }
}