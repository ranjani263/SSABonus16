package Simulation;

/**
 *	A source of products
 *	This class implements CProcess so that it can execute events.
 *	By continuously creating new events, the source keeps busy.
 */
public class Source implements CProcess
{
	/** Eventlist that will be requested to construct events */
	private CEventList list;
	/** Queue that buffers products for the machine */
	private Queue[] queues;
	/** Name of the source */
	private String name;
	/** Mean interarrival time */
	private double meanArrTime;


	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with specified mean
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param m	Mean arrival time
	*/
	public Source(Queue[] q,CEventList l,String n,double m)
	{
		queues = q;
		list = l;
		name = n;
		meanArrTime=m;
		// put first event in list for initialization
		if(name.equalsIgnoreCase("Regular")) {
			list.add(this, 0, drawRandomExponential(meanArrTime)); //target,type,time
		}
		else{
			list.add(this, 1, drawRandomExponential(meanArrTime)); //target,type,time
		}
	}


	private int shortest_queue(){
		int min = 0;
		for(int i=0;i<queues.length;i++){
			if(queues[i].getRow().size()<queues[min].getRow().size()){
				min=i;
			}
		}
		return min;
	}
	
        @Override
	public void execute(int type, double tme)
	{
		// show arrival
		// System.out.println("Arrival at time = " + tme);
		// give arrived product to queue
		Product p = new Product(tme, name);
		p.stamp(tme,"Creation",name);
		int min = shortest_queue();
		queues[min].giveProduct(p);
		// generate duration
		if(meanArrTime>0)
		{
			double duration = drawRandomExponential(meanArrTime);
			// Create a new event in the eventlist
			list.add(this,0,tme+duration); //target,type,time
		}
		else
		{
			list.stop();
		}
	}
	
	public static double drawRandomExponential(double mean)
	{
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with mean 33
		double res = -mean*Math.log(u);
		return res;
	}
}