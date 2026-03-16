package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.PatchShape;
import java.util.Collections;

public class PatchStackBuilder {
    public static PatchStack build() {
        PatchStack stack = new PatchStack();
        stack.addPatch(new Patch(1, PatchShape.L_SHAPE, 2, 2, 0));
        stack.addPatch(new Patch(2, PatchShape.T_SHAPE, 1, 3, 0));
        stack.addPatch(new Patch(3, PatchShape.S_SHAPE, 3, 2, 1));
        stack.addPatch(new Patch(4, PatchShape.Z_SHAPE, 2, 3, 0));
        stack.addPatch(new Patch(5, PatchShape.SQUARE, 3, 2, 2));
        stack.addPatch(new Patch(6, PatchShape.SMALL, 2, 1, 0));
        stack.addPatch(new Patch(7, PatchShape.L_SHAPE, 1, 2, 1));
        stack.shuffle();
        return stack;
    }
}