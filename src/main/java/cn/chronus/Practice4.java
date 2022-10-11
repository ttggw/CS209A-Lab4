package cn.chronus;

import java.io.IOException;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Practice4 {
    public static class City
    {
        private String name;
        private String state;
        private int population;

        public City(String name, String state, int population)
        {
            this.name = name;
            this.state = state;
            this.population = population;
        }

        public String getName()
        {
            return name;
        }

        public String getState()
        {
            return state;
        }

        public int getPopulation()
        {
            return population;
        }

        @Override
        public String toString() {
            return "City{name='" + this.name + "', state='" + this.state + "', population=" + this.population + "}";
        }

    }

    public static Stream<City> readCities(String filename) throws IOException
    {
        return Files.lines(Paths.get(filename))
                .map(l -> l.split(", "))
                .map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
    }

    public static void main(String[] args) throws IOException, URISyntaxException {

        Stream<City> cities = readCities("src/cities.txt");
        // Q1: count how many cities there are for each state
        Map<String, Long> cityCountPerState = cities.collect(Collectors.groupingBy(City::getState, Collectors.counting()));
        System.out.println("# of cities per state:");
        System.out.println(cityCountPerState);
        System.out.println();


        cities = readCities("src/cities.txt");
        // Q2: count the total population for each state
        Map<String, Integer> statePopulation = cities.collect(Collectors.groupingBy(City::getState,
                Collectors.summingInt(City::getPopulation)));
        System.out.println("population per state");
        System.out.println(statePopulation);
        System.out.println();

        cities = readCities("src/cities.txt");
        // Q3: for each state, get the set of cities with >500,000 population
//        cities = cities.filter(city -> city.getPopulation() > 500000);
        Map<String, Set<City>> largeCitiesByState = cities.collect(Collectors.groupingBy(City::getState,
                Collectors.filtering(city -> city.getPopulation() > 500000, Collectors.toSet())));
        System.out.println("cities with >500,000 population for each state:");
        for (String key : largeCitiesByState.keySet()) {
            System.out.println(key + ": " + largeCitiesByState.get(key));
        }
    }
}

