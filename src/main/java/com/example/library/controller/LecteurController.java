package com.example.library.controller;

import com.example.library.database.DatabaseUtil;
import com.example.library.model.Lecteur;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;

public class LecteurController {

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtTitre;

    @FXML
    private TextField txtAuteur;

    @FXML
    private TextField txtNbLivre;

    @FXML
    private Label lblResultat;
    @FXML
    private TextField codeField;


    @FXML
    private void ajouterLecteur() {
        try {
            Connection connection = DatabaseUtil.getConnection();

            int cin = Integer.parseInt(txtCode.getText().trim());
            String nom = txtTitre.getText().trim();
            String prenom = txtAuteur.getText().trim();
            String abonnement = txtNbLivre.getText().trim();

            Lecteur lecteur = new Lecteur(cin, nom, prenom, abonnement);
            lecteur.ajouterLecteur(connection);

            // Display a pop-up window for successful addition
            showAlert("Succès", "Lecteur ajouté avec succès.", Alert.AlertType.INFORMATION);

            lblResultat.setText("Lecteur ajouté avec succès.");

            txtAuteur.clear();
            txtCode.clear();
            txtNbLivre.clear();
            txtTitre.clear();

        } catch (SQLException | NumberFormatException e) {
            lblResultat.setText("Erreur lors de l'ajout du lecteur.");
            e.printStackTrace();
        }
    }
    @FXML
    private void supprimerLecteur() {
        try {
            Connection connection = DatabaseUtil.getConnection();
            Integer cinValue = null;
            try {
                cinValue = Integer.parseInt(codeField.getText().trim());
            } catch (NumberFormatException e) {
                lblResultat.setText("CIN doit être formé de 8 chiffres !");
                return; // Stop execution if parsing fails
            }

            if (cinValue != null) {
                Lecteur lecteurToDelete = Lecteur.chercherLecteurParCIN(connection, cinValue);

                if (lecteurToDelete != null) {
                    if (lecteurToDelete.supprimerLecteurParCIN(connection)) {
                        codeField.clear();
                        showAlert("Succès", "Lecteur supprimé avec succès.", Alert.AlertType.INFORMATION);
                        lblResultat.setText("");
                    } else {
                        lblResultat.setText("Échec de la suppression du lecteur.");
                    }
                } else {
                    lblResultat.setText("Aucun lecteur trouvé avec le CIN : " + cinValue);
                }
            } else {
                lblResultat.setText("Veuillez saisir le CIN du lecteur à supprimer.");
            }
        } catch (SQLException e) {
           // e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
