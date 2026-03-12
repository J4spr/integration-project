package be.kdg.programming.integrationproject.model;

import java.util.ArrayList;
import java.util.List;

public class QuiltBoard {
    private static final int SIZE = 9;
    private final boolean[][] grid = new boolean[SIZE][SIZE];
    private final List<Patch> placedPatches = new ArrayList<>();
}
