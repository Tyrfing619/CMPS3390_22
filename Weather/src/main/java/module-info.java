module a4.scarlos.weather {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.net.http;

    opens a4.scarlos.weather to javafx.fxml;
    exports a4.scarlos.weather;
}