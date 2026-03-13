package be.kdg.programming.integrationproject.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PatchStack {
    private final LinkedList<Patch> patches = new LinkedList<>();
    private int tokenPosition;

    //Returns the 3 available patches to the right of the neutral token.
    public List<Patch> getAvailablePatches() {
        List<Patch> available = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            int index = (tokenPosition + i) % patches.size();
            available.add(patches.get(index));
        }
        return available;
    }

     //Returns the position of the neutral token.
    public int getNeutralToken() {
        return this.tokenPosition;
    }
}

