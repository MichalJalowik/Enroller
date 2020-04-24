package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.PSource;
import javax.servlet.http.Part;
import java.util.*;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    Participant participant;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings() {
        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Meeting meeting){
//        Meeting foundMeetingTitle = meetingService.findByTitle(meeting.getTitle());
//        if(foundMeetingTitle != null){
//            return new ResponseEntity("cannot create requested Meeting ID " + meeting.getTitle() + " , because it's already exists! ", HttpStatus.CONFLICT);
//        }

        meetingService.add(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        meetingService.delete(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
    public ResponseEntity<?> getParticipants(@PathVariable("id") Long id){

        Meeting meeting = meetingService.findById(id);

        Collection<Participant> participants = meeting.getParticipants();
//        Participant p1 = new Participant();
//        Participant p2 = new Participant();
//        participants.add(p1);
//        participants.add(p2);
        return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);

    }


    @RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipant(@RequestBody Participant participant, @PathVariable("id") Long id){

        Meeting meeting = meetingService.findById(id);

        meeting.addParticipant(participant);
        return new ResponseEntity<Participant>(participant, HttpStatus.CREATED);
    }


//    @RequestMapping(value = "/{id}/participants/{login}", method = RequestMethod.DELETE)
//    public ResponseEntity<?> addParticipant(@PathVariable("id") Long id, @PathVariable("login") String login){
//
//        Meeting meeting = meetingService.findById(id);
//
//
//        String delete = participant.getLogin();
//
//        meeting.removeParticipant(participant);
//        return new ResponseEntity<Participant>(participant, HttpStatus.CREATED);
//    }



}
