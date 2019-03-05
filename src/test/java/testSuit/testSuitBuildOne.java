package testSuit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestGameManager.class,
        TestMapCreator.class,
        TestMapReader.class,
        TestMapValidation.class,
        TestTerritory.class,
})
public class testSuitBuildOne {
}
