package com.juja.patterns.observer.soft;

import java.util.LinkedList;
import java.util.List;

public class Candidates {

    private List<Candidate> list = new LinkedList<>();

    public void moveTo(Candidates candidates) {
        candidates.list.addAll(list);
        list.clear();
    }

    public void noticeAll(Vacancies vacancies) {
        for (Candidate candidate : list) {
            // фильтруем список
            Vacancies selected = vacancies.filter(candidate.getTechnologies());
            if (!selected.isEmpty()) {
                candidate.haveANew(selected.getCopy());
            }
        }
    }
    //удаляем кондидата
    public void removeCandidate(Candidate candidate) {
        list.remove(candidate);
    }

    public void add(Candidate candidate) {
        list.add(candidate);
    }
}
