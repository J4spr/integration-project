package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Move;
import be.kdg.programming.integrationproject.dao.MoveDao;
import be.kdg.programming.integrationproject.view.LeaderBoardView;
import be.kdg.programming.integrationproject.view.MainMenuView;

import java.sql.SQLException;
import java.util.List;

public class LeaderBoardPresenter {
    private final LeaderBoardView view;
    private final MoveDao moveDao;
    private final MainMenuView mmv;
    public LeaderBoardPresenter(LeaderBoardView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mmv = mainMenuView;
        this.moveDao = new MoveDao(new DbConnection());

        refreshLeaderboard();
        addHandlers();
    }

    public void refreshLeaderboard() {
        try {
            List<Move> moves = moveDao.findAll();
            view.setTableData(moves);
        } catch (SQLException e) {
            view.showError("Database error: " + e.getMessage());
        }
    }

    private void addHandlers() {
        if (view.getBtnBack() != null) {
            view.getBtnBack().setOnAction(event -> {
                if (view.getBtnBack().getScene() != null) {
                    view.getBtnBack().getScene().setRoot(mmv.getPane());
                    mmv.getPane().requestLayout();
                }
            });
        }
    }
}