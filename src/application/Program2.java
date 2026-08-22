package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;


public class Program2 {
    public static void main(String[] args) {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        Department dp = departmentDao.findById(3);
        System.out.println(dp);

        System.out.println();

        List<Department> departmentList = departmentDao.findAll();

        for (Department dep : departmentList){
            System.out.println(dep);
        }

    }
}
