package beerduff.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Beer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "max_temp", nullable = false)
    private int maxTempInCelsius;
    @Column(name = "min_temp", nullable = false)
    private int minTempInCelsius;
    @Column(name = "average_temp", nullable = false)
    private int averageTempInCelsius;

    public Beer() {
        super();
    }

    public Beer(String name, int minTemp, int maxTemp) {
        super();
        this.name = name;
        this.maxTempInCelsius = maxTemp;
        this.minTempInCelsius = minTemp;
        calcAverageTempInCelsius();
    }

    public int getMaxTemp() {
        return maxTempInCelsius;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTempInCelsius = maxTemp;
        calcAverageTempInCelsius();
    }

    public int getMinTemp() {
        return minTempInCelsius;
    }

    public void setMinTemp(int minTemp) {
        this.minTempInCelsius = minTemp;
        calcAverageTempInCelsius();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAverageTempInCelsius() {
        return averageTempInCelsius;
    }

    private void calcAverageTempInCelsius() {
        int sumTemp = (this.minTempInCelsius + this.maxTempInCelsius);
        this.averageTempInCelsius = Math.round(sumTemp / 2);
    }

    @Override
    public String toString() {
        return "Beer [id=" + id + ", name=" + name + ", maxTempInCelsius=" + maxTempInCelsius + ", minTempInCelsius="
                + minTempInCelsius + "]";
    }
}
