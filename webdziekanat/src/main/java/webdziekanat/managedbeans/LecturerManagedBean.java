package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;

import webdziekanat.interfaces.ILecturerDAO;
import webdziekanat.model.Lecturer;

@ManagedBean(name="lecturerMB")
@RequestScoped
public class LecturerManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4741255386760250882L;
    private static final Logger logger = LogManager.getLogger(LecturerManagedBean.class);
    
    @ManagedProperty(value="#{lecturerDAO}")
    ILecturerDAO lecturerDAO;
    
    Lecturer lecturer = new Lecturer();
    
    List<Lecturer> lectureres;
    
    public String addLecturer(){
        
        try {
            Lecturer lecturerNew = new Lecturer(lecturer);
            lecturerDAO.addLecturer(lecturerNew);
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Lecturer: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public List<Lecturer> getList() {
        return lectureres;
    }

    public void setList(List<Lecturer> list) {
        this.lectureres = list;
    }

    public ILecturerDAO getLecturerDAO() {
        return lecturerDAO;
    }

    public void setLecturerDAO(ILecturerDAO lecturerDAO) {
        this.lecturerDAO = lecturerDAO;
    }

}
