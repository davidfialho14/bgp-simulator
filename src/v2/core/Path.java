package v2.core;


import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

import static v2.core.InvalidPath.invalidPath;


/**
 * Represents a path to a destination
 */
public class Path implements Comparable<Path>, Iterable<Router> {

    private LinkedList<Router> path = null;   // must be a LinkedList in order to preserve insertion order

    /**
     * Constructs an empty path.
     */
    public Path() {
        this.path = new LinkedList<>();
    }

    /**
     * Creates a new path with a single router.
     * @param router router to initiate the path with.
     */
    public Path(Router router) {
        this.path = new LinkedList<>();
        path.add(router);
    }

    /**
     * Constructs a path given a sequence of routers. Routers are added to the path in the same order as 
     * the argument parameters.
     * 
     * @param routers routers to initiate the path with.
     */
    public Path(Router... routers) {
        this.path = new LinkedList<>();
        Collections.addAll(path, routers);
    }

    /**
     * Only to be used to create path internally.
     */
    private Path(LinkedList<Router> path) {
        this.path = path;
    }

    /**
     * Creates a copy of the given path. If the given path is the invalid path it returns the invalid path.
     *
     * @param path path to be copied (may be an invalid path).
     * @return new copy of the given path or the invalid path if the given path is the invalid path.
     */
    public static Path copy(Path path) {
        if (path == invalidPath()) {
            return invalidPath();
        } else {
            return new Path(new LinkedList<>(path.path));
        }
    }

    /**
     * Adds a new router to the path. If the router already exists in the path then it will no be added.
     * @param router router to be added to the path.
     */
    public void add(Router router) {
        path.addFirst(router);
    }

    /**
     * Checks if the path contains the given router.
     * @param router router to check if the path contains.
     * @return true if the path contains the router and false otherwise.
     */
    public boolean contains(Router router) {
        return this != invalidPath() && path.contains(router);
    }

    /**
     * Returns the path after the given router. If the router does not exist an empty path is returned.
     * If the path is invalid an invalid path is returned as well.
     *
     * @param router router to get path after.
     * @return path after the router or empty path if the router is not found or invalid if the path is invalid.
     */
    public Path getPathAfter(Router router) {
        if (this == invalidPath()) return invalidPath();

        // start with an empty path
        Path pathAfterRouter = new Path();

        Iterator<Router> routerItr = path.iterator();

        // start by finding in the path the router in question
        while (routerItr.hasNext()) {
            if (routerItr.next().equals(router)) {
                // found the router
                break;
            }
        }

        // add to the new path, all routers in the path after the router in question
        routerItr.forEachRemaining(pathAfterRouter.path::add);

        return pathAfterRouter;
    }

    /**
     * Returns the sub-path until reaching the ending router. The sub-path returned includes all router from start until
     * the ending router (inclusive). If the router is never found it returns null.

     * @param endingRouter ending router.
     * @return sub-path until reaching the ending router or null if the ending router does not exist.
     */
    public Path getSubPathBefore(Router endingRouter) {
        LinkedList<Router> subpath = new LinkedList<>();

        for (Router router : path) {
            subpath.add(router);

            if (router.equals(endingRouter)) {
                return new Path(subpath);
            }
        }

        return null; // ending router was not found
    }

    /**
     * Returns the source router of the path. Source router is the initial router in the path.
     *
     * @return the source router of the path.
     */
    public Router getSource() {
        return path.getFirst();
    }

    /**
     * Returns the destination router of the path. Source router is the initial router in the path.
     *
     * @return the destination router of the path.
     */
    public Router getDestination() {
        return path.getLast();
    }

    /**
     * Compares the path to another path. The comparison only takes into account the number of routers in the path
     * the specific routers it contains are not taken into account.
     *
     * @param other path to be compared with.
     * @return a negative integer, zero, or a positive integer as this path is less than, equal to or greater than
     * the specified object.
     */
    @Override
    public int compareTo(Path other) {
        return this.path.size() - other.path.size();
    }

    /**
     * Two paths are considered equal if they have the same routers in the same order.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path that = (Path) o;

        return path != null ? path.equals(that.path) : that.path == null;
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Path" + path;
    }

    /**
     * Returns an iterator over routers of the path.
     *
     * @return an Iterator of routers.
     */
    @Override
    public Iterator<Router> iterator() {
        return path.iterator();
    }

    public Stream<Router> stream() {
        return path.stream();
    }

    /**
     * Returns an iterator over the in-links in the path
     *
     * @return an Iterator of in-links.
     */
    public Iterator<Link> inLinksIterator() {
        return new InLinksIterator();
    }

    private class InLinksIterator implements Iterator<Link> {

        private Iterator<Router> iterator = Path.this.path.descendingIterator();
        private Router currentRouter = iterator.next(); // all paths have at least one router

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         */
        @Override
        public Link next() {
            Router neighbour = iterator.next();
            Link nextLink = currentRouter.getInLink(neighbour);
            currentRouter = neighbour;

            return nextLink;
        }

    }

}
