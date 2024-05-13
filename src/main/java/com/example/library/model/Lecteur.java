package com.example.library.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Lecteur {
    private int cin;
    private String nom;
    private String prenom;
    private String abonnement;

    public Lecteur(int cin, String nom, String prenom, String abonnement) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.abonnement = abonnement;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(String abonnement) {
        this.abonnement = abonnement;
    }

    public SimpleIntegerProperty cinProperty() {
        return new SimpleIntegerProperty(cin);
    }

    public SimpleStringProperty nomProperty() {
        return new SimpleStringProperty(nom);
    }

    public SimpleStringProperty prenomProperty() {
        return new SimpleStringProperty(prenom);
    }

    public SimpleStringProperty abonnementProperty() {
        return new SimpleStringProperty(abonnement);
    }
    public void ajouterLecteur(Connection connection) throws SQLException {
        String query = "INSERT INTO lecteur (CIN, Nom, Prenom, Abonnement) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, getCin());
            preparedStatement.setString(2, getNom());
            preparedStatement.setString(3, getPrenom());
            preparedStatement.setString(4, getAbonnement());

            preparedStatement.executeUpdate();
        }
    }
    public static Lecteur chercherLecteurParCIN(Connection connection, int cin) throws SQLException {
        String query = "SELECT * FROM lecteur WHERE Cin = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, cin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Lecteur(
                            resultSet.getInt("Cin"),
                            resultSet.getString("Nom"),
                            resultSet.getString("Prenom"),
                            resultSet.getString("Abonnement")
                    );
                }
            }
        }
        return null;
    }

    public static List<Lecteur> chercherLecteursParNom(Connection connection, String nom) throws SQLException {
        List<Lecteur> lecteurs = new ArrayList<>();
        String query = "SELECT * FROM lecteur WHERE Nom = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Lecteur lecteur = new Lecteur(
                            resultSet.getInt("Cin"),
                            resultSet.getString("Nom"),
                            resultSet.getString("Prenom"),
                            resultSet.getString("Abonnement")
                    );
                    lecteurs.add(lecteur);
                }
            }
        }

        return lecteurs;
    }

    public static List<Lecteur> chercherTousLecteurs(Connection connection) throws SQLException {
        List<Lecteur> lecteurs = new ArrayList<>();
        String query = "SELECT * FROM lecteur";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Lecteur lecteur = new Lecteur(
                        resultSet.getInt("Cin"),
                        resultSet.getString("Nom"),
                        resultSet.getString("Prenom"),
                        resultSet.getString("Abonnement")
                );
                lecteurs.add(lecteur);
            }
        }

        return lecteurs;
    }
    public boolean supprimerLecteurParCIN(Connection connection) throws SQLException {
        String query = "DELETE FROM lecteur WHERE CIN = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, getCin());
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        }
    }

}
