package com.notesbackend.repository;

import com.notesbackend.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByShareableLink(String shareableLink);
   
        List<Note> findByUserId(Long userId);
 
        List<Note> findAllByUserId(Long userId);
        Optional<Note> findByIdAndUserId(Long id, Long userId);

        
    }


