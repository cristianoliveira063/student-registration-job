package br.com.student.registration.job;

import br.com.student.registration.domain.model.Student;
import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StudentProcessor implements ItemProcessor<StudentImport, Student> {

    @Override
    public Student process(StudentImport studentImport) {
        return Student.builder()
                .id(UuidCreator.getTimeOrderedEpoch())
                .name(studentImport.getName())
                .cpf(studentImport.getCpf())
                .age(Integer.parseInt(studentImport.getAge()))
                .email(studentImport.getEmail())
                .phone(studentImport.getPhone())
                .build();
    }
}
