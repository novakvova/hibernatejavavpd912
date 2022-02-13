package program.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import program.dto.author.AuthorAddDto;
import program.dto.author.AuthorDto;
import program.entities.Author;
import program.mapper.ApplicationMapper;
import program.repositories.AuthorRepository;
import program.storage.StorageService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
//@RequiredArgsConstructor
public class HomeController {
    private final AuthorRepository authorRepository;
    private final ApplicationMapper applicationMapper;
    private final StorageService storageService;

    @Autowired
    public HomeController(AuthorRepository authorRepository, ApplicationMapper applicationMapper,
                          StorageService storageService) {
        this.authorRepository = authorRepository;
        this.applicationMapper = applicationMapper;
        this.storageService = storageService;
    }

    @GetMapping("/")
    public List<AuthorDto> index() {
       return  applicationMapper
               .ListAuthorByListAuthorDto(authorRepository.findAll());
    }

    @PostMapping("/")
    public String create(AuthorAddDto model) {
        Author author = applicationMapper.AuthorByAddAuthorDto(model);
        String fileName=storageService.store(model.getImageBase64());
        author.setImage(fileName);
        authorRepository.save(author);
        return fileName;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws Exception {

        Resource file = storageService.loadAsResource(filename);
        String urlFileName =  URLEncoder.encode("сало.jpg", StandardCharsets.UTF_8.toString());
        return ResponseEntity.ok()
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
                .contentType(MediaType.IMAGE_JPEG)

                .header(HttpHeaders.CONTENT_DISPOSITION,"filename=\""+urlFileName+"\"")
                .body(file);
    }

}
