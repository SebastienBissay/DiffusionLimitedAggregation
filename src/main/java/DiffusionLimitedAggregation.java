import particle.Particle;
import particle.UTree;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class DiffusionLimitedAggregation extends PApplet {

    private UTree uTree;
    private List<Particle> particles;

    public static void main(String[] args) {
        PApplet.main(DiffusionLimitedAggregation.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
        noiseSeed(floor(random(MAX_INT)));
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());

        particles = new ArrayList<>();
        uTree = new UTree(U_TREE_WIDTH, U_TREE_HEIGHT);
        Particle p = new Particle(new PVector(width / 2f, height / 2f), STARTING_RADIUS, this);
        uTree.add(p);
        p.render();
        particles.add(p);
    }

    @Override
    public void draw() {
        float angle = random(TWO_PI);
        Particle p = new Particle(PVector.fromAngle(angle)
                .mult(width / 2f)
                .add(new PVector(width / 2f, height / 2f)),
                floor(random(MINIMUM_RANDOM_RADIUS, random(MINIMUM_RANDOM_RADIUS, MAXIMUM_RANDOM_RADIUS)) / 2f),
                this);

        while (!uTree.check(p)) {
            p.move(PARTICLE_VELOCITY);
            if (p.position().x < -MARGIN
                    || p.position().x > WIDTH + MARGIN
                    || p.position().y < -MARGIN
                    || p.position().y > HEIGHT + MARGIN) {
                p = new Particle(PVector.fromAngle(random(TWO_PI))
                        .mult(width / 2f)
                        .add(new PVector(width / 2f, height / 2f)),
                        floor(random(MINIMUM_RANDOM_RADIUS, random(MINIMUM_RANDOM_RADIUS, MAXIMUM_RANDOM_RADIUS)) / 2f),
                        this);
            }
        }
        uTree.add(p);
        particles.add(p);
        p.render();

        if (frameCount >= NUMBER_OF_PARTICLES) {
            noLoop();
            background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
            for (Particle pa : particles) {
                pa.shadow();
            }
            for (Particle pa : particles) {
                pa.render();
            }
            saveSketch(this);
        }
    }
}
