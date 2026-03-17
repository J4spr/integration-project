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
        //shuffle to ensure random patch selection
        Collections.shuffle(availablePatches);

        Random random = new Random();
        PatchRotation[] rotations = PatchRotation.values();

        for (Patch patch : availablePatches) {
            //skip patch if player cannot afford it
            if (game.getCurrentPlayer().getTotalButtons() < patch.getButtonCost()) continue;

            //try up to 20 random positions and rotations for each patch
            for (int attempt = 0; attempt < 20; attempt++) {
                PatchRotation randomRotation = rotations[random.nextInt(rotations.length)];
                int randomRow = random.nextInt(QuiltBoard.getSize());
                int randomCol = random.nextInt(QuiltBoard.getSize());

                //buyAndPlacePatch returns true if the move was valid and executed
                if (game.buyAndPlacePatch(patch.getPatchID(), randomRow, randomCol, randomRotation)) return;
            }
        }
        //no valid patch placement found, pass the turn
        game.pass();
    }
}