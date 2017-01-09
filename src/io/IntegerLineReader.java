package io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 09-01-2017.
 *
 * Reader for line by line files with integer values in each line.
 */
public class IntegerLineReader implements Closeable {

    private final BufferedReader reader;

    public IntegerLineReader(File input) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(input));
    }

    public IntegerLineReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    public Integer readValue() throws IOException, ParseException {

        String line;
        int lineCount = 0;
        while((line = reader.readLine()) != null) {

            lineCount++;
            if (line.isEmpty()) continue;   // ignore empty lines

            try {
                return Integer.parseInt(line);

            } catch (NumberFormatException e) {
                throw new ParseException("Expected only integer values", lineCount);
            }
        }

        return null;
    }

    public List<Integer> readValues() throws IOException, ParseException {

        List<Integer> values = new ArrayList<>();

        Integer value;
        while ((value = readValue()) != null) {
            values.add(value);
        }

        return values;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

}
