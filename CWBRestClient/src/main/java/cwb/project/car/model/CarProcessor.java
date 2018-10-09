package cwb.project.car.model;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cwb.project.common.model.Processor;


@Component("carProcessor")
public class CarProcessor implements Processor{
	
	private static final Logger processorLogger = LogManager.getLogger(CarProcessor.class);

	
	@Autowired
	@Qualifier("carRestClient")
	private CarRestClient carRestClient;
	
	@Autowired
	@Qualifier("carDaoMySQLImpl")
	private CarDaoMySQLImpl carDaoMySQLImpl;
	
	public void process() {
		try {
			Car car = carRestClient.requestCar();
			carDaoMySQLImpl.insertCar(car);
		} catch (Exception e) {
			processorLogger.error("", e);
			//e.printStackTrace();
		} 
		
		
		
		
	}

}
