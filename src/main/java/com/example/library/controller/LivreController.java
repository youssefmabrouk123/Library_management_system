package com.example.library.controller;

import com.example.library.database.DatabaseUtil;
import com.example.library.model.Livre;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LivreController {

    @FXML
    private TextField titreField;

    @FXML
    private TextField isbnField;

    @FXML
    private Label lblResultat;

    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtTitre;
    @FXML
    private TextField txtAuteur;
    @FXML
    private TextField txtNbLivre;
    @FXML
    private TextField txtISBN;
    @FXML
    private TextField txtType;


    @FXML
    private void ajouterLivre() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            int code = Integer.parseInt(txtCode.getText());
            String titre = txtTitre.getText();
            String auteur = txtAuteur.getText();
            int nbLivre = Integer.parseInt(txtNbLivre.getText());
            String isbn = txtISBN.getText();
            String type = txtType.getText();

            Livre nouveauLivre = new Livre(code, titre, auteur, nbLivre, isbn, type);
            nouveauLivre.ajouterLivre(connection);

            txtTitre.clear();
            txtCode.clear();
            txtAuteur.clear();
            txtISBN.clear();
            txtType.clear();
            txtNbLivre.clear();
            lblResultat.setText("Livre ajouté avec succès !");
            // Display a pop-up window for successful addition
            showAlert("Succès", "Livre ajouté avec succès !", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            lblResultat.setText("Veuillez saisir des valeurs numériques pour Code et Nombre de livres.");
        } catch (SQLException e) {
            lblResultat.setText("Erreur lors de l'ajout du livre dans la base de données.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    private void supprimerLivreParTitre() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String titre = titreField.getText().trim();

            if (!titre.isEmpty()) {
                Livre livreToDelete = new Livre(0, titre, null, 0, null, null);
                if (livreToDelete.supprimerLivreParTitre(connection)) {
                    titreField.clear();
                    lblResultat.setText("Livre supprimé !");
                    showAlert("Succès", "Livre supprimé avec succès !", AlertType.INFORMATION);
                } else {
                    lblResultat.setText("Le Titre n'existe pas !");
                }
            } else {
                lblResultat.setText("Veuillez saisir le titre pour supprimer un livre.");
            }

        } catch (SQLException e) {
            lblResultat.setText("Erreur lors de la suppression du livre.");
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerLivreParISBN() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String isbn = isbnField.getText().trim();

            if (!isbn.isEmpty()) {
                Livre livreToDelete = new Livre(0, null, null, 0, isbn, null);

                if (livreToDelete.supprimerLivreParISBN(connection)) {
                    isbnField.clear();
                    lblResultat.setText("Livre supprimé !");
                    showAlert("Succès", "Livre supprimé avec succès !", AlertType.INFORMATION);
                } else {
                    lblResultat.setText("L'ISBN n'existe pas dans la base de données.");
                }
            } else {
                lblResultat.setText("Veuillez saisir l'ISBN pour supprimer un livre.");
            }

        } catch (SQLException e) {
            lblResultat.setText("Erreur lors de la suppression du livre.");
            e.printStackTrace();
        }
    }
}
