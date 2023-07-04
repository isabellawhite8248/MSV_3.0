import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Group {

    private ArrayList<Circle> circles;
    private ArrayList<Point2D> ranges;
    private int numGroups;
    private double increment;
    private HashMap<Circle, Point2D> circleToRangeIndex;
    private HashMap<Circle, Color> circleToColor;
    private ArrayList<Color> originalColors;
    private ArrayList<Color> colors;

    /*
    group stores a hashmap with all the groups - pre programmed so the anime board can toggle back and forth between them
     */

    public Group(ArrayList<Circle> dots, int groupNumber, HashMap<Circle, RBGNumber> dotCol, ArrayList<Color> OGcolors){

        //program should be called 9 times per the number of groups
        //per each group we should have a list of ranges

        this.colors = new ArrayList<>(Arrays.asList(Color.LIMEGREEN, Color.BLACK, Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.GRAY));
        this.originalColors = OGcolors;
        this.circles = dots;
        this.numGroups = groupNumber;
        this.circleToColor = new HashMap<>();
        this.circleToRangeIndex = new HashMap<>();
        this.ranges = new ArrayList<>();
        this.increment = (Constants.MAX_RBG_VAL/groupNumber);

        double last_el = 0, count = 1;

        for(int i = 0; i < numGroups; i++){
            double next_el = count*increment;
            Point2D newRange = new Point2D(last_el, next_el);
            ranges.add(newRange);
            count++;
            last_el = next_el;
        }

        for(int o = 0; o < this.circles.size(); o++){

            Circle currCirc = this.circles.get(o);
            //get the OG color value

            Color col = this.originalColors.get(o);
            //figure out what range it is in -- edge case if it does not fit into any range -- last one - rounds down increments
            Point2D range = getRange(col);

            //assign it a new color based on the range number
            int rNum = ranges.indexOf(range);

            Color newFill = this.colors.get(rNum);

            //update hashmaps accordingly to access later onSwitch
            circleToColor.put(currCirc, newFill);
            circleToRangeIndex.put(currCirc, range);
        }
    }

    //returns the range that a given color is slotted into
    public Point2D getRange(Color c){

        double cv = c.getBlue();
        cv = cv*255;

        for (Point2D dot : ranges){
            if(cv >= dot.getX() && cv <= dot.getY()){
                return dot;
            }
        }

        return ranges.get(ranges.size() - 1); //else return the last range

    }

    public HashMap<Circle, Color> getFills(){
        return circleToColor;
    }

    public ArrayList<Color> getCols(){
        return colors;
    }


}
