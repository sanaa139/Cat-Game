package entity;

import tiles.Tile;

public abstract class MovingEntity extends Entity {
    private SpriteDirection spriteDirection;

    public MovingEntity(double positionX, double positionY, int width, int height, int padding, boolean isCollidable) {
        super(positionX, positionY, width, height, padding, isCollidable);
        this.spriteDirection = SpriteDirection.STAY;
    }

    @Override
    protected void update(double deltaTime) {
        updateHitBox();
    }

    public CollisionResponseType onCollision(Entity other, MovementDirection direction, double deltaTime) {
        if(other.isCollidable()) {
            return switch (direction) {
                case HORIZONTAL -> {
                    if (other instanceof PushableEntity) {
                        other.setVelocityX(getVelocityX());
                        yield CollisionResponseType.PUSH;
                    } else {
                        yield CollisionResponseType.STOP;
                    }
                }
                case DOWN -> {
                    if (other instanceof BouncingEntity) {
                        setVelocityY(-3 - 0.5 * getVelocityY());
                        yield CollisionResponseType.FLY;
                    } else {
                        setVelocityY(0);
                        yield CollisionResponseType.STOP;
                    }
                }
                case UP -> CollisionResponseType.STOP;
                default -> CollisionResponseType.STOP;
            };
        }else{
            return switch(direction) {
                case HORIZONTAL, UP -> CollisionResponseType.PASS_THROUGH;
                case DOWN -> CollisionResponseType.FLY;
                default -> CollisionResponseType.STOP;
            };
        }
    }

    public CollisionResponseType onCollision(Tile tile, MovementDirection direction, double deltaTime) {
        if(tile.isCollidable()) {
            return switch (direction) {
                case HORIZONTAL, UP -> CollisionResponseType.STOP;
                case DOWN -> {
                    setVelocityY(0);
                    yield CollisionResponseType.STOP;
                }
                default -> CollisionResponseType.STOP;
            };
        }else{
            return CollisionResponseType.PASS_THROUGH;
        }
    }

    SpriteDirection getSpriteDirection() {
        return spriteDirection;
    }

    void setSpriteDirection(SpriteDirection spriteDirection) {
        this.spriteDirection = spriteDirection;
    }
}
