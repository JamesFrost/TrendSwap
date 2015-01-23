import twitter4j.TwitterException;

/**
 *
 * <p/>
 * Created by James Frost on 21/01/2015.
 */
public class TrendSwap implements Constants {

    public TrendSwap() {
        Controller controller = new Controller();
        controller.run();
    }

    public static void main(String[] args) throws TwitterException {
        new TrendSwap();
    }
}
