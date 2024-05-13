package com.example.library.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtilTest {

    public static void main(String[] args) {
        try {
            // Obtenez une connexion à la base de données
            Connection connection = DatabaseUtil.getConnection();

            // Affichez un message si la connexion est réussie
            System.out.println("Connexion à la base de données établie avec succès.");

            // N'oubliez pas de fermer la connexion après utilisation

        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }
}
