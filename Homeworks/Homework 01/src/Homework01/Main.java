package Homework01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public static MainWindowController mainWindowController;
    public static Scene mainWindow;
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Loads the FXML for the MainWindow Scene and creates the Scene.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/MainWindow.fxml"));
        mainWindow = new Scene(loader.load(), 1280, 720);
        mainWindowController = loader.getController();

        // Saves a reference of the Stage object so
        // the Controller class can access it.
        // It also sets the stage.
        stage = primaryStage;
        stage.setTitle("Homework #01 - Classical Encryption Techniques");
        stage.setResizable(true);
        stage.setScene(mainWindow);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
