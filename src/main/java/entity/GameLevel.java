package entity;

import main.GamePanel;
import tiles.Tile;
import tiles.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLevel {
    private final List<Entity> entities;
    private final String map;
    private final TileManager tileManager;

    public GameLevel(Builder builder){
        this.entities = Collections.unmodifiableList(builder.entities);
        this.map = builder.map;
        this.tileManager = builder.tileManager;
        tileManager.setMap(map);
    }

    protected boolean moveHorizontal(MovingEntity entity, double deltaTime) {
        Rectangle predictedHitbox = getPredictedHitbox(entity, entity.getVelocityX(), 0);
        MovementDirection direction = MovementDirection.HORIZONTAL;

        loop: for(Entity other : entities) {
            if (other == null || entity == other) continue;
            if (predictedHitbox.intersects(other.getHitbox())) {
                CollisionResponseType response = entity.onCollision(other, direction, deltaTime);
                switch (response) {
                    case PASS_THROUGH:
                        continue;
                    case STOP:
                        decideHowToMoveHorizontal(entity, other.getHitbox().x, other.getHitbox().width);
                        return false;
                    case PUSH:
                        if (!moveHorizontal((MovingEntity) other, deltaTime)) return false;
                        else break loop;
                }
            }
        }

        for(Tile tile : getTilesAhead(entity, entity.getVelocityX())){
            if (tile == null) continue;
            if (predictedHitbox.intersects(tile.getHitbox())) {
                CollisionResponseType response = entity.onCollision(tile, direction, deltaTime);
                if (response == CollisionResponseType.STOP) {
                    decideHowToMoveHorizontal(entity, tile.getHitbox().x, tile.getHitbox().width);
                    return false;
                }
            }
        }

        performMoveXAxis(entity, entity.getVelocityX(), 0);
        return true;
    }

    protected void decideHowToMoveHorizontal(Entity entity, int othersHitboxX, int othersHitboxWidth){
        if (entity.getVelocityX() < 0) {
            performMoveXAxis(entity,-1 * (entity.getHitbox().x - othersHitboxX - othersHitboxWidth), 0);
        } else {
            performMoveXAxis(entity,othersHitboxX - entity.getHitbox().x - entity.getHitbox().width, 0);
        }
    }

    protected void performMoveXAxis(Entity entity, double velX, double newVelocityX){
        entity.setPositionX(entity.getPositionX() + velX);
        entity.setVelocityX(newVelocityX);
        entity.updateHitBox();
    }

    protected void moveVertical(MovingEntity entity, double deltaTime) {
        applyGravity(entity, deltaTime);
        checkCeilingCollision(entity, deltaTime);
    }

    protected void applyGravity(MovingEntity entity, double deltaTime){
        Rectangle predictedHitbox = (entity.getVelocityY() == 0) ?
                getPredictedHitbox(entity,0, 1) :
                getPredictedHitbox(entity,0, (entity.getPositionY() - (int) entity.getPositionY()) + (deltaTime * entity.getVelocityY()));

        for(Entity other : entities){
            if(other == null || other == entity) continue;
            if(predictedHitbox.intersects(other.getHitbox())){
                CollisionResponseType response = entity.onCollision(other, MovementDirection.DOWN, deltaTime);
                switch (response) {
                    case PASS_THROUGH:
                        continue;
                    case STOP:
                        performMoveYAxis(entity, other.getHitbox().y - entity.getHitbox().y - entity.getHitbox().height, 0);
                        return;
                    case BOUNCE:
                        fly(entity, deltaTime);
                        return;
                }
            }
        }

        loop: for(Tile tile : getTilesBelow(entity, deltaTime * entity.getVelocityY())){
            if (tile == null) continue;
            if (predictedHitbox.intersects(tile.getHitbox())){
                CollisionResponseType response = entity.onCollision(tile, MovementDirection.DOWN, deltaTime);
                switch (response) {
                    case STOP:
                        performMoveYAxis(entity, tile.getHitbox().y - entity.getHitbox().y - entity.getHitbox().height, 0);
                        return;
                    case FLY:
                        break loop;
                }
            }
        }

        fly(entity, deltaTime);
    }

    protected void performMoveYAxis(Entity entity, double velY, double newVelYToSet){
        entity.setPositionY((int) (entity.getPositionY() + velY));
        entity.setVelocityY(newVelYToSet);
        entity.updateHitBox();
    }

    protected void fly(MovingEntity entity, double deltaTime){
        entity.setPositionY(entity.getPositionY() + entity.getVelocityY() * deltaTime);
        entity.setVelocityY(entity.getVelocityY() + deltaTime * entity.getGravityY());
    }

    protected void checkCeilingCollision(MovingEntity entity, double deltaTime){
        if(entity.getVelocityY() < 0) {
            double velY = deltaTime * entity.getVelocityY();
            Rectangle predictedHitbox = getPredictedHitbox(entity, 0, velY);
            for (Entity other : entities) {
                if (other == null || other == entity) continue;
                if(predictedHitbox.intersects(other.getHitbox())) {
                    CollisionResponseType response = entity.onCollision(other, MovementDirection.UP, deltaTime);
                    if (response == CollisionResponseType.STOP) {
                        performMoveYAxis(entity, other.getHitbox().y + other.getHitbox().height - entity.getHitbox().y, 0.5);
                        return;
                    }
                }
            }

            for(Tile tile : getTilesAbove(entity, deltaTime * entity.getVelocityY())){
                if (tile == null) continue;
                if (predictedHitbox.intersects(tile.getHitbox())){
                    CollisionResponseType response = entity.onCollision(tile, MovementDirection.UP, deltaTime);
                    if (response == CollisionResponseType.STOP) {
                        performMoveYAxis(entity, tile.getHitbox().y + tile.getHitbox().height - entity.getHitbox().y, 0.5);
                        return;
                    }
                }
            }
        }
    }

    protected Rectangle getPredictedHitbox(Entity entity, double velX, double velY){
        return new Rectangle((int) (entity.getHitbox().x + velX), (int) (entity.getHitbox().y + velY), entity.getHitbox().width, entity.getHitbox().height);
    }

    protected Tile[] getTilesAhead(Entity entity, double velX){
        int rowIndex = entity.getHitbox().y / GamePanel.TILE_SIZE;
        int rowIndex2 = (entity.getHitbox().y + entity.getHitbox().height - 1) / GamePanel.TILE_SIZE;

        int colIndex = (velX < 0) ? (int) ((entity.getHitbox().x + velX) / GamePanel.TILE_SIZE) : (int) ((entity.getHitbox().x + entity.getHitbox().width + velX) / GamePanel.TILE_SIZE);

        return getTilesAfterMove(rowIndex, colIndex, rowIndex2, colIndex);
    }

    protected Tile[] getTilesBelow(Entity entity, double velY){
        int rowIndex = (velY == 0) ? (entity.getHitbox().y + entity.getHitbox().height + 1) / GamePanel.TILE_SIZE : (int) ((entity.getHitbox().y + entity.getHitbox().height + velY) / GamePanel.TILE_SIZE);

        int colIndex = entity.getHitbox().x / GamePanel.TILE_SIZE;
        int colIndex2 = (entity.getHitbox().x + entity.getHitbox().width - 1) / GamePanel.TILE_SIZE;

        return getTilesAfterMove(rowIndex, colIndex, rowIndex, colIndex2);
    }

    protected Tile[] getTilesAbove(Entity entity, double velY){
        int colIndex = entity.getHitbox().x / GamePanel.TILE_SIZE;
        int rowIndex = (int) ((entity.getHitbox().y + velY) / GamePanel.TILE_SIZE);
        int colIndex2 = (entity.getHitbox().x + entity.getHitbox().width - 1) / GamePanel.TILE_SIZE;

        return getTilesAfterMove(rowIndex, colIndex, rowIndex, colIndex2);
    }

    protected Tile[] getTilesAfterMove(int rowIndex, int colIndex, int rowIndex2, int colIndex2){
        Tile[] tiles = new Tile[tileManager.getMapLayers().length * 2];
        int index = 0;
        for(Tile[][] tilesLayer : tileManager.getMapLayers()){
            tiles[index] = tilesLayer[colIndex][rowIndex];
            tiles[index + 1] = tilesLayer[colIndex2][rowIndex2];
            index += 2;
        }
        return tiles;
    }

    public void update(double deltaTime){
        for(Entity entity : entities){
            entity.update(deltaTime);
        }
        for(Entity entity : entities){
            if(entity instanceof MovingEntity movingEntity){
                moveHorizontal(movingEntity, deltaTime);
                moveVertical(movingEntity, deltaTime);
            }
        }
    }

    public List<Entity> getEntities(){
        return entities;
    }

    public String getMap(){
        return map;
    }

    public static class Builder {
        private final List<Entity> entities = new ArrayList<>();
        private String map;
        private TileManager tileManager;

        public Builder withEntity(Entity entity) {
            entities.add(entity);
            return this;
        }

        public Builder withEntities(List<Entity> entities) {
            this.entities.addAll(entities);
            return this;
        }

        public Builder withMap(String map) {
            this.map = map;
            return this;
        }

        public Builder withTileManager(TileManager tileManager) {
            this.tileManager = tileManager;
            return this;
        }

        public GameLevel build() {
            if (map == null) {
                throw new IllegalStateException("GameLevel must have a map!");
            }
            if (tileManager == null) {
                throw new IllegalStateException("GameLevel must have a TileManager!");
            }
            boolean hasPlayer = entities.stream().anyMatch(e -> e instanceof Player);
            if (!hasPlayer) {
                throw new IllegalStateException("GameLevel must contain a Player entity!");
            }
            return new GameLevel(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
