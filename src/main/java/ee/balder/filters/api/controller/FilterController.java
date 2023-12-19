package ee.balder.filters.api.controller;

import ee.balder.filters.domain.Filter;
import ee.balder.filters.service.FilterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;

    @GetMapping("/{id}")
    public ResponseEntity<Filter> getFilter(@PathVariable long id) {
        var filter = filterService.getById(id);
        return filter.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveFilter(@RequestBody @Valid Filter filter) {
        filterService.save(filter);
        return ResponseEntity.ok("Filter saved successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Filter>> getFilters() {
        return ResponseEntity.ok(filterService.getAll());
    }

}
