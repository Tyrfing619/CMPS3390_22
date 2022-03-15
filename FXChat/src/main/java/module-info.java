module a6.scarlos.fxchat.fxchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens a6.scarlos.fxchat.fxchat to javafx.fxml;
    exports a6.scarlos.fxchat.fxchat;
}