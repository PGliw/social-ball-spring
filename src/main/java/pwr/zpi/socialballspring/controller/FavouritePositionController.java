package pwr.zpi.socialballspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.zpi.socialballspring.config.IIdentityManager;
import pwr.zpi.socialballspring.dto.FavouritePositionDto;
import pwr.zpi.socialballspring.dto.Response.FavouritePositionResponse;
import pwr.zpi.socialballspring.service.FavouritePositionService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/favouritePositions")
public class FavouritePositionController {
    @Autowired
    private FavouritePositionService favouritePositionService;

    @Autowired
    IIdentityManager identityManager;

    @PostMapping
    public ResponseEntity<FavouritePositionResponse> saveFavouritePosition(@RequestBody FavouritePositionDto FavouritePosition) {
        return new ResponseEntity<>(favouritePositionService.save(FavouritePosition), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FavouritePositionResponse>> listFavouritePosition() {
        return new ResponseEntity<>(favouritePositionService.findAll(identityManager.getCurrentUser().getId()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<FavouritePositionResponse>> getOne(@PathVariable long id) {
        return new ResponseEntity<>(favouritePositionService.findAll(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavouritePositionResponse> update(@RequestBody FavouritePositionDto FavouritePositionDto, @PathVariable long id) {
        return new ResponseEntity<>(favouritePositionService.update(FavouritePositionDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        favouritePositionService.delete(id);
        return new ResponseEntity<>("FavouritePosition deleted successfully", HttpStatus.OK);
    }
}
