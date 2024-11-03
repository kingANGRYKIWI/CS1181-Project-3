

public class CheckoutEvent extends Event{

    private final double time;

    //makes the checkout event
    public CheckoutEvent(Customer customer, SimClock clock){
        super(customer, clock);
        this.time = clock.roundTime();
    }

    //returns the event's time
    @Override
    public double timeCheck(){
        return time;
    }

    //sees the wait time in that lane
    public double minuteWait(){
        CheckoutLane theLane = this.getCustomer().getLaneChosen();
        double total = 0.00;
        if(!theLane.laneQueue.isEmpty()){
            //check lane type
            if(theLane.typeOfLane().equals("express")){
                //goes through the whole list except for the first index(customer who is checking out) and adds all the times up for the wait time
                for (int i = 1; i < theLane.laneQueue.size(); i++) {
                    total += (theLane.laneQueue.get(i).getItems() * 0.10) + 1.0;
                }
            } else {
                for (int i = 1; i < theLane.laneQueue.size(); i++) {
                    total += (theLane.laneQueue.get(i).getItems() * 0.05) + 2.0;
                }
            }
        }
        return total;
    }


    //toString 
    public String toString(){
        return time+" Finished Checkout "+ getCustomer().toString()+ " on " + getCustomer().getLane()+" ("+getCustomer().getLaneChosen().queueString()+") "+"(" +minuteWait()+" minuite wait, " + getCustomer().getLaneChosen().laneQueue.size() + " people in line -- Finished shopping at " + getCustomer().getFinShopping()+", got to the front of the line at "+getCustomer().getFrontOfLine()+")";
    }
}
