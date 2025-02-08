package com.mynotes.myNotes.controller;

import com.mynotes.myNotes.DTO.NoteInputDTO;
import com.mynotes.myNotes.DTO.NoteOutputDTO;
import com.mynotes.myNotes.service.NoteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<NoteOutputDTO> getNoteById(@PathVariable Long id){
        try{
            NoteOutputDTO note = noteService.getById(id);
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //GET
    @GetMapping
    public ResponseEntity<List<NoteOutputDTO>> getAllNotes(){
        try{
            List<NoteOutputDTO> notes = noteService.listAll();
            return new ResponseEntity<>(notes, HttpStatus.OK);

        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //POST
    @PostMapping
    public ResponseEntity<NoteOutputDTO> createNote(@RequestBody NoteInputDTO noteInputDTO){
        try{
            NoteOutputDTO createdNote = noteService.create(noteInputDTO);

            return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id){
        try{
            noteService.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<NoteOutputDTO> updateNote(@PathVariable Long id, @RequestBody NoteInputDTO noteInputDTO){
        try{
            NoteOutputDTO editedNote = noteService.update(id, noteInputDTO);
            return new ResponseEntity<>(editedNote, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
