package fa.treeexplorerfx;

import Trees.Tree;
import fa.treeexplorerfx.models.TreeExplorerModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.util.Optional;

public class TreeInfoController {
    @FXML
    private TreeView<Tree.Node> treeView;
    @FXML
    private Button deleteNodeBtn, addNodeBtn;
    private final TreeExplorerModel treeExplorerModel;

    {
        this.treeExplorerModel = TreeExplorerModel.create();
    }

    public void handleDeleteNode() {
        TreeItem<Tree.Node> selectedNode = treeView.getSelectionModel().getSelectedItem();

        TreeItem<Tree.Node> parent = selectedNode.getParent();

        if (!selectedNode.getValue().isRoot()) {
            parent.getChildren().remove(selectedNode);
            selectedNode.getValue().remove();
        } else {
            treeExplorerModel.removeTreeById(selectedNode.getValue().getId());
            Stage stage = (Stage) deleteNodeBtn.getScene().getWindow();
            stage.close();
        }
    }

    public void handleAddNode() {
        TreeItem<Tree.Node> selectedNode = treeView.getSelectionModel().getSelectedItem();
        var newNode = new Tree.Node(treeExplorerModel.getMaxNodeId());
        selectedNode.getValue().addChild(newNode);
        selectedNode.getChildren().add(new TreeItem<>(newNode));
    }

    public void setTree(int id) {
        Optional<Tree> tree = treeExplorerModel.getTreeById(id);

        treeView.setRoot(_buildTree(tree.get().getRoot()));
        treeView.setShowRoot(true);
    }

    @FXML
    public void initialize() {
        deleteNodeBtn.disableProperty().bind(
                Bindings.isNull(
                        treeView.getSelectionModel().selectedItemProperty()
                )
        );
        addNodeBtn.disableProperty().bind(
                Bindings.isNull(
                        treeView.getSelectionModel().selectedItemProperty()
                )
        );
    }

    private TreeItem<Tree.Node> _buildTree(Tree.Node node) {
        var tree = new TreeItem<>(node);
        for (var child : node.getChildren()) {
            tree.getChildren().add(_buildTree(child));
        }
        return tree;
    }

}
