package org.robberer.flash;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    private static final String KLASSEN_NAME = "org.robberer.flash.Main";

    public static void main(String[] args) {

        CommandLine lvCmd = null;
        HelpFormatter lvFormater = new HelpFormatter();
        CommandLineParser lvParser = new BasicParser();
        Options lvOptions = new Options();

        Option lvHilfe = new Option("h", "help", false, "show this help");
        Option lvName = new Option("name", true, "der Namen.");

        lvOptions.addOption(lvHilfe);
        lvOptions.addOption(lvName);

        lvOptions.addOption(OptionBuilder
                .withLongOpt("tage")
                .withDescription("die Anzahl der Tage mit Gleichheitszeichen als Separator.")
                .isRequired()
                .withValueSeparator('=')
                .hasArg()
                .create("t"));

        lvOptions.addOption(OptionBuilder
                .withLongOpt("size")
                .withArgName("ARGUMENTEN_NAME")
                .withDescription("ist der Name der Datei.")
                .hasArg()
                .create("s"));

        lvOptions.addOption(OptionBuilder
                .withLongOpt("anzahl")
                .withDescription("die Anzahl der Tage als Pflichtfeld.")
                .isRequired()
                .hasArg()
                .create("a"));

        Option lvGleich = OptionBuilder
                .withLongOpt("gleich")
                .withDescription("die gleiche Anweisung wie oben nur ohne Argument und optional.")
                .isRequired(false)
                .create("g");

        lvOptions.addOption(lvGleich);

        OptionGroup lvGruppe = new OptionGroup();
        lvGruppe.addOption(OptionBuilder
                .withLongOpt("gruppe1")
                .withDescription("der 1. Parameter von der Gruppe")
                .create("g1"));
        lvGruppe.addOption(OptionBuilder
                .withLongOpt("gruppe2")
                .withDescription("der 2. Parameter von der Gruppe")
                .create("g2"));
        lvGruppe.addOption(OptionBuilder
                .withLongOpt("gruppe3")
                .withDescription("der 3. Parameter von der Gruppe")
                .create("g3"));
        lvOptions.addOptionGroup(lvGruppe);

        try {
            lvCmd = lvParser.parse(lvOptions, args);

            if (lvCmd.hasOption('h')) {
                lvFormater.printHelp(KLASSEN_NAME, lvOptions);
                return;
            }

        } catch (ParseException pvException) {
            lvFormater.printHelp(KLASSEN_NAME, lvOptions);
            System.out.println("Parse Fehler:" + pvException.getMessage());
            return;
        }

        System.out.println("Der Namen        :" + lvCmd.getOptionValue("name"));
        System.out.println("Der Size mit s   :" + lvCmd.getOptionValue("s"));
        System.out.println("Die Anzahl mit a :" + lvCmd.getOptionValue("a"));
        System.out.println("Gleich g ist     :" + lvCmd.hasOption("g"));
        System.out.println("Gruppe  1        :" + lvCmd.hasOption("g1"));
        System.out.println("Gruppe  2        :" + lvCmd.hasOption("g2"));
        System.out.println("Gruppe  3        :" + lvCmd.hasOption("g3"));


    }
}
