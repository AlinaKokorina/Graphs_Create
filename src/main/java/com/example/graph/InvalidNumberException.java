package com.example.graph;

public class InvalidNumberException extends Exception {
    /**
     * Exception Constructor
     */
    public InvalidNumberException() {
        super("Кол-во узлов должно быть не меньше трёх");
    }
}