/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

public class Simulation {

    public CEventList list;
    public Queue[] queues_regular;
    public Queue[] queues_service;
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
        // Queues for the machines
        queues_regular = new Queue[6];
        queues_regular[0] = new Queue("Regular");
        queues_regular[1] = new Queue("Regular");
        queues_regular[2] = new Queue("Regular");
        queues_regular[3] = new Queue("Regular");
        queues_regular[4] = new Queue("Regular");
        queues_regular[5] = new Queue("Regular");
        queues_service = new Queue[1];
        queues_service[0] = new Queue("Service");

        // A source
        source_reg = new Source(queues_regular,list,"Regular", IA_time_1); //source of service desk customers
        source_ser = new Source(queues_service,list,"Service", IA_time_2); //source of regular customers
        // A sink
        sink = new Sink("Sink 1");
        // A machine
        mach = new Machine[6];
        mach[0] = new Machine(queues_regular[0], queues_service[0], sink, list,"Desk 1", service_time_2, true); //the service desk
        mach[1] = new Machine(queues_regular[1], null, sink, list,"Desk 2", service_time_1, false);
        mach[2] = new Machine(queues_regular[2],null, sink, list,"Desk 3", service_time_1, false);
        mach[3] = new Machine(queues_regular[3],null, sink, list,"Desk 4", service_time_1, false);
        mach[4] = new Machine(queues_regular[4],null, sink, list,"Desk 5", service_time_1, false);
        mach[5] = new Machine(queues_regular[5], null, sink, list,"Desk 5", service_time_1, false);


    }
    
}
