

public class ArraivalEvent extends Event{

    private final double time;

    //makes the arrival event
    public ArraivalEvent(Customer customer, SimClock clock){
        super(customer, clock);
        this.time = clock.roundTime();
    }

    //returns the event's time
    @Override
    public double timeCheck(){
        return time;
    }

    //toString
    public String toString(){
        return time+": Arrival "+ getCustomer().toString();
    }
}
