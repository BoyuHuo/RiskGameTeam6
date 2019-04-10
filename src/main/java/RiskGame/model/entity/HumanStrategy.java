package RiskGame.model.entity;

/**
 * This is implement class for Human Strategy !!
 * contains: attack , fortification, reinforcement
 *
 * @author Baiyu Huo
 * @version v1.0.0
 */
public class HumanStrategy implements Strategy {
    /**
     * attack behavior for HumanStrategy robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    @Override
    public Thread attack(int movementTime) {
        return null;
    }

    /**
     * reinforce behavior for HumanStrategy robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    @Override
    public Thread reinforce(int movementTime) {
        return null;
    }

    /**
     * fortify behavior for HumanStrategy robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    @Override
    public Thread fortify(int movementTime) {
        return null;
    }

    /**
     * startup behavior for HumanStrategy robot
     * @param movementTime the time for every movement
     * @return thread the thread that used to finished attack strategy
     */
    @Override
    public Thread startup(int movementTime) {
        return null;
    }
}
