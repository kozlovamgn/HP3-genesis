package com.example.genesis.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public final class PersonIdValidator {
    private static final String DATA_PERSON_ID_RESOURCE = "/dataPersonId.txt";

    private static final List<String> personIdList = new ArrayList<>();

    @PostConstruct
    private void init() throws CannotReadPersonIdListException {
        try (InputStream is = Objects.requireNonNull(PersonIdValidator.class.getResourceAsStream(DATA_PERSON_ID_RESOURCE));
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {

            // Clear the person ID list, just to make sure
            personIdList.clear();

            String line;

            while ((line = br.readLine()) != null) {
                personIdList.add(line);
            }
        } catch (IOException e) {
            throw new CannotReadPersonIdListException("Cannot read person ID list from resources", e);
        }
    }

    public void validateId(String id) throws InvalidPersonIdException {
        if (!personIdList.contains(id))
            throw new InvalidPersonIdException("Person ID " + id + " is not valid");
    }
}
