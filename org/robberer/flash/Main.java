package org.robberer.flash;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        JOptSimple jopt = new JOptSimple(args);
        jopt.initParser();
    }
}
