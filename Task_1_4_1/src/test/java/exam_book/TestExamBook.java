package exam_book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestExamBook {

    Discipline osi, java;
    String irtegov, vlasov;

    @BeforeEach
    void setUp() {
        osi = new Discipline("Operating Systems", "СХЙ-007");
        java = new Discipline("OOP", "ОПК-131");
        irtegov = "Irtegov";
        vlasov = "Alexander Vlasov";
    }

    @Test   
    void testDisciplineInitialized() {
        Subject subject = new Subject(java, 5, vlasov, false); 
        assertEquals(java, subject.discipline);  
    }

    @Test
    void testMarkInitialized() {
        Subject subject = new Subject(osi, 2, irtegov, true); 
        assertEquals(2, subject.getMark());
    }

    @Test
    void testTeacherInitialized() {  
        String teacher = "Mr. Smith";
        Subject subject = new Subject(osi, 4, teacher, false);
        assertEquals(teacher, subject.teacher); 
    }

    @Test
    void testIsExamInitialized() {
        Subject subject = new Subject(java, 3, irtegov, true);
        assertTrue(subject.isExam);
    }

    @Test
    void testSetValidMark() {
        Subject subject = new Subject(osi, 2, vlasov, false);      
        subject.setMark(3);
        assertEquals(3, subject.getMark());
    }

    @Test
    void testSetInvalidMark() {
        Discipline math = new Discipline("Math", "ЩШГ-812");
        Subject subject = new Subject(math, 4, "Vaskevich", true);
        assertThrows(IllegalArgumentException.class, () -> {
            subject.setMark(6);
        });
    }

    @Test
    void testNumberInitialized() {
        int testNumber = 2;
        Semester semester = new Semester(testNumber);
        assertEquals(testNumber, semester.semesterNum);
    }

    @Test 
    void testSumInitializedToZero() {
        Semester semester = new Semester(1);
        assertEquals(0, semester.getGrade());
    }

    @Test
    void testGoodSemesterInitializedToTrue() {
        Semester semester = new Semester(2);
        assertTrue(semester.goodSemester());
    }

    @Test
    void testAddSubject() {
        Semester semester = new Semester(3); 
        Subject testSubject = new Subject(osi, 2, irtegov, true); 
        semester.addSubject(testSubject);
        assertEquals(1, semester.quantityOfSubjects());
    }

    @Test
    void testExistingSubjectReturned() {
        Semester semester = new Semester(4);
        Subject testSubject = new Subject(java, 5, vlasov, true); 
        semester.addSubject(testSubject);

        Subject result = semester.getSubject(0);
        assertSame(testSubject, result);  
    }

    /////
    
    @Test
    void testConstructor_StudentNameInitialized() {
        String testName = "John";
        ExamBook book = new ExamBook(testName);
        assertEquals(testName, book.student);
    }

    @Test
    void testConstructor_CurrentSemesterInitializedToZero() {
        ExamBook book = new ExamBook("Test");
        assertEquals(1, book.getSemester());
    }

    @Test
    void testConstructor_QualifiedWorkInitializedToZero() {
        ExamBook book = new ExamBook("Test");
        assertEquals(0, book.qualifiedWork); 
    }

    @Test
    void testConstructor_SemestersListInitialized() {
        ExamBook book = new ExamBook("Test");
        assertNotNull(book.semesters);
    }

    @Test
    void testNewSemester_SemesterIsAdded() {
        ExamBook book = new ExamBook("Test");
        book.newSemester();  
        assertEquals(2, book.semesters.size());
    }

    @Test
    void testGetSemester_ReturnsCorrectSemester() {
        ExamBook book = new ExamBook("Test");  
        book.newSemester();
        
        Semester semester = book.getSemester();
        assertEquals(2, semester.semesterNum);
    }

    @Test
    void testSetQualifier_InvalidMarkThrowsException() {
        ExamBook book = new ExamBook("Test");

        assertThrows(IllegalArgumentException.class, () -> {
            book.setQualifier(6);
        });
    }
    
    // ...

    @Test
    void testGetCurrentAverageMark_EmptyBookReturnsZero() {
        ExamBook book = new ExamBook("Test");
        
        assertEquals(0, book.getCurrentAverageMark());
    }

    @Test
    void testGetCurrentAverageMark_CalculatesCorrectly() {
        ExamBook book = new ExamBook("Test");
        
        book.getSemester().addSubject(new Subject(null, 5, null, false));
        book.newSemester();
        book.getSemester().addSubject(new Subject(null, 4, null, false));
        
        assertEquals(4.5, book.getCurrentAverageMark());  
    }

    @Test
    void testUpSalary_Eligible() {
        ExamBook book = new ExamBook("Test");
        
        book.getSemester().addSubject(new Subject(null, 5, null, false));
        
        assertTrue(book.upSalary());
    }

    @Test
    void testUpSalary_NotEligible() {
        ExamBook book = new ExamBook("Test");
        
        book.getSemester().addSubject(new Subject(null, 4, null, false));
        
        assertFalse(book.upSalary());
    }

    @Test
    void testRedDiploma_Eligible() {
        ExamBook book = new ExamBook("Test");
        
        book.setQualifier(5);
        
        for (int i = 0; i < 8; i++) {
            book.newSemester();
            book.getSemester().addSubject(new Subject(null, 5, null, false)); 
        }
        
        assertTrue(book.redDiploma());
    }

    @Test
    void testRedDiploma_NotEligible_LowAverageMark() {
        ExamBook book = new ExamBook("Test");
        
        book.setQualifier(5);
        
        for (int i = 0; i < 8; i++) {
            book.newSemester();
            book.getSemester().addSubject(new Subject(null, 4, null, false));
        }
        
        assertFalse(book.redDiploma()); 
    }

    @Test
    void testRedDiploma_NotEligible_BadSemester() {
        ExamBook book = new ExamBook("Test");
        
        book.setQualifier(5);
        
        for (int i = 0; i < 7; i++) {
            book.newSemester();
            book.getSemester().addSubject(new Subject(null, 5, null, false));
        }
        
        book.newSemester(); 
        book.getSemester().addSubject(new Subject(null, 3, null, false));
        
        assertFalse(book.redDiploma());
    }
}