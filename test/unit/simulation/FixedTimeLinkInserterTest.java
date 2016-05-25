package simulation;

import addons.linkinserters.FixedTimeLinkInserter;
import network.Network;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static wrappers.StubWrapper.stubLink;

@RunWith(MockitoJUnitRunner.class)
public class FixedTimeLinkInserterTest {

    @Mock
    private Network network;

    private FixedTimeLinkInserter linkInserter;

    @Test
    public void insertAnyLink_CurrentTimeIs1InsertTimeIs2_DoesNotInsert() throws Exception {
        linkInserter = new FixedTimeLinkInserter(stubLink(0, 1), 2);
        long currentTime = 1;

        assertThat(linkInserter.insertAnyLink(network, currentTime), is(nullValue()));
    }

    @Test
    public void insertAnyLink_CurrentTimeIs1InsertTimeIs1_InsertsLink() throws Exception {
        linkInserter = new FixedTimeLinkInserter(stubLink(0, 1), 1);
        long currentTime = 1;

        assertThat(linkInserter.insertAnyLink(network, currentTime), not(nullValue()));
    }

    @Test
    public void insertAnyLink_CurrentTimeIs2InsertTimeIs1_InsertsLink() throws Exception {
        linkInserter = new FixedTimeLinkInserter(stubLink(0, 1), 1);
        long currentTime = 2;

        assertThat(linkInserter.insertAnyLink(network, currentTime), not(nullValue()));
    }
}