package application;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.util.Date;
import java.util.Scanner;

public class Application2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DepartamentoDao departamentoDao = DaoFactory.criarDepartamentoDao();

        System.out.println("\n===== TESTE 1: departamento - insert =======");
        Departamento departamento = new Departamento(null, "Marketing");
        departamentoDao.insert(departamento);
        System.out.println("Inserido! Novo id = " + departamento.getId());

        System.out.println("\n===== TESTE 2: departamento - update =======");
        departamento = departamentoDao.findById(1);
        departamento.setNome("Carrer");
        departamentoDao.update(departamento);
        System.out.println("Atualização concluída com sucesso.");

        System.out.println("\n===== TESTE 4: departamento - findById =====");
        departamento = departamentoDao.findById(3);
        System.out.println(departamento);
    }
}
