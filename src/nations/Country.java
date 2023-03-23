package nations;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Country {
    private String name;
    private Set<String> languages;

    private int population;

    private long GPD;

    public Country(String name, int population, long GPD) {
        this.name = name;
        this.population = population;
        this.GPD = GPD;
        languages = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public long getGPD() {
        return GPD;
    }

    public void setGPD(long GPD) {
        this.GPD = GPD;
    }

    public void addLanguage(String lang){
         languages.add(lang);
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", languages=" + languages +
                ", population=" + population +
                ", GPD=" + GPD +
                '}';
    }
}
