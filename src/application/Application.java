package application;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.util.Date;

public class Application {

    public static void main(String[] args) {

        VendedorDao vendedorDao = DaoFactory.criarVendedorDao();
        Vendedor vendedor = vendedorDao.findById(3);
        System.out.println(vendedor);
    }
}
