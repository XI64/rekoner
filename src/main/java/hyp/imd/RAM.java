package hyp.imd;

public class RAM {
    final static int  DEFAULT_SIZE = 256; //Default size of the cells array
    private final String[] cells; //Memory Cells


    final static String RAM_READ = "RAM_READ";
    final static String RAM_WRITE = "RAM_WRITE";
    final static String ACK = "ACK"; //Acknowledgement for the CPU to sense from bus
   //To curb of the risk of a typo we define them as constants and `static` to establish one eye projection


    private final Bus bus;

    public RAM(Bus bus){
        this.bus = bus;
        cells = new String[DEFAULT_SIZE];
    }

    @Deprecated
    RAM(Bus bus, int size){
        this.bus = bus;
        cells = new String[size];
    }

    public void initialized(String[] initVal){
        if (initVal.length > cells.length) {
            System.err.println("Error initializing RAM: " + "Initial array is bigger than current RAM instance");
            System.exit(0);
        }
        System.arraycopy(initVal, 0, cells, 0, initVal.length);
    }


    public String giveCell(int i){
        return cells[i];
    }

    boolean execute() {
        if(bus.getComsg().equals(RAM_READ)) {
            bus.setDataExch(cells[bus.getMemAdr()]);
            bus.setComsg(ACK);
            return true;
        } else if (bus.getComsg().equals(RAM_WRITE)) {
            cells[bus.getMemAdr()] = bus.getDataExch();
            bus.setComsg(ACK); //Signal Handling <-?
            return true;
        }
        return false;
    }


    void dump(){

    }
}
