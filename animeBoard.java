import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import movements.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
//TODO: for a future feature -- could update so during the preview pane it plays that specific section of the audio when the animation is running

public class animeBoard {

    private ColorPicker backgroundCol;
    private Timeline time;
    private int fileNum; //keep track of anime files by num.txt
    private String ts;
    private String img;
    private Boolean infoSaved;
    private Groupizer groupMaker;
    private Label DotNum;
    private BorderPane frame;
    private String pathToPic;
    private HBox dotTogglerPane;
    private VBox dotGToMovement;
    private HashMap<String, ArrayList<Circle>> groupNametoDotG;
    private ArrayList<String> dotGNames;
    private int numGroups;
    private ArrayList<ColorPicker> colorPicks;
    private HashMap<RadioButton, ColorPicker> rbToclr; //contains tuples of radio button and color picker objects to return the color associated with that radio button
    private ArrayList<Circle> circles;
    private ArrayList<objStatus> statusOfCircles;
    private HashMap<Circle, RBGNumber> dotToColor; //hashmaps which maps the circle to the RBG
    private Pane circPane;
    private VBox dotGSettings;
    private ToggleGroup dGs;
    private Button saveInformation;
    private ArrayList<ChoiceBox> moveChoices;
    private boolean maskOGFills;
    private Button seeOGFills;
    private ImageView OGImg;
    private VBox grLayout;
    private VBox UGGRPane;
    private RadioButton selectedRB;
    private String fp;
    private ScrollPane scrollable;
    private int numUserGrps;

    /*
    class stores the animation controls for a selected time stamp which can be toggled by the rbg
    buttons in the animation class, features a OG fills button which shows the original picture
    a button to toggle default groups back and forth -- assigns movements via the choice box
    in the pane to the right, if save is checked this group will be saved to the final animation file
    also has an event handler to check for user input. If you choose a group via the radio button
    this will prompt the user to click dots and add them to this group -- if already added click again
    to remove the dot. The load button loads all information with a time stamp of when it was loaded
    and the preview will pull up a pane with how the final animation will look for that time stamp
    in the music video.
     */

    public animeBoard(String image, String timeStamp, int index, String animeFilePath) { //animeFilePath is the animation file path

        fp = animeFilePath;
        rbToclr = new HashMap<>();
        statusOfCircles = new ArrayList<>();
        grLayout = new VBox();
        UGGRPane = new VBox();
        numUserGrps = 0;
        OGImg = new ImageView();
        maskOGFills = true;
        this.moveChoices = new ArrayList<>();
        dGs = new ToggleGroup();
        circPane = new Pane();
        circPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, new selectDots()); //detects for user click or mouse drag
        circPane.addEventHandler(MouseEvent.MOUSE_PRESSED, new selectDots());


        colorPicks = new ArrayList<>();
        circles = new ArrayList<>();
        dotToColor = new HashMap<>();
        numGroups = Constants.DEF_GRNUM; //the default number of groups
        dotGNames = new ArrayList<>();
        groupNametoDotG = new HashMap<>();
        infoSaved = false; //only set to true if a file exists with info already
        img = image;
        dotTogglerPane = new HBox();
        this.setUpTimeline();
        ts = timeStamp;
        pathToPic = Constants.IMG_PATH_HEAD + image;
        fileNum = index;
        dotGToMovement = new VBox();

        //arranges board components GUI
        frame = new BorderPane();
        frame.setPadding(new Insets(Constants.BOARD_PADDING));

        Color frameFill = Color.WHITE;
        frame.getChildren().add(new Rectangle(Constants.FR_WIDTH, Constants.FR_HEIGHT, frameFill));

        //sets the circles in the middle
        setHashMap(); //granulizes the image initially into 3 dot groups - can be altered
        frame.setCenter(circPane);

        // top the image label
        Label time = new Label(image);
        time.setFont(new Font(Constants.TITLE_FONT_SIZE));
        frame.setTop(time);

        dotGSettings = new VBox();
        dotGSettings.setSpacing(Constants.DOTGPANE_SPACING);

        Button getNewGroup = new Button(Constants.NEW_GR_LAB);
        getNewGroup.setOnAction((ActionEvent e) -> createUserGenGr());
        dotGSettings.getChildren().add(getNewGroup);

