import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
This class contains an array list of anime boards and functions
to load and save data to load into the visualizer
 */

public class Animater {

    private Rectangle backdrop;
    private Pane rt;
    private Timeline time;
    private String folderPath;
    private String fileP;
    private VBox buttonPane;
    private ArrayList<String> timeStamps;
    private ArrayList<String> images;
    private ArrayList<animeBoard> slides;
    private HBox layout;
    private boolean allFilled;
    private String selectedTimeStamp;
    private ToggleGroup rbgGroup;

    public Animater(String folderFilePath, String filePath, Pane root){

        selectedTimeStamp = Constants.TS_INITIALIZER;
        rbgGroup = new ToggleGroup();
        allFilled = false;
        layout = new HBox();
        slides = new ArrayList<>();
        buttonPane = new VBox();
        buttonPane.setSpacing(Constants.BUTTON_PANE_SPACEING);
        timeStamps = new ArrayList<>();
        images = new ArrayList<>();
        rt = root;
        backdrop = new Rectangle(Constants.APP_WIDTH, Constants.APP_HEIGHT, Color.BLACK);
        rt.getChildren().add(backdrop);
        folderPath = folderFilePath;
        fileP = filePath;
        setUpTimeline();

        //parse the contents of the file and get an array of strings after splitting lines by spaces - two string arraylists - one time stamps one images - lines up with the line nums
        parseFile();

        //setup buttons and load them into the button vbox then add them to the board;
        setUpRadioButtons(buttonPane);
        setUpButtons(buttonPane);

        //to the right of that set the anime board within the layout h box - first add the vbox then the anime board, then hbox to the board;
        for(int i = 0; i < timeStamps.size(); i++){
            animeBoard slide = new animeBoard(images.get(i), timeStamps.get(i), i, folderPath);
            slides.add(slide);
        }

        layout.getChildren().addAll(buttonPane, slides.get(0).getFrame());

        rt.getChildren().add(layout);

    }

    /*
    updates with the timeline if the user selects a new time stamp aka animeboard
    it will update the GUI, also checks if all the info is saved, if it is loading to
    the visualizer will be enabled
     */
    public void update(){

        String prevStamp = selectedTimeStamp;
        String currStamp = rbgGroup.getSelectedToggle().toString().split("'")[1];

        if(!prevStamp.equals(currStamp)){

            int slideNumPrev = timeStamps.indexOf(prevStamp);
            int slideNumCurr = timeStamps.indexOf(currStamp);

            selectedTimeStamp = currStamp;
            //call save info on current slide

            if(slideNumPrev >= 0){ //prevents null pointer exception?? maybe idk -- go back and look at bug
                animeBoard currSlide = slides.get(slideNumPrev);
                currSlide.saveInfo();

                //remove currSlide and add the newly selected one
                layout.getChildren().remove(currSlide.getFrame());
                layout.getChildren().add(slides.get(slideNumCurr).getFrame());
            }
        }

        //checks if all the data is filled
        int count = 0;
        for(int i = 0; i < slides.size(); i++){
            if(slides.get(i).infoIsSaved()){
                count++;
            }
        }
        if(count == slides.size()){
            allFilled = true; //all the boxes have some sort of information
        }
    }

    public void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.KF_DUR), (ActionEvent e) -> update());
        this.time = new Timeline(kf);
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void parseFile(){

        //populates the time stamp and images array (index correlates to line number in the file);

        try {
            File myObj = new File(fileP);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] stuff = data.split(" ");
                String timeStamp = stuff[0] + " to " + stuff[2];
                String image = stuff[4];

                if(image.equals("Home")){
                    image = "astronaut.jpg";
                }

                timeStamps.add(timeStamp);
                images.add(image);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found error in Animater : ");
            e.printStackTrace();
        }

    }

    public void setUpRadioButtons(Pane buttonPane){

        //load in radio buttons with the time stamp
        for(int i = 0; i < timeStamps.size(); i++){
            RadioButton rb = new RadioButton(timeStamps.get(i));
            rb.setToggleGroup(rbgGroup);
            rb.setTextFill(Constants.TEXT_FILL);
            if(i == 0){
                rb.setSelected(true);
            }
            buttonPane.getChildren().add(rb);
        }

    }

    /*
    loads the visualizer if all information is filled out in the anime boards
     */
    public void playAnimation(){
        if(allFilled){
            //stop all timelines
            for(animeBoard a : slides){
                a.stopTime();
            }
            Visualizer visual = new Visualizer(rt, fileP, timeStamps);
        } else {
            Label error = new Label(Constants.LOAD_ERROR);
            error.setTextFill(Color.RED);
            buttonPane.getChildren().add(error);
        }
    }

    public void setUpButtons(Pane buttonPane){
        //under the toggles between animeBoard objects button that says load animation - should only be clicked if animations are selected for each dot group
        Button loadAnime = new Button(Constants.LOAD_ANIME_LABEL);
        loadAnime.setOnAction((ActionEvent e) -> playAnimation());

        //exit button
        Button quit = addQuit();

        //invisible spacer--- janky yes, but gets the job done
        Label error = new Label(Constants.SPACER);
        error.setTextFill(Constants.INVISIBLE);

        buttonPane.getChildren().addAll(quit, loadAnime, error);
    }

    public Button addQuit(){
        Button quitButton = new Button(Constants.QUIT);
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        quitButton.setFocusTraversable(false);
        quitButton.setLayoutX(Constants.APP_WIDTH);
        return quitButton;
    }

}
