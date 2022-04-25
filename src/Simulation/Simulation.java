/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

public class Simulation {

    public CEventList list;
    public Queue queue;
    public Source source_reg;
    public Source source_ser;
    public Sink sink;
    public Machine [] mach;
	

        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    double mean_reg_inter_arrival_time = 1;
    double mean_service_inter_arrival_time = 5;
    double mean_reg_service_time = 2.6;
    double mean_service_time = 4.1;
	Simulation simulation = new Simulation(mean_reg_inter_arrival_time, mean_service_inter_arrival_time, mean_reg_service_time, mean_service_time);

	// start the eventlist
	simulation.list.start(2000); // 2000 is maximum time

    }

    public Simulation(double IA_time_1, double IA_time_2, double service_time_1, double service_time_2){

        // Create an eventlist
        list = new CEventList();
        // A queue for the machine
        queue = new Queue();
        // A source
        source_reg = new Source(queue,list,"Source Reg", IA_time_1);
        source_ser = new Source(queue,list,"Source Ser", IA_time_2);
        // A sink
        sink = new Sink("Sink 1");
        // A machine
        mach = new Machine[6];
        mach[0] = new Machine(queue,sink,list,"Desk 1", true);
        mach[1] = new Machine(queue,sink,list,"Desk 2", false);
        mach[2] = new Machine(queue,sink,list,"Desk 3", false);
        mach[3] = new Machine(queue,sink,list,"Desk 4", false);
        mach[4] = new Machine(queue,sink,list,"Desk 5", false);
        mach[5] = new Machine(queue,sink,list,"Desk 5", false);


    }
    
}
