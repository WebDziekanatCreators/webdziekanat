package webdziekanat.interfaces;

import java.util.List;

import webdziekanat.model.Semester;

public interface ISemesterDAO {
    public void addSemester(Semester semester);
    
    public void deleteSemester(int id);
    
    public void updateSemester(Semester semester);
    
    public Semester getSemesterById(int id);
    
    public List<Semester> getAll();

}
