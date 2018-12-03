package mainapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import tableobjects.TableObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TableViewMapper extends HashMap<String,TableView<TableObject>>{

    public TableViewMapper(List<TableView<TableObject>> tableViews){
        for(TableView<TableObject> tableView :  tableViews){
            put((String)tableView.getUserData(),tableView);
        }
    }
    public int getSelectedId(String tableView){
        if(noSelection(tableView)){
            return -1;
        }
        return get(tableView).getSelectionModel().getSelectedItem().getId();
    }
    public int getIndex(String tableView){
        return get(tableView).getSelectionModel().getSelectedIndex();
    }
    public void removeSelected(String tableView){
        get(tableView).getItems().remove(get(tableView).getSelectionModel().getSelectedIndex());
    }
    public boolean noSelection(String tableView){
        return get(tableView).getSelectionModel().getSelectedIndex() == -1;
    }
    public TableObject getSelected(String tableView){
        return get(tableView).getSelectionModel().getSelectedItem();
    }
}
