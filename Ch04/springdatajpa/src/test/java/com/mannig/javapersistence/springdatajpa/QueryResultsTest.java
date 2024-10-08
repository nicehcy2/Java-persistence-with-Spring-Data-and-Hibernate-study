package com.mannig.javapersistence.springdatajpa;

import com.mannig.javapersistence.springdatajpa.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryResultsTest extends SpringdatajpaApplicationTests {

    @Test
    void testStreamable() {

        try(Stream<User> result =
                userRepository.findByEmailContaining("someother")
                        .and(userRepository.findByLevel(2))
                        .stream().distinct()) {
            assertEquals(6, result.count());
        }
    }

    @Test
    void testFindByArrayAndSort() {

        List<Object[]> usersList1 = userRepository.findByAsArrayAndSort("ar", Sort.by("username"));
        List<Object[]> usersList2 = userRepository.findByAsArrayAndSort("ar", Sort.by("email_length").descending());
        List<Object[]> usersList3 = userRepository.findByAsArrayAndSort("ar", JpaSort.unsafe("LENGTH(u.email)"));

        assertAll(
                () -> assertEquals(2, usersList1.size()),
                () -> assertEquals("darren", usersList1.get(0)[0]),
                () -> assertEquals(21, usersList1.get(0)[1]),
                () -> assertEquals(2, usersList2.size()),
                () -> assertEquals("marion", usersList2.get(0)[0]),
                () -> assertEquals(26, usersList2.get(0)[1]),
                () -> assertEquals(2, usersList3.size()),
                () -> assertEquals("darren", usersList3.get(0)[0]),
                () -> assertEquals(21, usersList3.get(0)[1])
        );
    }
}
