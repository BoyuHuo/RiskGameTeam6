package RiskGame.model.entity;

import java.io.Serializable;

public interface Strategy extends Serializable {
    Thread attack(int movementTime);
    Thread reinforce(int movementTime);
    Thread fortify(int movementTime);
    Thread startup(int movementTime);
}
