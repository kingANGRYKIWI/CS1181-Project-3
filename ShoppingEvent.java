

public class ShoppingEvent extends Event{
    
    private final double time;

    //makes the event
    public ShoppingEvent(Customer customer, SimClock clock){
        super(customer, clock);
        this.time = clock.roundTime();
    }

    //returns the event's time
    @Override
    public double timeCheck(){
        return time;
    }

    //shows if they have 12 or more items then puts it into a string for the event
    public String moreThan12(){
        if(getCustomer().getOrMore()){
            return " 12 or fewer ";
        } else {
        return " More than 12 items ";
        }
    }

    //toString
    public String toString(){        
        return time+" Finished Shopping " + getCustomer().toString()+moreThan12() + "chose " + getCustomer().getLane();
    }
}
