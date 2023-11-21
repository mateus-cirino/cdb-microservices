package br.com.cdbservice.service;

import br.com.cdbservice.model.entity.Paper;
import br.com.cdbservice.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    public Paper save(final Paper paper) {
        return paperRepository.save(paper);
    }

    public List<Paper> findAll() {
        return paperRepository.findAll();
    }

    public Paper findById(final Long id) {
        return paperRepository.findById(id).orElse(new Paper());
    }
}
