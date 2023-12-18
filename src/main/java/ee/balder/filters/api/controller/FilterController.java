package ee.balder.filters.api.controller;

import ee.balder.filters.domain.Filter;
import ee.balder.filters.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;

    @GetMapping("/all")
    public ResponseEntity<List<Filter>> getFilters() {
        return ResponseEntity.ok(filterService.getAll());
    }

}
