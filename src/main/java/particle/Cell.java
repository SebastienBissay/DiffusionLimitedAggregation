package particle;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final List<Particle> particles;

    Cell() {
        particles = new ArrayList<>();
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public void add(Particle particle) {
        particles.add(particle);
    }
}
