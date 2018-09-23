package beerduff.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Beer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private int maxTempInCelsius;
	private int minTempInCelsius;

	public Beer() {
		super();
	}

	public Beer(String name, int maxTemp, int minTemp) {
		super();
		this.name = name;
		this.maxTempInCelsius = maxTemp;
		this.minTempInCelsius = minTemp;
	}

	public int getMaxTemp() {
		return maxTempInCelsius;
	}

	public void setMaxTemp(int maxTemp) {
		this.maxTempInCelsius = maxTemp;
	}

	public int getMinTemp() {
		return minTempInCelsius;
	}

	public void setMinTemp(int minTemp) {
		this.minTempInCelsius = minTemp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
