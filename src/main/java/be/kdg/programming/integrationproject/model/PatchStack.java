package be.kdg.programming.integrationproject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Circular tracking structure managing the pool of purchaseable patches.
 * Tracks the index location of the neutral token to determine availability.
 *
 * @author Team 4
 * @version 1.0
 */
public class PatchStack {
    /** The linear sequence link storing currently active tile contents. */
    private final LinkedList<Patch> patches = new LinkedList<>();
    /** The index location tracking the neutral selection marker. */
    private int neutralTokenPosition;

    /**
     * Resolves the 3 available tiles located directly to the right of the neutral token,
     * wrapping around cleanly if calculations exceed boundaries.
     *
     * @return a sub-list containing exactly 3 choice options
     */
    public List<Patch> getAvailablePatches() {

        List<Patch> available = new ArrayList<>();

        for (int i = 0; i < Math.min(patches.size(), 3); i++) {

            int index = (neutralTokenPosition + i) % patches.size();

            available.add(patches.get(index));
        }

        return available;
    }

    public int getNeutralToken() {
        return this.neutralTokenPosition;
    }

    /**
     * appends a configured tile element straight into the primary stack collection.
     *
     * @param patch the model configuration to append
     */
    public void addPatch(Patch patch) {
        patches.add(patch);
    }

    /**
     * Extracted method filtering components by ID. Moves the neutral token
     * selection index straight to the slot of the purchased item.
     *
     * @param patchID primary verification identity lookup key
     * @return the extracted matched entity instance container, or {@code null} if missing
     */
    public Patch removePatch(int patchID) {
        for (int i = 0; i < patches.size(); i++) {
            if (patches.get(i).getPatchID() == patchID) {
                neutralTokenPosition = i;
                return patches.remove(i);
            }
        }
        return null;
    }

    /**
     * Evaluates whether a patch exists matching an ID without removing it from play.
     *
     * @param patchID targeted filter sequence selection value
     * @return matching patch entity profile configuration, or {@code null} if unmatched
     */
    public Patch getPatch(int patchID) {
        return patches.stream()
                .filter(p -> p.getPatchID() == patchID)
                .findFirst().orElse(null);
    }

    /**
     * Shuffles the collection order randomly.
     */
    public void shuffle() {
        Collections.shuffle(patches);
    }
}