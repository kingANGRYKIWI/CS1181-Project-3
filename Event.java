

class Event implements Comparable<Event>{

    private Customer customer;
    private double time;
    

    //constructor for events
    Event(Customer customer, SimClock clock){
        this.customer = customer;
        this.time = clock.currentTime();
    }

    //pulls the time the customer arrives at the event
    protected double timeCheck(){
        return time;
    }

    //returns that customer at that event
    protected Customer getCustomer(){
        return customer;
    }

    @Override
    //compares by time 
    public int compareTo(Event other) {
        if(this.timeCheck()> other.timeCheck()){
            return 1;
        } else if(this.timeCheck() < other.timeCheck()){
            return -1;
        } else {
        return 0;
        }
    }
    
}
