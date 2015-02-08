package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import webdziekanat.interfaces.IMarkDAO;
import webdziekanat.model.Lecturer;
import webdziekanat.model.Mark;
import webdziekanat.model.Student;
import webdziekanat.model.Term;
import webdziekanat.model.User;

@Component("markMB")
@Scope("application")
public class MarkManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9209614458615343840L;
    private static final Logger logger = LogManager.getLogger(MarkManagedBean.class);
    
    @Autowired
    IMarkDAO markDAO;
    
    private Mark mark = new Mark();
    
    private Student student = new Student();
    
    private Lecturer lecturer = new Lecturer();
    
    List<Mark> marks;
    
    boolean isAdd;
    boolean isEdit;
    
    private Map<Integer, Map<String, List<Mark>>> subjectsPerTerm;
    private Map<Term, List<Mark>> marksPerTerm;
    
    public String marksForStudent(Student src){
        this.student = src;
        return "/pages/studentMarks.xhtml";
    }
    
    public String prepareForStudent(User user){
        student = user.getStudent();
        marksPerTerm = new HashMap<Term, List<Mark>>();
        
        for(Mark mark : student.getMarks()){
            if(marksPerTerm.containsKey(mark.getTerm())){
                List<Mark> marks = marksPerTerm.get(mark.getTerm());
                marks.add(mark);
            } else {
                List<Mark> marks = new ArrayList<Mark>();
                marks.add(mark);
                marksPerTerm.put(mark.getTerm(), marks);
            }
        }
        List<Term> terms = new ArrayList<Term>();
        terms.addAll(marksPerTerm.keySet());
        for(Term term : terms){
            double marksSum = 0.0;
            int ectsSum = 0;
            
            for(Mark mark : marksPerTerm.get(term)){
                marksSum += mark.getMark() * mark.getSubject().getEcts();
                ectsSum += mark.getSubject().getEcts();
            }
            double average = marksSum/ectsSum;
            term.setAverage(average);
        }
        
        return "";
    }
    
    public String prepareForLecturer(User user){

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('marksList').hide();");
        lecturer = user.getLecturer();
        
        subjectsPerTerm = new HashMap<Integer, Map<String,List<Mark>>>();
        
        for(Mark mark : lecturer.getMarks()){
            if(subjectsPerTerm.containsKey(mark.getTerm().getId())){
                Map<String, List<Mark>> subjects = subjectsPerTerm.get(mark.getTerm().getId());
                if(subjects.containsKey(mark.getSubject().getName())){
                    subjects.get(mark.getSubject().getName()).add(mark);
                } else {
                    List<Mark> marks = new ArrayList<Mark>();
                    marks.add(mark);
                    subjects.put(mark.getSubject().getName(), marks);                    
                }
            } else {
                Map<String, List<Mark>> subjects = new HashMap<String, List<Mark>>();
                List<Mark> marks = new ArrayList<Mark>();
                marks.add(mark);
                subjects.put(mark.getSubject().getName(), marks);
                subjectsPerTerm.put(mark.getTerm().getId(), subjects);
            }
        }
        return "";

    }
    
    public void saveMarks(List<Mark> marks){
        for(Mark mark : marks){
            markDAO.updateMark(mark);
        }
    }
    
    public String startAdd(){
        mark = new Mark();
        isAdd = true;
        isEdit = false;
        return "/pages/addMark.xhtml";
    }
    
    public String addMark(){
        
        try {
            Mark markNew = new Mark(mark);
            markDAO.addMark(markNew);
            isAdd = false;
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Mark: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }
    
    public String startEdit(Mark src) {
        mark = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addMark.xhtml";
    }
    
    public String editMark(){
        markDAO.updateMark(mark);
        isEdit = false;
        return "/pages/success.xhtml";
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public List<Mark> getList() {
        return marks;
    }

    public void setList(List<Mark> list) {
        this.marks = list;
    }

    public IMarkDAO getMarkDAO() {
        return markDAO;
    }

    public void setMarkDAO(IMarkDAO markDAO) {
        this.markDAO = markDAO;
    }
    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Map<Integer, Map<String, List<Mark>>> getSubjectsPerTerm() {
        return subjectsPerTerm;
    }

    public void setSubjectsPerTerm(Map<Integer, Map<String, List<Mark>>> subjectsPerTerm) {
        this.subjectsPerTerm = subjectsPerTerm;
    }

    public Map<Term, List<Mark>> getMarksPerTerm() {
        return marksPerTerm;
    }

    public void setMarksPerTerm(Map<Term, List<Mark>> marksPerTerm) {
        this.marksPerTerm = marksPerTerm;
    }

}
