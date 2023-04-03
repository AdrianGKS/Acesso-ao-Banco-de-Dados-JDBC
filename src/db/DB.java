package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection connection = null;

    public static Connection getConnection() { //starta conexão com banco de dados
        if (connection == null) { //se estiver null = desconectado
            try {
                Properties properties = loadProperties(); //carrega as propriedades do banco de dados
                String url = properties.getProperty("dburl"); //a url do banco de dados
                connection = DriverManager.getConnection(url, properties); //e enfim iniciar a conexão
            } catch (SQLException e) { 
                throw new DbException(e.getMessage()); //se ocorrer erro, cai na exceção
            }
        }
        return connection;
    }

    public static void closeConnection() { //encerra conexão com banco de dados
        if (connection != null) { //se estiver on != null
            try {
                connection.close(); //.close() encerra
            } catch (SQLException e) {
                throw new DbException(e.getMessage()); //se não, lança exceção
            }
        }
    }

    private static Properties loadProperties() { // carregando o arquivo com as propriedades do banco
        try (FileInputStream fs =  new FileInputStream("db.properties")){ // adiciona o arquivo .properties à variável fs.
            Properties properties = new Properties(); // instancia-se a classe Properties na variável properties
            properties.load(fs); // carrega o arquivo de propriedades na variável properties
            return properties; // retorna a variável properties
        } catch (IOException e) {
            throw new DbException(e.getMessage()); // se não, lança exceção
        }
    }

    public static void closeStatement(Statement statement) { // fecha o Statement = Query
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) { // fecha o Result Set = recebedor de Statement (query)
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
