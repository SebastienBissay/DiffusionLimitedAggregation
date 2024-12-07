package particle;

import processing.core.PVector;

import static parameters.Parameters.HEIGHT;
import static parameters.Parameters.WIDTH;
import static processing.core.PApplet.*;

public class UTree {
    private final int nX;
    private final int nY;
    private final float cellWidth;
    private final float cellHeight;
    private final Cell[][] cells;

    public UTree(float width, float height) {
        nX = ceil(WIDTH / width);
        nY = ceil(HEIGHT / height);
        cellWidth = width;
        cellHeight = height;

        cells = new Cell[nX][nY];
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void add(Particle particle) {
        for (int i = max(0, floor((particle.position().x - particle.radius()) / cellWidth));
             i < min(WIDTH, floor((particle.position().x + particle.radius()) / cellWidth) + 1);
             i++) {
            for (int j = max(0, floor((particle.position().y - particle.radius()) / cellWidth));
                 j < min(HEIGHT, floor((particle.position().y + particle.radius()) / cellHeight) + 1);
                 j++) {
                cells[i][j].add(particle);
            }
        }
    }

    public boolean check(Particle p) {
        for (int i = max(0, floor((p.position().x - p.radius()) / cellWidth));
             i < min(nX, floor((p.position().x + p.radius()) / cellWidth) + 1);
             i++) {
            for (int j = max(0, floor((p.position().y - p.radius()) / cellWidth));
                 j < min(nY, floor((p.position().y + p.radius()) / cellHeight) + 1);
                 j++) {
                for (Particle q : cells[i][j].getParticles()) {
                    PVector v = PVector.sub(p.position(), q.position());
                    if (v.magSq() < sq(p.radius() + q.radius() + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
