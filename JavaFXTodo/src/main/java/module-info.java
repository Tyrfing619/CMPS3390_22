module a8.scarlos.javafxtodo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;


    opens a8.scarlos.javafxtodo to javafx.fxml;
    exports a8.scarlos.javafxtodo;
}