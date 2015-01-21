package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;

import webdziekanat.interfaces.IMarkDAO;
import webdziekanat.model.Mark;

@ManagedBean(name="markMB")
@RequestScoped
public class MarkManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9209614458615343840L;
    private static final Logger logger = LogManager.getLogger(MarkManagedBean.class);
    
    @ManagedProperty(value="#{markDAO}")
    IMarkDAO markDAO;
    
    Mark mark = new Mark();
    
    List<Mark> markes;
    
    public String addMark(){
        
        try {
            Mark markNew = new Mark(mark);
            markDAO.addMark(markNew);
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Mark: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public List<Mark> getList() {
        return markes;
    }

    public void setList(List<Mark> list) {
        this.markes = list;
    }

    public IMarkDAO getMarkDAO() {
        return markDAO;
    }

    public void setMarkDAO(IMarkDAO markDAO) {
        this.markDAO = markDAO;
    }

}
