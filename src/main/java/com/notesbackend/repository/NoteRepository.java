package com.notesbackend.repository;

import com.notesbackend.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByShareableLink(String shareableLink);
}
