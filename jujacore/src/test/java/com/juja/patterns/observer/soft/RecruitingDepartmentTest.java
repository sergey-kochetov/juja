package com.juja.patterns.observer.soft;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class RecruitingDepartmentTest {

    // внутрений класс-кандидат сохраняет вакансию.
    class SomeCandidate implements Candidate {
        Vacancy vacancy;

        @Override
        //когда кондидат уведомляет о новой вакансии, он ее сохраняет
        public void haveANew(Collection<Vacancy> vacancies) {
            assertEquals("Ожидается одна вакансия", 1, vacancies.size());
            this.vacancy = vacancies.iterator().next();
        }

        @Override
        public Set<String> getTechnologies() {
            return null;
        }
    }

    class VacanciesRecorder implements Candidate {
        List<Vacancy> vacancies = new LinkedList<>();
        @Override
        public void haveANew(Collection<Vacancy> vacancies) {

        }

        @Override
        public Set<String> getTechnologies() {
            return null;
        }
    }

}