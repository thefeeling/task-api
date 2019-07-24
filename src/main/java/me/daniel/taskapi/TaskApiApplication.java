package me.daniel.taskapi;

import com.github.javafaker.Faker;
import me.daniel.taskapi.global.model.search.Keyword;
import me.daniel.taskapi.global.model.search.SearchCategory;
import me.daniel.taskapi.global.model.user.UserId;
import me.daniel.taskapi.search.dao.UserSearchHistoryRepository;
import me.daniel.taskapi.search.domain.SearchHistory;
import me.daniel.taskapi.search.domain.UserSearchHistory;
import me.daniel.taskapi.user.dao.UserRepository;
import me.daniel.taskapi.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class TaskApiApplication implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserSearchHistoryRepository userSearchHistoryRepository;

//    private void saveDummyData() {
//        User user = repository.findByEmail("kschoi@dev.io").get();
//        Faker faker = new Faker(new Locale("ko"));
//        List<UserSearchHistory> list = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            list.add(UserSearchHistory.create(
//                UserId.of(user.getId()),
//                SearchHistory.create(
//                    SearchCategory.BOOK, Keyword.of(faker.book().title())
//                )
//            ));
//        }
//        userSearchHistoryRepository.saveAll(list);
//    }

    @Override
    public void run(String... args) throws Exception {
//        saveDummyData();
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskApiApplication.class, args);
    }

}
