package be.kdg.programming.integrationproject.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PatchStack {
    private final LinkedList<Patch> patches = new LinkedList<>();
    private int neutralTokenPosition;

    //returns the 3 available patches to the right of the neutral token.
    public List<Patch> getAvailablePatches() {
        List<Patch> available = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            int index = (neutralTokenPosition + i) % patches.size();
            available.add(patches.get(index));
        }
        return available;
    }

     //returns the position of the neutral token
    public int getNeutralToken() {
        return this.neutralTokenPosition;
    }
    //adds a patch to the list
    public void addPatch(Patch patch) {
        patches.add(patch);
    }

     //removes the patch with the given patchID from the list
     //moves the neutral token to the position of the removed patch
     //returns the removed patch
    public Patch removePatch(int patchID) {
        for (int i = 0; i < patches.size(); i++) {
            if (patches.get(i).getPatchID() == patchID) {
                neutralTokenPosition = i;
                return patches.remove(i);
            }
        }
        return null;
    }

    public Patch getPatch(int patchID) {
        return patches.stream()
                .filter(p -> p.getPatchID() == patchID)
                .findFirst().orElse(null);
    }

    public void shuffle() {
        Collections.shuffle(patches);
    }
}

