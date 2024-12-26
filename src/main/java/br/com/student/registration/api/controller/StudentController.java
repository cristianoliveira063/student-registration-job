package br.com.student.registration.api.controller;

import br.com.student.registration.api.assembler.StudentAssembler;
import br.com.student.registration.api.model.request.StudentRequest;
import br.com.student.registration.api.model.response.StudentResponse;
import br.com.student.registration.domain.filter.StudentFilter;
import br.com.student.registration.domain.model.Student;
import br.com.student.registration.domain.repository.StudentRepository;
import br.com.student.registration.domain.service.StudentService;
import br.com.student.registration.infraestruture.spec.StudentSpecs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final StudentAssembler studentAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse create(@RequestBody @Valid StudentRequest studentRequest) {
        Student student = studentAssembler.mapToStudentEntityFromRequest(studentRequest);
        return studentAssembler.mapToStudentResponseFromEntity(studentService.save(student));
    }

    @GetMapping
    public Page<StudentResponse> getAll(StudentFilter studentFilter, Pageable pageable) {
        Page<Student> studentPage = studentRepository.findAll(StudentSpecs.filter(studentFilter), pageable);
        List<StudentResponse> studentResponses = studentAssembler
                .mapToStudentResponseListFromEntities(studentPage.getContent());
        return new PageImpl<>(studentResponses, pageable, studentPage.getTotalElements());
    }

}
