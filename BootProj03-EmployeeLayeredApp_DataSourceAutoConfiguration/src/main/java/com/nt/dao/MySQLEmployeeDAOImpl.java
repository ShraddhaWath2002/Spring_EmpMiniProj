package com.nt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.nt.model.Employee;
@Repository("mysqlEmpDAO")
@Profile({"dev", "test"})
public class MySQLEmployeeDAOImpl implements IEmployeeDAO{
	private static final String GET_EMPS_BY_DESGS = "SELECT * FROM Employee WHERE JOB IN(?, ?, ?) ORDER BY JOB";
	
	@Autowired
	private DataSource ds;

	@Override
	public List<Employee> getEmployeesByDesgs(String desg1, String desg2, String desg3) throws Exception {
		System.out.println("EmployeeDAOImpl.getEmployeesByDesgs()::ds class name::"+ds.getClass());
		System.out.println("Oracle JDBC Driver Version::"+oracle.jdbc.OracleDriver.getJDBCVersion());
		List<Employee> list = null;
		try(Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(GET_EMPS_BY_DESGS);){
			ps.setString(1, desg1);
			ps.setString(2, desg2);
			ps.setString(3, desg3);
			
			try(ResultSet rs = ps.executeQuery();){
				list = new ArrayList();
				while(rs.next()) {
					Employee emp = new Employee();
					emp.setEno(rs.getInt(1));
					emp.setEname(rs.getString(2));
					emp.setDesg(rs.getString(3));
					emp.setSalary(rs.getDouble(4));
					emp.setDeptno(rs.getInt(5));
					
					list.add(emp);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
}
