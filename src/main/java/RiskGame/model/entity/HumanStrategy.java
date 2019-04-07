package RiskGame.model.entity;

public class HumanStrategy implements Strategy {
    @Override
    public boolean attack(int movementTime) {
        return false;
    }

    @Override
    public boolean reinforce(int movementTime) {
        return false;
    }

    @Override
    public boolean fortify(int movementTime) {
        return false;
    }
}
