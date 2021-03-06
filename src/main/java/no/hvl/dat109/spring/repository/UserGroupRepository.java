package no.hvl.dat109.spring.repository;

import no.hvl.dat109.spring.beans.UserGroupBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroupBean, Integer> {
}
