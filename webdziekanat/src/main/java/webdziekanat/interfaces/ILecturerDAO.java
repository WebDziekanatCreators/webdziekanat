package webdziekanat.interfaces;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import webdziekanat.model.Lecturer;
import webdziekanat.model.Subjects;

public interface ILecturerDAO {
    public void addLecturer(Lecturer lecturer);
    
    public boolean deleteLecturer(int id);
    
    public void updateLecturer(Lecturer lecturer);
    
    public Lecturer getLecturerById(int id);
    
    public List<Lecturer> getAll();

    public void addLecturerToSubjects(Lecturer lecturer,Set<Subjects> subs);

    public void deleteLecturerFormSubject(Lecturer lecturer,Set<Subjects> subs);

}
