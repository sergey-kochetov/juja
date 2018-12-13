package com.juja.patterns.observer.soft;

import java.util.*;

public class Project implements Vacancy {

    private String name;
    private Set<String> technologies = new HashSet<>();

    public Project(String name, String... technologies) {
        this.name = name;
        this.technologies.addAll(Arrays.asList(technologies));
    }

    @Override
    public boolean check(Set<String> technologies) {
        return this.technologies.containsAll(technologies);
    }

    @Override
    public String toString() {
        return String.format("%s-Project-%s", name, technologies);
    }
}
