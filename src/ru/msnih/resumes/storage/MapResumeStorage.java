package ru.msnih.resumes.storage;

import ru.msnih.resumes.model.Resume;

import java.security.cert.CollectionCertStoreParameters;
import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doSave(Resume resume, Resume keyResume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Resume keyResume) {
        doSave(resume, null);
    }

    @Override
    protected void doDelete(Resume keyResume) {
        map.remove(keyResume.getUuid());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Resume keyResume) {
        return keyResume != null;
    }

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> resumes = new ArrayList<>(map.values());
        Collections.sort(resumes);
        return resumes;
    }


    @Override
    public int size() {
        return map.size();
    }
}
