package com.example.library.model;

import com.example.library.database.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Emprunt {
    private int idEmprunt;
    private int codeLivre;
    private int codeLecteur;
    private Date dateEmprunt;
    private Date dateRetourPrevu;
    private Date dateRetourEffective;

    public Emprunt(int codeLivre, int codeLecteur, Date dateEmprunt, Date dateRetourPrevu, Date dateRetourEffective) {
        this.codeLivre = codeLivre;
        this.idEmprunt = 0 ;
        this.codeLecteur = codeLecteur;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevu = dateRetourPrevu;
        this.dateRetourEffective = dateRetourEffective;
    }
    public Emprunt(int idEmprunt, int codeLivre, int codeLecteur, Date dateEmprunt, Date dateRetourPrevu, Date dateRetourEffective) {
        this.idEmprunt = idEmprunt;
        this.codeLivre = codeLivre;
        this.codeLecteur = codeLecteur;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevu = dateRetourPrevu;
        this.dateRetourEffective = dateRetourEffective;
    }

    public int getIdEmprunt() {
        return idEmprunt;
    }

    public void setIdEmprunt(int idEmprunt) {
        this.idEmprunt = idEmprunt;
    }

    public int getCodeLivre() {
        return codeLivre;
    }

    public void setCodeLivre(int codeLivre) {
        this.codeLivre = codeLivre;
    }

    public int getCodeLecteur() {
        return codeLecteur;
    }

    public void setCodeLecteur(int codeLecteur) {
        this.codeLecteur = codeLecteur;
    }

    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public Date getDateRetourPrevu() {
        return dateRetourPrevu;
    }

    public void setDateRetourPrevu(Date dateRetourPrevu) {
        this.dateRetourPrevu = dateRetourPrevu;
    }

    public Date getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(Date dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    public static List<Emprunt> getAllEmpruntsFromDatabase() throws SQLException {
        List<Emprunt> emprunts = new ArrayList<>();

        String query = "SELECT * FROM emprunt";

        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Emprunt emprunt = new Emprunt(
                        resultSet.getInt("IdEmprunt"),
                        resultSet.getInt("CodeLivre"),
                        resultSet.getInt("CodeLecteur"),
                        resultSet.getDate("DateEmprunt"),
                        resultSet.getDate("DateRetourPrevu"),
                        resultSet.getDate("DateRetourEffective")
                );

                emprunts.add(emprunt);
            }
        }

        return emprunts;
    }

    public void ajouterEmprunt() throws SQLException {
        Connection connection = DatabaseUtil.getConnection();

        String query = "INSERT INTO emprunt (CodeLivre, CodeLecteur, DateEmprunt, DateRetourPrevu) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, codeLivre);
            preparedStatement.setInt(2, codeLecteur);
            preparedStatement.setDate(3, dateEmprunt);
            preparedStatement.setDate(4, dateRetourPrevu);

            // Exécute l'insertion
            preparedStatement.executeUpdate();

            // Récupère la clé générée automatiquement (idEmprunt)
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                idEmprunt = generatedKeys.getInt(1);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }


    public void retournerEmprunt(Connection connection, int idEmpruntToUpdate) throws SQLException {
        String query = "UPDATE emprunt SET dateRetourEffective = CURRENT_DATE WHERE idEmprunt = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idEmpruntToUpdate);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void retournerEmpruntById(int idEmpruntToUpdate) throws SQLException {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            retournerEmprunt(connection, idEmpruntToUpdate);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

}
