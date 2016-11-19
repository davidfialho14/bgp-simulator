package v2.core;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static v2.core.InvalidRoute.invalidRoute;
import static v2.stubs.Stubs.stubRoute;
import static v2.wrappers.PathWrapper.path;
import static v2.wrappers.RouteTableWrapper.emptyTable;
import static v2.wrappers.RouteTableWrapper.table;
import static v2.wrappers.TopologyWrapper.node;

public class RouteTableTest {

    @Test
    public void getRoute_OnEmptyTable_InvalidRoute() throws Exception {
        Node anyNeighbor = node(0);
        assertThat(emptyTable().getRoute(anyNeighbor), is(invalidRoute()));
    }

    @Test
    public void
    setRoute_WithAttr1AndEmptyPathForNeighbor0OnEmptyTable_RouteWithAttr1AndEmptyPath() throws Exception {
        RouteTable table = emptyTable();

        table.setRoute(node(0), stubRoute(1, path()));

        assertThat(table.getRoute(node(0)), is(stubRoute(1, path())));
    }

    @Test
    public void setRoute_WithAttr1ForNeighbor0OnEmptyTable_SelectedRouteWithAttr1() throws Exception {
        RouteTable table = emptyTable();

        table.setRoute(node(0), stubRoute(1, path()));

        assertThat(table.getSelectedRoute(), is(stubRoute(1, path())));
    }

    @Test
    public void
    setRoute_WithAttr2ForNeighbor0OnTableAlreadyContainingNeighbor0WithAttr1_RouteWithAttr2() throws Exception {
        RouteTable table = emptyTable();
        table.setRoute(node(0), stubRoute(1, path()));

        table.setRoute(node(0), stubRoute(2, path()));

        assertThat(table.getRoute(node(0)), is(stubRoute(2, path())));
    }

    @Test
    public void
    setRoute_WithAttr2ForNeighbor0OnTableOnlyContainingNeighbor0WithAttr1_SelectedRouteWithAttr2() throws Exception {
        RouteTable table = table()
                .entry(0, stubRoute(1, path()))
                .build();

        table.setRoute(node(0), stubRoute(2, path()));

        assertThat(table.getSelectedRoute(), is(stubRoute(2, path())));
    }

    @Test
    public void
    setRoute_WithAttr2ForNeighbor1OnTableOnlyContainingNeighbor0WithAttr1_SelectedRouteWithAttr2() throws Exception {
        RouteTable table = table()
                .entry(0, stubRoute(1, path()))
                .build();

        table.setRoute(node(1), stubRoute(2, path()));

        assertThat(table.getSelectedRoute(), is(stubRoute(2, path())));
    }

    @Test
    public void
    setRoute_WithAttr2ForNeighbor1OnTableOnlyContainingNeighbor0WithAttr1_SelectedNeighbor1() throws Exception {
        RouteTable table = table()
                .entry(0, stubRoute(1, path()))
                .build();

        table.setRoute(node(1), stubRoute(2, path()));

        assertThat(table.getSelectedNeighbour(), is(node(1)));
    }

    @Test
    public void
    setRoute_InvalidForNeighbor1OnTableContainingNeighbors0And1WithAttrs1And2_SelectedRouteWithAttr1() throws Exception {
        RouteTable table = table()
                .entry(0, stubRoute(1, path()))
                .entry(1, stubRoute(2, path()))
                .build();

        table.setRoute(node(1), invalidRoute());

        assertThat(table.getSelectedRoute(), is(stubRoute(1, path())));
    }

    @Test
    public void
    setRoute_InvalidForNeighbor1OnTableContainingNeighbors0And1WithAttrs1And2_SelectedNeighbor0() throws
            Exception {
        RouteTable table = table()
                .entry(0, stubRoute(1, path()))
                .entry(1, stubRoute(2, path()))
                .build();

        table.setRoute(node(1), invalidRoute());

        assertThat(table.getSelectedNeighbour(), is(node(0)));
    }

    @Test
    public void
    getAlternativeRoute_FromEmptyTable_InvalidRoute() throws Exception {
        Node anyNeighbor = node(0);

        assertThat(emptyTable().getAlternativeRoute(anyNeighbor), is(invalidRoute()));
    }

    @Test
    public void
    getAlternativeRoute_IgnoringNeighbor0FromTableWithOnlyNeighbor0_InvalidRoute() throws Exception {
        RouteTable table = table()
                .entry(0, stubRoute(1, path()))
                .build();

        assertThat(table.getAlternativeRoute(node(0)), is(invalidRoute()));
    }

    @Test
    public void
    getAlternativeRoute_IgnoringNeighbor0FromTableWithNeighbor0AndNeighbor1WithAttrs1And2_RouteWithAttr2() throws Exception {
        RouteTable table = table()
                .entry(0, stubRoute(1, path()))
                .entry(1, stubRoute(2, path()))
                .build();

        assertThat(table.getAlternativeRoute(node(0)), is(stubRoute(2, path())));
    }

    @Test
    public void
    getAlternativeRoute_IgnoringNeighbor1FromTableWithNeighbor0AndNeighbor1WithAttrs1And2_RouteWithAttr1() throws Exception {
        RouteTable table = table()
                .entry(0, stubRoute(1, path()))
                .entry(1, stubRoute(2, path()))
                .build();

        assertThat(table.getAlternativeRoute(node(1)), is(stubRoute(1, path())));
    }

}