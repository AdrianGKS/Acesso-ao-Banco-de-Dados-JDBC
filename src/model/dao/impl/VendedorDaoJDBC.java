package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendedorDaoJDBC implements VendedorDao {

    private Connection connection;

    public VendedorDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Vendedor obj) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO seller\n" +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId)\n" +
                            "VALUES\n" +
                            "(?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, obj.getNome());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(obj.getDataNascimento().getTime()));
            preparedStatement.setDouble(4, obj.getSalarioBase());
            preparedStatement.setInt(5, obj.getDepartamento().getId());

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
    public void update(Vendedor obj) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE seller\n" +
                        "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\n" +
                        "WHERE Id = ?");

            preparedStatement.setString(1, obj.getNome());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(obj.getDataNascimento().getTime()));
            preparedStatement.setDouble(4, obj.getSalarioBase());
            preparedStatement.setInt(5, obj.getDepartamento().getId());
            preparedStatement.setInt(6, obj.getId());

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
                Departamento departamento = instantiateDepartamento(resultSet);
                Vendedor vendedor = instantiateVendedor(resultSet, departamento);

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

    private Vendedor instantiateVendedor(ResultSet resultSet, Departamento departamento) throws SQLException{
        Vendedor vendedor =  new Vendedor();
        vendedor.setId(resultSet.getInt("Id"));
        vendedor.setNome(resultSet.getString("Name"));
        vendedor.setEmail(resultSet.getString("Email"));
        vendedor.setSalarioBase(resultSet.getDouble("BaseSalary"));
        vendedor.setDataNascimento(resultSet.getDate("BirthDate"));
        vendedor.setDepartamento(departamento);
        return vendedor;
    }

    private Departamento instantiateDepartamento(ResultSet resultSet) throws SQLException {
        Departamento departamento = new Departamento();
        departamento.setId(resultSet.getInt("DepartmentId"));
        departamento.setNome(resultSet.getString("DepName"));
        return departamento;
    }

    @Override
    public List<Vendedor> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName \n" +
                            "FROM seller INNER JOIN department \n" +
                            "ON seller.DepartmentId = department.Id\n" +
                            "ORDER BY Name");

            resultSet = preparedStatement.executeQuery();

            List<Vendedor> list = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (resultSet.next()) {
                Departamento dep = map.get(resultSet.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartamento(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }

                Vendedor vendedor = instantiateVendedor(resultSet, dep);
                list.add(vendedor);
            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Vendedor> findByDepartamento(Departamento departamento) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName \n" +
                    "FROM seller INNER JOIN department \n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "WHERE DepartmentId = ?\n" +
                    "ORDER BY Name");

            preparedStatement.setInt(1, departamento.getId());

            resultSet = preparedStatement.executeQuery();

            List<Vendedor> list = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (resultSet.next()) {
                Departamento dep = map.get(resultSet.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartamento(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }

                Vendedor vendedor = instantiateVendedor(resultSet, dep);
                list.add(vendedor);
            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }
}
