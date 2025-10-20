package entity;

public abstract class BouncingEntity extends PushableEntity {
    public BouncingEntity(double positionX, double positionY, int width, int height, int padding, boolean isCollidable) {
        super(positionX, positionY, width, height, padding, isCollidable);
    }
}
