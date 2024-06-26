package org.example.mongospring.config;

import org.example.mongospring.domain.Post;
import org.example.mongospring.domain.User;
import org.example.mongospring.dto.AuthorDTO;
import org.example.mongospring.dto.CommentDTO;
import org.example.mongospring.repository.UserRepository;
import org.example.mongospring.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;


@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        userRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria,alex,bob));

        Post post1 = new Post(null, sdf.parse("17/12/2004"), "Partiu Viagem", "VOU PARA BENTO. TCHAU", new AuthorDTO(maria));
        Post post2 = new Post(null, sdf.parse("29/04/2004"), "Partiu TRIP", "VOU PARA SALVADOR. TCHAU", new AuthorDTO(maria));

        CommentDTO c1 = new CommentDTO("Boa Viagem mano!", sdf.parse("18/12/2004"), new AuthorDTO(alex));
        CommentDTO c2 = new CommentDTO("Na paz de Deus!", sdf.parse("30/04/2004"), new AuthorDTO(bob));

        post1.getComments().addAll(Arrays.asList(c1));
        post2.getComments().addAll(Arrays.asList(c2));

        postRepository.saveAll(Arrays.asList(post1,post2));
        maria.getPosts().addAll(Arrays.asList(post1,post2));
        userRepository.save(maria);

    }
}
