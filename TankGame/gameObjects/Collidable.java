package gameObjects;

import java.awt.*;

public interface Collidable {
	Rectangle getBounds();
	void handleCollision(Collidable c);
}
