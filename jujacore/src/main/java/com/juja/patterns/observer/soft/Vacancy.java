package com.juja.patterns.observer.soft;

import java.util.Set;

interface Vacancy {

    // вакансия содержит набор требуемых технологий
    // вакансия умеет отвечать на вопрос, подходит ли ей список технологий или нет
    boolean check(Set<String> technologies);

}
