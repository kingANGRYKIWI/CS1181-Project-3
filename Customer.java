

public class Customer implements Comparable<Customer>{
    
    private final int nameNum;
    private final double time;
    private final int numOfItems;
    private final double timeTheyTake;
    private boolean orMore;
    private CheckoutLane laneChosen;
    private double finShopping;
    private double frontOfLineTime;
    private double minuteWait;

    //makes the customers and gives them all the fields it needs to pull from and keep track of each
    public Customer(int nameNum, double time, int numOfItems, double timeTheyTake){
        this.nameNum = nameNum;
        this.time = time;
        this.numOfItems = numOfItems;
        this.timeTheyTake = timeTheyTake;
        this.orMore = setOrMore();
        this.finShopping = 0;
        this.frontOfLineTime = 0;
        this.laneChosen = null;
        this.minuteWait = 0;
    }

    //sets the lane they chose
    public void setLaneChosen(CheckoutLane lane){
        this.laneChosen = lane;
    }

    //gets the lane that they chose and the checkout time according to their item amount
    public void setMinuteWait() {
        this.minuteWait = getLaneChosen().checkoutTime();
    }

    //able to check what lane they chose
    public CheckoutLane getLaneChosen(){
        return laneChosen;
    }

    //see what time they checkout
    public double timeTakenToGoToCheckout(){
        return (numOfItems * timeTheyTake) + time;
    }

    //if they can use express lanes or not
    public boolean setOrMore(){
        if(numOfItems > 12) {
            this.orMore = false;
        }else {
            this.orMore = true;
        }
        return orMore;
    }

    //whenever they finished shopping
    public void setFinShopping(SimClock clock){
        this.finShopping = clock.currentTime();
    }

    //whenever they got to the front of the line
    public void setFrontOfLine(SimClock clock){
        this.frontOfLineTime = clock.currentTime();
    }

    //see the their arrival time
    public double getTime(){
        return time;
    }

    //see how many items
    public int getItems(){
        return numOfItems;
    }

    //time they take to shop
    public double timeTaken(){
        return timeTheyTake;
    }

    //the customer number they are
    public int getName(){
        return nameNum;
    }

    //if they use express and or regular
    public boolean getOrMore(){
        return orMore;
    }

    //the lane name they chose
    public String getLane(){
        return laneChosen.toString();
    }

    //when they finished shopping
    public double getFinShopping(){
        return finShopping;
    }

    //when they got through the line
    public double getFrontOfLine(){
        return frontOfLineTime;
    }

    //returns the minute wait in the lane they chose
    public double getMinuteWait(){
        return minuteWait;
    }

    @Override
    //compares times then compares item numbers
    public int compareTo(Customer other) {
        if(this.getTime() > other.getTime()){
            return 1;
        } else if(this.getTime() < other.getTime()){
            return -1;
        } else {
            if(this.getItems() > other.getItems()){
                return -1;
            } else if(this.getItems() < other.getItems()){
                return 1;
            } else{
                return 0;
            }
        }
    }
    //toString
    public String toString(){
        return "Customer: "+nameNum;
    }

}