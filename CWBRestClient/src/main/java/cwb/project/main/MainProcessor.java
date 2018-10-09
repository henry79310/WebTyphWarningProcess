package cwb.project.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cwb.project.common.model.Processor;

@Component("mainProcessor")
public class MainProcessor implements Processor{

	private List<Processor> processors = new ArrayList<>();
	
	@Autowired
	@Qualifier("carProcessor")
	public void setCarProcessor(Processor carProcessor) {
		this.processors.add(carProcessor);
	}
	
	
	@Override
	public void process() {
		// TODO Auto-generated method stub
		for(Processor processor:processors) {
			processor.process();
		}
	}
	
	

}