        //right hand side list of dot groups - default is 3/ list of buttons to drop down animations in a list
        for (int i = 0; i < numGroups; i++) {
            VBox groupPane = createNewGroupGUI(i, false);
            dotGSettings.getChildren().addAll(groupPane);
        }

        scrollable = new ScrollPane();
        scrollable.setPrefViewportHeight(Constants.SCROLLP_HEIGHT);
        grLayout.getChildren().addAll(dotGSettings, UGGRPane);
        scrollable.setContent(grLayout);

        frame.setRight(scrollable);

        //bottom set the dot group toggler with the num of dots up and down arrows and a label (HBOX)
        HBox toggles = new HBox();
        toggles.setLayoutY(Constants.UPPER_BOUND);

        Button preview = new Button();
        preview.setText(Constants.PREV_MSG);
        preview.setOnAction((ActionEvent e) -> preview());

        saveInformation = new Button();
        saveInformation.setText(Constants.SAVE_MSG);
        saveInformation.setOnAction((ActionEvent e) -> saveInfo());

        seeOGFills = new Button();
        seeOGFills.setText(Constants.OG_COLS);
        seeOGFills.setOnAction((ActionEvent e) -> seeOGFills());

        DotNum = new Label(Constants.DEF_DOTNUM_LABEL);
        Button up = new Button(Constants.UP);
        up.setOnAction((ActionEvent e) -> changeDotCount(true));

        Button down = new Button(Constants.DOWN);
        down.setOnAction((ActionEvent e) -> changeDotCount(false));

        Label lab = new Label(Constants.BG_COL_LAB);
        backgroundCol = new ColorPicker();

        toggles.getChildren().addAll(up, DotNum, down, preview, saveInformation, seeOGFills, lab, backgroundCol);
        toggles.setMaxHeight(Constants.TOGGLE_PANE_HEIGHT);

