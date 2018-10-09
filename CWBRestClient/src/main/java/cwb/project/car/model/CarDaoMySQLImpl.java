package cwb.project.car.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("carDaoMySQLImpl")
public class CarDaoMySQLImpl implements CarDao{

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void insertCar(Car car) {
		System.out.println(car.getColor());
		int i = 1 / 0;
		
	}
	
 
	@Autowired
	@Qualifier("jdbcTemplateMySQL")
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	

}
