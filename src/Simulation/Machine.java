package Simulation;



/**
 *	Machine in a factory
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Machine implements CProcess,ProductAcceptor
{
	/** Product that is being handled  */
	private Product product;
	/** Eventlist that will manage events */
	private final CEventList eventlist;
	/** Queue 1 from which the machine has to take products */
	private Queue queue1; //queue for regular customers
	/** Queue 2 from which the machine has to take products */
	private Queue queue2; //queue for service customers only
	/** Sink to dump products */
	private ProductAcceptor sink;
	/** Status of the machine (b=busy, i=idle) */
	private char status;
	/** Machine name */
	private final String name;
	/** Mean processing time */
	private double meanProcTime;
	/** Type of Desk */
	private boolean service;


	/**
	*	Constructor
	*        Service times are exponentially distributed with specified mean
	*	@param q1	Queue 1 from which the machine has to take products
	*   @param q2   Queue 2 from which the machine can take products    (service customers)
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*   @param m	Mean processing time
	*	@param t 	Service or not
	*/
	public Machine(Queue q1, Queue q2, ProductAcceptor s, CEventList e, String n, double m, boolean t)
	{
		status='i';
		queue1=q1;
		queue2=q2;
		sink=s;
		eventlist=e;
		name=n;
		meanProcTime=m;
		service = t;
		if(service) {
			queue2.askProduct(this);
		}
		queue1.askProduct(this);
	}
	

	/**
	*	Method to have this object execute an event
	*	@param type	The type of the event that has to be executed //not needed
	*	@param tme	The current time
	*/
	public void execute(int type, double tme)
	{
		// show arrival
		System.out.println("Product finished at time = " + tme);
		// Remove product from system
		product.stamp(tme,"Production complete", name);
		sink.giveProduct(product);
		product=null;
		// set machine status to idle
		status='i';
		// Ask the queue for products
		if(service) {
			if(queue2.getRow().size()>0) {
				queue2.askProduct(this);
			}
			else{
				queue1.askProduct(this);
			}
		}
		else {
			queue1.askProduct(this);
		}
	}
	
	/**
	*	Let the machine accept a product and let it start handling it
	*	@param p	The product that is offered
	*	@return	true if the product is accepted and started, false in all other cases
	*/
        @Override
	public boolean giveProduct(Product p)
	{
		// Only accept something if the machine is idle
		if(status=='i')
		{
			// accept the product
			product=p;

			// mark starting time
			product.stamp(eventlist.getTime(),"Production started",name);
			// start production
			startProduction();
			// Flag that the product has arrived
			return true;
		}
		// Flag that the product has been rejected
		else return false;
	}
	
	/**
	*	Starting routine for the production
	*	Start the handling of the current product with an exponentionally distributed processingtime with average 30
	*	This time is placed in the eventlist
	*/
	private void startProduction()
	{
		// generate duration
		if(meanProcTime>0)
		{
			double duration = drawRandomExponential(meanProcTime);
			// Create a new event in the eventlist
			double tme = eventlist.getTime();
			eventlist.add(this,0,tme+duration); //target,type,time
			// set status to busy
			status='b';
		}
		else
		{
			eventlist.stop();
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