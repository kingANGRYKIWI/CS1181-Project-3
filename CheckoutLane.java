
import java.util.LinkedList;


public abstract class CheckoutLane implements Comparable<CheckoutLane> {

    protected LinkedList<Customer> laneQueue;
    private final int laneNum;
    protected double minuteWait;

    //constructer constraints for each checkout lane
    protected CheckoutLane(int laneNum){
        this.laneNum = laneNum;
        this.laneQueue = new LinkedList<Customer>();
        this.minuteWait = 0;
    }

    //returns the queue in the lane
    public LinkedList<Customer> getLaneQueue(){
        return laneQueue;
    }

    public void addMinuteWait(double minuteWait) {
        this.minuteWait += minuteWait;
    }

    public double getMinuteWait(){
        return minuteWait;
    }

    //customer at the front of the line
    public Customer getCurrentCustomer(){
        return laneQueue.peek();
    }

    //add the customer to the back of the line
    public void addCustomer(Customer customer){
        laneQueue.offer(customer);
    }

    //remove the first customer
    public void removeCustomer(){
        laneQueue.poll();
    }
    
    //size of the total number in the queue not including the first person
    public String queueString(){
        return laneQueue.size()+"";
    } 

    //lane number
    public int getLaneNum(){
        return laneNum;
    }

    @Override
    //compares by size of the queue
    public int compareTo(CheckoutLane other) {
        if(this.getLaneQueue().size() > other.getLaneQueue().size()){
            return 1;
        } else if(this.getLaneQueue().size() < other.getLaneQueue().size()){
            return -1;
        } else {
            return 0;
        }
    }

    //type of lane it is
    public abstract String typeOfLane();
    
    //checkout time for the lane
    public abstract double checkoutTime();

    @Override
    //toString
    public abstract String toString();

    
}
