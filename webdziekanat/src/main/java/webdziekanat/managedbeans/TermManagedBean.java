package webdziekanat.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import webdziekanat.Resources.Messages;
import webdziekanat.finders.DatabaseFinder;
import webdziekanat.interfaces.ICourseDAO;
import webdziekanat.interfaces.ILecturerDAO;
import webdziekanat.interfaces.IMarkDAO;
import webdziekanat.interfaces.IStudentDAO;
import webdziekanat.interfaces.ISubjectDAO;
import webdziekanat.interfaces.ITermDAO;
import webdziekanat.model.Lecturer;
import webdziekanat.model.Mark;
import webdziekanat.model.Student;
import webdziekanat.model.Subjects;
import webdziekanat.model.Term;

@Component("termMB")
@Scope("application")
public class TermManagedBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -883480192688558715L;
    

    private static final Logger logger = LogManager.getLogger(TermManagedBean.class);
    
    @Autowired
    ITermDAO termDAO;
    
    Term term = new Term();

    @Autowired
    private ISubjectDAO subjectDAO;
    
    @Autowired
    private ILecturerDAO lecturerDAO;
    
    @Autowired
    private IStudentDAO studentDAO;
    
    @Autowired
    private IMarkDAO markDAO;
    
    @Autowired
    private ICourseDAO courseDAO;
    
    @Autowired
    private DatabaseFinder finder;
    
    private List<Subjects> subjects;

    private List<Subjects> filteredSubjects;

    private Map<Subjects, Boolean> checkMap = new HashMap<Subjects, Boolean>();
    
    private DualListModel<Lecturer> lecturers = new DualListModel<Lecturer>();
    
    private Subjects subject;
    
    private List<Term> terms;
    
   
    boolean isAdd;
    boolean isEdit;
    
    @PostConstruct
    public void init() {
        List<Subjects> allSubjects = subjectDAO.getAll();
        subjects = new ArrayList<Subjects>();
        checkMap = new HashMap<Subjects, Boolean>();
        boolean subjectExists = false;
        for (Subjects subject : allSubjects) {
            for(Subjects subjectInTerm : term.getSubjectsList()){
                if(subjectInTerm.getId() == subject.getId()){
                    subjectExists = true;
                    break;
                }
            }
            if(subjectExists){
                subjectExists = false;
                continue;
            }
            subjects.add(subject);
            checkMap.put(subject, Boolean.FALSE);
        }
    }
    
    public void detailsReload(ComponentSystemEvent event){
        init();
    }
    
    public String startAdd(){
        term = new Term();
        isAdd = true;
        isEdit = false;
        return "/pages/addTerm.xhtml";
    }
    
    public String addTerm(){
        
        try {
            Term termNew = new Term(term);
            termDAO.addTerm(termNew);
            isAdd = false;
            return "/success.xhtml";
        } catch (DataAccessException e) {
            logger.error("Error while adding new Term: " + e.getMessage());
            e.printStackTrace();
            return "/error.xhtml"; 
        }
    }
    
    public void load(){
        
    }
    
    public void addSubjectForm(Term src){
        term = src;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('addSubject').show();");
    }
    
    public void displayAddLecturerForm(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('assignLecturersForm').show();");
    }
    
    public void assignLecturersForm(Term src, Subjects sbj){
        term = src;
        subject = sbj;
        List<Lecturer> selectedLecturers = new ArrayList<Lecturer>();
        List<Lecturer> srcLecturers = new ArrayList<Lecturer>();
        boolean lecturerAlreadyAdded = false;
        for(Lecturer lecturer : sbj.getLecturers()){
            for(Mark mark : lecturer.getMarks()){
                if(mark.getTerm().getId() == term.getId() && mark.getSubject().getId() == sbj.getId()){
                    lecturerAlreadyAdded = true;
                    break;
                }
            }
            if(lecturerAlreadyAdded){
                lecturerAlreadyAdded = false;
                continue;
            }
            srcLecturers.add(lecturer);
        }
        lecturers = new DualListModel<Lecturer>(srcLecturers, selectedLecturers);
        displayAddLecturerForm();
    }
    
    public void addSubjects(){
        Set<Subjects> result = new HashSet<Subjects>();
        for (Entry<Subjects, Boolean> entry : checkMap.entrySet()) {
            if(entry.getValue()){
                result.add(entry.getKey());
            }
        }
        
        for(Subjects subject : result){
            subject.getTerms().add(term);
            subjectDAO.updateSubject(subject);            

        }

        term.getSubjects().addAll(result);
        termDAO.updateTerm(term);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('addSubject').hide();");
        term.setSubjectAdded(true);
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful",  Messages.addSubjectsToTermSuccess));
        
    }
    
    public void assignLecturers(){
        List<Lecturer> result = new ArrayList<Lecturer>();

        result = lecturers.getTarget();
        
        Set<Student> studentsForTerm = null;
        studentsForTerm = new HashSet<Student>();
        studentsForTerm.addAll(term.getCourse().getStudents());

        for(Lecturer lecturer : result){
            term.getLecturers().add(lecturer);
            
            for(Student student : studentsForTerm){
                Mark mark = new Mark();
                mark.setActive(true);
                mark.setSubject(subject);
                mark.setTerm(term);
                mark.setMark(0.0);
                mark.setStudent(student);
                Mark tempMark = mark;
                mark.getLecturers().add(lecturer);
//                if(finder.findMark(mark) == null){
                markDAO.addMark(mark);
                student.getMarks().add(mark);
  /*              } else {
                    markDAO.updateMark(mark);
                    student.getMarks().remove(tempMark);
                    student.getMarks().add(mark);
                }*/
                
                lecturer.getMarks().add(mark);                
            }
            
        }

        termDAO.updateTerm(term);
        for(Student student : studentsForTerm){
            studentDAO.updateStudent(student);
        }
        
        for(Lecturer lecturer : result){
            lecturer.getTerms().add(term);
            lecturerDAO.updateLecturer(lecturer);
        }
        
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('assignLecturersForm').hide();");
        term.setLecturerAdded(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful",  Messages.assignLecturersToTermSuccess));
    }
    
    public String startEdit(Term src) {
        term = src;
        isEdit = true;
        isAdd = false;
        return "/pages/addTerm.xhtml";
    }
    
    public String editTerm(){
        termDAO.updateTerm(term);
        isEdit = false;
        return "/pages/success.xhtml";
    }
    
    public String deleteTerm(Term src) {
        logger.info(src.toString());
        if (termDAO.deleteTerm(src.getId())) {
            return "/pages/success.xhtml";
        }
        return "";
    }

    public String showDetails(Term src){
        term = src;
        return "/pages/termDetails.xhtml";
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

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public ITermDAO getTermDAO() {
        return termDAO;
    }

    public void setTermDAO(ITermDAO termDAO) {
        this.termDAO = termDAO;
    }

    public List<Term> getTerms() {
        terms = new ArrayList<Term>();
        terms.addAll(termDAO.getAll());
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }

    public List<Subjects> getFilteredSubjects() {
        return filteredSubjects;
    }

    public void setFilteredSubjects(List<Subjects> filteredSubjects) {
        this.filteredSubjects = filteredSubjects;
    }

    public Map<Subjects, Boolean> getCheckMap() {
        return checkMap;
    }

    public void setCheckMap(Map<Subjects, Boolean> checkMap) {
        this.checkMap = checkMap;
    }

    public DualListModel<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(DualListModel<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }
}
