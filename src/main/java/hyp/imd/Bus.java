package hyp.imd;

public class Bus {

    private String dataExch;
    private String comsg;
    private int memAdr;

    public void setDataExch(String data){
        this.dataExch = data;
    }

    public String getDataExch(){
        return dataExch;
    }

    public void setComsg(String command){
        this.comsg = command;
    }

    public String getComsg(){
        return comsg;
    }

    public void setMemAdr(int adr){
        this.memAdr = adr;
    }

    public int getMemAdr(){
        return memAdr;
    }

    //For commands issued by CPU and messages from other components
    public static String command(){
        return " ";
    }

    //For data between CPU <=> components
    public static String data(){

        return "";
    }

    // Address for memory cell.
    public static int address(){
        return 0;
    }



}
