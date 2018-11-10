package userinterface;

import connection.DBSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import tableobjects.TableObject;


public class SynkApp extends Application {
    private Stage stage;
    public static String driver = "com.mysql.cj.jdbc.Driver";
    
    
  /*  
    public void Design(Stage primaryStage) {

   	 try {
        	Parent root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
        	Scene scene = new Scene(root,400,400);
        	scene.getStylesheets().add(getClass().getResource("AppStyles.css").toExternalForm());
        	primaryStage.setScene(scene);
        	primaryStage.show();
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
    	
    }
    */
     
    public static void main(String args[]){
    	System.out.println("in main");
        try {
            Class.forName(driver);
        }catch(Exception e){
            System.err.println("Error in loading driver " + e);
            
        }
       
        launch(args);
        
    }

    private static SynkApp instance;

    public SynkApp(){
        instance = this;
    }
    public static SynkApp getInstance(){
        return instance;
    }
    public Stage getStage(){
        return stage;
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            DBSource.establish();
            gotoLogin();
            
        } catch (Exception ex) {

        }
        
       /* try {
        	Parent root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
        	Scene scene = new Scene(root,400,400);
        	scene.getStylesheets().add(getClass().getResource("AppStyles.css").toExternalForm());
        	primaryStage.setScene(scene);
        	primaryStage.show();
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }*/
        
        
        
        
        
        
    }
    public void gotoMainUI(){
        try {
            stage.setTitle("Synk");
            replaceSceneContent("/fxml/mainAppUI.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void gotoLogin() {
        try {
            stage.setTitle("Synk Login");
            replaceSceneContent("/fxml/loginScreen.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void replaceSceneContent(String fxml) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(page);
        scene.getStylesheets().add("/fxml/style.css");
        stage.setScene(scene);
        
        stage.show();
        stage.centerOnScreen();

    }
}
