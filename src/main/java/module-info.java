module com.example.graph {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;


    opens com.example.graph to javafx.fxml;
    exports com.example.graph;
}