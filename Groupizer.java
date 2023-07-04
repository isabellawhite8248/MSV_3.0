import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.HashMap;

public class Groupizer {

    private ArrayList<Group> groups;
    private ArrayList<Color> OGFills;

    /*
    class contains an array of groups and generates them based on the group number specified by the user
     */

    public Groupizer(ArrayList<Circle> circs,  HashMap<Circle, RBGNumber> dotToColor){

        this.groups = new ArrayList<>();
        this.OGFills = new ArrayList<>();

        for(int y = 0; y < circs.size(); y++){
            Paint fill = circs.get(y).getFill();
            this.OGFills.add((Color) fill);
        }

        for(int i = 1; i < 10; i++){ //nine available group nums
            this.groups.add(new Group(circs, i, dotToColor, OGFills));
        }

    }

    public Group getGroup(int groupNumber){
        int gN = groupNumber - 1; //the index starts at 0, group numbers need to be slotted the right number
        return groups.get(gN);
    }

}
