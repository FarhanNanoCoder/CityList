package com.example.citylist;

import org.junit.Test;

import static org.junit.Assert.*;

public class CityListTest {
    private CityList mockCityList() {
        CityList cityList = new CityList();
        cityList.add(mockCity());
        return cityList;
    }

    private City mockCity() {
        return new City("Edmonton", "AB");
    }

    /**
     * Unit test for adding a city in the cityList and it doesn't allow multiple existence.
     * Here firstly mockCityList() adds a city and so expected 1 in size test
     * Again another city is added so expected 2 in size test
     */
    @Test
    public void testAdd() {
        CityList cityList = mockCityList();
        assertEquals(1, cityList.getCities(false).size());

        City city = new City("Regina", "SK");
        cityList.add(city);

        assertEquals(2, cityList.getCities(false).size());
        assertTrue(cityList.getCities(false).contains(city));
    }

    /**
     * This deletes a city and throws exception if the city ' exist it throws a exception
     * Firstly we added two cities and deleted city1.
     * Now if we find the city1 in cityList it will not be able to find it.
     */
    @Test
    public void testDelete() {
//        CityList cityList = mockCityList();
        CityList cityList = new CityList();
        City city1 = new City("Regina", "SK");
        City city2 = new City("London", "US");
        cityList.add(city1);
        cityList.add(city2);

       cityList.delete(city1);
       assertFalse(cityList.getCities().contains(city1));
    }

    /**
     * This checks in city add exception when that city is already present in the list
     */
    @Test
    public void testAddException() {
        CityList cityList = new CityList();
        City city = mockCity();
        cityList.add(city);

        assertThrows(IllegalArgumentException.class, () -> {
            cityList.add(city);
        });
    }

    /**
     * This checks the delete exceptions.
     * Firstly we added two cities - city1, city2.
     * Then checked the size of the list which should be 2.
     * Then deleted the city1 and now the size of the list which should be 1.
     * After that we tried to delete city1 which is already deleted and thus we check the IllegalArgumentException.class.
     */
    @Test
    public void testDeleteException() {
//        CityList cityList = mockCityList();
        CityList cityList = new CityList();
        City city1 = new City("Regina", "SK");
        City city2 = new City("London", "US");
        cityList.add(city1);
        cityList.add(city2);
        assertEquals(2,cityList.count());
        cityList.delete(city1);
        assertEquals(1,cityList.count());
        assertThrows(IllegalArgumentException.class, () -> {
            cityList.delete(city1);
        });
    }

    /**
     * At first we added city1 and checking the index to it in the list which should be 0.
     * Then we added another city.
     * Now if we sort the city based of province param then in 0 index we should find the first city.So the string comparison should be 0.
     * Now if we sort the city based of province param then in 1 index we should find the second city.So the string comparison should be 0.
     */
    @Test
    public void testGetCities() {
        CityList cityList = new CityList();
        City city1 = new City("Regina", "CD");
        cityList.add(city1);
        assertEquals(0, city1.compareTo(cityList.getCities(false).get(0)));

        City city = new City("Charlottetown", "Prince Edward Island");
        cityList.add(city);

        assertEquals(0, city.compareTo(cityList.getCities(false).get(0)));
        assertEquals(0, city1.compareTo(cityList.getCities(false).get(1)));
    }
}
