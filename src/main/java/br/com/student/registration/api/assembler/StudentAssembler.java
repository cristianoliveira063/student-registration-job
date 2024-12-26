package br.com.student.registration.api.assembler;

import br.com.student.registration.api.model.request.StudentRequest;
import br.com.student.registration.api.model.response.StudentResponse;
import br.com.student.registration.core.mapper.StudentMapper;
import br.com.student.registration.domain.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentAssembler {

    private final StudentMapper studentMapper;

    public Student mapToStudentEntityFromRequest(StudentRequest studentRequest) {
        return studentMapper.mapToEntity(studentRequest);
    }

    public void copyPropertiesToEntity(StudentRequest studentRequest, Student student) {
        studentMapper.updateEntityFromRequest(studentRequest, student);
    }

    public StudentResponse mapToStudentResponseFromEntity(Student student) {
        return studentMapper.mapToResponse(student);
    }

    public List<StudentResponse> mapToStudentResponseListFromEntities(List<Student> students) {
        return studentMapper.mapToResponseList(students);
    }

}
