package com.uhuiapp.harness;

import com.uhuiapp.harness.testng.ExtentTestNGIReporterListener;
import com.uhuiapp.harness.utils.QAContext;
import org.apache.commons.cli.*;
import org.testng.TestNG;

import java.util.Arrays;
import java.util.List;


public class qatool {
    public static void main(String[] args){

        Options options = getCommandOptions();
        try {
            parserCommandLineArgs(args, options);
        }catch (Exception e){
            System.out.println(e.getMessage());
            printHelpMessages(options);
            System.exit(1);
        }

        TestNG testNG = new TestNG();
        testNG.setVerbose(-1);
        testNG.setTestSuites(QAContext.qAconfig.getTestSuites());
        testNG.setOutputDirectory(QAContext.qAconfig.getTestNGOutputDirectory());
        //testNG.addListener(new ExtentTestNGIReporterListener());
        testNG.run();
        System.exit(testNG.getStatus());
    }

    private static void parserCommandLineArgs(String[] args, Options options) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if(cmd.hasOption('h')){
            printHelpMessages(options);
        }
        List<String>  appTypes = Arrays.asList("android", "ios", "windows" ,"web");
        if(cmd.hasOption('t')){
            if(appTypes.contains(cmd.getOptionValue('t'))){
                QAContext.qAconfig.setAppType(cmd.getOptionValue('t'));
            }else {
                System.out.println("The app type must be:  android|ios|windows|web");
                printHelpMessages(options);
            }
        }else{
            System.out.println("-t parameter is required, please add the parameter.");
            printHelpMessages(options);
        }
    }

    private static void printHelpMessages(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("AppiumHarness", options);
        System.exit(0);
    }

    private static Options getCommandOptions() {
        Options options = new Options();
        options.addOption("t","apptype",true, "Give the app type which be tested. This parameter can be android|ios|windows|web .");
        options.addOption("h","help",false, "Print this help messages.");
        return options;
    }
}
