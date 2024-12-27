package br.com.student.registration.job;

import br.com.student.registration.domain.model.Student;
import br.com.student.registration.domain.repository.StudentRepository;
import com.github.f4b6a3.uuid.UuidCreator;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StudentProcessor implements ItemProcessor<StudentImport, Student> {
    private final StudentRepository studentRepository;

    @Override
    public Student process(StudentImport studentImport) {
        var existingStudent = studentRepository.findByCpf(studentImport.getCpf());
        int age = Integer.parseInt(studentImport.getAge());

        return existingStudent
                .map(student -> updateExistingStudent(student, studentImport, age))
                .orElseGet(() -> createNewStudent(studentImport, age));
    }

    private Student updateExistingStudent(Student student, StudentImport studentImport, int age) {
        return Student.builder()
                .id(student.getId())
                .cpf(student.getCpf())
                .name(studentImport.getName())
                .phone(studentImport.getPhone())
                .email(studentImport.getEmail())
                .age(age)
                .createdAt(student.getCreatedAt())
                .build();
    }

    private Student createNewStudent(StudentImport studentImport, int age) {
        return Student.builder()
                .id(UuidCreator.getTimeOrderedEpoch())
                .name(studentImport.getName())
                .cpf(studentImport.getCpf())
                .age(age)
                .email(studentImport.getEmail())
                .phone(studentImport.getPhone())
                .build();
    }
}
