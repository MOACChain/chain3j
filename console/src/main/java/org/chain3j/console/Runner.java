package org.chain3j.console;

import org.chain3j.codegen.Console;
import org.chain3j.codegen.SolidityFunctionWrapperGenerator;
import org.chain3j.codegen.TruffleJsonFunctionWrapperGenerator;
import org.chain3j.utils.Version;

import static org.chain3j.utils.Collection.tail;

/**
 * Main entry point for running command line utilities.
 */
public class Runner {

    private static String USAGE = "Usage: chain3j version|wallet|solidity ...";

    private static String LOGO = "\n" // generated at http://patorjk.com/software/taag
                                + " ██████╗██╗  ██╗ █████╗ ██╗███╗   ██╗██████╗      ██╗ \n"
                                + "██╔════╝██║  ██║██╔══██╗██║████╗  ██║╚════██╗     ██║ \n"
                                + "██║     ███████║███████║██║██╔██╗ ██║ █████╔╝     ██║ \n"
                                + "██║     ██╔══██║██╔══██║██║██║╚██╗██║ ╚═══██╗██   ██║ \n"
                                + "╚██████╗██║  ██║██║  ██║██║██║ ╚████║██████╔╝╚█████╔╝ \n"
                                + " ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝╚═════╝  ╚════╝  \n";


    public static void main(String[] args) throws Exception {
        System.out.println(LOGO);

        if (args.length < 1) {
            Console.exitError(USAGE);
        } else {
            switch (args[0]) {
                case "wallet":
                    WalletRunner.run(tail(args));
                    break;
                case "solidity":
                    SolidityFunctionWrapperGenerator.run(tail(args));
                    break;
                case "truffle":
                    TruffleJsonFunctionWrapperGenerator.run(tail(args));
                    break;
                case "version":
                    Console.exitSuccess("Version: " + Version.getVersion() + "\n"
                            + "Build timestamp: " + Version.getTimestamp());
                    break;
                default:
                    Console.exitError(USAGE);
            }
        }
    }
}
