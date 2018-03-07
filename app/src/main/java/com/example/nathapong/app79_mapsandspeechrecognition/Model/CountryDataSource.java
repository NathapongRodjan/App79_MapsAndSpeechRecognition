package com.example.nathapong.app79_mapsandspeechrecognition.Model;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CountryDataSource {

    // 13.749326,  100.495929

    public static final String COUNTRY_KEY = "country";
    public static final float MINIMUM_CONFIDENCE_LEVEL = 0.4F;
    public static final String DEFAULT_COUNTRY_NAME = "Thailand";
    public static final double DEFAULT_COUNTRY_LATITUDE = 13.749326;
    public static final double DEFAULT_COUNTRY_LONGITUDE = 100.495929;
    public static final String DEFAULT_MESSAGE = "Be Happy !";


    private Hashtable<String, String> countriesAndMessages;


    // Constructor
    public CountryDataSource(Hashtable<String, String> countriesAndMessages) {
        this.countriesAndMessages = countriesAndMessages;
    }


    public String matchWithMinimumConfidenceLevelOfUserWords
            (ArrayList<String> userWords, float[] confidenceLevels){

        if (userWords == null || confidenceLevels == null){

            return DEFAULT_COUNTRY_NAME;
        }

        int numberOfUserWords = userWords.size();

        Enumeration<String> countries;

        for (int index = 0; index < numberOfUserWords && index < confidenceLevels.length; index++){

            if (confidenceLevels[index] < MINIMUM_CONFIDENCE_LEVEL){
                break;
            }

            String acceptedUserWord = userWords.get(index);

            countries = countriesAndMessages.keys();

            while (countries.hasMoreElements()){

                String selectedCountry = countries.nextElement();

                if (acceptedUserWord.equalsIgnoreCase(selectedCountry)){
                    return acceptedUserWord;
                }
            }
        }

        return DEFAULT_COUNTRY_NAME;
    }


    public String getTheInfoOfTheCountry(String country){

        return countriesAndMessages.get(country);
    }
}
