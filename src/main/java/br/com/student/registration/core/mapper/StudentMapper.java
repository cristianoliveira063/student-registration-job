package br.com.student.registration.core.mapper;

import br.com.student.registration.api.model.request.StudentRequest;
import br.com.student.registration.api.model.response.StudentResponse;
import br.com.student.registration.domain.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = StudentMapper.SPRING_COMPONENT_MODEL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StudentMapper {

    String SPRING_COMPONENT_MODEL = "spring";

    Student mapToEntity(StudentRequest studentRequest);

    void updateEntityFromRequest(StudentRequest source, @MappingTarget Student target);

    StudentResponse mapToResponse(Student student);

    List<StudentResponse> mapToResponseList(List<Student> students);
}
