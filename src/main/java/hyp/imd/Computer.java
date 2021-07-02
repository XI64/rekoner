package hyp.imd;

/*
 * Design Choices:
 * 1. Usage of String attributes instead of integer attributes : Readability
 * 2. Hypothetical Instruction Set : This is just a demo project
 * 3. String defined cells : Readability
 *
 * All in all this is just a demo (beginner) project so the choices are very noobish as well as
 * rudimentary.
 * */

public class Computer {
    public final Bus bus = new Bus();
    CPU cpu = new CPU(bus);
    RAM ram = new RAM(bus);

    void boot(){
        int nst = 2;
        for (int step = 0; step < nst; ++step) {
            cpu.execute();
            if(ram.execute()){

            }
        }


    }
}
