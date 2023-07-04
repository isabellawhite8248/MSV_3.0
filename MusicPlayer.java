import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicPlayer {

    private Pane board;
    private ArrayList<File> songs;
    private File directory;
    private File[] files;
    private MediaPlayer play;
    private ObservableList<String> playList;
    private ChoiceBox plist;
    private Media med;
    private MediaView view;

    private int currentSong;

    private Timeline time;
    private Label t;
    private Circle progressMarker;

    private double currDur;
    private double totDur;
    private double OGX;

    private HashMap<String, Circle> stamp2time;
    private Pane pain;
    private ArrayList<Circle> timeMarkers;
    private ArrayList<CheckBox> checks;
    private VBox checkBs;
    private VBox miniBoard;
    private HashMap<CheckBox, String> status;
    private ArrayList<TimeStamp> boxes;
    private HBox stampBoard;

    private boolean songSwitched;
    private int count;

    /*
    contains a timeline to play the audio and stop it based off of the selected audio files, key listener enables the
    user to press t to make a new time stamp and a checkbox will appear to select it -- if selected a new time stamp will
    be added or taken away if deselected. The time stamp will have an image and a set of radiobuttons that enable the image to
    be altered, embedded within a scrollable pane.
     */

    public MusicPlayer(Pane root){

        this.status = new HashMap<>();
        this.count = Constants.FILLER;
        this.stampBoard = new HBox();
        this.boxes = new ArrayList<>();
        this.checks = new ArrayList<>();
        this.timeMarkers = new ArrayList<>();
        this.stamp2time = new HashMap<>();
        this.currDur = Constants.FILLER;
        this.miniBoard = new VBox();
        this.progressMarker = new Circle(Constants.PROGRESS_MARKER_RADIUS);
        this.progressMarker.setFill(Constants.PROGRESS_MARKER_FILL);
        this.OGX = this.progressMarker.getCenterX();
        this.songSwitched = false;

        this.t = new Label(Constants.START_TIME);
        this.t.setTextFill(Constants.TEXT_FILL);
        this.currentSong = Constants.DEFAULT_SONG_NUMBER;
        this.playList = FXCollections.observableArrayList();
        this.board = root;
        this.songs = new ArrayList<>();
        this.directory = new File(Constants.MUSIC_DIR_PATH);
        this.files = directory.listFiles();

        this.setUpTimeline();

        //adds the key listener
        this.board.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler());

        if(files != null){
            for(File file : files) {
                songs.add(file);
                String title = shortener(file.getAbsolutePath());
                playList.add(title);
            }
        }

        //starts the music
        this.med = new Media(songs.get(currentSong).toURI().toString());
        this.play = new MediaPlayer(med);

        this.view = new MediaView(play);
        this.board.getChildren().add(view);
        this.totDur = Constants.DEFAULT_SONG_DUR;

        this.setGUI();

    }

    //called at each time cycle of the time frame to detect for checked time stamps and alter the time stamps displayed
    public void update(){

        int d = (int) play.getCurrentTime().toSeconds();
        this.t.setText(Integer.toString(d));
        this.currDur = d;
        this.totDur = med.getDuration().toSeconds();
        this.progressMarker.setCenterX(this.OGX + calcX());

        //if the song is switched, clear the stamp board by covering every stamp except for the first one
        if(songSwitched && (count == Constants.DELAY)){
            resetStampBoard(Constants.START_TIME, this.med.getDuration().toString());
            songSwitched = false;
        } else if (count < Constants.DELAY){
            count++;
        }

        boolean changed = false;

        //checks if the status of the checked boxes has changed - if it has adds or removes a box
        for(int i = 0; i < checks.size(); i++){

            CheckBox cb = checks.get(i);
            String stat = getStatus(cb.isSelected());

            if(!status.get(cb).equals(stat)){
                status.put(cb, stat);
                changed = true;
            }

            if(checks.get(i).isSelected()){

                this.timeMarkers.get(i).setFill(Constants.TS_CHECKED_FILL);
                if(changed){ //if it was yellow before...
                    addStamp(checks.get(i).getText());
                    changed = false;
                }

            } else {

                this.timeMarkers.get(i).setFill(Constants.TS_UNCHECKED_FILL);
                if (changed){ //if it was green before...
                    removeStamp(checks.get(i).getText());
                    changed = false;
                }
            }
        }

    }

    public int findLastUncoveredTimeStamp(){

        for(int i = 0; i < boxes.size(); i++){
            if(boxes.get(i).getCoverStatus()){ //if that box is covered return the one before it
                return(i - 1);
            }
        }
        return boxes.size() - 1; //all of them are uncovered - just return the last one
    }

    public void removeStamp(String timeStamp){

        boolean reLabel = false;
        for(int i = 0; i < boxes.size(); i++){

            TimeStamp currStamp = boxes.get(i);
            String newEnd = currStamp.getEnd();

            if(timeStamp.equals(currStamp.getEnd())){

                String currStart = currStamp.getStart();
                TimeStamp nxt = boxes.get(i + 1);
                String end = nxt.getEnd();
                currStamp.reset(currStart, end);
                reLabel = true;

            } else if(reLabel){

                if(i == findLastUncoveredTimeStamp()){ //it i the last uncovered box

                    //reset the labels in the block before
                    String prevEnd = currStamp.getEnd();
                    TimeStamp prevBlock = boxes.get(i - 1);
                    String start = prevBlock.getStart();
                    prevBlock.reset(start, prevEnd);

                    //cover the last block
                    currStamp.cover();

                } else {

                    if((i+1) < boxes.size()){ //to prevent the index out of bounds
                        TimeStamp nxtStamp = boxes.get(i + 1);
                        currStamp.reset(newEnd, nxtStamp.getEnd());
                        newEnd = nxtStamp.getEnd();
                    }

                }

            } else {
                System.out.println(Constants.RMV_TR_ERR);
            }
        }
    }

    //method which returns the index of the first covered box in the global array of time stamps
    public int areCoveredBoxes(){
        for(int i = 0; i < boxes.size(); i++){
            if(boxes.get(i).getCoverStatus()){
                return i;
            }
        }
        return Constants.UPPER_BOUND; //means there are no covered boxes
    }

    //gets the first covered time stamp in the global boxes array of time stamps
    public TimeStamp getFirstCovered(){
        for(int i = 0; i < boxes.size(); i++){
            if(boxes.get(i).getCoverStatus()){
                return boxes.get(i);
            }
        }
        System.out.println(Constants.NO_COV_FOUND);
        return null;
    }

    //takes in a time stamp and a value and figures out if that val is in the range of the time stamp, returns boolean
    public boolean inRange(TimeStamp timeSt, double val){ //could also cut back on run time if it
        // returned a direction to go in if it is not in range like lower or higher but idk
        double end = Double.valueOf(timeSt.getEnd());
        double start = Double.valueOf(timeSt.getStart());
        if((val <= end) && (val >= start)){
            return true;
        } else {
            return false;
        }
    }

    //method to add a stamp - works by adding one to the end then switching the labeles accordingly
    public void addStamp(String timeStamp){

        int d = areCoveredBoxes();

        if(d == Constants.UPPER_BOUND){ //if there are no covered time stamps available...

            //we need a new box
            TimeStamp newStamp = new TimeStamp(Constants.START_TIME, Constants.START_TIME);
            this.boxes.add(newStamp);
            this.stampBoard.getChildren().add(newStamp.getAll());

        } else { //want this to be called if there are covered boxes left over

            //we need to uncover a box
            getFirstCovered().uncover(); //RESET THE LABELZZ
        }

        //reset the labels
        boolean needsLabelsReset = false;
        double nxtEnd = 0; //just the default val ideally it is switched

        for(int i = 0; i < boxes.size(); i++){

            TimeStamp currStamp = boxes.get(i);

            if(needsLabelsReset){ //switches the labels by taking the previous ending and mak

                TimeStamp prevStamp = boxes.get(i - 1);
                String prevEnd = prevStamp.getEnd();
                String currEnd = currStamp.getEnd();
                currStamp.reset(prevEnd, String.valueOf(nxtEnd));
                nxtEnd = Double.valueOf(currEnd);

            } else if (inRange(currStamp, Double.valueOf(timeStamp))){

                String currStart = currStamp.getStart();
                nxtEnd = Double.valueOf(currStamp.getEnd());
                currStamp.reset(currStart, timeStamp);
                needsLabelsReset = true;
            }
        }
    }

    //helper method to calculater where the time marker should go on the progress marker based on the duration of audio left
    public double calcX(){
        return (Constants.PROGRESS_BAR_WIDTH*this.currDur)/this.totDur;
    }

    public void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.KF_DUR), (ActionEvent e) -> update());
        this.time = new Timeline(kf);
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    //shortens given paths in the array to only get the song names
    public String shortener(String file){

        String[] chars = file.split(Constants.FORWARD_SL);
        String fileName = chars[6];
        String title = "";
        int count = 0;

        for (int counter = fileName.toCharArray().length - 1; counter >= 0; counter--) {
            if(counter > 3){
                title = title + fileName.toCharArray()[count];
                count++;
            }
        }

        return title;
    }

    //sets all the buttons for playing and pausing audio etc.
    public void setGUI(){

        HBox buttons = new HBox();
        HBox alignEl = new HBox();

        //check boxes for the time stamps
        VBox align = new VBox();
        this.checkBs = new VBox();
        this.checkBs.setLayoutX(Constants.CHECK_BOX_LAYOUTX);
        buttons.setSpacing(Constants.BUTTON_SPACING);

        //play and pause buttons
        Button playButton = addPlayButton();
        Button pauseButton = addPauseButton();

        pauseButton.setOnAction((ActionEvent e) -> pauseMusic());
        playButton.setOnAction((ActionEvent e) -> playMusic());

        Button loadButton = new Button(Constants.LD);
        loadButton.setOnAction((ActionEvent e) -> loadFile());

        //playlist
        ChoiceBox pl = addPlayList();
        pl.setMaxWidth(Constants.CHOICEBOX_WIDTH);

        //progress bar
        BorderPane progressPane = new BorderPane();
        progressPane.setMaxHeight(Constants.PPANE_MAX_HEIGHT);
        progressPane.setMaxWidth(Constants.PPANE_MAX_WIDTH);
        setUpProgress(progressPane);

        align.getChildren().addAll(progressPane, pl);
        buttons.getChildren().addAll(loadButton, pauseButton, playButton, t, align, addQuit(), this.checkBs);
        alignEl.getChildren().addAll(align, buttons);

        TimeStamp stamp = new TimeStamp(Constants.START_TIME, String.valueOf(this.totDur));
        boxes.add(stamp);
        this.stampBoard.getChildren().add(stamp.getAll());
        this.stampBoard.setSpacing(Constants.STAMP_BOARD_SPACING);

        this.miniBoard.getChildren().addAll(alignEl, stampBoard);
        this.miniBoard.setSpacing(Constants.MINI_BOARD_SPACING);

        this.board.getChildren().add(miniBoard);
    }

    //overwrites a pre-existing file if there was one - loads it to the corresponing audio folder into a txt file
    public void loadFile(){

        String name = plist.getValue() + Constants.WAVE_TG; //name of the audio and a .wav format to get the folder name
        String path = Constants.ANIME_PATH + name + Constants.FORWARD_SL + name + Constants.TXT_TAG;
        String folderPath = Constants.ANIME_PATH + name;

        File audioDir = new File(folderPath);
        File[] existingFiles = audioDir.listFiles(); //check for time stamp files - if existing - scrub to make room for new ones
        for(int u = 0; u < existingFiles.length; u++){
            if(!existingFiles[u].getName().equals(name + Constants.TXT_TAG)){
                existingFiles[u].delete();
            }
        }

        int count = 0;

        //flushes any text to clean the file for new settings t
        try{
            FileWriter fw = new FileWriter(path, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
        }catch(Exception exception){
            System.out.println(Constants.FLUSH_ERR + exception.getMessage());
        }

        for(int i = 0; i < this.boxes.size(); i++){
            count = i;
            TimeStamp st = this.boxes.get(i);

            if(!st.getCoverStatus() || i == 0){

                String start = st.getStart();
                String end = st.getEnd();
                String img = st.getSelectedImage().split("'")[1];
                System.out.println(img);

                try {
                    FileWriter output = new FileWriter(path, true); //false means do not append -- write over contents
                    output.write(start + " . " + end + " / " + img + "\n");
                    output.close();
                    System.out.println(Constants.FIL + name + Constants.WT);
                }

                catch (Exception e) {
                    System.out.println(Constants.ANIME_WRITE_ERR + e.getStackTrace());
                }

            } else {
                break;
            }
        }
        if(count >= 1){
            this.play.pause(); //pauses the music if it was playing
            Animater letsGo = new Animater(folderPath, path, this.board); //starts up the animation screen
        } //more than one time stamp, something must have been loaded yayyyy
    }

    //takes the time stamps are resets them to the first one as if no time stamps are selected
    public void resetStampBoard(String start, String end){
        for(int i = 0; i < boxes.size(); i++){
            if(i == 0){
                boxes.get(i).reset(Constants.START_TIME, (String.valueOf(this.totDur)));
            } else {
                boxes.get(i).cover();
            }
        }
    }

    //sets up the progress bar and the time marker
    public void setUpProgress(BorderPane pPane){

        pPane.setLayoutY(Constants.PPANE_LAYOUTY);
        Rectangle line = new Rectangle();
        line.setWidth(Constants.PROGRESS_BAR_WIDTH);
        line.setHeight(Constants.PROGRESS_BAR_HEIGHT);
        line.setFill(Color.DARKGREY);

        Rectangle padding1 = new Rectangle(Constants.PADDING_WIDTH, Constants.PADDING_HEIGHT);
        Rectangle padding2 = new Rectangle(Constants.PADDING_WIDTH, Constants.PADDING_HEIGHT);

        this.pain = new Pane();
        pain.setLayoutY(Constants.CHOICEBOX_WIDTH);
        pain.getChildren().addAll(line, progressMarker);

        pPane.setTop(padding1);
        pPane.setBottom(padding2);
        pPane.setCenter(pain);

    }

    //pauses the audio
    public void pauseMusic(){
        this.time.pause();
        this.play.pause();
    }

    //plays the audio
    public void playMusic(){

        int index = Constants.DEFAULT_SONG_NUMBER;

        //figure out if the value of the choicebox indicates a different song needs to be played
        if(plist.getValue() != null){
            for(int i = 0; i < playList.size(); i++){
                if(plist.getValue().equals(playList.get(i))){
                    index = i;
                }
            }
        }

        if(currentSong != index){

            //if the selected song is new switch the song
            this.play.stop();
            this.med = new Media(songs.get(index).toURI().toString());
            this.play = new MediaPlayer(med);


            MediaView view = new MediaView(play);
            this.board.getChildren().add(view);
            this.setUpTimeline();
            this.t.setText(Constants.START_TIME);

            for(int i = 0; i < timeMarkers.size(); i++){
                this.pain.getChildren().remove(timeMarkers.get(i));
                this.checkBs.getChildren().remove(checks.get(i));
            }

            this.checks = new ArrayList<CheckBox>();
            this.timeMarkers = new ArrayList<Circle>();
            this.stamp2time = new HashMap<>();
            songSwitched = true;
            count = 0;
        }

        currentSong = index;
        this.time.play();
        this.play.play();
        this.currDur = this.med.getDuration().toSeconds();
    }

    //adds the choiceBox below the progress bar to allow the user to choose which audio they want
    public ChoiceBox addPlayList(){

        // Create the ChoiceBox
        plist = new ChoiceBox<>();

        // Set the list of Course items to the ChoiceBox
        plist.setItems(playList);

        return plist;
    }

    //adds the button to exit the program - each stage animation window has one
    public Button addQuit(){
        Button quitButton = new Button(Constants.QUIT);
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        quitButton.setFocusTraversable(false);
        quitButton.setLayoutX(Constants.APP_WIDTH);
        return quitButton;
    }

    public Button addPauseButton(){

        //Creating a graphic (image)
        Image img = new Image(Constants.PAUSE_IMG);
        ImageView view = new ImageView(img);
        view.setFitHeight(Constants.IMG_DIM);
        view.setFitWidth(Constants.IMG_DIM);
        view.setPreserveRatio(true);

        //Creating a Button
        Button button = new Button();

        //Setting the location of the button
        button.setTranslateX(Constants.FILLER);
        button.setTranslateY(Constants.FILLER);

        //Setting the size of the button
        button.setPrefSize(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);

        //Setting a graphic to the button
        button.setGraphic(view);

        return button;
    }

    public Button addPlayButton(){

        //Creating a graphic (image)
        Image img = new Image(Constants.PLAY_IMG);
        ImageView view = new ImageView(img);
        view.setFitHeight(Constants.IMG_DIM);
        view.setFitWidth(Constants.IMG_DIM);
        view.setPreserveRatio(true);

        //Creating a Button
        Button button = new Button();

        //Setting the location of the button
        button.setTranslateX(Constants.FILLER);
        button.setTranslateY(Constants.FILLER);

        //Setting the size of the button
        button.setPrefSize(Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);

        //Setting a graphic to the button
        button.setGraphic(view);

        return button;
    }

    //helper to get how much of the audio has elapsed
    public String getTS(){
        return "" + this.currDur;
    }

    //pane for the progress bar used to track the progress of the audio
    public Pane getProgressPane(){
        return this.pain;
    }

    //red ellipse which marks where in the song we are (time)
    public Circle getTimeMarker(){
        return this.progressMarker;
    }

    //returns the time markers that have been selected by the user via checked boxes and labels
    //** only really works if the timeline is playing - not paused -- paused music affects detection
    //TODO: bug - kind of to fix - also think about the program having a one way flow - if I wanted to go back through the stages - I'd just have to start over
    public ArrayList<CheckBox> getChecks(){
        return checks;
    }

    public Pane getRoot(){
        return checkBs;
    }

    public String getStatus(boolean t){
        if(t){
            return Constants.TRUE;
        } else {
            return Constants.FALSE;
        }
    }

    //key handler which causes a timestamp to be added to the checklist every time
    //the user presses t and this is demonstrated visually by a yellow ellipse marker on the
    //progress bar
    private class KeyHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent e){

            String command = e.getText();

            if(command.equals(Constants.T)){

                Circle timeCirc = new Circle(Constants.TS_RAD, Constants.TS_UNCHECKED_FILL);
                timeCirc.setCenterX(getTimeMarker().getCenterX());
                timeCirc.setCenterY(getTimeMarker().getCenterY());
                String timeStamp = getTS();
                CheckBox check = new CheckBox(timeStamp);
                check.setTextFill(Constants.TEXT_FILL);

                if(!stamp2time.containsKey(timeStamp)){

                    stamp2time.put(getTS(), timeCirc);
                    timeMarkers.add(timeCirc);
                    getChecks().add(check);
                    status.put(check, getStatus(check.isSelected()));
                    getProgressPane().getChildren().add(timeCirc);
                    getRoot().getChildren().add(check);

                }
            }
        }
    }
}
