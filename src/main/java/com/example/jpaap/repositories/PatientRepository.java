package com.example.jpaap.repositories;
import com.example.jpaap.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    public List<Patient> findByMalade(boolean m);
    Page<Patient> findByMalade(boolean m, PageRequest of);
    List<Patient> findByMaladeAndScoreLessThan(boolean m , int score);
    List<Patient> findByMaladeIsTrueAndScoreLessThan(int score);
    List<Patient> findByDateNaissanceBetweenAndMaladeIsTrueOrNomLike(Date d1, Date d2, String mc);
    @Query("select p from Patient p where p.nom like :x and p.score< :y")
    List<Patient> chercherPatients(@Param("x") String n, @Param("y") int s);






}
