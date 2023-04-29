package com.example.jpaap;

import com.example.jpaap.entities.Patient;
import com.example.jpaap.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaApApplication implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaApApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {


        //Ajouter des patients manuellement
        patientRepository.save(new Patient(null,"adnane",new Date(),true,5677));
        patientRepository.save(new Patient(null,"ihssan",new Date(),false,56));
        patientRepository.save(new Patient(null,"med",new Date(),true,100));
        patientRepository.save(new Patient(null,"ibtissam",new Date(),false,156));

        //Ajouter des patients par une boucle
        for(int i=0; i<100; i++) {
            patientRepository.save(new Patient(null, "hassan", new Date(), Math.random() > 0.5 ? true : false, (int) (Math.random() * 100)));
        }


        //Diviser les patients en groupes de 5 et afficher le deuxieme
        Page<Patient> patients =  patientRepository.findAll(PageRequest.of(1,5));
        patients.forEach(p->{
            System.out.println("++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });

        System.out.println("Total pages: " + patients.getTotalPages());
        System.out.println("Total elements: " + patients.getTotalElements());
        System.out.println("Num page: " + patients.getNumber());
        List<Patient> content = patients.getContent();


        //Selectionner les patients ayant Malade true et choisir les 4 premiers
        Page<Patient> byMalade = patientRepository.findByMalade(true, PageRequest.of(0,4));
        byMalade.forEach(p->{
            System.out.println("------------------------------------------");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });


        //Chercher les patients dont leur nom contient la lettre h et avec un score <40
        List<Patient> patientList= patientRepository.chercherPatients("%h%",40);
        patientList.forEach(p->{
            System.out.println("===========================================");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });


        System.out.println("///////////////////////////////////////////////");
        Patient patient = patientRepository.findById(1L).orElse(null);
        if(patient!=null){
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
        }

        //Mettre le score du patient au dessus a 870
        patient.setScore(870);

        //Enregistrer la modification precedante dans la base de donnes
        patientRepository.save(patient);

        //Supprimer le patient avec id=1 de la base de donnees
        //patientRepository.deleteById(1L);

    }


}
