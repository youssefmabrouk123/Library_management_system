package com.example.library.controller;

import com.example.library.database.DatabaseUtil;
import com.example.library.model.Emprunt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class RechercheEmpruntController {
    @FXML
    public Label lblresultat;
    @FXML
    private TableView<Emprunt> empruntTable;

    @FXML
    private TableColumn<Emprunt, Integer> idEmpruntColumn;

    @FXML
    private TableColumn<Emprunt, Integer> codeLivreColumn;

    @FXML
    private TableColumn<Emprunt, Integer> codeLecteurColumn;

    @FXML
    private TableColumn<Emprunt, Date> dateEmpruntColumn;

    @FXML
    private TableColumn<Emprunt, Date> dateRetourPrevuColumn;

    @FXML
    private TableColumn<Emprunt, Date> dateRetourEffectiveColumn;

    @FXML
    private void initialize() {
        // Initialize the columns
        idEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("IdEmprunt"));
        codeLivreColumn.setCellValueFactory(new PropertyValueFactory<>("codeLivre"));
        codeLecteurColumn.setCellValueFactory(new PropertyValueFactory<>("codeLecteur"));
        dateEmpruntColumn.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
        dateRetourPrevuColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourPrevu"));
        dateRetourEffectiveColumn.setCellValueFactory(new PropertyValueFactory<>("dateRetourEffective"));

        try {
            List<Emprunt> emprunts = Emprunt.getAllEmpruntsFromDatabase();
            empruntTable.getItems().setAll(emprunts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadEmpruntsFromDatabase() throws SQLException {

        List<Emprunt> allEmprunts = Emprunt.getAllEmpruntsFromDatabase();

        if (!allEmprunts.isEmpty()) {
            // Mettez à jour la TableView avec les résultats de la recherche
            ObservableList<Emprunt> empruntList = FXCollections.observableArrayList(allEmprunts);
            empruntTable.setItems(empruntList);
            lblresultat.setText("Emprunts chargés depuis la base de données : " + allEmprunts.size() + " emprunt(s).");
        } else {
            // Assuming lblResultat is defined in your FXML file
            lblresultat.setText("Aucun emprunt trouvé dans la base de données.");
        }
    }
}
