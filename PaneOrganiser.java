import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.File;
import java.io.IOException;

public class PaneOrganiser {

    //contains the initial title GUI and gives the user the option of programming a new animation or
    //continueing to edit an existing one

    private Pane root;
    private boolean isNew;
    private File[] files;
    private File directory;

    public PaneOrganiser(){

        this.directory = new File(Constants.MUSIC_DIR_PATH);
        this.files = directory.listFiles();

        for(int i = 0; i < this.files.length; i++){

            String name = this.files[i].getName();
            //first make the directory in the animation folder for that audio file - if it doesn't exist

            File theDir = new File(Constants.ANIMATION_FOLDER + name);

            if (!theDir.exists()){
                theDir.mkdirs();
            }

            //next - make a file with the audio file name.txt
            try {
                File myObj = new File(Constants.ANIMATION_FOLDER + name + "/" + name + ".txt");
                if (myObj.createNewFile()) {
                    System.out.println(Constants.FL_CREATED + myObj.getName());
                }
            } catch (IOException e) {
                System.out.println(Constants.FILE_CREATION_ERR);
                e.printStackTrace();
            }
        }

        this.root = new Pane();
        this.setButtons();

    }

    public Pane getRoot(){
        return root;
    }

    //creates a new music player to start a new animation programming sect
    public void setUpAnim(){

        //starts a new animation board
        Rectangle rect = new Rectangle(Constants.APP_WIDTH, Constants.APP_HEIGHT, Color.BLACK);

        //animation screen
        this.root.getChildren().add(rect);
        MusicPlayer play = new MusicPlayer(root);
    }

    public void startNewAnimation(){

        this.isNew = true;
        setUpAnim();

    }

    public void startOldAnimation(){

        this.isNew = false;
        setUpAnim();

    }


    public void setButtons(){

        HBox buttonBox = new HBox();

        //quit button which exits the program
        Button quitButton = new Button(Constants.QUIT);
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        quitButton.setFocusTraversable(false);
        quitButton.setLayoutX(Constants.APP_WIDTH);

        VBox options = new VBox();
        int buttonSpacing = Constants.OFFSET*3;

        Label msg = new Label(Constants.SELECT_OPTION);

        Button newAnime = new Button(Constants.NEW_ANIME);
        newAnime.setOnAction((ActionEvent e) -> startNewAnimation());

        Button oldAnime = new Button(Constants.OLD_ANIME);
        oldAnime.setOnAction((ActionEvent e) -> startOldAnimation());

        options.getChildren().addAll(newAnime, oldAnime);
        options.setSpacing(buttonSpacing);

        //where you can add more buttons to the button box
        buttonBox.getChildren().addAll(msg, options, quitButton);
        buttonBox.setSpacing(buttonSpacing);
        this.root.getChildren().add(buttonBox);
    }

}
