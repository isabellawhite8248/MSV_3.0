import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.io.File;

/*
holds a time stamp which is formed when prompted by the user
displays an image for that time section of the song and contains
a list of rbg buttons with image file names that the user can
toggle and display
 */
public class TimeStamp {

    private Rectangle cell;
    private Rectangle cover;
    private ObservableList names;
    private Label timeLabel;
    private Pane p;
    private Timeline time;
    private Image img;
    private ImageView view;
    private int offset;
    private String selection;
    private ToggleGroup group;
    private String selectedImage;
    private String st;
    private String en;
    private boolean isCovered;
    private VBox rbPane;

    public TimeStamp(String startTime, String endTime){

        isCovered = false;
        cover = new Rectangle(Constants.COVER_DIM, Constants.COVER_DIM, Constants.COVER_FILL);
        st = startTime;
        en = endTime;
        offset = Constants.OFFSET;
        selection = Constants.DEFAULT_IMG_PATH;
        selectedImage = selection;
        rbPane = new VBox();

        img = new Image(Constants.DEFAULT_IMG_PATH);
        view = new ImageView(img);
        view.setFitHeight(Constants.IMG_VW_HGT);
        view.setFitWidth(Constants.IMG_VW_WID);
        view.setX(Constants.OFFSET);
        view.setY(Constants.OFFSET);
        view.setPreserveRatio(true);

        setUpTimeline();
        names = FXCollections.observableArrayList();
        p = new Pane();
        cell = new Rectangle(Constants.CELL_DIM, Constants.CELL_DIM, Constants.CELL_FILL);
        ChoiceBox<String> images = new ChoiceBox<>();

        //figures out what images are available in the images folder and makes note of them
        File directory = new File(Constants.IMG_DIR_PATH);
        File[] files = directory.listFiles();

        //creates a list of radio buttons for the user to choose from based off of the available images
        group = new ToggleGroup();
        RadioButton rb1 = new RadioButton(Constants.DEFAULT_RBG);
        rb1.setToggleGroup(group);
        rb1.setTextFill(Constants.TEXT_FILL);
        rb1.setSelected(true);
        rb1.setTextFill(Color.BLACK);
        rbPane.getChildren().add(rb1);

        if(files != null){

            for(File x : files){
                if(!(x.getName()).equals(Constants.BUG)){ //weird bug - not sure why that shows up as an option - but eliminate because it causes the program to crash
                    names.add(x.getName());
                    RadioButton rb = new RadioButton(x.getName());
                    rb.setTranslateX(Constants.OFFSET/2);
                    rb.setTranslateY((Constants.CELL_DIM + Constants.OFFSET) + offset);
                    offset = offset + (Constants.OFFSET*2);
                    rb.setToggleGroup(group);
                    rb.setTextFill(Color.BLACK);
                    rbPane.getChildren().add(rb);
                }
            }
        }

        ScrollPane scrollable = new ScrollPane();
        scrollable.setPrefViewportWidth(Constants.SCROLL_PANE_WIDTH);
        scrollable.setPrefViewportHeight(Constants.SCROLL_PANE_HEIGHT);
        scrollable.setContent(rbPane);
        scrollable.setLayoutY(Constants.SCROLL_LAYOUT_Y);
        p.getChildren().add(scrollable);

        //label of the time stamp from what time to what time it is marking / duration
        this.timeLabel = new Label(startTime + Constants.CONNECT + endTime);
        timeLabel.setTextFill(Constants.COVER_FILL);
        timeLabel.setLayoutY(Constants.TIME_LABEL_Y);
        timeLabel.setFont(new Font(Constants.TIME_LBL_FONT_SIZE));

        p.setPrefSize(Constants.PANE_DIM, Constants.PANE_DIM);
        p.getChildren().addAll(cell, timeLabel, view);

    }

    //updates with the timeline to figure out which toggle the user chooses
    public void update(){

        selectedImage = group.getSelectedToggle().toString();
        String[] array = group.getSelectedToggle().toString().split("'");
        String chosenToggle = array[1];

        if(!selection.equals(chosenToggle)){
            try{
                view.setImage(new Image(Constants.IMG + chosenToggle));
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
        selection = chosenToggle;
    }

    //gets the selected image by figuring out which toggle is selected
    public String getSelectedImage(){
        return selectedImage;
    }

    public void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.KF_DUR), (ActionEvent e) -> update());
        time = new Timeline(kf);
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public Pane getAll(){
        return p;
    }

    //resets the label and the text of the labels
    public void reset(String start, String end){
        this.st = start;
        this.en = end;
        this.timeLabel.setText(start + Constants.CONNECT + end);
    }

    public String getEnd(){
        return en;
    }

    //hides the time stamp by covering it with a black rectangle -- the same color as the backdrop
    public void cover(){
        if(!isCovered){
            p.getChildren().add(cover);
            isCovered = true;
        }
    }

    public String getStart(){
        return st;
    }

    //returns if the timestamp is hidden or not
    public boolean getCoverStatus(){
        return isCovered;
    }

    //unhides the time stamp
    public void uncover(){
        if(isCovered){
            p.getChildren().remove(cover);
            isCovered = false;
        }
    }
}
