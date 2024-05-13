package com.example.library.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;


public class MainController {

    @FXML
    private void handleAjouterUnLivre(ActionEvent event) {
        // Handle Rechercher Un Lecteur button click
        loadFXML("/com/example/library/AddBookView.fxml", "Ajouter un livre");
    }
    @FXML
    private void handleAjouterUnLecteur(ActionEvent event) {
        loadFXML("/com/example/library/AddLecteurView.fxml", "Ajouter un lecteur");
    }
    @FXML
    private void handleSupprimerUnLecteur(ActionEvent event) {
        loadFXML("/com/example/library/DeleteLecteur.fxml", "Supprimer un lecteur");
    }
    @FXML
    private void handleSupprimerUnLivre(ActionEvent event) {
        loadFXML("/com/example/library/DeleteBookView.fxml", "Ajouter un livre");
    }
    @FXML
    private void handleChercherUnLivre(ActionEvent event) {
        loadFXML("/com/example/library/RechercheLivreView.fxml", "Chercher un livre");
    }
    @FXML
    private void handleChercherUnLecteur(ActionEvent event) {
        loadFXML("/com/example/library/RechercheLecteurView.fxml", "Chercher un lecteur");
    }
    @FXML
    private void handleListeEmprunt(ActionEvent event) {
        loadFXML("/com/example/library/RechercheEmpruntView.fxml", "Liste des emprunts");
    }
    @FXML
    private void handleEmprunterLivre(ActionEvent event) {
        // Handle Rechercher Un Lecteur button click
        loadFXML("/com/example/library/EmpruntBookView.fxml", "Chercher un livre");
    }
    @FXML
    private void handleReturnLivre(ActionEvent event) {
        // Handle Rechercher Un Lecteur button click
        loadFXML("/com/example/library/ReturnBookView.fxml", "Chercher un livre");
    }

    private void loadFXML(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.getIcons().add(new Image("/com/example/library/img/icon_book.png"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
