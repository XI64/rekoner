package hyp.imd;

public class CPU {
    //Route Bus and RAM to CPU

    private String A_register;
    private String B_register;
    private Bus bus;
    private int cstate; //current state

    ///Hardwiriring for now.
    private final static int READ_MEM0_HEADER = 1;
    private final static int READ_MEM0 = 2;
    private final static int DATA_READ_A = 3;
    private final static int UTIL_A = 4;

    private final static int READ_MEM1_HEADER = 5;
    private final static int READ_MEM1 = 6;
    private final static int DATA_READ_B = 7;
    private final static int UTIL_B = 8;

    private final static int ADD_SIGNAL = 9;
    private final static int ADD = 10;

    private final static int WRITE_MEM2_HEADER = 11;
    private final static int WRITE_MEM2 = 12;
    private final static int HALT_SIGNAL = 13;
    private final static int HALT = 14;

    CPU(Bus bus) {
        this.bus = bus;
        cstate = READ_MEM0_HEADER; //Default to this
    }

    // current state evolver
    void execute(){
        switch (cstate) {
            case READ_MEM0_HEADER:
                bus.setComsg(RAM.RAM_READ);
                bus.setMemAdr(0);
                cstate++; //While not a proper way to do this, but I think this is good enough for state switching
            case READ_MEM0:
                // Explicit use of RAM.ACK since the ack token is only one side system ticket for now.
                if (bus.getComsg().equals(RAM.ACK)) {
                    cstate = DATA_READ_A;
                }
                break;
            case DATA_READ_A:
                A_register = bus.getDataExch();
                cstate++;
            case UTIL_A:
                cstate = READ_MEM1_HEADER;
                break;
            case READ_MEM1_HEADER:
                bus.setComsg(RAM.RAM_READ);
                bus.setMemAdr(1);
                cstate++;
            case READ_MEM1:
                if (bus.getComsg().equals(RAM.ACK)) {
                    cstate = DATA_READ_B;
                }
                break;
            case DATA_READ_B:
                B_register = bus.getDataExch();
                cstate++;
            case UTIL_B:
                cstate = ADD_SIGNAL;
                break;
            case ADD_SIGNAL:
                A_register = Integer.toString(Integer.parseInt(A_register) + Integer.parseInt(B_register));
            case ADD:
                cstate = WRITE_MEM2_HEADER;
                break;
            case WRITE_MEM2_HEADER:
                bus.setComsg(RAM.RAM_WRITE);
                bus.setDataExch(A_register);
                bus.setMemAdr(2);
                    cstate++;
            case HALT_SIGNAL:
                cstate++;
            case HALT:
                break; //Completely break execution.
        }
    }
    public boolean haleted(){
        return cstate == HALT;
    }
}
