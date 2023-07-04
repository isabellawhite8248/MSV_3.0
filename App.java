import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage stage) {

        //launches application
        PaneOrganiser organizer = new PaneOrganiser();
        Scene scene = new Scene(organizer.getRoot(), Constants.APP_WIDTH,
                Constants.APP_HEIGHT);
        stage.setScene(scene);
        stage.setTitle(Constants.APP_NAME);
        stage.show();


    }
}
