package producerconsumer;

public interface QueueInterface {

    public void put(String obj) throws InterruptedException;
    public String take() throws InterruptedException;
    
    /*
     * comments here    
     */
    public void interrupting();
}
