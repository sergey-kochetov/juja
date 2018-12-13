package com.juja.patterns.observer.soft;

public class Main {

    public static void main(String[] args) {
        // наш рекрутинг
        Recruiter recruiter = new RecruitingDepartment();

        System.out.println("----- Day1 -----");

        //двое кандидатов каждый со своим опытом
        Candidate candidate1 = new CandidateImpl("Eva", "J2EE");
        Candidate candidate2 = new CandidateImpl("Adam", "J2EE", "Android");

        // подготовили и отправили резюме рекрутеру
        recruiter.register(candidate1);
        recruiter.register(candidate2);

        // рекрутер за день собрал три компании
        recruiter.addNew(new Project("NifNif", "J2SE", "Android"));
        recruiter.addNew(new Project("NufNuf", "J2EE"));

        // и вечером отправил рассылку
        recruiter.notice();

        System.out.println("----- Day2 -----");

        Candidate candidate3 = new CandidateImpl("Mr.Angular", "JavaScript", "J2SE", "J2EE", "Angular");
        Candidate candidate4 = new CandidateImpl("NoName", "JavaScript", "J2SE");

        recruiter.register(candidate3);
        recruiter.register(candidate4);

        recruiter.remove(candidate1);

        recruiter.addNew(new Project("NafNaf", "JavaScript", "J2SE"));
        recruiter.addNew(new Project("NifNif", "Android", "J2SE"));
        recruiter.addNew(new Project("NufNuf",  "J2EE"));

        recruiter.notice();


    }
}
