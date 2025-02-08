package com.mynotes.myNotes.service;

import com.mynotes.myNotes.DTO.NoteInputDTO;
import com.mynotes.myNotes.DTO.NoteOutputDTO;
import com.mynotes.myNotes.model.Note;
import com.mynotes.myNotes.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    //Get By ID
    public NoteOutputDTO getById(Long id){
        try{
            Optional<Note> possibleNote = noteRepository.findById(id);
            if(possibleNote.isPresent()){
                return new NoteOutputDTO(possibleNote.get());
            }
            else{
                throw new EntityNotFoundException();
            }

        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    //List all
    public List<NoteOutputDTO> listAll(){
        try{
            List<Note> notes = noteRepository.findAll();
            List<NoteOutputDTO> outputDTOS = new ArrayList<>();
            for(Note note: notes){
                outputDTOS.add(new NoteOutputDTO(note));
            }
            return outputDTOS;

        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());

        }
    }

    //Create
    public NoteOutputDTO create(NoteInputDTO noteInputDTO){
        try{
            Note noteToCreate = new Note();
            noteToCreate.setTitle(noteInputDTO.getTitle());
            noteToCreate.setContent(noteInputDTO.getContent());

            Note createdNote = noteRepository.save(noteToCreate);

            return new NoteOutputDTO(createdNote);
        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //Delete
    public void delete(Long id){
        try{
            Optional<Note> possibleNote = noteRepository.findById(id);
            possibleNote.ifPresent(note -> noteRepository.delete(note));

        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());

        }
    }

    //Update
    public NoteOutputDTO update(Long id, NoteInputDTO noteInputDTO){
        try{
            Optional<Note> possibleNote = noteRepository.findById(id);

            if(possibleNote.isPresent()){
                Note noteToEdit = possibleNote.get();

                noteToEdit.setTitle(noteInputDTO.getTitle());
                noteToEdit.setContent(noteInputDTO.getContent());

                Note editedNote = noteRepository.save(noteToEdit);

                return new NoteOutputDTO(editedNote);

            }
            else{
                throw new EntityNotFoundException();
            }

        }
        catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
