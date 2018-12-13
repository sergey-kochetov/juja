package com.juja.patterns.observer.soft;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Vacancies {

    private List<Vacancy> list = new LinkedList<>();

    // метод перемещения вакансий в другой список
    void moveTo(Vacancies vacancies) {
        vacancies.list.addAll(list);
        list.clear();
    }
    // добовляем вакансии в список
    void add(Vacancy vacancy) {
        list.add(vacancy);
    }

    boolean isEmpty() {
        return list.isEmpty();
    }

    // метод фильтрации списка вакансий по категориям
    Vacancies filter(Set<String> technologies) {
        Vacancies result = new Vacancies();
        list.stream()
                .filter(vacancy -> vacancy.check(technologies))
                .forEach(result::add);
        return result;
    }
    // а это новый метод копирования списка вакансий
    Collection<Vacancy> getCopy() {
        return new LinkedList<>(list);
    }
}
