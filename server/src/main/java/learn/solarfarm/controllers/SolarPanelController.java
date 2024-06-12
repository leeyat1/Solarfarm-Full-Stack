package learn.solarfarm.controllers;

import learn.solarfarm.data.DataAccessException;
import learn.solarfarm.domain.ResultType;
import learn.solarfarm.domain.Result;
import learn.solarfarm.domain.SolarPanelService;
import learn.solarfarm.models.SolarPanel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/solarpanel")
@CrossOrigin
public class SolarPanelController {
    private final SolarPanelService service;

    public SolarPanelController(SolarPanelService service) {
        this.service = service;
    }

    @GetMapping
    public List<SolarPanel> findAll() throws DataAccessException {
        return service.findAll();
    }

//    @GetMapping
//    public ResponseEntity<?> findAll() throws DataAccessException {
//        try {
//            List<SolarPanel> panels = service.findAll();
//            return new ResponseEntity<>(panels, HttpStatus.OK);
//        } catch (Exception ex) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/section/{section}")
    public List<SolarPanel> findBySection(@PathVariable String section) throws DataAccessException {
        return service.findBySection(section);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolarPanel> findById(@PathVariable int id) throws DataAccessException {
        SolarPanel solarPanel = service.findById(id);
        if (solarPanel == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(solarPanel, HttpStatus.OK);
    }

    @GetMapping("/personal")
    public ResponseEntity<?> findByUserId(@RequestHeader HashMap<String, String> headers) throws DataAccessException {
        if (headers.get("authorization") == null) {
            return new ResponseEntity<>(List.of("You must be logged in"), HttpStatus.FORBIDDEN);
        }

        int userId = Integer.parseInt(headers.get("authorization"));
        List<SolarPanel> result = service.findByUserId(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SolarPanel solarPanel, @RequestHeader HashMap<String, String> headers) throws Exception {
        if (headers.get("authorization") == null) {
            return new ResponseEntity<>(List.of("You must be logged in"), HttpStatus.FORBIDDEN);
        }

        int userId = Integer.parseInt(headers.get("authorization"));
        solarPanel.setUserId(userId);
        Result<SolarPanel> result = service.create(solarPanel);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED); // 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody SolarPanel solarPanel, @RequestHeader HashMap<String, String> headers) throws DataAccessException {
        if (headers.get("authorization") == null) {
            return new ResponseEntity<>(List.of("You must be logged in"), HttpStatus.FORBIDDEN);
        }

        if (id != solarPanel.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
        }

        int userId = Integer.parseInt(headers.get("authorization"));
        SolarPanel existingSolarPanel = service.findById(id);
        if (existingSolarPanel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (existingSolarPanel.getUserId() != userId) {
            return new ResponseEntity<>(List.of("You cannot edit a panel you don't own"), HttpStatus.FORBIDDEN);
        }

        solarPanel.setUserId(userId);
        Result<SolarPanel> result = service.update(solarPanel);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, @RequestHeader HashMap<String, String> headers) throws DataAccessException {
        if (headers.get("authorization") == null) {
            return new ResponseEntity<>(List.of("You must be logged in"), HttpStatus.FORBIDDEN);
        }

        int userId = Integer.parseInt(headers.get("authorization"));
        SolarPanel existingSolarPanel = service.findById(id);
        if (existingSolarPanel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (existingSolarPanel.getUserId() != userId) {
            return new ResponseEntity<>(List.of("You cannot delete a panel you don't own"), HttpStatus.FORBIDDEN);
        }

        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }
}