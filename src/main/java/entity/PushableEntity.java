package entity;

public abstract class PushableEntity extends MovingEntity {
    public PushableEntity(double positionX, double positionY, int width, int height, int padding, boolean isCollidable) {
        super(positionX, positionY, width, height, padding, isCollidable);
    }
}
