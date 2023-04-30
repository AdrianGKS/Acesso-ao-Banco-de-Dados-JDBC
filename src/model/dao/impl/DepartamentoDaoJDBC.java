package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.sql.*;
import java.util.List;

public class DepartamentoDaoJDBC implements DepartamentoDao {

    private Connection connection;

    public DepartamentoDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Departamento obj) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO department\n" +
                        "(Name)\n" +
                        "VALUES (?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, obj.getNome());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();

                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha afetada.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Departamento obj) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE department\n" +
                        "SET Name = ?\n" +
                        "WHERE Id = ?");

            preparedStatement.setString(1, obj.getNome());
            preparedStatement.setInt(2, obj.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Departamento findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT Id, Name\n" +
                            "FROM department\n" +
                            "WHERE Id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Departamento departamento = instantiateDepartamento(resultSet);
                return departamento;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    private Departamento instantiateDepartamento(ResultSet resultSet) throws SQLException{
        Departamento departamento = new Departamento();
        departamento.setId(resultSet.getInt("Id"));
        departamento.setNome(resultSet.getString("Name"));
        return departamento;
    }

    @Override
    public List<Departamento> findAll() {
        return null;
    }
}
