package Interface;

import Model.Hospital;

public abstract class CommandLine {
    Hospital hospital;

    public CommandLine(Hospital hospital){
        this.hospital = hospital;
    }

    public abstract void run();
}
