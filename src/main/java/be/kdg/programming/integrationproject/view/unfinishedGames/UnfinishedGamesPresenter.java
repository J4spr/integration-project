package be.kdg.programming.integrationproject.view.unfinishedGames;

import be.kdg.programming.integrationproject.dao.GameDao;
import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.view.game.GamePresenter;
import be.kdg.programming.integrationproject.view.game.GameView;
import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;

/**
 * Controller class that tracks and processes suspended game records.
 * <p>
 * Pulls uncompleted game records from data layers, populates target list widgets,
 * extracts configuration codes out of UI lists, and handles state reload tasks.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class UnfinishedGamesPresenter {

    /** The root parent main menu dashboard panel instance pointer used during back navigation step triggers. */
    private final MainMenuView menu;
    /** The active historical list manager view dashboard window reference structure. */
    private final UnfinishedGamesView view;

    /**
     * Instantiates an active presenter pairing, querying data storage layers
     * and connecting interactive button events.
     *
     * @param view the historical games view manager element reference
     * @param menu the parent step-back destination view component tracker
     */
    public UnfinishedGamesPresenter(
            UnfinishedGamesView view,
            MainMenuView menu
    ){
        this.view=view;
        this.menu=menu;

        loadGames();
        addHandlers();
    }

    /**
     * Queries the database layer for suspended games and adds them
     * to the user selection view list component.
     */
    private void loadGames(){
        try{GameDao dao= new GameDao(new DbConnection());

            view.getGameList().getItems().addAll(dao.getUnfinishedGames());
        }catch(Exception e){e.printStackTrace();}
    }

    /**
     * Registers actions to handle back button steps and logic reloads
     * for selected matches.
     * <p>
     * Selected entries are parsed using regex text splitting blocks to extract embedded primary keys
     * (e.g., string segment reading blocks looking up {@code #}), loading states into game panels.
     * </p>
     */
    private void addHandlers(){
        view.getBtnBack().setOnAction(e-> view.getPane().getScene().setRoot(menu.getPane()));

        view.getBtnLoad().setOnAction(e->{String selected= view.getGameList().getSelectionModel().getSelectedItem();

            if(selected==null) return;
            try{int gameId= Integer.parseInt(selected.split("#")[1].split("\\(")[0].trim());

                GameDao dao= new GameDao(new DbConnection());

                Game game= dao.loadUnfinishedGame(gameId);

                GameView gv= new GameView(game.getPlayer1().getName(),
                        "#42a5f5",
                        "CPU",
                        "#ef5350");

                new GamePresenter(game, gv, menu);

                view.getPane().getScene().setRoot(gv.getPane());

            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }
}