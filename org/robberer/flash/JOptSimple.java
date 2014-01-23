package org.robberer.flash;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;

import static java.util.Arrays.asList;

public class JOptSimple {

    private final String[] args;
    private final static String newline = "\n";
    private OptionParser p;
    protected OptionSet opt;

    protected JOptSimple(String[] args) {
        this.args = args;
    }

    public void initParser()  throws IOException {
        //OptionParser parser = new OptionParser( "fc:q::" );
        p = new OptionParser();

        p.acceptsAll(asList("add", "a"), "add a new flash entry");
        p.accepts("subject", "subject of the new flash entry")
                .requiredIf("add")
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("string");

        p.accepts("tags", "additional search tags")
                .requiredIf("add")
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("string");

        p.acceptsAll(asList("del", "d"), "delete an existing flash entry");

        p.accepts("id", "id of the flash entry")
                .requiredIf("del")
                .withRequiredArg()
                .ofType(Integer.class)
                .describedAs("id");

        p.acceptsAll(asList("search", "s"), "search for a flash entry.")
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("string");

        p.acceptsAll(asList("help", "?", "h"), "this help").forHelp();

        //OptionSet opt = p.parse(args);
        parseArguments();

        if (opt.has("h")) {
            printHelp();
        }

        if (opt.has("add") && opt.has("del")) {
            System.out.println("can't use -add and -del together");
        }

        if (opt.has("add") && opt.has("search")) {
            System.out.println("can't use -add and -search together");
        }

        if (opt.has("del") && opt.has("search")) {
            System.out.println("can't use -del and -search together");
        }

        if (opt.has("ftp")) {
            // we know username and password
            // existe if -ftp is set
            String user = (String) opt.valueOf("user");
            String pwd = (String) opt.valueOf("pass");
            System.out.println("user:" + user + " pass:" + pwd);
        }
    }

    public void parseArguments() throws IOException {
        try {
            opt = p.parse(args);
        } catch (OptionException e) {
            System.out.println("ERROR: "+"unknown option"+newline);
            printHelp();
            System.exit(1);
        }
    }

    public void printHelp() throws IOException {
        p.printHelpOn(System.out);
    }
}
