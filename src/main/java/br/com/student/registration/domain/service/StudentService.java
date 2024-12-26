package br.com.student.registration.domain.service;

import br.com.student.registration.domain.model.Student;
import br.com.student.registration.domain.repository.StudentRepository;
import com.github.f4b6a3.uuid.UuidCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public Student save(Student student) {
        if (isNull(student.getId())) {
            log.info("Saving student {}", student);
            student.setId(UuidCreator.getTimeOrderedEpoch());
        }
        return studentRepository.save(student);
    }

}
