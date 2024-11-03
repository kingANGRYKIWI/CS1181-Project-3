
public class SimClock {

    private double clock;

    //makes the clock
    SimClock(double clock){
        this.clock = clock;
    }

    //returns the time of the clock
    public double currentTime(){
        return clock;
    }

    public double roundTime(){
        return (currentTime()*100)/100;
    }

    //sets the new time
    public void setClock(double newTime){
        clock = newTime;
    }
}
