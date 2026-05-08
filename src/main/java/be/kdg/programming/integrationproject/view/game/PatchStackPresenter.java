package be.kdg.programming.integrationproject.view.game;

import be.kdg.programming.integrationproject.model.Game;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;
import be.kdg.programming.integrationproject.model.Patch;

import java.util.List;

public class PatchStackPresenter {

    private final Game game;
    private final GameView view;
    //reference to the game presenter to access and modify shared selection state
    private final GamePresenter gamePresenter;

    public PatchStackPresenter(Game game, GameView view, GamePresenter gamePresenter) {
        this.game = game;
        this.view = view;
        this.gamePresenter = gamePresenter;
        addEventHandlers();
    }

    private void addEventHandlers() {
        //attach click handlers to the stable wrappers, not the GridPanes
        //because the GridPanes are rebuilt on every initializeView call
        for (int i = 0; i < view.getPatchStoreSize(); i++) {
            int slotIndex = i;
            view.getPatchSlotWrapper(i).setOnMouseClicked(e -> {
                Object userData = view.getPatchSlot(slotIndex).getUserData();
                if (userData != null) {
                    int newPatchId = (int) userData;
                    //only reset rotation if a different patch is selected
                    if (newPatchId != gamePresenter.getSelectedPatchId()) {
                        gamePresenter.setSelectedPatchId(newPatchId);
                        gamePresenter.setSelectedRotation(PatchRotation.NOROTATION);
                    }
                    initializeView();
                }
            });
        }

        view.getBtnRotate().setOnAction(e -> {
            //only rotate if a patch is selected
            if (gamePresenter.getSelectedPatchId() == -1) return;
            //cycle through the 4 rotations
            PatchRotation next = switch (gamePresenter.getSelectedRotation()) {
                case NOROTATION -> PatchRotation.NINETY;
                case NINETY -> PatchRotation.ONEEIGHTY;
                case ONEEIGHTY -> PatchRotation.TWOSEVENTY;
                case TWOSEVENTY -> PatchRotation.NOROTATION;
            };
            gamePresenter.setSelectedRotation(next);
            //re-initialize the patch stack so the preview updates with the new rotation
            initializeView();
        });
    }

    //initializes the patch stack view based on the current available patches
    public void initializeView() {
        List<Patch> available = game.getPatchStack().getAvailablePatches();

        for (int i = 0; i < view.getPatchStoreSize(); i++) {
            Patch patch = available.get(i);
            boolean[][] shape;
            if (patch.getPatchID() == gamePresenter.getSelectedPatchId()) {
                //get the preview shape for the selected rotation without modifying the patch's state
                shape = patch.getRotatedShapeFor(gamePresenter.getSelectedRotation());
            } else {
                //show the patch in its own default rotation without modifying it
                shape = patch.getRotatedShapeFor(patch.getRotation());
            }
            view.updatePatchSlot(
                    i,
                    shape,
                    patch.getPatchID(),
                    patch.getButtonCost(),
                    patch.getTimeCost(),
                    patch.getButtonIncome()
            );
        }

        //highlight the selected patch slot wrapper if any
        boolean foundSelected = false;
        for (int i = 0; i < view.getPatchStoreSize(); i++) {
            if (view.getPatchSlot(i).getUserData() != null
                    && (int) view.getPatchSlot(i).getUserData() == gamePresenter.getSelectedPatchId()) {
                view.highlightPatchSlot(i);
                foundSelected = true;
                break;
            }
        }
        //if no patch is selected, clear all highlights
        if (!foundSelected) {
            view.highlightPatchSlot(-1);
        }
    }
}