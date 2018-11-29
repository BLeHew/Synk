package mainapp;

import javafx.scene.control.TableView;
import tableobjects.TableObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class TableViewMapper {

    private HashMap<String, TableView<TableObject>> tableViewMap = new HashMap<>();

    public TableViewMapper(List<TableView<TableObject>> tableViews){
        for(TableView<TableObject> tableView :  tableViews){
            tableViewMap.put((String)tableView.getUserData(),tableView);
        }
    }
    public int getId(String tableView){
        return tableViewMap.get(tableView).getSelectionModel().getSelectedItem().getId();
    }
    public int getIndex(String tableView){
        return tableViewMap.get(tableView).getSelectionModel().getSelectedIndex();
    }
    public TableObject getObject(String tableView){
        return tableViewMap.get(tableView).getSelectionModel().getSelectedItem();
    }
}
