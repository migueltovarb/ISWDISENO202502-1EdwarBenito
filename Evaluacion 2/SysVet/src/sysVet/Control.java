package sysVet;
import java.util.*;
public class Control {
	private String date;
	private String observation;
	private TypeControl controlType;
	
	public Control(String date, String observation) {
		super();
		this.date = date;
		this.observation = observation;
	}

	public String  getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public TypeControl getControlType() {
		return controlType;
	}

	public void setControlType(TypeControl controlType) {
		this.controlType = controlType;
	}

	@Override
	public String toString() {
		return "Control [date=" + date + ", observation=" + observation + "]";
	}
	
	

}
