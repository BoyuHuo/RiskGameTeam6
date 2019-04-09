package RiskGame.model.entity;

public interface Strategy {
    Thread attack(int movementTime);
    Thread reinforce(int movementTime);
    Thread fortify(int movementTime);
    Thread startup(int movementTime);
}
