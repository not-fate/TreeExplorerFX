module fa.treeexplorerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens fa.treeexplorerfx to javafx.fxml;
    exports fa.treeexplorerfx;
    exports Trees;
    exports fa.treeexplorerfx.models;
    opens fa.treeexplorerfx.models to javafx.fxml;
}