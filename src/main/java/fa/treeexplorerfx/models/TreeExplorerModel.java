package fa.treeexplorerfx.models;

import Trees.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TreeExplorerModel {
    private static TreeExplorerModel instance;
    private List<Tree> trees;
    private int maxNodeId;

    public List<Tree> getTrees() {
        return trees;
    }

    public void addNewTree(){
        var newNode = new Tree.Node(getMaxNodeId());
        newNode.setParentNode(newNode);
        trees.add(new Tree(newNode));
    }
    public void removeTreeById(int treeID){
        trees.removeIf(item -> (item.getRoot().getId() == treeID));
    }

    public int getMaxNodeId(){
        ++maxNodeId;
        return maxNodeId;
    }

    public Optional<Tree> getTreeById(int id) {
        return trees.stream()
                .filter(tree -> tree.getRoot().getId() == id)
                .findFirst();
    }

    public void setTrees(ArrayList<Tree> trees){
        this.trees = trees;
        _setMaxId();
    }

    private void _setMaxId() {
        var ids = trees.stream().flatMap(tree -> tree.getNodes().stream().map(Tree.Node::getId)).toList();
        maxNodeId = ids.isEmpty() ? 0: Collections.max(ids);
    }

    private TreeExplorerModel() {
    }

    public static TreeExplorerModel create() {
        if (instance == null) {
            instance = new TreeExplorerModel();
        }
        return instance;
    }
}
