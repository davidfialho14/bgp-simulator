package io;

import org.apache.commons.lang.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by david on 09-01-2017.
 */
public class IntegerLineReaderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static Reader fakeLineFile(String... lines) {
        return new StringReader(StringUtils.join(lines, "\n"));
    }

    private static List<Integer> values(Integer... values) {
        return Arrays.asList(values);
    }

    @Test
    public void readValues_EmptyFile_EmptyList() throws Exception {
        Reader fakeReader = fakeLineFile();

        try (IntegerLineReader reader = new IntegerLineReader(fakeReader)) {
            assertThat(reader.readValues(), is(values()));
        }
    }

    @Test
    public void readValues_FileWithASingleEmptyFile_EmptyList() throws Exception {
        Reader fakeReader = fakeLineFile("");

        try (IntegerLineReader reader = new IntegerLineReader(fakeReader)) {
            assertThat(reader.readValues(), is(values()));
        }
    }

    @Test
    public void readValues_FileWithSingleValue10_ListWithValue10Only() throws Exception {
        Reader fakeReader = fakeLineFile("10");

        try (IntegerLineReader reader = new IntegerLineReader(fakeReader)) {
            assertThat(reader.readValues(), is(values(10)));
        }
    }

    @Test
    public void readValues_FileWithValues10And20InEachLine_ListWithValues10And20() throws Exception {
        Reader fakeReader = fakeLineFile("10", "20");

        try (IntegerLineReader reader = new IntegerLineReader(fakeReader)) {
            assertThat(reader.readValues(), is(values(10, 20)));
        }
    }

    @Test
    public void
    readValues_FileWithEmptyLineAnd10AndEmptyLineAnd20AndEmptyLine_ListWithValues10And20() throws Exception {
        Reader fakeReader = fakeLineFile("", "10", "", "20", "");

        try (IntegerLineReader reader = new IntegerLineReader(fakeReader)) {
            assertThat(reader.readValues(), is(values(10, 20)));
        }
    }

    @Test
    public void readValues_FileWithStringA_ThrowsParseException() throws Exception {
        Reader fakeReader = fakeLineFile("A");

        try (IntegerLineReader reader = new IntegerLineReader(fakeReader)) {
            thrown.expect(ParseException.class);
            reader.readValues();
        }
    }

}
