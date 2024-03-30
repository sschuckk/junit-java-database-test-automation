package org.schuck.database.services;

import org.schuck.dto.SchemaObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SchemaParsingService {
    private final String schemaFileName;
    private ArrayList<SchemaObject> lstSchemaObject;


    public SchemaParsingService(String schemaFile) {
        this.schemaFileName = schemaFile;
        parseSchema();
    }

    private void parseSchema() throws RuntimeException {
        lstSchemaObject = new ArrayList<SchemaObject>();
        String line;

        // When used try block the method close() from BufferedReader is called automatically.
        try (BufferedReader reader = new BufferedReader(new FileReader(schemaFileName))) {
            while ((line = reader.readLine()) != null) {
                SchemaObject objSchemaObject = new SchemaObject();

                // Skip comments, empty lines, and irrelevant lines
                if (line.trim().startsWith("--") || line.trim().isEmpty() || line.trim().startsWith(")")) {
                    continue;
                }

                if (line.trim().startsWith("CREATE")) {
                    continue;
                }

                if (line.trim().startsWith("PRIMARY")) {
                    continue;
                }

                // Remove backticks and commas from the line
                line = line.replaceAll("[`,]", "").trim();

                // Split the line by space
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    objSchemaObject.setColumnName(parts[0]);
                    objSchemaObject.setColumnType(parts[1]);
                } else {
                    System.err.println("Invalid line: " + line);
                }

                lstSchemaObject.add(objSchemaObject);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Schema file not found: " + schemaFileName, e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading schema file: " + schemaFileName, e);
        }
    }

    public String getColumnName(Integer index) {
        return lstSchemaObject.get(index).getColumnName();
    }

    public String getColumnType(Integer index) {
        return lstSchemaObject.get(index).getColumnType();
    }

    public Integer getColumnQty() {
        return lstSchemaObject.size();
    }
}
