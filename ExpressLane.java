import java.util.LinkedList;


public class ExpressLane extends CheckoutLane{

    //makes the express checkout lane
    ExpressLane(int laneNum) {
        super(laneNum);
        this.laneQueue = new LinkedList<Customer>();
    }

    @Override
    //returns the type of lane it is
    public String typeOfLane() {
        return "express";
    }

    @Override
    //the checkout times for express lanes
    public double checkoutTime() {
        return (getCurrentCustomer().getItems() * 0.10) + 1.0;
    }

    //toString
    public String toString(){
        return "Express lane "+ getLaneNum();
    }
}
