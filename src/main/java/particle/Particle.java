package particle;

import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static processing.core.PConstants.TWO_PI;

public record Particle(PVector position, float radius, PApplet pApplet) {

    public void render() {
        pApplet.stroke(STROKE_COLOR.red(), STROKE_COLOR.green(), STROKE_COLOR.blue(), STROKE_COLOR.alpha());
        pApplet.fill(FILL_COLOR.red(), FILL_COLOR.green(), FILL_COLOR.blue(), FILL_COLOR.alpha());
        pApplet.circle(position.x, position.y, 2 * radius);

    }

    public void shadow() {
        pApplet.stroke(STROKE_COLOR.red(), STROKE_COLOR.green(), STROKE_COLOR.blue(), STROKE_COLOR.alpha());
        pApplet.fill(STROKE_COLOR.red(), STROKE_COLOR.green(), STROKE_COLOR.blue(), STROKE_COLOR.alpha());
        pApplet.circle(position.x, position.y, 5 * radius);
    }

    public void move(float amt) {
        PVector v = PVector.fromAngle(TWO_PI * pApplet.noise(position.x * NOISE_SCALE, position.y * NOISE_SCALE));
        v.add(PVector.sub(new PVector(WIDTH / 2f, HEIGHT / 2f), position)).mult(FORCE_AMOUNT);
        v.normalize();
        v.mult(amt);
        position.add(v);
    }
}
