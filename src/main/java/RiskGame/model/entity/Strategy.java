package RiskGame.model.entity;

import java.io.Serializable;

/**
 * This is the father class for all strategies!!
 *
 * @author Baiyu Huo
 * @version v1.0.0
 */
public interface Strategy extends Serializable {
    /**
     * attack behavior for all strategies
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    Thread attack(int movementTime);
    /**
     * reinforce behavior for all strategies
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    Thread reinforce(int movementTime);
    /**
     * fortify behavior for all strategies
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    Thread fortify(int movementTime);
    /**
     * startup behavior for all strategies
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    Thread startup(int movementTime);
}
