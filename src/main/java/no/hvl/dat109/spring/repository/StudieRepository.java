package no.hvl.dat109.spring.repository;

import no.hvl.dat109.spring.beans.StudieBean;
import org.springframework.data.repository.CrudRepository;

public interface StudieRepository extends CrudRepository<StudieBean, Integer> {
}
