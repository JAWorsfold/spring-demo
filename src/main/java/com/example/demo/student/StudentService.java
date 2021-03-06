package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        // TODO: can do more complex validation like regex for email validation
        if (studentOptional.isPresent()) {
            // TODO: better exception handling
            throw new IllegalStateException("Email Taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalStateException("Student with ID " + studentId + " does not exist.");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalStateException("Student with ID " + studentId + " does not exist.")
        );

        // TODO: errors for empty strings or just whitespace
        if (name != null && !name.isEmpty() && !name.isBlank() && !name.equals(student.getName())) {
            student.setName(name);
        }
        if (email != null && !email.isEmpty() && !email.isBlank() && !email.equals(student.getEmail())) {
            if (studentRepository.findStudentByEmail(email).isPresent()) {
                throw new IllegalStateException("Email Taken");
            }
            student.setEmail(email);
        }
    }

}
