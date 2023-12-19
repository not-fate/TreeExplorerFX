module fa.treeexplorerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens fa.treeexplorerfx to javafx.fxml;
    exports fa.treeexplorerfx;
    exports Trees;
    exports fa.treeexplorerfx.models;
    opens fa.treeexplorerfx.models to javafx.fxml;
    exports fa.treeexplorerfx.controllers;
    opens fa.treeexplorerfx.controllers to javafx.fxml;
}