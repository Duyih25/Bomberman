package Object;

import Main.GamePanel;

import java.awt.*;

public class Flame extends SuperObject {
    private int maxRadius;
    public int explosionTime = 50;
    private int tempX, tempY;
    public boolean isCollidingNearBomb = false;
    public FlameSegment[] flameSegments = new FlameSegment[0];

    public Flame(GamePanel gp, int x, int y, String direction, int maxRadius) {
        super(gp);
        setWorldX(x);
        setWorldY(y);
        tempX = x;
        tempY = y;
        collision = true;
        this.maxRadius = maxRadius;
        this.direction = direction;
        setSpeed(64);
        setName("Flame");
        createFlameSegments();
    }

    private void createFlameSegments() {
        flameSegments = new FlameSegment[calculateFlameDistance()];

        setWorldY(tempX);
        setWorldY(tempY);

        boolean last = false;

        int x = getWorldX();
        int y = getWorldY();

        for (int i = 0; i < flameSegments.length; i++) {
            if (i == flameSegments.length - 1 && !isCollidingNearBomb) last = true;

            switch (direction) {
                case "down": y += 64; break;
                case "right": x += 64; break;
                case "up": y -= 64; break;
                case "left": x -= 64; break;
            }
            flameSegments[i] = new FlameSegment(gp, x, y, direction, last);
        }
    }

    private int calculateFlameDistance() {
        int radius = 0;
        if(direction.equals("center")) return 1;

        int x = getWorldX();
        int y = getWorldY();

        while(radius < maxRadius) {
            switch (direction) {
                case "down": y += 64; break;
                case "right": x += 64; break;
                case "up": y -= 64; break;
                case "left": x -= 64; break;
            }

            if (gp.objectManagement.checkNearBomb(x, y)) {
                isCollidingNearBomb = true;
                break;
            }
            collision = false;
            gp.collisionChecker.checkTitle(this);
            gp.collisionChecker.checkBlock(this);
            if(collision) break;

            setWorldX(x);
            setWorldY(y);

            ++radius;
        }
        return radius;
    }

    public void update() {
        for(FlameSegment fs: flameSegments) {
            fs.update();
        }
        explosionTime--;
    }

    @Override
    public void draw(Graphics2D g2) {
        for (int i = 0; i < flameSegments.length; i++) {
            flameSegments[i].draw(g2);
        }
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(getWorldX(), getWorldY(), solidArea.width , solidArea.height);
    }
}
