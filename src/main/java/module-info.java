module com.spomp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    opens com.spomp.model;
    opens com.spomp.controller;
    exports com.spomp;
}
