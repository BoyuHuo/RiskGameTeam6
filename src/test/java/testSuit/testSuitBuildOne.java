package testSuit;

import RiskGame.model.service.imp.MapManager;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.*;

/**
 * This is a Junit test suite for build one, it will run all the test case that tester made in build 1
 *
 * @author Baiyu Huo
 * @version  v1.0.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestGameManager.class,
        TestMapCreator.class,
        TestMapReader.class,
        TestMapValidation.class,
        TestTerritory.class,
        TestAttack.class,
})
public class testSuitBuildOne {
}
