package ee.balder.filters.service;

import ee.balder.filters.domain.Filter;
import ee.balder.filters.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilterService {

    private final FilterRepository filterRepository;

    public Optional<Filter> getById(Long id) {
        return filterRepository.findById(id);
    }

    public List<Filter> getAll() {
        return filterRepository.findAll();
    }

    public Filter save(Filter filter) {
        return filterRepository.save(filter);
    }

}
