package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {

    private final AtomicInteger nexId = new AtomicInteger(0);

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(0, "Andrey", "Java Dev", LocalDateTime.now(), 2));
        save(new Candidate(0, "Petr", "Mentor", LocalDateTime.now(), 1));
        save(new Candidate(0, "Ivan", "QA", LocalDateTime.now(), 3));
        save(new Candidate(0, "Dmitriy", "PM", LocalDateTime.now(), 1));
        save(new Candidate(0, "Denis", "Analytics", LocalDateTime.now(), 3));
    }

    @Override
    public Candidate save(Candidate candidate) {
        int currentId = nexId.get();
        if (currentId == nexId.get()) {
            candidate.setId(nexId.getAndIncrement());
        }
        candidates.putIfAbsent(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> new Candidate(
                oldCandidate.getId(), candidate.getName(),
                candidate.getDescription(), candidate.getCreationDate(), candidate.getCityId())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