        frame.setBottom(toggles);
        initializeStatus();

    }

    /*
    initializes a type objStatus for each circle, this stores the original color before the user clicks the
    circle and the new color to change it to, if the user changes it back it also stores the previous fill of the
    circle.
     */
    public void initializeStatus() {
        for (Circle c : circles) {
            statusOfCircles.add(new objStatus(c));
        }
    }

    /*
    update function updates with the timeline to make sure that the selected radio button global variable
    updates with user input
     */
    public void update() {
        if (dGs.getSelectedToggle() != null) {
            selectedRB = (RadioButton) dGs.getSelectedToggle();
        }
    }

    public void stopTime() {
        time.stop();
    }

    public void setUpTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(1), (ActionEvent e) -> update());
        this.time = new Timeline(kf);
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    /*
    sets the original image in the left portion of the borderpane, that way the
    user can see what it looked like before depixelation
     */
    public void seeOGFills() {
        //shows original colors by unmasking the OG image
        if (maskOGFills) {
            //unmask
            Image i = new Image(Constants.IMG_PATH_HEAD + img);
            OGImg.setImage(i);
            frame.setLeft(OGImg);
            maskOGFills = false;
            seeOGFills.setText(Constants.OG_COLS_ACTIVATED);
        } else {
            frame.getChildren().remove(OGImg);
            maskOGFills = true;
            seeOGFills.setText(Constants.OG_COLS);
        }
    }

    public void createUserGenGr() {
        VBox newUGroup = createNewGroupGUI(numUserGrps, true);
        UGGRPane.getChildren().add(newUGroup);
    }

    /*
        sets up a color picker for a new group == checks if it was user generated or
        generated by default
         */
    public ColorPicker createColorPicker(int index, boolean userGenerated) { //index is to identify what array index the color picker is picking for

        ColorPicker colorPicker = new ColorPicker();
        if (userGenerated) {
            colorPicker.setValue(Constants.USER_GEN_DEF_COL);
        } else {
            colorPicker.setValue(Constants.DEF_COL);
        }

        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                Color c = colorPicker.getValue();
                if (index < 0 || index > (colorPicks.size() - 1)) {
                    System.out.println(Constants.COLORPICKER_ERROR);
                } else {
                    colorPicks.get(index);
                    if (!userGenerated) {
                        Group g = groupMaker.getGroup(index + 1);
                    }
                }
            }
        });
        return colorPicker;
    }

    //controls the deafault group counts more groups makes image more clear / pixelated
    public void changeDotCount(boolean up) {

        if (up) {
            if (numGroups < 9) {
                numGroups++;
                DotNum.setText(String.valueOf(numGroups));
                resetScreen(numGroups, true);
            }

        } else {
            if (numGroups > 1) {
                numGroups--;
                DotNum.setText(String.valueOf(numGroups));
                resetScreen(numGroups, false);
            }
        }

    }

    //when a new group is created, settings are added for the user to program it in the GUI to the right
    public VBox createNewGroupGUI(int groupNum, boolean userGenerated) {

        //creates a vBox for the collection of information for animating that group
        VBox groupPane = new VBox();
        RadioButton dotGroup;

        if (userGenerated) {
            numUserGrps++;
            dotGroup = new RadioButton(Constants.USER_GEN + numUserGrps);
        } else {
            dotGroup = new RadioButton(dotGNames.get(groupNum));
        }

        dotGroup.setToggleGroup(dGs);

        File dir = new File(Constants.MOVEMENTS_PATH);
        File[] movements = dir.listFiles();
        ArrayList<String> moves = new ArrayList<>();

        if (movements != null) {
            for (File file : movements)
                moves.add(file.getName()); //adds the potential animations in the movements file path
        }

        CheckBox saveOrnot = new CheckBox(Constants.SAVE_BTN_MSG);

        //choice box with a movement selection for the dot group
        ChoiceBox<String> movementChoices = new ChoiceBox<String>();
        moveChoices.add(movementChoices);

        //Retrieving the observable list
        ObservableList<String> list = movementChoices.getItems();

        //Adding items to the list
        for (int y = 0; y < moves.size(); y++) {
            list.add(moves.get(y));
        }

        ColorPicker colorPicker;
        //color picker for the dot group
        if (userGenerated) {
            colorPicker = createColorPicker(groupNum, true);
        } else {
            colorPicker = createColorPicker(groupNum, false);
            colorPicks.add(colorPicker);
        }

        rbToclr.put(dotGroup, colorPicker);
        groupPane.getChildren().addAll(dotGroup, movementChoices, colorPicker, saveOrnot);

        return groupPane;
    }

    public void deleteGroup() {

        int lastEl = dotGSettings.getChildren().size() - 1;
        colorPicks.remove(colorPicks.size() - 1);
        dotGSettings.getChildren().remove(lastEl);
        moveChoices.remove(moveChoices.size() - 1);

    }

    //when a default group num is altered groups are reset - circles are filled in according to the
    //reset group num
    public void resetScreen(int groupNum, boolean up) {

        //update dot group list on the right
        if (up) {
            VBox v = createNewGroupGUI(groupNum, false);
            dotGSettings.getChildren().add(v);
        } else {
            deleteGroup();
        }

        //what we want is to have all of the groups set and loaded before that way we can just retrieve the hashmaps and grab the fills
        Group gr = groupMaker.getGroup(groupNum);
        HashMap<Circle, Color> fillsFinder = gr.getFills();
        ArrayList<Color> colorKey = gr.getCols();

        for (int i = 0; i < this.circles.size(); i++) {
            Circle circ = circles.get(i);
            Color c = fillsFinder.get(circ);
            int colorPickerInd = 0;
            for (int j = 0; j < colorKey.size(); j++) {
                if (colorKey.get(j) == c) {
                    colorPickerInd = j;
                    break;
                }
            }
            Color newFill = colorPicks.get(colorPickerInd).getValue();
            circ.setFill(newFill);
        }
        //add the circles back to the pane

    }

    //takes the string of 2d points that is read from the animeFile for this specific time stamp
    //and creates an arraylist of circles at those locations and returns it
    public ArrayList<Circle> parseGroup(String s){

        ArrayList<Circle> circles = new ArrayList<>();
        Circle c = new Circle();
        String[] parse = s.split(Constants.SP);
        String key = Constants.KEY_INITIALIZER;

        for(int i = 0; i < parse.length; i++){
            if(parse[i].equals(key)){
                //if it equals the x
                if(key.equals(Constants.KEY_INITIALIZER)){
                    c = new Circle();
                    String line = (parse[i + 2].split(Constants.COMMA)[0]);
                    key = Constants.KEY_NEW;
                    c.setCenterX(Double.valueOf(line));
                } else { //if it equals the y
                    String line = (parse[i + 2].split(Constants.COMMA)[0]).split(Constants.CLOSE_BR)[0];
                    key = Constants.KEY_INITIALIZER;
                    c.setCenterY(Double.valueOf(line));
                    c.setRadius(Constants.DOT_RADIUS);
                    circles.add(c);
                }
            }
        }
        return circles;
    }

    //takes in a string with the color values (rbg) and returns a color with those values
    public Color parseCol(String line){
        String[] rbg = line.split(Constants.COMMA);
        float[] RBG = new float[4];
        for(int i = 0; i < 4; i++){
            RBG[i] = (Float.parseFloat(rbg[i]));
        }
        Color backdrop = new Color(RBG[0], RBG[1], RBG[2], RBG[3]);
        return backdrop;
    }

    //stops the preview by removing the preview pane from the frame and adding back the original circles
    public void stopPreview(Pane root, ArrayList<Node> children){

        for(Node n : children){
            root.getChildren().remove(n);
        }
        frame.getChildren().remove(root);
        frame.setCenter(circPane);
    };

    //preview pane pops up and displays what the animation will look like for that time stamp
    public void preview() {

        String newFileName = getFPath() + Constants.FORWARD_SL + fileNum + Constants.TXT_TAG;

        //read the file
        BufferedReader reader;
        Rectangle backG = new Rectangle(Constants.PREV_BG_WIDTH, Constants.PREV_BG_HEIGHT);
        HashMap<String, ArrayList<Circle>> moveToDots = new HashMap<>();

        try {

            String key = "";
            ArrayList<Circle> storage = new ArrayList<>();
            reader = new BufferedReader(new FileReader(newFileName));
            String line = reader.readLine();
            int lineNum = 0;

            while (line != null) {
                lineNum++;
                if (lineNum == 2){
                    //backgroundfill
                    Color backdrop = parseCol(line);
                    backG.setFill(backdrop);
                } else if(lineNum != 1) { //must be line number 3 or greater
                    //group and group information
                    String[] words = line.split(" ");
                    String identifier = words[0];
                        switch (identifier) {
                            case "GROUP":
                                break;
                            case "COORDS":
                                storage = parseGroup(line);
                                break;
                            case "MOVE":
                                key = words[1];
                                break;
                            case "COL":
                                Color c = parseCol(words[1]);
                                for(int i = 0; i < storage.size(); i++){
                                    storage.get(i).setFill(c);
                                }
                                moveToDots.put(key, storage);
                                break;
                            default:
                                break;
                        }
                }
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //start a new timeline have it update then stop if stop button is clicked

        //setup the "stop preview" button
        if(infoSaved){
            Pane p = new Pane();

            ArrayList<Node> prevChildren = new ArrayList<>();
            prevChildren.add(backG);
            p.getChildren().add(backG);

            Object[] ks = moveToDots.keySet().toArray();

            for(int i = 0; i < ks.length; i++){

                String move = ks[i].toString();
                ArrayList<Circle> dots = moveToDots.get(move);
                Color dotCol = (Color)dots.get(0).getFill();

                switch (move) { //TODO: make it so you can alter these paramenters from the user side later
                    case "bigInOut.java":
                        bigInOut b = new bigInOut(dots, 1, dotCol, 1, p);
                        break;
                    case "bounce.java":
                        bounce bc = new bounce(dots, 1, dotCol, p);
                        break;
                    case "deMerge.java":
                        deMerge d = new deMerge(dots, 1, dotCol, p);
                        break;
                    case "merge.java":
                        //TODO: merge not working - fix - needs a borderpane as an input - p is not one
//                        merge m = new merge(dots, 1, dotCol, p);
                        break;
                    case "opacityFlicker.java":
                        opacityFlicker op = new opacityFlicker(dots, 1, dotCol, p);
                        break;
                    case "pixBlink.java":
                        pixBlink px = new pixBlink(dots, 1, dotCol, p);
                        break;
                    case "Rotate.java":
                        Rotate r = new Rotate(1, dots, 1, dotCol, new Point2D(200, 200), p);
                        break;
                    default: //translate.java
                        translate t = new translate(dots, 1, 1, 2, dotCol, p);
                        break;
                }
            }

            //each move has a dot group and a color, what we need is to set up the circles on the pane then tell them what to do

            //add stop preview button to the pane
            Button stop = new Button("STOP PREV");
            prevChildren.add(stop);

            p.getChildren().add(stop);

            stop.setOnAction((ActionEvent e) -> stopPreview(p, prevChildren));
            frame.setCenter(p);

        } else {
            System.out.println("Cannot preview, save information first");
        }
    }

    //take the image - seperate it into 3 dot group hues - color the dots into the 3 grey scale colors then populate
    //populate it by gridizing the image taking the average rbg of each pixel then making a dot from it and creating ranges, these ranges are then set to the dot group
    public void setHashMap() {

        for (int x = 0; x < 10; x++) {
            String label = Constants.GR_NUM_LABL + (x + 1);
            groupNametoDotG.put(label, new ArrayList<>());
            dotGNames.add(label);
        }

        //each time you add a dot group name to the map or update the map - update the groupNametoDotG arraylist with the new names

        File file = new File(Constants.INITIAL_FP + pathToPic);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int RADIUS_DOUBLE = Constants.DOT_RADIUS * 2;

        //used later for dot color groupings in the for loop
        int mark1 = (Constants.MAX_RBG_VAL / numGroups);
        int mark2 = mark1 * 2;

        for (int i = 0; i < (width - RADIUS_DOUBLE); i = i + RADIUS_DOUBLE) {
            for (int h = 0; h < (height - RADIUS_DOUBLE); h = h + RADIUS_DOUBLE) {

                //loop through all the pixels in the box of size 2*radius
                float red = 0; //keeps track of the sum of reds, blues and greens to average together for later
                float blue = 0;
                float green = 0;

                int count = 0;

                for (int x = i; x < (i + RADIUS_DOUBLE); x++) {
                    for (int y = h; y < (h + RADIUS_DOUBLE); y++) {
                        count++;
                        int p = image.getRGB(x, y);
//                        int a = (p>>24)&0xff; -- FIGURE OUT WTF THIS VALUE IS BECAUSE IM NOT SURE
                        float r = (p >> 16) & 0xff;
                        float b = p & 0xff;
                        float g = (p >> 8) & 0xff;
                        red = r + red;
                        blue = b + blue;
                        green = g + green;
                    }
                }

                double one = (int) red / 100;
                double two = (int) blue / 100;
                double three = (int) green / 100;
                Color avg = createRBG(one, two, three); //helper method converts color to greyscale by averaging the 2 rbg vals

                //FIRST GET THE HASHMAP TO UPDATE THEN THE VISUALS
                Circle c = createCircle(i, h, Constants.CIRC_RAD, avg);
                dotToColor.put(c, new RBGNumber(avg.getBlue()));
            }
        }

        groupMaker = new Groupizer(this.circles, dotToColor); //TODO: get the arraylist of dot groups from the getGroups method then toggle based on the button + attatch to the GUI

    }

    public Pane getFrame() {
        return frame;
    }

    public Circle createCircle(int i, int h, double circleRadius, Color col) {
        Point2D center = new Point2D(i + Constants.DOT_RADIUS, h + Constants.DOT_RADIUS);
        Circle circ = new Circle(circleRadius);
        circ.setCenterX(center.getX());
        circ.setCenterY(center.getY());
        circ.setFill(col);
        this.circles.add(circ);
        this.circPane.getChildren().add(circ);
        return circ;
    }

    public Color createRBG(double red, double green, double blue) {
        float r = (float) (red / Constants.MAX_RBG_VAL);
        float g = (float) (green / Constants.MAX_RBG_VAL);
        float b = (float) (blue / Constants.MAX_RBG_VAL);
        float av = ((r + g + b) / 3);
        return new Color(av, av, av, 1.0);
    }

    //saves info into a tfimestamp file, using timestamp int.txt as the file in the proper folder
    public void saveInfo() {

        boolean boxNull = false;
        for (ChoiceBox<String> box : moveChoices) {
            if (box.getValue() == null) {
                boxNull = true;
            }
        }
        if (!boxNull) {
            //if all the boxes are filled - file away the information
            createTSFile();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            saveInformation.setText("Information saved at: " + now);
            infoSaved = true;
        } else {
            saveInformation.setText("fill all boxes! then save.");
        }
    }

    //helper method which finds all the circles on the pane with a particular color, returns an arraylist
    //of type point 2D with their coordinates in a String
    public String getCoords(Color c){
        ArrayList<Point2D> points = new ArrayList<>();
        for(Circle cir : circles){
            if(cir.getFill() == c){
                points.add(new Point2D(cir.getCenterX(), cir.getCenterY()));
            }
        }
        return points.toString();
    }

    //gets the path to the directory of the audio file that we are programming the video for
    public String getFPath(){
        String[] parse = fp.split(Constants.FORWARD_SL);
        String path = "";
        for(int i = 0; i < parse.length; i++) {
            if (i == parse.length - 1) {
                path = path + parse[i];
            } else {
                path = path + parse[i] + Constants.FORWARD_SL;
            }
        }
        return path;
    }

    //creates a time stamp file with the number in form int of the animeboard .txt to store for the visualizer
    public void createTSFile(){

        String path = getFPath();
        fileNum++; //file numbers start at 0, we want them to start with 1
        //path gets us to the appropriate folder to store the files - ex: /Users/isabellawhite/MSV_3.0/src/animation/The Outfield - Your Love (Official HD Video).wav
        String newFileName = path + Constants.FORWARD_SL + fileNum + Constants.TXT_TAG;

        // grLayout should contain two vboxes - one with the default groups and one with the user generated groups
        VBox defGroups = (VBox)grLayout.getChildren().get(0);
        VBox usGroups = (VBox)grLayout.getChildren().get(1);

        ArrayList<VBox> allGroups = new ArrayList<>();

        ObservableList<Node> l1 = defGroups.getChildren();
        for(int y = 1; y < l1.size(); y++){ //first node is the add groups button -- want to skip this because it is not of type vBox
            allGroups.add((VBox)l1.get(y));
        }

        ObservableList<Node> l2 = usGroups.getChildren();
        for(Node n : l2){
            allGroups.add((VBox)n);
        }

        //per each group they all have the group format of children's lst ex: [RadioButton@2896ce02[styleClass=radio-button]'UG Gen Group 1', ChoiceBox@3551698d[styleClass=choice-box], ColorPicker@312bb10e[styleClass=combo-box-base color-picker], CheckBox@36d36d5c[styleClass=check-box]'save group']

        try {
            FileWriter myWriter = new FileWriter(newFileName);
            myWriter.write("" + fileNum + "\n"); //line one file number
            myWriter.write(backgroundCol.getValue().getRed() + "," + backgroundCol.getValue().getBlue() + "," + backgroundCol.getValue().getGreen() + "," + backgroundCol.getValue().getOpacity() + "\n"); //line two background fill
            for(int i = 0; i < allGroups.size(); i++){ //after that pertinent dot group info is recorded
                VBox groupBox = allGroups.get(i); //child one is a radio button, child 2 is choicebox of mov, child 3 colorpicker, child 4 checkbox
                CheckBox save = (CheckBox)groupBox.getChildren().get(3);
                //if the saved checkbox is checked...
                if(save.isSelected()){

                    ChoiceBox move = (ChoiceBox)groupBox.getChildren().get(1);
                    ColorPicker color = (ColorPicker)groupBox.getChildren().get(2);

                    myWriter.write(Constants.GR + (i+ 1) + "\n");
                    //arraylist of points (dot coords)
                    myWriter.write(Constants.CRDS + getCoords(color.getValue())+ "\n");
                    //movement selected
                    myWriter.write(Constants.MV + move.getValue().toString()+ "\n");
                    //color
                    myWriter.write(Constants.COL + color.getValue().getRed() + "," + color.getValue().getBlue() + "," + color.getValue().getGreen() + ","+ color.getValue().getOpacity() +"\n");

                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println(Constants.TS_WRITE_ERR);
            e.printStackTrace();
        }
    }

    public boolean infoIsSaved() {
        //returns true if info exists already
        return infoSaved;
    }

    //controls user manipulation of groups -- enables user to add or detract from a pre selected dot group
    //(if the rbg is selected) will add that dot to the group if clicked, then remove it if clicked again
    private class selectDots implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {

            if (selectedRB != null) {
                //if there is a selected RB -- we want to check the status of fill
                double xCoord = e.getX();
                double yCoord = e.getY();
                for (objStatus obj : statusOfCircles) {
                    //check if the coordinates are in bounds
                    boolean inBound = obj.inBounds(xCoord, yCoord);
                    //if they are, get the selected color from the toggle group and change the status - essentially changing the fill of the circle
                    ColorPicker cp = rbToclr.get(selectedRB);
                    Color currCol = cp.getValue();
                    if (inBound) {
                        if (currCol != null) {
                            obj.changeStatus(currCol);
                        } else {
                            System.out.println(Constants.RBG_NOTSEL_ERR);
                        }
                    }
                }

            }
        }
    }

}



