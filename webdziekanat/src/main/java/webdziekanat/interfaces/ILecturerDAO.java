package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Lecturer;

public interface ILecturerDAO {
    public void addLecturer(Lecturer lecturer);
    
    public void deleteLecturer(int id);
    
    public void updateLecturer(Lecturer lecturer);
    
    public Lecturer getLecturerById(int id);
    
    public List<Lecturer> getAll();
}
