package com.example.library.controller;

import com.example.library.database.DatabaseUtil;
import com.example.library.model.Emprunt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EmpruntController {

    @FXML
    private TextField txtIdEmprunt;
    @FXML
    private Label lblResultat;
    @FXML
    private TextField txtCodeLivre;

    @FXML
    private TextField txtCodeLecteur;

    @FXML
    private TextField txtDateEmprunt;

    @FXML
    private TextField txtDateRetourPrevu;
    private Emprunt emprunt;

    public EmpruntController() {
        this.emprunt = new Emprunt(0,0,null,null,null);
    }

    @FXML
    private void ajouterEmprunt(ActionEvent event) {
        try {
            int codeLivre = Integer.parseInt(txtCodeLivre.getText());
            int codeLecteur = Integer.parseInt(txtCodeLecteur.getText());

            // Convertir la chaîne de texte en objet java.util.Date pour txtDateEmprunt
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDateEmprunt = dateFormat.parse(txtDateEmprunt.getText());
            Date dateEmprunt = new Date(utilDateEmprunt.getTime());

            java.util.Date utilDateRetourPrevu = dateFormat.parse(txtDateRetourPrevu.getText());
            Date dateRetourPrevu = new Date(utilDateRetourPrevu.getTime());

            Emprunt nouvelEmprunt = new Emprunt(codeLivre, codeLecteur, dateEmprunt, dateRetourPrevu, null);

            nouvelEmprunt.ajouterEmprunt();
            txtCodeLivre.clear();
            txtCodeLecteur.clear();
            txtDateEmprunt.clear();
            txtDateRetourPrevu.clear();
            // Mettre à jour l'interface utilisateur ou afficher un message de succès
            lblResultat.setText("Emprunt ajouté avec succès!");
        } catch (NumberFormatException e) {
            lblResultat.setText("Veuillez entrer des valeurs valides.");
        } catch (ParseException e) {
            lblResultat.setText("Erreur lors de la conversion de la date. Assurez-vous d'utiliser le format correct (yyyy-MM-dd).");
            e.printStackTrace(); // Affichez des informations de débogage
        } catch (SQLException e) {
            lblResultat.setText("Erreur lors de l'ajout de l'emprunt dans la base de données.");
            e.printStackTrace(); // À adapter en fonction de la gestion des erreurs dans votre application
        }
    }


    @FXML
    private void retournerEmprunt() {
        try {
            int idEmprunt = Integer.parseInt(txtIdEmprunt.getText());
            Emprunt emprunt = new Emprunt(idEmprunt, 0, 0, null, null, null);

            // Appel de la méthode correcte pour retourner l'emprunt
            emprunt.retournerEmpruntById(idEmprunt);

            lblResultat.setText("Emprunt retourné avec succès!");
        } catch (NumberFormatException e) {
            lblResultat.setText("Veuillez saisir un ID d'emprunt valide.");
        } catch (SQLException e) {
            lblResultat.setText("Erreur lors du retour de l'emprunt: " + e.getMessage());
        }
    }



}
