package RiskGame.model.entity;

public interface Strategy {
    boolean attack(int movementTime);
    boolean reinforce(int movementTime);
    boolean fortify(int movementTime);
}
