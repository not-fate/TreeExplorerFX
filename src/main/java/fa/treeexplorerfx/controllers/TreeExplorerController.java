package fa.treeexplorerfx.controllers;

import Trees.Row;
import Trees.TreeBuilder;
import fa.treeexplorerfx.Connector;
import fa.treeexplorerfx.models.TreeExplorerModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Trees.Tree;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class TreeExplorerController {
    private final TreeExplorerModel treeExplorerModel;
    private final Connector connector;

    @FXML private VBox treesPanel;
    @FXML private ScrollPane treesScrollPane;
    @FXML private Button showTreesBtn, loadTreesBtn, uploadTreesBtn, addTreeBtn;

    {
        this.treeExplorerModel = TreeExplorerModel.create();
        this.connector = new Connector();
    }

    private void _showTrees(){
        treesPanel.getChildren().clear();
        treesPanel.setSpacing(5);

        for (Tree tree : treeExplorerModel.getTrees()) {
            HBox node = new HBox();
            node.setAlignment(Pos.CENTER);
            node.setSpacing(10);
            var treeID = tree.getRoot().getId();
            Label label = new Label("Root ID: " + tree.getRoot().getId());
            Button treeBtn = new Button("Show info");
            label.getStyleClass().add("tree");

            treeBtn.setOnAction(event -> {
                showTreeInfo(treeID);
            });

            node.getChildren().addAll(label, treeBtn);
            treesPanel.getChildren().add(node);
        }
    }

    @FXML
    private void onShowTrees() {
        _showTrees();
        addTreeBtn.setDisable(false);
    }

    private void _showAlert(String message, Alert.AlertType type) {
        var info = new Alert(type);
        info.setTitle("Information");
        info.setHeaderText(message);
        info.showAndWait();
    }


    @FXML
    private void onLoadTrees() {
        try {
            ArrayList<Row> rows = connector.getTrees();
            treeExplorerModel.setTrees(TreeBuilder.createListOfTrees(rows));
            treesPanel.getChildren().clear();
            showTreesBtn.setDisable(false);
            uploadTreesBtn.setDisable(false);
            _showAlert("The data has been downloaded from the database and saved.", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            _showAlert(ex.toString(),Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onUploadTrees() {
        try {
            connector.post(Row.buildRows(treeExplorerModel.getTrees()));
            _showAlert("The trees have been successfully uploaded to the database.", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            _showAlert(ex.toString(),Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onAddTree() {
        treeExplorerModel.addNewTree();
        _showTrees();
    }

    private void showTreeInfo(int treeID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fa/treeexplorerfx/views/TreeInfo.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Tree ID: " + treeID);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);


            stage.setScene(new Scene(loader.load()));
            var controller = (TreeInfoController) loader.getController();
            controller.setTree(treeID);
            stage.showAndWait();
            _showTrees();

        } catch (IOException ex) {
            _showAlert(ex.toString(),Alert.AlertType.ERROR);
        }
    }
}
