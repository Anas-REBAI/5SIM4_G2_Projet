package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorServices.addInstructor(instructor);

        assertNotNull(savedInstructor);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    public void testRetrieveAllInstructors() {
        Instructor instructor1 = new Instructor();
        Instructor instructor2 = new Instructor();
        List<Instructor> instructors = Arrays.asList(instructor1, instructor2);

        when(instructorRepository.findAll()).thenReturn(instructors);

        List<Instructor> retrievedInstructors = instructorServices.retrieveAllInstructors();

        assertEquals(2, retrievedInstructors.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(1L);

        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);

        assertNotNull(updatedInstructor);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    public void testRetrieveInstructor() {
        Long instructorId = 1L;
        Instructor instructor = new Instructor();
        instructor.setNumInstructor(instructorId);

        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));

        Instructor retrievedInstructor = instructorServices.retrieveInstructor(instructorId);

        assertNotNull(retrievedInstructor);
        assertEquals(instructorId, retrievedInstructor.getNumInstructor());
        verify(instructorRepository, times(1)).findById(instructorId);
    }

    @Test
    public void testAddInstructorAndAssignToCourse() {
        Long courseId = 1L;
        Course course = new Course();
        course.setNumCourse(courseId);

        Instructor instructor = new Instructor();
        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor assignedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, courseId);

        assertNotNull(assignedInstructor);
        assertNotNull(assignedInstructor.getCourses());
        assertEquals(1, assignedInstructor.getCourses().size());
        assertTrue(assignedInstructor.getCourses().contains(course));
        verify(courseRepository, times(1)).findById(courseId);
        verify(instructorRepository, times(1)).save(instructor);
    }
}
