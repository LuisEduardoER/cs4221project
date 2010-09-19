public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Control control = new Control();
        int maxFD = 10, width = 2;

        ///Get the line of argument
        String input = "";
        for (int i = 0; i < args.length; i++){
            input = input.concat(args[i]);
        }
        System.out.println(input);

        ///Create array of functional dependency and process to get the value
        String[][]fds = new String[maxFD][width];
        fds = control.readInput(input, maxFD, width);

        //Print the functional dependency
        control.printFD(fds);
    }
}
