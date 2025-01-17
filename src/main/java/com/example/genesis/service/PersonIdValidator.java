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
import java.util.Random;

@Service
public class PersonIdGenerator {
    private static final String DATA_PERSON_ID_RESOURCE = "/dataPersonId.txt";

    private static final Random random = new Random();
    private static final List<String> personIdList = new ArrayList<>();

    @PostConstruct
    private void init() throws CannotReadPersonIdListException {
        try (InputStream is = Objects.requireNonNull(PersonIdGenerator.class.getResourceAsStream(DATA_PERSON_ID_RESOURCE));
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

    public String generateId() {
        int index = random.nextInt(personIdList.size());
        return personIdList.get(index);
    }
}
