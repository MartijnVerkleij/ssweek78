package ss.week7;

import java.util.ArrayList;
import java.util.List;
/**
 * Utilities used for various purposes.
 * @author martijn
 *
 */
public final class Util {

    /**
     * private constructor.
     */
    private Util() { }

    /**
     * Zips 2 lists together.
     * @param <Element> type used in the list
     * @param l1 first list, merged at uneven positions
     * @param l2 second list, merged at even positions
     * @return zipped list
     */
    public static <Element> List<Element>
        zip(final List<Element> l1, final List<Element> l2) {
        ArrayList<Element> result = new ArrayList<Element>();
        for (int i = 0; i < l1.size(); i++) {
            result.add(l1.get(i));
            result.add(l2.get(i));
        }
        return result;
    }

    /**
     * signum function.
     * @param i the function argument
     * @return -1, 0 or 1 if the argument is negative, 0 or positive
     */
    public static int signum(final int i) {
        if (i < 0) {
            return -1;
        } else if (i > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
