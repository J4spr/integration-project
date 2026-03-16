package be.kdg.programming.integrationproject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PatchStack {
    private final LinkedList<Patch> patches = new LinkedList<>();
    private int neutralTokenPosition;

    //Returns the 3 available patches to the right of the neutral token.
    public List<Patch> getAvailablePatches() {
        List<Patch> available = new ArrayList<>();
        int count = Math.min(3, patches.size());
        for (int i = 1; i <= count; i++) {
            int index = (neutralTokenPosition + i) % patches.size();
            available.add(patches.get(index));
        }
        return available;
    }

     //Returns the position of the neutral token
    public int getNeutralToken() {
        return this.neutralTokenPosition;
    }

    public Patch getPatch(int patchID) {
        for (Patch patch : patches) {
            if (patch.getPatchID() == patchID) {
                return patch;
            }
        }
        return null;
    }

    //Adds a patch to the list
    public void addPatch(Patch patch) {
        patches.add(patch);
    }

    public void shuffle() {
        Collections.shuffle(patches);
    }

    // removes the patch with the given patchID from the circular list
    // moves the neutral token to the position of the removed patch
    // using modulo to handle the case where the removed patch was the last in the list
    public Patch removePatch(int patchID) {
        for (int i = 0; i < patches.size(); i++) {
            if (patches.get(i).getPatchID() == patchID) {
                Patch removed = patches.remove(i);
                neutralTokenPosition = i % patches.size();
                return removed;
            }
        }
        return null;
    }
}