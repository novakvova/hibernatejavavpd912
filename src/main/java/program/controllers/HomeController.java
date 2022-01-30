package program.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import program.dto.author.AuthorDto;
import program.entities.Author;
import program.repositories.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final AuthorRepository authorRepository;

    @GetMapping("/")
    public List<AuthorDto> index() {
        List<Author> list = authorRepository.findAll();
        List<AuthorDto> result = new ArrayList<>();
        for (Author author: list) {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setId(author.getId());
            authorDto.setName(author.getFullName());
            result.add(authorDto);
        }
        return result;
    }
}
