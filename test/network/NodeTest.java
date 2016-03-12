package network;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import policies.DummyAttribute;
import policies.DummyAttributeFactory;
import policies.DummyLabel;
import protocols.DummyProtocol;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NodeTest {

    @Mock
    protected Network mockNetwork;
    @Mock
    protected DummyProtocol stubProtocol;
    @Mock
    protected RouteTable stubRouteTable;

    protected DummyAttributeFactory attributeFactory = new DummyAttributeFactory();
    protected Node node;
    protected Node exportingNode;
    protected Node destination;
    protected Link outLink;
    protected Link inLink;
    protected Route learnedRoute;

    @Before
    public void setUp() throws Exception {
        when(mockNetwork.getAttrFactory()).thenReturn(new DummyAttributeFactory());

        node = new Node(mockNetwork, 0, this.stubProtocol);
        node.startTable();
        node.routeTable = stubRouteTable;
        exportingNode = new Node(mockNetwork, 1, stubProtocol);
        destination = new Node(mockNetwork, 2, stubProtocol);
        outLink = new Link(node, exportingNode, new DummyLabel());
        node.addOutLink(outLink);
        inLink = new Link(exportingNode, node, new DummyLabel());
        node.addInLink(inLink);
        learnedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
    }

    @After
    public void tearDown() throws Exception {
        reset(stubProtocol);
        reset(stubRouteTable);
    }

}