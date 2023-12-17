package com.example.graph;

public class InvalidNumberOrderException extends Exception {
    /**
     * Exception Constructor
     */
    public InvalidNumberOrderException() {
        super("Кол-во петель должно быть меньше кол-ва узлов");
    }
}