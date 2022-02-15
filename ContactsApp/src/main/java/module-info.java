module a3.scarlos.contactsapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens a3.scarlos.contactsapp to javafx.fxml;
    exports a3.scarlos.contactsapp;
}