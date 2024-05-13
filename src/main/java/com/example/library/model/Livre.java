package com.example.library.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Livre {
    private int code;
    private String titre;
    private String auteur;
    private int nbLivre;
    private String isbn;
    private String type;

    public Livre(int code, String titre, String auteur, int nbLivre, String isbn, String type) {
        this.code = code;
        this.titre = titre;
        this.auteur = auteur;
        this.nbLivre = nbLivre;
        this.isbn = isbn;
        this.type = type;
    }

    /*public int codeProperty() {
        return code;
    }*/

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getNbLivre() {
        return nbLivre;
    }

    public void setNbLivre(int nbLivre) {
        this.nbLivre = nbLivre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*public String titleProperty() {
        return titre;
    }*/

    /*public String authorProperty() {
        return auteur;
    }*/
    public StringProperty authorProperty() {
        return new SimpleStringProperty(auteur);
    }
    //...
    public IntegerProperty codeProperty() {
        return new SimpleIntegerProperty(code);
    }
    public StringProperty titleProperty() {
        return new SimpleStringProperty(titre);
    }

    public IntegerProperty nbLivreProperty() {
        return new SimpleIntegerProperty(nbLivre);
    }
    public StringProperty isbnProperty() {
        return new SimpleStringProperty(isbn);
    }
    public StringProperty typeProperty() {
        return new SimpleStringProperty(type);
    }
    public void ajouterLivre(Connection connection) throws SQLException {
        String query = "INSERT INTO livre (Code, Titre, Auteur, NbLivre, ISBN, Type) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, getCode());
            preparedStatement.setString(2, getTitre());
            preparedStatement.setString(3, getAuteur());
            preparedStatement.setInt(4, getNbLivre());
            preparedStatement.setString(5, getIsbn());
            preparedStatement.setString(6, getType());

            preparedStatement.executeUpdate();
        }
    }

    // Méthode pour supprimer un livre de la base de données
    public boolean supprimerLivreParTitre(Connection connection) throws SQLException {
        String query = "DELETE FROM livre WHERE Titre = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, getTitre());
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0 ;
        }
    }
    public boolean supprimerLivreParISBN(Connection connection) throws SQLException {
        String query = "DELETE FROM livre WHERE ISBN = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, getIsbn());
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        }
    }

    /*public static Livre chercherLivreParTitre(Connection connection, String titre) throws SQLException {
        String query = "SELECT * FROM livre WHERE Titre = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, titre);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Livre(
                            resultSet.getInt("Code"),
                            resultSet.getString("Titre"),
                            resultSet.getString("Auteur"),
                            resultSet.getInt("NbLivre"),
                            resultSet.getString("ISBN"),
                            resultSet.getString("Type")
                    );
                }
            }
        }

        // Aucun livre trouvé avec le titre spécifié
        return null;
    }*/
    public static List<Livre> chercherLivresParTitre(Connection connection, String titre) throws SQLException {
        List<Livre> livresTrouves = new ArrayList<>();
        String query = "SELECT * FROM livre WHERE Titre LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + titre + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Livre livre = new Livre(
                            resultSet.getInt("Code"),
                            resultSet.getString("Titre"),
                            resultSet.getString("Auteur"),
                            resultSet.getInt("NbLivre"),
                            resultSet.getString("ISBN"),
                            resultSet.getString("Type")
                    );
                    livresTrouves.add(livre);
                }
            }
        }

        return livresTrouves;
    }
    public static List<Livre> chercherTousLivres(Connection connection) throws SQLException {
        List<Livre> livres = new ArrayList<>();
        String query = "SELECT * FROM livre";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Livre livre = new Livre(
                        resultSet.getInt("Code"),
                        resultSet.getString("Titre"),
                        resultSet.getString("Auteur"),
                        resultSet.getInt("NbLivre"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("Type")
                );
                livres.add(livre);
            }
        }

        return livres;
    }
    public static List<Livre> chercherLivresParAuteur(Connection connection, String auteur) throws SQLException {
        List<Livre> livresTrouves = new ArrayList<>();
        String query = "SELECT * FROM livre WHERE Auteur LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + auteur + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Livre livre = new Livre(
                            resultSet.getInt("Code"),
                            resultSet.getString("Titre"),
                            resultSet.getString("Auteur"),
                            resultSet.getInt("NbLivre"),
                            resultSet.getString("ISBN"),
                            resultSet.getString("Type")
                    );
                    livresTrouves.add(livre);
                }
            }
        }

        return livresTrouves;
    }
}
