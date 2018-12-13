package com.juja.patterns.observer.soft;

public class RecruitingDepartment implements Recruiter {

    // реккрутеру нужно отличать как-то новых кандидатов от старых
    // тут будут храниться новые кандидаты
    private Candidates newCandidates = new Candidates();

    // а эта старая база резюме кандидатов - для тех, кто хоть раз полчал уведомление
    private Candidates candidates = new Candidates();

    // рекрутеру нужно временно хранить свои новые вакансии
    private Vacancies newVacancies = new Vacancies();

    // но так же ему нужно гдето складывать где-то вакансии, которые уже отправлялись
    private Vacancies vacancies = new Vacancies();

    @Override
    // момент, когда кондидат обращается к рекрутеру
    public void register(Candidate candidate) {
        // сохраняем кандидата
        newCandidates.add(candidate);

    }

    @Override
    // пришла новая вакансия
    public void addNew(Vacancy vacancy) {
        newVacancies.add(vacancy);
    }

    @Override
    // отписка от рассылки
    public void remove(Candidate candidate) {
        // удаляем из основного и нового списка
        candidates.removeCandidate(candidate);
        newCandidates.removeCandidate(candidate);
    }

    @Override
    // метод оповещает всех кандидатов
    public void notice() {
        // он достает список и уведомляет всех стареньких обо ввсем
        candidates.noticeAll(newVacancies);
        // после он переносит новые вакансии в список-историю
        newVacancies.moveTo(vacancies);
        //потом он уведомляет всех новеньких всеми вакансиями
        newCandidates.noticeAll(vacancies);
        // после переносит новеньких кандидатов в основной список
        newCandidates.moveTo(candidates);
    }
}
