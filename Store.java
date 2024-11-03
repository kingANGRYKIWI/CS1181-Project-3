
import java.io.*;
import java.util.*;
import java.util.LinkedList;


public class Store {//Logan Current, CS-1181L-06, 4/3/2022

    public static LinkedList<Customer> readData(String filename) throws FileNotFoundException{
        //reads the file then makes a list of customers
        int nameNum = 0;
        File file = new File(filename);
        Scanner fileScan = new Scanner(file);
        LinkedList<Customer> queueList = new LinkedList<>();
        //what scans the file and makes the data readable for it to be made into a Customer Obj
        while(fileScan.hasNextLine()){
            String line = fileScan.nextLine();
            //holds the nums
            String[] tokens = line.split("\\s+");
            //takes first num then coverts 
            double time = Double.parseDouble(tokens[0]);
            //second num and converts it 
            int numOfItems = Integer.parseInt(tokens[1]);
            //third num then converts it
            double timeTheyTake = Double.parseDouble(tokens[2]);
            //makes the customer
            Customer customer = new Customer(nameNum, time, numOfItems, timeTheyTake);
            //adds it to a list
            queueList.add(customer);
            nameNum++;
        }
        fileScan.close();
        //sorts before returning
        Collections.sort(queueList);
        return queueList;
    }

    public static LinkedList<Event> setEvents(LinkedList<Customer> customers, LinkedList<CheckoutLane> RegularCheckoutLanes, LinkedList<CheckoutLane> ExpressCheckoutLanes){
        //sets the time to 0.00
        SimClock clock = new SimClock(0.00);
        //makes the event queue by smallest time to greatest
        LinkedList<Event> events = new LinkedList<>();

        //holds the customers that are currently shopping but not in a checkout or they havent arrived yet
        LinkedList<Customer> shoppingCustomers = new LinkedList<>();

        while(!customers.isEmpty() || !shoppingCustomers.isEmpty()){

            //makes the arrival event of the customer then moves the customer to shopping list 

            events.add(newArrivalEvent(customers.peek(), clock));
            shoppingCustomers.add(customers.peek());
            customers.poll();

            //makes a new shopping event then takes that customer ou tof the shopping then moves them to their chosen lane
            events.add(newShoppingEvent(shoppingCustomers.peek(), RegularCheckoutLanes, ExpressCheckoutLanes, clock));
            shoppingCustomers.poll();


            //I think the issue with the code is in this logic around this area but unsure how I should fix it, it only allows 1 customer per lane which shouldn't be right if it gets overloaded, I tried many different things to see if I could somehow make it add multiple customers if need be but that never gave me a close enough answer as what I did here to the solution for the "arrival medium.txt" file. This gives me all the right times but the wait time is messed up because of the issue stated above and therefore I couldn't get an accurate arrival time whatsoever. So I apologize for my simulation paper, I tried to write what I had to work with and my two sense.
            

            //checks the lanes if there is a customer checking from express out then makes a end shopping event
            for(CheckoutLane eachLane: ExpressCheckoutLanes){
                //if the list isnt empty then make a newCheckoutEvent
                if(!eachLane.laneQueue.isEmpty()){
                events.add(newCheckoutEvent(eachLane.laneQueue.peek(), clock));
                eachLane.laneQueue.poll();
                    //for each customer left in the list after the head has been pulled then add the wait time per customer together and add to the average wait time
                    for (Customer eachCustomer : eachLane.laneQueue) {
                        addToAverageTime(eachCustomer.getMinuteWait());
                    }
                }
            }

            //checks the lanes if there is a customer checking from express out then makes a end shopping event
            for(CheckoutLane eachLane: RegularCheckoutLanes){
                //if the list isnt empty then make a newCheckoutEvent
                if(!eachLane.laneQueue.isEmpty()){
                events.add(newCheckoutEvent(eachLane.laneQueue.peek(), clock));
                eachLane.laneQueue.poll();
                //for each customer left in the list after the head has been pulled then add the wait time per customer together and add to the average wait time
                    for (Customer eachCustomer : eachLane.laneQueue) {
                        addToAverageTime(eachCustomer.getMinuteWait());
                    }
                }
            }
        }
        Collections.sort(events);
        return events;
    }

