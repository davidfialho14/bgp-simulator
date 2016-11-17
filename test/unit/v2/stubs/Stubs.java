package v2.stubs;

import v2.core.Link;
import v2.core.Router;

/**
 * Provides set of static methods to create stub components.
 */
public interface Stubs {

    /**
     * Creates a stub attribute in a more readable way.
     *
     * @param value value of the attribute.
     * @return new stub attribute instance with the given value.
     */
    static StubAttribute stubAttr(int value) {
        return new StubAttribute(value);
    }

    /**
     * Creates a stub label in a more readable way.
     *
     * @return new stub label instance.
     */
    static StubLabel stubLabel() {
        return new StubLabel();
    }

    /**
     * Creates a new link connecting the given source and target routers and associates the link with a stub
     * label. This would be the same as calling: '''new Link(source, target, stubLabel())'''
     *
     * @param source source router.
     * @param target target router.
     * @return a new link instance with a stub label connecting nodes src and dest.
     */
    static Link stubLink(Router source, Router target) {
        return new Link(source, target, stubLabel());
    }

}
