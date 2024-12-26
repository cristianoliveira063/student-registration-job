package br.com.student.registration.infraestruture.spec;

import br.com.student.registration.domain.filter.StudentFilter;
import br.com.student.registration.domain.model.Student;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

@UtilityClass
public class StudentSpecs {

    public static Specification<Student> filter(StudentFilter studentFilter) {
        return ((root, query, criteriaBuilder) -> {

            var predicates = new ArrayList<Predicate>();

            if (nonNull(studentFilter.cpf())) {
                predicates.add(criteriaBuilder.equal(root.get("cpf"), studentFilter.cpf()));
            }
            if (nonNull(studentFilter.name())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + studentFilter.name() + "%"));
            }
            if (nonNull(studentFilter.email())) {
                predicates.add(criteriaBuilder.equal(root.get("email"), studentFilter.email()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
