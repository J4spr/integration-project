package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;

public class CpuPlayer extends Player {
    private Difficulty difficulty;

    public CpuPlayer(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    @Override
    void updatePosition(int steps) {
        this.setPosition(this.getPosition() + steps);
    }

    public void decideTurn(Game game) {
        List<Patch> availablePatches = game.getPatchStack().getAvailablePatches();
        Collections.shuffle(availablePatches);

        Random random = new Random();
        PatchRotation[] rotations = PatchRotation.values();

        for (Patch patch : availablePatches) {
            if (game.getCurrentPlayer().getTotalButtons() < patch.getButtonCost()) continue;

            for (int attempt = 0; attempt < 20; attempt++) {
                PatchRotation randomRotation = rotations[random.nextInt(rotations.length)];
                int randomRow = random.nextInt(9);
                int randomCol = random.nextInt(9);

                if (game.buyAndPlacePatch(patch.getPatchID(), randomRow, randomCol, randomRotation)) return;
            }
        }
        game.pass();
    }
}