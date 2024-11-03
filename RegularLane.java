import java.util.LinkedList;


public class RegularLane extends CheckoutLane{

    //makes the regular checkout lane
    public RegularLane(int laneNum) {
        super(laneNum);
        this.laneQueue = new LinkedList<Customer>();
    }

    @Override
    //returns the type of lane it is
    public String typeOfLane() {
        return "regular";
    }

    @Override
    //the checkout times for regualr lanes
    public double checkoutTime() {
        return (getCurrentCustomer().getItems() * 0.05) + 2.0;
    }

    //toString
    public String toString(){
        return "Regular lane "+ getLaneNum();
    }

}
