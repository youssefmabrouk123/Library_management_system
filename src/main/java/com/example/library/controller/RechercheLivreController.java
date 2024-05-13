package com.example.library.controller;

import com.example.library.database.DatabaseUtil;
import com.example.library.model.Livre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RechercheLivreController implements Initializable {
    @FXML
    private TableColumn<Livre, Integer> codeColumn;

    @FXML
    private TableColumn<Livre, String> titreColumn;

    @FXML
    private TableColumn<Livre, String> auteurColumn;

    @FXML
    private TableColumn<Livre, Integer> nbLivreColumn;

    @FXML
    private TableColumn<Livre, String> isbnColumn;
    @FXML
    private TextField auteurField;
    @FXML
    private TableColumn<Livre, String> typeColumn;
    @FXML
    private TableView<Livre> resultatsTable;
    @FXML
    private TextField titreField;
    @FXML
    private Label lblResultat;

    /*@FXML
    public void rechercherLivreParTitre() {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String titre = titreField.getText().trim();

            if (!titre.isEmpty()) {
                Livre livreTrouve = Livre.chercherLivreParTitre(connection, titre);

                if (livreTrouve != null) {
                    // Mettez à jour le texte de lblResultat avec les détails du livre trouvé
                    lblResultat.setText("Livre trouvé : " + livreTrouve.getTitre());

                    // Mettez à jour les propriétés de la TableView
                    codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
                    titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
                    auteurColumn.setCellValueFactory(new PropertyValueFactory<>("auteur"));
                    nbLivreColumn.setCellValueFactory(new PropertyValueFactory<>("nbLivre"));
                    isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

                    // Créez une liste et ajoutez le livre trouvé
                    List<Livre> livres = new ArrayList<>();
                    livres.add(livreTrouve);

                    // Mettez à jour les données de la TableView
                    resultatsTable.getItems().setAll(livres);
                } else {
                    // Aucun livre trouvé
                    lblResultat.setText("Aucun livre trouvé avec le titre : " + titre);
                }
            } else {
                // Aucun titre saisi
                lblResultat.setText("Veuillez saisir un titre de livre.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    @FXML
    public void rechercherLivre() {
        try {
            Connection connection = DatabaseUtil.getConnection();
            String titreValue = titreField.getText().trim();
            String auteurValue = auteurField.getText().trim();

            List<Livre> combinedResults = null;
            if (!titreValue.isEmpty() || !auteurValue.isEmpty()) {
                List<Livre> livresTrouvesTitre = new ArrayList<>();
                List<Livre> livresTrouvesAuteur = new ArrayList<>();

                if (!titreValue.isEmpty()) {
                    livresTrouvesTitre = Livre.chercherLivresParTitre(connection, titreValue);
                }

                if (!auteurValue.isEmpty()) {
                    livresTrouvesAuteur = Livre.chercherLivresParAuteur(connection, auteurValue);
                }

                // Combine the results
                combinedResults = new ArrayList<>(livresTrouvesTitre);
                combinedResults.addAll(livresTrouvesAuteur);

                if (!combinedResults.isEmpty()) {
                    // Mettez à jour la TableView avec les résultats de la recherche combinée
                    resultatsTable.getItems().setAll(combinedResults);
                    lblResultat.setText("Résultats de la recherche combinée : " + combinedResults.size() + " livre(s) trouvé(s).");
                } else {
                    // Aucun livre trouvé
                    lblResultat.setText("Aucun livre trouvé avec les critères spécifiés.");
                    // Clear the TableView
                    resultatsTable.getItems().clear();
                }
            } else {
                // Aucun critère de recherche saisi
                lblResultat.setText("Veuillez saisir au moins un critère de recherche.");

                resultatsTable.getItems().setAll(Livre.chercherTousLivres(connection));
                // Clear the TableView

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadLivresFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadLivresFromDatabase() throws SQLException {
        Connection connection = DatabaseUtil.getConnection();
        List<Livre> allLivres = Livre.chercherTousLivres(connection);

        if (!allLivres.isEmpty()) {
            // Mettez à jour la TableView avec les résultats de la recherche
            resultatsTable.getItems().setAll(allLivres);
            lblResultat.setText("Livres chargés depuis la base de données : " + allLivres.size() + " livre(s).");
        } else {
            // Aucun livre trouvé
            lblResultat.setText("Aucun livre trouvé dans la base de données.");
        }
    }

}
