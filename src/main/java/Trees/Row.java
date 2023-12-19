package Trees;
import java.util.ArrayList;
import java.util.List;


public class Row {
    public int id;
    public int parent_id;

    public Row() {
    }
    public Row(int id, int parent_id){
        this.id = id;
        this.parent_id = parent_id;
    }

    public static ArrayList<Row> buildRows(List<Tree> trees) {
        var rows = new ArrayList<Row>();
        for (var tree:
                trees) {
            var treeChildren = tree.getNodes();
            for (var nodes:
                    treeChildren) {
                rows.add(new Row(nodes.getId(), nodes.getParentNode().getId()));
            }
        }
        return rows;
    }
}
