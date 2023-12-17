package com.example.graph;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class GraphsCreate {
    private static final Logger logger = LogManager.getLogger(GraphsWindow.class);
    private Boolean[][] matrixAdjacency;
    private List<Circle> nodes;
    private List<Shape> edges;
    public int cycles;
    public long time;

    /**
     * Creating a user-specified number of nodes
     *
     * @param value_textF_QNodes An instance of the Integer class, the number of nodes of the graph
     * @return List of created nodes
     */
    public List<Circle> createNodes(Integer value_textF_QNodes) {
        int node_radius;
        node_radius = 200 + value_textF_QNodes * 5;
        double centerX;
        double centerY;
        nodes = new ArrayList<>();

        centerX = 300 + value_textF_QNodes * 5;
        centerY = 300 + value_textF_QNodes * 5;

        for (int i = 0; i < value_textF_QNodes; i++) {
            double x = centerX + node_radius * Math.cos(2 * Math.PI * i / value_textF_QNodes);
            double y = centerY + node_radius * Math.sin(2 * Math.PI * i / value_textF_QNodes);
            Circle node = new Circle(x, y, 10, Color.DARKORANGE);
            nodes.add(node);
        }
        logger.info("Nodes have been created");
        return nodes;
    }

    /**
     * Creating graph edges
     *
     * @return List of created edges
     */
    public List<Shape> createEdges() {
        edges = new ArrayList<>();
        int num_nodes;

        if (nodes.size() <= 3) num_nodes = nodes.size() - 1;
        else num_nodes = nodes.size();

        for (int i1 = -1, j = 0; j < nodes.size(); i1++, j++) {
            int i = i1;
            if (i == -1) i = num_nodes - 1;
            Line edge = new Line(nodes.get(i).getCenterX(), nodes.get(i).getCenterY(), nodes.get(j).getCenterX(), nodes.get(j).getCenterY());

            if (j < num_nodes / 2) matrixAdjacency[i][j] = true;
            else matrixAdjacency[j][i] = true;

            edges.add(edge);
        }
        logger.info("Edges have been created");
        return edges;
    }

    /**
     * Creating arrows representing the direction of edges in the graph
     *
     * @return List of created arrows
     */
    public List<Shape> createArrow() {
        for (int i = 0; i < nodes.size(); i++)
            for (int j = 0; j < nodes.size(); j++) {
                if (matrixAdjacency[i][j]) {
                    double arrowSize = 15;
                    double arrowAngle = Math.toRadians(15);
                    double dx = nodes.get(j).getCenterX() - nodes.get(i).getCenterX();
                    double dy = nodes.get(j).getCenterY() - nodes.get(i).getCenterY();
                    double angle = Math.atan2(dy, dx);
                    double x1 = nodes.get(j).getCenterX() - arrowSize * Math.cos(angle - arrowAngle);
                    double y1 = nodes.get(j).getCenterY() - arrowSize * Math.sin(angle - arrowAngle);
                    double x2 = nodes.get(j).getCenterX() - arrowSize * Math.cos(angle + arrowAngle);
                    double y2 = nodes.get(j).getCenterY() - arrowSize * Math.sin(angle + arrowAngle);

                    Polygon arrowhead = new Polygon(nodes.get(j).getCenterX(), nodes.get(j).getCenterY(), x1, y1, x2, y2);
                    arrowhead.setFill(Color.DARKRED);
                    edges.add(arrowhead);
                }
            }
        logger.info("Arrow have been created");
        return edges;
    }

    /**
     * Creating the number of loops specified by the user in the graph
     *
     * @param value_textF_QLoops An instance of the Integer class, number of loops
     * @return List of created loops
     */
    public List<Shape> createLoop(Integer value_textF_QLoops) {
        Random random = new Random();

        for (int k = 0; k < value_textF_QLoops; k++) {
            int i = 0, j = 0;
            while (!((i != j) && ((i < nodes.size() / 2 && j < nodes.size() / 2) || (i >= nodes.size() / 2 && j >= nodes.size() / 2)))) {
                i = abs(random.nextInt()) % nodes.size();
                j = abs(random.nextInt()) % nodes.size();
            }

            if (i < j && i < nodes.size() / 2) {
                int copy = i;
                i = j;
                j = copy;
            }

            if (i > j && i >= nodes.size() / 2) {
                int copy = i;
                i = j;
                j = copy;
            }

            Line loop = new Line(nodes.get(i).getCenterX(), nodes.get(i).getCenterY(), nodes.get(j).getCenterX(), nodes.get(j).getCenterY());
            loop.setFill(Color.TRANSPARENT);
            loop.setStrokeWidth(1);
            loop.setStroke(Color.BLACK);

            matrixAdjacency[i][j] = true;
            edges.add(loop);
        }
        logger.info("Loops have been created");
        return edges;
    }


    /**
     * Creating a graph
     *
     * @param value_textF_QNodes An instance of the Integer class, the number of nodes of the graph
     * @param value_textF_QLoops An instance of the Integer class, the number of loops of the graph
     * @return An instance of the Pane class representing a directed graph
     */
    public Pane createGraph(Integer value_textF_QNodes, Integer value_textF_QLoops) {
        Pane pane = new Pane();
        nodes = createNodes(value_textF_QNodes);

        matrixAdjacency = new Boolean[nodes.size()][nodes.size()];
        for (int i = 0; i < nodes.size(); i++)
            for (int j = 0; j < nodes.size(); j++)
                matrixAdjacency[i][j] = false;

        edges = createEdges();
        edges = createLoop(value_textF_QLoops);
        edges = createArrow();
        pane.getChildren().addAll(nodes);
        pane.getChildren().addAll(edges);

        logger.info("Graph have been created");
        return pane;
    }

    /**
     * Returns the number of cycles of the graph
     * @return Number of cycles in the graph
     */
    public int getCycles() {
        GraphsCalculation graphsCalculation = new GraphsCalculation(matrixAdjacency);
        cycles = graphsCalculation.countCycles();
        time = graphsCalculation.endTime - graphsCalculation.startTime;
        return cycles;
    }

    /**
     * Returns the time spent calculating cycles in the graph
     *
     * @return Time in milliseconds
     */
    public long getTime() {
        return time;
    }
}