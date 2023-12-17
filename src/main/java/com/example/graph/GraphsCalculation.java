package com.example.graph;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GraphsCalculation {
    private static final Logger logger = LogManager.getLogger(GraphsWindow.class);
    private final boolean[] visited;
    private final boolean[] onStack;
    private int cycleCount;
    private final Boolean[][] matrixAdjacency;
    public long startTime;
    public long endTime;

    /**
     * Constructor of a class with parameters that initializes variables
     *
     * @param matrixAdjacency The adjacency matrix of the graph
     */
    public GraphsCalculation(Boolean[][] matrixAdjacency) {
        int value_textF_QNodes = matrixAdjacency.length;
        this.visited = new boolean[value_textF_QNodes];
        this.onStack = new boolean[value_textF_QNodes];
        this.cycleCount = 0;
        this.matrixAdjacency = matrixAdjacency;
    }

    /**
     * A method for calculating the number of cycles in a graph
     *
     * @return Number of cycles in the graph
     */
    public int countCycles() {
        startTime = System.currentTimeMillis();
        int value_textF_QNodes = matrixAdjacency.length;

        for (int i = 0; i < value_textF_QNodes; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
        endTime = System.currentTimeMillis();

        logger.info("The number of cycles is calculated");
        return cycleCount;
    }

    /**
     * A method for searching for cycles in a graph using the Depth-First Search (DFS) algorithm
     *
     * @param value_textF_QNodes Int type, number of nodes in the graph
     */
    private void dfs(int value_textF_QNodes) {
        visited[value_textF_QNodes] = true;
        onStack[value_textF_QNodes] = true;

        for (int i = 0; i < matrixAdjacency[value_textF_QNodes].length; i++)
            if (matrixAdjacency[value_textF_QNodes][i]) {
                if (!visited[i])
                    dfs(i);
                else if (onStack[i])
                    cycleCount++;
            }

        onStack[value_textF_QNodes] = false;
    }
}