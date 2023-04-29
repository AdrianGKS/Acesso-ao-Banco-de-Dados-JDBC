package application;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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

        System.out.println("\n===== TESTE 4: vendedor insert =======");
        Vendedor novoVendedor = new Vendedor(null, "Greg", "greg@gmail.com", new Date(), 4000.0, departamento);
        vendedorDao.insert(novoVendedor);
        System.out.println("Inserido! Novo id = " + novoVendedor.getId());

        System.out.println("\n===== TESTE 5: vendedor update =======");
        vendedor = vendedorDao.findById(1);
        vendedor.setNome("Marta Castilhos");
        vendedorDao.update(vendedor);
        System.out.println("Atualização completa!");

        System.out.println("\n===== TESTE 6: vendedor delete =======");
        System.out.println("Insira Id para deletar: ");
        int id = sc.nextInt();
        vendedorDao.deleteById(id);
        System.out.println("Deletado!");

        sc.close();
    }
}