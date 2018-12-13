package com.juja.patterns.observer.soft;

public interface Recruiter {

    // Через этот метод соискатель подписывается на рассылку
    // регистрация кондидата лично
    void register(Candidate candidate);

    //кто-то может через этот метод направить новую вакансию
    void addNew(Vacancy vacancy);

    //кандидат может отказаться от услуг рекрутера
    void remove(Candidate candidate);

    // оповистить всех кандидатов
    void notice();
}
