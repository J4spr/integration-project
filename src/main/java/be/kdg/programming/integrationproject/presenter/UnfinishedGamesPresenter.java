package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.dao.GameDao;
import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.view.*;

public class UnfinishedGamesPresenter {

    private final MainMenuView menu;
    private final UnfinishedGamesView view;

    public UnfinishedGamesPresenter(
            UnfinishedGamesView view,
            MainMenuView menu
    ){

        this.view=view;
        this.menu=menu;

        loadGames();
        addHandlers();
    }

    private void loadGames(){

        try{GameDao dao= new GameDao(new DbConnection());

            view.getGameList().getItems().addAll(dao.getUnfinishedGames());
        }catch(Exception e){e.printStackTrace();}
    }

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