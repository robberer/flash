package org.robberer.flash;

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 1/19/14 1:16 PM robberer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    public static void main(String[] a) throws Exception {
        Connection conn = null;
        String tab = "TESTTABELLE";

        try {

            Class.forName("org.h2.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:h2:db", "", "");

            DatabaseMetaData md = conn.getMetaData();

            String[] types = { "TABLE", "SYSTEM TABLE" };

            ResultSet metaRS = md.getTables(null, null, "%", types);

            while (metaRS.next()) {

                // Catalog
                String tableCatalog = metaRS.getString(1);
                System.out.println("Catalog: " + tableCatalog);

                // Schemata
                String tableSchema = metaRS.getString(2);
                System.out.println("Tabellen-Schema: " + tableSchema);

                // Tabellennamen
                String tableName = metaRS.getString(3);
                System.out.println("Tabellen-Name: " + tableName);

                // Tabellentyp
                String tableType = metaRS.getString(4);
                System.out.println("Tabellen-Typ: " + tableType + "\n");
            }

            Statement stmt = conn.createStatement();

            String dropQ = "DROP TABLE IF EXISTS " + tab;
            stmt.executeUpdate(dropQ);

            String createQ = "CREATE TABLE IF NOT EXISTS "
                    + tab
                    + "(ID INT PRIMARY KEY AUTO_INCREMENT(1,1) NOT NULL, NAME VARCHAR(255))";
            stmt.executeUpdate(createQ);

            String insertQ = "INSERT INTO " + tab
                    + " VALUES(TRANSACTION_ID(),'Hello World!')";
            stmt.executeUpdate(insertQ);

            ResultSet selectRS = stmt.executeQuery("SELECT * FROM " + tab);
            while (selectRS.next()) {
                System.out.printf("%s, %s\n", selectRS.getString(1),
                        selectRS.getString(2));
            }

            System.out.println("Liste Tabellen...");
            String tablesQ = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC'";
            ResultSet tablesRS = stmt.executeQuery(tablesQ);
            while (tablesRS.next()) {
                System.out.printf("Tabelle %s vorhanden \n",
                        tablesRS.getString(1));
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}