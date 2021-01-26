package controller.command;

import controller.Controller;

public enum Command {
    create_customer {
        @Override
        public void execute(String[] args) {
            String firstName = args[1];
            String lastName = args[2];
            //...

            //validation logic
            //String -> (String) or (int) or (double) or (boolean) or (...)

            //controller.createCustomer(...);
        }
    },

    create_employee {
        @Override
        public void execute(String[] args) {
            //...
        }
    }

    //...
    ;

    Controller controller = new Controller();

    public void execute(String[] args) {}

}
