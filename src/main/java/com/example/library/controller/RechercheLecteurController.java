package com.example.library.controller;

import com.example.library.database.DatabaseUtil;
import com.example.library.model.Lecteur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RechercheLecteurController {

    @FXML
    private TextField cinField;

    @FXML
    private TextField nomField;

    @FXML
    private Label lblResultat;

    @FXML
    private TableView<Lecteur> lecteurTable;

    @FXML
    private TableColumn<Lecteur, Integer> codeColumn;

    @FXML
    private TableColumn<Lecteur, String> titreColumn;

    @FXML
    private TableColumn<Lecteur, String> auteurColumn;

    @FXML
    private TableColumn<Lecteur, String> nbLivreColumn;

    @FXML
    public void rechercherLecteur() {
        try {
            Connection connection = DatabaseUtil.getConnection();
            Integer cinValue = null;

            try {
                cinValue = Integer.parseInt(cinField.getText().trim());
            } catch (NumberFormatException e) {
                lblResultat.setText("CIN doit etre numérique  !");
            }

            String nomValue = nomField.getText().trim();

            List<Lecteur> lecteursTrouves;

            if (cinValue != null && !nomValue.isEmpty()) {
                // Both CIN and name have values
                Lecteur lecteurTrouve = Lecteur.chercherLecteurParCIN(connection, cinValue);
                if (lecteurTrouve != null) {
                    lecteursTrouves = Lecteur.chercherLecteursParNom(connection, nomValue);
                    lecteursTrouves.add(lecteurTrouve);
                } else {
                    lecteursTrouves = List.of();
                }
            } else if (cinValue != null) {
                // Only CIN has a value
                Lecteur lecteurTrouve = Lecteur.chercherLecteurParCIN(connection, cinValue);
                lecteursTrouves = (lecteurTrouve != null) ? List.of(lecteurTrouve) : List.of();
            } else if (!nomValue.isEmpty()) {
                // Only name has a value
                lecteursTrouves = Lecteur.chercherLecteursParNom(connection, nomValue);
            } else {
                // Both criteria are null, call chercherTousLecteurs
                lecteursTrouves = Lecteur.chercherTousLecteurs(connection);
            }

            if (!lecteursTrouves.isEmpty()) {
                // Update the TableView with the results of the search
                lecteurTable.getItems().setAll(lecteursTrouves);
                lblResultat.setText("Résultats de la recherche : " + lecteursTrouves.size() + " lecteur(s) trouvé(s).");
            } else {
                // No lecteur found
                lblResultat.setText("Aucun lecteur trouvé avec les critères spécifiés.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void initialize() {
        // Initialize the TableView columns
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().cinProperty().asObject());
        titreColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        auteurColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        nbLivreColumn.setCellValueFactory(cellData -> cellData.getValue().abonnementProperty());

        // Load all lecteurs when the interface is opened
        loadAllLecteurs();
    }

    private void loadAllLecteurs() {
        try {
            Connection connection = DatabaseUtil.getConnection();
            List<Lecteur> allLecteurs = Lecteur.chercherTousLecteurs(connection);

            // Update the TableView with all lecteurs
            lecteurTable.getItems().setAll(allLecteurs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