    public static Event newArrivalEvent(Customer customer, SimClock clock){
        //sets the clock to the time the customer arrives
        clock.setClock(customer.getTime());
        //returns the new arrival event
        
        return new ArraivalEvent(customer, clock);
    }

    public static Event newShoppingEvent(Customer customer, LinkedList<CheckoutLane> RegularCheckoutLanes, LinkedList<CheckoutLane> ExpressCheckoutLanes, SimClock clock){

        //sets the clock to the time they got done shopping
        clock.setClock(customer.timeTakenToGoToCheckout());

        //sets the finished shoppping time on the customer
        customer.setFinShopping(clock);
        CheckoutLane lane = null;
        //logic to see if the customer is able to use exress or regular
        if(customer.getOrMore()){
            //if they are both empty then they chose the express lane
            if(ExpressCheckoutLanes.peek().getLaneQueue().isEmpty() && RegularCheckoutLanes.peek().getLaneQueue().isEmpty() 
            //if the express is smaller than the regular checkout line then they go to express
            || ExpressCheckoutLanes.peek().laneQueue.size() > RegularCheckoutLanes.peek().getLaneQueue().size()){

                ExpressCheckoutLanes.peek().addCustomer(customer);
                lane = ExpressCheckoutLanes.peek();
            }  
            
            // if regular is smaller than express then go to smallest regular
            else if(ExpressCheckoutLanes.peek().laneQueue.size() < RegularCheckoutLanes.peek().getLaneQueue().size()){
                RegularCheckoutLanes.peek().addCustomer(customer);
                lane = RegularCheckoutLanes.peek();
            }
        } else {
            //only able to go to smallest regular lane
            RegularCheckoutLanes.peek().getLaneQueue().add(customer);
            lane = RegularCheckoutLanes.peek();
        }

        Collections.sort(RegularCheckoutLanes);
        Collections.sort(ExpressCheckoutLanes);
        
        //sets the lane that the customer chose
        customer.setLaneChosen(lane);
        //sets their wait time based on what lane they chose
        customer.setMinuteWait();

        return new ShoppingEvent(customer,clock);
    }

    public static Event newCheckoutEvent(Customer customer, SimClock clock){
        //what time they got to the front of the line
        customer.setFrontOfLine(clock);
        //calculates the time the customer checkedout
        clock.setClock(customer.getLaneChosen().checkoutTime()+customer.getFinShopping());
        //takes the time to check out for each customer (except for the one checking out) and adds it together

        //returns the event
        return new CheckoutEvent(customer, clock);
    }

    private static void addToAverageTime(double minuteWait) {
        totalTimeAdded += minuteWait;
        timesAdded++;
    }

    public static double calculatedTime(double totalWaitTime, int timesAdded) {
        if(totalWaitTime <= 0){
            totalWaitTime = 0;
        }
        if(timesAdded <= 0){
            timesAdded = 1;
        }
        return totalWaitTime / timesAdded;
    }

    static double caclulatedTime;

    static int timesAdded;

    static double totalTimeAdded;

    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<Customer> allCustomers = readData("arrival.txt");
        LinkedList<CheckoutLane> RegularCheckoutLanes = new LinkedList<>();
        LinkedList<CheckoutLane> ExpressCheckoutLanes = new LinkedList<>();
        //makes the number of regular lanes specified
        for(int i = 0; i<8; i++){
            RegularCheckoutLanes.add(new RegularLane(i));
        }
        //makes the number of express specified
        for(int i = 0; i<4; i++){
            ExpressCheckoutLanes.add(new ExpressLane(i));
        }
        //queue of all events
        LinkedList<Event> events = setEvents(allCustomers, RegularCheckoutLanes, ExpressCheckoutLanes);
        // Outputs terminal to output.txt file
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        //goes through the whole events list and prints them to a file
        while(!events.isEmpty()){
            System.out.println(events.peek());
            events.poll();
        }
        double averageWaitTime = calculatedTime(totalTimeAdded, timesAdded);
        System.out.println("Average wait time: " + averageWaitTime);
        //1733 total > 12 items
        //1267 total <=12 items
    }
}
