package com.juja.patterns.observer.soft;

import java.util.Collection;
import java.util.Set;

interface Candidate {

    // кандидат умеет получать уведомление о вакансиях
    void haveANew(Collection<Vacancy> vacancies);

    Set<String> getTechnologies();
}
