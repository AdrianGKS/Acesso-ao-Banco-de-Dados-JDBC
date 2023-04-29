package application;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.util.Date;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        VendedorDao vendedorDao = DaoFactory.criarVendedorDao();

        System.out.println("===== TESTE 1: vendedor findById =======");
        Vendedor vendedor = vendedorDao.findById(3);
        System.out.println(vendedor);

        System.out.println("\n===== TESTE 2: vendedor findByDepartment =======");
        Departamento departamento = new Departamento(2, null);
        List<Vendedor> list = vendedorDao.findByDepartamento(departamento);

        for (Vendedor obj: list) {
            System.out.println(obj);
        }

        System.out.println("\n===== TESTE 3: vendedor findAll =======");
        list = vendedorDao.findAll();

        for (Vendedor obj: list) {
            System.out.println(obj);
        }
    }
}
