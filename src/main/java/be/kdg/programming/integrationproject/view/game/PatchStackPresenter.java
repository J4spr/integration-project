package be.kdg.programming.integrationproject.view.game;

import be.kdg.programming.integrationproject.model.Game;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;
import be.kdg.programming.integrationproject.model.Patch;

import java.util.List;

/**
 * Presenter class coordinating updates for the purchaseable patch market display.
 * Links layout wrapper slots to selection triggers managed by the parent {@link GamePresenter}.
 *
 * @author Team 4
 * @version 1.0
 */
public class PatchStackPresenter {
    private final Game game;
    private final GameView view;
    private final GamePresenter gamePresenter;

    /**
     * Binds a market component manager to the application lifecycle handlers.
     *
     * @param game          active core engine tracking session mapping
     * @param view          main layout interface mapping component nodes
     * @param gamePresenter reference pointer anchoring core game interaction parameters
     */
    public PatchStackPresenter(Game game, GameView view, GamePresenter gamePresenter) {
        this.game = game;
        this.view = view;
        this.gamePresenter = gamePresenter;
        addEventHandlers();
    }

    /**
     * Attaches interaction handlers to structural view panels.
     * Handlers target container wrappers to persist through runtime updates.
     */
    private void addEventHandlers() {
        for (int i = 0; i < view.getPatchStoreSize(); i++) {
            int slotIndex = i;
            view.getPatchSlotWrapper(i).setOnMouseClicked(e -> {
                Object userData = view.getPatchSlot(slotIndex).getUserData();
                if (userData != null) {
                    int newPatchId = (int) userData;
                    if (newPatchId != gamePresenter.getSelectedPatchId()) {
                        gamePresenter.setSelectedPatchId(newPatchId);
                        gamePresenter.setSelectedRotation(PatchRotation.NOROTATION);
                    }
                    initializeView();
                }
            });
        }

        view.getBtnRotate().setOnAction(e -> {
            if (gamePresenter.getSelectedPatchId() == -1) return;
            PatchRotation next = switch (gamePresenter.getSelectedRotation()) {
                case NOROTATION -> PatchRotation.NINETY;
                case NINETY -> PatchRotation.ONEEIGHTY;
                case ONEEIGHTY -> PatchRotation.TWOSEVENTY;
                case TWOSEVENTY -> PatchRotation.NOROTATION;
            };
            gamePresenter.setSelectedRotation(next);
            initializeView();
        });
    }

    /**
     * Refreshes market components based on available pool items.
     * Previews shape transformations for the selected item while keeping
     * unselected items in their default positions.
     */
    public void initializeView() {
        List<Patch> available = game.getPatchStack().getAvailablePatches();

        for (int i = 0; i < view.getPatchStoreSize(); i++) {
            Patch patch = available.get(i);
            boolean[][] shape;
            if (patch.getPatchID() == gamePresenter.getSelectedPatchId()) {
                shape = patch.getRotatedShapeFor(gamePresenter.getSelectedRotation());
            } else {
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

        boolean foundSelected = false;
        for (int i = 0; i < view.getPatchStoreSize(); i++) {
            if (view.getPatchSlot(i).getUserData() != null
                    && (int) view.getPatchSlot(i).getUserData() == gamePresenter.getSelectedPatchId()) {
                view.highlightPatchSlot(i);
                foundSelected = true;
                break;
            }
        }
        if (!foundSelected) {
            view.highlightPatchSlot(-1);
        }
    }
}