package Interface;

import Model.Employee;
import Model.Hospital;

public abstract class CommandLineUser extends CommandLine{

    private Employee user;

    public CommandLineUser(Hospital hospital, Employee user){
        super(hospital);
        this.user = user;
    }

}
