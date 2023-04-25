package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VendedorDaoJDBC implements VendedorDao {

    private Connection connection;

    public VendedorDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Vendedor obj) {

    }

    @Override
    public void update(Vendedor obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Vendedor findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE seller.Id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Departamento departamento = new Departamento();
                departamento.setId(resultSet.getInt("DepartmentId"));
                departamento.setNome(resultSet.getString("DepName"));

                Vendedor vendedor = new Vendedor();
                vendedor.setId(resultSet.getInt("Id"));
                vendedor.setNome(resultSet.getString("Name"));
                vendedor.setEmail(resultSet.getString("Email"));
                vendedor.setSalarioBase(resultSet.getDouble("BaseSalary"));
                vendedor.setDataNascimento(resultSet.getDate("BirthDate"));
                vendedor.setDepartamento(departamento);

                return vendedor;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Vendedor> findAll() {
        return null;
    }
}
