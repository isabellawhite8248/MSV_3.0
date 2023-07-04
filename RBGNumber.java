//class used to store the RBG number of a color to dissect in the
//animeboard class when gettting color fills

public class RBGNumber {
    private double cv;
    public RBGNumber(double colorValue){
        this.cv = colorValue;
    }
    public double getCV(){
        return cv;
    }
}
