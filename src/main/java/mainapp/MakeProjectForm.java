package mainapp;

import javafx.fxml.FXML;
import tableobjects.Project;
import tableobjects.User;

public class MakeProjectForm {
    @FXML
    public void submitForm(){
        AppData.getInstance().getProjItems().add(new Project(21,"TEST ENTRY","THIS IS A TEST"));
    }
}
