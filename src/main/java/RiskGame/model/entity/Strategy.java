package RiskGame.model.entity;

public interface Strategy {
    void attack(int movementTime);
    void reinforce(int movementTime);
    void fortify(int movementTime);
    void startup(int movementTime);
}
